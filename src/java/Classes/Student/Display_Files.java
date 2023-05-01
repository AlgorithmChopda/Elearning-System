/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Student;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author anubh
 */
public class Display_Files extends HttpServlet {

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

        FileInputStream fileInputStream = null;
        OutputStream responseOutputStream = null;
        String filePath = "..\\work\\Catalina\\localhost\\Elearning\\Files_Uploaded\\";
   
            
        
        try (PrintWriter out = response.getWriter()) {

            // front end code
            out.println("<!DOCTYPE html>");
            out.println("<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.19.1/css/mdb.min.css\" rel=\"stylesheet\">"
                    + "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css\" rel=\"stylesheet\">"
                    + "<link href=\"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap\" rel=\"stylesheet\">"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.6.0/mdb.min.css\" rel=\"stylesheet\" >");
            out.print(" <link rel='stylesheet' type='text/css' href='DisplayStudentData.css'>");

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Display_Online_Video</title>");
            out.println("</head>");
            out.println("<body>");

            Connection c = Classes.Connect_To_Database.connect();
            HttpSession session = request.getSession();

            // fetching the class of student
            String student_id = (String) session.getAttribute("student_id");
            String sql = "select * from student where userid=?";
            PreparedStatement pd = c.prepareStatement(sql);
            pd.setString(1, student_id);

            ResultSet rs = pd.executeQuery();
            rs.next();
            String cla = rs.getString("class");

            // displaying the files
            pd = c.prepareStatement("select * from file_upload where class = ? OR class = '0' ");
            pd.setString(1, cla);
            rs = pd.executeQuery();

            // table code
            out.println("<form id='form' method='post' action='Download_Files'><table class='styled-table'>");
            out.println("<div class='form-group pull-right'>"
                    + "<input type='text' id='l' name='l' value='default' hidden> </lable> <br><br>"
                    + "<input type='text' class='search form-control' placeholder='Search...'>"
                    + "</div>"
                    + "<span class='counter pull-right'></span>"
                    + "<table id='tab' class='table table-hover table-bordered results'>"
                    + "<thead>"
                    + "<tr>"
                    + "      <th class=\"th-sm\">File ID\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\">File Name\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\">Uploaded by\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\">Download\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\">File Size\n"
                    + "      </th>\n"
                    + "      <tr class=\"warning no-result\">\n"
                    + "      <td colspan=\"4\"><i class=\"fa fa-warning\"></i> No result</td>\n"
                    + "    </tr>\n"
                    + "  </thead>\n");

            while (rs.next()) {

                String fileid = rs.getString("file_id");
                String filename = rs.getString("file_name");
                String uploadedby = rs.getString("uploader_name");
                String size = rs.getString("file_size");
                
                String cl = rs.getString("class");
                if(cl.equals("0"))
                    cl = "All";
                
                String temp = filePath+cl+"\\"+fileid+"_"+filename;
                    
                float s = Float.parseFloat(size) / 1024;

                out.println("<tbody><tr><td>" + fileid + "</td><td>" + filename + "</td><td>" + uploadedby + "</td>"
                        + "<td><button type='submit' id='" + temp + "' name='" + temp + "'  onClick='download(this)' class='btn btn btn-outline-dark btn-rounded btn-sm my-0'><i class='fa fa-download fa-2x'></i> Download</button></td>"
                        + "<td>" + String.format("%.2f", s) + " KB</td></tr></tbody>");
            }

           
            out.println("</table></form></body></html><script>"
                    + "$(document).ready(function() {\n"
                    + "  $(\".search\").keyup(function () {\n"
                    + "    var searchTerm = $(\".search\").val();\n"
                    + "    var listItem = $('.results tbody').children('tr');\n"
                    + "    var searchSplit = searchTerm.replace(/ /g, \"'):containsi('\")\n"
                    + "    \n"
                    + "  $.extend($.expr[':'], {'containsi': function(elem, i, match, array){\n"
                    + "        return (elem.textContent || elem.innerText || '').toLowerCase().indexOf((match[3] || \"\").toLowerCase()) >= 0;\n"
                    + "    }\n"
                    + "  });\n"
                    + "    \n"
                    + "  $(\".results tbody tr\").not(\":containsi('\" + searchSplit + \"')\").each(function(e){\n"
                    + "    $(this).attr('visible','false');\n"
                    + "  });\n"
                    + "\n"
                    + "  $(\".results tbody tr:containsi('\" + searchSplit + \"')\").each(function(e){\n"
                    + "    $(this).attr('visible','true');\n"
                    + "  });\n"
                    + "\n"
                    + "  var jobCount = $('.results tbody tr[visible=\"true\"]').length;\n"
                    + "    $('.counter').text(jobCount + ' item');\n"
                    + "\n"
                    + "  if(jobCount == '0') {$('.no-result').show();}\n"
                    + "    else {$('.no-result').hide();}\n"
                    + "		  });\n"
                    + "$(window).keydown(function(event){\n"
                    + "    if(event.keyCode == 13) {\n"
                    + "      event.preventDefault();\n"
                    + "      return false;\n"
                    + "    }\n"
                    + "  });"
                    + "});"
                    + " function download(btn){"
                    + " document.getElementById('l').value = btn.id; "
                    + "} "
                    + "</script>");
        } catch (Exception ex) {
            ex.printStackTrace();
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
            Logger.getLogger(Download_Files.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Download_Files.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Download_Files.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Download_Files.class.getName()).log(Level.SEVERE, null, ex);
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
