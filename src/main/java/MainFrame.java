import javax.swing.*;

public class MainFrame extends JFrame {
    MainFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Shop");
        this.setSize(800, 600);
        this.setVisible(true);
        this.setResizable(false);
        SignInPanel signInPanel =new  SignInPanel(this);
        this.add(signInPanel);
    }
}
