/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dbhelper.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public ArrayList<LeaveRequestDTO> getLeaveRequest() {
        ArrayList<LeaveRequestDTO> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                String sql = "SELECT * FROM LeaveRequest\n"
                        + "  ORDER BY HrApprove ASC, ManagerApprove ASC";
                stm = connection.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int leaveRequestID = rs.getInt("LeaveRequestID");
                    int employeeID = rs.getInt("EmployeeID");
                    LocalDate sentDate = DATE_UTIL.parseSqlDate(rs.getDate("SentDate"));
                    LocalDate startDate = DATE_UTIL.parseSqlDate(rs.getDate("StartDate"));
                    LocalDate endDate = DATE_UTIL.parseSqlDate(rs.getDate("EndDate"));
                    String reason = rs.getNString("Reason");
                    Boolean managerApprove = parseBoolean(rs.getString("ManagerApprove"));
                    Boolean hrApprove = parseBoolean(rs.getString("HrApprove"));
                    int managerID = rs.getInt("ManagerID");
                    int hrID = rs.getInt("HrID");

                    LeaveRequestDTO lr = new LeaveRequestDTO(leaveRequestID, employeeID, sentDate, startDate, endDate, reason, managerApprove, hrApprove, managerID, hrID);
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

    public boolean approvalOfApplicationByManager(int status , int managerID , int requestID) {
        if (connection != null) {
            try {
                String sql = "UPDATE LeaveRequest\n"
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

    public boolean approvalOfApplicationByHr(int status , int hrID , int requestID) {
        if (connection != null) {
            try {
                String sql = "UPDATE LeaveRequest\n"
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
    
    public static void main(String[] args) {
        LeaveRequestDAO dao = new LeaveRequestDAO();
        EmployeeDAO emDao = new EmployeeDAO();
        EmployeeDTO emDTO = new EmployeeDTO();
        EmployeeDTO managerDTO = new EmployeeDTO();
        ArrayList<LeaveRequestDTO> list = dao.getLeaveRequest();
        System.out.println("List size: " + list.size() + "\n");

        for (LeaveRequestDTO lr : list) {
            emDTO = emDao.getEmployeeDTO(lr.getEmployeeID());
            managerDTO = emDao.getEmployeeDTO(lr.getManagerID());
            if(!lr.getManagerApprove()){
                System.out.println("false");
            }
//            if(lr.getManagerID() == 4){
//                System.out.println("Quan li nhan su");
//            }else if(lr.getHrID() == 2){
//                System.out.println("Quan li");
//            }
//            System.out.println(lr.getManagerApprove());
//            System.out.println(lr.getHrApprove());
//            System.out.println(emDTO);
//            if (managerDTO != null) {
//                System.out.println(managerDTO);
//            }
        }
    }
}
