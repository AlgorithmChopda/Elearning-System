/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Student;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author anubh
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class Save_Upload_Assignment extends HttpServlet {

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
            HttpSession session = request.getSession();

            String file_id = request.getParameter("file_id");
            System.out.println(file_id);
            out.println("<script>alert(" + file_id + ");</script>");
            String student_id = (String) session.getAttribute("student_id");
            String sql = "select * from student where userid=?";
            PreparedStatement pd = c.prepareStatement(sql);
            pd.setString(1, student_id);

            ResultSet rs = pd.executeQuery();
            rs.next();
            String student_name = rs.getString("name");
            String roll_no = rs.getString("rollno");

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String d = (formatter.format(date));

            pd = c.prepareStatement("select * from assignment_upload where assign_id=?");

            pd.setInt(1, Integer.parseInt(file_id));
            rs = pd.executeQuery();
            rs.next();
            String assign_name = rs.getString("assign_name");

           //     String name = file.getSubmittedFileName();
            Part file = request.getPart("file");
            System.out.println(file.getSize());

            String query = "";
            pd = c.prepareStatement("select * from student_assignment_uploaded where rollno=? and assign_id = ? ");
            pd.setString(1, roll_no);
            pd.setString(2, file_id);
            rs = pd.executeQuery();

            if (rs.next()) {   // student record found - update it.
                pd = c.prepareStatement("update student_assignment_uploaded set uploaded_date=? where rollno=? and assign_id=?");
                pd.setString(1, d);
                pd.setString(2, roll_no);
                pd.setString(3, file_id);

                pd.execute();
            } else {       // student record not found - insert 

                pd = c.prepareStatement("insert into student_assignment_uploaded values(?,?,?,?,?,-1)");
                pd.setString(1, student_name);
                pd.setString(2, roll_no);
                pd.setString(3, d);
                pd.setString(4, file_id);
                pd.setString(5, assign_name);

                pd.execute();

            }
            file.write("\\Assignment_Uploaded\\" + file_id + "_" + assign_name + "\\" + student_name + "_" + assign_name);

            out.println("<script>alert('Assignment Uploaded Successfully..');"
                    + "window.location.replace('./Student_Display_Assignment');"
                    + "</script>");

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
            Logger.getLogger(Save_Upload_Assignment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Save_Upload_Assignment.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Save_Upload_Assignment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Save_Upload_Assignment.class.getName()).log(Level.SEVERE, null, ex);
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
