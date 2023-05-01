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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author anubh
 */
public class Staff_Display_Assignments extends HttpServlet {

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

            out.println("<html><body>"
                    + "<link rel='stylesheet' type='text/css' href='DisplayStudentData.css'>"
                    + "<center><h1></h1></center>"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.19.1/css/mdb.min.css\" rel=\"stylesheet\">"
                    + "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css\" rel=\"stylesheet\">"
                    + "<link href=\"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap\" rel=\"stylesheet\">"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.6.0/mdb.min.css\" rel=\"stylesheet\" >");

            Connection c = Classes.Connect_To_Database.connect();

            HttpSession session = request.getSession();
            String staffid = (String) session.getAttribute("staff_id");

            PreparedStatement pd = c.prepareStatement("select * from staff where userid = ?");
            pd.setString(1, staffid);
            ResultSet rs = pd.executeQuery();
            rs.next();
            String staff_name = rs.getString("name");

            pd = c.prepareStatement("select * from assignment_upload where assign_uploader_name = ?");
            pd.setString(1, staff_name);
            
            rs = pd.executeQuery();
                        
            out.println("<form id ='f1' method='post' action='Assignment_Received'>");

            out.println("<div id='form'><table class='styled-table'  id='dtBasicExample'>");

            out.println(""
                    + ""
                    + "<div class='form-group pull-right'>"
                    + "<input type='text' class='search form-control' placeholder='Search...'>"
                    + "</div>"
                    + "<span class='counter pull-right'></span>"
                    + "<table class='table table-hover table-bordered results'>"
                    + "<thead>"
                    + "<tr>"
                    + "      <th class=\"th-sm\"  style='font-weight:bold'>ASSIGNMENT ID\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>ASSIGNMENT NAME\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>CLASS\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>DATE UPLOADED\n"
                    + "       </th>"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>ASSIGNMENT DEADLINE\n"
                    + "       </th>"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>MARKS\n"
                    + "       </th>"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>ASSIGNMENTS RECIEVED\n"
                    + "       </th>"
                    + "      <tr class=\"warning no-result\">\n"
                    + "      <td colspan=\"4\"><i class=\"fa fa-warning\"></i> No result</td>\n"
                    + "    </tr>\n"
                    + "  </thead>\n");

            while (rs.next()) {

                String id = rs.getString("assign_id");
                String name = rs.getString("assign_name");
                String cla = rs.getString("assign_class");
                String date_uploaded = rs.getString("assign_date_uploaded");
                String deadline = rs.getString("assign_deadline");
                String marks = rs.getString("assign_marks");

                out.println("<tbody>"
                        + "<tr>"
                        + "<td  style='font-weight:normal'>" + id + "</td>"
                        + "<td  style='font-weight:normal'>" + name + "</td>"
                        + "<td  style='font-weight:normal'>" + cla + "</td>"
                        + "<td  style='font-weight:normal'>" + date_uploaded + "</td>"
                        + "<td  style='font-weight:normal'>" + deadline + "</td>"
                        + "<td  style='font-weight:normal'>" + marks + "</td>"
                        
                        + "<td><button type='submit'  name='abc' value='"+id+"'  class='btn btn btn-outline-dark btn-rounded btn-sm my-0'><i class='fas fa-poll fa-2x'></i> Results</button></td>"
                        + "</tr>"
                        + "</tbody>");
            }

            out.println("</table>");

            out.println(""
                    + " "
                    + "");
            out.println("</div></form>"
                    + "</body></html>");

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
                    + " });\n"
                    + "$(window).keydown(function(event){\n"
                    + "    if(event.keyCode == 13) {\n"
                    + "      event.preventDefault();\n"
                    + "      return false;\n"
                    + "    }\n"
                    + "  });"
                    + "});"
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Staff_Display_Assignments.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Staff_Display_Assignments.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Staff_Display_Assignments.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Staff_Display_Assignments.class.getName()).log(Level.SEVERE, null, ex);
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
