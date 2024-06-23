import javax.swing.*;
import java.awt.*;

public class IncreaseBalancePanel extends AbstractEditPanel {
    IncreaseBalancePanel() {
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
        buttons = new JButton[1];
        JButton payButton = new JButton("پرداخت");
        payButton.setFocusable(false);
        payButton.setPreferredSize(new Dimension(150, 35));
        payButton.setFont(new Font("Arial", Font.PLAIN, 18));
        buttons[0] = payButton;

        construct(this);

    }
}
