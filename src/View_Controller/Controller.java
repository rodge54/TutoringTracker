package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private static final String homePagePath = "Home.fxml";

    public Stage loadStage(String fxmlFile, ActionEvent event, String title){

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = null;
        try {
            scene = FXMLLoader.load(getClass().getResource(fxmlFile));
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle(title);
        stage.setScene(new Scene(scene));
        return stage;
    }

    public void onHomeBtnPress(ActionEvent event) {
        // Returns user to Home Page
        loadStage(homePagePath, event, "Home");
    }

    public void onAddStudentBtnPress(ActionEvent event) {
        Stage stage = loadStage("AddStudent.fxml", event, "Add Student");
        stage.show();
    }
}
