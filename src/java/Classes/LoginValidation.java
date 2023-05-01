package Classes;


import Classes.DataBase_Operations;
import Classes.Connect_To_Database;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author anubh
 */

@WebServlet(name="AdminLogin", urlPatterns={"/AdminLogin"})

public class LoginValidation extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
           HttpSession session = request.getSession();
            
            
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            String designation = request.getParameter("designation");
            
            Connection c = Connect_To_Database.connect();
        
            session.setAttribute("Staff",false);
            session.setAttribute("Student",false);
            session.setAttribute("Admin",false);
            
            //Check User exists or not
            
            if(designation.equals("STAFF")){
                if(DataBase_Operations.check_User(c,userID,password,"STAFF")){
                    session.setAttribute("Staff",true);
                    session.setAttribute("staff_id", userID);
                    response.sendRedirect("TeacherDashboard.html");
                }
                else{
                    session.setAttribute("Staff",false);
                    out.println("<script>alert('INVALID USERID or PASSWORD');"
                            + "window.location.replace('AdminLogin.html');</script>");
                    //response.sendRedirect("AdminLogin.html");
                }
            }
            else if(designation.equals("STUDENT")){
                if(DataBase_Operations.check_User(c,userID,password,"STUDENT")){
                    session.setAttribute("Student",true);
                    session.setAttribute("student_id", userID);
                    response.sendRedirect("StudentDashboard.html");
                }
                else{
                    session.setAttribute("Student",false);
                    out.println("<script>alert('INVALID USERID or PASSWORD');"
                            + "window.location.replace('AdminLogin.html');</script>");
                }
            }
            else if(designation.equals("ADMIN")){
                if(DataBase_Operations.check_User(c,userID,password,"ADMIN")){
                    session.setAttribute("Admin",true);
                    session.setAttribute("admin_id", userID);
                    response.sendRedirect("AdminDashboard.html");
                }
                else{
                    session.setAttribute("Admin",false);
                    out.println("<script>alert('INVALID USERID or PASSWORD');"
                            + "window.location.replace('AdminLogin.html');</script>");
                    //response.sendRedirect("AdminLogin.html");
                }
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginValidation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginValidation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
