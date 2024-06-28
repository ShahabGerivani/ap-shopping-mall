import java.sql.*;
import java.util.ArrayList;

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
        PreparedStatement setBalanceStmt = dbConnection.prepareStatement("UPDATE users SET balance = ? WHERE username = ?");
        setBalanceStmt.setDouble(1, balance);
        setBalanceStmt.setString(2, username);
        setBalanceStmt.executeUpdate();
    }

    public ArrayList<User> getAllNonAdminUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();

        Statement getAllUsersStmt = dbConnection.createStatement();
        ResultSet getAllUsersRs = getAllUsersStmt.executeQuery("SELECT * FROM users WHERE is_admin = 0");
        while (getAllUsersRs.next()) {
            users.add(new User(
                    getAllUsersRs.getString("username"),
                    getAllUsersRs.getString("name"),
                    getAllUsersRs.getString("phone"),
                    getAllUsersRs.getString("address"),
                    getAllUsersRs.getDouble("balance"),
                    getAllUsersRs.getBoolean("is_admin")
            ));
        }

        return users;
    }
}
