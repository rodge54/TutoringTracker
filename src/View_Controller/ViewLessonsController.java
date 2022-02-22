package View_Controller;

import DataAccess.LessonDb;
import Model.Lesson;
import Model.Student;
import Utils.CustomAlerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ViewLessonsController extends Controller implements Initializable {
    @FXML public TableColumn<Lesson, String> studentCol;
    @FXML public TableColumn<Lesson, String> dateCol;
    @FXML public TableColumn<Lesson, Integer> rateCol;
    @FXML public TableColumn<Lesson, Integer> lengthCol;
    @FXML public TableColumn<Lesson, String> subjectCol;
    @FXML public TableColumn<Lesson, String> paymentTypeCol;
    @FXML public TableColumn<Lesson, Integer> earningsCol;
    @FXML public TableColumn<Lesson, Boolean> paidCol;
    @FXML public TableView<Lesson> lessonTbl;
    @FXML public ComboBox<Integer> yearCb;
    @FXML public ComboBox<String> monthCb;
    @FXML public Label earningsLbl;
    @FXML public Label hoursLbl;

    private ObservableList<Lesson> data = FXCollections.observableArrayList();

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

        data = LessonDb.getFilteredLessons(LocalDateTime.now().getMonthValue(), LocalDateTime.now().getYear());

        // TODO: change table to accept lesson object
        studentCol.setCellValueFactory(new PropertyValueFactory<Lesson, String>("student"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Lesson, String>("date"));
        rateCol.setCellValueFactory(new PropertyValueFactory<Lesson, Integer>("hourlyRate"));
        lengthCol.setCellValueFactory(new PropertyValueFactory<Lesson, Integer>("lessonLength"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<Lesson, String>("subject"));
        paymentTypeCol.setCellValueFactory(new PropertyValueFactory<Lesson, String>("paymentType"));
        earningsCol.setCellValueFactory(new PropertyValueFactory<Lesson, Integer>("earnings"));
        paidCol.setCellValueFactory(new PropertyValueFactory<Lesson, Boolean>("paid"));
        lessonTbl.setRowFactory(lessonTbl -> new TableRow<Lesson>()
        {
            @Override
            protected void updateItem(Lesson lessonRow, boolean empty)
            {
                super.updateItem(lessonRow, empty);

                if (lessonRow != null )
                {
                    if (!lessonRow.isPaid())
                    {
                        getStyleClass().add("highlight-unpaid");
                    }
                }
            }
        });
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

        double totalEarnings = data.stream().mapToDouble(Lesson::getEarnings).sum();
        stringTotal = "Total Earnings: $" + totalEarnings;
        return stringTotal;
    }

    private String calculateTotalHours(){
        String stringTotal;

        double totalEarnings = data.stream().mapToDouble(Lesson::getLessonLength).sum();
        stringTotal = String.format("Total Hours: %4.2f", totalEarnings/60);
        return stringTotal;
    }

    public void onMarkPaidBtnPress(ActionEvent actionEvent) {
        Lesson selectedItem = lessonTbl.getSelectionModel().getSelectedItem();
        LessonDb.markPaid(selectedItem);
        data.stream().filter(obj->obj.getLessonId()==selectedItem.getLessonId())
                .findFirst()
                .ifPresent(o->o.setPaid(true));
        lessonTbl.setItems(data);
        lessonTbl.refresh();
    }

    public void onDeleteBtnPress(ActionEvent actionEvent) {
        Lesson selectedItem = lessonTbl.getSelectionModel().getSelectedItem();
        boolean success = LessonDb.deleteLesson(selectedItem);
        Predicate<Lesson> equalsId = i -> (i.getLessonId() == selectedItem.getLessonId());
        if (success) {
            for (Lesson lesson:data) {
                if (lesson.getLessonId() == selectedItem.getLessonId()){
                    data.remove(lesson);
                    lessonTbl.setItems(data);
                    lessonTbl.refresh();
                }
            }
        }
    }

    public void onEditButtonPress(ActionEvent actionEvent) {
    }
}
