package model;

import dbhelper.DAOBase;
import java.time.LocalDate;
import java.time.LocalTime;
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
                int openBefore = rs.getInt("openBefore");
                int closeAfter = rs.getInt("closeAfter");
                return new OvertimeDTO(date, employeeID, startTime, endTimeD, checkIn, checkOut, openBefore, closeAfter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return null;
    }
    
    public static void main(String[] args) {
        OvertimeDAO overtimeDAO = new OvertimeDAO();
        LocalDate date = LocalDate.parse("2024-01-20");
        System.out.println(overtimeDAO.getOverTimeDTO(date, 1));
    }
}
