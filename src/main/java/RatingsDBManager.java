import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RatingsDBManager {
    private final Connection dbConnection;

    public RatingsDBManager(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

    public void submitNewRating(String username, int product_id, int rating) throws SQLException {
        PreparedStatement submitRatingStatement = dbConnection.prepareStatement("INSERT INTO ratings (user_username, product_id, rating) VALUES (?, ?, ?)");
        submitRatingStatement.setString(1, username);
        submitRatingStatement.setInt(2, product_id);
        submitRatingStatement.setInt(3, rating);
        submitRatingStatement.executeUpdate();
    }
}
