import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class ChangePasswordPanel extends AbstractEditPanel{
    ChangePasswordPanel(JFrame frame, Connection dbConnection, User user){
        //تعریف لیبل بالایی
        label = new JLabel("تغییر رمز عبور");
        label.setFont(new Font("Arial",Font.PLAIN,30));
        //تعریف لیبل های راستی
        fieldLabels = new JLabel[3];
        JLabel previousPasswordLabel = new JLabel("رمز قبلی");
        previousPasswordLabel.setFont(new Font("Arial",Font.PLAIN,25));
        fieldLabels[0]=previousPasswordLabel;
        JLabel newPasswordLabel = new JLabel("رمز جدید");
        newPasswordLabel.setFont(new Font("Arial",Font.PLAIN,25));
        fieldLabels[1] = newPasswordLabel;
        JLabel passwordRepeatLabel = new JLabel("تکرار رمز");
        passwordRepeatLabel.setFont(new Font("Arial",Font.PLAIN,25));
        fieldLabels[2] = passwordRepeatLabel;
        //تعریف تکس فیلد های چپی
        fields = new JComponent[3];
        JPasswordField previousPasswordField = new JPasswordField();
        previousPasswordField.setEchoChar('*');
        previousPasswordField.setPreferredSize(new Dimension(140,30));
        previousPasswordField.setFont(new Font("Arial",Font.PLAIN,18));
        fields[0] = previousPasswordField;
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setFont(new Font("Arial",Font.PLAIN,18));
        newPasswordField.setEchoChar('*');
        newPasswordField.setPreferredSize(new Dimension(140,30));
        fields[1] = newPasswordField;
        JPasswordField passwordRepeatField = new JPasswordField();
        passwordRepeatField.setFont(new Font("Arial",Font.PLAIN,18));
        passwordRepeatField.setEchoChar('*');
        passwordRepeatField.setPreferredSize(new Dimension(140,30));
        fields[2] = passwordRepeatField;
        //تعریف دکمه ها
        buttons = new JButton[2];
        JButton changePasswordButton = new JButton("تغییر رمز");
        changePasswordButton.setFocusable(false);
        changePasswordButton.setPreferredSize(new Dimension(150,35));
        changePasswordButton.setFont(new Font("Arial",Font.PLAIN,18));
        buttons[0] = changePasswordButton;
        JButton cancelButton = new JButton("لغو");
        cancelButton.addActionListener(e -> PanelUtil.changePanel(frame,this,new EditProfilePanel(frame, dbConnection, user)));
        cancelButton.setFocusable(false);
        cancelButton.setPreferredSize(new Dimension(150,35));
        cancelButton.setFont(new Font("Arial",Font.PLAIN,18));
        buttons[1]=cancelButton;
        construct(this);
    }




}
