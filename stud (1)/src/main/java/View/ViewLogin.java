package View;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;


/**
 * Created by Sufian Vaio on 17.01.2017.
 */
public class ViewLogin {

    TextField username;
    PasswordField password;
    Dialog <Pair<String, String>>  dialog ;
    ButtonType loginButtonType;
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

         username = new TextField();
        username.setPromptText("Username");
         password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() ->{});

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                options = 0;
                return new Pair<>(username.getText(), password.getText());
            }else{
                options =1;
            }
            return null;
        });

        result = dialog.showAndWait();


    }


    public int getChoice() {
        return options;

    }

    public String getName() {
        return username.getText();
    }

    public String getPass() {
        return password.getText();
    }
}
