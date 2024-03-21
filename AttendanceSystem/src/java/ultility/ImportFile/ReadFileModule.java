/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ultility.ImportFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import model.EmployeeDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.math3.linear.*;
import ultility.datetimeutil.DateTimeUtil;

/**
 *
 * @author admin
 */
public class ReadFileModule {

    public static final int COLUMN_INDEX_FIRST_NAME = 2;
    public static final int COLUMN_INDEX_MIDDLE_NAME = 1;
    public static final int COLUMN_INDEX_LAST_NAME = 0;
    public static final int COLUMN_INDEX_BIRTH_DATE = 3;
    public static final int COLUMN_INDEX_GENDER = 4;
    public static final int COLUMN_INDEX_EMAIL = 5;
    public static final int COLUMN_INDEX_PASSWORD = 6;
    public static final int COLUMN_INDEX_CCCD = 7;
    public static final int COLUMN_INDEX_PHONE_NUMBER = 8;
    public static final int COLUMN_INDEX_EMPLOYEE_TYPE_ID = 9;
    public static final int COLUMN_INDEX_DEPARTMENT_ID = 10;
    public static final int COLUMN_INDEX_ROLE_ID = 11;
    public static final int COLUMN_INDEX_START_DATE = 12;
    public static final int COLUMN_INDEX_END_DATE = 13;

    private static final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    private static int getIdFromString(String s) {
        String[] str = s.split("-");
        return Integer.parseInt(str[0]);
    }

    private static boolean convertGender(String s) {
        return s.equals("Male") ? true : false;
    }

    public ArrayList<EmployeeDTO> readExcel(InputStream inputStream) throws IOException, ParseException {

        ArrayList<EmployeeDTO> listEmployees = new ArrayList<>();

        // Get file
//        InputStream inputStream = new FileInputStream(new File(excelFilePath));
        // Get workbook
        Workbook workbook = WorkbookFactory.create(inputStream);

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }
            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for book object
            EmployeeDTO employee = new EmployeeDTO();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    cell.setCellValue("--");
//                    continue;
                }
                // Set value for employee object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {

                    case COLUMN_INDEX_FIRST_NAME: {
                        // Set firstName property
                        if (getCellValue(cell).toString().equals("--")) {
                            employee.setFirstName("");
                        } else {
                            employee.setFirstName(getCellValue(cell).toString());
                        }
                        break;
                    }

                    case COLUMN_INDEX_MIDDLE_NAME: {
                        // Set middleName property
                        if (getCellValue(cell).toString().equals("--")) {
                            employee.setMiddleName("");
                        } else {
                            employee.setMiddleName(getCellValue(cell).toString());
                        }
                        break;
                    }

                    case COLUMN_INDEX_LAST_NAME: {
                        // Set lastName property
                        if (getCellValue(cell).toString().equals("--")) {
                            employee.setLastName("");
                        } else {
                            employee.setLastName(getCellValue(cell).toString());
                        }
                        break;
                    }
                    case COLUMN_INDEX_BIRTH_DATE:
                        if (getCellValue(cell).toString().equals("--")) {
                            employee.setBirthDate(null);
                        } else {
                            employee.setBirthDate(DATE_UTIL.parseUtilToSqlDate(cell.getDateCellValue()));
                        }
                        break;
                    case COLUMN_INDEX_GENDER: {
                        // Set gender property
                        employee.setGender(convertGender(getCellValue(cell).toString()));
                        break;
                    }
                    case COLUMN_INDEX_EMAIL: {
                        // Set email property
                        if (getCellValue(cell).toString().equals("--")) {
                            employee.setEmail("");
                        } else {
                            employee.setEmail(cell.getStringCellValue());
                        }
                        break;
                    }
                    case COLUMN_INDEX_PASSWORD: {
                        // Set password property
                        if (getCellValue(cell).toString().equals("--")) {
                            employee.setPassword("");
                        } else {
                            employee.setPassword(getCellValue(cell).toString());
                        }
                        break;
                    }
                    case COLUMN_INDEX_CCCD: {
                        // Set cccd property
                        if (getCellValue(cell).toString().equals("--")) {
                            employee.setCccd("");
                        } else {
                            employee.setCccd(getCellValue(cell).toString());
                        }
                        break;
                    }
                    case COLUMN_INDEX_PHONE_NUMBER: {
                        // Set phoneNumber property

                        if (getCellValue(cell).toString().equals("--")) {
                            employee.setPhoneNumber("");
                        } else {
                            employee.setPhoneNumber(getCellValue(cell).toString());
                        }
                        break;
                    }
                    case COLUMN_INDEX_EMPLOYEE_TYPE_ID: {
                        // Set employeeTypeID property
                        employee.setEmployeeTypeID(getIdFromString(getCellValue(cell).toString()));
                        break;
                    }
                    case COLUMN_INDEX_DEPARTMENT_ID: {
                        // Set departmentID property
                        employee.setDepartmentID(getIdFromString(getCellValue(cell).toString()));
                        break;
                    }
                    case COLUMN_INDEX_ROLE_ID: {
                        // Set roleID property
                        employee.setRoleID(getIdFromString(getCellValue(cell).toString()));
                        break;
                    }
                    case COLUMN_INDEX_START_DATE: {
                        // Set startDate property
                        if (getCellValue(cell).toString().equals("--")) {
                            employee.setStartDate(null);
                        } else {
                            employee.setStartDate(DATE_UTIL.parseUtilToSqlDate(cell.getDateCellValue()));
                        }
                        break;
                    }
                    case COLUMN_INDEX_END_DATE: {
                        // Set endDate property
                        if (getCellValue(cell).toString().equals("--")) {
                            employee.setEndDate(null);
                        } else {
                            employee.setEndDate(DATE_UTIL.parseUtilToSqlDate(cell.getDateCellValue()));
                        }
                        break;
                    }
                    default: {
                        // Handle unknown column index or ignore it
                        break;
                    }

                }
            }
            listEmployees.add(employee);
        }

        workbook.close();
        inputStream.close();

        return listEmployees;
    }

    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    private static CellStyle createStyleForBody(Sheet sheet, String type) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Workbook workbook = sheet.getWorkbook();
        DataFormat format = workbook.createDataFormat();
        switch (type) {
            case "text": {
                cellStyle.setDataFormat(format.getFormat("@"));
                break;
            }
            case "date": {
                cellStyle.setDataFormat(format.getFormat("Short Date"));
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

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        try {
            switch (cellType) {
                case BOOLEAN:
                    cellValue = cell.getBooleanCellValue();
                    break;
                case FORMULA:
                    Workbook workbook = cell.getSheet().getWorkbook();
                    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                    cellValue = evaluator.evaluate(cell).getNumberValue();
                    break;
                case NUMERIC:
                    cellValue = cell.getNumericCellValue();
                    break;
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case _NONE:
                case BLANK:
                case ERROR:
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
        }

        return cellValue;
    }
}
