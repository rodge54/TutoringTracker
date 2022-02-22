package DataAccess;

import Model.*;
import Utils.SQLDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LessonDb extends AllDb{

    /**
     *
     * @param lesson
     * @return -
     */
    public static boolean addLesson(Lesson lesson){
        String query = "INSERT INTO "+schema+"lesson(date, hourly_rate, lesson_length, subject_id, payment_type_id, student_id, paid) " +
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

    public static boolean markPaid(Lesson lesson){
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
    public static ObservableList<Lesson> getAllLessons() throws SQLException {
        String query = "SELECT lesson_id, date, hourly_rate, lesson_length, lesson.subject_id, lesson.payment_type_id, lesson.student_id, paid, title, payment_type.name AS payment_type_name, student.name AS student_name, phone_number, email, timezone_id " +
                "FROM "+schema+"lesson \n" +
                "INNER JOIN "+schema+"subject ON "+schema+"subject.subject_id = "+schema+"lesson.subject_id\n" +
                "INNER JOIN "+schema+"student ON "+schema+"student.student_id = "+schema+"lesson.student_id\n" +
                "INNER JOIN "+schema+"payment_type ON "+schema+"payment_type.payment_type_id = "+schema+"lesson.payment_type_id;";
        try {
            return buildLessonList(query);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public static ObservableList<Lesson> getFilteredLessons(int month, int year) {
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

        String query = "SELECT lesson_id, date, hourly_rate, lesson_length, lesson.subject_id, lesson.payment_type_id, lesson.student_id, paid, title, payment_type.name AS payment_type_name, student.name AS student_name, phone_number, email, timezone_id " +
                "FROM "+schema+"lesson \n" +
                "INNER JOIN "+schema+"subject ON "+schema+"subject.subject_id = "+schema+"lesson.subject_id\n" +
                "INNER JOIN "+schema+"student ON "+schema+"student.student_id = "+schema+"lesson.student_id\n" +
                "INNER JOIN "+schema+"payment_type ON "+schema+"payment_type.payment_type_id = "+schema+"lesson.payment_type_id "+
                "WHERE date BETWEEN '"+startDate+"' and '"+endDate+"';";

        try {
            return buildLessonList(query);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
    private static ObservableList<Lesson> buildLessonList(String query) throws SQLException {
        Statement statement = SQLDatabase.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        ObservableList<Lesson> lessonTables = FXCollections.observableArrayList();
        while (rs.next()) {
            int lessonId = rs.getInt("lesson_id");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            formatter = formatter.withLocale( new Locale("en"));  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
            String stringDate = rs.getString("date");
            LocalDate date = LocalDate.parse(stringDate);
            int hourlyRate = rs.getInt("hourly_rate");
            int lessonLength = rs.getInt("lesson_length");

            // Student creation
            int studentId = rs.getInt("student_id");
            String name = rs.getString("student_name");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");
            int timeZoneId = rs.getInt("timezone_id");
            Student student = new Student(studentId, name, phoneNumber, email, timeZoneId);
            // Subject creation
            int subjectId = rs.getInt("subject_id");
            String title = rs.getString("title");
            Subject subject = new Subject(subjectId, title);
            // Payment Type creation
            int paymentTypeId = rs.getInt("payment_type_id");
            String paymentTypeName = rs.getString("payment_type_name");
            PaymentType paymentType = new PaymentType(paymentTypeId, paymentTypeName);

            double earnings = hourlyRate * lessonLength;
            earnings = paymentType.getName().equals("Wyzant") ?((earnings - (earnings * .25))/60):(earnings/60);

            // one line
            BigDecimal earningsBd = new BigDecimal(earnings).setScale(2, RoundingMode.HALF_UP);
            // convert BigDecimal back to double
            double earningsRounded = earningsBd.doubleValue();
            boolean paid = rs.getBoolean("paid");

            Lesson lesson = new Lesson(lessonId,
                    date,
                    hourlyRate,
                    lessonLength,
                    subjectId,
                    paymentTypeId,
                    studentId,
                    paid,
                    subject,
                    paymentType,
                    student
            );
            lessonTables.add(lesson);
        }

        statement.close();
        return lessonTables;
    }

    public static boolean deleteLesson(Lesson selectedItem) {
        String query = "DELETE FROM "+schema+"lesson WHERE lesson_id = ?;";
        PreparedStatement ps = null;
        boolean success = false;
        try {
            System.out.println(selectedItem.getStudent().getName());
            System.out.println(selectedItem.getStudentId());
            System.out.println(selectedItem.getLessonId());
            ps = SQLDatabase.getConnection().prepareStatement(query);
            ps.setInt(1, selectedItem.getLessonId());
            int numRowsUpdated = ps.executeUpdate();
            System.out.println(numRowsUpdated + " rows deleted in lesson table.");
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            close(ps);
        }
        return success;
    }
}
