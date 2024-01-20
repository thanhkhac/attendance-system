package model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import dbhelper.DAOBase;
import ultility.datetimeutil.DateTimeUtil;

public class TimesheetDAO extends DAOBase {

    private final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    public TimesheetDTO getTimesheetDTO(int sTimesheetID) {
        query = "SELECT * FROM Timesheet \n" +
                "WHERE \n" +
                "	TimesheetID = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, sTimesheetID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int timesheetID = rs.getInt("TimesheetID");
                LocalDate date = DATE_UTIL.parseSqlDate(rs.getDate("Date"));
                int employeeID = rs.getInt("EmployeeID");
                int shiftID = rs.getInt("ShiftID");
                LocalTime checkin = DATE_UTIL.parseSQLTime(rs.getTime("CheckIn"));
                LocalTime checkout = DATE_UTIL.parseSQLTime(rs.getTime("CheckOut"));
                String note = rs.getNString("note");
                return new TimesheetDTO(timesheetID, date, employeeID, shiftID, checkin, checkout, note);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return null;
    }

    public ArrayList<TimesheetDTO> getTimesheetInRange(int xEmployeeID, LocalDate start, LocalDate end) {
        ArrayList<TimesheetDTO> list = new ArrayList<>();
        query = "SELECT * FROM\n" +
                "	Timesheet\n" +
                "WHERE\n" +
                "	EmployeeID = ? \n" +
                "	AND [Date] Between ? and ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, xEmployeeID);
            ps.setString(2, start.toString());
            ps.setString(3, end.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                int timesheetID = rs.getInt("TimesheetID");
                LocalDate date = DATE_UTIL.parseSqlDate(rs.getDate("Date"));
                int employeeID = rs.getInt("EmployeeID");
                int shiftID = rs.getInt("ShiftID");
                LocalTime checkin = DATE_UTIL.parseSQLTime(rs.getTime("CheckIn"));
                LocalTime checkout = DATE_UTIL.parseSQLTime(rs.getTime("CheckOut"));
                String note = rs.getNString("note");
                list.add(new TimesheetDTO(timesheetID, date, employeeID, shiftID, checkin, checkout, note));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return list;
    }

    public TimesheetDTO getTimesheetByDate(int xEmployeeID, LocalDate xDate) {
        query = "SELECT * FROM\n" +
                "	Timesheet\n" +
                "WHERE\n" +
                "	EmployeeID = ? \n" +
                "	AND [Date]  = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, xEmployeeID);
            ps.setString(2, xDate.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                int timesheetID = rs.getInt("TimesheetID");
                LocalDate date = DATE_UTIL.parseSqlDate(rs.getDate("Date"));
                int employeeID = rs.getInt("EmployeeID");
                int shiftID = rs.getInt("ShiftID");
                LocalTime checkin = DATE_UTIL.parseSQLTime(rs.getTime("CheckIn"));
                LocalTime checkout = DATE_UTIL.parseSQLTime(rs.getTime("CheckOut"));
                String note = rs.getNString("note");
                return new TimesheetDTO(timesheetID, date, employeeID, shiftID, checkin, checkout, note);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return null;
    }

    public static void main(String[] args) {
        TimesheetDAO timesheetDAO = new TimesheetDAO();
        System.out.println("getTimesheetDTO(1)");
        System.out.println(timesheetDAO.getTimesheetDTO(1));
        System.out.println("getTimesheetInRange()");
        LocalDate start = LocalDate.parse("2024-01-05");
        LocalDate end = LocalDate.parse("2024-01-30");
        ArrayList<TimesheetDTO> list = timesheetDAO.getTimesheetInRange(1, start, end);
        for (TimesheetDTO timesheetDTO : list) {
            System.out.println(timesheetDTO);
        }
        System.out.println("getTimesheetByDate: " + timesheetDAO.getTimesheetByDate(1, start));
    }

}
