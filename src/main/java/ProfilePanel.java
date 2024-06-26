import javax.swing.*;
import java.sql.Connection;

public class ProfilePanel extends JPanel {
    ProfilePanel(JFrame frame, Connection dbConnection, User user) {
        super();

        UserNavBar navBar = new UserNavBar(user.getBalance(),  "خوش آمدید " + user.getUsername());
        this.add(navBar);

        JLabel usernameLabel = new JLabel(user.getUsername());
        this.add(usernameLabel);

        this.add(new JLabel("نام کاربری"));

        JLabel nameLabel = new JLabel(user.getName());
        this.add(nameLabel);

        this.add(new JLabel("نام"));

        JLabel phoneLabel = new JLabel(user.getPhone());
        this.add(phoneLabel);

        this.add(new JLabel("شماره تماس"));

        JLabel addressLabel = new JLabel(user.getAddress());
        this.add(addressLabel);
        this.add(new JLabel("آدرس"));

        JButton logoutButton = new JButton("خروج");
        logoutButton.addActionListener(e -> PanelUtil.changePanel(frame, this, new SignInPanel(frame, dbConnection)));
        this.add(logoutButton);

        JButton editButton = new JButton("ویرایش");
//        editButton.addActionListener(e -> PanelUtil.changePanel(frame, this, new ProfileEditPanel(frame, dbConnection, user)));
        this.add(editButton);
    }
}
