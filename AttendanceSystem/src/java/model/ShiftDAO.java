package model;

import java.sql.Time;
import java.time.LocalTime;
import ultility.datetimeutil.DateTimeUtil;
import dbhelper.DAOBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ShiftDAO extends DAOBase {

    static final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    public ShiftDTO getShiftDTO(ResultSet rs) throws SQLException {
        int shiftID = rs.getInt("ShiftID");
        String name = rs.getNString("Name");
        LocalTime startTime = DATE_UTIL.parseSQLTime(rs.getTime("StartTime"));
        LocalTime endTime = DATE_UTIL.parseSQLTime(rs.getTime("EndTime"));
        LocalTime openAt = DATE_UTIL.parseSQLTime(rs.getTime("openAt"));
        LocalTime closeAt = DATE_UTIL.parseSQLTime(rs.getTime("closeAt"));
        return new ShiftDTO(shiftID, name, startTime, endTime, openAt, closeAt, true);
    }

    public ShiftDTO getShiftDTO(int xShiftID) {
        connect();
        query = "SELECT * FROM Shifts\n" +
                "WHERE ShiftID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, xShiftID);
            rs = ps.executeQuery();
            while (rs.next()) {
                return getShiftDTO(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return null;
    }

    public ArrayList<ShiftDTO> getAllShiftDTO() {
        connect();
        ArrayList<ShiftDTO> list = new ArrayList<ShiftDTO>();
        query = "SELECT * FROM Shifts\n";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getShiftDTO(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public HashMap<Integer, ShiftDTO> getAllShiftHashMap() {
        connect();
        HashMap<Integer, ShiftDTO> map = new HashMap<Integer, ShiftDTO>();
        query = "SELECT * FROM Shifts\n";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                ShiftDTO shift = getShiftDTO(rs);
                map.put(shift.getShiftID(), shift);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return map;
    }

    public ArrayList<ShiftDTO> getActiveShiftDTO() {
        connect();
        ArrayList<ShiftDTO> list = new ArrayList<ShiftDTO>();
        query = "SELECT * FROM Shifts " +
                "WHERE IsActive = 1 \n";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getShiftDTO(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public static void main(String[] args) {
        ShiftDAO shiftDAO = new ShiftDAO();
        ArrayList<ShiftDTO> shifts = new ShiftDAO().getAllShiftDTO();
        System.out.println("getTimesheetDTO: " + shiftDAO.getShiftDTO(3));
        System.out.println(shiftDAO.getAllShiftDTO().size());
        System.out.println(shiftDAO.getActiveShiftDTO().size());
        HashMap<Integer, ShiftDTO> shiftMap = new ShiftDAO().getAllShiftHashMap();
    }
}
