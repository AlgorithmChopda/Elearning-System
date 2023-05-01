/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import static Classes.Connect_To_Database.c;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author anubh
 */
public class Testing {
    public static void main(String args[]) throws ClassNotFoundException, SQLException{
        
        Connection c = null;
    
       Class.forName("com.mysql.cj.jdbc.Driver");
       System.out.println("driver ");
       c = DriverManager.getConnection("jdbc:mysql://localhost:3307/anubhav","root", "Mysql@12345");     
       System.out.println("connection");
       
        Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("select * from abc");
                
                while(rs.next())
                {
                    System.out.println("name : "+rs.getString("name")+" rollno : "+rs.getString("rollno"));
                }
               
                
               //st.execute("insert into abc values('data', 123);");
    }
}
