package dbhelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    protected static Connection connection;
    public static final String USER = "sa";
    public static final String PASS = "123456";
    public static final String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=Attendance_DB_Final";

    public DBContext() {
        connect();
    }

    public void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Connect Successfully");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        if (connection != null) {
            try {
                System.out.println("close");
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        DBContext dBC = new DBContext();
    }
}
