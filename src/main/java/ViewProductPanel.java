import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ViewProductPanel extends JPanel {
    ViewProductPanel(JFrame frame, Connection dbConnection, User user, Product product) {
        RatingsDBManager ratingsDBManager = new RatingsDBManager(dbConnection);
        CartsDBManager cartsDBManager = new CartsDBManager(dbConnection);
        int currentUserRating;
        Cart userCart;
        try {
            currentUserRating = ratingsDBManager.getRating(user.getUsername(), product.getId());
            userCart = cartsDBManager.getOrCreateCartForUser(user.getUsername());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
            PanelUtil.changePanel(frame, this, new ProfilePanel(frame, dbConnection, user));
            e.printStackTrace();
            return;
        }

        this.setLayout(new GridBagLayout());
        JLabel imageLabel = new JLabel();
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel productNameField = new JLabel(product.getName());
        productNameField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productNameField, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 2;

        JLabel productNameLabel = new JLabel("نام کالا:");
        productNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productNameLabel, gbc);

        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.gridx = 3;

        try {
            if (product.getImageFile() != null) {
                BufferedImage bufferedImage = ImageIO.read(product.getImageFile());
                ImageIcon imageIcon = new ImageIcon(bufferedImage.getScaledInstance(250, 250, Image.SCALE_DEFAULT));
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

        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;

        JLabel productPriceField = new JLabel(String.valueOf(product.getPrice()));
        productPriceField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productPriceField, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 2;

        JLabel productPriceLabel = new JLabel("قیمت:");
        productPriceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productPriceLabel, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;

        JLabel productRateField = new JLabel(String.format("%.2g%n", product.getRating()));
        productRateField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productRateField, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 2;

        JLabel productRateLabel = new JLabel("امتیاز:");
        productRateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productRateLabel, gbc);

        gbc.gridwidth = 5;
        gbc.gridx = 0;
        gbc.gridy = 3;

        JLabel descriptionField = new JLabel(product.getDescription());
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(descriptionField, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 5;

        JLabel descriptionLabel = new JLabel("توضیحات: ");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(descriptionLabel, gbc);

        gbc.gridwidth = 3;
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JSlider ratingSlider = new JSlider(0, 5, 1);
        ratingSlider.setPreferredSize(new Dimension(400, 200));
        ratingSlider.setPaintTicks(true);
        ratingSlider.setMinorTickSpacing(1);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setPaintTrack(true);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setValue(currentUserRating);
        this.add(ratingSlider, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;

        JButton submitRatingButton = new JButton("ثبت امتیاز");
        submitRatingButton.setFont(new Font("Arial", Font.PLAIN, 17));
        submitRatingButton.setPreferredSize(new Dimension(100, 35));
        submitRatingButton.setFocusable(false);
        submitRatingButton.addActionListener(e -> {
            try {
                if (ratingSlider.getValue() == 0) {
                    JOptionPane.showMessageDialog(frame, "لطفا امتیازی بین 1 تا 5 انتخاب کنید.");
                } else {
                    if (currentUserRating == 0) {
                        ratingsDBManager.submitNewRating(user.getUsername(), product.getId(), ratingSlider.getValue());
                    } else {
                        ratingsDBManager.updateRating(user.getUsername(), product.getId(), ratingSlider.getValue());
                    }
                    JOptionPane.showMessageDialog(frame, "امتیاز شما با موفقیت ثبت شد.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
                ex.printStackTrace();
            }
        });
        this.add(submitRatingButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 5;
        gbc.fill = GridBagConstraints.NONE;

        JLabel ratingLabel = new JLabel("امتیاز دهی: ");
        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(ratingLabel, gbc);

        gbc.gridwidth = 3;
        gbc.gridy = 5;
        gbc.gridx = 0;

        JButton backButton = new JButton("بازگشت");
        backButton.setFont(new Font("Arial", Font.PLAIN, 17));
        backButton.setPreferredSize(new Dimension(100, 35));
        backButton.setFocusable(false);
        backButton.addActionListener(e -> PanelUtil.changePanel(frame, this, new UserMainPanel(frame, dbConnection, user, UserMainPanel.SORT_DEFAULT, "")));
        this.add(backButton, gbc);

        gbc.gridx = 3;

        boolean isProductInCart = userCart.getProductsAndCount().keySet().stream().anyMatch(p -> p.getId() == product.getId());
        JButton buyButton = isProductInCart ? new JButton("حذف از سبد") : new JButton("افزودن به سبد");
        buyButton.setFont(new Font("Arial", Font.PLAIN, 17));
        buyButton.setPreferredSize(new Dimension(100, 35));
        buyButton.setFocusable(false);
        buyButton.addActionListener(e -> {
            try {
                if (isProductInCart) {
                    cartsDBManager.removeProductFromCart(userCart.getId(), product.getId());
                } else {
                    cartsDBManager.addProductToCart(userCart.getId(), product.getId());
                }
                PanelUtil.changePanel(frame, this, new ViewProductPanel(frame, dbConnection, user, product));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
                ex.printStackTrace();
            }
        });
        this.add(buyButton, gbc);
    }
}
