package Classes.Staff;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
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

public class DisplayResult extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println(""
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.19.1/css/mdb.min.css\" rel=\"stylesheet\">"
                    + "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css\" rel=\"stylesheet\">"
                    + "<link href=\"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap\" rel=\"stylesheet\">"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.6.0/mdb.min.css\" rel=\"stylesheet\" >");

            out.print(" <link rel='stylesheet' type='text/css' href='DisplayStudentData.css'>");
            out.println("<html>");
            out.println("<head>");
            out.println("</head>");
            out.println("<body>");

            String info[] = request.getParameter("l").split(",");

            String test_id = info[0];
            String cla = info[1];
            String div = info[2];
            String test_name = info[3];

            String excel_name = "TestID_" + test_id + "_" + test_name + ".xlsx";
            out.println("<form id='fd' method='post' action='Download_Excel_Test'><button type='submit' name = 'excel_file' value='" + excel_name + "' class='btn btn btn-outline-dark btn-rounded btn-sm my-0'><i class='fa fa-download fa-2x'></i><b> Download Excel File</b></button></form>");

            out.println(""
                    + "<table class='styled-table'><div class='form-group pull-right'>"
                    + "<input type='text' id='l' name='l' value='default' hidden> </lable> <br><br>"
                    + "<input type='text' class='search form-control' placeholder='Search...'>"
                    + "</div>"
                    + "<span class='counter pull-right'></span>"
                    + "<table id='tab' class='table table-hover table-bordered results'>"
                    + "<thead>"
                    + "<tr>"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>TEST NAME\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>CLASS\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>DIVISION\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>ROLL NO\n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'> NAME  \n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>TEST SUBMITTED<br>(hr:min:sec) \n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>MARKS \n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>PERCENTAGE \n"
                    + "      </th>\n"
                    + "      <th class=\"th-sm\" style='font-weight:bold'>STATUS \n"
                    + "      </th>\n"
                    + "      <tr class=\"warning no-result\">\n"
                    + "      <td colspan=\"4\"><i class=\"fa fa-warning\"></i> No result</td>\n"
                    + "    </tr>\n"
                    + "  </thead>\n");

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(excel_name);
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 3000);
            sheet.setColumnWidth(2, 3000);
            
            sheet.setColumnWidth(3, 8000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 4000);
            
            sheet.setColumnWidth(6, 6000);
            sheet.setColumnWidth(7, 5000);
            
            
// Setting the width of all columns in the worksheet to 20.5


            XSSFFont font = workbook.createFont();
            XSSFCellStyle style = workbook.createCellStyle();
            
            font.setBold(true);
            style.setFont(font);
            int rowCount = 1;

            Row headerRow = sheet.createRow(0);

            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("CLASS");
            headerCell.setCellStyle(style);
            
            headerCell = headerRow.createCell(1);
            headerCell.setCellValue("DIVISION");
            headerCell.setCellStyle(style);
            
            headerCell = headerRow.createCell(2);
            headerCell.setCellValue("ROLL NO");
            headerCell.setCellStyle(style);

            headerCell = headerRow.createCell(3);
            headerCell.setCellValue("NAME");
            headerCell.setCellStyle(style);

            headerCell = headerRow.createCell(4);
            headerCell.setCellValue("MARKS OBTAINED");
            headerCell.setCellStyle(style);

            headerCell = headerRow.createCell(5);
            headerCell.setCellValue("PERCENTAGE");
            headerCell.setCellStyle(style);

            headerCell = headerRow.createCell(6);
            headerCell.setCellValue("SUBMITTED TEST TIME");
            headerCell.setCellStyle(style);

            headerCell = headerRow.createCell(7);
            headerCell.setCellValue("STATUS");
            headerCell.setCellStyle(style);
            //headerCell.setColumnWidth(12);
            
            Connection c = Classes.Connect_To_Database.connect();

            String sql = "";
            PreparedStatement pd;
            ResultSet rs = null;

            if (div.equals("0")) {
                sql = "select * from test_history where class=?";
                pd = c.prepareStatement(sql);
                pd.setString(1, cla);
                rs = pd.executeQuery();
            } else {
                sql = "select * from test_history where class=? and div = ?";
                pd = c.prepareStatement(sql);
                pd.setString(1, cla);
                pd.setString(2, div);
                rs = pd.executeQuery();
            }
            HashSet<Integer> hs = new HashSet<>();      // Test : Appeared
            HashSet<Integer> t_p = new HashSet<>();     // Test : Appearing
            while (rs.next()) {
                String test_ids[] = (rs.getString("test_ids").split(","));      // given tests
                String progress[] = (rs.getString("test_progress").split(",")); // test appearing
                String test_time[] = (rs.getString("end_time").split(","));
                int index = -1;
                
                for (int i = 1; i < test_ids.length; i++) {
                    if (test_ids[i].equals(test_id)) { // required test given
                        index = i;
                        hs.add(Integer.parseInt(rs.getString("student_id")));
                        break;
                    }
                }
                
                if (index != -1) { // student appeared

                    String marks[] = rs.getString("marks").split(",");

                    pd = c.prepareStatement("select * from student where userid = ?");
                    pd.setString(1, rs.getString("student_id"));
                    ResultSet rs1 = pd.executeQuery();
                    rs1.next();
                    String name = rs1.getString("name");
                    String rollno = rs1.getString("rollno");
                    String division = rs1.getString("division");

                    String test_marks = marks[index];
                    String current_test_time = test_time[index];
                    String percentage[] = test_marks.split("/");
                    float m = Float.parseFloat(percentage[0]);
                    float outoff = Float.parseFloat(percentage[1]);
                    float total = m * 100 / outoff;

                    out.println("<tbody ><tr><td style='font-weight:normal'>" + test_name + "</td><td style='font-weight:normal'>" + cla + "</td><td style='font-weight:normal'>" + division + "</td>"
                            + "<td style='font-weight:normal'>" + rollno + "</td><td style='font-weight:normal'>" + name + "</td><td style='font-weight:normal'>" + current_test_time + "</td><td style='font-weight:normal'>" + test_marks + "</td>"
                            + "<td style='font-weight:normal'>" + total + "%</td>"
                            + "<td style='color: green;' ><b>Appeared</b></td>"
                            + "</tr></tbody>");

                    Row row = sheet.createRow(rowCount++);

                    int columnCount = 0;
                    Cell cell = row.createCell(columnCount++);
                    cell.setCellValue(cla);

                    cell = row.createCell(columnCount++);
                    cell.setCellValue(division);

                    cell = row.createCell(columnCount++);
                    cell.setCellValue(rollno);

                    cell = row.createCell(columnCount++);
                    cell.setCellValue(name);

                    cell = row.createCell(columnCount++);
                    cell.setCellValue(test_marks);

                    cell = row.createCell(columnCount++);
                    cell.setCellValue(total+"%");

                    cell = row.createCell(columnCount++);
                    cell.setCellValue(current_test_time);
                    
                    cell = row.createCell(columnCount++);
                    cell.setCellValue(" Appeared ");
                }
                else    // index == -1
                {
                    // 2 case not appeared / appearing
                    int i = 0;
                    for(i = 0 ; i < progress.length; i++)
                    {
                        if(progress[i].equals(test_id)){
                            hs.add(Integer.parseInt(rs.getString("student_id")));
                            break;
                        }
                    }
                    
                    if(i != progress.length){   // appearing
                        pd = c.prepareStatement("select * from student where userid = ?");
                    pd.setString(1, rs.getString("student_id"));
                    ResultSet rs1 = pd.executeQuery();
                    rs1.next();
                    String name = rs1.getString("name");
                    String rollno = rs1.getString("rollno");
                    String division = rs1.getString("division");


                    out.println("<tbody ><tr><td style='font-weight:normal'>" + test_name + "</td><td style='font-weight:normal'>" + cla + "</td><td style='font-weight:normal'>" + division + "</td>"
                            + "<td style='font-weight:normal'>" + rollno + "</td><td style='font-weight:normal'>" + name + "</td><td style='font-weight:normal'> -- </td><td style='font-weight:normal'> -- </td>"
                            + "<td style='font-weight:normal'> -- </td>"
                            + "<td style='color: Red;' ><b>Appearing</b></td>"
                            + "</tr></tbody>");

                    
                    Row row = sheet.createRow(rowCount++);

                    int columnCount = 0;
                    Cell cell = row.createCell(columnCount++);
                    cell.setCellValue(cla);

                    cell = row.createCell(columnCount++);
                    cell.setCellValue(division);

                    cell = row.createCell(columnCount++);
                    cell.setCellValue(rollno);

                    cell = row.createCell(columnCount++);
                    cell.setCellValue(name);

                    cell = row.createCell(columnCount++);
                    cell.setCellValue("--");

                    cell = row.createCell(columnCount++);
                    cell.setCellValue("--");

                    cell = row.createCell(columnCount++);
                    cell.setCellValue("--");
                    
                    cell = row.createCell(columnCount++);
                    cell.setCellValue(" Appearing ...");
                    
                    }
                    
                    
                    
                }

            }

            if (div.equals("0")) {
                sql = "select * from student where class=?";
                pd = c.prepareStatement(sql);
                pd.setString(1, cla);
                rs = pd.executeQuery();
            } else {
                sql = "select * from student where class=? and div = ?";
                pd = c.prepareStatement(sql);
                pd.setString(1, cla);
                pd.setString(2, div);
                rs = pd.executeQuery();
            }

            while (rs.next()) {
                if (!hs.contains(Integer.parseInt(rs.getString("userid")))) {
                    out.println("<tbody style='background-color: #ffc6c4; ' ><tr ><td>" + test_name + "</td><td style='font-weight: 900;' >" + cla + "</td><td style='font-weight: 900;' >" + rs.getString("division") + "</td>"
                            + "<td style='font-weight: 900;' >" + rs.getString("rollno") + "</td><td style='font-weight: 900;' > " + rs.getString("name") + "</td><td style='font-weight: 900;' > -- </td><td style='font-weight: 900;' > -- </td>"
                            + "<td style='font-weight: 900;' > -- </td>"
                            + "<td style='font-weight: 900;' >Not Appeared</td>"
                            + "</tr></tbody>");

                    Row row = sheet.createRow(rowCount++);

                    int columnCount = 0;
                    Cell cell = row.createCell(columnCount++);
                    cell.setCellValue(cla);

                    cell = row.createCell(columnCount++);
                    cell.setCellValue(rs.getString("division"));

                    cell = row.createCell(columnCount++);
                    cell.setCellValue(rs.getString("rollno"));

                    cell = row.createCell(columnCount++);
                    cell.setCellValue(rs.getString("name"));

                    cell = row.createCell(columnCount++);
                    cell.setCellValue("--");

                    cell = row.createCell(columnCount++);
                    cell.setCellValue("--");

                    cell = row.createCell(columnCount++);
                    cell.setCellValue("--");
                    
                    
                    cell = row.createCell(columnCount++);
                    cell.setCellValue(" Not Appeared ");

                }
            }

            FileOutputStream outputStream = new FileOutputStream("..\\work\\Catalina\\localhost\\Elearning\\Excel_Tests\\" + excel_name);
            workbook.write(outputStream);
            workbook.close();

            out.println("</body>");
            out.println("</html>");

            out.println("<script>"
                    + "// Basic example\n"
                    + "$(document).ready(function () {\n"
                    + "  $('#dtBasicExample').DataTable({\n"
                    + "    \"pagingType\": \"simple\" // \"simple\" option for 'Previous' and 'Next' buttons only\n"
                    + "  });\n"
                    + "  $('.dataTables_length').addClass('bs-select');\n"
                    + "});"
                    + ""
                    + ""
                    + ""
                    + ""
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
                    + "$(document).ready(function () {\n"
                    + "$('#dtBasicExample').DataTable();\n"
                    + "$('.dataTables_length').addClass('bs-select');\n"
                    + "});"
                    + "</script>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DisplayResult.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DisplayResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DisplayResult.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DisplayResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
