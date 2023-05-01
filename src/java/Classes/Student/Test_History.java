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
public class Test_History extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */

            HttpSession session = request.getSession();
            String student_id = (String) session.getAttribute("student_id");
            String temp;
            String color = "#d1ffd1";
            Connection c = Classes.Connect_To_Database.connect();

            PreparedStatement pd = c.prepareStatement("select * from test_history where student_id = ?");
            pd.setString(1, student_id);

            ResultSet rs = pd.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>"
                    + "	<head>\n"
                    + "			<style>\n"
                    + "					table {\n"
                    + "  font-family: arial, sans-serif;\n"
                    + "  border-collapse: collapse;\n"
                    + "  width: 95%;\n"
                    + "  margin-left: -5px;\n"
                    + "  overflow-y: auto;\n"
                    + "  color: #17252a;\n"
                    + "  margin-left: 30px;\n"
                    + "  border-collapse: separate;\n"
                    + "  border-spacing: 0 15px;\n"
                    + "}\n"
                    + "\n"
                    + "td, th {\n"
                    + "\n"
                    + "  text-align: center;\n"
                    + "  padding-left: 12px;\n"
                    + "  padding-bottom: 6px;\n"
                    + "  color:#17252a;\n"
                    + "  line-height: 37px;\n"
                    + "  padding-left:  40px;\n"
                    + "}\n"
                    + "		\n"
                    + "\n"
                    + " .score{\n"
                    + "				margin-top: 10px;\n"
                    + "				margin-left: 520px;\n"
                    + "				height: 40px;\n"
                    + "				width: 400px;\n"
                    + "				font-size: 30px;\n"
                    + "				border: 1px solid white;\n"
                    + "		}\n"
                    + "		\n"
                    + "		\n"
                    + "\n"
                    + "		</style>\n"
                    + "	</head>\n"
                    + "	\n"
                    + "	\n"
                    + "	<body>\n"
                    + "		<br><input type = \"text\" value=\"\" id=\"score\" class=\"score\"><br><br><br>\n"
                    + "		<table>\n"
                    + "		<th>Test ID</th>\n"
                    + "		<th>Test Name </th>\n"
                    + "		<th>Marks Obtained</th>\n"
                    + "		<th>Total Marks</th>\n"
                    + "		<th>Percentage</th>"
                    + "		<th>Test Date</th>\n");

            if (rs.next()) {

                temp = rs.getString("test_ids");    //test given
                String test_ids[] = temp.split(",");

                temp = rs.getString("marks");   //marks
                String marks[] = temp.split(",");

                for (int i = 1; i < marks.length; i++) {

                    pd = c.prepareStatement("select * from test_headers where test_id = ? ");

                    pd.setInt(1, Integer.parseInt(test_ids[i]));
                    rs = pd.executeQuery();
                    rs.next();
                    String test_date = rs.getString("test_date");
                    String t[] = marks[i].split("/");
                    float temp1 = Float.parseFloat(t[0]);
                    float temp2 = Float.parseFloat(t[1]);
                    float percentage = temp1 * 100 / temp2;

                    out.println("<tr style='background-color: " + color + "'>   "
                            + "					<td>" + test_ids[i] + "</td>"
                            + "					<td>" + rs.getString("test_name") + "</td>"
                            + "					<td>" + temp1 + "</td>"
                            + "					<td>" + temp2 + "</td>"
                            + "					<td> " + percentage + "% </td>"
                            + "					<td> " + test_date + " </td>"
                            + "		</tr>");

                }

            }
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
            Logger.getLogger(Test_History.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Test_History.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Test_History.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Test_History.class.getName()).log(Level.SEVERE, null, ex);
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
