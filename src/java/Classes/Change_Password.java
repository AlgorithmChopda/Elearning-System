/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author anubh
 */
public class Change_Password extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            
            Connection c = Classes.Connect_To_Database.connect();
            
            
            String temp[] =  request.getParameter("userid").split(" ");
            String user = temp[0];
            String id = temp[3];
            String pass =  request.getParameter("password");
            String cpass =  request.getParameter("cpassword");
            if(cpass.equals(pass))
            {
            
            String table_name = "";
            
            if(user.equalsIgnoreCase("Student"))
            {
                table_name = "student";
            }
                else if (user.equalsIgnoreCase("Staff"))
                {
                    table_name="staff";
                }
                else if (user.equalsIgnoreCase("Admin"))
                {
                    table_name="admin";
                }
            String sql = "update "+table_name+" set password=? where userid=?";
            
            PreparedStatement pd = c.prepareStatement(sql);
            pd.setString(1, pass);
            pd.setString(2, id);
            
            pd.execute();
            
            /* TODO output your page here. You may use following sample code. */
            
            
            if(user.equalsIgnoreCase("Student"))
            {
                out.println("<script>alert('Your Password has been changed...');"
                        + "window.location.replace('./StudentDashboard.html');"
                        + "</script>");
            }
            else if(user.equalsIgnoreCase("Staff"))
            {
                out.println("<script>alert('Your Password has been changed...');"
                        + "window.location.replace('./TeacherDashboard.html');"
                        + "</script>");
            }
            else if(user.equalsIgnoreCase("Admin"))
            {
                out.println("<script>alert('Your Password has been changed...');"
                        + "window.location.replace('./AdminDashboard.html');"
                        + "</script>");
            }
            
        
        }
            else if(user.equalsIgnoreCase("Student"))
            {
                out.println("<script>alert('Password does not match...');"
                        + "window.location.replace('./Student_Change_Password');"
                        + "</script>");
            }
            else if(user.equalsIgnoreCase("Staff"))
            {
                out.println("<script>alert('Password does not match...');"
                        + "window.location.replace('./Staff_Change_Password');"
                        + "</script>");
            }
            else if(user.equalsIgnoreCase("Admin"))
            {
                out.println("<script>alert('Password does not match...');"
                        + "window.location.replace('./Admin_Change_Password');"
                        + "</script>");
            }
        
    }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Change_Password.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Change_Password.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Change_Password.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Change_Password.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
