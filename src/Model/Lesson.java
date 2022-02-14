package Model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Lesson {

    private final SimpleIntegerProperty lessonId = new SimpleIntegerProperty();
    private LocalDate date;
    private final SimpleIntegerProperty hourlyRate = new SimpleIntegerProperty();
    private final SimpleIntegerProperty lessonLength = new SimpleIntegerProperty();
    private final SimpleIntegerProperty subjectId = new SimpleIntegerProperty();
    private final SimpleIntegerProperty paymentId = new SimpleIntegerProperty();
    private final SimpleIntegerProperty studentId = new SimpleIntegerProperty();
    private final SimpleBooleanProperty paid = new SimpleBooleanProperty();
    private Subject subject;
    private PaymentType paymentType;
    private Student student;
    // Calculated earnings
    private final SimpleDoubleProperty earnings = new SimpleDoubleProperty();

    public Lesson(int lessonId, LocalDate date, int hourlyRate, int lessonLength, int subjectId, int paymentId, int studentId,
                  boolean paid,Subject subject, PaymentType paymentType, Student student) {
        setLessonId(lessonId);
        this.date = date;
        setHourlyRate(hourlyRate);
        setLessonLength(lessonLength);
        setSubjectId(subjectId);
        setPaymentId(paymentId);
        setStudentId(studentId);
        setPaid(paid);
        this.subject = subject;
        this.paymentType = paymentType;
        this.student = student;
        calculateEarnings();
    }

    public Lesson(LocalDate date, int hourlyRate, int lessonLength, int subjectId, int paymentId, int studentId,
                  boolean paid,Subject subject, PaymentType paymentType, Student student) {
        this.date = date;
        setHourlyRate(hourlyRate);
        setLessonLength(lessonLength);
        setSubjectId(subjectId);
        setPaymentId(paymentId);
        setStudentId(studentId);
        setPaid(paid);
        this.subject = subject;
        this.paymentType = paymentType;
        this.student = student;
        calculateEarnings();
    }

    public int getLessonId() {
        return lessonId.get();
    }

    public SimpleIntegerProperty lessonIdProperty() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId.set(lessonId);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHourlyRate() {
        return hourlyRate.get();
    }

    public SimpleIntegerProperty hourlyRateProperty() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate.set(hourlyRate);
    }

    public int getLessonLength() {
        return lessonLength.get();
    }

    public SimpleIntegerProperty lessonLengthProperty() {
        return lessonLength;
    }

    public void setLessonLength(int lessonLength) {
        this.lessonLength.set(lessonLength);
    }

    public int getSubjectId() {
        return subjectId.get();
    }

    public SimpleIntegerProperty subjectIdProperty() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId.set(subjectId);
    }

    public int getPaymentId() {
        return paymentId.get();
    }

    public SimpleIntegerProperty paymentIdProperty() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId.set(paymentId);
    }

    public int getStudentId() {
        return studentId.get();
    }

    public SimpleIntegerProperty studentIdProperty() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId.set(studentId);
    }

    public boolean isPaid() {
        return paid.get();
    }

    public SimpleBooleanProperty paidProperty() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid.set(paid);
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getEarnings() {
        return earnings.get();
    }

    public SimpleDoubleProperty earningsProperty() {
        return earnings;
    }

    public void setEarnings(double earnings){
        this.earnings.set(earnings);
    }
    public void calculateEarnings() {
        double earnings = getHourlyRate() * getLessonLength();
        earnings = paymentType.getName().equals("Wyzant") ?((earnings - (earnings * .25))/60):(earnings/60);

        // one line
        BigDecimal earningsBd = new BigDecimal(earnings).setScale(2, RoundingMode.HALF_UP);
        // convert BigDecimal back to double
        double earningsRounded = earningsBd.doubleValue();
        setEarnings(earningsRounded);
    }
}
