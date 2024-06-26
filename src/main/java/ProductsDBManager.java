import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class ProductsDBManager {
    private final Connection dbConnection;

    public ProductsDBManager(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

    public double calculateRatingForProduct(int product_id) throws SQLException {
        int ratingsSum = 0;
        int ratingsCount = 0;

        PreparedStatement statement = dbConnection.prepareStatement("SELECT rating FROM ratings WHERE product_id = ?");
        statement.setInt(1, product_id);
        ResultSet ratingsRs = statement.executeQuery();

        while (ratingsRs.next()) {
            ratingsSum += ratingsRs.getInt("rating");
            ratingsCount++;
        }

        if (ratingsCount == 0) return 0;
        else return (double) ratingsSum / ratingsCount;
    }

    public ArrayList<Product> getAllProducts() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        Product product;

        Statement statement = dbConnection.createStatement();
        ResultSet productsRs = statement.executeQuery("SELECT * FROM products");
        while (productsRs.next()) {
            product = new Product(
                    productsRs.getInt("id"),
                    productsRs.getString("name"),
                    productsRs.getDouble("price"),
                    productsRs.getString("description"),
                    productsRs.getInt("stock"),
                    null,
                    this.calculateRatingForProduct(productsRs.getInt("id"))
            );
            try {
                product.setImageFile(new File(productsRs.getString("image_file_name")));
            } catch (NullPointerException e) {
                product.setImageFile(null);
            }
            products.add(product);
        }

        statement.close();
        return products;
    }
}
