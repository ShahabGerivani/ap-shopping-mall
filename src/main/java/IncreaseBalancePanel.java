import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class IncreaseBalancePanel extends AbstractEditPanel {
    IncreaseBalancePanel(JFrame frame, Connection dbConnection, User user) {
        //تعریف لیبل بالایی
        label = new JLabel("افزایش اعتبار");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        //تعریف لیبل راستی
        fieldLabels = new JLabel[1];
        JLabel increaseBalanceLabel = new JLabel("مبلغ");
        increaseBalanceLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[0] = increaseBalanceLabel;
        // تعریف تکس فیلد چپی
        fields = new JComponent[1];
        JTextField increaseBalanceTextField = new JTextField();
        increaseBalanceTextField.setPreferredSize(new Dimension(140, 40));
        increaseBalanceTextField.setFont(new Font("Arial", Font.PLAIN, 25));
        fields[0] = increaseBalanceTextField;
        // تعریف دکمه
        buttons = new JButton[2];
        JButton cancelButton = new JButton("لغو");
        cancelButton.setFocusable(false);
        cancelButton.setPreferredSize(new Dimension(150, 35));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 18));
        cancelButton.addActionListener(e -> PanelUtil.changePanel(frame,this,new ProfilePanel(frame,dbConnection,user)));
        buttons[0] = cancelButton;
        JButton payButton = new JButton("پرداخت");
        payButton.setFocusable(false);
        payButton.setPreferredSize(new Dimension(150, 35));
        payButton.setFont(new Font("Arial", Font.PLAIN, 18));
        payButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(increaseBalanceTextField.getText());
                UsersDBManager usersDBManager = new UsersDBManager(dbConnection);
                usersDBManager.setBalanceForUser(user.getUsername(), user.getBalance() + amount);
                PanelUtil.changePanel(frame, this, new ProfilePanel(frame, dbConnection, usersDBManager.getUser(user.getUsername())));
            } catch (NullPointerException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "لطفا یک عدد برای مبلغ وارد کنید.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
                ex.printStackTrace();
            }
        });
        buttons[1] =payButton;
        construct(this);

    }
}
