/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.request;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author admin
 */
public class OverTimeRequestDTO {

    private int overTimeRequestID;
    private LocalDateTime date;
    private int employeeID;
    private LocalDateTime sentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean managerApprove;
    private Boolean hrApprove;
    private int managerID;
    private int hrID;
    private int createdBy;
    private Boolean status;

    public OverTimeRequestDTO() {
    }

    public OverTimeRequestDTO(int overTimeRequestID, LocalDateTime date, int employeeID, LocalDateTime sentDate, LocalTime startTime, LocalTime endTime, Boolean managerApprove, Boolean hrApprove, int managerID, int hrID, int createdBy, Boolean status) {
        this.overTimeRequestID = overTimeRequestID;
        this.date = date;
        this.employeeID = employeeID;
        this.sentDate = sentDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.managerApprove = managerApprove;
        this.hrApprove = hrApprove;
        this.managerID = managerID;
        this.hrID = hrID;
        this.createdBy = createdBy;
        this.status = status;
    }

    public int getOverTimeRequestID() {
        return overTimeRequestID;
    }

    public void setOverTimeRequestID(int overTimeRequestID) {
        this.overTimeRequestID = overTimeRequestID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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

    public Boolean getStatus() {
        return status;
    }

//    public void setStatus(Boolean status) {
//        this.status = status;
//    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    
    
    
}
