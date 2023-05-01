/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Staff;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Take_Test_Details extends HttpServlet {

    private int Generate_Random_Test_ID(Connection c) throws SQLException {

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("select * from test_headers order by test_id desc");

        if (rs.next()) {   // if record exists in table

            int id = (rs.getInt("test_id") + 1);
            return id;
        }
        return 1;

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {

        Connection c = Classes.Connect_To_Database.connect();
        int id = Generate_Random_Test_ID(c);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println(""
                    + "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head>\n"
                    + "	<meta charset=\"utf-8\">\n"
                    + "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no\">\n"
                    + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n"
                    + "<meta name=\"HandheldFriendly\" content=\"true\">\n"
                    + "  <title>Admin page</title>\n"
                    + "   <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css\">\n"
                    + "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js\" charset=\"utf-8\"></script>\n"
                    + "	\n"
                    + "	<style>\n"
                    + "		\n"
                    + "	\n"
                    + "\n"
                    + "body {\n"
                    + "	font-family: Elephant;\n"
                    + "	background-color: #fff;\n"
                    + "}\n"
                    + "\n"
                    + "h2 {\n"
                    + "	color: #336895;\n"
                    + "}\n"
                    + "\n"
                    + "/* NAVIGATION */\n"
                    + "\n"
                    + "nav {\n"
                    + "	position: fixed;\n"
                    + "	top: 10px;\n"
                    + "	left: 10px;\n"
                    + "}\n"
                    + "\n"
                    + "nav a {\n"
                    + "	color: #4889C2;\n"
                    + "	font-weight: bold;\n"
                    + "	text-decoration: none;\n"
                    + "	opacity: .3;\n"
                    + "	-moz-transition: all .4s;\n"
                    + "}\n"
                    + "\n"
                    + "nav a:hover {\n"
                    + "	opacity: 1;\n"
                    + "}\n"
                    + "\n"
                    + "nav a.focus {\n"
                    + "	opacity: 1;\n"
                    + "}\n"
                    + "\n"
                    + "/* LOGIN & REGISTER FORM */\n"
                    + "\n"
                    + "form {\n"
                    + "	width: 380px;\n"
                    + "	height: 500px;\n"
                    + "	margin: 200px auto;\n"
                    + "	background: #fff;\n"
                    + "	border: 1px solid lightgrey;\n"
                    + "	border-radius: 3px;\n"
                    + "	box-shadow: white(0, 0, 0, 0.3) 0px 19px 38px, rgba(0, 0, 0, 0.22) 0px 15px 12px;\n"
                    + "	text-align: center;\n"
                    + "	padding-top: 1px;\n"
                    + "	margin-top: 100px;\n"
                    + "}\n"
                    + "\n"
                    + "form.register{																				\n"
                    + "	height: 420px;\n"
                    + "}\n"
                    + "\n"
                    + "form .text-field {																			\n"
                    + "	border: 1.3px solid #86b5f2;\n"
                    + "	width: 280px;\n"
                    + "	height: 40px;\n"
                    + "	border-radius: 3px;\n"
                    + "	margin-top: 10px;\n"
                    + "	padding-left: 10px;\n"
                    + "	color: blue;\n"
                    + "	background: #fff;\n"
                    + "	outline: none;\n"
                    + "}\n"
                    + "\n"
                    + "form .text-field:focus {\n"
                    + "	box-shadow: inset 0 0 2px rgba(0,0,0, .3);\n"
                    + "	color: black;\n"
                    + "	\n"
                    + "}\n"
                    + "\n"
                    + "\n"
                    + ".focus{\n"
                    + "	margin-left: 5px;\n"
                    + "\n"
                    + "}\n"
                    + " .button {																				\n"
                    + "	border-radius: 7px;\n"
                    + "	border: 1px white;\n"
                    + "	box-shadow: inset 0 1px 0 #8dc2f0;\n"
                    + "	width: 288px;\n"
                    + "	height: 40px;\n"
                    + "	margin-top: 60px;\n"
                    + "	\n"
                    + "	cursor: pointer;\n"
                    + "	color: white;\n"
                    + "	text-shadow: 0 -1px 0 #336895;\n"
                    + "	background-color: #1a73e8;\n"
                    + "	font-size: 16px;\n"
                    + "}\n"
                    + "\n"
                    + "\n"
                    + "form .button:hover {\n"
                    + "	background: linear-gradient(bottom, #1ED515 0%, #6bafea 100%);\n"
                    + "	background: -o-linear-gradient(bottom, #1ED515 0%, #6bafea 100%);\n"
                    + "	background: -moz-linear-gradient(bottom, #1ED515 0%, #6bafea 100%);\n"
                    + "	background: -webkit-linear-gradient(bottom, #1ED515 0%, #6bafea 100%);\n"
                    + "	background: -ms-linear-gradient(bottom, #1ED515 0%, #6bafea 100%);\n"
                    + "\n"
                    + "}\n"
                    + "\n"
                    + "form .button:active {\n"
                    + "	box-shadow: inset 0 0 2px rgba(0,0,0, .3), 0 1px 0 white;\n"
                    + "	background-color: #1a73e8;\n"
                    + "}\n"
                    + "\n"
                    + "	</style>\n"
                    + "\n"
                    + "\n"
                    + "</head>\n"
                    + "\n"
                    + "<body>\n"
                    + "<form method=\"post\" action='Save_Quiz_Details'>\n"
                    + "\n"
                    + "	<label> <h2 style=\"color: #3c88eb; font-family: sans-serif; margin-top: 30px; \">Create Test</h2> </label>\n"
                    + "		<input type=\"number\" name=\"id\" class=\"text-field\"  style=\"margin-top: 30px;\" value= '" + id + "' readonly//>\n"
                    + "		<input type=\"text\" name=\"title\" class=\"text-field\" placeholder=\"Title\" required />\n"
                    + "<div class=\"select\" id='select'>\n"
                    + "        <select id='class' name='class' class=\"text-field\" style='width: 295px ;' >\n"
                    + "            <option hidden=\"\" value='null'>Class</option>\n"
                    + "            <option name='class' value='07'>07</option>\n"
                    + "            <option name='class' value='08'>08</option>\n"
                    + "            <option name='class' value='09'>09</option>\n"
                    + "            <option name='class' value='10'>10</option>\n"
                    + "        </select>\n"
                    + "      </div>"
                    + "<div class=\"select\" id='select'>\n"
                    + "        <select id='div' name='div' class=\"text-field\" style='width: 295px ;' >\n"
                    + "            <option hidden=\"\" value='null'>Division</option>\n"
                    + "            <option name='div' value='0'>ALL</option>\n"
                    + "            <option name='div' value='A'>A</option>\n"
                    + "            <option name='div' value='B'>B</option>\n"
                    + "        </select>\n"
                    + "      </div>"
                    + "	    <input type=\"text\" name=\"pass\" class=\"text-field\" placeholder=\"Test Time in Minutes\" required />\n"
                    + "    \n"
                    + "		<input type=\"submit\"  class=\"button\" value=\"Submit\">\n"
                    + "\n"
                    + "</form>\n"
                    + "\n"
                    + "</body>\n"
                    + "\n"
                    + "</html>"
                    + ""
                    + "");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Take_Test_Details.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Take_Test_Details.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Take_Test_Details.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Take_Test_Details.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
