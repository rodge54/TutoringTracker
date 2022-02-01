package Utils;

import java.sql.*;

public class SQLDatabase {
    //private static final String url = "jdbc:mysql://localhost:3306/tutoringtrackerdb";
    //private static final String User_Name = "root";
    //WINDOWS LOCAL DB
//    private static final String password = "1234";
    //MAC LOCAL DB
    //private static final String password = "12345678";
    public static Connection conn;

    // Connect to Database
    public static void connect() {
        try {
            //AZURE CONNECTION
            String password = "NotMegatron1234!";
            String connectionString = "jdbc:sqlserver://robserver54.database.windows.net:1433;database=megatron;user=azureuser@robserver54;password="+password+";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            conn = DriverManager.getConnection(connectionString);
            System.out.println("Connected to SQL Server Database");
            //LOCAL CONNECTION
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //conn = DriverManager.getConnection(url, User_Name, password);
            //System.out.println("Connected to MySQL Database");
            //} catch (ClassNotFoundException e) {
            // System.out.println("Class Not Found " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    // Close Database Connection
    public static void disconnect() {
        try {
            conn.close();
            System.out.println("Disconnected From SQL Server Database");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    // Returns Database Connection
    public static Connection getConnection() {
        return conn;
    }
}

