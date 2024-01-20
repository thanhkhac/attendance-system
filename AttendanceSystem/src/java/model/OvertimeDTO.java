package model;

import java.time.LocalDate;
import java.time.LocalTime;


public class OvertimeDTO {
    LocalDate date;
    int employeeID;
    LocalTime startTime;
    LocalTime endTime;
    LocalTime checkIn;
    LocalTime checkOut;
    int openBefore;
    int closeAfter;

    public OvertimeDTO(LocalDate date, int employeeID, LocalTime startTime, LocalTime endTime, LocalTime checkIn, LocalTime checkOut, int openBefore, int closeAfter) {
        this.date = date;
        this.employeeID = employeeID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.openBefore = openBefore;
        this.closeAfter = closeAfter;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
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

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }

    public int getOpenBefore() {
        return openBefore;
    }

    public void setOpenBefore(int openBefore) {
        this.openBefore = openBefore;
    }

    public int getCloseAfter() {
        return closeAfter;
    }

    public void setCloseAfter(int closeAfter) {
        this.closeAfter = closeAfter;
    }

    @Override
    public String toString() {
        return "OvertimeDTO{" + "date=" + date + ", employeeID=" + employeeID + ", startTime=" + startTime + ", endTime=" + endTime + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", openBefore=" + openBefore + ", closeAfter=" + closeAfter + '}';
    }

    
    
    
    
}