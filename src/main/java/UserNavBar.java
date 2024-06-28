import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.Connection;

public class UserNavBar extends JPanel {
    UserNavBar(double balance, String title, JFrame frame, Connection dbConnection, User user,JPanel panel) {
        super();
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5,5,5,5);

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;

        JButton increaseBalanceButton = new JButton("+");
        increaseBalanceButton.setFocusable(false);
        increaseBalanceButton.setPreferredSize(new Dimension(50, 40));
        increaseBalanceButton.setFont(new Font("Arial", Font.PLAIN, 13));
        increaseBalanceButton.addActionListener(e -> PanelUtil.changePanel(frame,panel,new IncreaseBalancePanel(frame,dbConnection,user)));
        this.add(increaseBalanceButton, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(5,5,5,5);

        JLabel increaseBalance = new JLabel(String.valueOf(balance));
        increaseBalance.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(increaseBalance, gbc);

        gbc.gridx = 2;

        gbc.insets = new Insets(5,0,5,0);

        JLabel increaseBalanceLabel = new JLabel("موجودی :");
        increaseBalanceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(increaseBalanceLabel, gbc);

        gbc.gridx = 3;
        gbc.insets = new Insets(5,0,5,0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(titleLabel, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 4;
        gbc.insets = new Insets(5,5,5,5);

        JButton mainPanelButton = new JButton("صفحه اصلی");
        mainPanelButton.setFocusable(false);
        mainPanelButton.setPreferredSize(new Dimension(100, 35));
        mainPanelButton.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanelButton.addActionListener(e -> PanelUtil.changePanel(frame,panel,new UserMainPanel(frame, dbConnection, user, UserMainPanel.SORT_DEFAULT, "") ));
        this.add(mainPanelButton, gbc);

        gbc.gridx = 5;

        JButton cartPanelButton = new JButton("سبد خرید");
        cartPanelButton.setFocusable(false);
        cartPanelButton.setPreferredSize(new Dimension(100, 35));
        cartPanelButton.setFont(new Font("Arial", Font.PLAIN, 15));
        cartPanelButton.addActionListener(e -> PanelUtil.changePanel(frame,panel,new CartPanel(frame,dbConnection,user)));
        this.add(cartPanelButton, gbc);

        gbc.gridx = 6;

        JButton profileButton = new JButton("پروفایل");
        profileButton.setFocusable(false);
        profileButton.setPreferredSize(new Dimension(100, 35));
        profileButton.setFont(new Font("Arial", Font.PLAIN, 15));
        profileButton.addActionListener(e -> PanelUtil.changePanel(frame,panel,new ProfilePanel(frame,dbConnection,user)));
        this.add(profileButton, gbc);

        Border blackLine = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        this.setBorder(blackLine);


    }
}
