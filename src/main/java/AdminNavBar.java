import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AdminNavBar extends JPanel {

    AdminNavBar(double sales ,String title){
        super();
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5,5,5,5);

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;

        JLabel salesAmountLabel = new JLabel(String.valueOf(sales));
        salesAmountLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(salesAmountLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(5,5,5,5);

        JLabel salesLabel = new JLabel("فروش :");
        salesLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(salesLabel, gbc);

        gbc.gridx = 2;

        gbc.insets = new Insets(5,80,5,80);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(titleLabel, gbc);

        gbc.gridx = 3 ;
        gbc.insets = new Insets(5,5,5,5);

        JButton mainPanelButton = new JButton("صفحه اصلی");
        mainPanelButton.setFocusable(false);
        mainPanelButton.setPreferredSize(new Dimension(100, 35));
        mainPanelButton.setFont(new Font("Arial", Font.PLAIN, 15));
        this.add(mainPanelButton, gbc);

        gbc.gridx = 4;

        JButton newProductButton = new JButton("محصول جدید");
        newProductButton.setFocusable(false);
        newProductButton.setPreferredSize(new Dimension(100, 35));
        newProductButton.setFont(new Font("Arial", Font.PLAIN, 15));
        this.add(newProductButton, gbc);

        gbc.gridx=5;

        JButton usersListButton = new JButton("لیست کاربران");
        usersListButton.setFocusable(false);
        usersListButton.setPreferredSize(new Dimension(105, 35));
        usersListButton.setFont(new Font("Arial", Font.PLAIN, 15));
        this.add(usersListButton, gbc);

        Border blackLine = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        this.setBorder(blackLine);
    }
}
