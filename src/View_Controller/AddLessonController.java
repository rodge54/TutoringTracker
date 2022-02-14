package View_Controller;

import DataAccess.*;
import Model.Lesson;
import Model.PaymentType;
import Model.Student;
import Model.Subject;
import Utils.CustomAlerts;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddLessonController extends Controller implements Initializable {

    public DatePicker dateDp;
    public TextField hourlyRateTf;
    public TextField lessonLengthTf;
    public ComboBox<Subject> subjectCb;
    public ComboBox<PaymentType> paymentTypeCb;
    public ComboBox<Student> studentCb;
    public ToggleGroup group;
    public RadioButton yesRb;
    public RadioButton noRb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        subjectCb.setItems(SubjectDb.getSubjects());
        paymentTypeCb.setItems(PaymentTypeDb.getPaymentTypes());
        studentCb.setItems(StudentDb.getAllStudents());
        yesRb.setSelected(true);
    }

    public void onAddLessonBtnPress(ActionEvent event) throws SQLException {
        LocalDate date = null;
        int hourlyRate = 0;
        int lessonLength = 0;
        int subjectId = 0;
        int paymentTypeId = 0;
        int studentId = 0;
        boolean paid = false;

        RadioButton selected = (RadioButton) group.getSelectedToggle();
        if (selected.getText().equals("Yes")){
            paid = true;
        }

        try {
            date = dateDp.getValue();

            String hourlyRateText = hourlyRateTf.getText();

            String lessonLengthText = lessonLengthTf.getText();
            if(!hourlyRateText.equals("") || !lessonLengthText.equals("")){
                hourlyRate = Integer.parseInt(hourlyRateText);
                lessonLength = Integer.parseInt(lessonLengthText);
            }
            else {
                CustomAlerts.WarningAlert("Warning", "Fill in all text fields");
                return;
            }
            subjectId = subjectCb.getValue().getSubjectId();
            paymentTypeId = paymentTypeCb.getValue().getPaymentTypeId();
            studentId = studentCb.getValue().getStudentId();

        }
        catch (NullPointerException e){
            CustomAlerts.WarningAlert("Warning", "Fill in all text fields");
            return;
        }
        catch (NumberFormatException e){
            CustomAlerts.WarningAlert("Warning", "Please only enter numbers in hourly rate and lesson length fields");
            return;
        }
        boolean confirm = CustomAlerts.ConfirmationAlert("Confirm", "Do you want to add lesson?");

        Subject subject = SubjectDb.getSubjectById(subjectId);
        PaymentType paymentType = PaymentTypeDb.getPaymentTypeById(paymentTypeId);
        Student student = StudentDb.getStudentById(studentId);
        if(confirm){
            boolean success = LessonDb.addLesson(new Lesson(date, hourlyRate, lessonLength, subjectId, paymentTypeId,
                    studentId, paid, subject, paymentType, student));
            if (success){
                dateDp.setValue(null);
                hourlyRateTf.clear();
                lessonLengthTf.clear();
                subjectCb.setValue(null);
                paymentTypeCb.setValue(null);
                studentCb.setValue(null);
            }
            System.out.println("success");
        }
    }
}
