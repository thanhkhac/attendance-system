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
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return null;
    }

}
