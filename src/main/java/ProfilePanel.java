import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class ProfilePanel extends JPanel {
    ProfilePanel(JFrame frame, Connection dbConnection, User user) {
        super();
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth=4;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        gbc.gridx = 0;

        UserNavBar navBar = new UserNavBar(user.getBalance(),  "خوش آمدید " + user.getUsername(),frame,dbConnection,user,this);
        this.add(navBar,gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth=1;
        gbc.gridx=0;
        gbc.gridy=1;

        JLabel nameField = new JLabel(user.getName());
        nameField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(nameField,gbc);

        gbc.gridx=1;
        gbc.gridy=1;

        JLabel nameLabel =new JLabel("نام");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(nameLabel,gbc);

        gbc.gridx=2;
        gbc.gridy=1;

        JLabel usernameField = new JLabel(user.getUsername());
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(usernameField,gbc);

        gbc.gridx =3;
        gbc.gridy=1;

        JLabel usernameLabel = new JLabel("نام کاربری");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(usernameLabel,gbc);

        gbc.gridx=0;
        gbc.gridy=2;

        JTextArea addressTextArea = new JTextArea(user.getAddress());
        addressTextArea.setLineWrap(true);
        addressTextArea.setOpaque(false);
        addressTextArea.setEditable(false);
        addressTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        this.add(addressTextArea,gbc);

        gbc.gridx=1;
        gbc.gridy=2;
        JLabel addressLabel = new JLabel("آدرس");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(addressLabel,gbc);

        gbc.gridx=2;

        JLabel phoneField = new JLabel(user.getPhone());
        phoneField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(phoneField,gbc);

        gbc.gridx=3;

        JLabel phoneLabel = new JLabel("شماره تماس");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(phoneLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=3;

        JButton logoutButton = new JButton("خروج");
        logoutButton.addActionListener(e -> PanelUtil.changePanel(frame, this, new SignInPanel(frame, dbConnection)));
        logoutButton.setFocusable(false);
        logoutButton.setPreferredSize(new Dimension(100,35));
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(logoutButton,gbc);

        gbc.gridx=2;
        JButton editButton = new JButton("ویرایش");
        editButton.addActionListener(e -> PanelUtil.changePanel(frame, this, new EditProfilePanel(frame, dbConnection, user)));
        editButton.setFocusable(false);
        editButton.setPreferredSize(new Dimension(100,35));
        editButton.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(editButton,gbc);
    }
}
