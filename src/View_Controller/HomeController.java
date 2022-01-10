package View_Controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;


public class HomeController extends Controller{

    public void onAddLessonBtnPress(ActionEvent event) {
        Stage stage = loadStage("AddLesson.fxml", event, "Add Lesson");
        stage.show();
    }

    public void onAddStudentBtnPress(ActionEvent event) {
        Stage stage = loadStage("AddStudent.fxml", event, "Add Student");
        stage.show();
    }

    public void onViewLessonBtnPress(ActionEvent event) {
        Stage stage = loadStage("ViewLessons.fxml", event, "View Lessons");
        stage.show();
    }
}
