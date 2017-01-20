package View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sufian Vaio on 17.01.2017.
 */
public class ViewLogin {
    JPanel panel;
    JLabel nameUabel;
    JLabel pwLabel;
    JTextField name;
    JPasswordField pass;
    int option;
    String[] options;


    public ViewLogin() {

        panel = new JPanel(new GridLayout(2, 2));
        nameUabel = new JLabel("Username:");
        pwLabel = new JLabel("Password:");
        pass = new JPasswordField(10);
        name = new JTextField(10);
        panel.add(nameUabel);
        panel.add(name);
        panel.add(pwLabel);
        panel.add(pass);
        options = new String[]{"OK", "Cancel"};
        option = JOptionPane.showOptionDialog(null, panel, "Login",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);

    }



    public int getChoice() {
        return option;
    }

    public String getName() {
        return name.getText();
    }

    public String getPass() {
        return new String(pass.getPassword());
    }
}
