package Classes.Staff;

import java.io.IOException;
import java.lang.System;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class Save_Test_Questions extends HttpServlet {

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
        try (PrintWriter out1 = response.getWriter()) {
            
            Connection c = Classes.Connect_To_Database.connect();
            PreparedStatement pd = null;
            
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select * from test_headers order by test_id desc");
            
            rs.next();
            
            String t_id = Integer.toString(rs.getInt("test_id"));        // id of the test
            
            int n = Integer.parseInt(request.getParameter("hid"));  // no of questions
            
            for(int i = 1 ; i <= n ; i++){
                
                String question_name = request.getParameter("a"+i);
                
               
                
                //question_name=question_name.replaceAll("\\r", " ").replaceAll("\\n", " /$$ ");
                question_name=question_name.replace(System.getProperty("line.separator"), "12qpmzx9");
                
                String option_a = request.getParameter("a"+i+"a");
                String option_b = request.getParameter("a"+i+"b");
                String option_c = request.getParameter("a"+i+"c");
                String option_d = request.getParameter("a"+i+"d");
                
                String option_answer = request.getParameter("a"+i+"z");
                 String marks = request.getParameter("a"+i+"m");
                
                
                pd = c.prepareStatement("insert into test_questions values(?,?,?,?,?,?,?,?) ");
                
                pd.setString(1, question_name);
                
                pd.setString(2, option_a);
                pd.setString(3, option_b);
                pd.setString(4, option_c);
                pd.setString(5, option_d);
                
                pd.setString(6, option_answer);
                
                pd.setString(7, t_id);
                
                pd.setString(8, marks);


                
                
                pd.execute();
                
            }
            
            out1.println("<script>"
                + "alert('Your test has been created...');"
                + "window.location.replace('./Display_Uploaded_Tests');"
                + " </script> ");
            
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
            Logger.getLogger(Save_Test_Questions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Save_Test_Questions.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Save_Test_Questions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Save_Test_Questions.class.getName()).log(Level.SEVERE, null, ex);
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
