import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.sql.Connection;

public class FinalizeCartPanel extends JPanel {
    FinalizeCartPanel(JFrame frame, Connection dbConnection, User user, Cart cart) {
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(800,600));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx=1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border blackLine = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);

        JLabel productTotalPriceLabel = new JLabel("قیمت کل", SwingConstants.RIGHT);
        productTotalPriceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        productTotalPriceLabel.setBorder(new CompoundBorder(blackLine,padding));
        panel.add(productTotalPriceLabel, gbc);

        gbc.gridx = 1;

        JLabel productCountLabel = new JLabel("تعداد",SwingConstants.RIGHT);
        productCountLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        productCountLabel.setBorder(new CompoundBorder(blackLine,padding));
        panel.add(productCountLabel, gbc);

        gbc.gridx = 2;

        JLabel productPriceLabel = new JLabel("قیمت",SwingConstants.RIGHT);
        productPriceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        productPriceLabel.setBorder(new CompoundBorder(blackLine,padding));
        panel.add(productPriceLabel, gbc);

        gbc.gridx = 3;

        JLabel productNameLabel = new JLabel("نام",SwingConstants.RIGHT);
        productNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        productNameLabel.setBorder(new CompoundBorder(blackLine,padding));
        panel.add(productNameLabel, gbc);

        int row = 1;

        for (Product product : cart.getProductsAndCount().keySet()) {
            gbc.gridy = row;
            gbc.gridx = 0;

            JLabel productTotalPriceField = new JLabel(String.valueOf(cart.getProductsAndCount().get(product) * product.getPrice()),SwingConstants.RIGHT);
            productTotalPriceField.setFont(new Font("Arial", Font.PLAIN, 20));
            productTotalPriceField.setBorder(new CompoundBorder(blackLine,padding));
            panel.add(productTotalPriceField, gbc);

            gbc.gridx = 1;

            JLabel productCountField = new JLabel(String.valueOf(cart.getProductsAndCount().get(product)),SwingConstants.RIGHT);
            productCountField.setFont(new Font("Arial", Font.PLAIN, 20));
            productCountField.setBorder(new CompoundBorder(blackLine,padding));
            panel.add(productCountField, gbc);

            gbc.gridx = 2;

            JLabel productPriceField = new JLabel(String.valueOf(product.getPrice()),SwingConstants.RIGHT);
            productPriceField.setFont(new Font("Arial", Font.PLAIN, 20));
            productPriceField.setBorder(new CompoundBorder(blackLine,padding));
            panel.add(productPriceField, gbc);

            gbc.gridx = 3;

            JLabel productNameField = new JLabel(product.getName(),SwingConstants.RIGHT);
            productNameField.setFont(new Font("Arial", Font.PLAIN, 20));
            productNameField.setBorder(new CompoundBorder(blackLine,padding));
            panel.add(productNameField,gbc);

            row++;
        }
        gbc.gridx = 0;
        gbc.gridy = row;

        JLabel cartTotalPriceField = new JLabel(String.valueOf(cart.getTotal()),SwingConstants.RIGHT);
        cartTotalPriceField.setFont(new Font("Arial", Font.PLAIN, 20));
        cartTotalPriceField.setBorder(new CompoundBorder(blackLine,padding));
        panel.add(cartTotalPriceField,gbc);

        gbc.gridx=1;

        JLabel cartTotalPriceLabel = new JLabel("جمع",SwingConstants.RIGHT);
        cartTotalPriceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        cartTotalPriceLabel.setBorder(new CompoundBorder(blackLine,padding));
        panel.add(cartTotalPriceLabel,gbc);

        gbc.gridx=0;
        gbc.gridy = ++row;
        gbc.gridwidth = 2;

        JButton cancelButton = new JButton("لغو");
        cancelButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 20));
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(e -> PanelUtil.changePanel(frame,this,new CartPanel(frame,dbConnection,user)));
        panel.add(cancelButton,gbc);

        gbc.gridx = 2;

        JButton payButton = new JButton("پرداخت");
        payButton.setPreferredSize(new Dimension(100, 35));
        payButton.setFont(new Font("Arial", Font.PLAIN, 20));
        payButton.setFocusable(false);
        panel.add(payButton,gbc);

        JScrollPane scrollPanel = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setLayout(new GridLayout(1,1));
        this.add(scrollPanel);
    }
}
