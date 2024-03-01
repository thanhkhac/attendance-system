/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author admin
 */
public class StatisticsDTO {

    private int employeeID;
    private LocalDate date;
    private String shiftName;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private Duration shiftHours;
    private LocalTime otStartTime;
    private LocalTime otEndTime;
    private LocalTime otCheckIn;
    private LocalTime otCheckOut;
    private Duration otHours;
    private Duration totalDay;

    public StatisticsDTO() {
    }

    public StatisticsDTO(int employeeID, LocalDate date, String shiftName, LocalTime startTime, LocalTime endTime, LocalTime checkIn, LocalTime checkOut, Duration shiftHours, LocalTime otStartTime, LocalTime otEndTime, LocalTime otCheckIn, LocalTime otCheckOut, Duration otHours, Duration totalDay) {
        this.employeeID = employeeID;
        this.date = date;
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.shiftHours = shiftHours;
        this.otStartTime = otStartTime;
        this.otEndTime = otEndTime;
        this.otCheckIn = otCheckIn;
        this.otCheckOut = otCheckOut;
        this.otHours = otHours;
        this.totalDay = totalDay;
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

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
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

    public Duration getShiftHours() {
        return shiftHours;
    }

    public void setShiftHours(Duration shiftHours) {
        this.shiftHours = shiftHours;
    }

    public LocalTime getOtStartTime() {
        return otStartTime;
    }

    public void setOtStartTime(LocalTime otStartTime) {
        this.otStartTime = otStartTime;
    }

    public LocalTime getOtEndTime() {
        return otEndTime;
    }

    public void setOtEndTime(LocalTime otEndTime) {
        this.otEndTime = otEndTime;
    }

    public LocalTime getOtCheckIn() {
        return otCheckIn;
    }

    public void setOtCheckIn(LocalTime otCheckIn) {
        this.otCheckIn = otCheckIn;
    }

    public LocalTime getOtCheckOut() {
        return otCheckOut;
    }

    public void setOtCheckOut(LocalTime otCheckOut) {
        this.otCheckOut = otCheckOut;
    }

    public Duration getOtHours() {
        return otHours;
    }

    public void setOtHours(Duration otHours) {
        this.otHours = otHours;
    }

    public Duration getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Duration totalDay) {
        this.totalDay = totalDay;
    }

}
