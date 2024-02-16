package model;

import java.time.LocalDate;
import java.time.LocalTime;


public class OvertimeDTO {
    LocalDate date;
    int employeeID;
    LocalTime startTime;
    LocalTime endTime;
    LocalTime openAt;
    LocalTime closeAt;
    LocalTime checkIn;
    LocalTime checkOut;
    int createdBy;

    public OvertimeDTO(LocalDate date, int employeeID, LocalTime startTime, LocalTime endTime, LocalTime openAt, LocalTime closeAt, LocalTime checkIn, LocalTime checkOut, int createdBy) {
        this.date = date;
        this.employeeID = employeeID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "OvertimeDTO{" + "date=" + date + ", employeeID=" + employeeID + ", startTime=" + startTime + ", endTime=" + endTime + ", openAt=" + openAt + ", closeAt=" + closeAt + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", createdBy=" + createdBy + '}';
    }

    public LocalDate getDate() {
        return date;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalTime getOpenAt() {
        return openAt;
    }

    public LocalTime getCloseAt() {
        return closeAt;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    
    
    
    
    
}