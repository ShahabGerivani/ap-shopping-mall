import javax.swing.*;
import java.sql.Connection;

public class ProfilePanel extends JPanel {
    ProfilePanel(JFrame frame, Connection dbConnection, User user) {
        super();
        UserNavBar navBar = new UserNavBar(user.getBalance(),  "خوش آمدید " + user.getUsername());
        this.add(navBar);
    }
}
