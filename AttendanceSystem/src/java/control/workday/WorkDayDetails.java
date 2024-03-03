package control.workday;

import java.time.LocalDate;
import java.util.ArrayList;
import model.EmployeeDAO;
import model.EmployeeDTO;
import model.LeaveDAO;
import model.LeaveDTO;
import model.OvertimeDAO;
import model.OvertimeDTO;
import model.ShiftDAO;
import model.ShiftDTO;
import model.TimesheetDAO;
import model.TimesheetDTO;

public class WorkDayDetails {

    public int employeeID;
    public LocalDate date;
    public ArrayList<TimesheetDTO> timesheet;
    public LeaveDTO leave;
    public OvertimeDTO overtime;
    public ShiftDTO shift;

    public WorkDayDetails(LocalDate date, int employeeID) {
        this.date = date;
        this.employeeID = employeeID;
        timesheet = new TimesheetDAO().getTimesheetByDate(employeeID, date);
        leave = new LeaveDAO().getLeaveDTO(employeeID, date.toString());
        overtime = new OvertimeDAO().getOverTimeDTO(date, employeeID);
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

    public ArrayList<TimesheetDTO> getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(ArrayList<TimesheetDTO> timesheet) {
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

    public void setShift(ShiftDTO shift) {
        this.shift = shift;
    }

    @Override
    public String toString() {
        return "WorkDayDetails{" + "employeeID=" + employeeID + ", date=" + date + ", timesheet=" + timesheet + ", leave=" + leave + ", overtime=" + overtime + ", shift=" + shift + '}';
    }
    
    

}
