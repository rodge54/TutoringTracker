package Model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Lesson {

    private int lessonId;
    private LocalDate date;
    private int hourlyRate;
    private int lessonLength;
    private int subjectId;
    private int paymentId;
    private int studentId;
    private boolean paid;

    public Lesson(LocalDate date, int hourlyRate, int lessonLength, int subjectId, int paymentId, int studentId, boolean paid) {
        this.date = date;
        this.hourlyRate = hourlyRate;
        this.lessonLength = lessonLength;
        this.subjectId = subjectId;
        this.paymentId = paymentId;
        this.studentId = studentId;
        this.paid = paid;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getLessonLength() {
        return lessonLength;
    }

    public void setLessonLength(int lessonLength) {
        this.lessonLength = lessonLength;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
