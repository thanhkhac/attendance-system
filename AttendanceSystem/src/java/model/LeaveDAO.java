package model;

import dbhelper.DAOBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import ultility.datetimeutil.DateTimeUtil;

public class LeaveDAO extends DAOBase {

    static final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    private LeaveDTO getLeaveDTOrs(ResultSet rs) throws SQLException {
        int leaveID = rs.getInt("leaveID");
        int employeeID = rs.getInt("employeeID");
        LocalDate startDate = DATE_UTIL.parseSqlDate(rs.getDate("startDate"));
        LocalDate endDate = DATE_UTIL.parseSqlDate(rs.getDate("endDate"));
        String filePath = rs.getNString("filePath");
        LocalDate createdDate = DATE_UTIL.parseSqlDate(rs.getDate("CreatedDate"));
        int createdBy = rs.getInt("createdBy");

        return new LeaveDTO(leaveID, employeeID, startDate, endDate, filePath, createdDate, createdBy);
    }

    public LeaveDTO getLeaveDTO(int xEmployeeID, String xDate) {
        connect();
        query = "SELECT * FROM Leaves\n"
                + "WHERE \n"
                + "	EmployeeID = ? \n"
                + "	AND ? Between [StartDate] AND [EndDate] ";
        try {
            ps = connection.prepareStatement(query);
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
            closeAll();
        }
        return null;
    }

    public ArrayList<LeaveDTO> getLeaveInRange(int employeeID, LocalDate startDate, LocalDate endDate) {
        ArrayList<LeaveDTO> listLeave = new ArrayList<>();
        connect();
        try {
            String sql = "SELECT * FROM Leaves "
                    + "WHERE EmployeeID = ? "
                    + "AND ((StartDate BETWEEN ? AND ?  ) AND (EndDate BETWEEN ? AND ? ))";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, employeeID);
            ps.setString(2, startDate.toString());
            ps.setString(3, endDate.toString());
            ps.setString(4, startDate.toString());
            ps.setString(5, endDate.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                listLeave.add(getLeaveDTOrs(rs));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return listLeave;

    }

    public static void main(String[] args) {
        LeaveDAO leaveDAO = new LeaveDAO();
        System.out.println(leaveDAO.getLeaveDTO(1, "2024-02-01"));
    }

}
