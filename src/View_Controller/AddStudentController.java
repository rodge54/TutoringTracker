package View_Controller;

import DataAccess.StudentDb;
import DataAccess.TimezoneDb;
import Model.Student;
import Model.Timezone;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddStudentController extends Controller implements Initializable {

    public TextField nameTf;
    public TextField phoneNumberTf;
    public TextField emailTf;
    public ComboBox<Timezone> timezoneCb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timezoneCb.setItems(TimezoneDb.getTimezones());
    }

    public void onAddStudentBtnPress(ActionEvent event) {
        String name = "";
        String phoneNumber = "";
        String email = "";
        int timezoneId = -1;

        try {
            name = nameTf.getText();
            phoneNumber = phoneNumberTf.getText();
            email = emailTf.getText();
            // TODO: May need seperate validation check for CB in case of NULL
            timezoneId = timezoneCb.getValue().getTimezoneId();
        }
        catch (NullPointerException e){
            System.out.println("Fill in all text fields");
            return;
        }
        boolean success = StudentDb.addStudent(new Student(name, phoneNumber, email, timezoneId));
        if (success){
            nameTf.clear();
            phoneNumberTf.clear();
            emailTf.clear();
            timezoneCb.setValue(null);
        }
    }
}
