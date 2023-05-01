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

/**
 *
 * @author anubh
 */
public class Upload_Files extends HttpServlet {

    private int Generate_Random_Test_ID(Connection c) throws SQLException {

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("select * from file_upload order by file_id desc");

        if (rs.next()) {   // if record exists in table
            int id = (rs.getInt("file_id") + 1);
            return id;
        }
        return 1;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            Connection c = Classes.Connect_To_Database.connect();
            int file_id = Generate_Random_Test_ID(c);

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Upload_Files</title>");
            out.println("</head>");
            out.println("<body>"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.19.1/css/mdb.min.css\" rel=\"stylesheet\">"
                    + "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css\" rel=\"stylesheet\">"
                    + "<link href=\"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap\" rel=\"stylesheet\">"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.6.0/mdb.min.css\" rel=\"stylesheet\" >"
   
            );
            
            out.println("<style>"
                    + "* {\n"
                    + "  box-sizing: border-box;\n"
                    + "  -moz-box-sizing: border-box;\n"
                    + "  -webkit-box-sizing: border-box;\n"
                    + "}\n"
                    + "\n"
                    + "body {\n"
                    + "  font-family: 'Montserrat', sans-serif;\n"
                    + "  background: white;\n"
                    + "}\n"
                    + "\n"
                    + ".wrapper {\n"
                    + "  margin: auto;\n"
                    + "  max-width: 640px;\n"
                    + "  padding-top: 60px;\n"
                    + "  text-align: center;\n"
                    + "}\n"
                    + "\n"
                    + ""
                    + ".container {\n"
                    + "  background-color: white;\n"
                    + "  padding: 20px;\n"
                    + "  border-radius: 10px;\n"
                    + ""
                    + "box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 2px 6px 2px;"
                    + "}\n"
                    + "\n"
                    + "h1 {\n"
                    + "  color: #130f40;\n"
                    + "  font-family: 'Varela Round', sans-serif;\n"
                    + "  letter-spacing: -.5px;\n"
                    + "  font-weight: 700;\n"
                    + "  padding-bottom: 10px;\n"
                    + "}\n"
                    + "\n"
                    + ""
                    + ""
                    + "\n"
                    + ".border-container {\n"
                    + "  border: 5px dashed rgba(198, 198, 198, 0.65);\n"
                    + "/*   border-radius: 4px; */\n"
                    + "  padding: 20px;\n"
                    + "}\n"
                    + "\n"
                    + ".border-container p {\n"
                    + "  color: #130f40;\n"
                    + "  font-weight: 600;\n"
                    + "  font-size: 1.1em;\n"
                    + "  letter-spacing: -1px;\n"
                    + "  margin-top: 30px;\n"
                    + "  margin-bottom: 0;\n"
                    + "  opacity: 0.65;\n"
                    + "}\n"
                    + "\n"
                    + "#file-browser {\n"
                    + "  text-decoration: none;\n"
                    + "  color: rgb(22,42,255);\n"
                    + "  border-bottom: 3px dotted rgba(22, 22, 255, 0.85);\n"
                    + "}\n"
                    + "\n"
                    + "#file-browser:hover {\n"
                    + "  color: rgb(0, 0, 255);\n"
                    + "  border-bottom: 3px dotted rgba(0, 0, 255, 0.85);\n"
                    + "}\n"
                    + "\n"
                    + ".icons {\n"
                    + "  color: #95afc0;\n"
                    + "  opacity: 0.55;\n"
                    + "}"
                    + "</style>"
                    + "<div class=\"wrapper\">\n"
                    + "  <div class=\"container\">\n"
                    + "    <h1>Upload a file</h1>\n");

            out.println("    <div class=\"upload-container\">\n"
                    + "      <div class=\"border-container\">\n"
                    + "        <div class=\"icons fa-4x\">\n"
                    + "          <i class=\"fas fa-file-image\" data-fa-transform=\"shrink-3 down-2 left-6 rotate--45\"></i>\n"
                    + "          <i class=\"fas fa-file-alt\" data-fa-transform=\"shrink-2 up-4\"></i>\n"
                    + "          <i class=\"fas fa-file-pdf\" data-fa-transform=\"shrink-3 down-2 right-6 rotate-45\"></i>\n"
                    + "        </div>\n"
                    + "        <!--<input type=\"file\" id=\"file-upload\">-->\n"
                    + "<form action='Save_File' method = 'POST' enctype=\"multipart/form-data\" >"
                    + ""
                    + " FILE ID: <input type = 'text' name='file_id' id = 'file_id' value='" + file_id + "'readonly> <br><br><br>"
                    + ""
                    + ""
                    + "<div class=\"select\" id='select' required>\n"
                    + "        <select id='class' name='class' style=\" height:40px;width:400px;margin-left:40px;\n"
                    + "         border-radius: 5px; background-color:window; font: bolder\" >\n"
                    + "            <option hidden=\"\" value='null'>Select Class</option>\n"
                    + "             <option name='class' value='0'>All</option>"
                    + "            <option name='class' value='07'>07</option>\n"
                    + "            <option name='class' value='08'>08</option>\n"
                    + "            <option name='class' value='09'>09</option>\n"
                    + "            <option name='class' value='10'>10</option>\n"
                    + "        </select>\n"
                    + "      </div>"
                    + "<br><br><br>"
                    + "<input type = 'file' name='file' required> <br><br>"
                    + ""
                    + "<button type='submit' class='btn btn-outline-dark'><i class='fas fa-cloud-upload-alt fa-2x'></i> Upload</button> "
                    + "</form>"
                    + "      </div>\n"
                    + "    </div>\n"
                    + "  </div>\n"
                    + "</div>"
            );

            out.println("</body>");
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
        } catch (SQLException ex) {
            Logger.getLogger(Upload_Files.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Upload_Files.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Upload_Files.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Upload_Files.class.getName()).log(Level.SEVERE, null, ex);
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
