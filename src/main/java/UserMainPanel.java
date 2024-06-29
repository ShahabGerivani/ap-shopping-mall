import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserMainPanel extends JPanel {

    public static final int SORT_DEFAULT = 0;
    public static final int SORT_BY_PRICE_ASC = 1;
    public static final int SORT_BY_PRICE_DESC = 2;
    public static final int SORT_BY_RATING_ASC = 3;
    public static final int SORT_BY_RATING_DESC = 4;

    UserMainPanel(JFrame frame, Connection dbConnection, User user, int sortBy, String searchTerm) {
        // Preparing the products' list
        ProductsDBManager productsDBManager = new ProductsDBManager(dbConnection);
        ArrayList<Product> products;
        try {
            products = productsDBManager.getAllProducts(!user.isAdmin());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
            PanelUtil.changePanel(frame, this, new ProfilePanel(frame, dbConnection, user));
            e.printStackTrace();
            return;
        }
        switch (sortBy) {
            case SORT_BY_PRICE_ASC -> products.sort(Comparator.comparing(Product::getPrice));
            case SORT_BY_PRICE_DESC -> {
                products.sort(Comparator.comparing(Product::getPrice));
                Collections.reverse(products);
            }
            case SORT_BY_RATING_ASC -> products.sort(Comparator.comparing(Product::getRating));
            case SORT_BY_RATING_DESC -> {
                products.sort(Comparator.comparing(Product::getRating));
                Collections.reverse(products);
            }
            default -> products.sort(Comparator.comparing(Product::getId));
        }
        for (int i = 0; i < products.size(); i++) {
            if (!products.get(i).getName().contains(searchTerm.trim())) {
                products.remove(i);
                i--;
            }
        }

        this.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JPanel UpperPanel = new JPanel();
        JPanel lowerPanel = new JPanel();

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        if (!user.isAdmin()) {
            UserNavBar userNavBar = new UserNavBar(user.getBalance(), "خوش آمدید " + user.getUsername(), frame, dbConnection, user, this);
            UpperPanel.add(userNavBar);
            this.add(userNavBar, gridBagConstraints);
        } else {
            AdminNavBar adminNavBar = new AdminNavBar("انبار", frame, dbConnection, user, this);
            UpperPanel.add(adminNavBar);
            this.add(adminNavBar, gridBagConstraints);
        }

        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 3, 3, 3);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField searchTextField = new JTextField(searchTerm);
        searchTextField.setPreferredSize(new Dimension(140, 30));
        searchTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        lowerPanel.add(searchTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton searchButton = new JButton("جستجو");
        searchButton.setFocusable(false);
        searchButton.setPreferredSize(new Dimension(150, 35));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 20));
        searchButton.addActionListener(e -> PanelUtil.changePanel(frame, this, new UserMainPanel(frame, dbConnection, user, sortBy, searchTextField.getText())));
        lowerPanel.add(searchButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;

        String[] sortOptions = {"پیش فرض", "قیمت صعودی", "قیمت نزولی", "امتیاز صعودی", "امتیاز نزولی"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setSelectedIndex(sortBy);
        sortComboBox.setPreferredSize(new Dimension(140, 30));
        sortComboBox.setFont(new Font("Arial", Font.PLAIN, 20));
        sortComboBox.addActionListener(e -> PanelUtil.changePanel(frame, this, new UserMainPanel(frame, dbConnection, user, sortComboBox.getSelectedIndex(), searchTerm)));
        lowerPanel.add(sortComboBox, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;

        JLabel sortLabel = new JLabel("مرتب سازی");
        sortLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        lowerPanel.add(sortLabel, gbc);

        gbc.insets = new Insets(20, 6, 20, 6);
        Border blackLine = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);

        ProductCardPanel productCardPanel;
        for (int i = 0; i < products.size(); i++) {
            productCardPanel = new ProductCardPanel(frame, dbConnection, user, products.get(i), this);
            if (i % 2 == 0) {
                gbc.gridx = 0;
            } else {
                gbc.gridx = i % 2 + 1;
            }
            gbc.gridy = i / 2 + 1;
            gbc.gridwidth = 2;
            productCardPanel.setBorder(blackLine);
            lowerPanel.add(productCardPanel, gbc);
        }

        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;

        JScrollPane scrollPanel = new JScrollPane(lowerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPanel, gridBagConstraints);
    }
}
