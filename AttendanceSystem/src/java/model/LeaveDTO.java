package model;

import java.time.LocalDate;


public class LeaveDTO {
    private int leaveID;
    private int employeeID;
    private LocalDate startDate;
    private LocalDate endDate;
    private String filePath;
    private LocalDate createdDate;
    private int createdBy;



    public LeaveDTO(int leaveID, int employeeID, LocalDate startDate, LocalDate endDate, String filePath, LocalDate createdDate, int createdBy) {
        this.leaveID = leaveID;
        this.employeeID = employeeID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.filePath = filePath;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }
    
    

    public int getLeaveID() {
        return leaveID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
    
    

    @Override
    public String toString() {
        return "LeaveDTO{" + "leaveID=" + leaveID + ", employeeID=" + employeeID + ", startDate=" + startDate + ", endDate=" + endDate + ", filePath=" + filePath + ", createdDate=" + createdDate + ", createdBy=" + createdBy + '}';
    }


    
    
    
}
