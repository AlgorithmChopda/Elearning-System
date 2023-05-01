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
import java.util.Date;
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
public class Result extends HttpServlet {

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

            HttpSession session = request.getSession();

            String student_id = (String) session.getAttribute("student_id");
            String test_id = (String) session.getAttribute("test_id");

            Connection c = Classes.Connect_To_Database.connect();

            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>\n"
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
                    + "<form action='StudentDashboard.html' method='post'>"
                    + "<button type='submit'>OK</button>"
                    + "</form>"
                    + "		<br><input type = \"text\" value=\"\" id=\"score\" class=\"score\" readonly><br><br><br>\n"
                    + "		<table>\n"
                    + "		<th>NO</th>\n"
                    + "		<th>Question </th>\n"
                    + "		<th>Selected Answer</th>\n"
                    + "		<th>Correct Answer</th>\n"
                    + "		<th>Marks</th>\n"
                    + "		<th>Status</th>");

            int length = (int) session.getAttribute("length");

            int total_marks = 0;
            int obtained_marks = 0;

            for (int i = 1; i <= length; i++) {

                String que = request.getParameter("a" + i);
                String selected = request.getParameter("a" + i + "a");
                String correct = request.getParameter("a" + i + "z");
                String label = request.getParameter("a" + i + "" + selected);
                String answer = request.getParameter("a" + i + "" + correct);
                String marks = request.getParameter("a" + i + "m");

                total_marks += Integer.parseInt(marks);
                String color;
                String status;

                if (label == null) { // question not answered                

                    color = "#ffc6c4";
                    status = "Wrong";

                    out.println("<tr style='background-color: " + color + "'>   "
                            + "					<td>" + i + "</td>"
                            + "					<td>" + que + "</td>"
                            + "					<td> --- </td>"
                            + "					<td> " + marks + " </td>"
                            + "					<td>" + answer + "</td>"
                            + "					<td>" + status + "</td>"
                            + "		</tr>");

                    continue;

                }

                if (selected.equalsIgnoreCase(correct)) {
                    color = "#d1ffd1";
                    status = "Correct";
                    obtained_marks += Integer.parseInt(marks);
                } else {
                    color = "#ffc6c4";
                    status = "Wrong";
                }

                out.println("<tr style='background-color: " + color + "'>   "
                        + "					<td>" + i + "</td>"
                        + "					<td>" + que + "</td>"
                        + "					<td>" + label + "</td>"
                        + "					<td>" + answer + "</td>"
                        + "					<td> " + marks + " </td>"
                        + "					<td>" + status + "</td>"
                        + "		</tr>");

            }
            
            out.println("</table>");
            //out.println("<h1>Servlet Result at " + request.getContextPath() + "</h1>");
            out.println("	<script>\n"
                    + "				var ele = document.getElementById(\"score\");\n"
                    + "				ele.value='Final Score: " + obtained_marks + " / " + total_marks + "';"
                    + "		</script>");
            out.println("</body>");
            out.println("</html>");

            PreparedStatement pd = c.prepareStatement("select * from test_history where student_id = ?");
            pd.setString(1, student_id);
            System.out.println(student_id);
            ResultSet rs = pd.executeQuery();

            Date d = new Date();
            
            if (rs.next()) {
                System.out.println("if");
                String time = "";
                if(rs.getString("end_time") == null)
                    time = ","+d.getHours()+": "+d.getMinutes()+": "+d.getSeconds();
                else 
                    time = rs.getString("end_time")+","+d.getHours()+": "+d.getMinutes()+": "+d.getSeconds();
                System.out.println(time);
                String test_ids = rs.getString("test_ids");
                test_ids = test_ids + "," + test_id;

                String marks = rs.getString("marks");
                marks = marks + "," + obtained_marks + "/" + total_marks;

                pd = c.prepareStatement("update test_history set test_ids=?, marks=?, end_time=? where student_id = ?");

                pd.setString(1, test_ids);
                pd.setString(2, marks);
                pd.setString(3, time);
                pd.setString(4, student_id);

                pd.execute();

            } 
            else 
            {
                System.out.println("else");
                String time = ","+d.getHours()+": "+d.getMinutes()+": "+d.getSeconds();
                System.out.println(time);
                String test_ids = "," + test_id;
                String marks = "," + obtained_marks + "/" + total_marks;

                pd = c.prepareStatement("insert into test_history values(?,?,?,?,?,?)");

                pd.setString(1, student_id);
                pd.setString(2, test_ids);
                pd.setString(3, marks);
                pd.setString(4, (String) session.getAttribute("class"));
                pd.setString(5, (String) session.getAttribute("division"));
                pd.setString(6, time);

                pd.execute();
            }

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
            Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, ex);
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
