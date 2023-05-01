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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author anubh
 */
public class Display_Online_Video extends HttpServlet {

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

            String student_id = (String) session.getAttribute("student_id");
            String sql = "select * from student where userid=?";
            PreparedStatement pd = c.prepareStatement(sql);
            pd.setString(1, student_id);

            ResultSet rs = pd.executeQuery();
            rs.next();
            String cla = rs.getString("class");

            sql = "select * from online_videos where class=? or class='0'";
            pd = c.prepareStatement(sql);
            pd.setString(1, cla);

            rs = pd.executeQuery();

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
            out.println("<form id='form' method='post' action='StartVideo'><table class='styled-table'>");
            out.println("<div class='form-group pull-right'>"
                    + "<input type='text' id='l' name='l' value='default' hidden> </lable> <br><br>"
                    + "<input type='text' class='search form-control' placeholder='Search...'>"
                    + "</div>"
                    + "<span class='counter pull-right'></span>"
                    + "<table id='tab' class='table table-hover table-bordered results'>"
                    + "<thead>"
                    + "<tr>"
                    + "      <th class=\"th-sm\"  style='font-weight:bold'>VIDEO TITLE\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>SUBJECT\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>UPLOADED BY\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>WATCH NOW\n"
                    + "      </th>\n"
                    + "      <tr class=\"warning no-result\">\n"
                    + "      <td colspan=\"4\"><i class=\"fa fa-warning\"></i> No result</td>\n"
                    + "    </tr>\n"
                    + "  </thead>\n");

            while (rs.next()) {
                String title = rs.getString("video_name");
                String subject = rs.getString("video_subject");
                String staff = rs.getString("staff_id");
                String link = rs.getString("video_link");

                out.println("<tbody><tr><td style='font-weight:normal'>" + title + "</td><td  style='font-weight:normal'>" + subject + "</td><td style='font-weight:normal'>" + staff + "</td>"
                        + "<td><button type='submit' id='" + link + "," + subject + "," + title + "' name='" + link + "'  onClick='start(this)' class='btn btn btn-outline-dark btn-rounded btn-sm my-0'><i class='fab fa-youtube fa-2x'></i> Watch Now</button></td>"
                        + "</tr></tbody>");
            }

            out.println("<script>"
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
                    + " function start(btn){"
                    + " document.getElementById('l').value = btn.id; "
                    + "} "
                    + "</script>");
            out.println("</body>");
            out.println("</html>");

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
            Logger.getLogger(Display_Online_Video.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Display_Online_Video.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Display_Online_Video.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Display_Online_Video.class.getName()).log(Level.SEVERE, null, ex);
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
