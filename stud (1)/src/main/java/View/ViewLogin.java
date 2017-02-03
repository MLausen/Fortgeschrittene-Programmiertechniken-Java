package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


/**
 * Created  by Team 10
 */
public class ViewLogin extends GridPane {
    public static final String CANCEL_BUTTON = "cancelButton";
    public static final String LOGIN_BUTTON = "loginButton";

    private TextField usernameField;
    private PasswordField passwordField;


    Button loginBt;
    Button cancelBt;


    public ViewLogin() {

        cancelBt = new Button("Abbrechen");
        cancelBt.setId(CANCEL_BUTTON);

        loginBt = new Button("Login");
        loginBt.setId(LOGIN_BUTTON);


        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20, 150, 10, 10));

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        this.add(new Label("Username:"), 0, 0);
        this.add(usernameField, 1, 0);
        this.add(new Label("Password:"), 0, 1);
        this.add(passwordField, 1, 1);
        this.add(loginBt, 0, 2);
        this.add(cancelBt, 1, 2);

    }

    public String getName() {
        return usernameField.getText();
    }

    public String getPass() {
        return passwordField.getText();
    }

    public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
        loginBt.addEventHandler(ActionEvent.ACTION, eventHandler);
        cancelBt.addEventHandler(ActionEvent.ACTION, eventHandler);
    }
}
