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
public class StudentDetails extends HttpServlet {

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
            
            String userid = (String) session.getAttribute("student_id");
            Connection c = Classes.Connect_To_Database.connect();
            
            PreparedStatement pd = c.prepareStatement("select * from student where userid = ? and del=1");
            pd.setString(1, userid);
            
            ResultSet rs = pd.executeQuery();
            
            rs.next();
            
            
            
            
            
            out.println("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head>\n"
                    + "    <meta charset=\"utf-8\">\n"
                    + "    <title></title>\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "    <script src=\"https://code.jquery.com/jquery-1.10.2.min.js\"></script>\n"
                    + "    <link href=\"https://netdna.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\" rel=\"stylesheet\">\n"
                    + "	<script src=\"https://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "<link href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css\" rel=\"stylesheet\">\n"
                    + "<div class=\"container bootstrap snippets bootdey\">\n"
                    + ""
                    + "  <div class=\"profile-info col-md-9\">\n"
                    + ""
                    + "      <div class=\"panel\">\n"
                    + "          <div class=\"bio-graph-heading\">\n"
                    + "             WELCOME !!!\n"
                    + "          </div>\n"
                    + "          <div class=\"panel-body bio-graph-info\">\n"
                    + "              <h1>BIO</h1>\n"
                    + "              <div class=\"row\">\n"
                    + "                  <div class=\"bio-row\">\n"
                    + "                      <p><span>GR No.</span>:"+rs.getString("userid")+"</p>\n"
                    + "                  </div>\n"
                    + "                  <div class=\"bio-row\">\n"
                    + "                      <p><span>Roll No</span>:"+rs.getString("rollno")+"</p>\n"
                    + "                  </div>\n"
                    + "                  <div class=\"bio-row\">\n"
                    + "                      <p><span>Name</span>: "+rs.getString("name")+"</p>\n"
                    + "                  </div>\n"
                    + "                  <div class=\"bio-row\">\n"
                    + "                      <p><span>Class</span>: "+rs.getString("class")+"</p>\n"
                    + "                  </div>\n"
                    + "                  <div class=\"bio-row\">\n"
                    + "                      <p><span>Divison</span>: "+rs.getString("division")+"</p>\n"
                    + "                  </div>\n"
                    + "                  <div class=\"bio-row\">\n"
                    + "                      <p><span>Phone No</span>:"+rs.getString("phoneno")+"</p>\n"
                    + "                  </div>\n"
                    + "              </div>\n"
                    + "          </div>\n"
                    + "      </div>\n"
                    + "      <div>\n"
                    + "          <div class=\"row\">\n"
                    + "              <div class=\"col-md-6\">\n"
                    + "                  <div class=\"panel\">\n"
                    + "                      <div class=\"panel-body\">\n"
                    + "                          <div class=\"bio-chart\">\n"
                    + "                              <div style=\"display:inline;width:100px;height:100px;\"><canvas width=\"100\" height=\"100px\"></canvas><input class=\"knob\" data-width=\"100\" data-height=\"100\" data-displayprevious=\"true\" data-thickness=\".2\" value=\"35\" data-fgcolor=\"#e06b7d\" data-bgcolor=\"#e8e8e8\" style=\"width: 54px; height: 33px; position: absolute; vertical-align: middle; margin-top: 33px; margin-left: -77px; border: 0px; font-weight: bold; font-style: normal; font-variant: normal; font-stretch: normal; font-size: 20px; line-height: normal; font-family: Arial; text-align: center; color: rgb(224, 107, 125); padding: 0px; -webkit-appearance: none; background: none;\"></div>\n"
                    + "                          </div>\n"
                    + "                          <div class=\"bio-desk\">\n"
                    + "                              <h4 class=\"red\">Assignment subject name</h4>\n"
                    + "                              <p>Started : 15 July</p>\n"
                    + "                              <p>Deadline : 15 August</p>\n"
                    + "                          </div>\n"
                    + "                      </div>\n"
                    + "                  </div>\n"
                    + "              </div>\n"
                    + "              <div class=\"col-md-6\">\n"
                    + "                  <div class=\"panel\">\n"
                    + "                      <div class=\"panel-body\">\n"
                    + "                          <div class=\"bio-chart\">\n"
                    + "                              <div style=\"display:inline;width:100px;height:100px;\"><canvas width=\"100\" height=\"100px\"></canvas><input class=\"knob\" data-width=\"100\" data-height=\"100\" data-displayprevious=\"true\" data-thickness=\".2\" value=\"63\" data-fgcolor=\"#4CC5CD\" data-bgcolor=\"#e8e8e8\" style=\"width: 54px; height: 33px; position: absolute; vertical-align: middle; margin-top: 33px; margin-left: -77px; border: 0px; font-weight: bold; font-style: normal; font-variant: normal; font-stretch: normal; font-size: 20px; line-height: normal; font-family: Arial; text-align: center; color: rgb(76, 197, 205); padding: 0px; -webkit-appearance: none; background: none;\"></div>\n"
                    + "                          </div>\n"
                    + "                          <div class=\"bio-desk\">\n"
                    + "                              <h4 class=\"terques\">Assignment </h4>\n"
                    + "                              <p>Started : 15 July</p>\n"
                    + "                              <p>Deadline : 15 August</p>\n"
                    + "                          </div>\n"
                    + "                      </div>\n"
                    + "                  </div>\n"
                    + "              </div>\n"
                    + "              <div class=\"col-md-6\">\n"
                    + "                  <div class=\"panel\">\n"
                    + "                      <div class=\"panel-body\">\n"
                    + "                          <div class=\"bio-chart\">\n"
                    + "                              <div style=\"display:inline;width:100px;height:100px;\"><canvas width=\"100\" height=\"100px\"></canvas><input class=\"knob\" data-width=\"100\" data-height=\"100\" data-displayprevious=\"true\" data-thickness=\".2\" value=\"75\" data-fgcolor=\"#96be4b\" data-bgcolor=\"#e8e8e8\" style=\"width: 54px; height: 33px; position: absolute; vertical-align: middle; margin-top: 33px; margin-left: -77px; border: 0px; font-weight: bold; font-style: normal; font-variant: normal; font-stretch: normal; font-size: 20px; line-height: normal; font-family: Arial; text-align: center; color: rgb(150, 190, 75); padding: 0px; -webkit-appearance: none; background: none;\"></div>\n"
                    + "                          </div>\n"
                    + "                          <div class=\"bio-desk\">\n"
                    + "                              <h4 class=\"green\">Assignment</h4>\n"
                    + "                              <p>Started : 15 July</p>\n"
                    + "                              <p>Deadline : 15 August</p>\n"
                    + "                          </div>\n"
                    + "                      </div>\n"
                    + "                  </div>\n"
                    + "              </div>\n"
                    + "              <div class=\"col-md-6\">\n"
                    + "                  <div class=\"panel\">\n"
                    + "                      <div class=\"panel-body\">\n"
                    + "                          <div class=\"bio-chart\">\n"
                    + "                              <div style=\"display:inline;width:100px;height:100px;\"><canvas width=\"100\" height=\"100px\"></canvas><input class=\"knob\" data-width=\"100\" data-height=\"100\" data-displayprevious=\"true\" data-thickness=\".2\" value=\"50\" data-fgcolor=\"#cba4db\" data-bgcolor=\"#e8e8e8\" style=\"width: 54px; height: 33px; position: absolute; vertical-align: middle; margin-top: 33px; margin-left: -77px; border: 0px; font-weight: bold; font-style: normal; font-variant: normal; font-stretch: normal; font-size: 20px; line-height: normal; font-family: Arial; text-align: center; color: rgb(203, 164, 219); padding: 0px; -webkit-appearance: none; background: none;\"></div>\n"
                    + "                          </div>\n"
                    + "                          <div class=\"bio-desk\">\n"
                    + "                              <h4 class=\"purple\">Assignment</h4>\n"
                    + "                              <p>Started : 15 July</p>\n"
                    + "                              <p>Deadline : 15 August</p>\n"
                    + "                          </div>\n"
                    + "                      </div>\n"
                    + "                  </div>\n"
                    + "              </div>\n"
                    + "          </div>\n"
                    + "      </div>\n"
                    + "  </div>\n"
                    + "</div>\n"
                    + "</div>\n"
                    + "\n"
                    + "<script type=\"text/javascript\">\n"
                    + "\n"
                    + "</script>\n"
                    + "<style type=\"text/css\">\n"
                    + "body {\n"
                    + "    \n"
                    + "    color: #797979;\n"
                    + "    background:#F0F8FF;\n"
                    + "    font-family: 'Open Sans', sans-serif;\n"
                    + "    padding: 0px !important;\n"
                    + "    margin: 40px !important;\n"
                    + "    margin-top: 40px !important;\n"
                    + "    font-size: 15px;\n"
                    + "    text-rendering: optimizeLegibility;\n"
                    + "    -webkit-font-smoothing: antialiased;\n"
                    + "    -moz-font-smoothing: antialiased;\n"
                    + "}\n"
                    + "\n"
                    + ".bio-graph-heading {\n"
                    + "    background: #FF8C00;\n"
                    + "    font-size: 55px;\n"
                    + "    color:rgb(0, 0, 0);\n"
                    + "    text-align: center;\n"
                    + "    font-style:normal;\n"
                    + "    font-size: 25px !important;    \n"
                    + "    padding: 30px 110px;\n"
                    + "    border-radius: 4px 4px 0 0;\n"
                    + "    -webkit-border-radius: 4px 4px 0 0;\n"
                    + "    font-size: 16px;\n"
                    + "    font-weight: 300;\n"
                    + "}\n"
                    + "\n"
                    + ".bio-graph-info {\n"
                    + "    color: #252525;\n"
                    + "}\n"
                    + "\n"
                    + ".bio-graph-info h1 {\n"
                    + "    font-size: 22px;\n"
                    + "    font-weight: 300;\n"
                    + "    margin: 0 0 20px;\n"
                    + "}\n"
                    + "\n"
                    + ".bio-row {\n"
                    + "    width: 50%;\n"
                    + "    float: left;\n"
                    + "    margin-bottom: 10px;\n"
                    + "    padding:0 15px;\n"
                    + "}\n"
                    + "\n"
                    + ".bio-row p span {\n"
                    + "    width: 100px;\n"
                    + "    display: inline-block;\n"
                    + "}\n"
                    + "\n"
                    + ".bio-chart, .bio-desk {\n"
                    + "    float: left;\n"
                    + "}\n"
                    + "\n"
                    + ".bio-chart {\n"
                    + "    width: 40%;\n"
                    + "}\n"
                    + "\n"
                    + ".bio-desk {\n"
                    + "    width: 60%;\n"
                    + "}\n"
                    + "\n"
                    + ".bio-desk h4 {\n"
                    + "    font-size: 15px;\n"
                    + "    font-weight:400;\n"
                    + "}\n"
                    + "\n"
                    + ".bio-desk h4.terques {\n"
                    + "    color: #009faa;\n"
                    + "}\n"
                    + "\n"
                    + ".bio-desk h4.red {\n"
                    + "    color: #b9001f;\n"
                    + "}\n"
                    + "\n"
                    + ".bio-desk h4.green {\n"
                    + "    color: #71aa00;\n"
                    + "}\n"
                    + "\n"
                    + ".bio-desk h4.purple {\n"
                    + "    color: #71009e;\n"
                    + "}\n"
                    + "\n"
                    + ".file-pos {\n"
                    + "    margin: 6px 0 10px 0;\n"
                    + "}\n"
                    + "\n"
                    + ".profile-activity h5 {\n"
                    + "    font-weight: 300;\n"
                    + "    margin-top: 0;\n"
                    + "    color: #c3c3c3;\n"
                    + "}\n"
                    + "\n"
                    + ".summary-head {\n"
                    + "    background: #ee7272;\n"
                    + "    color: #fff;\n"
                    + "    text-align: center;\n"
                    + "    border-bottom: 1px solid #ee7272;\n"
                    + "}\n"
                    + "\n"
                    + ".summary-head h4 {\n"
                    + "    font-weight: 300;\n"
                    + "    text-transform: uppercase;\n"
                    + "    margin-bottom: 5px;\n"
                    + "}\n"
                    + "\n"
                    + ".summary-head p {\n"
                    + "    color: rgba(255,255,255,0.6);\n"
                    + "}\n"
                    + "\n"
                    + "ul.summary-list {\n"
                    + "    display: inline-block;\n"
                    + "    padding-left:0 ;\n"
                    + "    width: 100%;\n"
                    + "    margin-bottom: 0;\n"
                    + "}\n"
                    + "\n"
                    + "ul.summary-list > li {\n"
                    + "    display: inline-block;\n"
                    + "    width: 19.5%;\n"
                    + "    text-align: center;\n"
                    + "}\n"
                    + "\n"
                    + "ul.summary-list > li > a > i {\n"
                    + "    display:block;\n"
                    + "    font-size: 18px;\n"
                    + "    padding-bottom: 5px;\n"
                    + "}\n"
                    + "\n"
                    + "ul.summary-list > li > a {\n"
                    + "    padding: 10px 0;\n"
                    + "    display: inline-block;\n"
                    + "    color: #818181;\n"
                    + "}\n"
                    + "\n"
                    + "ul.summary-list > li  {\n"
                    + "    border-right: 1px solid #eaeaea;\n"
                    + "}\n"
                    + "\n"
                    + "ul.summary-list > li:last-child  {\n"
                    + "    border-right: none;\n"
                    + "}\n"
                    + "\n"
                    + ".activity {\n"
                    + "    width: 100%;\n"
                    + "    float: left;\n"
                    + "    margin-bottom: 10px;\n"
                    + "}\n"
                    + "\n"
                    + ".activity.alt {\n"
                    + "    width: 100%;\n"
                    + "    float: right;\n"
                    + "    margin-bottom: 10px;\n"
                    + "}\n"
                    + "\n"
                    + ".activity span {\n"
                    + "    float: left;\n"
                    + "}\n"
                    + "\n"
                    + ".activity.alt span {\n"
                    + "    float: right;\n"
                    + "}\n"
                    + ".activity span, .activity.alt span {\n"
                    + "    width: 45px;\n"
                    + "    height: 45px;\n"
                    + "    line-height: 45px;\n"
                    + "    border-radius: 50%;\n"
                    + "    -webkit-border-radius: 50%;\n"
                    + "    background: #eee;\n"
                    + "    text-align: center;\n"
                    + "    color: #fff;\n"
                    + "    font-size: 16px;\n"
                    + "}\n"
                    + "\n"
                    + ".activity.terques span {\n"
                    + "    background: #8dd7d6;\n"
                    + "}\n"
                    + "\n"
                    + ".activity.terques h4 {\n"
                    + "    color: #8dd7d6;\n"
                    + "}\n"
                    + ".activity.purple span {\n"
                    + "    background: #b984dc;\n"
                    + "}\n"
                    + "\n"
                    + ".activity.purple h4 {\n"
                    + "    color: #b984dc;\n"
                    + "}\n"
                    + ".activity.blue span {\n"
                    + "    background: #0000FF;\n"
                    + "}\n"
                    + "\n"
                    + ".activity.blue h4 {\n"
                    + "    color: #90b4e6;\n"
                    + "}\n"
                    + ".activity.green span {\n"
                    + "    background: #aec785;\n"
                    + "}\n"
                    + "\n"
                    + ".activity.green h4 {\n"
                    + "    color: #aec785;\n"
                    + "}\n"
                    + "\n"
                    + ".activity h4 {\n"
                    + "    margin-top:0 ;\n"
                    + "    font-size: 16px;\n"
                    + "}\n"
                    + "\n"
                    + ".activity p {\n"
                    + "    margin-bottom: 0;\n"
                    + "    font-size: 13px;\n"
                    + "}\n"
                    + "\n"
                    + ".activity .activity-desk i, .activity.alt .activity-desk i {\n"
                    + "    float: left;\n"
                    + "    font-size: 18px;\n"
                    + "    margin-right: 10px;\n"
                    + "    color: #bebebe;\n"
                    + "}\n"
                    + "\n"
                    + ".activity .activity-desk {\n"
                    + "    margin-left: 70px;\n"
                    + "    position: relative;\n"
                    + "}\n"
                    + "\n"
                    + ".activity.alt .activity-desk {\n"
                    + "    margin-right: 70px;\n"
                    + "    position: relative;\n"
                    + "}\n"
                    + "\n"
                    + ".activity.alt .activity-desk .panel {\n"
                    + "    float: right;\n"
                    + "    position: relative;\n"
                    + "}\n"
                    + "\n"
                    + ".activity-desk .panel {\n"
                    + "    background: #F4F4F4 ;\n"
                    + "    display: inline-block;\n"
                    + "}\n"
                    + "\n"
                    + "\n"
                    + ".activity .activity-desk .arrow {\n"
                    + "    border-right: 8px solid #F4F4F4 !important;\n"
                    + "}\n"
                    + ".activity .activity-desk .arrow {\n"
                    + "    border-bottom: 8px solid transparent;\n"
                    + "    border-top: 8px solid transparent;\n"
                    + "    display: block;\n"
                    + "    height: 0;\n"
                    + "    left: -7px;\n"
                    + "    position: absolute;\n"
                    + "    top: 13px;\n"
                    + "    width: 0;\n"
                    + "}\n"
                    + "\n"
                    + ".activity-desk .arrow-alt {\n"
                    + "    border-left: 8px solid #F4F4F4 !important;\n"
                    + "}\n"
                    + "\n"
                    + ".activity-desk .arrow-alt {\n"
                    + "    border-bottom: 8px solid transparent;\n"
                    + "    border-top: 8px solid transparent;\n"
                    + "    display: block;\n"
                    + "    height: 0;\n"
                    + "    right: -7px;\n"
                    + "    position: absolute;\n"
                    + "    top: 13px;\n"
                    + "    width: 0;\n"
                    + "}\n"
                    + "\n"
                    + ".activity-desk .album {\n"
                    + "    display: inline-block;\n"
                    + "    margin-top: 10px;\n"
                    + "}\n"
                    + "\n"
                    + ".activity-desk .album a{\n"
                    + "    margin-right: 10px;\n"
                    + "}\n"
                    + "\n"
                    + ".activity-desk .album a:last-child{\n"
                    + "    margin-right: 0px;\n"
                    + "}\n"
                    + "</style>"
                    + "</body>\n"
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentDetails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StudentDetails.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(StudentDetails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StudentDetails.class.getName()).log(Level.SEVERE, null, ex);
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
