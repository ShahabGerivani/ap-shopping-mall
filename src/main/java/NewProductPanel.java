import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class NewProductPanel extends AbstractEditPanel implements ActionListener {
    private File file;
    private static final String filePath = ".//";
    private final JFileChooser fileChooser = new JFileChooser(filePath);
    private final JFrame frame;


    NewProductPanel(JFrame frame, Connection dbConnection, User admin) {
        this.frame = frame;
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        //تعریف لیبل بالایی
        label = new JLabel("محصول جدید");
        label.setFont(new Font("Arial", Font.PLAIN, 30));

        //تعریف لیبل های راستی
        fieldLabels = new JLabel[5];
        JLabel productNameLabel = new JLabel("نام محصول");
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
        // نعریف کامپوننت های سمت چپ
        fields = new JComponent[5];
        JTextField productNameTextField = new JTextField();
        productNameTextField.setPreferredSize(new Dimension(140, 30));
        productNameTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        fields[0] = productNameTextField;
        JTextField productPriceTextField = new JTextField();
        productPriceTextField.setPreferredSize(new Dimension(140, 30));
        productPriceTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        fields[1] = productPriceTextField;
        JTextArea productDescriptionTextField = new JTextArea();
        productDescriptionTextField.setPreferredSize(new Dimension(140, 150));
        productDescriptionTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        productDescriptionTextField.setLineWrap(true);
        fields[2] = productDescriptionTextField;
        JSpinner productStockSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10000, 1));
        productStockSpinner.setPreferredSize(new Dimension(140, 30));
        productStockSpinner.setFont(new Font("Arial", Font.PLAIN, 18));
        fields[3] = productStockSpinner;
        JButton chooseImageButton = new JButton("انتخاب");
        chooseImageButton.setPreferredSize(new Dimension(120, 30));
        chooseImageButton.setFont(new Font("Arial", Font.PLAIN, 18));
        chooseImageButton.setFocusable(false);
        chooseImageButton.addActionListener(this);
        fields[4] = chooseImageButton;

        //تعریف دکمه ها
        buttons = new JButton[2];
        JButton cancelButton = new JButton("انصراف");
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
                String productName = productNameTextField.getText();
                double productPrice = Double.parseDouble(productPriceTextField.getText());
                String productDescription = productDescriptionTextField.getText();
                int productStock = (int) productStockSpinner.getValue();
                File productImageFile = fileChooser.getSelectedFile();

                if (productName.isBlank()) {
                    JOptionPane.showMessageDialog(frame, "نام کالا نباید خالی باشد.");
                } else {
                    // Adding the product to database
                    ProductsDBManager productsDBManager = new ProductsDBManager(dbConnection);
                    int productId = productsDBManager.addNewProduct(
                            productName,
                            productPrice,
                            productDescription,
                            productStock,
                            productImageFile == null ? null : FilenameUtils.getExtension(productImageFile.getName())
                    );

                    // Saving the image
                    if (productImageFile != null) {
                        FileUtils.copyFile(productImageFile, new File(Main.PRODUCTS_IMAGES_FOLDER_PATH + productId + "." + FilenameUtils.getExtension(productImageFile.getName())));
                    }

                    PanelUtil.changePanel(frame, this, new UserMainPanel(frame, dbConnection, admin, UserMainPanel.SORT_DEFAULT, ""));
                }
            } catch (NullPointerException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "لطفا یک عدد برای قیمت وارد کنید.");
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

    //اکشن پرفورم برای دکمه انتخاب عکس
    @Override
    public void actionPerformed(ActionEvent e) {
        fileChooser.setAcceptAllFileFilterUsed(false);
        int option = fileChooser.showOpenDialog(this.frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        JButton fileChooserButton = (JButton) e.getSource();
        fileChooserButton.setText(file.getName());
        frame.revalidate();
        frame.repaint();
    }
}
