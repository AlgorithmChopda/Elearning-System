package Classes;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBase_Operations {
    public static boolean check_User(Connection c , String userID, String  password, String tablename) throws SQLException{
        String sql;
        
        if(tablename.equalsIgnoreCase("student")){
            sql = "select * from "+tablename+" where userid=? and password =? and del = 1";
        }
        else{
            sql = "select * from "+tablename+" where userid=? and password =?";
        }
        
        
        
        PreparedStatement pd = c.prepareStatement(sql);
        pd.setString(1, userID);
        pd.setString(2, password);
        
        ResultSet rs = pd.executeQuery();
        
        return rs.next();        
    }
    
    public static boolean insert_User(Connection c,  String userID, String  password, String tablename) throws SQLException {
        String sql= "insert into "+tablename+" values(?,?)";
        PreparedStatement pd = c.prepareStatement(sql);
        pd.setString(1, userID);
        pd.setString(2, password);
        
        int val = pd.executeUpdate();
        
        if(val > 0)
            return true;
        else
            return false;
    }
    
    public static void getStudentData(Connection c) throws SQLException
    {
        ArrayList<Student_Class> ar = new ArrayList<Student_Class>();
       
        PreparedStatement pd = c.prepareStatement("select * from student");
        ResultSet rs = pd.executeQuery();
        
        while(rs.next()){
            ar.add(new Student_Class(rs.getString("userid"),rs.getString("password"), rs.getString("name"),
            rs.getString("rollno"), rs.getString("class"), rs.getString("division"),rs.getString("phoneno")));
        }
        
        for(Student_Class a : ar){
            System.out.print("\n name : "+a.name+" rollno : "+a.rollno);
        }
        
    }
}
