package model;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import utils.DAOBase;

public class TimesheetDAO extends DAOBase {

    public TimesheetDTO getTimesheetDTO(int sTimesheetID) {
        query = "SELECT * FROM Timesheet \n" +
                "WHERE \n" +
                "	TimesheetID = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, sTimesheetID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int timesheetID = rs.getInt("TimeSheetID");
                Date date = rs.getDate("Date");
                int employeeID = rs.getInt("EmployeeID");
                int shiftID = rs.getInt("ShiftID");
                Time checkIn = rs.getTime("CheckIn");
                Time checkOut = rs.getTime("CheckOut");
                String note = rs.getNString("Note");
                return new TimesheetDTO(timesheetID, date, employeeID, shiftID, checkIn, checkOut, note);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return null;
    }

    public ArrayList<TimesheetDTO> getTimesheetByMonth(int sEmployeeID, int month, int year) {
        query = "SELECT * FROM Timesheet\n" +
                "WHERE\n" +
                "	EmployeeID = ? AND MONTH([Date]) = ? AND YEAR([Date]) = ?\n" +
                "ORDER BY [Date]";
        ArrayList<TimesheetDTO> al = new ArrayList<>();
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, sEmployeeID);
            ps.setInt(2, month);
            ps.setInt(3, year);
            rs = ps.executeQuery();
            while (rs.next()) {
                int timesheetID = rs.getInt("TimeSheetID");
                Date date = rs.getDate("Date");
                int employeeID = rs.getInt("EmployeeID");
                int shiftID = rs.getInt("ShiftID");
                Time checkIn = rs.getTime("CheckIn");
                Time checkOut = rs.getTime("CheckOut");
                String note = rs.getNString("Note");
                al.add(new TimesheetDTO(timesheetID, date, employeeID, shiftID, checkIn, checkOut, note));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return al;
    }

    public static void main(String[] args) {
        TimesheetDAO DAO = new TimesheetDAO();
        System.out.println("getTimesheetDTO: ");
        System.out.println(DAO.getTimesheetDTO(2));
        System.out.println("getTimesheetByMonth");
        ArrayList<TimesheetDTO> al = DAO.getTimesheetByMonth(1, 1, 2024);
        for (TimesheetDTO timesheetDTO : al) {
            System.out.println(timesheetDTO);
        }
    }
}
