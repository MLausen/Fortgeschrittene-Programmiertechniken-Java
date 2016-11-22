package Helper;

import javax.swing.*;

/**
 * Created by Team 10
 */
// error message class to inform user
public class ErrorDialog {
    public static void error(String message) {
        JOptionPane.showMessageDialog(null,
                "Error : " + message);
    }
}
