package Helper;

import javax.swing.*;

/**
 * Created by Team 10
 */
public class ErrorDialog{
    public static void error(String message){
        JOptionPane.showMessageDialog(null,
                "Error : " + message);
    }
}
