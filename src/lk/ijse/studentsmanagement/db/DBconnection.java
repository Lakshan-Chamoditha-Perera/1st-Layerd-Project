package lk.ijse.studentsmanagement.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBconnection {
    private static DBconnection dbConnection;
    private final Connection connection;
    private DBconnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "#Shan0503");
    }
    public static DBconnection getInstance() throws SQLException, ClassNotFoundException {
        return (dbConnection==null) ? dbConnection = new DBconnection(): dbConnection;
    }
    public Connection getConnection(){return connection;}
}