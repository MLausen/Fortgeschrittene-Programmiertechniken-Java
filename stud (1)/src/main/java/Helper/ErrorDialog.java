package Helper;

import javax.swing.*;

/**
 * Created
 */
public class ErrorDialog{
    public static void error(String message){
        JOptionPane.showMessageDialog(null,
                "Error : " + message);
    }
}
