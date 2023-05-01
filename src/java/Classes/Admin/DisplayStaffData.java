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
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author anubh
 */
public class DisplayStaffData extends HttpServlet {

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
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.print(" <link rel='stylesheet' type='text/css' href='DisplayStudentData.css'>");
        out.println("<html><body>"
                + "<center><h1>STAFF DATABASE</h1></center>"
                + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.19.1/css/mdb.min.css\" rel=\"stylesheet\">"
                + "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>"
                + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css\" rel=\"stylesheet\">"
                + "<link href=\"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap\" rel=\"stylesheet\">"
                + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.6.0/mdb.min.css\" rel=\"stylesheet\" >");

        response.setContentType("text/html;charset=UTF-8");
        try {
            Connection c = Connect_To_Database.connect();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from staff where del=1 order by userid asc");
            out.print(" <link rel='stylesheet' type='text/css' href='DisplayStudentData.css'>");

            out.println("<button onClick='add()' class='btn btn-outline-info btn-rounded btn-sm my-0'><i class='fas fa-user-plus fa-3x'></i>Add</button>");
            out.println("<form id='form' method='post' action='EditStaff'><table class='styled-table'  id='dtBasicExample'>");
            //out.println("<thead><tr><th>GR No.</th><th>CLASS</th><th>DIV</th><th>ROLL No.</th>"
            //      + "<th>NAME</th><th>PASSWORD</th><th>PHONE No.</th><tr></thead>");  

            out.println(""
                    + ""
                    + "<div class='form-group pull-right'>"
                    + "<input type='text' id='l' name='l' value='default' hidden> </lable> <br><br>"
                    + "<input type='text' class='search form-control' placeholder='Search...'>"
                    + "</div>"
                    + "<span class='counter pull-right'></span>"
                    + "<table class='table table-hover table-bordered results'>"
                    + "<thead>"
                    + "<tr >"
                    + "      <th class=\"th-sm\"  style='font-weight:bold;'>USER ID\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold;'>NAME\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold;'>CLASS ASSIGNED\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold;'>DIVISION ASSIGNED\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold;'>PHONE NO.\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold;'>PASSWORD\n"
                    + "      </th>"
                    + "      <tr class=\"warning no-result\">\n"
                    + "      <td colspan=\"4\"><i class=\"fa fa-warning\"></i> No result</td>\n"
                    + "    </tr>\n"
                    + "  </thead>\n");

            while (rs.next()) {
                String userid = rs.getString("userid");
                String cla = rs.getString("class");
                //String rollno = rs.getString("rollno");
                String name = rs.getString("name");
                String division = rs.getString("division");
                String password = rs.getString("password");
                String phoneno = rs.getString("phoneno");
                
                if(cla.equalsIgnoreCase("0"))
                {
                    cla="--";
                    division="--";
                }

                out.println("<tbody>"
                        + "<tr>"
                        + "<td  style='font-weight:normal;'>" + userid + "</td>"
                        + "<td  style='font-weight:normal;'>" + name + "</td>"
                        + "<td style='font-weight:normal;'>" + cla + "</td>"
                        + "<td style='font-weight:normal;'>" + division + "</td>"
                        + "<td style='font-weight:normal;'>" + phoneno + "</td>"
                        + "<td style='font-weight:normal;'>" + password + "</td>"
                        + "<td style='font-weight:bold;'><button type='submit' id='" + userid + "' name='" + userid + "'  onClick='edit(this)' class='btn btn btn-outline-success btn-rounded btn-sm my-0'><i class='fas fa-user-edit fa-2x'></i> Edit</button></td>"
                        + "<td style='font-weight:normal;'>  <button type='submit' id='" + userid + "' name='" + userid + "'  onClick='del(this)' class='btn btn-outline-danger btn-rounded btn-sm my-0'><i class='fas fa-user-times fa-2x'></i> Delete</button></td>"
                        + "</tr>"
                        + "</tbody>");

                /* out.println("<tbody><tr><td>" + userid + "</td><td>" + cla + "</td><td>" + div + "</td>"
                         + "<td>" + rollno + "</td><td>"+ name +"</td><td>"+ password
                         +"</td><td>"+ phoneno +"  <button type='submit' id='"+userid+"' name='"+userid+"'  onClick='edit(this)'>edit</button></td>"
                                 + "<td>  <button type='submit' id='"+userid+"' name='"+userid+"'  onClick='del(this)'>delete</button></td>"
                                 + "</tr></tbody>");*/
            }

            out.println("</table>");

            out.println(""
                    + " "
                    + "");
            out.println("</form>"
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
                    + " function edit(btn){"
                    + "  document.getElementById('l').value = btn.id+',1'; "
                    + "} "
                    + "function del(btn){"
                    + "var ans = confirm('Are you sure you want to DELETE the staff recored PERMANENTLY?');"
                    + "if(ans){"
                    + "var res = confirm('Reconfirmation....');"
                    + "if(res)"
                    + "{"
                    + "document.getElementById('l').value = btn.id+',2'; "
                    + "}"
                    + "else"
                    + "{"
                    + "document.getElementById('l').value = btn.id+',0';"
                    + "}"
                    + "}"
                    + "else"
                    + "{"
                    + "document.getElementById('l').value = btn.id+',0';"
                    + "}"
                    + "}"
                    + "  function add(){"
                    + ""
                    + "window.location.replace('./Add_Staff.html');"
                    + " }</script> ");

        } catch (Exception e) {
            out.println("error");
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
        processRequest(request, response);
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
        processRequest(request, response);
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
