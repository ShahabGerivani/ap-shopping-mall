import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInPanel extends AbstractEditPanel {
    SignInPanel(JFrame frame, Connection dbConnection) {
        //تعریف لیبل بالایی
        label = new JLabel("ورود");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        //تعریف لیبل های راستی
        fieldLabels = new JLabel[2];
        JLabel usernameLabel = new JLabel("نام کاربری");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[0] = usernameLabel;
        JLabel passwordLabel = new JLabel("رمز");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[1] = passwordLabel;
        //تعریف تکس فیلد های چپی
        fields = new JComponent[2];
        JTextField usernameTextField = new JTextField();
        usernameTextField.setPreferredSize(new Dimension(140, 30));
        usernameTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        fields[0] = usernameTextField;
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setEchoChar('*');
        fields[1] = passwordField;
        passwordField.setPreferredSize(new Dimension(140, 30));
        //تعریف دکمه ها
        buttons = new JButton[2];

        JButton signUpButton = new JButton("ثبت نام");
        signUpButton.setFocusable(false);
        signUpButton.setPreferredSize(new Dimension(150, 35));
        signUpButton.setFont(new Font("Arial", Font.PLAIN, 18));
        signUpButton.addActionListener((e -> PanelUtil.changePanel(frame, this, new SignUpPanel(frame, dbConnection))));
        buttons[0] = signUpButton;
        JButton signInButton = new JButton("ورود");
        signInButton.setFocusable(false);
        signInButton.setPreferredSize(new Dimension(150, 35));
        signInButton.setFont(new Font("Arial", Font.PLAIN, 18));
        // Sign in functionality
        signInButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = String.valueOf(passwordField.getPassword());

            // Validating inputs
            if (username.isBlank() || password.isBlank()) {
                JOptionPane.showMessageDialog(frame, "لطفا همه فیلد ها را پر کنید.");

            } else {
                try {
                    PreparedStatement getUserStmt = dbConnection.prepareStatement("SELECT * FROM users WHERE username = ?");
                    getUserStmt.setString(1, username);
                    ResultSet userRs = getUserStmt.executeQuery();
                    if (!userRs.next()) {
                        JOptionPane.showMessageDialog(frame, "کاربر با این نام کاربری یافت نشد.");
                    } else {
                        if (!AuthUtil.checkPassword(password, userRs.getString("password"), userRs.getString("password_salt"))) {
                            JOptionPane.showMessageDialog(frame, "رمز اشتباه است!");
                        } else {
                            User user = new User(
                                    userRs.getString("username"),
                                    userRs.getString("name"),
                                    userRs.getString("phone"),
                                    userRs.getString("address"),
                                    userRs.getDouble("balance"),
                                    userRs.getBoolean("is_admin")
                            );

                            if (user.isAdmin()) {
//                                PanelUtil.changePanel(frame, this, MainPanel);
                            } else {
//                                PanelUtil.changePanel(frame, this, ProfilePanel);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
                    ex.printStackTrace();
                }
            }
        });

        buttons[1] = signInButton;
        construct(this);
    }

}
