package DataAccess;

import Model.Lesson;
import Model.LessonTable;
import Utils.SQLDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

public class LessonDb extends AllDb{

    /**
     *
     * @param lesson
     * @return -
     */
    public static boolean addLesson(Lesson lesson){
        String query = "INSERT INTO "+schema+"lesson(date, hourly_rate, lesson_length, subject_id, payment_type_id, student_id, paid)" +
                "VALUES(?,?,?,?,?,?,?);";
        PreparedStatement ps = null;
        boolean success = false;
        try {
            ps = SQLDatabase.getConnection().prepareStatement(query);
            ps.setString(1, lesson.getDate().toString());
            ps.setInt(2, lesson.getHourlyRate());
            ps.setInt(3, lesson.getLessonLength());
            ps.setInt(4, lesson.getSubjectId());
            ps.setInt(5, lesson.getPaymentId());
            ps.setInt(6, lesson.getStudentId());
            ps.setBoolean(7, lesson.isPaid());
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

    public static boolean markPaid(LessonTable lesson){
        String query = "UPDATE "+schema+"lesson " +
                "SET paid = 'true' " +
                "WHERE lesson_id = ?;";
        PreparedStatement ps = null;
        boolean success = false;
        try {
            ps = SQLDatabase.getConnection().prepareStatement(query);
            ps.setInt(1, lesson.getLessonId());
            int numRowsUpdated = ps.executeUpdate();
            System.out.println(numRowsUpdated + " rows update in lesson table.");
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
        }
        return success;
    }
    public static ObservableList<LessonTable> getAllLessons() throws SQLException {
        String query = "SELECT lesson_id, student.name, date, hourly_rate, lesson_length, subject.title, "+schema+"payment_type.name AS payment_name, paid " +
                "FROM "+schema+"lesson \n" +
                "INNER JOIN "+schema+"subject ON "+schema+"subject.subject_id = "+schema+"lesson.subject_id\n" +
                "INNER JOIN "+schema+"student ON "+schema+"student.student_id = "+schema+"lesson.student_id\n" +
                "INNER JOIN "+schema+"payment_type ON "+schema+"payment_type.payment_type_id = "+schema+"lesson.payment_type_id;";
//        String query = "SELECT student.name, date, hourly_rate, lesson_length, subject.title, [tutoring].payment_type.name AS payment_name FROM [tutoring].lesson \n" +
//                "INNER JOIN tutoring.subject ON tutoring.subject.subject_id = tutoring.lesson.subject_id\n" +
//                "INNER JOIN tutoring.student ON tutoring.student.student_id = tutoring.lesson.student_id\n" +
//                "INNER JOIN tutoring.payment_type ON tutoring.payment_type.payment_type_id = tutoring.lesson.payment_type_id;";
        ObservableList<LessonTable> lessonTables = FXCollections.observableArrayList();

        try {
            Statement statement = SQLDatabase.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int hourlyRate = rs.getInt("hourly_rate");
                int lessonLength = rs.getInt("lesson_length");
                String payType = rs.getString("payment_name");

                double earnings = hourlyRate * lessonLength;
                earnings = payType.equals("Wyzant") ?((earnings - (earnings * .25))/60):(earnings/60);
                System.out.println(earnings);
                // one line
                BigDecimal earningsBd = new BigDecimal(earnings).setScale(2, RoundingMode.HALF_UP);
                // convert BigDecimal back to double
                double earningsRounded = earningsBd.doubleValue();

                LessonTable customer = new LessonTable(
                        rs.getInt("lesson_id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        hourlyRate,
                        lessonLength,
                        rs.getString("title"),
                        payType,
                        earningsRounded,
                        rs.getBoolean("paid"));
                lessonTables.add(customer);
            }

            statement.close();
            return lessonTables;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public static ObservableList<LessonTable> getFilteredLessons(int month, int year) {
        int day = 31;
        String startDate;
        String endDate;
        //Check for leap year
        if (month == 2 && year % 4 == 0){
            day = 29;
        }
        else if (month == 2){
            day = 28;
        }
        //30 days if month is April(4), June(6), September(9), and November(11)
        else if (month == 4 || month == 6 || month == 9 || month == 11){
            day = 30;
        }
        if (month < 10){
            startDate = year + "-0" + month + "-01";
            endDate = year + "-0" + month + "-" + day;
        }
        else {
            startDate = year + "-" + month + "-01";
            endDate = year + "-" + month + "-" + day;
        }

        System.out.println(day);
        System.out.println(month);
        System.out.println(year);
        String query = "SELECT lesson_id, student.name, date, hourly_rate, lesson_length, subject.title, payment_type.name AS payment_type_name, paid FROM "+schema+"lesson \n" +
                "INNER JOIN "+schema+"subject ON "+schema+"subject.subject_id = "+schema+"lesson.subject_id\n" +
                "INNER JOIN "+schema+"student ON "+schema+"student.student_id = "+schema+"lesson.student_id\n" +
                "INNER JOIN "+schema+"payment_type ON "+schema+"payment_type.payment_type_id = "+schema+"lesson.payment_type_id\n" +
                "WHERE date BETWEEN '"+startDate+"' and '"+endDate+"';";
        ObservableList<LessonTable> lessonTables = FXCollections.observableArrayList();

        try {
            Statement statement = SQLDatabase.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int hourlyRate = rs.getInt("hourly_rate");
                int lessonLength = rs.getInt("lesson_length");
                String payType = rs.getString("payment_type_name");

                double earnings = hourlyRate * lessonLength;
                earnings = payType.equals("Wyzant") ?((earnings - (earnings * .25))/60):(earnings/60);
                // one line
                BigDecimal earningsBd = new BigDecimal(earnings).setScale(2, RoundingMode.HALF_UP);
                System.out.println(earningsBd);
                // convert BigDecimal back to double
                double earningsRounded = earningsBd.doubleValue();

                LessonTable customer = new LessonTable(
                        rs.getInt("lesson_id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        hourlyRate,
                        lessonLength,
                        rs.getString("title"),
                        payType,
                        earningsRounded,
                        rs.getBoolean("paid"));
                lessonTables.add(customer);
            }
            statement.close();
            System.out.println(lessonTables.size());
            return lessonTables;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
}
