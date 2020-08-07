package includes;
import java.sql.*;

public class DatabaseConnection {
    
    String dbURL = "jdbc:mysql://localhost:3306/magazin";
    String username = "root";
    String password = "";

    public Connection conn;
    private static DatabaseConnection dbc;
    private DatabaseConnection()
    {
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public static DatabaseConnection getDatabaseConnection()
    {
        if(dbc == null)
        {
            dbc = new DatabaseConnection();
        }
        return dbc;
    }
	
    public Connection getConnection()
    {
        return conn;
    }

}
