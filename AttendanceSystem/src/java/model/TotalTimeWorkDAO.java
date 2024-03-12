/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dbhelper.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class TotalTimeWorkDAO extends DBContext {
    
    public ArrayList<TotalTimeWorkDTO> getTotalTimeWork(int id, String start,String end){
        connect();
        ArrayList<TotalTimeWorkDTO> list = new ArrayList<TotalTimeWorkDTO>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        if(connection != null){
            try{
                
                String sql = "WITH AllDates AS (\n" +
"    SELECT DATEADD(DAY, number, ?) AS Date\n" +
"    FROM master..spt_values\n" +
"    WHERE type = 'P'\n" +
"    AND DATEADD(DAY, number, ?) <= ?\n" +
")\n" +
"\n" +
"SELECT CONVERT(DATE, AllDates.Date) AS Date, COALESCE(SUM(ISNULL(DATEDIFF(MINUTE, CheckIn, CheckOut), 0)), 0) AS TotalTimeWork\n" +
"FROM AllDates\n" +
"LEFT JOIN Timesheet ON CONVERT(DATE, AllDates.Date) = CONVERT(DATE, Timesheet.Date)\n" +
"AND EmployeeID = ?\n" +
"GROUP BY CONVERT(DATE, AllDates.Date) "
                        + "Order by  Date;";
                stm = connection.prepareStatement(sql);
                stm.setString(1, start);
                stm.setString(2, start);
                stm.setString(3, end);
                stm.setInt(4, id);
                rs = stm.executeQuery();
                while(rs.next()){
                    String Date = rs.getString("Date");
                    int total = rs.getInt("TotalTimeWork");
                    list.add(new TotalTimeWorkDTO(Date,total));
                }
            }catch(Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            finally{
                close();
            }
        }
        return list;
    }
    
    
    public ArrayList<TotalTimeWorkDTO> getTotalOvertime(int id, String start,String end){
        connect();
        ArrayList<TotalTimeWorkDTO> list = new ArrayList<TotalTimeWorkDTO>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        if(connection != null){
            try{
                
                String sql = "WITH AllDates AS (\n" +
"    SELECT DATEADD(DAY, number, ?) AS Date\n" +
"    FROM master..spt_values\n" +
"    WHERE type = 'P'\n" +
"    AND DATEADD(DAY, number, ?) <= ?\n" +
")\n" +
"\n" +
"SELECT CONVERT(DATE, AllDates.Date) AS Date, COALESCE(SUM(ISNULL(DATEDIFF(MINUTE, CheckIn, CheckOut), 0)), 0) AS TotalOverTime\n" +
"FROM AllDates\n" +
"LEFT JOIN Overtimes ON CONVERT(DATE, AllDates.Date) = CONVERT(DATE, Overtimes.Date)\n" +
"AND EmployeeID = ?\n" +
"GROUP BY CONVERT(DATE, AllDates.Date) "
                        + "Order by  Date;";
                stm = connection.prepareStatement(sql);
                stm.setString(1, start);
                stm.setString(2, start);
                stm.setString(3, end);
                stm.setInt(4, id);
                rs = stm.executeQuery();
                while(rs.next()){
                    String Date = rs.getString("Date");
                    int total = rs.getInt("TotalOverTime");
                    list.add(new TotalTimeWorkDTO(Date,total));
                }
            }catch(Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            finally{
                close();
            }
        }
        return list;
    }
    
    public static void main(String[] args) {
//        TotalTimeWorkDAO dao = new TotalTimeWorkDAO();
//        ArrayList<TotalTimeWorkDTO> list = dao.getTotalOvertime(1, "2024-02-01", "2024-04-10");
//        for(TotalTimeWorkDTO x: list){
//            System.out.println(x.getTotalTime());
//        }
    }
    
}
