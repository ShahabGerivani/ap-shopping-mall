import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordPanel extends AbstractEditPanel {
    ChangePasswordPanel(JFrame frame, Connection dbConnection, User user) {
        //تعریف لیبل بالایی
        label = new JLabel("تغییر رمز عبور");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        //تعریف لیبل های راستی
        fieldLabels = new JLabel[3];
        JLabel previousPasswordLabel = new JLabel("رمز قبلی");
        previousPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[0] = previousPasswordLabel;
        JLabel newPasswordLabel = new JLabel("رمز جدید");
        newPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[1] = newPasswordLabel;
        JLabel passwordRepeatLabel = new JLabel("تکرار رمز");
        passwordRepeatLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[2] = passwordRepeatLabel;
        //تعریف تکس فیلد های چپی
        fields = new JComponent[3];
        JPasswordField previousPasswordField = new JPasswordField();
        previousPasswordField.setEchoChar('*');
        previousPasswordField.setPreferredSize(new Dimension(140, 30));
        previousPasswordField.setFont(new Font("Arial", Font.PLAIN, 18));
        fields[0] = previousPasswordField;
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setFont(new Font("Arial", Font.PLAIN, 18));
        newPasswordField.setEchoChar('*');
        newPasswordField.setPreferredSize(new Dimension(140, 30));
        fields[1] = newPasswordField;
        JPasswordField passwordRepeatField = new JPasswordField();
        passwordRepeatField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordRepeatField.setEchoChar('*');
        passwordRepeatField.setPreferredSize(new Dimension(140, 30));
        fields[2] = passwordRepeatField;
        //تعریف دکمه ها
        buttons = new JButton[2];
        JButton cancelButton = new JButton("لغو");
        cancelButton.addActionListener(e -> PanelUtil.changePanel(frame, this, new EditProfilePanel(frame, dbConnection, user)));
        cancelButton.setFocusable(false);
        cancelButton.setPreferredSize(new Dimension(150, 35));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 18));
        buttons[0] = cancelButton;
        JButton changePasswordButton = new JButton("تغییر رمز");
        changePasswordButton.setFocusable(false);
        changePasswordButton.setPreferredSize(new Dimension(150, 35));
        changePasswordButton.setFont(new Font("Arial", Font.PLAIN, 18));
        changePasswordButton.addActionListener(e -> {
            String previousPassword = String.valueOf(previousPasswordField.getPassword());
            String newPassword = String.valueOf(newPasswordField.getPassword());
            String passwordRepeat = String.valueOf(passwordRepeatField.getPassword());

            try {
                // Validating fields
                PreparedStatement previousPasswordCheckStatement = dbConnection.prepareStatement("SELECT password, password_salt FROM users WHERE username = ?");
                previousPasswordCheckStatement.setString(1, user.getUsername());
                ResultSet previousPasswordCheckRs = previousPasswordCheckStatement.executeQuery();
                if (!previousPasswordCheckRs.next()) {
                    JOptionPane.showMessageDialog(frame, "کاربر یافت نشد.");
                } else if (!AuthUtil.checkPassword(previousPassword, previousPasswordCheckRs.getString("password"), previousPasswordCheckRs.getString("password_salt"))) {
                    JOptionPane.showMessageDialog(frame, "رمز اشتباه است!");
                } else if (previousPassword.isBlank() || newPassword.isBlank() || passwordRepeat.isBlank()) {
                    JOptionPane.showMessageDialog(frame, "لطفا همه فیلد ها را پر کنید.");
                } else if (!newPassword.equals(passwordRepeat)) {
                    JOptionPane.showMessageDialog(frame, "تکرار رمز اشتباه است.");
                } else if (!newPassword.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                    JOptionPane.showMessageDialog(frame, "طول رمز عبور باید بیشتر از 8 کاراکتر باشد و رمز عبور باید دارای حداقل یک حرف و یک عدد باشد.");
                } else if (newPassword.equals(previousPassword)) {
                    JOptionPane.showMessageDialog(frame, "رمز جدید باید با رمز قبلی متفاوت باشد.");

                } else {
                    // Hashing the password
                    String[] hashAndSalt = AuthUtil.hashPassword(newPassword);

                    // Updating the password in the database
                    PreparedStatement insertNewUser = dbConnection.prepareStatement("UPDATE users SET password = ?, password_salt = ? WHERE username = ?");
                    insertNewUser.setString(1, hashAndSalt[0]);
                    insertNewUser.setString(2, hashAndSalt[1]);
                    insertNewUser.setString(3, user.getUsername());
                    insertNewUser.executeUpdate();

                    // Going back to the profile panel
                    PanelUtil.changePanel(frame, this, new ProfilePanel(frame, dbConnection, user));
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
                ex.printStackTrace();
            }
        });
        buttons[1] = changePasswordButton;

        construct(this);
    }


}
