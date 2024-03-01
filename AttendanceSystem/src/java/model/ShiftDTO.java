package model;

import java.time.LocalTime;

public class ShiftDTO {

    private int shiftID;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime openAt;
    private LocalTime closeAt;
    private boolean isActive;

    public ShiftDTO(int shiftID, String name, LocalTime startTime, LocalTime endTime, LocalTime openAt, LocalTime closeAt, boolean isActive) {
        this.shiftID = shiftID;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.isActive = isActive;
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

    public LocalTime getOpenAt() {
        return openAt;
    }

    public LocalTime getCloseAt() {
        return closeAt;
    }

    public boolean isIsActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "ShiftDTO{" + "shiftID=" + shiftID + ", name=" + name + ", startTime=" + startTime + ", endTime=" + endTime + ", openAt=" + openAt + ", closeAt=" + closeAt + ", isActive=" + isActive + '}';
    }

}
