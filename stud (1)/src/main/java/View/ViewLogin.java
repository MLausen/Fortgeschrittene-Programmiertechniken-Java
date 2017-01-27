package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;


/**
 * Created by Sufian Vaio on 17.01.2017.
 */
public class ViewLogin {
    public static final String CANCEL_BUTTON ="cancelButton";
    public static final String LOGIN_BUTTON = "loginButton";

    private TextField usernameField;
    private PasswordField passwordField;
    private Dialog<Pair<String, String>> dialog;
    private ButtonType loginButtonType;

    Button loginBt;
    Button cancelBt;

    Optional<Pair<String, String>> result;
    int options;

    public ViewLogin() {
        dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Please login first");

        loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> {
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                options = 0;
                return new Pair<>(usernameField.getText(), passwordField.getText());
            } else {
                options = 1;
            }
            return null;
        });

        result = dialog.showAndWait();
    }

    public int getChoice() {
        return options;
    }

    public String getName() {
        return usernameField.getText();
    }

    public String getPass() {
        return passwordField.getText();
    }

    public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
        //loginBt.addEventHandler(ActionEvent.ACTION, eventHandler);
       // cancelBt.addEventHandler(ActionEvent.ACTION, eventHandler);
    }
}
