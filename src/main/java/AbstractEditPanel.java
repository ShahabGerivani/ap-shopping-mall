import javax.swing.*;
import java.util.HashMap;

public abstract class AbstractEditPanel extends JPanel {
    protected JLabel label;
    protected HashMap<JLabel, JComponent> fields;
    protected JButton[] buttons;

    protected void construct() {
        // TODO
    }
}
