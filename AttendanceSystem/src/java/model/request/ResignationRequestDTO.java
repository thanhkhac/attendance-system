/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.request;

import java.time.LocalDate;

/**
 *
 * @author admin
 */
public class ResignationRequestDTO {

    private int resignationRequestID;
    private int employeeID;
    private LocalDate sentDate;
    private LocalDate startDateContract;
    private LocalDate endDateContract;
    private LocalDate extendDate;
    private String reason;
    private Boolean managerApprove;
    private Boolean hrApprove;
    private int managerID;
    private int hrID;
    private Boolean status;

    public ResignationRequestDTO() {
    }

    public ResignationRequestDTO(int resignationRequestID, int employeeID, LocalDate sentDate, LocalDate startDateContract, LocalDate endDateContract, LocalDate extendDate, String reason, Boolean managerApprove, Boolean hrApprove, int managerID, int hrID, Boolean status) {
        this.resignationRequestID = resignationRequestID;
        this.employeeID = employeeID;
        this.sentDate = sentDate;
        this.startDateContract = startDateContract;
        this.endDateContract = endDateContract;
        this.extendDate = extendDate;
        this.reason = reason;
        this.managerApprove = managerApprove;
        this.hrApprove = hrApprove;
        this.managerID = managerID;
        this.hrID = hrID;
        this.status = status;
    }

    public int getResignationRequestID() {
        return resignationRequestID;
    }

    public void setResignationRequestID(int resignationRequestID) {
        this.resignationRequestID = resignationRequestID;
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

    public LocalDate getStartDateContract() {
        return startDateContract;
    }

    public void setStartDateContract(LocalDate startDateContract) {
        this.startDateContract = startDateContract;
    }

    public LocalDate getEndDateContract() {
        return endDateContract;
    }

    public void setEndDateContract(LocalDate endDateContract) {
        this.endDateContract = endDateContract;
    }

    public LocalDate getExtendDate() {
        return extendDate;
    }

    public void setExtendDate(LocalDate extendDate) {
        this.extendDate = extendDate;
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

    public Boolean getStatus() {
        return status;
    }

//    public void setStatus(Boolean status) {
//        this.status = status;
//    }

    
}
