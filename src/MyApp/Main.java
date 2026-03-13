package MyApp;

import MyLib.Dialogs.Login;
import javax.swing.UIManager;

/**
 *
 * @author ymnis
 */
public class Main {
    public static void main(String[] args) {
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
            
            UIManager.put("Button.arc", 15);
            UIManager.put("Component.arc", 15);
            
        } catch (Exception ex) {
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
