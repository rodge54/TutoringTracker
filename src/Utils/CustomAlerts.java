package Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class CustomAlerts {
    // TODO: Create custom alerts
    public static void WarningAlert(String title, String message){
        Alert a = new Alert(Alert.AlertType.NONE);
        // set alert type
        a.setAlertType(Alert.AlertType.WARNING);
        a.setTitle(title);
        a.setContentText(message);
        // show the dialog
        a.show();
        System.out.println(message);
    }

    public static boolean ConfirmationAlert(String title, String message){
        boolean confirm = false;
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                message,
                yes,
                no);

        alert.setTitle("Date format warning");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {
            confirm = true;
        }
        return confirm;
    }
}
