package model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import dbhelper.DAOBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import ultility.datetimeutil.DateTimeUtil;

public class TimesheetDAO extends DAOBase {

    private final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    public TimesheetDTO getTimesheetDTO(ResultSet rs) throws SQLException {
        int timesheetID = rs.getInt("TimesheetID");
        LocalDate date = DATE_UTIL.parseSqlDate(rs.getDate("Date"));
        int employeeID = rs.getInt("EmployeeID");
        int shiftID = rs.getInt("ShiftID");
        LocalTime checkin = DATE_UTIL.parseSQLTime(rs.getTime("CheckIn"));
        LocalTime checkout = DATE_UTIL.parseSQLTime(rs.getTime("CheckOut"));
        int createdBy = rs.getInt("createdBy");
        return new TimesheetDTO(timesheetID, date, employeeID, shiftID, checkin, checkout, createdBy);
    }

    public TimesheetDTO getTimesheetDTO(int sTimesheetID) {

        query = "SELECT * FROM Timesheet \n" +
                "WHERE \n" +
                "TimesheetID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, sTimesheetID);
            rs = ps.executeQuery();
            while (rs.next()) {
                return getTimesheetDTO(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return null;
    }

    public ArrayList<TimesheetDTO> getTimesheetInRange(int xEmployeeID, LocalDate start, LocalDate end) {
                connect();

        ArrayList<TimesheetDTO> list = new ArrayList<>();

        query = "SELECT * FROM\n" +
                "Timesheet\n" +
                "WHERE \n" +
                "EmployeeID = ? \n" +
                "AND [Date] Between ? and ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, xEmployeeID);
            ps.setString(2, start.toString());
            ps.setString(3, end.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getTimesheetDTO(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public TimesheetDTO getTimesheetByDate(int xEmployeeID, LocalDate xDate) {
        connect();
        query = "SELECT * FROM\n" +
                "Timesheet\n" +
                "WHERE\n" +
                "EmployeeID = ? \n" +
                "AND [Date]  = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, xEmployeeID);
            ps.setString(2, xDate.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                return getTimesheetDTO(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return null;
    }

    public boolean insertTimesheet(String[] rawShifts, String[] rawEmployeeIDs, int createdBy) {
        String query = "INSERT INTO Timesheet ([Date], EmployeeID, ShiftID, CreatedBy) VALUES (?, ?, ?, ?)";

        try {
            super.connect();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query);
            for (String rawShift : rawShifts) {
                String[] shiftInfo = rawShift.split("#");
                int shiftID = Integer.parseInt(shiftInfo[1]);
                String date = shiftInfo[0];

                for (String rawEmployeeID : rawEmployeeIDs) {
                    int employeeID = Integer.parseInt(rawEmployeeID);
                    ps.setString(1, date);
                    ps.setInt(2, employeeID);
                    ps.setInt(3, shiftID);
                    ps.setInt(4, createdBy);
                    ps.addBatch();
                }
            }

            int[] result = ps.executeBatch();
            connection.commit();

            for (int res : result) {
                if (res == Statement.EXECUTE_FAILED) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            super.closeAll();
        }
    }

    public boolean deleteTimesheets(String[] rawEmployeeIDs, int month, int year) {
        String query = "DELETE FROM Timesheet WHERE MONTH(Date) = ? AND YEAR(Date) = ? AND Date > GETDATE() AND EmployeeID = ?";

        try {
            super.connect();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(query);

            for (String rawEmployeeID : rawEmployeeIDs) {
                int employeeID = Integer.parseInt(rawEmployeeID);
                ps.setInt(1, month);
                ps.setInt(2, year);
                ps.setInt(3, employeeID);
                ps.addBatch();
            }

            int[] result = ps.executeBatch();
            connection.commit();

            for (int res : result) {
                if (res == Statement.EXECUTE_FAILED) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            super.closeAll();
        }
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
        for (int i = 0; i < 100000; i++) {
            timesheetDAO.getTimesheetInRange(1, start, end);
        }
    }

}
