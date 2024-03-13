package ultility.exportFile;

import dbhelper.DBContext;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet(name = "DownloadWorkSheet", urlPatterns = {"/DownloadWorkSheet"})
public class DownloadWorkSheet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            
            DBContext db = new DBContext();
            Connection connection = db.connection;
            String query = "" +
                    "SELECT \n" +
                    "	EM.EmployeeID,	\n" +
                    "	(EM.LastName + ' ' + EM.MiddleName + ' ' + EM.FirstName) AS Name,\n" +
                    "	ET.Name AS EmployeeType,\n" +
                    "	TB.Date,\n" +
                    "	TB.StartTime,\n" +
                    "	TB.EndTime,\n" +
                    "	TB.CheckIn,\n" +
                    "	TB.CheckOut,\n" +
                    "	TB.ShiftType,\n" +
                    "	TB.Leave\n" +
                    "FROM\n" +
                    "(\n" +
                    "	SELECT \n" +
                    "		TS.EmployeeID\n" +
                    "		,TS.Date\n" +
                    "		,S.StartTime\n" +
                    "		,S.EndTime\n" +
                    "		,TS.CheckIn\n" +
                    "		,TS.CheckOut\n" +
                    "		,S.Name as ShiftType\n" +
                    "		, CASE WHEN LeaveID is not null THEN N'Nghỉ phép' ELSE '' END AS Leave\n" +
                    "	FROM \n" +
                    "		Timesheet TS\n" +
                    "		JOIN Shifts S ON TS.ShiftID = S.ShiftID\n" +
                    "		LEFT JOIN Leaves L ON TS.Date >= L.StartDate AND TS.Date <= L.EndDate AND TS.EmployeeID = L.EmployeeID\n" +
                    "UNION ALL\n" +
                    "\n" +
                    "	SELECT\n" +
                    "		OT.EmployeeID\n" +
                    "		,OT.Date\n" +
                    "		,OT.StartTime\n" +
                    "		,OT.EndTime\n" +
                    "		,OT.CheckIn\n" +
                    "		,OT.CheckOut\n" +
                    "		,N'Tăng ca' as ShiftType\n" +
                    "		, CASE WHEN LeaveID is not null THEN N'Nghỉ phép' ELSE '' END AS Leave\n" +
                    "	FROM Overtimes OT\n" +
                    "	LEFT JOIN Leaves L ON OT.Date >= L.StartDate AND OT.Date <= L.EndDate AND OT.EmployeeID = L.EmployeeID\n" +
                    ") AS TB\n" +
                    "	JOIN Employees EM ON TB.EmployeeID = EM.EmployeeID\n" +
                    "	JOIN EmployeeTypes ET ON EM.EmployeeTypeID = ET.EmployeeTypeID\n" +
                    "WHERE \n" +
                    "	TB.Date BETWEEN ? AND ?\n" +
                    "ORDER BY EM.FirstName, TB.Date";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ResultSet resultSet = ps.executeQuery();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Mã nhân viên");
            headerRow.createCell(1).setCellValue("Họ và tên");
            headerRow.createCell(2).setCellValue("Chức vụ");
            headerRow.createCell(3).setCellValue("Ngày");
            headerRow.createCell(4).setCellValue("Giờ bắt đầu");
            headerRow.createCell(5).setCellValue("Giờ kết thúc");
            headerRow.createCell(6).setCellValue("Giờ vào");
            headerRow.createCell(7).setCellValue("Giờ ra");
            headerRow.createCell(8).setCellValue("Loại ca");
            headerRow.createCell(9).setCellValue("Nghỉ phép");

            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(resultSet.getInt("EmployeeID"));
                row.createCell(1).setCellValue(resultSet.getString("Name").toString());
                row.createCell(2).setCellValue(resultSet.getString("EmployeeType").toString());
                row.createCell(3).setCellValue(resultSet.getDate("Date").toString());
                row.createCell(4).setCellValue(resultSet.getTime("StartTime").toString());
                row.createCell(5).setCellValue(resultSet.getTime("EndTime").toString());
                row.createCell(6).setCellValue(resultSet.getTime("CheckIn") == null ? "" : resultSet.getTime("CheckIn").toString());
                row.createCell(7).setCellValue(resultSet.getTime("CheckOut") == null ? "" : resultSet.getTime("CheckOut").toString());
                row.createCell(8).setCellValue(resultSet.getString("ShiftType"));
                row.createCell(9).setCellValue(resultSet.getString("Leave"));
            }

            // Lưu workbook vào OutputStream
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=output.xlsx");
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);

            // Đóng các resource
            workbook.close();
            resultSet.close();
            ps.close();
            connection.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Có lỗi xảy ra, vui lòng thử lại");
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
// </editor-fold>

}
