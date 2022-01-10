package View_Controller;

import DataAccess.LessonDb;
import Model.LessonTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewLessonsController extends Controller implements Initializable {
    @FXML public TableColumn<LessonTable, String> studentCol;
    @FXML public TableColumn<LessonTable, String> dateCol;
    @FXML public TableColumn<LessonTable, Integer> rateCol;
    @FXML public TableColumn<LessonTable, Integer> lengthCol;
    @FXML public TableColumn<LessonTable, String> subjectCol;
    @FXML public TableColumn<LessonTable, String> paymentTypeCol;
    @FXML public TableColumn<LessonTable, Integer> earningsCol;
    @FXML public TableView<LessonTable> lessonTbl;
    private ObservableList<LessonTable> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            data = LessonDb.getAllLessons();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        studentCol.setCellValueFactory(new PropertyValueFactory<LessonTable, String>("student"));
        dateCol.setCellValueFactory(new PropertyValueFactory<LessonTable, String>("date"));
        rateCol.setCellValueFactory(new PropertyValueFactory<LessonTable, Integer>("rate"));
        lengthCol.setCellValueFactory(new PropertyValueFactory<LessonTable, Integer>("length"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<LessonTable, String>("subject"));
        paymentTypeCol.setCellValueFactory(new PropertyValueFactory<LessonTable, String>("paymentType"));
        earningsCol.setCellValueFactory(new PropertyValueFactory<LessonTable, Integer>("earnings"));
        lessonTbl.setItems(data);

    }
}
