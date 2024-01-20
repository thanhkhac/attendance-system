package model;


public class LeaveDTO {
    int timesheetDTO;
    String reason;
    Boolean status;
    Integer responedBy;

    public LeaveDTO(int timesheetDTO, String reason, Boolean status, Integer responedBy) {
        this.timesheetDTO = timesheetDTO;
        this.reason = reason;
        this.status = status;
        this.responedBy = responedBy;
    }

    public int getTimesheetDTO() {
        return timesheetDTO;
    }

    public String getReason() {
        return reason;
    }

    public Boolean getStatus() {
        return status;
    }

    public Integer getResponedBy() {
        return responedBy;
    }

    @Override
    public String toString() {
        return "LeaveDTO{" + "timesheetDTO=" + timesheetDTO + ", reason=" + reason + ", status=" + status + ", responedBy=" + responedBy + '}';
    }
    
}
