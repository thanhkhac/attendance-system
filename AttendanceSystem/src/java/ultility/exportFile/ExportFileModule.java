/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ultility.exportFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.StatisticsDAO;
import model.StatisticsDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ultility.datetimeutil.DateTimeUtil;

/**
 *
 * @author admin
 */
public class ExportFileModule {

    private static final DateTimeUtil DATE_UTIL = new DateTimeUtil();
    public static final int COLUMN_INDEX_DATE = 0;
    public static final int COLUMN_INDEX_SHIFT_NAME = 1;
    public static final int COLUMN_INDEX_START_TIME = 2;
    public static final int COLUMN_INDEX_END_TIME = 3;
    public static final int COLUMN_INDEX_CHECK_IN = 4;
    public static final int COLUMN_INDEX_CHECK_OUT = 5;
    public static final int COLUMN_INDEX_TOTAL_SHIFT = 6;
    public static final int COLUMN_INDEX_OT_START_TIME = 7;
    public static final int COLUMN_INDEX_OT_END_TIME = 8;
    public static final int COLUMN_INDEX_OT_CHECK_IN = 9;
    public static final int COLUMN_INDEX_OT_CHECK_OUT = 10;
    public static final int COLUMN_INDEX_TOTAL_OT = 11;
    public static final int COLUMN_INDEX_TOTAL_HOURS = 12;

    private static CellStyle cellStyleFormatNumber = null;

    public static void main(String[] args) throws IOException {
        StatisticsDAO staDAO = new StatisticsDAO();
        LocalDate startDate = LocalDate.parse("2024-02-01");
        LocalDate endDate = LocalDate.parse("2024-03-01");

        final ArrayList<StatisticsDTO> statistics = staDAO.getStatistics(3, startDate, endDate);
        final String excelFilePath = "C:\\Demo\\Book.xlsx";
        System.out.println(excelFilePath);
        writeExcel(statistics, excelFilePath);
    }

    public static void writeExcel(List<StatisticsDTO> statistics, String excelFilePath) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Create sheet
        Sheet sheet = workbook.createSheet("Statistics"); // Create sheet with sheet name
        int rowIndex = 0;

        // Write header
        writeHeader(sheet, rowIndex);
        writeHeader(sheet, rowIndex);
        // Write data
        rowIndex++;
        for (StatisticsDTO s : statistics) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            writeBook(s, row);
            rowIndex++;
        }

        // Write footer
        writeFooter(sheet, rowIndex);

        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Done!!!");
    }
// Create workbook

    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    private static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_DATE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Date");

        cell = row.createCell(COLUMN_INDEX_SHIFT_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Shift");

        cell = row.createCell(COLUMN_INDEX_START_TIME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Start-Time");

        cell = row.createCell(COLUMN_INDEX_END_TIME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("End-Time");

        cell = row.createCell(COLUMN_INDEX_CHECK_IN);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Check-In");

        cell = row.createCell(COLUMN_INDEX_CHECK_OUT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Check-Out");

        cell = row.createCell(COLUMN_INDEX_TOTAL_SHIFT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Total-Shift");

        cell = row.createCell(COLUMN_INDEX_OT_START_TIME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("OT-Start-Time");

        cell = row.createCell(COLUMN_INDEX_OT_END_TIME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("OT-End-Time");

        cell = row.createCell(COLUMN_INDEX_OT_CHECK_IN);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("OT-Check-In");

        cell = row.createCell(COLUMN_INDEX_OT_CHECK_OUT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("OT-Check-Out");

        cell = row.createCell(COLUMN_INDEX_TOTAL_OT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Total-OT");

        cell = row.createCell(COLUMN_INDEX_TOTAL_HOURS);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Total-Day");

    }

    // Write data
    private static void writeBook(StatisticsDTO s, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }

        Cell cell = row.createCell(COLUMN_INDEX_DATE);
        cell.setCellValue(s.getDate().toString());

        cell = row.createCell(COLUMN_INDEX_SHIFT_NAME);
        if (s.getShiftName().length() > 0 || s.getShiftName() != null) {
            cell.setCellValue(s.getShiftName());
        } else {
            cell.setCellValue("--");
        }

        cell = row.createCell(COLUMN_INDEX_START_TIME);
        if (s.getStartTime() != null) {
            cell.setCellValue(s.getStartTime().toString());
        } else {
            cell.setCellValue("--");
        }

        cell = row.createCell(COLUMN_INDEX_END_TIME);
        if (s.getEndTime() != null) {
            cell.setCellValue(s.getEndTime().toString());
        } else {
            cell.setCellValue("--");
        }

        cell = row.createCell(COLUMN_INDEX_CHECK_IN);
        if (s.getCheckIn() != null) {
            cell.setCellValue(s.getCheckIn().toString());
        } else {
            cell.setCellValue("--");
        }

        cell = row.createCell(COLUMN_INDEX_CHECK_OUT);
        if (s.getCheckOut() != null) {
            cell.setCellValue(s.getCheckOut().toString());
        } else {
            cell.setCellValue("--");
        }

        cell = row.createCell(COLUMN_INDEX_TOTAL_SHIFT);
        if (s.getShiftHours() != null) {
            cell.setCellValue((double) s.getShiftHours().toMinutes() / 60);
        } else {
            cell.setCellValue(0);
        }

        cell = row.createCell(COLUMN_INDEX_OT_START_TIME);
        if (s.getOtStartTime() != null) {
            cell.setCellValue(s.getOtStartTime().toString());
        } else {
            cell.setCellValue("--");
        }

        cell = row.createCell(COLUMN_INDEX_OT_END_TIME);
        if (s.getOtEndTime() != null) {
            cell.setCellValue(s.getOtEndTime().toString());
        } else {
            cell.setCellValue("--");
        }

        cell = row.createCell(COLUMN_INDEX_OT_CHECK_IN);
        if (s.getOtCheckIn() != null) {
            cell.setCellValue(s.getOtCheckIn().toString());
        } else {
            cell.setCellValue("--");
        }

        cell = row.createCell(COLUMN_INDEX_OT_CHECK_OUT);
        if (s.getOtCheckOut() != null) {
            cell.setCellValue(s.getOtCheckOut().toString());
        } else {
            cell.setCellValue("--");
        }

        cell = row.createCell(COLUMN_INDEX_TOTAL_OT);
        if (s.getOtHours() != null) {
            cell.setCellValue((double) s.getOtHours().toMinutes() / 60);
        } else {
            cell.setCellValue(0);
        }

//        cell = row.createCell(COLUMN_INDEX_TOTAL_HOURS);
        // Create cell formula
        // total hours = total_shift + total_ot
        cell = row.createCell(COLUMN_INDEX_TOTAL_HOURS, CellType.FORMULA);
        cell.setCellStyle(cellStyleFormatNumber);
        int currentRow = row.getRowNum() + 1;
        String columnTotalShift = CellReference.convertNumToColString(COLUMN_INDEX_TOTAL_SHIFT);
        String columnTotalOT = CellReference.convertNumToColString(COLUMN_INDEX_TOTAL_OT);
        cell.setCellFormula("ROUND((" + columnTotalShift + currentRow + "+" + columnTotalOT + currentRow + "),2)");
    }
    // Write footer

    private static void writeFooter(Sheet sheet, int rowIndex) {
        // Create row
        Row row = sheet.createRow(rowIndex);
        String columnTotal = CellReference.convertNumToColString(COLUMN_INDEX_TOTAL_HOURS);
        String columnTotalShift = CellReference.convertNumToColString(COLUMN_INDEX_TOTAL_SHIFT);
        String columnTotalOt = CellReference.convertNumToColString(COLUMN_INDEX_TOTAL_OT);

        Cell cell_Total_Shift = row.createCell(COLUMN_INDEX_TOTAL_HOURS, CellType.FORMULA);
        cell_Total_Shift.setCellFormula("SUM(" + columnTotalShift + ":" + columnTotalShift + ")");

        Cell cell_Total_Ot = row.createCell(COLUMN_INDEX_TOTAL_HOURS, CellType.FORMULA);
        cell_Total_Ot.setCellFormula("SUM(" + columnTotalOt + ":" + columnTotalOt + ")");

        Cell cell = row.createCell(COLUMN_INDEX_TOTAL_HOURS, CellType.FORMULA);
        cell.setCellFormula("ROUND(SUM(" + columnTotalShift + ":" + columnTotalOt + "),2)");
    }

    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }
    // Auto resize column width

    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try ( OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }

}
