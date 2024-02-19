/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author nguye
 */
public class LeaveRequestDTO {
    private int leaveRequestID;
    private int employeeID;
    private LocalDate sentDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private Boolean managerApprove;
    private Boolean hrApprove;
    private int managerID;
    private int hrID;

    public LeaveRequestDTO() {
    }

    public LeaveRequestDTO(int leaveRequestID, int employeeID, LocalDate sentDate, LocalDate startDate, LocalDate endDate, String reason, Boolean managerApprove, Boolean hrApprove, int managerID, int hrID) {
        this.leaveRequestID = leaveRequestID;
        this.employeeID = employeeID;
        this.sentDate = sentDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.managerApprove = managerApprove;
        this.hrApprove = hrApprove;
        this.managerID = managerID;
        this.hrID = hrID;
    }

    public int getLeaveRequestID() {
        return leaveRequestID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }

    public Boolean getManagerApprove() {
        return managerApprove;
    }

    public Boolean getHrApprove() {
        return hrApprove;
    }

    public int getManagerID() {
        return managerID;
    }

    public int getHrID() {
        return hrID;
    }

    
    @Override
    public String toString() {
        return "LeaveRequestDTO{" + "leaveRequestID=" + leaveRequestID + ", employeeID=" + employeeID + ", sentDate=" + sentDate + ", startDate=" + startDate + 
                ", endDate=" + endDate + ", reason=" + reason + ", managerApprove=" + managerApprove + ", hrApprove=" + hrApprove + ", managerID=" + managerID + ", hrID=" + hrID;
    }
    
    
    
}
