import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class NewProductPanel extends AbstractEditPanel implements ActionListener {
    private File file;
    private static final String filePath = ".//";
    private final JFileChooser fileChooser = new JFileChooser(filePath);
    private final JFrame frame;


    NewProductPanel(JFrame frame) {
        this.frame=frame;
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
        JSpinner productInventorySpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10000, 1));
        productInventorySpinner.setPreferredSize(new Dimension(140, 30));
        productInventorySpinner.setFont(new Font("Arial", Font.PLAIN, 18));
        fields[3] = productInventorySpinner;
        JButton choseImageButton = new JButton("انتخاب");
        choseImageButton.setPreferredSize(new Dimension(120, 30));
        choseImageButton.setFont(new Font("Arial", Font.PLAIN, 18));
        choseImageButton.setFocusable(false);
        choseImageButton.addActionListener(this);
        fields[4] = choseImageButton;

        //تعریف دکمه ها
        buttons = new JButton[2];
        JButton cancelButton = new JButton("انصراف");
        cancelButton.setFocusable(false);
        cancelButton.setPreferredSize(new Dimension(150, 35));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 18));
        buttons[0] = cancelButton;
        JButton submitButton = new JButton("ثبت");
        submitButton.setFocusable(false);
        submitButton.setPreferredSize(new Dimension(150, 35));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 18));
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
        JButton fileChooserButton  = (JButton) e.getSource();
        fileChooserButton.setText(file.getName());
        frame.revalidate();
        frame.repaint();
    }
}
