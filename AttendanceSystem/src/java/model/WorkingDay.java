package model;

import java.time.LocalDate;

public class WorkingDay {

    public int employeeID;
    public LocalDate date;
    public TimesheetDTO timesheet;
    public LeaveDTO leave;
    public OvertimeDTO overtime;
    public ShiftDTO shift;

    public WorkingDay(LocalDate date, int employeeID) {
        this.date = date;
        this.employeeID = employeeID;
        timesheet = new TimesheetDAO().getTimesheetByDate(employeeID, date);
        leave = (timesheet == null) ? null : new LeaveDAO().getLeaveDTO(timesheet.getTimesheetID());
        overtime = new OvertimeDAO().getOverTimeDTO(date, employeeID);
        shift = (timesheet == null) ? null : new ShiftDAO().getShiftDTO(timesheet.getShiftID());
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TimesheetDTO getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(TimesheetDTO timesheet) {
        this.timesheet = timesheet;
    }

    public LeaveDTO getLeave() {
        return leave;
    }

    public void setLeave(LeaveDTO leave) {
        this.leave = leave;
    }

    public OvertimeDTO getOvertime() {
        return overtime;
    }

    public void setOvertime(OvertimeDTO overtime) {
        this.overtime = overtime;
    }

    public ShiftDTO getShift() {
        return shift;
    }

    @Override
    public String toString() {
        return "WorkingDay{" + "employeeID=" + employeeID + ", date=" + date + ", timesheet=" + timesheet + ", leave=" + leave + ", overtime=" + overtime + ", shift=" + shift + '}';
    }

}
