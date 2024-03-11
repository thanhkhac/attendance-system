/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.request;

import dbhelper.DAOBase;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import model.EmployeeDTO;
import ultility.datetimeutil.DateTimeUtil;

/**
 *
 * @author admin
 */
public class OverTimeRequestDAO extends DAOBase {

    static final LeaveRequestDAO LR = new LeaveRequestDAO();
    static final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    public boolean insertOverTimeRequest(EmployeeDTO e, LocalDate sentDate, LocalDate date, Time startTime, Time endTime, int createdBy) {
        if (connection != null) {
            try {
                String sql = "INSERT INTO OvertimeRequests(Date, EmployeeID, SentDate, StartTime , EndTime, CreatedBy) "
                        + "VALUES (?,?,?,?,?,?)";
                ps = connection.prepareStatement(sql);
                ps.setString(1, date.toString());
                ps.setInt(2, e.getEmployeeID());
                ps.setString(3, sentDate.toString());
                ps.setTime(4, startTime);
                ps.setTime(5, endTime);
                ps.setInt(6, createdBy);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    return true;
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            } finally {
                closeAll();
            }
        }
        return false;
    }

    public ArrayList<OverTimeRequestExtendDTO> getOverTimeListByDepartment(int departID) {
        connect();
        ArrayList<OverTimeRequestExtendDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT\n"
                    + "    OvertimeRequestID,\n"
                    + "    Date,\n"
                    + "    orq.EmployeeID,\n"
                    + "    SentDate,\n"
                    + "    StartTime,\n"
                    + "    EndTime, \n"
                    + "    ManagerApprove,\n"
                    + "    HrApprove,\n"
                    + "    ManagerID,\n"
                    + "    HrID,\n"
                    + "    CreatedBy,\n"
                    + "    [Status],\n"
                    + "    e.DepartmentID,\n"
                    + "    CASE \n"
                    + "        WHEN OvertimeRequestID IN (\n"
                    + "            SELECT OTR.OvertimeRequestID\n"
                    + "            FROM OvertimeRequests OTR \n"
                    + "            JOIN Overtimes OT ON OTR.Date = OT.Date AND OTR.EmployeeID = OT.EmployeeID \n"
                    + "            WHERE \n"
                    + "                (OTR.StartTime > OT.StartTime AND OTR.StartTime < OT.EndTime)\n"
                    + "                OR\n"
                    + "                (OTR.EndTime > OT.StartTime AND OTR.EndTime < OT.EndTime)\n"
                    + "                OR\n"
                    + "                (OT.StartTime > OTR.StartTime AND OT.StartTime < OTR.EndTime)\n"
                    + "                OR\n"
                    + "                (OT.EndTime > OTR.StartTime AND OT.EndTime < OTR.EndTime)\n"
                    + "                OR\n"
                    + "                (OTR.StartTime = OT.StartTime)\n"
                    + "                OR\n"
                    + "                (OTR.EndTime = OT.EndTime)\n"
                    + "            UNION\n"
                    + "            SELECT OTR.OvertimeRequestID\n"
                    + "            FROM OvertimeRequests OTR\n"
                    + "            JOIN Timesheet TS ON OTR.Date = TS.Date\n"
                    + "            JOIN Shifts SH ON TS.ShiftID = SH.ShiftID\n"
                    + "            WHERE\n"
                    + "                (OTR.StartTime > SH.StartTime AND OTR.StartTime < SH.EndTime)\n"
                    + "                OR\n"
                    + "                (OTR.EndTime > SH.StartTime AND OTR.EndTime < SH.EndTime)\n"
                    + "                OR\n"
                    + "                (SH.StartTime > OTR.StartTime AND SH.StartTime < OTR.EndTime)\n"
                    + "                OR\n"
                    + "                (SH.EndTime > OTR.StartTime AND SH.EndTime < OTR.EndTime)\n"
                    + "                OR\n"
                    + "                (OTR.StartTime = SH.StartTime)\n"
                    + "                OR\n"
                    + "                (OTR.EndTime = SH.EndTime)\n"
                    + "        ) THEN 'conflict'\n"
                    + "        ELSE NULL\n"
                    + "    END AS Conflict\n"
                    + "FROM OvertimeRequests orq\n"
                    + "join Employees e on e.EmployeeID = orq.EmployeeID\n"
                    + "WHERE e.DepartmentID = ?\n"
                    + "ORDER BY \n"
                    + "ManagerApprove ASC , OvertimeRequestID ASC , Conflict ASC";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, departID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int overTimeRequestID = rs.getInt("OvertimeRequestID");
                LocalDate date = DATE_UTIL.parseSqlDate(rs.getDate("Date"));
                int employeeID = rs.getInt("EmployeeID");
                LocalDateTime sentDate = DATE_UTIL.parseSqlDateTime(rs.getTimestamp("SentDate"));
                LocalTime startTime = DATE_UTIL.parseSQLTime(rs.getTime("StartTime"));
                LocalTime endTime = DATE_UTIL.parseSQLTime(rs.getTime("EndTime"));
                Boolean managerApprove = LR.parseBoolean(rs.getString("ManagerApprove"));
                Boolean hrApprove = LR.parseBoolean(rs.getString("HrApprove"));
                int managerID = rs.getInt("ManagerID");
                int hrID = rs.getInt("HrID");
                int createdBy = rs.getInt("CreatedBy");
                Boolean status = LR.parseBoolean(rs.getString("Status"));
                String conflict = rs.getString("Conflict");
                int departmentID = rs.getInt("DepartmentID");

                OverTimeRequestExtendDTO ore = new OverTimeRequestExtendDTO(overTimeRequestID, date, employeeID, sentDate, startTime, endTime, managerApprove, hrApprove, managerID, hrID, createdBy, status, departmentID, conflict);
                list.add(ore);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            closeAll();
        }
        return list;
    }

    public ArrayList<OverTimeRequestExtendDTO> getOverTimeListForHR(int approve) {
        connect();
        ArrayList<OverTimeRequestExtendDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT\n"
                    + "    OvertimeRequestID,\n"
                    + "    Date,\n"
                    + "    orq.EmployeeID,\n"
                    + "    SentDate,\n"
                    + "    StartTime,\n"
                    + "    EndTime, \n"
                    + "    ManagerApprove,\n"
                    + "    HrApprove,\n"
                    + "    ManagerID,\n"
                    + "    HrID,\n"
                    + "    CreatedBy,\n"
                    + "    [Status],\n"
                    + "    e.DepartmentID,\n"
                    + "    CASE \n"
                    + "        WHEN OvertimeRequestID IN (\n"
                    + "            SELECT OTR.OvertimeRequestID\n"
                    + "            FROM OvertimeRequests OTR \n"
                    + "            JOIN Overtimes OT ON OTR.Date = OT.Date AND OTR.EmployeeID = OT.EmployeeID \n"
                    + "            WHERE \n"
                    + "                (OTR.StartTime > OT.StartTime AND OTR.StartTime < OT.EndTime)\n"
                    + "                OR\n"
                    + "                (OTR.EndTime > OT.StartTime AND OTR.EndTime < OT.EndTime)\n"
                    + "                OR\n"
                    + "                (OT.StartTime > OTR.StartTime AND OT.StartTime < OTR.EndTime)\n"
                    + "                OR\n"
                    + "                (OT.EndTime > OTR.StartTime AND OT.EndTime < OTR.EndTime)\n"
                    + "                OR\n"
                    + "                (OTR.StartTime = OT.StartTime)\n"
                    + "                OR\n"
                    + "                (OTR.EndTime = OT.EndTime)\n"
                    + "            UNION\n"
                    + "            SELECT OTR.OvertimeRequestID\n"
                    + "            FROM OvertimeRequests OTR\n"
                    + "            JOIN Timesheet TS ON OTR.Date = TS.Date\n"
                    + "            JOIN Shifts SH ON TS.ShiftID = SH.ShiftID\n"
                    + "            WHERE\n"
                    + "                (OTR.StartTime > SH.StartTime AND OTR.StartTime < SH.EndTime)\n"
                    + "                OR\n"
                    + "                (OTR.EndTime > SH.StartTime AND OTR.EndTime < SH.EndTime)\n"
                    + "                OR\n"
                    + "                (SH.StartTime > OTR.StartTime AND SH.StartTime < OTR.EndTime)\n"
                    + "                OR\n"
                    + "                (SH.EndTime > OTR.StartTime AND SH.EndTime < OTR.EndTime)\n"
                    + "                OR\n"
                    + "                (OTR.StartTime = SH.StartTime)\n"
                    + "                OR\n"
                    + "                (OTR.EndTime = SH.EndTime)\n"
                    + "        ) THEN 'conflict'\n"
                    + "        ELSE NULL\n"
                    + "    END AS Conflict\n"
                    + "FROM OvertimeRequests orq\n"
                    + "join Employees e on e.EmployeeID = orq.EmployeeID\n"
                    + "WHERE orq.ManagerApprove like '%" + approve + "%'\n"
                    + "ORDER BY \n"
                    + "HrApprove ASC , OvertimeRequestID ASC , Conflict ASC";
            ps = connection.prepareStatement(sql);
//            ps.setInt(1, approve);
            rs = ps.executeQuery();
            while (rs.next()) {
                int overTimeRequestID = rs.getInt("OvertimeRequestID");
                LocalDate date = DATE_UTIL.parseSqlDate(rs.getDate("Date"));
                int employeeID = rs.getInt("EmployeeID");
                LocalDateTime sentDate = DATE_UTIL.parseSqlDateTime(rs.getTimestamp("SentDate"));
                LocalTime startTime = DATE_UTIL.parseSQLTime(rs.getTime("StartTime"));
                LocalTime endTime = DATE_UTIL.parseSQLTime(rs.getTime("EndTime"));
                Boolean managerApprove = LR.parseBoolean(rs.getString("ManagerApprove"));
                Boolean hrApprove = LR.parseBoolean(rs.getString("HrApprove"));
                int managerID = rs.getInt("ManagerID");
                int hrID = rs.getInt("HrID");
                int createdBy = rs.getInt("CreatedBy");
                Boolean status = LR.parseBoolean(rs.getString("Status"));
                String conflict = rs.getString("Conflict");
                int departmentID = rs.getInt("DepartmentID");

                OverTimeRequestExtendDTO ore = new OverTimeRequestExtendDTO(overTimeRequestID, date, employeeID, sentDate, startTime, endTime, managerApprove, hrApprove, managerID, hrID, createdBy, status, departmentID, conflict);
                list.add(ore);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            closeAll();
        }
        return list;
    }

    public boolean overtimeRequestApproveByManager(int managerApprove, int managerID, int overTimeRequestID) {
        connect();
        try {
            String sql = "UPDATE OvertimeRequests\n"
                    + "	SET ManagerApprove = ? , ManagerID = ?\n"
                    + "	WHERE OvertimeRequestID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, managerApprove);
            ps.setInt(2, managerID);
            ps.setInt(3, overTimeRequestID);
            int row = ps.executeUpdate();
            if (row > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            closeAll();
        }
        return false;
    }

    public void updateStatus(int status, int requestID) {
        connect();
        try {
            String sql = "update OvertimeRequests\n"
                    + "set Status = ?\n"
                    + "where OvertimeRequestID = ?";
            connect();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, requestID);
            int rs = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    public OverTimeRequestDTO getOverTimeRequestByID(int requestID) {
        connect();
        OverTimeRequestDTO request = new OverTimeRequestDTO();
        try {
            String sql = "SELECT * FROM OvertimeRequests \n"
                    + "WHERE OvertimeRequestID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, requestID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int overTimeRequestID = rs.getInt("OvertimeRequestID");
                LocalDate date = DATE_UTIL.parseSqlDate(rs.getDate("Date"));
                int employeeID = rs.getInt("EmployeeID");
                LocalDateTime sentDate = DATE_UTIL.parseSqlDateTime(rs.getTimestamp("SentDate"));
                LocalTime startTime = DATE_UTIL.parseSQLTime(rs.getTime("StartTime"));
                LocalTime endTime = DATE_UTIL.parseSQLTime(rs.getTime("EndTime"));
                Boolean managerApprove = LR.parseBoolean(rs.getString("ManagerApprove"));
                Boolean hrApprove = LR.parseBoolean(rs.getString("HrApprove"));
                int managerID = rs.getInt("ManagerID");
                int hrID = rs.getInt("HrID");
                int createdBy = rs.getInt("CreatedBy");
                Boolean status = LR.parseBoolean(rs.getString("Status"));
                request = new OverTimeRequestDTO(overTimeRequestID, date, employeeID, sentDate, startTime, endTime, managerApprove, hrApprove, managerID, hrID, createdBy, status);
                return request;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            closeAll();
        }
        return null;
    }

    public boolean overtimeRequestApproveByHr(int hrApprove, int hrID, int overTimeRequestID) {
        connect();
        try {
            String sql = "UPDATE OvertimeRequests\n"
                    + "	SET HrApprove = ? , HrID = ?\n"
                    + "	WHERE OvertimeRequestID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, hrApprove);
            ps.setInt(2, hrID);
            ps.setInt(3, overTimeRequestID);
            int row = ps.executeUpdate();
            if (row > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            closeAll();
        }
        return false;
    }

    public static void main(String[] args) {
        OverTimeRequestDAO otDAO = new OverTimeRequestDAO();
        ArrayList<OverTimeRequestExtendDTO> list = new ArrayList<>();
//        list = otDAO.getOverTimeListByDepartment(2);
        list = otDAO.getOverTimeListForHR(1);
        System.out.println("1 List size : " + list.size());
        System.out.println("2 Manager approve : " + otDAO.overtimeRequestApproveByManager(1, 4, 65));
        System.out.println("3 HR approve : " + otDAO.overtimeRequestApproveByHr(1, 2, 65));
        /*
        for (OverTimeRequestExtendDTO otrq : list) {
            System.out.println(otrq.getOverTimeRequestID());
            System.out.println(otrq.getSentDate());
            System.out.println(otrq.getStartTime());
            System.out.println(otrq.getEndTime());
            System.out.println(otrq.getStatus());
            System.out.println(otrq.getManagerApprove());
            System.out.println(otrq.getDepartmentID());
        }
         */

        OverTimeRequestDTO otDTO = new OverTimeRequestDTO();
        otDTO = otDAO.getOverTimeRequestByID(1);
        System.out.println("4 " + otDTO);
    }
}
