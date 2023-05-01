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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author anubh
 */
public class DisplayStudentData extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");

        try {
            Connection c = Connect_To_Database.connect();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from student where del=1 order by class,division,rollno asc ");
            out.println("<meta charset=\"UTF-8\">\n"
                    + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "  <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">");
            out.print(" <link rel='stylesheet' type='text/css' href='DisplayStudentData.css'>");
            
            
            out.println("<html><body>"
                + "<center><h1>STUDENT DATABASE</h1></center>"
                + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.19.1/css/mdb.min.css\" rel=\"stylesheet\">"
                + "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>"
                + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css\" rel=\"stylesheet\">"
                + "<link href=\"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap\" rel=\"stylesheet\">"
                + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.6.0/mdb.min.css\" rel=\"stylesheet\" >");

            out.println("<button onClick='add()' class='btn btn-outline-info btn-rounded btn-sm my-0' id='btn' style=\"display:inline;\"><i class='fas fa-user-plus fa-3x'></i>Add</button>");
            
            out.println("<form action ='Add_Excel' method='post' id='xl'> "
            + "<input type='text' id='excel' name='excel' value='default' hidden> </lable> <br><br>");
            out.println("<button onClick='add_excel()' class='btn btn-outline-danger btn-rounded btn-sm my-0' id='btn' style=\"display:inline;\"><i class='fas fa-user-plus fa-3x'></i>Add Excel</button>"
                    + "</form>");
            
            out.println("<br><br>");
            out.println("<form action='SortData' method='post'>");

            out.print("<button type='submit' class='btn btn-outline-default btn-rounded btn-sm my-0' style=\"display:inline;\"><i class='fas fa-filter fa-3x'></i>Filter</button>");

            out.println("<div class=\"select\" id='select'>\n"
                    + "        <select id='class' name='class' style=\" height:25px;margin-left:95px;\n"
                    + "         border-radius: 15px; background-color:window; font: bolder\">\n"
                    + "            <option hidden=\"\" value='null'>Class</option>\n"
                    + "             <option name='class' value='0'>All</option>"
                    + "            <option name='class' value='07'>07</option>\n"
                    + "            <option name='class' value='08'>08</option>\n"
                    + "            <option name='class' value='09'>09</option>\n"
                    + "            <option name='class' value='10'>10</option>\n"
                    + "        </select>\n"
                    + "      </div>");

            out.println("<div class='select' >\n"
                    + "        <select id='div' name='div' style=\" height:25px;margin-left:95px;\n"
                    + "         border-radius: 15px; background-color:window; font: bolder\">\n"
                    + "           <option hidden=\"\" value='null'>Division</option>\n"
                    + "             <option name='div' value='0'>All</option>"
                    + "            <option name='div' value='A'>A</option>\n"
                    + "            <option name='div' value='B'>B</option>\n"
                    + "        </select>\n"
                    + "      </div>"
                    + "");

            out.println("</form>");

            out.println("<form id='form' method='post' action='EditStudent'><table class='styled-table'>");
            out.println("<div class='form-group pull-right'>"
                    + "<input type='text' id='l' name='l' value='default' hidden> </lable> <br><br>"
                    + "<input type='text' class='search form-control' placeholder='Search...'>"
                    + "</div>"
                    + "<span class='counter pull-right'></span>"
                    + "<table id='tab' class='table table-hover table-bordered results'>"
                    + "<thead>"
                    + "<tr>"
                    + "      <th class=\"th-sm\" style='font-weight:bold;'>CLASS\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold;'>DIVISION\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold;'>ROLL NO.\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold;'>NAME\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold;'>PHONE NO.\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold;'>USER ID\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold;'>PASSWORD\n"
                    + "      </th>\n"
                    + "      <tr class=\"warning no-result\">\n"
                    + "      <td colspan=\"4\"><i class=\"fa fa-warning\"></i> No result</td>\n"
                    + "    </tr>\n"
                    + "  </thead>\n");
            while (rs.next()) {
                String userid = rs.getString("userid");
                String cla = rs.getString("class");
                String rollno = rs.getString("rollno");
                String name = rs.getString("name");
                String div = rs.getString("division");
                String password = rs.getString("password");
                String phoneno = rs.getString("phoneno");
                

                out.println("<tbody><tr><td style='font-weight:normal;'>" + cla + "</td><td style='font-weight:normal;'>" + div + "</td><td style='font-weight:normal;'>" + rollno + "</td>"
                        + "<td style='font-weight:normal;'>" + name + "</td><td style='font-weight:normal;'>" + phoneno + "</td><td style='font-weight:normal;'>" + userid
                        + "</td><td style='font-weight:normal;'>" + password + "  "
                        + "<td><button type='submit' id='" + userid + "' name='" + userid + "'  onClick='edit(this)' class='btn btn btn-outline-success btn-rounded btn-sm my-0'><i class='fas fa-user-edit fa-2x'></i> Edit</button></td>"
                        + "<td><button id='" + userid + "' name='" + userid + "'  onClick='del(this)' class='btn btn-outline-danger btn-rounded btn-sm my-0'><i class='fa fa-trash fa-2x'></i> Bin</button></td>"
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
                    + "var ans = confirm('Do you want to place this student record in bin?');"
                    + "if(ans){"
                    + "document.getElementById('l').value = btn.id+',2'; "
                    + ""
                    + "}"
                    + "else"
                    + "{"
                    + "document.getElementById('l').value = btn.id+',0';"
                    + "}"
                    + "}"
                    + "  function add(){"
                    + "window.location.replace('./Add_Student.html');"
                    + " }"
                    + ""
                     + "function add_excel()"
                    + "{"
                   
                    + "         document.getElementById('xl').submit();"
                    + "}"
                    
                    
                    
                    + "function sort()"
                    + "{}"
                    + "</script> ");

        } catch (Exception e) {
            out.println("error");
        }
    }
}
