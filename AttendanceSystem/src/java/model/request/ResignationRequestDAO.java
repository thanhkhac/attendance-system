/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.request;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import model.EmployeeDTO;
import ultility.datetimeutil.DateTimeUtil;

/**
 *
 * @author admin
 */
public class ResignationRequestDAO extends dbhelper.DAOBase {

    static final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    public boolean insertResignationRequest(EmployeeDTO e, LocalDate sentDate, LocalDate extendDate, String reason) {
        if (con != null) {
            try {
                String sql = "INSERT INTO ResignationRequests(EmployeeID, SentDate, StartDateContract, EndDateContract, ExtendDate, reason) "
                        + "VALUES (?, ?, ?, ?, ? ,?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, e.getEmployeeID());
                ps.setString(2, sentDate.toString());
                ps.setString(3, e.getStartDate().toString());
                ps.setString(4, e.getEndDate().toString());
                ps.setString(5, extendDate.toString());
                ps.setNString(6, reason);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            } finally {
                closeAll();
            }
        }
        return false;
    }

    public ArrayList<ResignationRequestDTO> getRegisnationRequestByDepartment(int departmentID) {
        LeaveRequestDAO dao = new LeaveRequestDAO();
        ArrayList<ResignationRequestDTO> list = new ArrayList<>();
        if (connection != null) {
            try {
                String sql = "SELECT ResignationRequestID,\n"
                        + "	rr.EmployeeID,\n"
                        + "	StartDateContract,\n"
                        + "	EndDateContract,\n"
                        + "	SentDate,\n"
                        + "	ExtendDate,\n"
                        + "	Reason,\n"
                        + "	ManagerApprove,\n"
                        + "	HrApprove,\n"
                        + "	ManagerID,\n"
                        + "	HrID,\n"
                        + "	[Status],\n"
                        + "	e.DepartmentID\n"
                        + "FROM ResignationRequests rr\n"
                        + "JOIN Employees e ON rr.EmployeeID = e.EmployeeID\n"
                        + "WHERE e.DepartmentID like '%" + departmentID + "%'\n"
                        + "ORDER BY HrApprove ASC , ManagerApprove ASC";
                ps = connection.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int resignation = rs.getInt("ResignationRequestID");
                    int employeeID = rs.getInt("EmployeeID");
//                    int departmentID = rs.getInt("DepartmentID");
                    LocalDate startDateContract = DATE_UTIL.parseSqlDate(rs.getDate("StartDateContract"));
                    LocalDate endDateContract = DATE_UTIL.parseSqlDate(rs.getDate("EndDateContract"));
                    LocalDate sentDate = DATE_UTIL.parseSqlDate(rs.getDate("SentDate"));
                    LocalDate extendDate = DATE_UTIL.parseSqlDate(rs.getDate("ExtendDate"));
                    String reason = rs.getString("Reason");
                    Boolean managerApprove = dao.parseBoolean(rs.getString("ManagerApprove"));
                    Boolean hrApprove = dao.parseBoolean(rs.getString("HrApprove"));
                    int managerID = rs.getInt("ManagerID");
                    int hrID = rs.getInt("HrID");
                    Boolean status = dao.parseBoolean(rs.getString("Status"));

                    ResignationRequestDTO resign = new ResignationRequestDTO(resignation, employeeID, departmentID, startDateContract, endDateContract, sentDate, extendDate, reason, managerApprove, hrApprove, managerID, hrID, status);
                    list.add(resign);
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

    public ArrayList<ResignationRequestDTO> getRegisnationRequestForHR(int approve) {
        LeaveRequestDAO dao = new LeaveRequestDAO();
        ArrayList<ResignationRequestDTO> list = new ArrayList<>();
        if (connection != null) {
            try {
                String sql = "SELECT ResignationRequestID,\n"
                        + "	rr.EmployeeID,\n"
                        + "	StartDateContract,\n"
                        + "	EndDateContract,\n"
                        + "	SentDate,\n"
                        + "	ExtendDate,\n"
                        + "	Reason,\n"
                        + "	ManagerApprove,\n"
                        + "	HrApprove,\n"
                        + "	ManagerID,\n"
                        + "	HrID,\n"
                        + "	[Status],\n"
                        + "	e.DepartmentID\n"
                        + "FROM ResignationRequests rr\n"
                        + "JOIN Employees e ON rr.EmployeeID = e.EmployeeID\n"
                        + "WHERE rr.ManagerApprove like '%" + approve + "%'\n"
                        + "ORDER BY HrApprove ASC , ManagerApprove ASC";
                ps = connection.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int resignation = rs.getInt("ResignationRequestID");
                    int employeeID = rs.getInt("EmployeeID");
                    int departmentID = rs.getInt("DepartmentID");
                    LocalDate startDateContract = DATE_UTIL.parseSqlDate(rs.getDate("StartDateContract"));
                    LocalDate endDateContract = DATE_UTIL.parseSqlDate(rs.getDate("EndDateContract"));
                    LocalDate sentDate = DATE_UTIL.parseSqlDate(rs.getDate("SentDate"));
                    LocalDate extendDate = DATE_UTIL.parseSqlDate(rs.getDate("ExtendDate"));
                    String reason = rs.getString("Reason");
                    Boolean managerApprove = dao.parseBoolean(rs.getString("ManagerApprove"));
                    Boolean hrApprove = dao.parseBoolean(rs.getString("HrApprove"));
                    int managerID = rs.getInt("ManagerID");
                    int hrID = rs.getInt("HrID");
                    Boolean status = dao.parseBoolean(rs.getString("Status"));

                    ResignationRequestDTO resign = new ResignationRequestDTO(resignation, employeeID, departmentID, startDateContract, endDateContract, sentDate, extendDate, reason, managerApprove, hrApprove, managerID, hrID, status);
                    list.add(resign);
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
    
    public boolean approvalOfResignationByManager(int status, int managerID, int requestID) {
        if (connection != null) {
            try {
                String sql = "UPDATE ResignationRequests \n"
                        + "SET ManagerApprove = ? , ManagerID = ? \n"
                        + "WHERE ResignationRequestID = ?";
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

    public boolean approvalOfResignationByHR(int status, int managerID, int requestID) {
        if (connection != null) {
            try {
                String sql = "Update ResignationRequests \n"
                        + "set HrApprove = ? , HrID = ? \n"
                        + "where ResignationRequestID = ?";
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

    public static void main(String[] args) {
        ResignationRequestDAO rrDao = new ResignationRequestDAO();
        ArrayList<ResignationRequestDTO> list = rrDao.getRegisnationRequestForHR(1);
        System.out.println("List size : " + list.size());

    }
}
