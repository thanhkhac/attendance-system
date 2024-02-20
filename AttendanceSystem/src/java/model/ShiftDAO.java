package model;

import java.sql.Time;
import java.time.LocalTime;
import ultility.datetimeutil.DateTimeUtil;
import dbhelper.DAOBase;
import java.util.ArrayList;

public class ShiftDAO extends DAOBase {

    static final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    public ShiftDTO getShiftDTO(int xShiftID) {
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
                LocalTime endTime = DATE_UTIL.parseSQLTime(rs.getTime("EndTime"));
                LocalTime breakStartTime = DATE_UTIL.parseSQLTime(rs.getTime("breakStartTime"));
                LocalTime breakEndTime = DATE_UTIL.parseSQLTime(rs.getTime("breakEndTime"));
                LocalTime openAt = DATE_UTIL.parseSQLTime(rs.getTime("openAt"));
                LocalTime closeAt = DATE_UTIL.parseSQLTime(rs.getTime("closeAt"));
                return new ShiftDTO(shiftID, name, startTime, endTime, breakStartTime, breakEndTime, openAt, closeAt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return null;
    }

    public ArrayList<ShiftDTO> getAllShiftDTO() {
        ArrayList<ShiftDTO> list = new ArrayList<ShiftDTO>();
        query = "SELECT * FROM Shifts\n";
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                int shiftID = rs.getInt("ShiftID");
                String name = rs.getNString("Name");
                LocalTime startTime = DATE_UTIL.parseSQLTime(rs.getTime("StartTime"));
                LocalTime endTime = DATE_UTIL.parseSQLTime(rs.getTime("EndTime"));
                LocalTime breakStartTime = DATE_UTIL.parseSQLTime(rs.getTime("breakStartTime"));
                LocalTime breakEndTime = DATE_UTIL.parseSQLTime(rs.getTime("breakEndTime"));
                LocalTime openAt = DATE_UTIL.parseSQLTime(rs.getTime("openAt"));
                LocalTime closeAt = DATE_UTIL.parseSQLTime(rs.getTime("closeAt"));
                list.add(new ShiftDTO(shiftID, name, startTime, endTime, breakStartTime, breakEndTime, openAt, closeAt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return list;
    }

    public ArrayList<ShiftDTO> getActiveShiftDTO() {
        ArrayList<ShiftDTO> list = new ArrayList<ShiftDTO>();
        query = "SELECT * FROM Shifts " +
                "WHERE IsActive = 1 \n";
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                int shiftID = rs.getInt("ShiftID");
                String name = rs.getNString("Name");
                LocalTime startTime = DATE_UTIL.parseSQLTime(rs.getTime("StartTime"));
                LocalTime endTime = DATE_UTIL.parseSQLTime(rs.getTime("EndTime"));
                LocalTime breakStartTime = DATE_UTIL.parseSQLTime(rs.getTime("breakStartTime"));
                LocalTime breakEndTime = DATE_UTIL.parseSQLTime(rs.getTime("breakEndTime"));
                LocalTime openAt = DATE_UTIL.parseSQLTime(rs.getTime("openAt"));
                LocalTime closeAt = DATE_UTIL.parseSQLTime(rs.getTime("closeAt"));
                list.add(new ShiftDTO(shiftID, name, startTime, endTime, breakStartTime, breakEndTime, openAt, closeAt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return list;
    }

    public static void main(String[] args) {
        ShiftDAO shiftDAO = new ShiftDAO();
        System.out.println("getTimesheetDTO: " + shiftDAO.getShiftDTO(3));
        System.out.println(shiftDAO.getAllShiftDTO().size());
        System.out.println(shiftDAO.getActiveShiftDTO().size());
    }
}
