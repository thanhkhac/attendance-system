package model;

import dbhelper.DAOBase;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import ultility.datetimeutil.DateTimeUtil;

public class OvertimeDAO extends DAOBase {

    final DateTimeUtil dateTimeUtil = new DateTimeUtil();

    public OvertimeDTO getOverTimeDTO(LocalDate xDate, int xEmployeeID) {
        query = "SELECT * FROM Overtimes\n" +
                "WHERE\n" +
                "	EmployeeID = ? AND [Date] = ? ";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, xEmployeeID);
            ps.setString(2, xDate.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate date = dateTimeUtil.parseSqlDate(rs.getDate("date"));
                int employeeID = rs.getInt("EmployeeID");
                LocalTime startTime = dateTimeUtil.parseSQLTime(rs.getTime("StartTime"));
                LocalTime endTimeD = dateTimeUtil.parseSQLTime(rs.getTime("EndTime"));
                LocalTime checkIn = dateTimeUtil.parseSQLTime(rs.getTime("CheckIn"));
                LocalTime checkOut = dateTimeUtil.parseSQLTime(rs.getTime("CheckOut"));
                LocalTime openAt = dateTimeUtil.parseSQLTime(rs.getTime("OpenAt"));
                LocalTime closeAt = dateTimeUtil.parseSQLTime(rs.getTime("CloseAt"));;
                int createdBy = rs.getInt("createdBy");
                return new OvertimeDTO(date, employeeID, startTime, endTimeD, openAt, closeAt, checkIn, checkOut, createdBy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return null;
    }
    
    public ArrayList<LocalDate> getAllOverTimeDTO() {
        ArrayList<LocalDate> list = new ArrayList<>();
        query = "select distinct Date from [Overtimes]";
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate date = dateTimeUtil.parseSqlDate(rs.getDate("date"));
                
                list.add(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return list;
    }
    
    public ArrayList<OvertimeDTO> getOverTimeDTOByDay(LocalDate xDate) {
        ArrayList<OvertimeDTO> list = new ArrayList<>();
        query = "SELECT * FROM Overtimes\n" +
                "WHERE\n" +
                "	[Date] = ? ";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, xDate.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate date = dateTimeUtil.parseSqlDate(rs.getDate("date"));
                int employeeID = rs.getInt("EmployeeID");
                LocalTime startTime = dateTimeUtil.parseSQLTime(rs.getTime("StartTime"));
                LocalTime endTimeD = dateTimeUtil.parseSQLTime(rs.getTime("EndTime"));
                LocalTime checkIn = dateTimeUtil.parseSQLTime(rs.getTime("CheckIn"));
                LocalTime checkOut = dateTimeUtil.parseSQLTime(rs.getTime("CheckOut"));
                LocalTime openAt = dateTimeUtil.parseSQLTime(rs.getTime("OpenAt"));
                LocalTime closeAt = dateTimeUtil.parseSQLTime(rs.getTime("CloseAt"));;
                int createdBy = rs.getInt("createdBy");
                list.add(new OvertimeDTO(date, employeeID, startTime, endTimeD, openAt, closeAt, checkIn, checkOut, createdBy));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return list;
    }

    public OvertimeDTO getConflicts(int xEmployeeid, String xDate, int xShiftID) {
        query = "DECLARE @EmployeeID INT = ? \n" +
                "DECLARE @Date DATE = ? \n" +
                "DECLARE @ShiftID INT = ? \n" +
                "\n" +
                "SELECT \n" +
                "	OT.Date,\n" +
                "	OT.EmployeeID,\n" +
                "	OT.StartTime,\n" +
                "	OT.EndTime,\n" +
                "	OT.OpenAt,\n" +
                "	OT.CloseAt,\n" +
                "	OT.CheckIn,\n" +
                "	OT.CheckOut,\n" +
                "	OT.CreatedBy\n" +
                "FROM \n" +
                "    Overtimes OT\n" +
                "    JOIN Shifts S ON OT.Date = @Date\n" +
                "WHERE \n" +
                "    ShiftID = @ShiftID\n" +
                "	AND EmployeeID = @EmployeeID\n" +
                "    AND \n" +
                "    (   \n" +
                "        (OT.StartTime > S.StartTime AND OT.StartTime < S.EndTime)\n" +
                "        OR\n" +
                "        (OT.EndTime > S.StartTime AND OT.EndTime < S.EndTime)\n" +
                "        OR \n" +
                "        (S.StartTime > OT.StartTime AND S.StartTime < OT.EndTime)\n" +
                "        OR\n" +
                "        (S.EndTime > OT.StartTime AND S.EndTime < OT.EndTime)\n" +
                "		OR\n" +
                "		(S.StartTime = OT.StartTime)\n" +
                "		OR \n" +
                "		(S.EndTime = OT.EndTime)\n" +
                "    )";
        try {
            //Mở kết nối
            super.connect();
            ps = con.prepareStatement(query);
            ps.setInt(1, xEmployeeid);
            ps.setString(2, xDate);
            ps.setInt(3, xShiftID);
            rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate date = dateTimeUtil.parseSqlDate(rs.getDate("date"));
                int employeeID = rs.getInt("EmployeeID");
                LocalTime startTime = dateTimeUtil.parseSQLTime(rs.getTime("StartTime"));
                LocalTime endTimeD = dateTimeUtil.parseSQLTime(rs.getTime("EndTime"));
                LocalTime checkIn = dateTimeUtil.parseSQLTime(rs.getTime("CheckIn"));
                LocalTime checkOut = dateTimeUtil.parseSQLTime(rs.getTime("CheckOut"));
                LocalTime openAt = dateTimeUtil.parseSQLTime(rs.getTime("OpenAt"));
                LocalTime closeAt = dateTimeUtil.parseSQLTime(rs.getTime("CloseAt"));;
                int createdBy = rs.getInt("createdBy");
                return new OvertimeDTO(date, employeeID, startTime, endTimeD, openAt, closeAt, checkIn, checkOut, createdBy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Đóng PrepareStatement, ResultSet, Connection
            super.closeAll();
        }
        return null;
    }

    public static void main(String[] args) {
        OvertimeDAO overtimeDAO = new OvertimeDAO();
        LocalDate date = LocalDate.parse("   ");
        LocalDate date2 = LocalDate.parse("2024-02-24");
        //System.out.println(date.compareTo(date2));
//        System.out.println(overtimeDAO.getOverTimeDTO(date, 1));
//        System.out.println(overtimeDAO.getConflicts(1, "2024-02-23", 1));
           System.out.println(overtimeDAO.getOverTimeDTOByDay(date).size());
    }
}
