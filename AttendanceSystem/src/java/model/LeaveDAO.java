package model;

import dbhelper.DAOBase;
import java.time.LocalDate;
import java.time.LocalTime;
import ultility.datetimeutil.DateTimeUtil;

public class LeaveDAO extends DAOBase {

    static final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    public LeaveDTO getLeaveDTO(int xEmployeeID, String xDate) {
        query = "SELECT * FROM Leaves\n" +
                "WHERE \n" +
                "	EmployeeID = ? \n" +
                "	AND ? Between [StartDate] AND [EndDate] ";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, xEmployeeID);
            ps.setString(2, xDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                int leaveID = rs.getInt("leaveID");
                int employeeID = rs.getInt("employeeID");
                LocalDate startDate = DATE_UTIL.parseSqlDate(rs.getDate("startDate"));
                LocalDate endDate = DATE_UTIL.parseSqlDate(rs.getDate("endDate"));
                String filePath = rs.getNString("filePath");
                LocalDate createdDate = DATE_UTIL.parseSqlDate(rs.getDate("CreatedDate"));
                int createdBy = rs.getInt("createdBy");
                return new LeaveDTO(leaveID, employeeID, startDate, endDate, filePath, createdDate, createdBy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return null;
    }

    public static void main(String[] args) {
        LeaveDAO leaveDAO = new LeaveDAO();
        System.out.println(leaveDAO.getLeaveDTO(1, "2024-02-01"));
    }
}
