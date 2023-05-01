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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author anubh
 */
public class EditStudent extends HttpServlet {

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

            Connection c = Connect_To_Database.connect();

            String ar[] = request.getParameter("l").split(",");
            String id = ar[0];
            int op = Integer.parseInt(ar[1]);

            if (id.equals("null")) {
                String sql = "delete from student where userid is null";
                PreparedStatement pd = c.prepareStatement(sql);
                pd.execute();
                out.println("<script>window.location.replace('./DisplayStudentData');  </script> ");
                return;
            }

            if (op == 1) {
                String sql = "select * from student where userid=? and del=1";

                PreparedStatement pd = c.prepareStatement(sql);
                pd.setString(1, id);

                ResultSet rs = pd.executeQuery();
                rs.next();
                String name = rs.getString("name");
                String rollno = rs.getString("rollno");
                String cla = rs.getString("class");
                String division = rs.getString("division");
                String phoneno = rs.getString("phoneno");
                String password = rs.getString("password");

                out.println(" <link rel='stylesheet' href='AddStudent.css'>");

                out.print("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "<head>\n"
                        + "  <meta charset=\"utf-8\">\n"
                        + "  <meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\">\n"
                        + "\n"
                        + "  <title>Forms / Elements - NiceAdmin Bootstrap Template</title>\n"
                        + "  <meta content=\"\" name=\"description\">\n"
                        + "  <meta content=\"\" name=\"keywords\">\n"
                        + "\n"
                        + "\n"
                        + "\n"
                        + "  <!-- Google Fonts -->\n"
                        + "  <link href=\"https://fonts.gstatic.com\" rel=\"preconnect\">\n"
                        + "  <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i\" rel=\"stylesheet\">\n"
                        + "\n"
                        + "  <!-- Vendor CSS Files -->\n"
                        + "  <link href=\"bootstrap.min.css\" rel=\"stylesheet\">\n"
                        + "\n"
                        + "  <!-- Template Main CSS File -->\n"
                        + "  <link href=\"style.css\" rel=\"stylesheet\">\n"
                        + "  \n"
                        + "  <style>\n"
                        + "      .card{\n"
                        + "          border: solid;\n"
                        + "          border-radius: 10px;\n"
                        + "      }\n"
                        + " </style>\n"
                        + "\n"
                        + "  \n"
                        + "</head>\n"
                        + "\n"
                        + "\n"
                        + "<body>\n"
                        + " <section class=\"section\">\n"
                        + "      <div class=\"row\">\n"
                        + "        <div class=\"col-lg-6\">\n"
                        + "\n"
                        + "          <div class=\"card\" style=\"margin-left: 100px;margin-top:30px;width: 900px\">\n"
                        + "            <div class=\"card-body\">\n"
                        + "                <h3 class=\"card-title\"><center><h2><b>UPDATE STUDENT RECORD</b></h2></center></h3>\n"
                        + "                <br><br>\n"
                        + "              <!-- General Form Elements -->\n"
                        + "              <form action='SaveEditedStudent' method=\"POST\">\n"
                        + "                <div class=\"row mb-3\">\n"
                        + "                    <label for=\"userid\" class=\"col-sm-2 col-form-label\"><b>Gr. Number</b></label>\n"
                        + "                  <div class=\"col-sm-10\">\n"
                        + "                    <input type=\"text\" class=\"form-control\" id = \"userid\" name=\"userid\" value='" + id + "' readonly>\n"
                        + "                  </div>\n"
                        + "                </div>\n"
                        + "                <div class=\"row mb-3\">\n"
                        + "                    <label for=\"password\" class=\"col-sm-2 col-form-label\"><b>Password</b></label>\n"
                        + "                  <div class=\"col-sm-10\">\n"
                        + "                    <input type=\"text\" class=\"form-control\" name=\"password\" id=\"password\" value='" + password + "' required>\n"
                        + "                  </div>\n"
                        + "                </div>\n"
                        + "                <div class=\"row mb-3\">\n"
                        + "                  <label for=\"name\" class=\"col-sm-2 col-form-label\"><b>Name</b></label>\n"
                        + "                  <div class=\"col-sm-10\">\n"
                        + "                    <input type=\"text\" class=\"form-control\" id=\"name\" name=\"name\" value='" + name + "' required>\n"
                        + "                  </div>\n"
                        + "                </div>\n"
                                
                        + "                <div class=\"row mb-3\">\n"
                        + "                  <label for=\"class\" class=\"col-sm-2 col-form-label\"><b>Class</b></label>\n"
                        + "                  <div class=\"col-sm-10\">\n"
                        + "                    <input type=\"text\" class=\"form-control\" id=\"class\" name=\"class\" value='" + cla + "' required>\n"
                        + "                  </div>\n"
                        + "                </div>\n"
                                
                        + "                <div class=\"row mb-3\">\n"
                        + "                  <label for=\"division\" class=\"col-sm-2 col-form-label\"><b>Division</b></label>\n"
                        + "                  <div class=\"col-sm-10\">\n"
                        + "                    <input type=\"text\" class=\"form-control\" id=\"division\" name=\"division\" value='" + division + "' required>\n"
                        + "                  </div>\n"
                        + "                </div>\n"
                                
                        + "                  <div class=\"row mb-3\">\n"
                        + "                      <label for=\"rollno\" class=\"col-sm-2 col-form-label\"><b>Roll Number</b></label>\n"
                        + "                  <div class=\"col-sm-10\">\n"
                        + "                    <input type=\"number\" class=\"form-control\" id=\"rollno\" name=\"rollno\" value='" + rollno + "' required>\n"
                        + "                  </div>\n"
                        + "                </div>\n"
                        + "                  \n"
                        + "                  <div class=\"row mb-3\">\n"
                        + "                  <label for=\"phoneno\" class=\"col-sm-2 col-form-label\"><b>Phone Number</b></label>\n"
                        + "                  <div class=\"col-sm-10\">\n"
                        + "                    <input type=\"number\" class=\"form-control\" name=\"phoneno\" id=\"phoneno\" value='" + phoneno + "' required>\n"
                        + "                  </div>\n"
                        + "                </div>\n"
                        + "                <br>\n"
                        + "\n"
                        + "                <div class=\"row mb-3\">\n"
                        + "                  \n"
                        + "                  <div class=\"col-sm-10\">\n"
                        + "                    <button type=\"submit\" onClick='call()' class=\"btn btn-dark\" style=\"background-color:  #ff0800\" >UPDATE</button>\n"
                        + "                  </div>\n"
                        + "                </div>\n"
                        + "\n"
                        + "              </form><!-- End General Form Elements -->\n"
                        + "\n"
                        + "            </div>\n"
                        + "          </div>\n"
                        + "\n"
                        + "        </div>\n"
                        + "\n"
                        + "        </div>\n"
                        + "    </section>\n"
                        + "</body>\n"
                        + "</html>");
                
                
                

                /*out.println("<!DOCTYPE html>");

                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet EditStudent</title>");
                out.println("</head>");

              
                out.println("<div class='edit-student'>"
                        + "      <form method='post' action='SaveEditedStudent' autocomplete='off' class='form' >"
                        + "      <center><h2>UPDATE STUDENT RECORD</h2></center>"
                        + "      <br>"
                        + "      <br>"
                        + "      <br>"
                        + "      <div class='input'>"
                        + "        <input type='text' id='fname' name='id' value='" + id + "' required onfocus='blur()'>"
                        + "        <label for='userid'>GR No.</label>"
                        + "      </div>"
                        + "      <div class='input'>"
                        + "        <input type='text' id='fname' name='password' value='" + password + "' required>"
                        + "        <label for='password'>Password</label>"
                        + "      </div>"
                        + "      <div class='input'>"
                        + "        <input type='text' id='fname' name='class' value='" + cla + "' required>"
                        + "        <label for='class'>Class</label>"
                        + "      </div>"
                        + "      <div class='input'>"
                        + "        <input type='text' id='fname' name='division' value='" + division + "' required>"
                        + "        <label for='division'>Division</label>"
                        + "      </div>"
                        + "      <div class='input'>"
                        + "        <input type='text' id='fname' name='rollno' value='" + rollno + "' required>"
                        + "        <label for='rollno'>Roll No.</label>"
                        + "      </div>"
                        + "      <div class='input'>"
                        + "        <input type='text' id='fname' name='name' value='" + name + "' required>"
                        + "        <label for='name'>Name</label>"
                        + "      </div>"
                        + "      <div class='input'>"
                        + "        <input type='text' id='fname' name='phoneno' value='" + phoneno + "' required>"
                        + "        <label for=\"phoneno\">Phone No.</label>"
                        + "      </div>"
                        + "      <button type=\"submit\" class=\"submit-btn\">UPDATE</button>\n"
                        + "    </form>"
                        + "  </div>"
                        + "");
                out.println("</body></html>");*/
            } else {
                if (op == 2) {
                    String sql = "update student set del=0 where userid=?";

                    PreparedStatement pd = c.prepareStatement(sql);
                    pd.setString(1, id);

                    ResultSet rs = pd.executeQuery();

                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet EditStudent</title>");
                    out.println("</head>");
                    out.println("<body> ");
                    out.println("<script> "
                            + "alert('Student Record Placed in Bin'); window.location.replace('./DisplayStudentData');"
                            + "</script> ");
                    out.println("</body></html>");
                } else {
                    out.println("<script> "
                            + " window.location.replace('./DisplayStudentData');"
                            + "</script> ");
                }

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
        } catch (SQLException ex) {
            Logger.getLogger(EditStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditStudent.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EditStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditStudent.class.getName()).log(Level.SEVERE, null, ex);
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
