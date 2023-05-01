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
import java.sql.Statement;
import java.util.Random;
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
public class StartTest extends HttpServlet {

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
            session.setAttribute("result", true);

            String test_id = request.getParameter("l");

            PreparedStatement pd;
            ResultSet rs;
            /* ------ */

            // Setting test_progress to 1
            String student_id = (String) session.getAttribute("student_id");

            pd = c.prepareStatement("select * from test_history where student_id = ?");
            pd.setString(1, student_id);

            rs = pd.executeQuery();

            if (rs.next()) {

                String test_progress = rs.getString("test_progress");
                test_progress += "," + test_id;

                pd = c.prepareStatement("update test_history set test_progress=? where student_id = ?");

                pd.setString(1, test_progress);
                pd.setString(2, student_id);

                pd.execute();
            } else {
                pd = c.prepareStatement("insert into test_history (student_id, class, div, test_progress) values(?,?,?,?)");

                pd.setString(1, student_id);
                pd.setString(2, (String) session.getAttribute("class"));

                pd.setString(3, (String) session.getAttribute("division"));
                pd.setString(4, "," + test_id);

                pd.execute();
            }
            /* ------ */
            pd = c.prepareStatement("select * from test_headers where test_id = ?");
            pd.setString(1, test_id);

            rs = pd.executeQuery();

            rs.next();

            int time = Integer.parseInt(rs.getString("test_pass"));     // in minutes

            int hour = time / 60;
            int min = time %= 60;

            out.println("<!DOCTYPE html>");

            

            out.println(" <script>\n"
                    + "        //set minutes\n"
                    + "        var mins = '" + time + "';\n"
                    + ""
                    + "var hours = mins/60;"
                    + "  \n"
                    + "        //calculate the seconds\n"
                    + "        var secs = mins * 60;\n"
                    + "  \n"
                    + "        //countdown function is evoked when page is loaded\n"
                    + "        function countdown() {\n"
                    + "            setTimeout('Decrement()', 60);\n"
                    + "        }\n"
                    + "  \n"
                    + "        //Decrement function decrement the value.\n"
                    + "        function Decrement() {\n"
                    + "            if (document.getElementById) "
                    + "    {"
                    + "                hours = document.getElementById('hours');"
                    + "                minutes = document.getElementById(\"minutes\");\n"
                    + "                seconds = document.getElementById(\"seconds\");\n"
                    + ""
                    + "        if(seconds == 1)"
                    + "{"
                    + "}"
                    + "else"
                    + "{"
                    + "     seconds.value = seconds.value - 1;"
                    + "}"
                    + ""
                    + ""
                    + "                //if less than a minute remaining\n"
                    + "                //Display only seconds value.\n"
                    + "                if (seconds < 59) {\n"
                    + "                    seconds.value = secs;\n"
                    + "                }\n"
                    + "  \n"
                    + "                //Display both minutes and seconds\n"
                    + "                //getminutes and getseconds is used to\n"
                    + "                //get minutes and seconds\n"
                    + "                else if(minutes<59){\n"
                    + "                    minutes.value = getminutes();\n"
                    + "                    seconds.value = getseconds();\n"
                    + "                }\n"
                    + "else"
                    + "{"
                    + "hours.value = gethours();"
                    + "minutes.value = getminutes();\n"
                    + "                    seconds.value = getseconds();\n"
                    + "}"
                    + "                //when less than a minute remaining\n"
                    + "                //colour of the minutes and seconds\n"
                    + "                //changes to red\n"
                    + "                if (mins < 1) {\n"
                    + "                    minutes.style.color = \"red\";\n"
                    + "                    seconds.style.color = \"red\";\n"
                    + "                }\n"
                    + "                //if seconds becomes zero,\n"
                    + "                //then page alert time up\n"
                    + "                if (hours < 0) {\n"
                    + "                    alert('time up');\n"
                    + "                    minutes.value = 0;\n"
                    + "                    seconds.value = 0;\n"
                    + "                    document.getElementById('t').submit();"
                    + "                }\n"
                    + "                //if seconds > 0 then seconds is decremented\n"
                    + "                else {\n"
                    + "                    secs--;\n"
                    + "                    setTimeout('Decrement()', 1000);\n"
                    + "                }\n"
                    + "            }\n"
                    + "        }\n"
                    + "  \n"
                    + "        function gethours() {\n"
                    + "            //minutes is seconds divided by 60, rounded down\n"
                    + "            hours = Math.floor(secs / 3600);\n"
                    + "            return hours;\n"
                    + "        }\n"
                    + "        function getminutes() {\n"
                    + "            //minutes is seconds divided by 60, rounded down\n"
                    + "            mins = Math.floor(hours * 60);\n"
                    + "            return mins;\n"
                    + "        }\n"
                    + "  \n"
                    + "        function getseconds() {\n"
                    + "            //take minutes remaining (as seconds) away \n"
                    + "            //from total seconds remaining\n"
                    + "            return secs - Math.round(mins *60);\n"
                    + "        }\n"
                    + "    </script>");

            out.println("<script>\n"
                    + "	\n"
                    + "	function starttimer(){\n"
                    + "\n"
                    + "		var h = document.getElementById(\"hour\");\n"
                    + "		var m = document.getElementById(\"min\");\n"
                    + "		var s = document.getElementById(\"sec\");\n"
                    + "\n"
                    + "		h.value =  '" + hour + "';"
                    + "		m.value = '" + min + "';"
                    + "		s.value = 1;"
                    + "\n"
                    + "		decrement();\n"
                    + "	}\n"
                    + "\n"
                    + "	function decrement()\n"
                    + "	{\n"
                    + "\n"
                    + "		var h = document.getElementById(\"hour\");\n"
                    + "		var m = document.getElementById(\"min\");\n"
                    + "		var s = document.getElementById(\"sec\");\n"
                    + "\n"
                    + "\n"
                    + "		if(h.value == 0 && m.value == 0 && s.value == 1){\n"
                    + "			setTimeout( function ( ) { alert( \"Your test is submitted\" ); }, 3000 );"
                    + "document.getElementById('t').submit();\n"
                    + "		}\n"
                    + "\n"
                    + "		if(s.value == 1)\n"
                    + "		{\n"
                    + "			if(m.value == 0)\n"
                    + "			{\n"
                    + "				if(h.value == 0){\n"
                    + "					s.value = 59;\n"
                    + "				}\n"
                    + "				else{\n"
                    + "					h.value = h.value - 1;\n"
                    + "					m.value = 59;\n"
                    + "					s.value = 59;\n"
                    + "				}\n"
                    + "			}\n"
                    + "			else{\n"
                    + "				m.value = m.value - 1;\n"
                    + "				s.value = 59;\n"
                    + "			}\n"
                    + "		}\n"
                    + "		else{\n"
                    + "			s.value = s.value - 1;\n"
                    + "		}\n"
                    + "\n"
                    + "		setTimeout('decrement()', 1000);\n"
                    + "\n"
                    + "	}\n"
                    + "\n"
                    + "</script>");

            out.println("<style>\n"
                    + "	body{\n"
                    + "		background: white;\n"
                    + "		}\n"
                    + "	form{\n"
                    + "		text-align: center;\n"
                    + "		\n"
                    + "		width: 800px;\n"
                    + "		margin:auto;\n"
                    + "		background: #fff;\n"
                    + "		border-radius: 15px;\n"
                    + "		box-shadow: rgba(221, 221, 221, 0.99) 1px 0px 20px;\n"
                    + "\n"
                    + "	}\n"
                    + "	.d{\n"
                    + "		margin-left: 20px; \n"
                    + "		margin-right: 298px;\n"
                    + "		margin-top: 60px;\n"
                    + "		width: 800px;\n"
                    + "		height: 100px;\n"
                    + "		padding: 20px 7px;\n"
                    + "		outline: none;\n"
                    + "		border: 2.5px solid black;\n"
                    + "		border-radius: 15px;\n"
                    + "         max-width: 800px;"
                    + "	}"
                    + "	.d1+.labels{\n"
                    + "align-items:center;"
                    + "         width:450px;"
                    + "         height:25px;"
                    + "		border: 2px solid grey;\n"
                    + "         display:flex;"
                    + "flex:none;"
                    + "	}\n"
                    + ".d1:hover{"
                    + "cursor:pointer;"
                    + "}"
                    + ".d1:checked + .labels{"
                    + "background-color:#F6D8D8;\n"
                    + "color:black;"
                    + "}"
                    + ""
                    + "	.sum{\n"
                    + "		margin-left: -1050px;\n"
                    + "		 margin-top: 50px;\n"
                    + "		 border-radius: 3px;\n"
                    + "	 \n"
                    + "		box-shadow: inset 0 1px 0 #8dc2f0;\n"
                    + "	width: 160px;\n"
                    + "	height: 35px;\n"
                    + "	\n"
                    + "	border-radius: 30px;\n"
                    + "		\n"
                    + "	background-color: green;\n"
                    + "	cursor: pointer;\n"
                    + "	color: white;\n"
                    + "	font-weight: bold;\n"
                    + "	text-shadow: 0 -1px 0 #336895;\n"
                    + "	\n"
                    + "	font-size: 15px;\n"
                    + "	}\n"
                    + "	</style>");

            out.println(""
                    + "<script>\n"
                    + "	var i = 1;\n"
                    + "	\n"
                    + ""
                    + ""
                    + "		function addelement(que,o1,o2,o3,o4,ca,m)\n"
                    + "		{"
                    + ""
                    + ""
                    + ""
                    + "\n"
                    + "				\n"
                    + "                         "
                    + "				var abc = document.createElement('textarea');\n"
                    + "                          const newText = que.replace(/12qpmzx9/g, '\\n'); "
                    + ""
                    + ""
                    + ""
                    + ""
                    + "					abc.type='text';\n"
                    + "					abc.className = 'd';\n"
                    + "					abc.name = \"a\"+i;\n"
                    + "					abc.value=newText;"
                    + "                                  \n"
                    + "					abc.setAttribute(\"required\",true);\n"
                    + "					abc.readOnly = true;\n"
                    + "					\n"
                    + "				var opt1 = document.createElement('input');\n"
                    + "					opt1.type='radio';\n"
                    + "					opt1.className = 'd1';\n"
                    + "					opt1.name = \"a\"+i+\"a\";\n"
                    + "					opt1.value=1;\n"
                    + "					opt1.setAttribute(\"required\",true);\n"
                    + "                         var label1 = document.createElement('input');\n"
                    + "                                     label1.type = 'text'; "
                    + "                                     label1.value=o1;"
                    + "                                     label1.readOnly = true;"
                    + "                                     label1.name = 'a'+i+'1';"
                    + "                                     label1.className='labels';	"
                    + "				var opt2 = document.createElement('input');\n"
                    + "					opt2.type='radio';\n"
                    + "					opt2.className = 'd1';\n"
                    + "					opt2.name = \"a\"+i+\"a\";\n"
                    + "					opt2.value=2;\n"
                    + "					opt2.setAttribute(\"required\",true);\n"
                    + "						\n"
                    + "					\n"
                    + "                         var label2 = document.createElement('input');\n"
                    + "                                     label2.type = 'text'; "
                    + "                                     label2.value=o2;"
                    + "                                     label2.readOnly = true;"
                    + "                                      label2.name = 'a'+i+'2'; "
                    + "                                     label2.className='labels';	"
                    + "					"
                    + "				var opt3 = document.createElement('input');\n"
                    + "					opt3.type='radio';\n"
                    + "					opt3.className = 'd1';\n"
                    + "					opt3.name = \"a\"+i+\"a\";\n"
                    + "					opt3.value=3;\n"
                    + "					opt3.setAttribute(\"required\",true);\n"
                    + "                         var label3 = document.createElement('input');\n"
                    + "                                     label3.type = 'text'; "
                    + "                                     label3.value=o3;"
                    + "                                     label3.readOnly = true;"
                    + "                                     label3.name = 'a'+i+'3';"
                    + "                                    label3.className='labels';						"
                    + ""
                    + "					\n"
                    + "				var opt4 = document.createElement('input');\n"
                    + "					opt4.type='radio';\n"
                    + "					opt4.className = 'd1';\n"
                    + "					opt4.name = \"a\"+i+\"a\";\n"
                    + "					opt4.value=4;\n"
                    + "					opt4.setAttribute(\"required\",true);\n"
                    + "					\n"
                    + "                         var label4 = document.createElement('input');\n"
                    + "                                     label4.type = 'text'; "
                    + "                                     label4.value=o4;"
                    + "                                     label4.name = 'a'+i+'4';"
                    + "                                    label4.className='labels';						"
                    + ""
                    + "                                     label4.readOnly = true;"
                    + "					\n"
                    + "				var cans = document.createElement('input');\n"
                    + "					cans.type='number';\n"
                    + "					cans.name = \"a\"+i+\"z\";\n"
                    + "					\n"
                    + "					cans.value=ca;\n"
                    + "					cans.style.visibility = \"hidden\";\n"
                    + "                         var marks = document.createElement('input');\n"
                    + "                                     marks.type = 'number'; "
                    + "                                     marks.value=m;"
                    + "                                     marks.name = 'a'+i+'m';"
                    + "                                     marks.className='labels';						"
                    + ""
                    + "                                     marks.readOnly = true;"
                    + "					\n"
                    + "				\n"
                    + "				document.getElementById(\"t\").appendChild(abc);\n"
                    + "				document.getElementById(\"t\").appendChild(opt1);"
                    + "                         document.getElementById(\"t\").appendChild(label1);"
                    + "				document.getElementById(\"t\").appendChild(opt2);\n"
                    + "                         document.getElementById(\"t\").appendChild(label2);"
                    + "				document.getElementById(\"t\").appendChild(opt3);\n"
                    + "                         document.getElementById(\"t\").appendChild(label3);"
                    + "				document.getElementById(\"t\").appendChild(opt4);\n"
                    + "                         document.getElementById(\"t\").appendChild(label4);"
                    + "				document.getElementById(\"t\").appendChild(cans);\n"
                    + "                         document.getElementById(\"t\").appendChild(marks);"
                    + ""
                    + ""
                    + "				\n"
                    + "				i++;\n"
                    + "				\n"
                    + "			}\n"
                    + "	\n"
                    + "	\n"
                    + "	\n"
                    + "</script>");

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StartTest</title>");
            out.println("</head>");
            out.println("<body onload='starttimer();' oncontextmenu=\"return false;\">"
                    + "<div>\n"
                    + "        Time Left ::\n"
                    + "        <input id=\"hour\" type=\"text\" style=\"width: 100px;\n"
                    + "             border: none; font-size: 16px; \n"
                    + "            font-weight: bold; color: black;\"><font size=\"5\"> :\n"
                    + "                        </font>\n"
                    + "        <input id=\"min\" type=\"text\" style=\"width: 100px;\n"
                    + "             border: none; font-size: 16px; \n"
                    + "            font-weight: bold; color: black;\"><font size=\"5\"> :\n"
                    + "                        </font>\n"
                    + "        <input id=\"sec\" type=\"text\" style=\"width: 100px;\n"
                    + "                        border: none; font-size: 16px;\n"
                    + "                        font-weight: bold; color: black;\">\n"
                    + "    </div>"
                    + "<br><br>"
                    + "<div id =\"newelement\">	\n"
                    + "		<form method =\"post\" action=\"Result\" id=\"t\" target='_parent'>\n"
                    + "				<button type='submit'  class=\"sum\" >Submit</button>	\n"
                    + "				<br>\n"
                    + "		</form>\n"
                    + "	</div>"
                    + "");

            pd = c.prepareStatement("select * from test_questions where test_id = ?");
            pd.setInt(1, Integer.parseInt(test_id));

            pd.execute();

            rs = pd.executeQuery();
            int length = 0;
            while (rs.next()) {

                String question = rs.getString("question_name");

                String option_a = rs.getString("option_a");
                String option_b = rs.getString("option_b");
                String option_c = rs.getString("option_c");
                String option_d = rs.getString("option_d");
                String cans = rs.getString("option_answer");
                String marks = rs.getString("marks");
                length++;
                out.println("<script> 	addelement('" + question + "' , '" + option_a + "' , '" + option_b + "' , '" + option_c + "' , '" + option_d + "' , '" + cans + "' , '" + marks + "'); </script>");

            }

            session.setAttribute("length", length);

            session.setAttribute("test_id", test_id);
            out.println("</body>");
            out.println("</html>");

            out.println("<script>\n"
                    + "\n"
                    + ""
                    + ""
                    // back button
                    + "function preventBack() { window.history.forward(); }  \n"
                    + "    setTimeout(\"preventBack()\", 0);  \n"
                    + "    window.onunload = function () { null }; "
                    + ""
                    + ""
                    // inspect
                    + ""
                    + "    document.onkeydown = function (e) {\n"
                    + "        if (event.keyCode == 123) {\n"
                    + "            return false;\n"
                    + "        }\n"
                    + "        if (e.ctrlKey && e.shiftKey && (e.keyCode == 'I'.charCodeAt(0) || e.keyCode == 'i'.charCodeAt(0))) {\n"
                    + "            return false;\n"
                    + "        }\n"
                    + "        if (e.ctrlKey && e.shiftKey && (e.keyCode == 'C'.charCodeAt(0) || e.keyCode == 'c'.charCodeAt(0))) {\n"
                    + "            return false;\n"
                    + "        }\n"
                    + "        if (e.ctrlKey && e.shiftKey && (e.keyCode == 'J'.charCodeAt(0) || e.keyCode == 'j'.charCodeAt(0))) {\n"
                    + "            return false;\n"
                    + "        }\n"
                    + "        if (e.ctrlKey && (e.keyCode == 'U'.charCodeAt(0) || e.keyCode == 'u'.charCodeAt(0))) {\n"
                    + "            return false;\n"
                    + "        }\n"
                    + "        if (e.ctrlKey && (e.keyCode == 'S'.charCodeAt(0) || e.keyCode == 's'.charCodeAt(0))) {\n"
                    + "            return false;\n"
                    + "        }\n"
                    + "    }\n"
                    + "</script>    ");

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
            Logger.getLogger(StartTest.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StartTest.class
                    .getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(StartTest.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StartTest.class
                    .getName()).log(Level.SEVERE, null, ex);
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
