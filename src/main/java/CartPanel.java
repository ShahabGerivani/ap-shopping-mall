import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;

public class CartPanel  extends JPanel {
    CartPanel(JFrame frame, Connection dbConnection, User user){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JPanel UpperPanel = new JPanel();
        JPanel lowerPanel = new JPanel();

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        UserNavBar userNavBar = new UserNavBar(user.getBalance(), "خوش آمدید " + user.getUsername(), frame, dbConnection, user, this);
        UpperPanel.add(userNavBar);
        this.add(userNavBar, gridBagConstraints);

        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 3, 3, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;

        //just for test
        ArrayList<Product> products = new ArrayList<>();
        Product product1 = new Product(1,"gol1",100,"hi",10,null,5);
        Product product2 = new Product(2,"gol2",1000,"himoz",20,null,3);
        Product product3 = new Product(3,"golabi",1500,"himozjkjk",200,null,1);
        products.add(product1);
        products.add(product2);
        products.add(product3);

        gbc.gridwidth=2;
        for (int i=0; i<products.size();i++){
            gbc.gridy=i;
            lowerPanel.add(new CartProductCardPanel(frame,dbConnection,user,products.get(i),this),gbc);
        }
        gbc.insets = new Insets(5,3,10,3);
        gbc.gridwidth=1;
        gbc.gridx=0;
        gbc.gridy= products.size();
        JButton emptyCartBut = new JButton("خالی کردن سبد");
        emptyCartBut.setFont(new Font("Arial", Font.PLAIN, 20));
        emptyCartBut.setPreferredSize(new Dimension(200, 35));
        emptyCartBut.setFocusable(false);
        lowerPanel.add(emptyCartBut,gbc);

        gbc.gridx=1;
        JButton buyButton = new JButton("پرداخت");
        buyButton.setFont(new Font("Arial", Font.PLAIN, 20));
        buyButton.setPreferredSize(new Dimension(100, 35));
        buyButton.setFocusable(false);
        lowerPanel.add(buyButton,gbc);


        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        JScrollPane scrollPanel = new JScrollPane(lowerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPanel, gridBagConstraints);
    }
}
