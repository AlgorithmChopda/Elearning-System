/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Staff;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author anubh
 */
public class Assignment_Received extends HttpServlet {

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

            // front end code
            out.println("<!DOCTYPE html>");
            out.println("<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.19.1/css/mdb.min.css\" rel=\"stylesheet\">"
                    + "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css\" rel=\"stylesheet\">"
                    + "<link href=\"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap\" rel=\"stylesheet\">"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.6.0/mdb.min.css\" rel=\"stylesheet\" >");
            out.print(" <link rel='stylesheet' type='text/css' href='DisplayStudentData.css'>");

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Display_Online_Video</title>");
            out.println("</head>");
            out.println("<body>");
            
        
        
        
        

            Connection c = Classes.Connect_To_Database.connect();
            String val = request.getParameter("abc");

            PreparedStatement pd = c.prepareStatement("select * from assignment_upload where assign_id = ?");
            pd.setString(1, val);
            ResultSet rs = pd.executeQuery();
            rs.next();
            String assign_name = rs.getString("assign_name");
            int ex = assign_name.lastIndexOf(".");
            assign_name = assign_name.substring(0,ex);
            System.out.println(assign_name);
            
            String dead = rs.getString("assign_deadline");
            String total_marks = rs.getString("assign_marks");
            Date deadline = new SimpleDateFormat("dd/MM/yyyy").parse(dead);
            System.out.println(deadline.getDate() + "/" + (deadline.getMonth() + 1) + "/" + (deadline.getYear() + 1900));
            
        String excel_name = "AssignmentID_"+val+"_"+assign_name+".xlsx";
        System.out.println(excel_name);
        
        out.println("<form id='fd' method='post' action='Download_Excel_Assignment'><button type='submit' name = 'excel_file' value='" + excel_name + "' class='btn btn btn-outline-dark btn-rounded btn-sm my-0'><i class='fa fa-download fa-2x'></i><b> Download Excel File</b></button></form>");
            
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(excel_name);
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        font.setBold(true);
        style.setFont(font);

        Row headerRow = sheet.createRow(0);

        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("ROLL NO");
        headerCell.setCellStyle(style);

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("NAME");
        headerCell.setCellStyle(style);

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("DATE UPLOADED");
        headerCell.setCellStyle(style);

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("DEADLINE");
        headerCell.setCellStyle(style);

        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("STATUS");
        headerCell.setCellStyle(style);

        headerCell = headerRow.createCell(5);
        headerCell.setCellValue("MARKS OBTAINED");
        headerCell.setCellStyle(style);

        headerCell = headerRow.createCell(6);
        headerCell.setCellValue("OUT OFF");
        headerCell.setCellStyle(style); 
        
        sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 8000);
            sheet.setColumnWidth(2, 7000);
            
            sheet.setColumnWidth(3,5000);
            sheet.setColumnWidth(4,5000);
            sheet.setColumnWidth(5, 5000);
            
            sheet.setColumnWidth(6, 4000);
            
            
            
          
            pd = c.prepareStatement("select * from student_assignment_uploaded where assign_id = ?");
            pd.setString(1, val);
            rs = pd.executeQuery();

            out.println("<form id='form' method='post' action='Download_Student_Assignment' ><table class='styled-table'>");
            out.println("<div class='form-group pull-right'>"
                    + "<input type='text' id='l' name='l' value='default' hidden> </lable> <br><br>"
                    + "<input type='text' class='search form-control' placeholder='Search...'>"
                    + "</div>"
                    + "<span class='counter pull-right'></span>"
                    + "<table id='tab' class='table table-hover table-bordered results'>"
                    + "<thead>"
                    + "<tr>"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>STUDENT ROLLNO\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>STUDENT NAME\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>DATE UPLOADED\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>DEADLINE\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>DOWNLOAD\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>STATUS\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>ASSIGNMENT MARKS\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>ASSIGN MARKS\n"
                    + "      </th>\n"
                    + "      <tr class=\"warning no-result\">\n"
                    + "      <td colspan=\"4\"><i class=\"fa fa-warning\"></i> No result</td>\n"
                    + "    </tr>\n"
                    + "  </thead>\n");
            
            int rowCount = 1;

            while (rs.next()) {

                String assign_id = rs.getString("assign_id");
                String name = rs.getString("student_name");
                String rollno = rs.getString("rollno");
                String obt = rs.getString("obtained_marks");
                String u_d = rs.getString("uploaded_date");
                Date upload_date = new SimpleDateFormat("dd/MM/yyyy").parse(u_d.split(" ")[0]);
                System.out.println(upload_date.getDate() + "/" + (upload_date.getMonth() + 1) + "/" + (upload_date.getYear() + 1900));
                String path = assign_id + "#" + name + "#" + rs.getString("assign_name");
                String extra = "BEFORE TIME";
                String color_code = "#00D100";
                
                String value= assign_id+"_"+name+"_"+rollno;

                if (upload_date.after(deadline)) {
                    color_code = "red";
                    if (deadline.getMonth() == (upload_date.getMonth())) // within 1 month
                    {
                        extra = "LATE, after " + (upload_date.getDate() - deadline.getDate()) + " days";
                    } else {
                        if (upload_date.getDate() <= deadline.getDate()) // within 1 month
                        {
                            extra = "LATE, after " + (upload_date.getDate() - deadline.getDate()) + " days";
                        } else {
                            extra = "LATE, after 1 month";
                        }
                    }
                }

                if(!obt.equals("-1"))
                {
                  out.println("<tbody><tr><td style='font-weight:normal'>" + rollno + "</td><td  style='font-weight:normal'>" + name + "</td><td  style='font-weight:normal'>" + u_d + "</td><td  style='font-weight:normal'>" + dead + "</td>"
                        + "<td><button type='submit' name='abc' value='" + path + "'  class='btn btn btn-outline-dark btn-rounded btn-sm my-0'><i class='fa fa-download fa-2x'></i> Download</button></td>"
                        + "<td style='color:" + color_code + "; font-weight: bold;  ' >" + extra + "</td>"
                        + "<td  style='font-weight:normal'>"+total_marks+"</td>"
                        + "<td  style='font-weight:normal'>"+obt+"</td></tr></tbody>");
                
                }
                else
                {
                out.println("<tbody><tr><td  style='font-weight:normal'>" + rollno + "</td><td  style='font-weight:normal'>" + name + "</td><td  style='font-weight:normal'>" + u_d + "</td><td  style='font-weight:normal'>" + dead + "</td>"
                        + "<td><button type='submit' name='abc' value='" + path + "'  class='btn btn btn-outline-dark btn-rounded btn-sm my-0'><i class='fa fa-download fa-2x'></i> Download</button></td>"
                        + "<td style='color:" + color_code + "; font-weight: bold;  ' >" + extra + "</td>"
                        + "<td  style='font-weight:normal'>"+total_marks+"</td>"
                        + "<td style='font-weight:normal'><button name='a' value='"+value+"' onClick='assign(this)'>Assign Marks</button></td></tr></tbody>");
                }
                
                
                
                
                

            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(rollno);

            cell = row.createCell(columnCount++);
            cell.setCellValue(name);

            cell = row.createCell(columnCount++);
            cell.setCellValue(u_d);

            cell = row.createCell(columnCount++);
            cell.setCellValue(dead);

            cell = row.createCell(columnCount++);
            cell.setCellValue(extra);
            
            cell = row.createCell(columnCount++);
            cell.setCellValue(obt);

            cell = row.createCell(columnCount++);
            cell.setCellValue(total_marks);
                
                
                
                }
            
        FileOutputStream outputStream = new FileOutputStream("..\\work\\Catalina\\localhost\\Elearning\\Excel_Assignments\\"+excel_name);
        workbook.write(outputStream);
        workbook.close();

            out.println("</table></form></body></html><script>"
                    + "$(document).ready(function() {\n"
                    + "  $(\".search\").keyup(function () {\n"
                    + "    var searchTerm = $(\".search\").val();\n"
                    + "    var listItem = $('.results tbody').children('tr');\n"
                    + "    var searchSplit = searchTerm.replace(/ /g, \"'):containsi('\")\n"
                    + "    \n"
                    + "  $.extend($.expr[':'], {'containsi': function(elem, i, match, array){\n"
                    + "        return (elem.textContent || elem.innerText || '').toLowerCase().indexOf((match[3] || \"\").toLowerCase()) >= 0;\n"
                    + "    }\n"
                    + "  });\n"
                    + "    \n"
                    + "  $(\".results tbody tr\").not(\":containsi('\" + searchSplit + \"')\").each(function(e){\n"
                    + "    $(this).attr('visible','false');\n"
                    + "  });\n"
                    + "\n"
                    + "  $(\".results tbody tr:containsi('\" + searchSplit + \"')\").each(function(e){\n"
                    + "    $(this).attr('visible','true');\n"
                    + "  });\n"
                    + "\n"
                    + "  var jobCount = $('.results tbody tr[visible=\"true\"]').length;\n"
                    + "    $('.counter').text(jobCount + ' item');\n"
                    + "\n"
                    + "  if(jobCount == '0') {$('.no-result').show();}\n"
                    + "    else {$('.no-result').hide();}\n"
                    + "		  });\n"
                    + "$(window).keydown(function(event){\n"
                    + "    if(event.keyCode == 13) {\n"
                    + "      event.preventDefault();\n"
                    + "      return false;\n"
                    + "    }\n"
                    + "  });"
                    + "});"
                    + ""
                    + "function assign(btn)"
                    + "{"
                    + "let foo = prompt('Enter Marks');"
                    + "if(foo!=null)"
                    + "{"
                    + " var res = confirm(foo);"
                    + "if(res)"
                    + "{"
                   + "document.getElementById('l').value=foo;"
                    + "document.getElementById('form').action='./Assign_Marks';"
                    + "document.getElementById('form').submit();"
                    + ""
                    + "}"
                    + "}"
                   
                    + "}"
                    
                    + "</script>");
        } catch (Exception ex) {
            ex.printStackTrace();
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
            Logger.getLogger(Assignment_Received.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Assignment_Received.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Assignment_Received.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Assignment_Received.class.getName()).log(Level.SEVERE, null, ex);
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
