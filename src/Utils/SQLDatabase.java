package Utils;

import java.io.*;
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
            //Get password and connection string from DatabaseInfo.txt file - File ignored by gitignore
            File file = new File("src/Utils/DatabaseInfo.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String connectionString = br.readLine();

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
        catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
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

