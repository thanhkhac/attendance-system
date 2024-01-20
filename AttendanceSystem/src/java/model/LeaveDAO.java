package model;

import dbhelper.DAOBase;
import java.time.LocalDate;
import java.time.LocalTime;

public class LeaveDAO extends DAOBase {

    public LeaveDTO getLeaveDTO(int sTimesheetID) {
        query = "SELECT * FROM Leaves \n" +
                "WHERE \n" +
                "	TimesheetID = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, sTimesheetID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int timesheetID = rs.getInt("TimesheetID");
                String reason = rs.getNString("reason");
                Boolean status = (rs.getString("status") == null) ? null : rs.getBoolean("status");
                Integer responedBy = (rs.getString("ResponedBy") == null) ? null : rs.getInt("ResponedBy");
                return new LeaveDTO(timesheetID, reason, status, responedBy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return null;
    }

    public LeaveDTO getApprovedLeaveDTO(int sTimesheetID) {
        query = "SELECT * FROM Leaves \n" +
                "WHERE \n" +
                "	TimesheetID = ? AND Status = 1";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, sTimesheetID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int timesheetID = rs.getInt("TimesheetID");
                String reason = rs.getNString("reason");
                Boolean status = (rs.getString("status") == null) ? null : rs.getBoolean("status");
                Integer responedBy = (rs.getString("ResponedBy") == null) ? null : rs.getInt("ResponedBy");
                return new LeaveDTO(timesheetID, reason, status, responedBy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return null;
    }

    public static void main(String[] args) {
        LeaveDAO leaveDAO = new LeaveDAO();
        System.out.println(leaveDAO.getLeaveDTO(1));
        System.out.println(leaveDAO.getApprovedLeaveDTO(1));
        LocalDate date = LocalDate.parse("2024-01-02");
    }
}
