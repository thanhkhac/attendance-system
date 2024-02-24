package model;

import java.time.LocalTime;

public class ShiftDTO {

    private int shiftID;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;
    private LocalTime openAt;
    private LocalTime closeAt;
    private boolean isActive;
    
    public ShiftDTO(int shiftID, String name, LocalTime startTime, LocalTime endTime, LocalTime breakStartTime, LocalTime breakEndTime, LocalTime openAt, LocalTime closeAt) {
        this.shiftID = shiftID;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.openAt = openAt;
        this.closeAt = closeAt;
    }

    public ShiftDTO(int shiftID, String name, LocalTime startTime, LocalTime endTime, LocalTime breakStartTime, LocalTime breakEndTime, LocalTime openAt, LocalTime closeAt, boolean isActive) {
        this.shiftID = shiftID;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.isActive = isActive;
    }
    
    
    
    

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalTime getBreakStartTime() {
        return breakStartTime;
    }

    public void setBreakStartTime(LocalTime breakStartTime) {
        this.breakStartTime = breakStartTime;
    }

    public LocalTime getBreakEndTime() {
        return breakEndTime;
    }

    public void setBreakEndTime(LocalTime breakEndTime) {
        this.breakEndTime = breakEndTime;
    }

    public LocalTime getOpenAt() {
        return openAt;
    }

    public void setOpenAt(LocalTime openAt) {
        this.openAt = openAt;
    }

    public LocalTime getCloseAt() {
        return closeAt;
    }

    public void setCloseAt(LocalTime closeAt) {
        this.closeAt = closeAt;
    }

    public boolean isIsActive() {
        return isActive;
    }
    
    @Override
    public String toString() {
        return "ShiftDTO{" + "shiftID=" + shiftID + ", name=" + name + ", startTime=" + startTime + ", endTime=" + endTime + ", breakStartTime=" + breakStartTime + ", breakEndTime=" + breakEndTime + ", openAt=" + openAt + ", closeAt=" + closeAt + ", isActive=" + isActive + '}';
    }


}
