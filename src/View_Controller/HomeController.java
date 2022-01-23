package View_Controller;

import DataAccess.StudentDb;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;


public class HomeController extends Controller implements Initializable {

    public Button addLessonBtn;
    public Button addStudentBtn;
    public Button viewLessonsBtn;
    public Label titleLbl;
    public Button viewStudentsBtn;

    public void onAddLessonBtnPress(ActionEvent event) {
        Stage stage = loadStage("AddLesson.fxml", event, "Add Lesson");
        stage.show();
    }

    public void onViewLessonBtnPress(ActionEvent event) {
        Stage stage = loadStage("ViewLessons.fxml", event, "View Lessons");
        stage.show();
    }

    public void onViewStudentsBtnPress(ActionEvent event) {
        Stage stage = loadStage("ViewStudents.fxml", event, "View Students");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Get all students from database
        try {
            StudentDb.setStudents();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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
