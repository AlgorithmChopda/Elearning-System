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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class Test_Status extends HttpServlet {

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

            Connection c = Classes.Connect_To_Database.connect();

            String info[] = request.getParameter("l1").split(",");
            String status;
            String test_id = info[0];

            if (info[1].equals("1")) {  // test is active in DB
                //test deactivated
                status = "0";
                String sql = "update test_headers set status=? where test_id=?";
                PreparedStatement pd = c.prepareStatement(sql);
                pd.setString(1, status);
                pd.setString(2, test_id);
                pd.execute();
            } else {
                status = "1";
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();

                String sql = "update test_headers set status=?,test_date=? where test_id=?";
                PreparedStatement pd = c.prepareStatement(sql);
                pd.setString(1, status);
                pd.setString(2, formatter.format(date));
                pd.setString(3, test_id);
                
                pd.execute();
            }

            String sql = "update test_headers set status=? where test_id=?";
            PreparedStatement pd = c.prepareStatement(sql);
            pd.setString(1, status);
            pd.setString(2, test_id);
            pd.execute();

            out.println("<script>"
                    + "var st ='" + status + "';"
                    + "if(st==0)"
                    + "{"
                    + "alert('Test has been DEACTIVATED');"
                    + "}"
                    + "else"
                    + "{"
                    + "alert('Test has been ACTIVATED');"
                    + "}"
                    + "window.location.replace('./Display_Uploaded_Tests');"
                    + "</script>");

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
            Logger.getLogger(Test_Status.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Test_Status.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Test_Status.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Test_Status.class.getName()).log(Level.SEVERE, null, ex);
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
