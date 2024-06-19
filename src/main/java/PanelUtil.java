import javax.swing.*;

public class PanelUtil {
    public static void changePanel(JFrame frame, JPanel currentPanel , JPanel nextPanel){
        frame.remove(currentPanel);
        frame.add(nextPanel);
        frame.repaint();
        frame.revalidate();
    }


}
