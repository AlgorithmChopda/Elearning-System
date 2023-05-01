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
import java.util.ArrayList;
import java.util.HashMap;
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
public class StaffDetails extends HttpServlet {

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
            HttpSession session = request.getSession();

            String userid = (String) session.getAttribute("staff_id");
            Connection c = Classes.Connect_To_Database.connect();

            PreparedStatement pd = c.prepareStatement("select * from staff where userid = ?");
            pd.setString(1, userid);

            ResultSet rs = pd.executeQuery();

            rs.next();

            String staff_name = rs.getString("name");
            String password = rs.getString("password");
            String phone = rs.getString("phoneno");

            // -- fetch all the record  of the staff
            //------------------test count------
            pd = c.prepareStatement("select COUNT('test_id') from test_headers where staff_name = ? ");
            pd.setString(1, userid);

            rs = pd.executeQuery();

            long test_count = 0;
            if (rs.next()) {
                test_count = rs.getLong(1);
            }

            //-------------------assignment-----------------------
            pd = c.prepareStatement("select COUNT('assign_id') from assignment_upload where assign_uploader_name = ? ");
            pd.setString(1, staff_name);

            rs = pd.executeQuery();

            long assign_count = 0;
            if (rs.next()) {
                assign_count = rs.getLong(1);
            }

            //-----------------------study material----------------------
            pd = c.prepareStatement("select COUNT('file_id') from file_upload where uploader_name = ? ");
            pd.setString(1, staff_name);

            rs = pd.executeQuery();

            long study_count = 0;
            if (rs.next()) {
                study_count = rs.getLong(1);
                System.out.println("interface if");
            }

            // ----------------------------------------------   //
            pd = c.prepareStatement("select * from test_headers where staff_name = ? order by test_id DESC");
            pd.setString(1, userid);
            rs = pd.executeQuery();

            // test_id, array_index
            HashMap<Integer, Integer> hs = new HashMap<>();
            int[] test_id = new int[6];
            int[] l_marks = new int[6];
            int[] m_marks = new int[6];
            int[] avg_marks = new int[6];
            int n = 0;

            while (rs.next() && n < 6) {
                test_id[n] = Integer.parseInt(rs.getString("test_id"));
                hs.put(Integer.parseInt(rs.getString("test_id")), n);
                l_marks[n] = Integer.MAX_VALUE;
                n++;
            }

            pd = c.prepareStatement("select * from test_history");

            rs = pd.executeQuery();

            /* while(rs.next()){
            System.out.println(rs.getString("test_ids"));
        }
             */
            while (rs.next()) {
                String test_ids[] = (rs.getString("test_ids").split(","));      // given tests
                String marks[] = (rs.getString("marks").split(","));

                int index = -1;

                for (int i = 1; i < test_ids.length; i++) {

                    int key = Integer.parseInt(test_ids[i]);
                    String mk[] = marks[i].split("/");      // 0 = marks obtained , 1 = out of

                    System.out.println(" idd : " + key + " marks : " + mk[0] + " rray inex : " + hs.get(key));
                    if (hs.containsKey(key)) {
                        System.out.println(" temp : " + l_marks[hs.get(key)]);
                        l_marks[hs.get(key)] = Math.min(l_marks[hs.get(key)], Integer.parseInt(mk[0]));
                        m_marks[hs.get(key)] = Math.max(m_marks[hs.get(key)], Integer.parseInt(mk[0]));
                        avg_marks[hs.get(key)] += Integer.parseInt(mk[0]);
                    }
                }

            }

            for (int i = 0; i < n; i++) {
                System.out.println(test_id[i] + " " + l_marks[i] + " " + m_marks[i] + " " + (avg_marks[i] / (n + 1)));
            }
            int i = 0;

            out.println("<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head>\n"
                    + "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> \n"
                    + "            \n"
                    + "	<meta charset=\"utf-8\">\n"
                    + "	<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">\n"
                    + "	<!-- CSS only -->\n"
                    + "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\n"
                    + "<!-- JavaScript Bundle with Popper -->\n"
                    + "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>\n"
                    + "\n"
                    + "	\n"
                    + "<link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" >\n"
                    + "<link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js\" >\n"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/MaterialDesign-Webfont/3.6.95/css/materialdesignicons.css\" >\n"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\">\n"
                    + "\n"
                    + "\n"
                    + "	<title></title>\n"
                    + "\n"
                    + "\n"
                    + "\n"
                    + "	<style>\n"
                    + "body{\n"
                    + "  background: #eaeef3;\n"
                    + "}\n"
                    + "\n"
                    + "\n"
                    + "		.profile{\n"
                    + "			height:  300px;\n"
                    + "			width: 400px;\n"
                    + "			margin-left: 10px;\n"
                    + "			box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px;\n"
                    + "		}\n"
                    + "\n"
                    + "		\n"
                    + "\n"
                    + "		.chart{\n"
                    + "			height:  350px;\n"
                    + "			width: 550px;\n"
                    + "			box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px;\n"
                    + "			margin-left: 480px;\n"
                    + "			margin-top: 50px;\n"
                    + "\n"
                    + "		}\n"
                    + "\n"
                    + "\n"
                    + "		\n"
                    + "\n"
                    + ".one {\n"
                    + "    background-color: #232428;\n"
                    + "    border-radius: 5px\n"
                    + "\n"
                    + "}\n"
                    + "\n"
                    + ".dotdot {\n"
                    + "    font-size: 13px !important;\n"
                    + "    font-weight: lighter !important;\n"
                    + "    color: #76787b\n"
                    + "}\n"
                    + "\n"
                    + ".name {\n"
                    + "\n"
                    + "    font-size: 18px;\n"
                    + "    color: #b7b7b7;\n"
                    + "    font-weight: 600\n"
                    + "}\n"
                    + "\n"
                    + ".des {\n"
                    + "    font-size: 15px;\n"
                    + "    font-weight: 500;\n"
                    + "    color: #5a5d64\n"
                    + "}\n"
                    + "\n"
                    + "\n"
                    + "\n"
                    + "\n"
                    + ".button-34 {\n"
                    + "  background: #5E5DF0;\n"
                    + "  border-radius: 999px;\n"
                    + "  box-shadow: #5E5DF0 0 10px 20px -10px;\n"
                    + "  box-sizing: border-box;\n"
                    + "  color: #FFFFFF;\n"
                    + "  cursor: pointer;\n"
                    + "  font-family: Inter,Helvetica,\"Apple Color Emoji\",\"Segoe UI Emoji\",NotoColorEmoji,\"Noto Color Emoji\",\"Segoe UI Symbol\",\"Android Emoji\",EmojiSymbols,-apple-system,system-ui,\"Segoe UI\",Roboto,\"Helvetica Neue\",\"Noto Sans\",sans-serif;\n"
                    + "  font-size: 15px;\n"
                    + "  font-weight: 700;\n"
                    + "  line-height: 24px;\n"
                    + "  opacity: 1;\n"
                    + "  outline: 0 solid transparent;\n"
                    + "  padding: 8px 18px;\n"
                    + "  user-select: none;\n"
                    + "  -webkit-user-select: none;\n"
                    + "  touch-action: manipulation;\n"
                    + "  width: fit-content;\n"
                    + "  word-break: break-word;\n"
                    + "  border: 0;\n"
                    + "}\n"
                    + "\n"
                    + ".button-34:hover {\n"
                    + "  background-color: white; /* Green */\n"
                    + "  color: #5E5DF0;\n"
                    + "}\n"
                    + "\n"
                    + "\n"
                    + "\n"
                    + ".flip-card {\n"
                    + "  background-color: transparent;\n"
                    + "  width: 200px;\n"
                    + "  height: 300px;\n"
                    + "  perspective: 1000px;\n"
                    + "  margin-left: 10px;\n"
                    + "}\n"
                    + "\n"
                    + ".flip-card-inner {\n"
                    + "  position: relative;\n"
                    + "  width: 100%;\n"
                    + "  height: 100%;\n"
                    + "  text-align: center;\n"
                    + "  transition: transform 1s;\n"
                    + "  transform-style: preserve-3d;\n"
                    + "  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);\n"
                    + "}\n"
                    + "\n"
                    + ".flip-card:hover .flip-card-inner {\n"
                    + "  transform: rotateY(180deg);\n"
                    + "  width: 350px;\n"
                    + "  height: 350px;\n"
                    + "  margin-left: 15px;\n"
                    + "}\n"
                    + "\n"
                    + ".flip-card-front, .flip-card-back {\n"
                    + "  position: absolute;\n"
                    + "  width: 100%;\n"
                    + "  height: 100%;\n"
                    + "  -webkit-backface-visibility: hidden;\n"
                    + "  backface-visibility: hidden;\n"
                    + "}\n"
                    + "\n"
                    + ".flip-card-front {\n"
                    + "  background-color: #bbb;\n"
                    + "  color: black;\n"
                    + "}\n"
                    + "\n"
                    + ".flip-card-back {\n"
                    + "  background-color: whitesmoke;\n"
                    + "  color: black;\n"
                    + "  transform: rotateY(180deg);\n"
                    + "  width: 120%;\n"
                    + "  box-shadow: 2px 4px 6px;\n"
                    + "  border-left-color: rebeccapurple;\n"
                    + "  border-style:solid;\n"
                    + "}\n"
                    + "\n"
                    + "\n"
                    + "	</style>\n"
                    + "\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "\n"
                    + "\n"
                    + "	<div class=\"container d-flex justify-content-center mt-5\"  style=\"margin-left: -400px;\">\n"
                    + "\n"
                    + "<div class=\"flip-card\">\n"
                    + "    <div class=\"flip-card-inner\">\n"
                    + "        <div class=\"flip-card-front\">\n"
                    + "\n"
                    + "            <div class=\"main\">\n"
                    + "                <div class=\"row\">\n"
                    + "                    <div class=\"col-md-3\">\n"
                    + "                        <div>\n"
                    + "                            <div class=\"one\" style=\"width: 200px; height: 300px;\">\n"
                    + "                                <div class=\"text-right pr-2 pt-1\"><i class=\"mdi mdi-dots-vertical dotdot\"></i></div>\n"
                    + "                                <div class=\"d-flex justify-content-center\" style=\"margin-top: 30px;\"><img src=\"t.png\" width=\"80\" class=\"rounded-circle\"></div>\n"
                    + "                                <div class=\"text-center\">\n"
                    + "                                  <br>\n"
                    + "                                    <span class=\"name\" style=\"color:white;\">" + staff_name + "</span>\n"
                    + "                                    <p class=\"des\" style=\"color:whitesmoke;\">Teacher</p>\n"
                    + "                                </div>\n"
                    + "                                \n"
                    + "                                <div><center> <button class=\"button-34\" style=\"height: 35px; width: 140px; margin-top: 30px; align:center;\"> 	View Profile</button> </center></div>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "\n"
                    + "        <div class=\"flip-card-back\">\n"
                    + "          \n"
                    + "          <br>\n"
                    + "            <h2>Prof. " + staff_name + "</h2>\n"
                    + "            <hr style=\"width:100%;text-align:left;margin-left:0;height: 5px; color:gold;\">\n"
                    + "            <br>\n"
                    + "            \n"
                    + "            <p><h4><span style=\"color:#C46210;font-size: 18px;\"><b>Designation:</b></span>&nbsp;<span style=\"color:darkred;\">Teacher</span ></h4></p>\n"
                    + "            <p><h4><span style=\"color:#C46210;font-size: 18px;\"><b>User ID:</b></span>&nbsp;<span style=\"color:darkred;\">" + userid + "</span></h4></p>\n"
                    + "            <p><h4><span style=\"color:#C46210;font-size: 18px;\"><b>Password:</b></span>&nbsp;<span style=\"color:darkred;\">" + password + "</span></h4></p>\n"
                    + "            <p><h4><span style=\"color:#C46210;font-size: 18px;\"><b>Phone Number:</b></span>&nbsp;<span style=\"color:darkred;\">" + phone + "</span></h4></p>\n"
                    + "            \n"
                    + "        </div>\n"
                    + "\n"
                    + "    </div>\n"
                    + "\n"
                    + "\n"
                    + "\n"
                    + "</div>\n"
                    + "</div>\n"
                    + "	\n"
                    + "\n"
                    + "\n"
                    + "<div class=\"card border-info mb-3\" style=\"height: 100px;width: 120px;box-shadow: 2px 4px 6px;color: darkblue;\n"
                    + "margin-left: 500px;margin-top: -290px;\">\n"
                    + "  \n"
                    + "  <div class=\"card-body\">\n"
                    
                    + "    <h6 class=\"card-title\" style=\"color:black;\"><b>Total Students</b></h6>\n"
                    + "    <p class=\"card-text\" style=\"font-size: 20px;color:blue;\"><b>10</b></p>\n"
                    + "   </div>\n"
                    + "</div>\n"
                    + "\n"
                    + "<div class=\"card border-success mb-3\" style=\"height: 100px;width: 120px;box-shadow: 2px 4px 6px;color: darkgreen;\n"
                    + "margin-left: 630px;margin-top: -115px;\">\n"
                    + "  \n"
                    + "  <div class=\"card-body\">\n"
                    + "    <h6 class=\"card-title\" style=\"color:black;\"><b>Study Material</b></h6>\n"
                    + "    <p class=\"card-text\" style=\"font-size: 20px;color:green;\"><b>" + study_count + "</b></p>\n"
                    + "   </div>\n"
                    + "</div>\n"
                    + "\n"
                    + "<div class=\"card border-warning mb-3\" style=\"height: 100px;width: 120px;box-shadow: 2px 4px 6px;color:#FFC300;\n"
                    + "margin-left: 760px;margin-top: -115px;\">\n"
                    + "  \n"
                    + "  <div class=\"card-body\">\n"
                    + "    <h6 class=\"card-title\" style=\"color:black;\"><b>Total No. of Tests</b></h6>\n"
                    + "    <p class=\"card-text\" style=\"font-size: 20px;color:#FFC300;\"><b>" + test_count + "</b></p>\n"
                    + "   </div>\n"
                    + "</div>\n"
                    + "\n"
                    + "<div class=\"card border-danger mb-3\" style=\"height: 100px;width: 120px;box-shadow: 2px 4px 6px;color:red;\n"
                    + "margin-left: 890px;margin-top: -115px;\">\n"
                    + "  \n"
                    + "  <div class=\"card-body\">\n"
                    + "    <h6 class=\"card-title\" style=\"color:black;\"><b>Tasks Assigned</b></h6>\n"
                    + "    <b><p class=\"card-text\" style=\"font-size: 20px;color:red;\">" + assign_count + "</p></b>\n"
                    + "   </div>\n"
                    + "</div>\n"
                    + "\n"
                    + "\n"
                    + "	 <div id=\"columnchart_material\" style=\" height: 400px;width: 600px;\n"
                    + "			box-shadow:\n"
                    + "2px 0px 2px 0px black,\n"
                    + "    0px 2px 2px 0px black,\n"
                    + "    -2px 0px 2px 0px black,\n"
                    + "    0px -2px 2px black;"
                    + "			margin-left: 480px;\n"
                    + "			margin-top: 50px;\">\n"
                    + "				\n"
                    + "	</div>\n"
                    + "\n"
                    + "<div class=\"card border-secondary mb-3\" style=\"height: 200px;width: 400px;box-shadow: 2px 4px 6px;\n"
                    + "margin-left: 10px;margin-top:-200px;\">\n"
                    + "  <div class=\"card-header\" style=\"color:black\">\n"
                    + "    <b>Quote</b>\n"
                    + "  </div>\n"
                    + "  <div class=\"card-body\"  >\n"
                    + "    <blockquote class=\"blockquote mb-3\">\n"
                    + "      <b><p style=\"color: darkmagenta; font-size: 20px;\"><i>\"I believe there is no other profession in the world that is more important to society than that of a teacher.\"</i></p></b>\n"
                    + "      <footer class=\"blockquote-footer\" style=\"color:darkblue; font-size: 19px;\"><cite title=\"Source Title\">Dr. A. P. J. Abdul Kalam</cite></footer>\n"
                    + "    </blockquote>\n"
                    + "  </div>\n"
                    + "</div>\n"
                    + "\n"
                    + "\n"
                    + "\n"
                    + "</body>\n"
                    + " <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n"
                    + "    <script type=\"text/javascript\">\n"
                    + "      google.charts.load('current', {'packages':['bar']});\n"
                    + "      google.charts.setOnLoadCallback(drawChart);\n"
                    + "\n"
                    
                    + "      function drawChart() {\n"
                    + "var data = new google.visualization.DataTable();"
                    + "data.addColumn('number','TEST ID');"
                    + "data.addColumn('number','Highest Marks');"
                    + "data.addColumn('number','Lowest Marks');"
                    + "data.addColumn('number','Average Marks');");

            for (int j = 0; j < n; j++) {

                int a = test_id[j];
                int b = m_marks[j];
                int c1;
                int avg = avg_marks[i] / (n + 1);
                if (l_marks[j] > 1000) {
                    c1 = 0;
                    continue;
                } else {
                    c1 = l_marks[j];
                }
                out.println("data.addRow([parseInt(" + a + "),parseInt(" + b + "), parseInt(" + c1 + "), parseInt(" + avg + ")]);");
            }

            out.println(" var options = {"
                    + "    height : 400,"
                    + "    width :  600,"
                    + "   title: 'TEST Performance',"
                    + "   colors: ['#006400', '#e30022','#fff700'],"
                    + "animation:{"
                    + "        duration: 4000,"
                    + "        easing: 'in',"
                    + "      },"
                    + ""
                    + "        };\n"
                    + "\n"
                    + "        var chart = new google.charts.Bar(document.getElementById('columnchart_material'));\n"
                    + "\n"
                    + "        chart.draw(data, google.charts.Bar.convertOptions(options));\n"
                 //   + "      }\n"
                    +"}"
                    + "    </script>"
                    + ""
                    
                    + "</script>"
                    + "</html>");

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
            Logger.getLogger(StaffDetails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StaffDetails.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(StaffDetails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StaffDetails.class.getName()).log(Level.SEVERE, null, ex);
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
