package com.example.liquibasedemo;

import com.example.liquibasedemo.domain.Person;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Person> personList;

    public ExcelExporter(List<Person> personList) {
        this.personList = personList;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Persons");

    }

    private void writeHeaderRow() {
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("Name");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Phone Number");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Street");
        cell.setCellStyle(style);
    }

    private void writeDataRows() {
        int rowCount = 1;
        for (Person person : personList){
            Row row = sheet.createRow(rowCount);

            Cell cell = row.createCell(0);
            cell.setCellValue(person.getFirstName()+" "+person.getLastName());
            sheet.autoSizeColumn(0);

            cell = row.createCell(1);
            cell.setCellValue(person.getPhoneNumber1());
            sheet.autoSizeColumn(1);

            cell = row.createCell(2);
            cell.setCellValue(person.getStreet1()+", "+person.getStreet2());
            sheet.autoSizeColumn(2);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
