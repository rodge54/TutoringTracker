package DataAccess;

import java.sql.SQLException;
import java.sql.Statement;

public class AllDb {
    public static final String schema = "tutoring_tracker.";

    public static void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
