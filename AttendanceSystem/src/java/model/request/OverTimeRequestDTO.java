/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.request;

import java.sql.Time;
import java.time.LocalDate;

/**
 *
 * @author admin
 */
public class OverTimeRequestDTO {

    private int overTimeRequestID;
    private int employeeID;
    private LocalDate sentDate;
    private LocalDate date;
    private Time startTime;
    private Time endTime;
    private Boolean managerApprove;
    private Boolean hrApprove;
    private int managerID;
    private int hrID;
    private Boolean status;
    private int createdBy;

    public OverTimeRequestDTO() {
    }

    public OverTimeRequestDTO(int overTimeRequestID, int employeeID, LocalDate sentDate, LocalDate date, Time startTime, Time endTime, Boolean managerApprove, Boolean hrApprove, int managerID, int hrID, Boolean status, int createdBy) {
        this.overTimeRequestID = overTimeRequestID;
        this.employeeID = employeeID;
        this.sentDate = sentDate;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.managerApprove = managerApprove;
        this.hrApprove = hrApprove;
        this.managerID = managerID;
        this.hrID = hrID;
        this.status = status;
        this.createdBy = createdBy;
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

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
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
