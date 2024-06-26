import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpPanel extends AbstractEditPanel {
    SignUpPanel(JFrame frame, Connection dbConnection) {
        //تعریف لیبل بالایی
        label = new JLabel("ثبت نام");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        //تعریف لیبل های راستی
        fieldLabels = new JLabel[3];
        JLabel usernameLabel = new JLabel("نام کاربری");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[0] = usernameLabel;
        JLabel passwordLabel = new JLabel("رمز");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[1] = passwordLabel;
        JLabel passwordRepeatLabel = new JLabel("تکرار رمز");
        passwordRepeatLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[2] = passwordRepeatLabel;
        //تعریف تکس فیلد های چپی
        fields = new JComponent[3];
        JTextField usernameTextField = new JTextField();
        usernameTextField.setPreferredSize(new Dimension(140, 30));
        usernameTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        fields[0] = usernameTextField;
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setEchoChar('*');
        passwordField.setPreferredSize(new Dimension(140, 30));
        fields[1] = passwordField;
        JPasswordField passwordRepeatField = new JPasswordField();
        passwordRepeatField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordRepeatField.setEchoChar('*');
        passwordRepeatField.setPreferredSize(new Dimension(140, 30));
        fields[2] = passwordRepeatField;

        //تعریف دکمه ها
        buttons = new JButton[2];

        JButton signUpButton = new JButton("ثبت نام");
        signUpButton.setFocusable(false);
        signUpButton.setPreferredSize(new Dimension(150, 35));
        signUpButton.setFont(new Font("Arial", Font.PLAIN, 18));
        // Sign up functionality
        signUpButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String passwordRepeat = String.valueOf(passwordRepeatField.getPassword());

            // Validating inputs
            if (username.isBlank() || password.isBlank() || passwordRepeat.isBlank()) {
                JOptionPane.showMessageDialog(frame, "لطفا همه فیلد ها را پر کنید.");
            } else if (!password.equals(passwordRepeat)) {
                JOptionPane.showMessageDialog(frame, "تکرار رمز اشتباه است.");
            } else if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                JOptionPane.showMessageDialog(frame, "طول رمز عبور باید بیشتر از 8 کاراکتر باشد و رمز عبور باید دارای حداقل یک حرف و یک عدد باشد.");

            } else {
                try {
                    // Checking if username exists
                    PreparedStatement usernameExists = dbConnection.prepareStatement("SELECT username FROM users WHERE username = ?");
                    usernameExists.setString(1, username);
                    ResultSet usernameExistsRS = usernameExists.executeQuery();
                    if (usernameExistsRS.next()) {
                        JOptionPane.showMessageDialog(frame, "کاربر با این نام کاربری وجود دارد.");

                    } else {
                        // Hashing the password
                        String[] hashAndSalt = AuthUtil.hashPassword(password);

                        // Inserting the new user into the database
                        PreparedStatement insertNewUser = dbConnection.prepareStatement("INSERT INTO users (username, password, password_salt) VALUES (?, ?, ?)");
                        insertNewUser.setString(1, username);
                        insertNewUser.setString(2, hashAndSalt[0]);
                        insertNewUser.setString(3, hashAndSalt[1]);
                        insertNewUser.executeUpdate();

                        User user = new User(username, null, null, null, 0, false);

                         PanelUtil.changePanel(frame, this, new ProfilePanel(frame, dbConnection, user));
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
                    ex.printStackTrace();
                }
            }
        });
        buttons[0] = signUpButton;

        JButton signInButton = new JButton("ورود");
        signInButton.setFocusable(false);
        signInButton.setPreferredSize(new Dimension(150, 35));
        signInButton.setFont(new Font("Arial", Font.PLAIN, 18));
        signInButton.addActionListener((e) -> PanelUtil.changePanel(frame, this, new SignInPanel(frame, dbConnection)));
        buttons[1] = signInButton;

        construct(this);
    }
}
