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
import java.util.Iterator;
import java.util.Map;
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
public class Display_Test_Details extends HttpServlet {

    // hs = hashmap
    // hm = hashset
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

        HashMap<Integer, String> hs = new HashMap<Integer, String>();
        HashMap<Integer, String> hs1 = new HashMap<>();
        HashSet<String> t_p = new HashSet<>();

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>"
                    + ""
                    + ""
                    + "");

            out.println("<html>");
            out.println("<head>"
                   
                    + ""
                    + "");
            out.println("</head>");
            out.println("<body>");
            out.println(""
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.19.1/css/mdb.min.css\" rel=\"stylesheet\">"
                    + "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css\" rel=\"stylesheet\">"
                    + "<link href=\"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap\" rel=\"stylesheet\">"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.6.0/mdb.min.css\" rel=\"stylesheet\" >");
            out.print(" <link rel='stylesheet' type='text/css' href='DisplayStudentData.css'>");

            Connection c = Classes.Connect_To_Database.connect();
            HttpSession session = request.getSession();

            String userid = (String) session.getAttribute("student_id");

            PreparedStatement pd = c.prepareStatement("select * from student where userid = ? and del=1");
            pd.setString(1, userid);

            ResultSet rs = pd.executeQuery();

            rs.next();

            String cla = rs.getString("class");
            String div = rs.getString("division");

            session.setAttribute("class", cla);
            session.setAttribute("division", div);

            pd = c.prepareStatement("select * from test_headers where class = ? and  (div = '0' or div = ?) and (status='1') ");

            pd.setString(1, cla);
            pd.setString(2, div);

            rs = pd.executeQuery();

            // method='post'  action='StartTest'
            out.println(""
                    + " "
                    + "<form id='form' method='post'  action='StartTest' target='_parent'>"
                    + "<table class='styled-table'>"
                    + "<div class='form-group pull-right'>"
                    + "<input type='text' id='l' name='l' value='default' hidden> "
                    + "<input type='text' class='search form-control' placeholder='Search...'>"
                    + "</div>"
                    + "<span class='counter pull-right'></span>"
                    + "<table class='table table-hover table-bordered results'>"
                    + "<thead>"
                    + "<tr>"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>TEST ID\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\"  style='font-weight:bold'>TEST NAME\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>CLASS\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>DIVISION\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>TEST DURATION (minutes) \n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>TEST DATE</th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>STATUS</th>\n"
                    + "      <tr class=\"warning no-result\">\n"
                    + "      <td colspan=\"4\"><i class=\"fa fa-warning\"></i> No result</td>\n"
                    + "    </tr>\n"
                    + "  </thead>\n");

            int no_of_test = 0;
            String test_date;
            while (rs.next()) {
                test_date = rs.getString("test_date");

                String test_id = Integer.toString(rs.getInt("test_id"));

                String test_name = rs.getString("test_name");

                hs.put(Integer.parseInt(test_id), test_name);
                hs1.put(Integer.parseInt(test_id), rs.getString("test_pass") + "," + test_date);

                no_of_test++;

            }

            pd = c.prepareStatement("select * from test_history where student_id=? ");

            pd.setString(1, userid);

            rs = pd.executeQuery();

            String test_ids[];

            String test_progress[];
            HashSet<Integer> hm = new HashSet<Integer>();

            if (rs.next()) {

                test_progress = rs.getString("test_progress").split(",");
                //System.out.println("name : ");
                for (int i = 1; i < test_progress.length; i++) {
                    
                  //  System.out.println(" i : "+test_progress[i]);
                    t_p.add(test_progress[i]);
                }

                if (rs.getString("test_ids") == null) {

                } else {
                    test_ids = rs.getString("test_ids").split(",");

                    for (int i = 1; i < test_ids.length; i++) {
                        hm.add(Integer.parseInt(test_ids[i]));
                    }
                }

                for (Map.Entry mapElement : hs.entrySet()) {

                    Integer key = (Integer) mapElement.getKey();
                    String value = (String) mapElement.getValue();

                    String time[] = hs1.get(key).split(",");

                    if (hm.contains(key)) { // student appeared
                        out.println("<tbody><tr><td  style='font-weight:normal'>" + key + "</td><td style='font-weight:normal'>" + hs.get(key) + "</td><td style='font-weight:normal'>" + cla + "</td>"
                                + "<td style='font-weight:normal'>" + div + "</td><td style='font-weight:normal'>" + time[0] + "</td>"
                                + "<td style='font-weight:normal'>" + time[1] + "</td>"
                                + "<td style='color: green; font-weight: bold;' >Appeared</td>"
                                + "</tr></tbody>");
                    } else // not appeared
                    {
                        if (t_p.contains(Integer.toString(key))) {
                            // Appearing
                            out.println("<tbody><tr><td style='font-weight:normal'>" + key + "</td><td  style='font-weight:normal'>" + hs.get(key) + "</td><td style='font-weight:normal'>" + cla + "</td>"
                                    + "<td style='font-weight:normal'>" + div + "</td><td style='font-weight:normal'>" + time[0] + "</td>"
                                    + "<td style='font-weight:normal'>" + time[1] + "</td>"
                                    + "<td style='color: red; font-weight: bold;' >Appearing...</td>"
                                    + "</tr></tbody>");
                        } else { 

                            out.println("<tbody><tr><td style='font-weight:normal'>" + key + "</td><td style='font-weight:normal'>" + hs.get(key) + "</td><td style='font-weight:normal'>" + cla + "</td>"
                                    + "<td style='font-weight:normal'>" + div + "</td><td style='font-weight:normal'>" + time[0] + "</td>"
                                    + "<td style='font-weight:normal'>" + time[1] + "</td>"
                                    + "<td><button type='submit' onClick='start(this)' id='" + key + "' name='" + key + "'   class='btn btn btn-outline-success btn-rounded btn-sm my-0'><i class='fas fa-play  fa-2x'></i> START</button></td>"
                                    + "</tr></tbody>");
                        }
                    }
                }

            } else {    
                Iterator hmIterator = hs.entrySet().iterator();

                for (Map.Entry mapElement : hs.entrySet()) {

                    Object key = mapElement.getKey();

                    Object value = mapElement.getValue();

                    String time[] = hs1.get(key).split(",");

                    out.println("<tbody><tr><td  style='font-weight:normal'>" + key + "</td><td style='font-weight:normal'>" + value + "</td><td style='font-weight:normal'>" + cla + "</td>"
                            + "<td style='font-weight:normal'>" + div + "</td><td style='font-weight:normal'>" + time[0] + "</td>"
                            + "<td style='font-weight:normal'>" + time[1] + "</td>"
                            + "<td><button type='submit' onClick='start(this)' id='" + key + "' name='" + key + "'   class='btn btn btn-outline-success btn-rounded btn-sm my-0'><i class='fas fa-play  fa-2x'></i> START</button></td>"
                            + "</tr></tbody>");

                }
            }

            out.println("</table>"
                    + "</form>");

            out.println("<script>"
                    + "function start(btn)"
                    + "{"
                    + "document.getElementById('l').value = btn.id; "
                    // + "document.getElementById('f').submit(); "
                    //    + "var ele = alert('test started');"
                    + ""
                    + ""
                    + ""
                    + "}"
                    + "</script>");

            out.println("</body>");
            out.println("</html>");

            out.println("<script>"
                    + ""
                    + ""
                    + ""
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
                    + " }</script> ");
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
            Logger.getLogger(Display_Test_Details.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Display_Test_Details.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Display_Test_Details.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Display_Test_Details.class.getName()).log(Level.SEVERE, null, ex);
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
