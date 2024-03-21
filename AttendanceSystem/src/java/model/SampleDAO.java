package model;

import dbhelper.DAOBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import static model.ShiftDAO.DATE_UTIL;
import model.request.RequestDTO;

public class SampleDAO extends DAOBase {

    public ShiftDTO getShiftDTO(int xShiftID) {
        connect();
        query = "";
        try {
            //Mở kết nối
            super.connect();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return null;
    }

    public RequestDTO insert(RequestDTO request) {
        connect();
        query = "";
        try {
            //Mở kết nối
            super.connect();
            ps = connection.prepareStatement(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return null;
    }

    public void printAll() {
        query = "-- Khai báo bảng tạm\n" +
                "DECLARE @TempTable TABLE(\n" +
                "    OvertimeRequestID INT\n" +
                ");\n" +
                "\n" +
                "INSERT INTO @TempTable\n" +
                "SELECT OTR.OvertimeRequestID\n" +
                "FROM \n" +
                "    OvertimeRequests OTR \n" +
                "    JOIN Overtimes OT ON OTR.Date = OT.Date AND OTR.EmployeeID = OT.EmployeeID \n" +
                "WHERE \n" +
                "    (OTR.StartTime > OT.StartTime AND OTR.StartTime < OT.EndTime)\n" +
                "    OR\n" +
                "    (OTR.EndTime > OT.StartTime AND OTR.EndTime < OT.EndTime)\n" +
                "    OR\n" +
                "    (OT.StartTime > OTR.StartTime AND OT.StartTime < OTR.EndTime)\n" +
                "    OR\n" +
                "    (OT.EndTime > OTR.StartTime AND OT.EndTime < OTR.EndTime)\n" +
                "    OR\n" +
                "    (OTR.StartTime = OT.StartTime)\n" +
                "    OR\n" +
                "    (OTR.EndTime = OT.EndTime);\n" +
                "\n" +
                "-- Chèn dữ liệu từ truy vấn thứ hai\n" +
                "INSERT INTO @TempTable\n" +
                "SELECT OTR.OvertimeRequestID\n" +
                "FROM \n" +
                "    OvertimeRequests OTR	\n" +
                "    JOIN Timesheet TS ON OTR.Date = TS.Date\n" +
                "    JOIN Shifts SH ON TS.ShiftID = SH.ShiftID\n" +
                "WHERE\n" +
                "    (OTR.StartTime > SH.StartTime AND OTR.StartTime < SH.EndTime)\n" +
                "    OR\n" +
                "    (OTR.EndTime > SH.StartTime AND OTR.EndTime < SH.EndTime)\n" +
                "    OR\n" +
                "    (SH.StartTime > OTR.StartTime AND SH.StartTime < OTR.EndTime)\n" +
                "    OR\n" +
                "    (SH.EndTime > OTR.StartTime AND SH.EndTime < OTR.EndTime)\n" +
                "    OR\n" +
                "    (OTR.StartTime = SH.StartTime)\n" +
                "    OR\n" +
                "    (OTR.EndTime = SH.EndTime);\n" +
                "--Câu truy vấn để hiển thị\n" +
                "SELECT\n" +
                "    OvertimeRequestID,\n" +
                "    Date,\n" +
                "    EmployeeID,\n" +
                "    SentDate,\n" +
                "    StartTime,\n" +
                "    EndTime, \n" +
                "    ManagerApprove,\n" +
                "    HrApprove,\n" +
                "    ManagerID,\n" +
                "    HrID,\n" +
                "    CreatedBy,\n" +
                "    [Status],\n" +
                "    CASE \n" +
                "        WHEN OvertimeRequestID IN (SELECT OvertimeRequestID FROM @TempTable) THEN 'conflict'\n" +
                "        ELSE NULL\n" +
                "    END AS Conflict\n" +
                "FROM OvertimeRequests\n" +
                "ORDER BY \n" +
                "    Conflict";

        try ( Connection connection = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = connection.prepareStatement(query)) {
            System.out.println("Executing the query...");
            ps.execute();

            ResultSet rs = ps.getResultSet();
            if (rs != null) {
                System.out.println("Processing the result set...");
                int count = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= count; i++) {
                        System.out.println(rs.getNString(i));
                    }
                }
            } else {
                System.out.println("No result set returned.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SampleDAO dao = new SampleDAO();
        dao.printAll();
    }

}
