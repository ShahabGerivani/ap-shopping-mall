import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminNavBar extends JPanel {
    AdminNavBar(String title, JFrame frame, Connection dbConnection, User admin, JPanel panel) {
        super();

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;

        JLabel salesAmountLabel = new JLabel();
        salesAmountLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(salesAmountLabel, gbc);

        // Calculating total sales and putting it in salesAmountLabel
        double totalSales = 0;
        try {
            Statement calculateTotalSalesStmt = dbConnection.createStatement();
            ResultSet calculateTotalSalesRs = calculateTotalSalesStmt.executeQuery("SELECT finalized_total FROM carts WHERE finalized = 1");
            while (calculateTotalSalesRs.next()) {
                totalSales += calculateTotalSalesRs.getDouble("finalized_total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
            PanelUtil.changePanel(frame, panel, new SignInPanel(frame, dbConnection));
            e.printStackTrace();
            return;
        }
        salesAmountLabel.setText(String.format("%.2g%n", totalSales));

        gbc.gridx = 1;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel salesLabel = new JLabel("فروش :");
        salesLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(salesLabel, gbc);

        gbc.gridx = 2;

        gbc.insets = new Insets(5, 80, 5, 80);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(titleLabel, gbc);

        gbc.gridx = 3;
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton mainPanelButton = new JButton("صفحه اصلی");
        mainPanelButton.setFocusable(false);
        mainPanelButton.setPreferredSize(new Dimension(100, 35));
        mainPanelButton.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanelButton.addActionListener(e -> PanelUtil.changePanel(frame, panel, new UserMainPanel(frame, dbConnection, admin, UserMainPanel.SORT_DEFAULT, "")));
        this.add(mainPanelButton, gbc);

        gbc.gridx = 4;

        JButton newProductButton = new JButton("محصول جدید");
        newProductButton.setFocusable(false);
        newProductButton.setPreferredSize(new Dimension(100, 35));
        newProductButton.setFont(new Font("Arial", Font.PLAIN, 15));
        newProductButton.addActionListener(e -> PanelUtil.changePanel(frame, panel, new NewProductPanel(frame, dbConnection, admin)));
        this.add(newProductButton, gbc);

        gbc.gridx = 5;

        JButton usersListButton = new JButton("لیست کاربران");
        usersListButton.setFocusable(false);
        usersListButton.setPreferredSize(new Dimension(105, 35));
        usersListButton.setFont(new Font("Arial", Font.PLAIN, 15));
        usersListButton.addActionListener(e -> PanelUtil.changePanel(frame, panel, new UsersListPanel(frame, dbConnection, admin)));
        this.add(usersListButton, gbc);

        Border blackLine = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        this.setBorder(blackLine);
    }
}
