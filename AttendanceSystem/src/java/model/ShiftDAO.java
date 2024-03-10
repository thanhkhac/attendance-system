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
                "WHERE " +
                "   IsActive = 1 \n" +
                "ORDER BY StartTime ";
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

    public int insertShift(ShiftDTO shift) {
        connect();
        query = "INSERT INTO Shifts (Name, StartTime, EndTime, OpenAt, CloseAt) VALUES\n" +
                "(?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setNString(1, shift.getName());
            ps.setString(2, shift.getStartTime().toString());
            ps.setString(3, shift.getEndTime().toString());
            ps.setString(4, shift.getOpenAt().toString());
            ps.setString(5, shift.getCloseAt().toString());

            int result = ps.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return 0;
    }

    public int deleteShift(int ID) {
        connect();
        query = "	UPDATE Shifts\n" +
                "	SET IsActive = 0" +
                "	WHERE ShiftID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, ID);
            int result = ps.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return 0;
    }

    public int checkConflict(ShiftDTO shift) {
        connect();
        query = "DECLARE @StartTime Time = ?\n" +
                "DECLARE @EndTIme Time = ?\n" +
                "\n" +
                "SELECT * \n" +
                "FROM \n" +
                "	Shifts S\n" +
                "WHERE\n" +
                "	IsActive = 1\n" +
                "       AND ShiftID != ?" +
                "	AND (\n" +
                "	(@StartTime  BETWEEN S.StartTime AND S.EndTIme)\n" +
                "	OR\n" +
                "	(@EndTIme BETWEEN S.StartTime AND S.EndTIme)\n" +
                "	OR\n" +
                "	(S.StartTime BETWEEN @StartTime AND @EndTIme)\n" +
                "	OR\n" +
                "	(S.EndTime BETWEEN @StartTime AND @EndTIme)\n" +
                "	)";
        try {
            int result = 0;
            ps = connection.prepareStatement(query);
            ps.setString(1, shift.getStartTime().toString());
            ps.setString(2, shift.getEndTime().toString());
            ps.setInt(3, shift.getShiftID());
            rs = ps.executeQuery();
            while (rs.next()) {
                result++;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return 0;
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
