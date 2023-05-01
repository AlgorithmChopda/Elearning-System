/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Student;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author anubh
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)

public class Student_Upload_Assignment extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            int file_id = Integer.parseInt(request.getParameter("l"));

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
                    + "<form action='Save_Upload_Assignment' method = 'POST' enctype=\"multipart/form-data\" >"
                    + ""
                    + " FILE ID: <input type = 'text' name='file_id' id = 'file_id' value='" + file_id + "'readonly> <br><br><br>"
                    + ""
                    + ""
                    
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
