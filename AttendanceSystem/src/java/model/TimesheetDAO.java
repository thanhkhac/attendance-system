package model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import dbhelper.DAOBase;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                int createdBy = rs.getInt("createdBy");
                return new TimesheetDTO(timesheetID, date, employeeID, shiftID, checkin, checkout, createdBy);
            }
        } catch (SQLException e) {
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
                int createdBy = rs.getInt("createdBy");
                list.add(new TimesheetDTO(timesheetID, date, employeeID, shiftID, checkin, checkout, createdBy));
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
                int createdBy = rs.getInt("createdBy");
                return new TimesheetDTO(timesheetID, date, employeeID, shiftID, checkin, checkout, createdBy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return null;
    }

    public boolean insertTimesheet(String[] rawShifts, String[] rawEmployeeIDs, int createdBy) {
        String query = "INSERT INTO Timesheet ([Date], EmployeeID, ShiftID, CreatedBy) VALUES (?, ?, ?, ?)";
        try {
            super.connect();
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);

            for (int i = 0; i < rawShifts.length; i++) {
                String[] shiftInfo = rawShifts[i].split("#");
                int shiftID = Integer.parseInt(shiftInfo[1]);
                String date = shiftInfo[0];

                for (int j = 0; j < rawEmployeeIDs.length; j++) {
                    int employeeID = Integer.parseInt(rawEmployeeIDs[j]);
                    ps.setString(1, date);
                    ps.setInt(2, employeeID);
                    ps.setInt(3, shiftID);
                    ps.setInt(4, createdBy);
                    ps.addBatch();
                }
            }
            try {
                ps.executeBatch();
                con.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(TimesheetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeAll();
        }
        return true;
    }

    public boolean deleteTimesheets(String[] rawEmployeeIDs,  int month, int year) {
        String query = "" +
                "DELETE FROM Timesheet\n" +
                "WHERE \n" +
                "	MONTH(Date) = ?\n" +
                "	AND YEAR(Date) = ?\n" +
                "	AND Date > GETDATE()\n" +
                "	AND EmployeeID = ? ";
        try {
            super.connect();
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setInt(1, month);
            ps.setInt(2, year);
            for (int j = 0; j < rawEmployeeIDs.length; j++) {
                int employeeID = Integer.parseInt(rawEmployeeIDs[j]);
                ps.setInt(3, employeeID);
                ps.addBatch();
            }

            try {
                ps.executeBatch();
                con.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(TimesheetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeAll();
        }
        return true;
    }

    public static void main(String[] args) {
        TimesheetDAO timesheetDAO = new TimesheetDAO();
        System.out.println("getTimesheetDTO(1)");
        System.out.println(timesheetDAO.getTimesheetDTO(1));
        System.out.println("getTimesheetInRange()");
        LocalDate start = LocalDate.parse("2024-02-05");
        LocalDate end = LocalDate.parse("2024-02-27");
        ArrayList<TimesheetDTO> list = timesheetDAO.getTimesheetInRange(1, start, end);
        for (TimesheetDTO timesheetDTO : list) {
            System.out.println(timesheetDTO);
        }
        System.out.println("getTimesheetByDate: " + timesheetDAO.getTimesheetByDate(1, start));
    }

}
