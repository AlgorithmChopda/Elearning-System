/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Admin;

import Classes.Connect_To_Database;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author anubh
 */
public class SortData extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String cla = request.getParameter("class");
            String div = request.getParameter("div");
            /* TODO output your page here. You may use following sample code. */
            
            Connection c = Connect_To_Database.connect();
            String sql;
            ResultSet rs=null; 
            
        if( (cla.equalsIgnoreCase("0") && div.equalsIgnoreCase("0")) || (cla.equalsIgnoreCase("null") && div.equalsIgnoreCase("null"))) 
        {
           response.sendRedirect("./DisplayStudentData");
            
            return;
      
        }
        else if(cla.equalsIgnoreCase("0"))
        {
            sql = "select * from student where division=?";
            PreparedStatement pd = c.prepareStatement(sql);
            pd.setString(1, div);
        
            rs = pd.executeQuery();
        }
        else if(div.equalsIgnoreCase("0"))
        {
            sql = "select * from student where class=?";
            PreparedStatement pd = c.prepareStatement(sql);
            pd.setString(1, cla);
        
            rs = pd.executeQuery();
        }
        else
        {
            
            sql = "select * from student where class=? and division=?";
            PreparedStatement pd = c.prepareStatement(sql);
            pd.setString(1, cla);
            pd.setString(2, div);
        
            rs = pd.executeQuery();
        }   
            
            out.print(" <link rel='stylesheet' type='text/css' href='DisplayStudentData.css'>");
            out.println("<html><body>"
                    + "<!-- Font Awesome -->\n"
                    + "<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.8.2/css/all.css\">\n"
                    + "<!-- Google Fonts -->\n"
                    + "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap\">\n"
                    + "<!-- Bootstrap core CSS -->\n"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.0/css/bootstrap.min.css\" rel=\"stylesheet\">\n"
                    + "<!-- Material Design Bootstrap -->\n"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.19.1/css/mdb.min.css\" rel=\"stylesheet\">"
                    + "<!-- JQuery -->\n"
                    + "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\n"
                    + "<!-- Bootstrap tooltips -->\n"
                    + "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.4/umd/popper.min.js\"></script>\n"
                    + "<!-- Bootstrap core JavaScript -->\n"
                    + "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.0/js/bootstrap.min.js\"></script>\n"
                    + "<!-- MDB core JavaScript -->\n"
                    + "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.19.1/js/mdb.min.js\"></script>"
                    + "<!-- Font Awesome -->\n"
                    + "<link\n"
                    + "  href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css\"\n"
                    + "  rel=\"stylesheet\"\n"
                    + "/>\n"
                    + "<!-- Google Fonts -->\n"
                    + "<link\n"
                    + "  href=\"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap\"\n"
                    + "  rel=\"stylesheet\"\n"
                    + "/>\n"
                    + "<!-- MDB -->\n"
                    + "<link\n"
                    + "  href=\"https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.6.0/mdb.min.css\"\n"
                    + "  rel=\"stylesheet\"\n"
                    + "/>");

            out.println("<button onClick='add()' class='btn btn-outline-info btn-rounded btn-sm my-0'><i class='fas fa-user-plus fa-3x'></i>Add</button>");
            
            
            
            
            
            out.println("<form id='form' method='post' action='EditStudent'><table class='styled-table'>");
            out.println("<div class='form-group pull-right'>"
                    + "<input type='text' id='l' name='l' value='default' hidden> </lable> <br><br>"
                    + "<input type='text' class='search form-control' placeholder='Search...'>"
                    + "</div>"
                    + "<span class='counter pull-right'></span>"
                    + "<table id='tab' class='table table-hover table-bordered results'>"
                    + "<thead>"
                    + "<tr>"
                    + "      <th class=\"th-sm\">CLASS\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\">DIVISION\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\">ROLL NO.\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\">NAME\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\">PHONE NO.\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\">USER ID\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\">PASSWORD\n"
                    + "      </th>\n"
                    + "      <tr class=\"warning no-result\">\n"
                    + "      <td colspan=\"4\"><i class=\"fa fa-warning\"></i> No result</td>\n"
                    + "    </tr>\n"
                    + "  </thead>\n");
            while (rs.next()) {
                String userid = rs.getString("userid");
                cla = rs.getString("class");
                String rollno = rs.getString("rollno");
                String name = rs.getString("name");
                div = rs.getString("division");
                String password = rs.getString("password");
                String phoneno = rs.getString("phoneno");

                out.println("<tbody><tr><td>" + cla + "</td><td>" + div + "</td><td>" + rollno + "</td>"
                        + "<td>" + name + "</td><td>" + phoneno + "</td><td>" + userid
                        + "</td><td>" + password + "  "
                        + "<td><button type='submit' id='" + userid + "' name='" + userid + "'  onClick='edit(this)' class='btn btn btn-outline-success btn-rounded btn-sm my-0'><i class='fas fa-user-edit fa-2x'></i> Edit</button></td>"
                        + "<td>  <button type='submit' id='" + userid + "' name='" + userid + "'  onClick='del(this)' class='btn btn-outline-danger btn-rounded btn-sm my-0'><i class='fas fa-user-times fa-2x'></i> Delete</button></td>"
                        + "</tr></tbody>");
            }

            out.println("</table>");

            out.println("</form>"
                    + ""
                    + "</body></html>");

            out.println("<script>"
                    + "// Basic example\n"
                    + "$(document).ready(function () {\n"
                    + "  $('#dtBasicExample').DataTable({\n"
                    + "    \"pagingType\": \"simple\" // \"simple\" option for 'Previous' and 'Next' buttons only\n"
                    + "  });\n"
                    + "  $('.dataTables_length').addClass('bs-select');\n"
                    + "});"
                    + ""
                    + ""
                    + ""
                    + ""
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
                    + "$(document).ready(function () {\n"
                    + "$('#dtBasicExample').DataTable();\n"
                    + "$('.dataTables_length').addClass('bs-select');\n"
                    + "});"
                    + " function edit(btn){"
                    + "  document.getElementById('l').value = btn.id+',1'; "
                    + "} "
                    + "function del(btn){"
                    + "  document.getElementById('l').value = btn.id+',2'; "
                    + "}"
                    + "  function add(){"
                    + "window.location.replace('./AddStudent.html');"
                    + " }"
                    + ""
                    + "function sort()"
                    + "{}"
                    + "</script> ");


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
        } catch (SQLException ex) {
            Logger.getLogger(SortData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SortData.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(SortData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SortData.class.getName()).log(Level.SEVERE, null, ex);
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
