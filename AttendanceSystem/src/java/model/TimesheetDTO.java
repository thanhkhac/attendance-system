package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimesheetDTO {

    private int timesheetID;
    private LocalDate date;
    private int employeeID;
    private int shiftID;
    private LocalTime checkin;
    private LocalTime checkout;
    private String note;

    public TimesheetDTO(int timesheetID, LocalDate date, int employeeID, int shiftID, LocalTime checkin, LocalTime checkout, String note) {
        this.timesheetID = timesheetID;
        this.date = date;
        this.employeeID = employeeID;
        this.shiftID = shiftID;
        this.checkin = checkin;
        this.checkout = checkout;
        this.note = note;
    }

    public int getTimesheetID() {
        return timesheetID;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public int getShiftID() {
        return shiftID;
    }

    public LocalTime getCheckin() {
        return checkin;
    }

    public LocalTime getCheckout() {
        return checkout;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "TimesheetDTO{" + "timesheetID=" + timesheetID + ", date=" + date + ", employeeID=" + employeeID + ", shiftID=" + shiftID + ", checkin=" + checkin + ", checkout=" + checkout + ", note=" + note + '}';
    }

}
