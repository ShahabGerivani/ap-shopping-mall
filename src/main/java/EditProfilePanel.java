import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class EditProfilePanel extends JPanel {
    EditProfilePanel(JFrame frame, Connection dbConnection, User user){
        super();
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=4;

        JLabel editLabel = new JLabel("ویرایش");
        editLabel.setFont(new Font("Arial", Font.PLAIN, 23));
        this.add(editLabel,gbc);

        gbc.gridwidth=1;
        gbc.gridx=0;
        gbc.gridy=1;

        JTextField nameField = new JTextField(user.getName());
        nameField.setFont(new Font("Arial", Font.PLAIN, 20));
        nameField.setPreferredSize(new Dimension(140,30));
        this.add(nameField,gbc);

        gbc.gridx=1;

        JLabel nameLabel =new JLabel("نام");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(nameLabel,gbc);

        gbc.gridx=2;


        JTextField usernameField = new JTextField(user.getUsername());
        usernameField.setPreferredSize(new Dimension(140,30));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(usernameField,gbc);

        gbc.gridx=3;

        JLabel usernameLabel = new JLabel("نام کاربری");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(usernameLabel,gbc);

        gbc.gridx=0;
        gbc.gridy=2;

        JTextArea addressTextArea = new JTextArea(user.getAddress());
        addressTextArea.setLineWrap(true);
        addressTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        this.add(addressTextArea,gbc);

        gbc.gridx=1;

        JLabel addressLabel = new JLabel("آدرس");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(addressLabel,gbc);

        gbc.gridx=2;

        JTextField phoneField = new JTextField(user.getPhone());
        phoneField.setPreferredSize(new Dimension(140,30));
        phoneField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(phoneField,gbc);

        gbc.gridx=3;

        JLabel phoneLabel = new JLabel("شماره تماس");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(phoneLabel,gbc);

        gbc.gridx=0;
        gbc.gridy=3;

        JButton cancelButton = new JButton("لغو");
        cancelButton.addActionListener(e -> PanelUtil.changePanel(frame,this,new ProfilePanel(frame, dbConnection, user)));
        cancelButton.setPreferredSize(new Dimension(100,35));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 20));
        cancelButton.setFocusable(false);
        this.add(cancelButton,gbc);

        gbc.gridx=1;
        gbc.gridy=3;

        JButton submitButton = new JButton("ثبت");
        submitButton.setPreferredSize(new Dimension(100,35));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        submitButton.setFocusable(false);
        this.add(submitButton,gbc);

        gbc.gridx=3;
        gbc.gridy=3;

        JButton changePasswordButton = new JButton("تغییر رمز");
        changePasswordButton.addActionListener(e -> PanelUtil.changePanel(frame,this,new ChangePasswordPanel(frame, dbConnection, user)));
        changePasswordButton.setPreferredSize(new Dimension(100,35));
        changePasswordButton.setFont(new Font("Arial", Font.PLAIN, 20));
        changePasswordButton.setFocusable(false);
        this.add(changePasswordButton,gbc);

    }
}
