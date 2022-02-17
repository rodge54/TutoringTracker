package View_Controller;

import DataAccess.LessonDb;
import DataAccess.StudentDb;
import Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewStudentsController extends Controller implements Initializable {

    public TableView<Student> studentTbl;
    public TableColumn<Student, String> studentCol;
    public TableColumn<Student, String> phoneNumberCol;
    public TableColumn<Student, String> emailCol;
//    public TableColumn<> timeZoneCol;
    private ObservableList<Student> data = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupStudentsTable();
    }

    private void setupStudentsTable() {
        data = StudentDb.getAllStudents();
        studentCol.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Student, String>("phoneNumber"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        //TODO: Need to add TimeZones
        studentTbl.setItems(data);
    }
}
