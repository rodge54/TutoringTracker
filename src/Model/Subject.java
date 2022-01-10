package Model;

public class Subject {
    private int subjectId;
    private String title;

    public Subject(int subjectId, String zone) {
        this.subjectId = subjectId;
        this.title = zone;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}