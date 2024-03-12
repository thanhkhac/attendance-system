/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ultility.exportFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import model.*;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;

/**
 *
 * @author admin
 */
public class ExportEmployeeFormattedFile {

    public static final int COLUMN_INDEX_FIRST_NAME = 2;
    public static final int COLUMN_INDEX_MIDDLE_NAME = 1;
    public static final int COLUMN_INDEX_LAST_NAME = 0;
    public static final int COLUMN_INDEX_BIRTH_DATE = 3;
    public static final int COLUMN_INDEX_GENDER = 4;
    public static final int COLUMN_INDEX_EMAIL = 5;
    public static final int COLUMN_INDEX_PASSWORD = 6;
    public static final int COLUMN_INDEX_CCCD = 7;
    public static final int COLUMN_INDEX_PHONE_NUMBER = 8;
    public static final int COLUMN_INDEX_EMPLOYEE_TYPE = 9;
    public static final int COLUMN_INDEX_DEPARTMENT = 10;
    public static final int COLUMN_INDEX_ROLE = 11;
    public static final int COLUMN_INDEX_START_DATE = 12;
    public static final int COLUMN_INDEX_END_DATE = 13;
//    public static final int COLUMN_INDEX_IS_ACTIVED = 15;
    private static CellStyle cellStyleFormatNumber = null;
    private static CellStyle cellStyleFormatText = null;
    private static CellStyle cellStyleFormatDate = null;

    public static void main(String[] args) throws IOException {
        final String excelFilePath = "C:\\Demo\\Employee.xlsx";
        writeExcel(excelFilePath);
        System.out.println(excelFilePath);
        ExportEmployeeFormattedFile ex = new ExportEmployeeFormattedFile();
        String[] de = ex.getDepartments();
        String[] ty = ex.getEmployeeTypes();
        String[] ro = ex.getRoles();

        for (String s : de) {
            System.out.println(s);
        }

    }

    public static void writeExcel(String excelFilePath) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Create sheet
        Sheet sheet = workbook.createSheet("Employee"); // Create sheet with sheet name
        int rowIndex = 0;

        // Write header
        writeHeader(sheet, rowIndex);
//        writeHeader(sheet, rowIndex);
        // Write data
        rowIndex++;
        for (int i = 1; i <= 100; i++) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            WriteBody(row);
            rowIndex++;
        }

        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Done!!!");
    }

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
        Cell cell = row.createCell(COLUMN_INDEX_LAST_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("LastName");

        cell = row.createCell(COLUMN_INDEX_MIDDLE_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("MiddleName");

        cell = row.createCell(COLUMN_INDEX_FIRST_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("FirstName");

        cell = row.createCell(COLUMN_INDEX_BIRTH_DATE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("BirthDate");

        cell = row.createCell(COLUMN_INDEX_GENDER);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Gender");

        cell = row.createCell(COLUMN_INDEX_EMAIL);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Email");

        cell = row.createCell(COLUMN_INDEX_PASSWORD);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Password");

        cell = row.createCell(COLUMN_INDEX_CCCD);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("CCCD");

        cell = row.createCell(COLUMN_INDEX_PHONE_NUMBER);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Phone");

        cell = row.createCell(COLUMN_INDEX_EMPLOYEE_TYPE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("EmployeeType");

        cell = row.createCell(COLUMN_INDEX_DEPARTMENT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Department");

        cell = row.createCell(COLUMN_INDEX_ROLE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Role");

        cell = row.createCell(COLUMN_INDEX_START_DATE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("StartDate");

        cell = row.createCell(COLUMN_INDEX_END_DATE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("EndDate");
    }

    private String[] getDepartments() {
        DepartmentDAO deDAO = new DepartmentDAO();
        ArrayList<DepartmentDTO> departments = deDAO.getListDepartment();
        String[] departmentName = new String[departments.size()];
        int i = 0;
        for (DepartmentDTO d : departments) {
            departmentName[i] = d.getDepartmentID() + "-" + d.getName();
            i++;
        }
        return departmentName;
    }

    private String[] getEmployeeTypes() {
        EmployeeTypeDAO emtDAO = new EmployeeTypeDAO();
        ArrayList<EmployeeTypeDTO> types = emtDAO.getEmployeeTypeList();
        String[] typeName = new String[types.size()];
        int i = 0;
        for (EmployeeTypeDTO et : types) {
            typeName[i] = et.getEmployeeTypeID() + "-" + et.getName();
            i++;
        }
        return typeName;
    }

    private String[] getRoles() {
        RoleDAO rDAO = new RoleDAO();
        ArrayList<RoleDTO> roles = rDAO.getRoleList();
        String[] roleName = new String[roles.size()];
        int i = 0;
        for (RoleDTO r : roles) {
            roleName[i] = r.getRoleID() + "-" + r.getName();
            i++;
        }
        return roleName;
    }

    private String[] getGender() {
        String[] gender = new String[2];
        gender[0] = "Female";
        gender[1] = "Male";
        return gender;
    }

    private static void applyDropdownValidation(Sheet sheet, int columnIndex, String[] items) {
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        CellRangeAddressList addressList = new CellRangeAddressList(1, 1048575, columnIndex, columnIndex);

//        String[] itemArray = items.stream().map(Object::toString).toArray(String[]::new);
        DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(items);

        DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
        dataValidation.setShowErrorBox(true);
        dataValidation.setEmptyCellAllowed(false);

        sheet.addValidationData(dataValidation);
    }

    private static void WriteBody(Row row) {
        cellStyleFormatNumber = null;
        cellStyleFormatText = null;
        cellStyleFormatDate = null;
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

        ExportEmployeeFormattedFile importFile = new ExportEmployeeFormattedFile();
        String[] departments = importFile.getDepartments();
        String[] types = importFile.getEmployeeTypes();
        String[] roles = importFile.getRoles();
        String[] gender = importFile.getGender();
        Sheet sheet = row.getSheet();
        CellStyle cellStyle = createStyleForBody(sheet);
        Cell cell = row.createCell(COLUMN_INDEX_LAST_NAME);
        cell.setCellStyle(createStyleForBody(sheet, "text"));

        cell = row.createCell(COLUMN_INDEX_MIDDLE_NAME);
        cell.setCellStyle(createStyleForBody(sheet, "text"));

        cell = row.createCell(COLUMN_INDEX_FIRST_NAME);
        cell.setCellStyle(createStyleForBody(sheet, "text"));

        cell = row.createCell(COLUMN_INDEX_BIRTH_DATE);
//        cell.setCellStyle(createStyleForBody(sheet, "date"));
        cell.setCellStyle(cellStyle);

        cell = row.createCell(COLUMN_INDEX_GENDER);
        for (String g : gender) {
            cell.setCellValue(g);
        }
        cell.setCellStyle(createStyleForBody(sheet, "text"));
        applyDropdownValidation(sheet, COLUMN_INDEX_GENDER, gender);

        cell = row.createCell(COLUMN_INDEX_EMAIL);
        cell.setCellStyle(createStyleForBody(sheet, "text"));

        cell = row.createCell(COLUMN_INDEX_PASSWORD);
        cell.setCellStyle(createStyleForBody(sheet, "text"));

        cell = row.createCell(COLUMN_INDEX_CCCD);
        cell.setCellStyle(createStyleForBody(sheet, "text"));

        cell = row.createCell(COLUMN_INDEX_PHONE_NUMBER);
        cell.setCellStyle(createStyleForBody(sheet, "text"));

        cell = row.createCell(COLUMN_INDEX_EMPLOYEE_TYPE);
        for (String t : types) {
            cell.setCellValue(t);
        }
        cell.setCellStyle(createStyleForBody(sheet, "text"));
        applyDropdownValidation(sheet, COLUMN_INDEX_EMPLOYEE_TYPE, types);

        cell = row.createCell(COLUMN_INDEX_DEPARTMENT);
        for (String d : departments) {
            cell.setCellValue(d);
        }
        cell.setCellStyle(createStyleForBody(sheet, "text"));
        applyDropdownValidation(sheet, COLUMN_INDEX_DEPARTMENT, departments);

        cell = row.createCell(COLUMN_INDEX_ROLE);
        for (String r : roles) {
            cell.setCellValue(r);
        }
        cell.setCellStyle(createStyleForBody(sheet, "text"));
        applyDropdownValidation(sheet, COLUMN_INDEX_ROLE, roles);

        cell = row.createCell(COLUMN_INDEX_START_DATE);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(COLUMN_INDEX_END_DATE);
        cell.setCellStyle(cellStyle);

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
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    private static CellStyle createStyleForBody(Sheet sheet, String type) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Workbook workbook = sheet.getWorkbook();
        DataFormat format = workbook.createDataFormat();
        CreationHelper createHelper = workbook.getCreationHelper();
        switch (type) {
            case "text": {
                cellStyle.setDataFormat(format.getFormat("@"));
                break;
            }
            case "date": {
                cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
                break;
            }
            case "number": {
                cellStyle.setDataFormat(format.getFormat("0"));
                break;
            }
            default:
                throw new AssertionError();
        }
        return cellStyle;
    }

    private static CellStyle createStyleForBody(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Workbook workbook = sheet.getWorkbook();

        return cellStyle;
    }

    // Auto resize column width
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    public static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try ( OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }
}
