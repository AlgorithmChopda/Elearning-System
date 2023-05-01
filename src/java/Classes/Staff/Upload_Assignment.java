/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Staff;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class Upload_Assignment extends HttpServlet {
    
    private void temp(Connection c) throws SQLException{
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("select * from assignment_upload order by assign_id desc");

        while(rs.next()){
            System.out.println(rs.getString("assign_id")+" : "+rs.getString("assign_name"));
        }
        
    }
    private int Generate_Random_Test_ID(Connection c) throws SQLException {

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("select * from assignment_upload order by assign_id desc");

        if (rs.next()) {   // if record exists in table
            
            int id = rs.getInt("assign_id");
            System.out.println("before : "+id);
            id++;
             System.out.println("after : "+id);
            return id;
        }
        return 1;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            Connection c = Classes.Connect_To_Database.connect();
            HttpSession session = request.getSession();
            String staff_id = (String) session.getAttribute("staff_id");
            temp(c);
            int id = Generate_Random_Test_ID(c);
            
            String deadline = request.getParameter("deadline");
            String deadlinearr[]= deadline.split("-");
            deadline= deadlinearr[2]+"/"+deadlinearr[1]+"/"+deadlinearr[0];
            
            String cla = request.getParameter("class");
            String marks = request.getParameter("marks");

            String sql = "select * from staff where userid=?";
            PreparedStatement pd = c.prepareStatement(sql);
            pd.setString(1, staff_id);
            pd.execute();

            ResultSet rs = pd.executeQuery();
            rs.next();
            String staff_name = rs.getString("name");

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String d = (formatter.format(date));

            Part file = request.getPart("file");
            String name = file.getName();
            String nwe = name.substring(0,name.lastIndexOf("."));
            sql = "insert into assignment_upload values(?,?,?,?,?,?,?)";
            pd = c.prepareStatement(sql);
            pd.setInt(1, id);
            pd.setString(2, name);
            pd.setString(3, staff_name);
            pd.setString(4, d);
            pd.setString(5, deadline);
            pd.setString(6, cla);
            pd.setString(7, marks);

            pd.execute();
            
            //nwe name without extension
            
            System.out.println(nwe);
            System.out.println(name);

            // creating directory
            File filed = new File("..\\work\\Catalina\\localhost\\Elearning\\Assignment_Uploaded\\" + id + "_" + name);
            filed.mkdirs();

            // saving file in assignment folder
            file.write("\\Assignment_Uploaded\\Assignments\\" + id + "_" +name);

            out.println("<script>"
                    + "alert('File Uploaded Successfully');"
                    + "</script>");

            out.println("<script>"
                    + "window.location.replace('./Upload_Assignment.html');"
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
            Logger.getLogger(Upload_Assignment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Upload_Assignment.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Upload_Assignment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Upload_Assignment.class.getName()).log(Level.SEVERE, null, ex);
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
