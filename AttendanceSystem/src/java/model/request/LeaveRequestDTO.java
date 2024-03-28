/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.request;

import java.time.LocalDate;


public class LeaveRequestDTO {

    private int leaveRequestID;
    private int employeeID;
    private int departmentID;
    private LocalDate sentDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String filePath;
    private String reason;
    private Boolean managerApprove;
    private Boolean hrApprove;
    private int managerID;
    private int hrID;
    private int createdBy;
    private Boolean status;

    public LeaveRequestDTO() {
    }

    public LeaveRequestDTO(int leaveRequestID, int employeeID, LocalDate sentDate, LocalDate startDate, LocalDate endDate, String filePath, String reason, Boolean managerApprove, Boolean hrApprove, int managerID, int hrID, int createdBy, Boolean status) {
        this.leaveRequestID = leaveRequestID;
        this.employeeID = employeeID;
        this.sentDate = sentDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.filePath = filePath;
        this.reason = reason;
        this.managerApprove = managerApprove;
        this.hrApprove = hrApprove;
        this.managerID = managerID;
        this.hrID = hrID;
        this.createdBy = createdBy;
        this.status = status;
    }

    public LeaveRequestDTO(int leaveRequestID, int employeeID, int departmentID, LocalDate sentDate, LocalDate startDate, LocalDate endDate, String filePath, String reason, Boolean managerApprove, Boolean hrApprove, int managerID, int hrID, Boolean status) {
        this.leaveRequestID = leaveRequestID;
        this.employeeID = employeeID;
        this.departmentID = departmentID;
        this.sentDate = sentDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.filePath = filePath;
        this.reason = reason;
        this.managerApprove = managerApprove;
        this.hrApprove = hrApprove;
        this.managerID = managerID;
        this.hrID = hrID;
        this.status = status;
    }

    public LeaveRequestDTO(int leaveRequestID, int employeeID, LocalDate sentDate, LocalDate startDate, LocalDate endDate, String filePath, String reason, Boolean managerApprove, Boolean hrApprove, int managerID, int hrID, Boolean status) {
        this.leaveRequestID = leaveRequestID;
        this.employeeID = employeeID;
        this.sentDate = sentDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.filePath = filePath;
        this.reason = reason;
        this.managerApprove = managerApprove;
        this.hrApprove = hrApprove;
        this.managerID = managerID;
        this.hrID = hrID;
        this.status = status;
    }

    public LeaveRequestDTO(int leaveRequestID, int employeeID, LocalDate sentDate, LocalDate startDate, LocalDate endDate, String reason, Boolean managerApprove, Boolean hrApprove, int managerID, int hrID, Boolean status) {
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
        this.status = status;
    }

    public int getLeaveRequestID() {
        return leaveRequestID;
    }

    public void setLeaveRequestID(int leaveRequestID) {
        this.leaveRequestID = leaveRequestID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getManagerApprove() {
        return managerApprove;
    }

    public void setManagerApprove(Boolean managerApprove) {
        this.managerApprove = managerApprove;
    }

    public Boolean getHrApprove() {
        return hrApprove;
    }

    public void setHrApprove(Boolean hrApprove) {
        this.hrApprove = hrApprove;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public int getHrID() {
        return hrID;
    }

    public void setHrID(int hrID) {
        this.hrID = hrID;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getStatus() {
        return status;
    }

//    public void setStatus(Boolean status) {
//        this.status = status;
//    }
    @Override
    public String toString() {
        return "LeaveRequestDTO{" + "leaveRequestID=" + leaveRequestID + ", employeeID=" + employeeID + ", sentDate=" + sentDate + ", startDate=" + startDate + ", endDate=" + endDate + ", reason=" + reason + ", managerApprove=" + managerApprove + ", hrApprove=" + hrApprove + ", managerID=" + managerID + ", hrID=" + hrID + ", status=" + status + '}';
    }

}
