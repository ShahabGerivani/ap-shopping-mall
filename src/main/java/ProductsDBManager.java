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
            String imageFileName = productsRs.getString("image_file_name");
            if (imageFileName == null) {
                product.setImageFile(null);
            } else {
                product.setImageFile(new File(Main.PRODUCTS_IMAGES_FOLDER_PATH + imageFileName));
            }
            products.add(product);
        }

        statement.close();
        return products;
    }

    public int addNewProduct(String name, double price, String description, int stock, String imageExtension) throws SQLException {
        PreparedStatement insertNewProductStatement = dbConnection.prepareStatement("INSERT INTO products (name, price, description, stock) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        insertNewProductStatement.setString(1, name);
        insertNewProductStatement.setDouble(2, price);
        insertNewProductStatement.setString(3, description);
        insertNewProductStatement.setInt(4, stock);
        insertNewProductStatement.executeUpdate();

        insertNewProductStatement.getGeneratedKeys().next();
        int id = insertNewProductStatement.getGeneratedKeys().getInt(1);
        if (imageExtension != null) {
            PreparedStatement setProductImageFileNameStmt = dbConnection.prepareStatement("UPDATE products SET image_file_name = ? WHERE id = ?");
            setProductImageFileNameStmt.setString(1, id + "." + imageExtension);
            setProductImageFileNameStmt.setInt(2, id);
            setProductImageFileNameStmt.executeUpdate();
        }
        return id;
    }

    public void updateProduct(int id, double price, String description, int stock, String imageFileName) throws SQLException {
        PreparedStatement updateProductStmt = dbConnection.prepareStatement("UPDATE products SET price = ?, description = ?, stock = ?, image_file_name = ? WHERE id = ?");
        updateProductStmt.setDouble(1, price);
        updateProductStmt.setString(2, description);
        updateProductStmt.setInt(3, stock);
        updateProductStmt.setString(4, imageFileName);
        updateProductStmt.setInt(5, id);
        updateProductStmt.executeUpdate();
    }

    public void deleteProduct(int id) throws SQLException {
        PreparedStatement removeProductStmt = dbConnection.prepareStatement("DELETE FROM products WHERE id = ?");
        removeProductStmt.setInt(1, id);
        removeProductStmt.executeUpdate();
    }

    public Product getProductById(int id) throws SQLException {
        PreparedStatement getProductStmt = dbConnection.prepareStatement("SELECT * FROM products WHERE id = ?");
        getProductStmt.setInt(1, id);
        ResultSet getProductRs = getProductStmt.executeQuery();
        if (getProductRs.next()) {
            Product product = new Product(
                    getProductRs.getInt("id"),
                    getProductRs.getString("name"),
                    getProductRs.getDouble("price"),
                    getProductRs.getString("description"),
                    getProductRs.getInt("stock"),
                    null,
                    this.calculateRatingForProduct(getProductRs.getInt("id"))
            );

            String imageFileName = getProductRs.getString("image_file_name");
            if (imageFileName == null) {
                product.setImageFile(null);
            } else {
                product.setImageFile(new File(Main.PRODUCTS_IMAGES_FOLDER_PATH + imageFileName));
            }

            return product;
        } else {
            return null;
        }
    }
}
