import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class EditProductPanel extends AbstractEditPanel implements ActionListener {

    private File file;
    private static final String filePath = ".//";
    private final JFileChooser fileChooser = new JFileChooser(filePath);
    private final JFrame frame;
    private Product product;
    private Connection dbConnection;
    private User user;

    EditProductPanel(JFrame frame, Connection dbConnection, User admin, Product product) {
        ProductsDBManager productsDBManager = new ProductsDBManager(dbConnection);

        this.frame = frame;
        this.product = product;
        this.dbConnection = dbConnection;
        this.user = admin;
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));

        // عکس بالای صفحه
        label = new JLabel();
        try {
            if (product.getImageFile() != null) {
                BufferedImage bufferedImage = ImageIO.read(product.getImageFile());
                ImageIcon imageIcon = new ImageIcon(bufferedImage.getScaledInstance(150, 150, Image.SCALE_DEFAULT));
                label.setIcon(imageIcon);
            } else {
                label.setFont((new Font("Arial", Font.PLAIN, 20)));
                label.setText("عکس موجود نیست");
            }
        } catch (IOException e) {
            label.setFont((new Font("Arial", Font.PLAIN, 20)));
            label.setText("خطا در نمایش عکس");
            e.printStackTrace();
        }

        // لیبل های سمت راست
        fieldLabels = new JLabel[6];
        JLabel productNameLabel = new JLabel("نام کالا");
        productNameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[0] = productNameLabel;
        JLabel productPriceLabel = new JLabel("قیمت");
        productPriceLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[1] = productPriceLabel;
        JLabel productDescriptionLabel = new JLabel("توضیحات");
        productDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[2] = productDescriptionLabel;
        JLabel productInventoryLabel = new JLabel("موجودی");
        productInventoryLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[3] = productInventoryLabel;
        JLabel choseImageLabel = new JLabel("انتخاب عکس");
        choseImageLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[4] = choseImageLabel;
        JLabel deleteImageLabel = new JLabel("حذف کالا");
        deleteImageLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        fieldLabels[5] = deleteImageLabel;

        //کامپونت های سمت چپ
        fields = new JComponent[6];
        JLabel productNameField = new JLabel(product.getName());
        productNameField.setFont(new Font("Arial", Font.PLAIN, 25));
        fields[0] = productNameField;
        JTextField productPriceTextField = new JTextField(String.valueOf(product.getPrice()));
        productPriceTextField.setPreferredSize(new Dimension(140, 30));
        productPriceTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        fields[1] = productPriceTextField;
        JTextArea productDescriptionTextField = new JTextArea(product.getDescription());
        productDescriptionTextField.setPreferredSize(new Dimension(140, 150));
        productDescriptionTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        productDescriptionTextField.setLineWrap(true);
        fields[2] = productDescriptionTextField;
        JSpinner productStockSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10000, 1));
        productStockSpinner.setValue(product.getStock());
        productStockSpinner.setPreferredSize(new Dimension(140, 30));
        productStockSpinner.setFont(new Font("Arial", Font.PLAIN, 18));
        fields[3] = productStockSpinner;
        JButton choseImageButton = new JButton("ویرایش");
        if (product.getImageFile() != null) {
            choseImageButton.setText(product.getImageFile().getName());
        }
        choseImageButton.setPreferredSize(new Dimension(120, 30));
        choseImageButton.setFont(new Font("Arial", Font.PLAIN, 18));
        choseImageButton.setFocusable(false);
        choseImageButton.addActionListener(this);
        fields[4] = choseImageButton;

        JButton deleteButton = new JButton("حذف");
        deleteButton.setFocusable(false);
        deleteButton.setPreferredSize(new Dimension(150, 35));
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 18));
        deleteButton.addActionListener(e -> {
            try {
                productsDBManager.deleteProduct(product.getId());
                PanelUtil.changePanel(frame, this, new UserMainPanel(frame, dbConnection, admin, UserMainPanel.SORT_DEFAULT, ""));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
                ex.printStackTrace();
            }
        });
        fields[5] = deleteButton;

        //تعریف دکمه ها
        buttons = new JButton[2];
        JButton cancelButton = new JButton("لغو");
        cancelButton.setFocusable(false);
        cancelButton.setPreferredSize(new Dimension(150, 35));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 18));
        cancelButton.addActionListener(e -> PanelUtil.changePanel(frame, this, new UserMainPanel(frame, dbConnection, admin, UserMainPanel.SORT_DEFAULT, "")));
        buttons[0] = cancelButton;
        JButton submitButton = new JButton("ثبت");
        submitButton.setFocusable(false);
        submitButton.setPreferredSize(new Dimension(150, 35));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        submitButton.addActionListener(e -> {
            try {
                double productPrice = Double.parseDouble(productPriceTextField.getText());
                String productDescription = productDescriptionTextField.getText();
                int productStock = (int) productStockSpinner.getValue();

                // Saving the image
                if (product.getImageFile() != null) {
                    File newProductImageFile = new File(Main.PRODUCTS_IMAGES_FOLDER_PATH + product.getId() + "." + FilenameUtils.getExtension(product.getImageFile().getName()));
                    FileUtils.copyFile(product.getImageFile(), newProductImageFile);
                    product.setImageFile(newProductImageFile);
                }

                // Updating the product in the database
                productsDBManager.updateProduct(
                        product.getId(),
                        productPrice,
                        productDescription,
                        productStock,
                        product.getImageFile() == null ? null : product.getImageFile().getName()
                );

                PanelUtil.changePanel(frame, this, new UserMainPanel(frame, dbConnection, admin, UserMainPanel.SORT_DEFAULT, ""));
            } catch (NullPointerException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "لطفا یک عدد برای قیمت وارد کنید.");
                ex.printStackTrace();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "اختلال در ارتباط با پایگاه داده. لطفا بعدا دوباره امتحان کنید.");
                ex.printStackTrace();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "ارور: فایل عکس ذخیره نشد. در صورت نیاز کالا را ویرایش کنید.");
                ex.printStackTrace();
            }
        });
        buttons[1] = submitButton;

        construct(this);
    }

    // اکشن پرفورم برای انتخاب عکس
    @Override
    public void actionPerformed(ActionEvent e) {
        fileChooser.setAcceptAllFileFilterUsed(false);
        int option = fileChooser.showOpenDialog(this.frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        Product productForChangeImage = product;
        productForChangeImage.setImageFile(file);
        PanelUtil.changePanel(this.frame, this, new EditProductPanel(this.frame, this.dbConnection, this.user, productForChangeImage));
    }
}
