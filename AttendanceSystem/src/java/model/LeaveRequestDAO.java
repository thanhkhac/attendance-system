/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

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
public class LeaveRequestDAO extends DBContext{
    static final DateTimeUtil DATE_UTIL = new DateTimeUtil();
    
    public ArrayList<LeaveRequestDTO> getLeaveRequest(){
        ArrayList<LeaveRequestDTO> list = new ArrayList();
        PreparedStatement stm = null;
        ResultSet rs = null;
        if(connection != null){
            try{
                String sql = "SELECT * FROM LeaveRequest";
                stm = connection.prepareStatement(sql);
                rs = stm.executeQuery();
                while(rs.next()){
                    int leaveRequestID =  rs.getInt("LeaveRequestID");
                    int employeeID = rs.getInt("EmployeeID");
                    LocalDate sentDate = DATE_UTIL.parseSqlDate(rs.getDate("SentDate"));
                    LocalDate startDate = DATE_UTIL.parseSqlDate(rs.getDate("StartDate"));
                    LocalDate endDate = DATE_UTIL.parseSqlDate(rs.getDate("EndDate"));
                    String reason = rs.getNString("Reason");
                    boolean managerApprove = rs.getBoolean("ManagerApprove");
                    boolean hrApprove = rs.getBoolean("HrApprove");
                    int managerID = rs.getInt("ManagerID");
                    int hrID = rs.getInt("HrID");
                    
                    LeaveRequestDTO lr = new LeaveRequestDTO(leaveRequestID, employeeID, sentDate, startDate, endDate, reason, managerApprove, hrApprove, managerID, hrID);
                    list.add(lr);
                }
            }catch(Exception e){
                e.printStackTrace();
                System.out.println(e);
            }
        }
        return list;
    }
    
    
    
    public static void main(String[] args) {
        LeaveRequestDAO dao = new LeaveRequestDAO();
        ArrayList<LeaveRequestDTO> list = dao.getLeaveRequest();
//        System.out.println(list.size());
        
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
        System.out.println("\n");
    }
}
