package View_Controller;

import DataAccess.*;
import Model.Lesson;
import Model.PaymentType;
import Model.Student;
import Model.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddLessonController extends Controller implements Initializable {

    public DatePicker dateDp;
    public TextField hourlyRateTf;
    public TextField lessonLengthTf;
    public ComboBox<Subject> subjectCb;
    public ComboBox<PaymentType> paymentTypeCb;
    public ComboBox<Student> studentCb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            StudentDb.setStudents();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        subjectCb.setItems(SubjectDb.getSubjects());
        paymentTypeCb.setItems(PaymentTypeDb.getPaymentTypes());
        studentCb.setItems(StudentDb.getStudents());
    }

    public void onAddLessonBtnPress(ActionEvent event) {
        String date = "";
        int hourlyRate = 0;
        int lessonLength = 0;
        int subjectId = 0;
        int paymentTypeId = 0;
        int studentId = 0;

        try {
            date = dateDp.getValue().toString();
            hourlyRate = Integer.parseInt(hourlyRateTf.getText());
            lessonLength = Integer.parseInt(lessonLengthTf.getText());
            subjectId = subjectCb.getValue().getSubjectId();
            paymentTypeId = paymentTypeCb.getValue().getPaymentTypeId();
            studentId = studentCb.getValue().getStudentId();
        }
        catch (NullPointerException e){
            System.out.println("Fill in all text fields");
            return;
        }
        boolean success = LessonDb.addLesson(new Lesson(date, hourlyRate, lessonLength, subjectId, paymentTypeId, studentId));
        if (success){
            dateDp.setValue(null);
            hourlyRateTf.clear();
            lessonLengthTf.clear();
            subjectCb.setValue(null);
            paymentTypeCb.setValue(null);
            studentCb.setValue(null);
        }
    }




}
