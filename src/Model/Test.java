package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Test {
    private SimpleStringProperty name;
//    private final SimpleIntegerProperty age = new SimpleIntegerProperty();

    public Test(String name) {
        this.name = new SimpleStringProperty(name);
//        setAge(age);
    }

    public String getName() {
        return name.get();
    }

//    public StringProperty nameProperty() {
//        return fname;
//    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

//    public int getAge() {
//        return age.get();
//    }

//    public SimpleIntegerProperty ageProperty() {
//        return age;
//    }

//    public void setAge(int age) {
//        this.age.set(age);
//    }
}
