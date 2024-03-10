package model;

import dbhelper.DAOBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import ultility.datetimeutil.DateTimeUtil;

public class OvertimeDAO extends DAOBase {

    final DateTimeUtil dateTimeUtil = new DateTimeUtil();

    public OvertimeDTO getOvertimeDTO(ResultSet result) throws SQLException {
        LocalDate date = dateTimeUtil.parseSqlDate(result.getDate("date"));
        int employeeID = result.getInt("EmployeeID");
        LocalTime startTime = dateTimeUtil.parseSQLTime(result.getTime("StartTime"));
        LocalTime endTimeD = dateTimeUtil.parseSQLTime(result.getTime("EndTime"));
        LocalTime checkIn = dateTimeUtil.parseSQLTime(result.getTime("CheckIn"));
        LocalTime checkOut = dateTimeUtil.parseSQLTime(result.getTime("CheckOut"));
        LocalTime openAt = dateTimeUtil.parseSQLTime(result.getTime("OpenAt"));
        LocalTime closeAt = dateTimeUtil.parseSQLTime(result.getTime("CloseAt"));;
        int createdBy = result.getInt("createdBy");
        return new OvertimeDTO(date, employeeID, startTime, endTimeD, openAt, closeAt, checkIn, checkOut, createdBy);
    }

    public boolean insertOvertime(String Date, int EmployeeID, String StartTime, String EndTime, String open, String Close, String CheckIn, String CheckOut, int CreateID) {
        connect();
        query = "INSERT INTO Overtimes ([Date], [EmployeeID], [StartTime], [EndTime], [OpenAt], [CloseAt], [CheckIn], [CheckOut], CreatedBy)\n" +
                "VALUES\n" +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Date);
            ps.setInt(2, EmployeeID);
            ps.setString(3, StartTime);
            ps.setString(4, EndTime);
            ps.setString(5, open);
            ps.setString(6, Close);
            ps.setString(7, CheckIn);
            ps.setString(8, CheckOut);
            ps.setInt(9, CreateID);
            int a = ps.executeUpdate();
            if (a > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return false;
    }

    public boolean deleteOvertime(String Date, String start, String End, int ID) {
        connect();
        query = "delete from Overtimes\n" +
                "where Date = ? and StartTime = ? and EndTime = ? and EmployeeID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Date);
            ps.setString(2, start);
            ps.setString(3, End);
            ps.setInt(4, ID);
            int a = ps.executeUpdate();
            if (a > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return false;
    }

    public OvertimeDTO getOverTimeDTO(LocalDate xDate, int xEmployeeID) {
        connect();
        query = "SELECT * FROM Overtimes\n" +
                "WHERE\n" +
                "	EmployeeID = ? AND [Date] = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, xEmployeeID);
            ps.setString(2, xDate.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate date = dateTimeUtil.parseSqlDate(rs.getDate("date"));
                int employeeID = rs.getInt("EmployeeID");
                LocalTime startTime = dateTimeUtil.parseSQLTime(rs.getTime("StartTime"));
                LocalTime endTimeD = dateTimeUtil.parseSQLTime(rs.getTime("EndTime"));
                LocalTime checkIn = dateTimeUtil.parseSQLTime(rs.getTime("CheckIn"));
                LocalTime checkOut = dateTimeUtil.parseSQLTime(rs.getTime("CheckOut"));
                LocalTime openAt = dateTimeUtil.parseSQLTime(rs.getTime("OpenAt"));
                LocalTime closeAt = dateTimeUtil.parseSQLTime(rs.getTime("CloseAt"));;
                int createdBy = rs.getInt("createdBy");
                return new OvertimeDTO(date, employeeID, startTime, endTimeD, openAt, closeAt, checkIn, checkOut, createdBy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return null;
    }

    public ArrayList<LocalDate> getAllOverTimeDTO() {
        connect();
        ArrayList<LocalDate> list = new ArrayList<>();
        query = "select distinct Date from [Overtimes]";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate date = dateTimeUtil.parseSqlDate(rs.getDate("date"));

                list.add(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public ArrayList<OvertimeDTO> getOverTimeDTOByDay(LocalDate xDate) {
        connect();
        ArrayList<OvertimeDTO> list = new ArrayList<>();
        query = "SELECT * FROM Overtimes\n" +
                "WHERE\n" +
                "	[Date] = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, xDate.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate date = dateTimeUtil.parseSqlDate(rs.getDate("date"));
                int employeeID = rs.getInt("EmployeeID");
                LocalTime startTime = dateTimeUtil.parseSQLTime(rs.getTime("StartTime"));
                LocalTime endTimeD = dateTimeUtil.parseSQLTime(rs.getTime("EndTime"));
                LocalTime checkIn = dateTimeUtil.parseSQLTime(rs.getTime("CheckIn"));
                LocalTime checkOut = dateTimeUtil.parseSQLTime(rs.getTime("CheckOut"));
                LocalTime openAt = dateTimeUtil.parseSQLTime(rs.getTime("OpenAt"));
                LocalTime closeAt = dateTimeUtil.parseSQLTime(rs.getTime("CloseAt"));
                int createdBy = rs.getInt("createdBy");
                list.add(new OvertimeDTO(date, employeeID, startTime, endTimeD, openAt, closeAt, checkIn, checkOut, createdBy));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public OvertimeDTO getConflicts(int xEmployeeid, String xDate, int xShiftID) {
        connect();
        query = "DECLARE @EmployeeID INT = ? \n" +
                "DECLARE @Date DATE = ? \n" +
                "DECLARE @ShiftID INT = ? \n" +
                "\n" +
                "SELECT \n" +
                "	OT.Date,\n" +
                "	OT.EmployeeID,\n" +
                "	OT.StartTime,\n" +
                "	OT.EndTime,\n" +
                "	OT.OpenAt,\n" +
                "	OT.CloseAt,\n" +
                "	OT.CheckIn,\n" +
                "	OT.CheckOut,\n" +
                "	OT.CreatedBy\n" +
                "FROM \n" +
                "    Overtimes OT\n" +
                "    JOIN Shifts S ON OT.Date = @Date\n" +
                "WHERE \n" +
                "    ShiftID = @ShiftID\n" +
                "	AND EmployeeID = @EmployeeID\n" +
                "    AND \n" +
                "    (   \n" +
                "        (OT.StartTime > S.StartTime AND OT.StartTime < S.EndTime)\n" +
                "        OR\n" +
                "        (OT.EndTime > S.StartTime AND OT.EndTime < S.EndTime)\n" +
                "        OR \n" +
                "        (S.StartTime > OT.StartTime AND S.StartTime < OT.EndTime)\n" +
                "        OR\n" +
                "        (S.EndTime > OT.StartTime AND S.EndTime < OT.EndTime)\n" +
                "		OR\n" +
                "		(S.StartTime = OT.StartTime)\n" +
                "		OR \n" +
                "		(S.EndTime = OT.EndTime)\n" +
                "    )";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, xEmployeeid);
            ps.setString(2, xDate);
            ps.setInt(3, xShiftID);
            rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate date = dateTimeUtil.parseSqlDate(rs.getDate("date"));
                int employeeID = rs.getInt("EmployeeID");
                LocalTime startTime = dateTimeUtil.parseSQLTime(rs.getTime("StartTime"));
                LocalTime endTimeD = dateTimeUtil.parseSQLTime(rs.getTime("EndTime"));
                LocalTime checkIn = dateTimeUtil.parseSQLTime(rs.getTime("CheckIn"));
                LocalTime checkOut = dateTimeUtil.parseSQLTime(rs.getTime("CheckOut"));
                LocalTime openAt = dateTimeUtil.parseSQLTime(rs.getTime("OpenAt"));
                LocalTime closeAt = dateTimeUtil.parseSQLTime(rs.getTime("CloseAt"));;
                int createdBy = rs.getInt("createdBy");
                return new OvertimeDTO(date, employeeID, startTime, endTimeD, openAt, closeAt, checkIn, checkOut, createdBy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    public boolean UpdateCheckIn(int xEmployeeID) {
        connect();
        query = "" +
                "UPDATE Overtimes\n" +
                "SET CheckIn = CONVERT(time, GETDATE())\n" +
                "FROM\n" +
                "	Overtimes OT\n" +
                "WHERE \n" +
                "	CONVERT(date, OT.Date) = CONVERT(date, GETDATE()) \n" +
                "	AND CONVERT(time, GETDATE()) BETWEEN OT.OpenAt AND OT.CloseAt\n" +
                "	AND EmployeeID = ?\n" +
                "	AND CheckIn is null";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, xEmployeeID);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return false;
    }

    public boolean UpdateCheckOut(int xEmployeeID) {
        connect();
        query = "" +
                "UPDATE Overtimes\n" +
                "SET CheckOut = CONVERT(time, GETDATE())\n" +
                "FROM\n" +
                "	Overtimes OT\n" +
                "WHERE \n" +
                "	CONVERT(date, OT.Date) = CONVERT(date, GETDATE()) \n" +
                "	AND CONVERT(time, GETDATE()) BETWEEN OT.OpenAt AND OT.CloseAt\n" +
                "	AND EmployeeID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, xEmployeeID);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.closeAll();
        }
        return false;
    }

    public OvertimeDTO getCurrentOvertime(int xEmployeeID) {
        connect();
        query = "" +
                "SELECT * \n" +
                "FROM\n" +
                "	Overtimes OT\n" +
                "WHERE \n" +
                "	CONVERT(date, OT.Date) = CONVERT(date, GETDATE()) \n" +
                "	AND CONVERT(time, GETDATE()) BETWEEN OT.OpenAt AND OT.CloseAt\n" +
                "	AND EmployeeID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, xEmployeeID);
            rs = ps.executeQuery();
            while (rs.next()) {
                return getOvertimeDTO(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Đóng PrepareStatement, ResultSet, Connection
            super.closeAll();
        }
        return null;
    }

    public OvertimeDTO getCurrentOvertimeByDay(int xEmployeeID) {
        connect();
        query = "" +
                "SELECT * \n" +
                "FROM\n" +
                "	Overtimes OT\n" +
                "WHERE \n" +
                "	CONVERT(date, OT.Date) = CONVERT(date, GETDATE()) \n" +
                "	AND EmployeeID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, xEmployeeID);
            rs = ps.executeQuery();
            while (rs.next()) {
                return getOvertimeDTO(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Đóng PrepareStatement, ResultSet, Connection
            super.closeAll();
        }
        return null;
    }

    public ArrayList<OvertimeDTO> getOverTimeByRange(int id, LocalDate startDate, LocalDate endDate) {
        connect();
        ArrayList<OvertimeDTO> list = new ArrayList<>();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM Overtimes " +
                        "WHERE EmployeeID = ? " +
                        "AND [Date] BETWEEN ? AND ? ";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setString(2, startDate.toString());
                ps.setString(3, endDate.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    LocalDate date = dateTimeUtil.parseSqlDate(rs.getDate("date"));
                    int employeeID = rs.getInt("EmployeeID");
                    LocalTime startTime = dateTimeUtil.parseSQLTime(rs.getTime("StartTime"));
                    LocalTime endTime = dateTimeUtil.parseSQLTime(rs.getTime("EndTime"));
                    LocalTime checkIn = dateTimeUtil.parseSQLTime(rs.getTime("CheckIn"));
                    LocalTime checkOut = dateTimeUtil.parseSQLTime(rs.getTime("CheckOut"));
                    LocalTime openAt = dateTimeUtil.parseSQLTime(rs.getTime("OpenAt"));
                    LocalTime closeAt = dateTimeUtil.parseSQLTime(rs.getTime("CloseAt"));;
                    int createdBy = rs.getInt("createdBy");
                    list.add(new OvertimeDTO(date, employeeID, startTime, endTime, openAt, closeAt, checkIn, checkOut, createdBy));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeAll();
            }
        }
        return list;
    }

    public static void main(String[] args) {
        OvertimeDAO overtimeDAO = new OvertimeDAO();
        System.out.println(overtimeDAO.deleteOvertime("2024-02-23", "17:00", "19:30", 1));
//        LocalDate date = LocalDate.parse("   ");
//        LocalDate date2 = LocalDate.parse("2024-02-24");
//        System.out.println(date.compareTo(date2));
//        System.out.println(overtimeDAO.getOverTimeDTO(date, 1));
//        System.out.println(overtimeDAO.getConflicts(1, "2024-02-23", 1));
        //System.out.println(overtimeDAO.insertOvertime("2024-03-3", 1, "17:00", "19:30", "16:30", "20:00", null, null, 1));

    }
}
