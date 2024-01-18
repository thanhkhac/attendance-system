package model;

import java.sql.Date;
import java.sql.Time;
import utils.DAOBase;

public class ShiftDAO extends DAOBase {

    public ShiftDTO getTimesheetDTO(int xShiftID) {
        query = "SELECT * FROM Shifts\n" +
                "WHERE ShiftID = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, xShiftID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int shiftID = rs.getInt("ShiftID");
                String name = rs.getNString("Name");
                Time StartTime = rs.getTime("StartTime");
                Time EndTime = rs.getTime("EndTime");;
                return new ShiftDTO(shiftID, name, StartTime, EndTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return null;
    }
    
    public static void main(String[] args) {
        ShiftDAO shiftDAO = new ShiftDAO();
        System.out.println("getTimesheetDTO: " + shiftDAO.getTimesheetDTO(1));
    }
}
