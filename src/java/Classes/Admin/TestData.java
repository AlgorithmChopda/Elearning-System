/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author anubh
 */
public class TestData {

    public static void main(String args[]) throws ClassNotFoundException, SQLException, ParseException, FileNotFoundException, IOException {

        Connection c = Classes.Connect_To_Database.connect();
        String sql = "select * from student order by class";
        PreparedStatement pd = c.prepareStatement(sql);
        ResultSet rs = pd.executeQuery();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Reviews");
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        font.setBold(true);
        style.setFont(font);

        Row headerRow = sheet.createRow(0);

        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("USERID");
        headerCell.setCellStyle(style);

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("PASSWORD");
        headerCell.setCellStyle(style);

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("NAME");
        headerCell.setCellStyle(style);

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("ROLL NO");
        headerCell.setCellStyle(style);

        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("CLASS");
        headerCell.setCellStyle(style);

        headerCell = headerRow.createCell(5);
        headerCell.setCellValue("DIVISION");
        headerCell.setCellStyle(style);

        headerCell = headerRow.createCell(6);
        headerCell.setCellValue("PHONE NO.");
        headerCell.setCellStyle(style);
        
       

        int rowCount = 1;

        while (rs.next()) {
            String id = rs.getString("userid");
            String pass = rs.getString("password");
            String name = rs.getString("name");
            String roll = rs.getString("rollno");
            String cla = rs.getString("class");
            String div = rs.getString("division");
            String phone = rs.getString("phoneno");

            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(id);

            cell = row.createCell(columnCount++);
            cell.setCellValue(pass);

            cell = row.createCell(columnCount++);
            cell.setCellValue(name);

            cell = row.createCell(columnCount++);
            cell.setCellValue(roll);

            cell = row.createCell(columnCount++);
            cell.setCellValue(cla);

            cell = row.createCell(columnCount++);
            cell.setCellValue(div);

            cell = row.createCell(columnCount++);
            cell.setCellValue(phone);

        }

        FileOutputStream outputStream = new FileOutputStream("..\\work\\Catalina\\localhost\\Elearning\\");
        workbook.write(outputStream);
        workbook.close();

        /*FileInputStream fis = new FileInputStream(new File("C:\\Users\\anubh\\OneDrive\\Desktop\\Book1.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
        Connection c = Classes.Connect_To_Database.connect();

        String sql = "insert into admin values(?, ?)";
        PreparedStatement pd = c.prepareStatement(sql);
        
        c.setAutoCommit(false);
        int numRows = sheet.getLastRowNum() + 1;
		int numCols = sheet.getRow(0).getLastCellNum();
		String dataTable[][] = new String[numRows][numCols];
                
		for (int i = 0; i < numRows; i++) {
			XSSFRow xlRow = sheet.getRow(i);
                        String name = xlRow.getCell(0).toString();
                        String pass = xlRow.getCell(1).toString();

                        System.out.println("pass : "+pass);
                        String temp[] = pass.split("\\.");
                        if(temp.length >= 2)       // point found
                            pass = temp[0];
                        pd.setString(1,name);
                        pd.setString(2,pass);
                        pd.addBatch();
        }
       pd.executeBatch();
       c.commit();*/
    }
}
