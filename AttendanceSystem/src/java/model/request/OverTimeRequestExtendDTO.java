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
 * @author nguye
 */
public class OverTimeRequestExtendDTO extends OverTimeRequestDTO{
    private int departmentID;
    private String conflict;

    public OverTimeRequestExtendDTO() {
    }

    public OverTimeRequestExtendDTO(String conflict) {
        this.conflict = conflict;
    }

    public OverTimeRequestExtendDTO(int overTimeRequestID, LocalDateTime date, int employeeID, LocalDateTime sentDate, LocalTime startTime, LocalTime endTime, Boolean managerApprove, Boolean hrApprove, int managerID, int hrID, int createdBy, Boolean status, int departmentID, String conflict) {
        super(overTimeRequestID, date, employeeID, sentDate, startTime, endTime, managerApprove, hrApprove, managerID, hrID, createdBy, status);
        this.departmentID = departmentID;
        this.conflict = conflict;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getConflict() {
        return conflict;
    }

    public void setConflict(String conflict) {
        this.conflict = conflict;
    }
    
}
