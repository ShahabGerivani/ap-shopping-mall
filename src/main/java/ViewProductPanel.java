import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class ViewProductPanel extends JPanel {
    File file;

    ViewProductPanel(JFrame frame, Connection dbConnection, User user, Product product) {
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
            if (file != null) {
                BufferedImage bufferedImage = ImageIO.read(this.file);
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

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 4;

        JButton submitRatingButton = new JButton("ثبت امتیاز");
        submitRatingButton.setFont(new Font("Arial", Font.PLAIN, 17));
        submitRatingButton.setPreferredSize(new Dimension(100,35));
        submitRatingButton.setFocusable(false);
        this.add(submitRatingButton,gbc);

        gbc.gridwidth = 3 ;
        gbc.gridx=2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JSlider ratingSlider = new JSlider(1,5,1);
        ratingSlider.setPreferredSize(new Dimension(400,200));
        ratingSlider.setPaintTicks(true);
        ratingSlider.setMinorTickSpacing(1);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setPaintTrack(true);
        ratingSlider.setMajorTickSpacing(1);
        this.add(ratingSlider,gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 5 ;
        gbc.fill = GridBagConstraints.NONE;

        JLabel ratingLabel = new JLabel("امتیاز دهی: ");
        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(ratingLabel, gbc);

        gbc.gridwidth=3;
        gbc.gridy=5;
        gbc.gridx=0;

        JButton backButton = new JButton("بازگشت");
        backButton.setFont(new Font("Arial", Font.PLAIN, 17));
        backButton.setPreferredSize(new Dimension(100,35));
        backButton.setFocusable(false);
        this.add(backButton,gbc);

        gbc.gridx=3;

        JButton buyButton = new JButton("خرید");
        buyButton.setFont(new Font("Arial", Font.PLAIN, 17));
        buyButton.setPreferredSize(new Dimension(100,35));
        buyButton.setFocusable(false);
        this.add(buyButton,gbc);





    }
}
