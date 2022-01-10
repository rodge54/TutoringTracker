package DataAccess;

import Model.Lesson;
import Model.LessonTable;
import Utils.SQLDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

public class LessonDb extends AllDb{

    public static boolean addLesson(Lesson lesson){
        String query = "INSERT INTO lesson(date, hourly_rate, lesson_length, subject_id, payment_type_id, student_id)" +
                "VALUES(?,?,?,?,?,?);";
        PreparedStatement ps = null;
        boolean success = false;
        try {
            ps = SQLDatabase.getConnection().prepareStatement(query);
            ps.setString(1, lesson.getDate());
            ps.setInt(2, lesson.getHourlyRate());
            ps.setInt(3, lesson.getLessonLength());
            ps.setInt(4, lesson.getSubjectId());
            ps.setInt(5, lesson.getPaymentId());
            ps.setInt(6, lesson.getStudentId());
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

    public static ObservableList<LessonTable> getAllLessons() throws SQLException {
        String query = "SELECT student.name, date, hourly_rate, lesson_length, subject.title, payment_type.name FROM lesson \n" +
                "INNER JOIN subject ON subject.subject_id = lesson.subject_id\n" +
                "INNER JOIN student ON student.student_id = lesson.student_id\n" +
                "INNER JOIN payment_type ON payment_type.payment_type_id = lesson.payment_type_id;";
        ObservableList<LessonTable> lessonTables = FXCollections.observableArrayList();

        try {
            Statement statement = SQLDatabase.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
//            lessonTables.add(new LessonTable("Robert", "09/13/1987", 50, 90, "Python", "Wyzant", 75));
            while (rs.next()) {
                int hourlyRate = rs.getInt("hourly_rate");
                int lessonLength = rs.getInt("lesson_length");
                String payType = rs.getString("payment_type.name");

                // TODO: Should be float
                int earnings = hourlyRate * lessonLength;
                earnings = (int) (payType.equals("Wyzant") ?((earnings - (earnings * .25))/60):(earnings/60));
//                int calculatedEarnings = Integer.parseInt(earnings);

                LessonTable customer = new LessonTable(
                        rs.getString("student.name"),
                        rs.getString("date"),
                        hourlyRate,
                        lessonLength,
                        rs.getString("subject.title"),
                        payType,
                        earnings);
                lessonTables.add(customer);
            }

            statement.close();
            return lessonTables;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
}
