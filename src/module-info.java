module TutoringTracker {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.java;

    opens View_Controller to javafx.fxml;
    opens Model to javafx.fxml;

    exports View_Controller;
    exports Model;
}