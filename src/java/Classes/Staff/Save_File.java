/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Staff;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)

/**
 *  String mimeType = context.getMimeType(filePath);
 * @author anubh
 */
public class Save_File extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) 
        {

            
            
            
            Connection c = Classes.Connect_To_Database.connect();
            HttpSession session = request.getSession();
            String staff_id= (String) session.getAttribute("staff_id");
           
            String file_id = request.getParameter("file_id");
            String cla = request.getParameter("class");
            
            
            String sql = "select * from staff where userid=?";
            PreparedStatement pd = c.prepareStatement(sql);
            pd.setString(1, staff_id);
            pd.execute();

            ResultSet rs = pd.executeQuery();
            rs.next();
            String staff_name = rs.getString("name");
            
            Part file = request.getPart("file");
            String filename = file.getName();
            String size = Long.toString(file.getSize());
           
            sql = "insert into file_upload values(?,?,?,?,?)";
            pd = c.prepareStatement(sql);
            pd.setString(1,file_id);
            pd.setString(2,filename);
            pd.setString(3,cla);
            pd.setString(4,size);
            pd.setString(5,staff_name);
            
            
            pd.execute();
           
            
            if(cla.equals("07"))
            {
                file.write("\\Files_Uploaded\\07\\" + file_id +"_"+filename);
                out.println("<script>"
                    + "alert('File Uploaded Successfully');"
                    + "</script>");
            }
            else if(cla.equals("08")){
                    file.write("\\Files_Uploaded\\08\\" + file_id +"_"+filename);
                    out.println("<script>"
                    + "alert('File Uploaded Successfully');"
                    + "</script>");
            }
            else if(cla.equals("09")){
                    file.write("\\Files_Uploaded\\09\\" + file_id +"_"+filename);
                    out.println("<script>"
                    + "alert('File Uploaded Successfully');"
                    + "</script>");
            }
            
            else if(cla.equals("10")){
                    file.write("\\Files_Uploaded\\10\\" + file_id +"_"+filename);
                    out.println("<script>"
                    + "alert('File Uploaded Successfully');"
                    + "</script>");
            }
            else{
                file.write("\\Files_Uploaded\\All\\" + file_id +"_"+filename);
                out.println("<script>"
                    + "alert('File Uploaded Successfully');"
                    + "</script>");
            }
            
            
            out.println("<script>"
                    + "window.location.replace('./Upload_Files');"
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
            Logger.getLogger(Save_File.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Save_File.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Save_File.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Save_File.class.getName()).log(Level.SEVERE, null, ex);
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
