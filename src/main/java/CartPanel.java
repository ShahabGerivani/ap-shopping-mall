import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class CartPanel extends JPanel {
    CartPanel(JFrame frame, Connection dbConnection, User user) {
        CartsDBManager cartsDBManager = new CartsDBManager(dbConnection);
        Cart cart;
        try {
            cart = cartsDBManager.getOrCreateCartForUser(user.getUsername());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
            PanelUtil.changePanel(frame, this, new ProfilePanel(frame, dbConnection, user));
            e.printStackTrace();
            return;
        }

        this.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JPanel UpperPanel = new JPanel();
        JPanel lowerPanel = new JPanel();

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        UserNavBar userNavBar = new UserNavBar(user.getBalance(), "خوش آمدید " + user.getUsername(), frame, dbConnection, user, this);
        UpperPanel.add(userNavBar);
        this.add(userNavBar, gridBagConstraints);

        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 3, 3, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridwidth = 2;
        int i = 0;
        for (Product product : cart.getProductsAndCount().keySet()) {
            gbc.gridy = i++;
            lowerPanel.add(new CartProductCardPanel(frame, dbConnection, user, product, this, cart, cartsDBManager), gbc);
        }
        gbc.insets = new Insets(5, 3, 10, 3);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = cart.getProductsAndCount().size();
        JButton emptyCartButton = new JButton("خالی کردن سبد");
        emptyCartButton.setFont(new Font("Arial", Font.PLAIN, 20));
        emptyCartButton.setPreferredSize(new Dimension(200, 35));
        emptyCartButton.setFocusable(false);
        emptyCartButton.addActionListener(e -> {
            try {
                for (Product product : cart.getProductsAndCount().keySet()) {
                    cartsDBManager.removeProductFromCart(cart.getId(), product.getId());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
                ex.printStackTrace();
            }
            PanelUtil.changePanel(frame, this, new CartPanel(frame, dbConnection, user));
        });
        lowerPanel.add(emptyCartButton, gbc);

        gbc.gridx = 1;
        JButton buyButton = new JButton("نهایی کردن سبد");
        buyButton.setFont(new Font("Arial", Font.PLAIN, 20));
        buyButton.setPreferredSize(new Dimension(100, 35));
        buyButton.setFocusable(false);
        buyButton.addActionListener(e -> {
            PanelUtil.changePanel(frame, this, new FinalizeCartPanel(frame, dbConnection, user, cart));
        });
        lowerPanel.add(buyButton, gbc);

        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        JScrollPane scrollPanel = new JScrollPane(lowerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPanel, gridBagConstraints);
    }
}
