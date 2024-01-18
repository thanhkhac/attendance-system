package model;

import java.sql.Date;
import java.sql.Time;


public class TimesheetDTO {
    private int timesheetID;
    private Date date;
    private int employeeID;
    private int shiftID;
    private Time checkIn;
    private Time checkOut;
    private String note;

    public TimesheetDTO(int timesheetID, Date date, int employeeID, int shiftID, Time CheckIn, Time CheckOut, String note) {
        this.timesheetID = timesheetID;
        this.date = date;
        this.employeeID = employeeID;
        this.shiftID = shiftID;
        this.checkIn = CheckIn;
        this.checkOut = CheckOut;
        this.note = note;
    }

    public int getTimesheetID() {
        return timesheetID;
    }

    public Date getDate() {
        return date;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public int getShiftID() {
        return shiftID;
    }

    public Time getCheckIn() {
        return checkIn;
    }

    public Time getCheckOut() {
        return checkOut;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "TimesheetDTO{" + "timesheetID=" + timesheetID + ", date=" + date + ", employeeID=" + employeeID + ", shiftID=" + shiftID + ", CheckIn=" + checkIn + ", CheckOut=" + checkOut + ", note=" + note + '}';
    }
    
}
