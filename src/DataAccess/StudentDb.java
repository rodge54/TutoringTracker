package DataAccess;

import Model.Lesson;
import Model.Student;
import Model.Student;
import Utils.SQLDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDb extends AllDb{
    private static final ObservableList<Student> students = FXCollections.observableArrayList();

    public static void setStudents() throws SQLException {
        students.clear();
        Statement statement = SQLDatabase.getConnection().createStatement();

        ResultSet results = statement.executeQuery("SELECT * FROM student");
        while (results.next()){
            int id = results.getInt("student_id");
            String name = results.getString("name");
            String phoneNumber = results.getString("phone_number");
            String email = results.getString("email");
            int timezoneId = results.getInt("timezone_id");
            students.add(new Student(id, name, phoneNumber, email, timezoneId));
        }
    }
    public static ObservableList<Student> getAllStudents() {
        return students;
    }

    public static boolean addStudent(Student student){
        String query = "INSERT INTO student(name, phone_number, email, timezone_id)" +
                "VALUES(?,?,?,?);";
        PreparedStatement ps = null;
        boolean success = false;
        try {
            ps = SQLDatabase.getConnection().prepareStatement(query);
            ps.setString(1, student.getName());
            ps.setString(2, student.getPhoneNumber());
            ps.setString(3, student.getEmail());
            ps.setInt(4, student.getTimeZoneId());
            int numRowsInserted = ps.executeUpdate();
            System.out.println(numRowsInserted + " rows inserted into lesson table.");
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
        }
        return success;
    }

}
