package View_Controller;

import Model.LessonTable;
import Model.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TestTableController implements Initializable {
    public Button pressBtn;
    @FXML
    private TableColumn<Test, String> nameCol;

    private final ObservableList<Test> data = FXCollections.observableArrayList(new Test("Robert"));
    @FXML
    private TableView<Test> testTbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onPressBtn(ActionEvent event) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

//        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        testTbl.setItems(data);
//        testTbl.getItems().add(data.get(0));
    }
}
