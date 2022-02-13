package Model;

import javafx.beans.property.*;

public class LessonTable{
    private final SimpleIntegerProperty lessonId = new SimpleIntegerProperty();
    private final SimpleStringProperty student = new SimpleStringProperty();
    private final SimpleStringProperty date =  new SimpleStringProperty();
    private final SimpleIntegerProperty rate = new SimpleIntegerProperty();
    private final SimpleIntegerProperty length = new SimpleIntegerProperty();
    private final SimpleStringProperty subject = new SimpleStringProperty();
    private final SimpleStringProperty paymentType = new SimpleStringProperty();
    private final SimpleDoubleProperty earnings = new SimpleDoubleProperty();
    private final SimpleBooleanProperty paid = new SimpleBooleanProperty();

    public LessonTable(int lessonId, String student, String date, int rate, int length, String subject, String paymentType, double earnings, boolean paid) {
        setLessonId(lessonId);
        setStudent(student);
        setDate(date);
        setRate(rate);
        setLength(length);
        setSubject(subject);
        setPaymentType(paymentType);
        setEarnings(earnings);
        setPaid(paid);
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

    public String getStudent() {
        return student.get();
    }

    public SimpleStringProperty studentProperty() {
        return student;
    }

    public void setStudent(String student) {
        this.student.set(student);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public int getRate() {
        return rate.get();
    }

    public SimpleIntegerProperty rateProperty() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate.set(rate);
    }

    public int getLength() {
        return length.get();
    }

    public SimpleIntegerProperty lengthProperty() {
        return length;
    }

    public void setLength(int length) {
        this.length.set(length);
    }

    public String getSubject() {
        return subject.get();
    }

    public SimpleStringProperty subjectProperty() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public String getPaymentType() {
        return paymentType.get();
    }

    public SimpleStringProperty paymentTypeProperty() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType.set(paymentType);
    }

    public double getEarnings() {
        return earnings.get();
    }

    public SimpleDoubleProperty earningsProperty() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings.set(earnings);
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
}
