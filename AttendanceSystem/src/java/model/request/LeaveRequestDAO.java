/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.request;

import dbhelper.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import model.*;
import ultility.datetimeutil.DateTimeUtil;

/**
 *
 * @author nguye
 */
public class LeaveRequestDAO extends DAOBase {

    static final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    public Boolean parseBoolean(String str) {
        Boolean boo = null;
        try {
            if (str == null || str.equalsIgnoreCase("null")) {
                boo = null;
            } else if (str.equalsIgnoreCase("1")) {
                boo = true;
            } else if (str.equalsIgnoreCase("0")) {
                boo = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return boo;
    }

    public ArrayList<LeaveRequestDTO> getLeaveRequestByEmployeeID(int emplID) {
        ArrayList<LeaveRequestDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM LeaveRequests\n"
                    + "WHERE EmployeeID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, emplID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int leaveRequestID = rs.getInt("LeaveRequestID");
                int employeeID = rs.getInt("EmployeeID");
                LocalDate sentDate = DATE_UTIL.parseSqlDate(rs.getDate("SentDate"));
                LocalDate startDate = DATE_UTIL.parseSqlDate(rs.getDate("StartDate"));
                LocalDate endDate = DATE_UTIL.parseSqlDate(rs.getDate("EndDate"));
                String filePath = rs.getNString("FilePath");
                String reason = rs.getNString("Reason");
                Boolean managerApprove = parseBoolean(rs.getString("ManagerApprove"));
                Boolean hrApprove = parseBoolean(rs.getString("HrApprove"));
                int managerID = rs.getInt("ManagerID");
                int hrID = rs.getInt("HrID");
                Boolean status = parseBoolean(rs.getString("Status"));

                LeaveRequestDTO lr = new LeaveRequestDTO(leaveRequestID, employeeID, sentDate, startDate, endDate, filePath, reason, managerApprove, hrApprove, managerID, hrID, status);
                list.add(lr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return list;
    }

    public ArrayList<LeaveRequestDTO> getLeaveRequestByDepartment(int deID) {
        ArrayList<LeaveRequestDTO> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                String sql = "SELECT LeaveRequestID ,\n"
                        + "		l.EmployeeID,\n"
                        + "		SentDate,\n"
                        + "		l.StartDate,\n"
                        + "		l.EndDate,\n"
                        + "		FilePath,\n"
                        + "		Reason,\n"
                        + "		ManagerApprove,\n"
                        + "		HrApprove,\n"
                        + "		ManagerID,\n"
                        + "		HrID,\n"
                        + "		[Status],\n"
                        + "		e.DepartmentID\n"
                        + "FROM LeaveRequests l\n"
                        + "JOIN Employees e ON l.EmployeeID = e.EmployeeID\n"
                        + "WHERE e.DepartmentID like '%" + deID + "%'\n"
                        + "ORDER BY ManagerApprove ASC , LeaveRequestID ASC";
                stm = connection.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int leaveRequestID = rs.getInt("LeaveRequestID");
                    int employeeID = rs.getInt("EmployeeID");
                    int departmentID = rs.getInt("DepartmentID");
                    LocalDate sentDate = DATE_UTIL.parseSqlDate(rs.getDate("SentDate"));
                    LocalDate startDate = DATE_UTIL.parseSqlDate(rs.getDate("StartDate"));
                    LocalDate endDate = DATE_UTIL.parseSqlDate(rs.getDate("EndDate"));
                    String filePath = rs.getNString("FilePath");
                    String reason = rs.getNString("Reason");
                    Boolean managerApprove = parseBoolean(rs.getString("ManagerApprove"));
                    Boolean hrApprove = parseBoolean(rs.getString("HrApprove"));
                    int managerID = rs.getInt("ManagerID");
                    int hrID = rs.getInt("HrID");
                    Boolean status = parseBoolean(rs.getString("Status"));
                    LeaveRequestDTO lr = new LeaveRequestDTO(leaveRequestID, employeeID, departmentID, sentDate, startDate, endDate, filePath, reason, managerApprove, hrApprove, managerID, hrID, status);
                    list.add(lr);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            } finally {
                closeAll();
            }
        }
        return list;
    }

    public ArrayList<LeaveRequestDTO> getLeaveRequestForHR(int approve) {
        ArrayList<LeaveRequestDTO> list = new ArrayList<>();
//        if (connection != null) {
        try {
            String sql = "SELECT LeaveRequestID ,\n"
                    + "		l.EmployeeID,\n"
                    + "		SentDate,\n"
                    + "		l.StartDate,\n"
                    + "		l.EndDate,\n"
                    + "		FilePath,\n"
                    + "		Reason,\n"
                    + "		ManagerApprove,\n"
                    + "		HrApprove,\n"
                    + "		ManagerID,\n"
                    + "		HrID,\n"
                    + "		[Status],\n"
                    + "		e.DepartmentID\n"
                    + "FROM LeaveRequests l\n"
                    + "JOIN Employees e ON l.EmployeeID = e.EmployeeID\n"
                    + "WHERE l.ManagerApprove like '%" + approve + "%'\n"
                    + "ORDER BY  HrApprove ASC , LeaveRequestID ASC";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int leaveRequestID = rs.getInt("LeaveRequestID");
                int employeeID = rs.getInt("EmployeeID");
                int departmentID = rs.getInt("DepartmentID");
                LocalDate sentDate = DATE_UTIL.parseSqlDate(rs.getDate("SentDate"));
                LocalDate startDate = DATE_UTIL.parseSqlDate(rs.getDate("StartDate"));
                LocalDate endDate = DATE_UTIL.parseSqlDate(rs.getDate("EndDate"));
                String filePath = rs.getString("FilePath");
                String reason = rs.getNString("Reason");
                Boolean managerApprove = parseBoolean(rs.getString("ManagerApprove"));
                Boolean hrApprove = parseBoolean(rs.getString("HrApprove"));
                int managerID = rs.getInt("ManagerID");
                int hrID = rs.getInt("HrID");
                Boolean status = parseBoolean(rs.getString("Status"));

                LeaveRequestDTO lr = new LeaveRequestDTO(leaveRequestID, employeeID, departmentID, sentDate, startDate, endDate, filePath, reason, managerApprove, hrApprove, managerID, hrID, status);
                list.add(lr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            closeAll();
        }
//        }
        return list;
    }

    public boolean approvalOfApplicationByManager(int status, int managerID, int requestID) {
        if (connection != null) {
            try {
                String sql = "UPDATE LeaveRequests\n"
                        + "SET ManagerApprove = ?, ManagerID = ?\n"
                        + "WHERE LeaveRequestID = ?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, status);
                ps.setInt(2, managerID);
                ps.setInt(3, requestID);
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
        }
        return false;
    }

    public boolean approvalOfApplicationByHr(int status, int hrID, int requestID) {
        if (connection != null) {
            try {
                String sql = "UPDATE LeaveRequests\n"
                        + "SET HrApprove = ?, HrID = ?\n"
                        + "WHERE LeaveRequestID = ?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, status);
                ps.setInt(2, hrID);
                ps.setInt(3, requestID);
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
        }
        return false;
    }

    public boolean InsertLeaveRequest(EmployeeDTO e, LocalDate sentDate, LocalDate startDate, LocalDate endDate, String reason, String filePath) {
        if (connection != null) {
            try {
                String sql = "INSERT INTO LeaveRequests(EmployeeID, SentDate, StartDate, EndDate, Reason, FilePath) "
                        + "VALUES (?,?,?,?,?,?) ";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, e.getEmployeeID());
                ps.setString(2, sentDate.toString());
                ps.setString(3, startDate.toString());
                ps.setString(4, endDate.toString());
                ps.setString(5, reason);
                ps.setNString(6, filePath);
                int result = ps.executeUpdate();
                if (result > 0) {
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

    public LeaveRequestDTO getRequestById(int requestID) {
        LeaveRequestDTO request = new LeaveRequestDTO();

        try {
            String sql = "SELECT * FROM LeaveRequests "
                    + "WHERE LeaveRequestID = ? ";
            connect();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, requestID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int leaveRequestID = rs.getInt("LeaveRequestID");
                int employeeID = rs.getInt("EmployeeID");
                LocalDate sentDate = DATE_UTIL.parseSqlDate(rs.getDate("SentDate"));
                LocalDate startDate = DATE_UTIL.parseSqlDate(rs.getDate("StartDate"));
                LocalDate endDate = DATE_UTIL.parseSqlDate(rs.getDate("EndDate"));
                String reason = rs.getNString("Reason");
                String filePath = rs.getNString("filePath");
                Boolean managerApprove = parseBoolean(rs.getString("ManagerApprove"));
                Boolean hrApprove = parseBoolean(rs.getString("HrApprove"));
                int managerID = rs.getInt("ManagerID");
                int hrID = rs.getInt("HrID");
                Boolean status = parseBoolean(rs.getString("Status"));
                request = new LeaveRequestDTO(leaveRequestID, employeeID, sentDate, startDate, endDate, filePath, reason, managerApprove, hrApprove, managerID, hrID, status);
//                    if (request != null) {
//                        return request;
//                    }
                return request;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return null;
    }

    public boolean updateRequestStatus(int requestID) {
        try {
            String sql = "UPDATE LeaveRequests "
                    + "SET Status = 1 "
                    + "WHERE LeaveRequestID = ? ";
            connect();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, requestID);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return false;
    }

    public boolean scheduleLeaveForRequest(LeaveRequestDTO request, int id) {
        try {
            String sql = "INSERT INTO Leaves(EmployeeID, StartDate, EndDate, FilePath, Reason, CreatedDate, CreatedBy) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            connect();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, request.getEmployeeID());
            ps.setString(2, request.getStartDate().toString());
            ps.setString(3, request.getEndDate().toString());
            ps.setNString(4, request.getFilePath());
            ps.setNString(5, request.getReason());
            ps.setString(6, LocalDate.now().toString());
            ps.setInt(7, id);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return false;
    }

    public static void main(String[] args) {
        LeaveRequestDAO dao = new LeaveRequestDAO();
        EmployeeDAO emDao = new EmployeeDAO();
        EmployeeDTO emDTO = new EmployeeDTO();
        EmployeeDTO managerDTO = new EmployeeDTO();
//        ArrayList<LeaveRequestDTO> list1 = dao.getLeaveRequestByDepartment(2);
//        ArrayList<LeaveRequestDTO> list2 = dao.getLeaveRequestForHR(1);
        ArrayList<LeaveRequestDTO> list3 = dao.getLeaveRequestByEmployeeID(3);
//        System.out.println("List size (by departmentID): " + list1.size() + "\n");
//        System.out.println("List size (by HR): " + list2.size() + "\n");
        System.out.println("List size : " + list3.size());

//        for (LeaveRequestDTO leaveRequestDTO : list2) {
//            if (leaveRequestDTO.getManagerApprove() != null && leaveRequestDTO.getManagerApprove()) {
//                System.out.println("true");
//            }
//            leaveRequestDTO.getStatus();
//        }
//        for (LeaveRequestDTO lr : list) {
//            System.out.println(lr.getLeaveRequestID());
//            System.out.println(lr.getEmployeeID());
//            System.out.println(lr.getSentDate());
//            System.out.println(lr.getStartDate());
//            System.out.println(lr.getEndDate());
//            System.out.println(lr.getReason());
//            System.out.println(lr.getManagerApprove());
//            System.out.println(lr.getHrApprove());
//            System.out.println(lr.getManagerID());
//            System.out.println(lr.getHrID());
//            System.out.println(lr.getStatus());
//            System.out.println("\n");
//        }
    }
}
