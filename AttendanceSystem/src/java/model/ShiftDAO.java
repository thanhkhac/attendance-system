package model;

import java.sql.Time;
import java.time.LocalTime;
import ultility.dateutil.DateTimeUtil;
import utils.DAOBase;

public class ShiftDAO extends DAOBase {

    static final DateTimeUtil DATE_UTIL = new DateTimeUtil();

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
                LocalTime startTime = DATE_UTIL.parseSQLTime(rs.getTime("StartTime"));
                LocalTime endTime = DATE_UTIL.parseSQLTime(rs.getTime("StartTime"));
                return new ShiftDTO(shiftID, name, startTime, endTime);
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
