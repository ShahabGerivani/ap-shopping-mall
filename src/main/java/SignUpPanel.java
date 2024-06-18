import javax.swing.*;
import java.awt.*;

public class SignUpPanel extends AbstractEditPanel {
    SignUpPanel(){
        //تعریف لیبل بالایی
        label = new JLabel("ثبت نام");
        label.setFont(new Font("Arial",Font.PLAIN,30));
        //تعریف لیبل های راستی
        fieldLabels = new JLabel[3];
        JLabel usernameLabel = new JLabel("نام کاربری");
        usernameLabel.setFont(new Font("Arial",Font.PLAIN,25));
        fieldLabels[0]=usernameLabel;
        JLabel passwordLabel = new JLabel("رمز");
        passwordLabel.setFont(new Font("Arial",Font.PLAIN,25));
        fieldLabels[1] = passwordLabel;
        JLabel passwordRepeatLabel = new JLabel("تکرار رمز");
        passwordRepeatLabel.setFont(new Font("Arial",Font.PLAIN,25));
        fieldLabels[2] = passwordRepeatLabel;
        //تعریف تکس فیلد های چپی
        fields = new JComponent[3];
        JTextField usernameTextField = new JTextField();
        usernameTextField.setPreferredSize(new Dimension(140,30));
        usernameTextField.setFont(new Font("Arial",Font.PLAIN,18));
        fields[0] = usernameTextField;
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial",Font.PLAIN,18));
        passwordField.setEchoChar('*');
        passwordField.setPreferredSize(new Dimension(140,30));
        fields[1] = passwordField;
        JPasswordField passwordRepeatField = new JPasswordField();
        passwordRepeatField.setFont(new Font("Arial",Font.PLAIN,18));
        passwordRepeatField.setEchoChar('*');
        passwordRepeatField.setPreferredSize(new Dimension(140,30));
        fields[2] = passwordRepeatField;

        //تعریف دکمه ها
        buttons = new JButton[2];

        JButton signUpButton = new JButton("ثبت نام");
        signUpButton.setFocusable(false);
        signUpButton.setPreferredSize(new Dimension(150,35));
        signUpButton.setFont(new Font("Arial",Font.PLAIN,18));
        buttons[0] =signUpButton;
        JButton signInButton = new JButton("ورود");
        signInButton.setFocusable(false);
        signInButton.setPreferredSize(new Dimension(150,35));
        signInButton.setFont(new Font("Arial",Font.PLAIN,18));
        buttons[1] = signInButton;
        construct(this);
    }
}
