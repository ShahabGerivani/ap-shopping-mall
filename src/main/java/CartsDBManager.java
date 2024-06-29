import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class CartsDBManager {
    private final Connection dbConnection;

    public CartsDBManager(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

    public Cart getOrCreateCartForUser(String username) throws SQLException {
        Cart cart;
        PreparedStatement getCartStmt = dbConnection.prepareStatement("SELECT * FROM carts WHERE user_username = ? AND finalized = 0");
        getCartStmt.setString(1, username);
        ResultSet getCartRs = getCartStmt.executeQuery();
        if (getCartRs.next()) {
            cart = new Cart(getCartRs.getInt("id"), username, new LinkedHashMap<>());
            PreparedStatement getProductsForCartStmt = dbConnection.prepareStatement("SELECT * FROM carts_products WHERE cart_id = ?");
            getProductsForCartStmt.setInt(1, cart.getId());
            ResultSet getProductsForCartRs = getProductsForCartStmt.executeQuery();
            ProductsDBManager productsDBManager = new ProductsDBManager(dbConnection);
            Product product;
            while (getProductsForCartRs.next()) {
                product = productsDBManager.getProductById(getProductsForCartRs.getInt("product_id"));
                if (product != null) {
                    cart.getProductsAndCount().put(product, getProductsForCartRs.getInt("product_count"));
                }
            }
            return cart;
        } else {
            PreparedStatement createCartStmt = dbConnection.prepareStatement("INSERT INTO carts (user_username) VALUES (?)");
            createCartStmt.setString(1, username);
            createCartStmt.executeUpdate();
            return getOrCreateCartForUser(username);
        }
    }

    public void addProductToCart(int cart_id, int product_id) throws SQLException {
        PreparedStatement addProductToCartStmt = dbConnection.prepareStatement("INSERT INTO carts_products (cart_id, product_id) VALUES (?, ?)");
        addProductToCartStmt.setInt(1, cart_id);
        addProductToCartStmt.setInt(2, product_id);
        addProductToCartStmt.executeUpdate();
    }

    public void setProductCountInCart(int cart_id, int product_id, int product_count) throws SQLException {
        PreparedStatement addProductCountInCartStmt = dbConnection.prepareStatement("UPDATE carts_products SET product_count = ? WHERE cart_id = ? AND product_id = ?");
        addProductCountInCartStmt.setInt(1, product_count);
        addProductCountInCartStmt.setInt(2, cart_id);
        addProductCountInCartStmt.setInt(3, product_id);
        addProductCountInCartStmt.executeUpdate();
    }

    public void removeProductFromCart(int cart_id, int product_id) throws SQLException {
        PreparedStatement removeProductStmt = dbConnection.prepareStatement("DELETE FROM carts_products WHERE cart_id = ? AND product_id = ?");
        removeProductStmt.setInt(1, cart_id);
        removeProductStmt.setInt(2, product_id);
        removeProductStmt.executeUpdate();
    }

    public void finalizeCart(Cart cart) throws SQLException {
        double finalizedTotal = 0;

        PreparedStatement buyProductsStmt;
        for (Product product : cart.getProductsAndCount().keySet()) {
            buyProductsStmt = dbConnection.prepareStatement("UPDATE products SET stock = ? WHERE id = ?");
            buyProductsStmt.setInt(1, product.getStock() - cart.getProductsAndCount().get(product));
            buyProductsStmt.setInt(2, product.getId());
            buyProductsStmt.executeUpdate();
            finalizedTotal += cart.getProductsAndCount().get(product) * product.getPrice();
        }

        PreparedStatement finalizeCartStmt = dbConnection.prepareStatement("UPDATE carts SET finalized = 1, finalized_total = ? WHERE id = ?");
        finalizeCartStmt.setDouble(1, finalizedTotal);
        finalizeCartStmt.setInt(2, cart.getId());
        finalizeCartStmt.executeUpdate();
    }
}
