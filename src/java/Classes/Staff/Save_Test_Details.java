package Classes.Staff;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Save_Test_Details extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        
        Connection c = Classes.Connect_To_Database.connect();
        
        HttpSession session = request.getSession();
        
        PrintWriter out = response.getWriter();
        
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("title");
        String pass = request.getParameter("pass");
        
        String cla = request.getParameter("class");
        String div = request.getParameter("div");
        
        String sql = "insert into test_headers values(?, ?, ? , ?, ?, ?,'0','  --  ')";
        PreparedStatement pd = c.prepareStatement(sql);

        pd.setInt(1, id);
        pd.setString(2, name);
        pd.setString(3, pass);
        pd.setString(4, cla);
        pd.setString(5, div);
        pd.setString(6, (String) session.getAttribute("staff_id"));

        pd.execute();

        out.println("<script>"
                + "window.location.replace('./Take_Test_Questions.html');"
                + " </script> ");
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Save_Test_Details.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Save_Test_Details.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Save_Test_Details.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Save_Test_Details.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
