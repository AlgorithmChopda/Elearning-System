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
import java.util.HashMap;
import java.util.HashSet;
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
public class Student_Display_Assignment extends HttpServlet {

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
            HashMap<Integer,String> hs = new HashMap<>();

            // fetching the class of student
            String student_id = (String) session.getAttribute("student_id");
            String sql = "select * from student where userid=?";
            PreparedStatement pd = c.prepareStatement(sql);
            pd.setString(1, student_id);

            ResultSet rs = pd.executeQuery();
            rs.next();
            String cla = rs.getString("class");
            String rollno = rs.getString("rollno");
            String student_name = rs.getString("name");

            //getting uploaded assignments by student
            pd = c.prepareStatement("select * from student_assignment_uploaded where rollno = ? and student_name=? ");
            pd.setString(1, rollno);
            pd.setString(2, student_name);
            rs = pd.executeQuery();

            while (rs.next()) {
                hs.put(Integer.parseInt(rs.getString("assign_id")), rs.getString("obtained_marks"));
            }

            // displaying the files
            pd = c.prepareStatement("select * from assignment_upload where assign_class = ?");
            pd.setString(1, cla);
            rs = pd.executeQuery();

            out.println("<form id ='form' method='post' action='Download_Assignment'>");

            out.println("<div id='form'><table class='styled-table'  id='dtBasicExample'>");

            out.println(""
                    + "<input type='text' id='l' name='l' value='default' hidden> </lable> <br><br>"
                    + "<div class='form-group pull-right'>"
                    + "<input type='text' class='search form-control' placeholder='Search...'>"
                    + "</div>"
                    + "<span class='counter pull-right'></span>"
                    + "<table class='table table-hover table-bordered results'>"
                    + "<thead>"
                    + "<tr>"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>ASSIGNMENT ID\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold'>ASSIGNMENT Name\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold'>UPLOADED by\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold'>DEADLINE\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold'>MARKS\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold'>DOWNLOAD\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold'>UPLOAD\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold'>STATUS \n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold'>OBTAINED MARKS \n"
                    + "      </th>\n"
                    + "      <tr class=\"warning no-result\">\n"
                    + "      <td colspan=\"4\"><i class=\"fa fa-warning\"></i> No result</td>\n"
                    + "    </tr>\n"
                    + "  </thead>\n");

            while (rs.next()) {

                String a_id = rs.getString("assign_id");
                String status = "NOT UPLOADED";
                String up = "UPLOAD";
                String color_code = "red";
                String obt_marks = "Not Assigned";
                boolean flag = true;
                if (hs.containsKey(Integer.parseInt(a_id))) {      
                    up = "RE-UPLOAD";
                    obt_marks = hs.get(Integer.parseInt(a_id));
                    if(obt_marks.equals("-1")){     // marks not assigned
                        obt_marks = "Not Assigned";
                    }
                    else{       // marks assigned
                        flag = false;
                    }
                    status = "UPLOADED";
                    color_code = "#00D100";
                }
                String a_name = rs.getString("assign_name");
                String uploaded_by = rs.getString("assign_uploader_name");
                String deadline = rs.getString("assign_deadline");
                String marks = rs.getString("assign_marks");

                
                if(flag){       // marks not assigned
                           
                    out.println("<tbody>"
                        + "<tr>"
                        + "<td  style='font-weight:normal'>" + a_id + "</td>"
                        + "<td style='font-weight:normal'>" + a_name + "</td>"
                        + "<td style='font-weight:normal'>" + uploaded_by + "</td>"
                        + "<td style='font-weight:normal'>" + deadline + "</td>"
                        + "<td style='font-weight:normal'>" + marks + "</td>"
                        + "<td><button type='submit' id='" + a_id + "_" + a_name + "' name='" + a_id + "_" + a_name + "'  onClick='download(this)' class='btn btn btn-outline-dark btn-rounded btn-sm my-0'><i class='fa fa-download fa-2x'></i>&nbsp;Download</button></td>"
                        + "<td><button type='submit'  id='" + a_id + "' name='" + a_id + "' value='" + a_id + "'  onClick='upload(this)' class='btn btn btn-outline-dark btn-rounded btn-sm my-0'><i class='fa fa-upload fa-2x'></i><br>" + up + "</button></td>"
                        + "<td style='color:" + color_code + "; font-weight: bold;  ' >" + status + "</td>"
                        + "<td style='font-weight:normal'>"+obt_marks+"</td></tr>"
                        + "</tbody>");
                }
                
                else
                {
                    out.println("<tbody>"
                        + "<tr>"
                        + "<td style='font-weight:normal'>" + a_id + "</td>"
                        + "<td style='font-weight:normal'>" + a_name + "</td>"
                        + "<td style='font-weight:normal'>" + uploaded_by + "</td>"
                        + "<td style='font-weight:normal'>" + deadline + "</td>"
                        + "<td style='font-weight:normal'>" + marks + "</td>"
                        + "<td><button type='submit' id='" + a_id + "_" + a_name + "' name='" + a_id + "_" + a_name + "'  onClick='download(this)' class='btn btn btn-outline-dark btn-rounded btn-sm my-0'><i class='fa fa-download fa-2x'></i>&nbsp;Download</button></td>"
                        + "<td style='font-weight:normal'>----</td>"
                        + "<td style='color:" + color_code + "; font-weight: bold;  ' >" + status + "</td>"
                        + "<td style='font-weight:normal'>"+obt_marks+"</td></tr>"
                        + "</tbody>");
                }
            }
            out.println("</table>");

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
                    + "		  });\n"
                    + "$(window).keydown(function(event){\n"
                    + "    if(event.keyCode == 13) {\n"
                    + "      event.preventDefault();\n"
                    + "      return false;\n"
                    + "    }\n"
                    + "  });"
                    + "});"
                    + "function download(btn)"
                    + "{"
                    + " document.getElementById('l').value = btn.id; "
                    + "}"
                    + "function upload(btn)"
                    + "{"
                    + " document.getElementById('l').value = btn.id; "
                    + " document.getElementById('form').action='Student_Upload_Assignment';"
                    + "document.getElementById('form').submit();"
                    + "}"
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
            Logger.getLogger(Student_Display_Assignment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Student_Display_Assignment.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Student_Display_Assignment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Student_Display_Assignment.class.getName()).log(Level.SEVERE, null, ex);
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
