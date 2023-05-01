package Classes;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect_To_Database {
    static Connection c = null;
    
    public static Connection connect() throws ClassNotFoundException, SQLException{
       if(c != null)
         return c; 
       Class.forName("oracle.jdbc.driver.OracleDriver");
       c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "SYS AS SYSDBA", "Anubhav1234567");     
       
       return c;
    }
}
