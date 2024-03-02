/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.*;
import java.time.format.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import ultility.datetimeutil.DateTimeUtil;
import java.util.Locale;

/**
 *
 * @author admin
 */
public class StatisticsDAO extends dbhelper.DAOBase {

    private final DateTimeUtil DATE_UTIL = new DateTimeUtil();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    private static long parseToMinutes(String workedHoursString) {
        if (workedHoursString == null || workedHoursString.isEmpty()) {
            return 0;
        }
        String[] parts = workedHoursString.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    private StatisticsDTO getStatisticsDTO(ResultSet rs) throws SQLException {
        int employeeID = rs.getInt("EmployeeID");
        LocalDate date = DATE_UTIL.parseSqlDate(rs.getDate("Date"));
        String shiftName = rs.getString("ShiftName");
        LocalTime startTime = DATE_UTIL.parseSQLTime(rs.getTime("StartTime"));
        LocalTime endTime = DATE_UTIL.parseSQLTime(rs.getTime("EndTime"));
        LocalTime checkIn = DATE_UTIL.parseSQLTime(rs.getTime("CheckIn"));
        LocalTime checkOut = DATE_UTIL.parseSQLTime(rs.getTime("CheckOut"));
        Duration shiftHours = Duration.ofMinutes(parseToMinutes(rs.getString("Hours")));
        LocalTime otStartTime = DATE_UTIL.parseSQLTime(rs.getTime("OtStartTime"));
        LocalTime otEndTime = DATE_UTIL.parseSQLTime(rs.getTime("OtEndTime"));
        LocalTime otCheckIn = DATE_UTIL.parseSQLTime(rs.getTime("OtCheckIn"));
        LocalTime otCheckOut = DATE_UTIL.parseSQLTime(rs.getTime("OtCheckOut"));
        Duration otHours = Duration.ofMinutes(parseToMinutes(rs.getString("OtHours")));
        Duration totalDay = shiftHours.plus(otHours);

        return new StatisticsDTO(employeeID, date, shiftName, startTime, endTime, checkIn, checkOut, shiftHours,
                otStartTime, otEndTime, otCheckIn, otCheckOut, otHours, totalDay);
    }

    public ArrayList<StatisticsDTO> getStatisticInRange(int employeeID, LocalDate startDate, LocalDate endDate) {
        ArrayList<StatisticsDTO> list = new ArrayList<>();
        try {
            String SQL = "SELECT EmployeeID, [Date],ShiftName , StartTime, EndTime, CheckIn, CheckOut, \n"
                    + "CONVERT(VARCHAR, MinutesDifference / 60) + ':' + RIGHT('0' + CONVERT(VARCHAR, MinutesDifference % 60), 2) AS [Hours], \n"
                    + "OtStartTime, OtEndTime, OtCheckIn, OtCheckOut, \n"
                    + "CONVERT(VARCHAR, OtDifference / 60) + ':' + RIGHT('0' + CONVERT(VARCHAR, OtDifference % 60), 2) AS [OtHours]\n"
                    + "FROM (\n"
                    + "	SELECT ts.EmployeeID, ts.[Date], s.[Name] AS ShiftName, s.[StartTime], s.[EndTime], ts.CheckIn, ts.CheckOut,\n"
                    + "	DATEDIFF(MINUTE, ts.CheckIn, ts.CheckOut) AS MinutesDifference, \n"
                    + "	ot.[Date] as OtDate, ot.StartTime as OtStartTime, ot.EndTime as OtEndTime, \n"
                    + "	ot.CheckIn as OtCheckIn , ot.CheckOut as OtCheckOut, \n"
                    + "	DATEDIFF(MINUTE, ot.CheckIn, ot.CheckOut) AS OtDifference\n"
                    + "	FROM Timesheet ts\n"
                    + "	JOIN Shifts s ON ts.ShiftID = s.ShiftID\n"
                    + "	JOIN Employees e ON ts.EmployeeID = e.EmployeeID\n"
                    + "	LEFT JOIN Overtimes ot ON e.EmployeeID = ot.EmployeeID AND ts.[Date] = ot.[Date]\n"
                    + "	WHERE ts.EmployeeID = ? AND ts.[Date] BETWEEN ? AND ?	\n"
                    + ") AS subquery ORDER BY [Date];";
            connect();
            ps = connection.prepareStatement(SQL);
            ps.setInt(1, employeeID);
            ps.setString(2, startDate.toString());
            ps.setString(3, endDate.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getStatisticsDTO(rs));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public ArrayList<StatisticsDTO> getStatisticInRangeByAJAX(int employeeID, LocalDate startDate, LocalDate endDate, int page) {
        ArrayList<StatisticsDTO> list = new ArrayList<>();
        try {
            String SQL = "SELECT EmployeeID, [Date],ShiftName , StartTime, EndTime, CheckIn, CheckOut, \n"
                    + "CONVERT(VARCHAR, MinutesDifference / 60) + ':' + RIGHT('0' + CONVERT(VARCHAR, MinutesDifference % 60), 2) AS [Hours], \n"
                    + "OtStartTime, OtEndTime, OtCheckIn, OtCheckOut, \n"
                    + "CONVERT(VARCHAR, OtDifference / 60) + ':' + RIGHT('0' + CONVERT(VARCHAR, OtDifference % 60), 2) AS [OtHours]\n"
                    + "FROM (\n"
                    + "	SELECT ts.EmployeeID, ts.[Date], s.[Name] AS ShiftName, s.[StartTime], s.[EndTime], ts.CheckIn, ts.CheckOut,\n"
                    + "	DATEDIFF(MINUTE, ts.CheckIn, ts.CheckOut) AS MinutesDifference, \n"
                    + "	ot.[Date] as OtDate, ot.StartTime as OtStartTime, ot.EndTime as OtEndTime, \n"
                    + "	ot.CheckIn as OtCheckIn , ot.CheckOut as OtCheckOut, \n"
                    + "	DATEDIFF(MINUTE, ot.CheckIn, ot.CheckOut) AS OtDifference\n"
                    + "	FROM Timesheet ts\n"
                    + "	JOIN Shifts s ON ts.ShiftID = s.ShiftID\n"
                    + "	JOIN Employees e ON ts.EmployeeID = e.EmployeeID\n"
                    + "	LEFT JOIN Overtimes ot ON e.EmployeeID = ot.EmployeeID AND ts.[Date] = ot.[Date]\n"
                    + "	WHERE ts.EmployeeID = ? AND ts.[Date] BETWEEN ? AND ?	\n"
                    + ") AS subquery ORDER BY [Date] "
                    + "OFFSET ? ROWS FETCH NEXT 10 ROW ONLY"
                    + ";";
            connect();
            ps = connection.prepareStatement(SQL);
            ps.setInt(1, employeeID);
            ps.setString(2, startDate.toString());
            ps.setString(3, endDate.toString());
            ps.setInt(4, (page - 1) * 10);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getStatisticsDTO(rs));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public int getCountStatistics(int employeeID, LocalDate startDate, LocalDate endDate) {
        int n = 0;
        try {
            connect();
            String sql = "SELECT COUNT(*) FROM (\n"
                    + "	SELECT ts.EmployeeID, ts.[Date], s.[Name] AS ShiftName, s.[StartTime], s.[EndTime], ts.CheckIn, ts.CheckOut,\n"
                    + "		DATEDIFF(MINUTE, ts.CheckIn, ts.CheckOut) AS MinutesDifference, \n"
                    + "		ot.[Date] as OtDate, ot.StartTime as OtStartTime, ot.EndTime as OtEndTime, \n"
                    + "		ot.CheckIn as OtCheckIn , ot.CheckOut as OtCheckOut, \n"
                    + "		DATEDIFF(MINUTE, ot.CheckIn, ot.CheckOut) AS OtDifference\n"
                    + "	FROM Timesheet ts\n"
                    + "		JOIN Shifts s ON ts.ShiftID = s.ShiftID\n"
                    + "		JOIN Employees e ON ts.EmployeeID = e.EmployeeID\n"
                    + "		LEFT JOIN Overtimes ot ON e.EmployeeID = ot.EmployeeID AND ts.[Date] = ot.[Date]\n"
                    + "	WHERE ts.EmployeeID = ? AND ts.[Date] BETWEEN ? AND ? \n"
                    + ") AS Subquery;";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, employeeID);
            ps.setString(2, startDate.toString());
            ps.setString(3, endDate.toString());
            n = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return n;
    }

    private static final TimesheetDAO tsDAO = new TimesheetDAO();
    private static final OvertimeDAO otDAO = new OvertimeDAO();
    private static final ShiftDAO sDAO = new ShiftDAO();
    private static final LeaveDAO lvDAO = new LeaveDAO();

    private ArrayList<TimesheetDTO> getAllTimeSheet(int id, LocalDate startDate, LocalDate endDate) {
        connect();
        ArrayList<TimesheetDTO> timeSheets = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Timesheet "
                    + "WHERE EmployeeID = ? "
                    + "AND Date BETWEEN ? AND ? ";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, startDate.toString());
            ps.setString(3, endDate.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                timeSheets.add(tsDAO.getTimesheetDTO(rs));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return timeSheets;
    }

    public ArrayList<StatisticsDTO> getStatistics(int id, LocalDate startDate, LocalDate endDate) {
        StatisticsDAO dao = new StatisticsDAO();
        ArrayList<StatisticsDTO> statistics = new ArrayList<>();
        ArrayList<LocalDate> listDay = dao.getListDay(startDate, endDate);
        ArrayList<TimesheetDTO> listTs = tsDAO.getTimesheetInRange(id, startDate, endDate);
        ArrayList<OvertimeDTO> listOT = otDAO.getOverTimeByRange(id, startDate, endDate);
        ArrayList<ShiftDTO> listShift = sDAO.getAllShiftDTO();
        ArrayList<LeaveDTO> listLeave = lvDAO.getLeaveInRange(id, startDate, endDate);
//        for (TimesheetDTO ts : listDay) {
//            System.out.println(ts.getDate());
//        }
        for (LocalDate d : listDay) {
            statistics.add(new StatisticsDTO(id, d, "", null, null, null,
                    null, Duration.ZERO, null, null, null,
                    null, Duration.ZERO, Duration.ZERO));
        }

        for (StatisticsDTO s : statistics) {
            for (TimesheetDTO ts : listTs) {
                if (s.getDate().equals(ts.getDate())) {
                    s.setEmployeeID(id);
                    s.setShiftName(dao.getShiftName(listShift, ts.getShiftID()));
                    s.setStartTime(dao.getStartTime(listShift, id));
                    s.setEndTime(dao.getEndTime(listShift, id));
                    if (ts.getCheckin() != null) {
                        s.setCheckIn(ts.getCheckin());
                    }
                    if (ts.getCheckout() != null) {
                        s.setCheckOut(ts.getCheckout());
                    }
                    if (ts.getCheckin() != null && ts.getCheckout() != null) {
                        s.setShiftHours(Duration.between(ts.getCheckin(), ts.getCheckout()));
                    }

                }
            }
            for (OvertimeDTO ot : listOT) {
                if (s.getDate().equals(ot.getDate())) {
                    if (ot.getStartTime() != null) {
                        s.setOtStartTime(ot.getStartTime());
                    }
                    if (ot.getEndTime() != null) {
                        s.setOtEndTime(ot.getEndTime());
                    }
                    if (ot.getCheckIn() != null) {
                        s.setOtCheckIn(ot.getCheckIn());
                    }
                    if (ot.getCheckOut() != null) {
                        s.setOtCheckOut(ot.getCheckOut());
                    }
                    if (ot.getCheckIn() != null && ot.getCheckOut() != null) {
                        s.setOtHours(Duration.between(ot.getCheckIn(), ot.getCheckOut()));
                    }
                }
            }
            if (s.getShiftHours() != null && s.getOtHours() == null) {
                s.setTotalDay(s.getShiftHours());
            }
            if (s.getShiftHours() == null && s.getOtHours() != null) {
                s.setTotalDay(s.getOtHours());
            }
            if (s.getShiftHours() != null && s.getOtHours() != null) {
                s.setTotalDay(s.getShiftHours().plus(s.getOtHours()));
            }
            if (s.getShiftHours() == null && s.getOtHours() == null) {
                s.setTotalDay(Duration.ZERO);
            }
        }
//        for (StatisticsDTO s : statistics) {
//            System.out.println(s.toString());
//        }
        return statistics;
    }

    private ArrayList<LocalDate> getListDay(LocalDate startDate, LocalDate endDate) {
        ArrayList<LocalDate> listDay = new ArrayList<>();
        int startYear = startDate.getYear();
        int endYear = endDate.getYear();
        int startMonth = startDate.getMonthValue();
        int endMonth = endDate.getMonthValue();
        int startDay = startDate.getDayOfMonth();
        int endDay = endDate.getDayOfMonth();
        int numberOfYears = 0;
        if (endYear > startYear) {
            numberOfYears = (endYear - startYear);
        }
        if (numberOfYears > 0) {
//            for (int month = startMonth; month <= 12; month++) {
//                LocalDate firstDayOfMonth = LocalDate.of(startYear, month, 1);
//                int daysInMonth = firstDayOfMonth.lengthOfMonth();
//                for (int day = 1; day <= daysInMonth; day++) {
//                    LocalDate current = LocalDate.of(startYear, month, day);
//                    listDay.add(current);
//                }
//            }
//            for (int month_2 = 1; month_2 <= endMonth; month_2++) {
//                LocalDate firstDayOfMonth = LocalDate.of(endYear, month_2, 1);
//                int daysInMonth = firstDayOfMonth.lengthOfMonth();
//                for (int day = 1; day <= daysInMonth; day++) {
//                    LocalDate current = LocalDate.of(endYear, month_2, day);
//                    listDay.add(current);
//                }
//            }
            // startDate -> End 1st year
            for (int month = startMonth; month <= 12; month++) {
                LocalDate firstDayOfMonth = LocalDate.of(startYear, month, 1);
                int daysInMonth = firstDayOfMonth.lengthOfMonth();
                for (int day = 1; day <= daysInMonth; day++) {
                    LocalDate current = LocalDate.of(startYear, month, day);
                    listDay.add(current);
                }
            }
            // End 1st year -> endDate
            int iterYear = startYear + 1; //sau khi het 1st year
            for (int i = numberOfYears - 1; i >= 0; i--) {
                if (i > 0) {
                    for (int month = 1; month <= 12; month++) { // 1 year
                        LocalDate firstDayOfMonth = LocalDate.of(iterYear, month, 1);
                        int daysInMonth = firstDayOfMonth.lengthOfMonth();
                        for (int day = 1; day <= daysInMonth; day++) {
                            LocalDate current = LocalDate.of(iterYear, month, day);
                            listDay.add(current);
                        }
                    }
                } else {
                    for (int month = 1; month <= endMonth; month++) {
                        LocalDate firstDayOfMonth = LocalDate.of(iterYear, month, 1);
                        int daysInMonth = firstDayOfMonth.lengthOfMonth();
                        for (int day = 1; day <= daysInMonth; day++) {
                            LocalDate current = LocalDate.of(iterYear, month, day);
                            listDay.add(current);
                        }
                    }
                }
                iterYear++;
            }
        } else {
            for (int month = startMonth; month <= endMonth; month++) {
                LocalDate firstDayOfMonth = LocalDate.of(startYear, month, 1);
                int daysInMonth = firstDayOfMonth.lengthOfMonth();
                for (int day = 1; day <= daysInMonth; day++) {
                    LocalDate current = LocalDate.of(startYear, month, day);
                    listDay.add(current);
                }
            }
        }
        return listDay;
    }

    private String getShiftName(ArrayList<ShiftDTO> listS, int id) {
        for (ShiftDTO s : listS) {
            if (s.getShiftID() == id) {
                return s.getName();
            }
        }
        return "";
    }

    private LocalTime getStartTime(ArrayList<ShiftDTO> listS, int id) {
        for (ShiftDTO s : listS) {
            if (s.getShiftID() == id) {
                return s.getStartTime();
            }
        }
        return null;
    }

    private LocalTime getEndTime(ArrayList<ShiftDTO> listS, int id) {
        for (ShiftDTO s : listS) {
            if (s.getShiftID() == id) {
                return s.getEndTime();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        StatisticsDAO dao = new StatisticsDAO();
        int id = 3;
        LocalDate startDate = LocalDate.parse("2024-02-01");
        LocalDate endDate = LocalDate.parse("2026-02-28");

//        ArrayList<StatisticsDTO> statistics = dao.getStatisticInRangeByAJAX(id, startDate, endDate, 0);
//        int i = 0;
//        long sum = 0;
//        for (StatisticsDTO s : statistics) {
//            System.out.print(s.getDate());
//            System.out.print(" - ");
//            System.out.print(s.getShiftHours().toHours());
//            System.out.print(":");
//            System.out.print(s.getShiftHours().toMinutes() % 60);
//            sum += s.getShiftHours().toMinutes();
//            System.out.println(" - Total Day: " + s.getTotalDay().toHours());
//        }
//        System.out.println("Total Hours: " + sum / 60 + ":" + sum % 60);
//        ArrayList<StatisticsDTO> statistics_2 = dao.getStatisticInRangeByAJAX(id, startDate, endDate, 2);
//        System.out.println("Size: " + statistics_2.size());
////        System.out.println(statistics.size());
//        ArrayList<LocalDate> days = dao.getListDay(startDate, endDate);
//        for (LocalDate d : days) {
//            System.out.println(d.toString());
//        }

        ArrayList<StatisticsDTO> listS = dao.getStatistics(id, startDate, endDate);
        for(StatisticsDTO s: listS){
            System.out.println(s.toString());
        }
    }
}
