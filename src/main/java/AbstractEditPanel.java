import javax.swing.*;
import java.awt.*;

public abstract class AbstractEditPanel extends JPanel {
    protected JLabel label;
    protected JLabel[] fieldLabels;
    protected JComponent[] fields;
    protected JButton[] buttons;

    protected static void construct(AbstractEditPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1;
        gbc.weighty = 1;

        int sizeOfFieldLabels = panel.fieldLabels.length;
        int numbersOfButton = panel.buttons.length;

        //لیبل بالایی قرار داده می شود
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(panel.label, gbc);
        gbc.gridwidth = 1;

        // لیبل های سمت راست قرار داده می شود
        for (int i = 1; i <= sizeOfFieldLabels; i++) {
            gbc.gridx = 1;
            gbc.gridy = i;
            panel.add(panel.fieldLabels[i - 1], gbc);
        }
        // فیلد های سمت چپ قرار داده می شود
        for (int i = 1; i <= sizeOfFieldLabels; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(panel.fields[i - 1], gbc);
        }
        // بررسی تعداد دکمه ها و دکمه های پایین قرار داده می شود
        if (numbersOfButton == 1) {
            gbc.gridwidth = 2;
        }
        for (int j = 0; j < numbersOfButton; j++) {
            gbc.gridx = j;
            gbc.gridy = sizeOfFieldLabels + 1;
            panel.add(panel.buttons[j], gbc);
        }
    }
}
