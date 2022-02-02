package DataAccess;

import Model.Timezone;
import Utils.SQLDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TimezoneDb extends AllDb{
    private static final ObservableList<Timezone> timezones = FXCollections.observableArrayList();

    public static void setTimezones() throws SQLException {
        Statement statement = SQLDatabase.getConnection().createStatement();

        ResultSet results = statement.executeQuery("SELECT * FROM "+schema+"timezone");
        while (results.next()){
            int id = results.getInt("timezone_id");
            String zone = results.getString("zone");
            timezones.add(new Timezone(id, zone));
        }
    }
    public static ObservableList<Timezone> getTimezones() {
        return timezones;
    }
}
