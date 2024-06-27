import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDBManager {
    private final Connection dbConnection;

    public UsersDBManager(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

    public User getUser(String username) throws SQLException {
        User user = null;

        PreparedStatement getUserStmt = dbConnection.prepareStatement("SELECT * FROM users WHERE username = ?");
        getUserStmt.setString(1, username);
        ResultSet userRs = getUserStmt.executeQuery();

        if (userRs.next()) {
            user = new User(
                    userRs.getString("username"),
                    userRs.getString("name"),
                    userRs.getString("phone"),
                    userRs.getString("address"),
                    userRs.getDouble("balance"),
                    userRs.getBoolean("is_admin")
            );
        }

        return user;
    }

    public void setBalanceForUser(String username, double balance) throws SQLException {
        PreparedStatement setBalanceStmt = dbConnection.prepareStatement("UPDATE users SET balance = ?, WHERE username = ?");
        setBalanceStmt.setDouble(1, balance);
        setBalanceStmt.setString(2, username);
        setBalanceStmt.executeUpdate();
    }
}
