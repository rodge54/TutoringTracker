package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;


public class HomeController extends Controller implements Initializable {

    public Button addLessonBtn;
    public Button addStudentBtn;
    public Button viewLessonsBtn;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();

        rb = ResourceBundle.getBundle("Resources/Languages", locale);
        // FOR TESTING ONLY
//        String test = "Test";
//        rb = ResourceBundle.getBundle("Resources/Languages", Locale.forLanguageTag("fr"));
//        addLessonBtn.setText(String.format(rb.getString("ADD_LESSON_BTN"),test));
        addLessonBtn.setText(rb.getString("ADD_LESSON_BTN"));
        addStudentBtn.setText(rb.getString("ADD_STUDENT_BTN"));
        viewLessonsBtn.setText(rb.getString("VIEW_LESSON_BTN"));
    }
}
