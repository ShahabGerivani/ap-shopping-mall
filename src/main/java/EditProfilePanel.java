import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditProfilePanel extends JPanel {
    EditProfilePanel(JFrame frame, Connection dbConnection, User user) {
        super();
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        JLabel editLabel = new JLabel("ویرایش");
        editLabel.setFont(new Font("Arial", Font.PLAIN, 23));
        this.add(editLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 1;
        JTextField nameField = new JTextField(user.getName());
        nameField.setFont(new Font("Arial", Font.PLAIN, 20));
        nameField.setPreferredSize(new Dimension(140, 30));
        this.add(nameField, gbc);

        gbc.gridx = 3;
        JLabel nameLabel = new JLabel("نام");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(nameLabel, gbc);

        gbc.gridx = 0;
        JTextField phoneField = new JTextField(user.getPhone());
        phoneField.setPreferredSize(new Dimension(140, 30));
        phoneField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(phoneField, gbc);

        gbc.gridx = 1;
        JLabel phoneLabel = new JLabel("شماره تماس");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(phoneLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        JTextArea addressTextArea = new JTextArea(user.getAddress());
        addressTextArea.setLineWrap(true);
        addressTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        this.add(addressTextArea, gbc);

        gbc.gridx = 3;
        JLabel addressLabel = new JLabel("آدرس");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(addressLabel, gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton changePasswordButton = new JButton("تغییر رمز");
        changePasswordButton.addActionListener(e -> PanelUtil.changePanel(frame, this, new ChangePasswordPanel(frame, dbConnection, user)));
        changePasswordButton.setPreferredSize(new Dimension(110, 35));
        changePasswordButton.setFont(new Font("Arial", Font.PLAIN, 20));
        changePasswordButton.setFocusable(false);
        this.add(changePasswordButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JButton cancelButton = new JButton("لغو");
        cancelButton.addActionListener(e -> PanelUtil.changePanel(frame, this, new ProfilePanel(frame, dbConnection, user)));
        cancelButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 20));
        cancelButton.setFocusable(false);
        this.add(cancelButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        JButton submitButton = new JButton("ثبت");
        submitButton.setPreferredSize(new Dimension(100, 35));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        submitButton.setFocusable(false);
        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String address = addressTextArea.getText();

            if (!phone.matches("^(\\+98|0)?9\\d{9}$")) {
                JOptionPane.showMessageDialog(frame, "شماره وارد شده معتبر نمی باشد!");
            } else {
                try {
                    PreparedStatement updateUserStatement = dbConnection.prepareStatement("UPDATE users SET name = ?, phone = ?, address = ? WHERE username = ?");
                    updateUserStatement.setString(1, name);
                    updateUserStatement.setString(2, phone);
                    updateUserStatement.setString(3, address);
                    updateUserStatement.setString(4, user.getUsername());
                    updateUserStatement.executeUpdate();
                    UsersDBManager usersDBManager = new UsersDBManager(dbConnection);
                    PanelUtil.changePanel(frame, this, new ProfilePanel(frame, dbConnection, usersDBManager.getUser(user.getUsername())));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
                    ex.printStackTrace();
                }
            }
        });
        this.add(submitButton, gbc);
    }
}
