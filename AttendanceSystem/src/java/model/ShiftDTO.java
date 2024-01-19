package model;

import java.time.LocalTime;

public class ShiftDTO {

    int shiftID;
    String name;
    LocalTime startTime;
    LocalTime endTime;

    public ShiftDTO(int shiftID, String name, LocalTime startTime, LocalTime endTime) {
        this.shiftID = shiftID;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getShiftID() {
        return shiftID;
    }

    public String getName() {
        return name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "ShiftDTO{" + "shiftID=" + shiftID + ", name=" + name + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }
    
    

}
