/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dbhelper.DAOBase;
import dbhelper.DBContext;
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

    public ArrayList<LeaveRequestDTO> getLeaveRequest() {
        ArrayList<LeaveRequestDTO> list = new ArrayList();
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                String sql = "SELECT * FROM LeaveRequest";
                stm = connection.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int leaveRequestID = rs.getInt("LeaveRequestID");
                    int employeeID = rs.getInt("EmployeeID");
                    LocalDate sentDate = DATE_UTIL.parseSqlDate(rs.getDate("SentDate"));
                    LocalDate startDate = DATE_UTIL.parseSqlDate(rs.getDate("StartDate"));
                    LocalDate endDate = DATE_UTIL.parseSqlDate(rs.getDate("EndDate"));
                    String reason = rs.getNString("Reason");
                    Boolean managerApprove = rs.getString("ManagerApprove") != null ? Boolean.valueOf(rs.getString("ManagerApprove")) : null;
                    Boolean hrApprove = rs.getString("HrApprove") != null ? Boolean.valueOf(rs.getString("HrApprove")) : null;
                    int managerID = rs.getInt("ManagerID");
                    int hrID = rs.getInt("HrID");

                    LeaveRequestDTO lr = new LeaveRequestDTO(leaveRequestID, employeeID, sentDate, startDate, endDate, reason, managerApprove, hrApprove, managerID, hrID);
                    list.add(lr);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
        return list;
    }

    public boolean InsertLeaveRequest(EmployeeDTO e, LocalDate sentDate, LocalDate startDate, LocalDate endDate, String reason) {
        if (con != null) {
            try {
                String sql = "INSERT INTO LeaveRequest(EmployeeID, SentDate, StartDate, EndDate, Reason) "
                        + "VALUES (?,?,?,?,?) ";
                ps = con.prepareStatement(sql);
                ps.setInt(1, e.getEmployeeID());
                ps.setString(2, sentDate.toString());
                ps.setString(3, startDate.toString());
                ps.setString(4, endDate.toString());
                ps.setString(5, reason);
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

    public static void main(String[] args) {
        LeaveRequestDAO dao = new LeaveRequestDAO();
        ArrayList<LeaveRequestDTO> list = dao.getLeaveRequest();
        System.out.println("List size: " + list.size() + "\n");

        for (LeaveRequestDTO lr : list) {
            System.out.println(lr.getLeaveRequestID());
            System.out.println(lr.getEmployeeID());
            System.out.println(lr.getSentDate());
            System.out.println(lr.getStartDate());
            System.out.println(lr.getEndDate());
            System.out.println(lr.getReason());
            System.out.println(lr.getManagerApprove());
            System.out.println(lr.getHrApprove());
            System.out.println(lr.getManagerID());
            System.out.println(lr.getHrID());
            System.out.println("\n");
        }
    }
}
