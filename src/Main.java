import ui.RentACarFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Rent a Car tətbiqinin əsas giriş nöqtəsi.
 */
public class Main {
    public static void main(String[] args) {
        // Əməliyyat sisteminin vizual görünüşünü (Look & Feel) tətbiq edirik
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        // Swing interfeysini Event Dispatch Thread (EDT) daxilində başladırıq
        SwingUtilities.invokeLater(() -> {
            RentACarFrame frame = new RentACarFrame();
            frame.setVisible(true);
        });
    }
}
