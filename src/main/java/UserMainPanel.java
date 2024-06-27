import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMainPanel extends JPanel {
    UserMainPanel(JFrame frame, Connection dbConnection, User user) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JPanel UpperPanel = new JPanel();
        JPanel lowerPanel = new JPanel();

        UserNavBar userNavBar = new UserNavBar(user.getBalance(), "خوش آمدید " + user.getUsername(), frame, dbConnection, user, this);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;


        UpperPanel.add(userNavBar);
        this.add(userNavBar, gridBagConstraints);

        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5,3,3,3);

        JButton searchButton = new JButton("جستجو");
        searchButton.setFocusable(false);
        searchButton.setPreferredSize(new Dimension(150, 35));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 20));
        lowerPanel.add(searchButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;

        JTextField searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(140, 30));
        searchTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        lowerPanel.add(searchTextField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;

        String[] sortOptions = {"قیمت صعودی","قیمت نزولی","امتیاز صعودی","امتیاز نزولی"};
        JComboBox sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setPreferredSize(new Dimension(140, 30));
        sortComboBox.setFont(new Font("Arial", Font.PLAIN, 20));
        lowerPanel.add(sortComboBox, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;

        JLabel sortLabel = new JLabel("مرتب سازی");
        sortLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        lowerPanel.add(sortLabel, gbc);

        gbc.insets=new Insets(20,6,20,6);
        Border blackLine = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);

        ProductsDBManager productsDBManager = new ProductsDBManager(dbConnection);
        try {
            ArrayList<Product> products = productsDBManager.getAllProducts();
            ProductCardPanel productCardPanel;
            for (int i = 0; i < products.size(); i++) {
                productCardPanel = new ProductCardPanel(frame, dbConnection, user, products.get(i), this);
                if (i % 2 == 0) {
                    gbc.gridx = i % 2;
                    gbc.gridy = i / 2 + 1;
                } else {
                    gbc.gridx = i % 2 + 1;
                    gbc.gridy = i / 2 + 1;
                }
                gbc.gridwidth = 2;
                productCardPanel.setBorder(blackLine);
                lowerPanel.add(productCardPanel, gbc);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
            PanelUtil.changePanel(frame, this, new ProfilePanel(frame, dbConnection, user));
            e.printStackTrace();
        }

        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;

        JScrollPane scrollPanel = new JScrollPane(lowerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPanel, gridBagConstraints);
    }
}
