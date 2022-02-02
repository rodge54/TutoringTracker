package DataAccess;

import Model.Subject;
import Model.Timezone;
import Utils.SQLDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SubjectDb extends AllDb{
    private static final ObservableList<Subject> subjects = FXCollections.observableArrayList();

    public static void setSubjects() throws SQLException {
        Statement statement = SQLDatabase.getConnection().createStatement();

        ResultSet results = statement.executeQuery("SELECT * FROM "+schema+"subject");
        while (results.next()){
            int id = results.getInt("subject_id");
            String title = results.getString("title");
            subjects.add(new Subject(id, title));
        }
    }
    public static ObservableList<Subject> getSubjects() {
        return subjects;
    }
}
