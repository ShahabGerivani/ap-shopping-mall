import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    MainFrame() throws SQLException {
        // Frame setup
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Shop");
        this.setSize(800, 600);
        this.setVisible(true);
        this.setResizable(false);

        // Getting a database connection
        Connection dbConnection = DriverManager.getConnection("jdbc:sqlite:./ap_shopping_mall.db");

        SignInPanel signInPanel = new SignInPanel(this, dbConnection);
        this.add(signInPanel);
    }
}
