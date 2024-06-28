import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.Connection;

public class UserInfoCardPanel extends JPanel {
    UserInfoCardPanel(User user) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel phoneField = new JLabel(user.getPhone());
        phoneField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(phoneField, gbc);

        gbc.gridx = 1;

        JLabel phoneLabel = new JLabel("شماره:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(phoneLabel, gbc);

        gbc.gridx = 2;

        JLabel nameField = new JLabel(user.getName());
        nameField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(nameField, gbc);

        gbc.gridx = 3;

        JLabel nameLabel = new JLabel("نام:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(nameLabel, gbc);

        gbc.gridx = 4;

        JLabel userNameField = new JLabel(user.getUsername());
        userNameField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(userNameField, gbc);

        gbc.gridx = 5;

        JLabel userNameLabel = new JLabel("نام کاربری:");
        userNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(userNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth =5;

        JLabel addressField = new JLabel(user.getAddress(), SwingConstants.RIGHT);
        addressField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(addressField, gbc);

        gbc.gridx = 5;
        gbc.gridwidth=1;

        JLabel addressLabel = new JLabel("آدرس:", SwingConstants.RIGHT);
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(addressLabel, gbc);

        Border blackLine = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
        this.setBorder(blackLine);

    }
}
