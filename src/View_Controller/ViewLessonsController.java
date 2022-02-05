package View_Controller;

import DataAccess.LessonDb;
import Model.LessonTable;
import Utils.CustomAlerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    @FXML public ComboBox<Integer> yearCb;
    @FXML public ComboBox<String> monthCb;
    @FXML public Label earningsLbl;
    @FXML public Label hoursLbl;
    private ObservableList<LessonTable> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupMonthYearCbs();
        setupLessonTable();
    }

    // SETUP ACTIONS
    private void setupMonthYearCbs() {
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April",
                "May", "June", "July", "August", "September", "October", "November", "December");
        ObservableList<Integer> years = FXCollections.observableArrayList();
        for (int i = 2019; i <= 2022; i++){
            years.add(i);
        }
        monthCb.setItems(months);
        yearCb.setItems(years);
    }

    private void setupLessonTable() {
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
        earningsLbl.setText(calculateTotalEarnings());
        hoursLbl.setText(calculateTotalHours());
        lessonTbl.setItems(data);
    }

    // BUTTON ACTIONS
    public void onSearchBtnPress(ActionEvent actionEvent) {
        int year;
        int month = monthCb.getSelectionModel().getSelectedIndex() + 1;
        // TODO: Add alerts for errors
        try{
            year = yearCb.getValue();
        }
        catch (NullPointerException e){
            CustomAlerts.WarningAlert("Year not selected", "Please select a year");
            return;
        }
        if (month != 0){
            System.out.println(
                    year + month
            );
            data = LessonDb.getFilteredLessons(month, year);
            earningsLbl.setText(calculateTotalEarnings());
            hoursLbl.setText(calculateTotalHours());
            lessonTbl.setItems(data);
        }
        else{
            CustomAlerts.WarningAlert("Month not selected", "Please select a month");
        }
    }

    private String calculateTotalEarnings(){
        String stringTotal;

        double totalEarnings = data.stream().mapToDouble(LessonTable::getEarnings).sum();
        stringTotal = "Total Earnings: $" + totalEarnings;
        return stringTotal;
    }

    private String calculateTotalHours(){
        String stringTotal;

        double totalEarnings = data.stream().mapToDouble(LessonTable::getLength).sum();
        stringTotal = String.format("Total Hours: %4.2f", totalEarnings/60);
        return stringTotal;
    }


}
