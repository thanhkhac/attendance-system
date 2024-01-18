package model;

import java.sql.Time;


public class ShiftDTO {
    int shiftID;
    String name;
    Time startTime;
    Time endTime;

    public ShiftDTO(int shiftID, String name, Time StartTime, Time EndTime) {
        this.shiftID = shiftID;
        this.name = name;
        this.startTime = StartTime;
        this.endTime = EndTime;
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

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time StartTime) {
        this.startTime = StartTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time EndTime) {
        this.endTime = EndTime;
    }

    @Override
    public String toString() {
        return "ShiftDTO{" + "shiftID=" + shiftID + ", name=" + name + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }
    
    
    
}
