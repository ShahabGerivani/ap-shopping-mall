import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class ProductCardPanel extends JPanel {
    File file = new File("C:\\Users\\USER\\Desktop\\capsule_616x353.jpg");

    ProductCardPanel(JFrame frame, Connection dbConnection,User user,Product product,JPanel panel) {
        JLabel imageLabel = new JLabel();
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth=2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        try {
            BufferedImage bufferedImage = ImageIO.read(this.file);
            ImageIcon imageIcon = new ImageIcon(bufferedImage.getScaledInstance(200, 200, Image.SCALE_DEFAULT));
            imageLabel.setIcon(imageIcon);
        } catch (IOException e) {
            imageLabel.setFont((new Font("Arial", Font.PLAIN, 20)));
            imageLabel.setText("خطا در نمایش عکس");
            e.printStackTrace();
        }
        this.add(imageLabel, gbc);

        gbc.gridwidth=1;
        gbc.gridx = 0;
        gbc.gridy = 1;

        JLabel productNameField = new JLabel(product.getName());
        productNameField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productNameField,gbc);

        gbc.gridx=1;

        JLabel productNameLabel = new JLabel("نام کالا:");
        productNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productNameLabel,gbc);

        gbc.gridx=0;
        gbc.gridy=2;

        JLabel productPriceField = new JLabel(String.valueOf(product.getPrice()));
        productPriceField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productPriceField,gbc);

        gbc.gridx=1;


        JLabel productPriceLabel = new JLabel("قیمت:");
        productPriceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productPriceLabel,gbc);

        gbc.gridx=0;
        gbc.gridy=3;

        JLabel productRateField = new JLabel(String.valueOf(product.getRating()));
        productRateField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productRateField,gbc);

        gbc.gridx=1;

        JLabel productRateLabel = new JLabel("امتیاز:");
        productRateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(productRateLabel,gbc);

        gbc.gridy=4;
        gbc.gridx=0;
        gbc.gridwidth=2;

        JButton viewButton = new JButton("مشاهده");
        viewButton.setFont(new Font("Arial", Font.PLAIN, 20));
        viewButton.setPreferredSize(new Dimension(100,35));
        viewButton.setFocusable(false);
        this.add(viewButton,gbc);
    }
}
