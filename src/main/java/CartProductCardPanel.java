import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CartProductCardPanel extends JPanel {
    CartProductCardPanel(JFrame frame, Connection dbConnection, User user, Product product, JPanel panel, Cart cart, CartsDBManager cartsDBManager) {
        JLabel imageLabel = new JLabel();
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 3, 3, 3);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel countLabel = new JLabel("تعداد:");
        countLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(countLabel, gbc);

        gbc.gridx = 1;

        JLabel productNameField = new JLabel(product.getName());
        productNameField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productNameField, gbc);

        gbc.gridx = 2;

        JLabel productNameLabel = new JLabel("نام");
        productNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productNameLabel, gbc);

        gbc.gridx = 3;
        gbc.gridheight = 3;

        try {
            if (product.getImageFile() != null) {
                BufferedImage bufferedImage = ImageIO.read(product.getImageFile());
                ImageIcon imageIcon = new ImageIcon(bufferedImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
                imageLabel.setIcon(imageIcon);
            } else {
                imageLabel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
                imageLabel.setFont((new Font("Arial", Font.PLAIN, 20)));
                imageLabel.setText("عکس موجود نیست");
            }
        } catch (IOException e) {
            imageLabel.setFont((new Font("Arial", Font.PLAIN, 20)));
            imageLabel.setText("خطا در نمایش عکس");
            e.printStackTrace();
        }
        this.add(imageLabel, gbc);

        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;

        JSpinner productCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, product.getStock(), 1));
        productCountSpinner.setPreferredSize(new Dimension(140, 30));
        productCountSpinner.setFont(new Font("Arial", Font.PLAIN, 18));
        productCountSpinner.setValue(cart.getProductsAndCount().get(product));
        productCountSpinner.addChangeListener(e -> {
            try {
                cartsDBManager.setProductCountInCart(cart.getId(), product.getId(), (Integer) productCountSpinner.getValue());
                PanelUtil.changePanel(frame, panel, new CartPanel(frame, dbConnection, user));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
                ex.printStackTrace();
            }
        });
        this.add(productCountSpinner, gbc);

        gbc.gridx = 1;

        JLabel priceField = new JLabel(String.valueOf(product.getPrice()));
        priceField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(priceField, gbc);

        gbc.gridx = 2;

        JLabel priceLabel = new JLabel("قیمت: ");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(priceLabel, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;

        JButton deleteButton = new JButton("حذف");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 20));
        deleteButton.setPreferredSize(new Dimension(100, 35));
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(e -> {
            try {
                cartsDBManager.removeProductFromCart(cart.getId(), product.getId());
                PanelUtil.changePanel(frame, panel, new CartPanel(frame, dbConnection, user));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
                ex.printStackTrace();
            }
        });
        this.add(deleteButton, gbc);

        gbc.gridx = 1;

        JLabel ratingField = new JLabel(String.valueOf(product.getRating()));
        ratingField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(ratingField, gbc);

        gbc.gridx = 2;

        JLabel ratingLabel = new JLabel("امتیاز: ");
        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(ratingLabel, gbc);
        Border blackLine = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
        this.setBorder(blackLine);
    }
}
