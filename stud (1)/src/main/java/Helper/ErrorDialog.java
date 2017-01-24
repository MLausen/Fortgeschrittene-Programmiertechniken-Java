package Helper;

import javafx.scene.control.Alert;

/**
 * Created by Team 10
 */
// error message class to inform user
public class ErrorDialog {
    public static void error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error");
        alert.setContentText(message);

        alert.showAndWait();
    }
}
