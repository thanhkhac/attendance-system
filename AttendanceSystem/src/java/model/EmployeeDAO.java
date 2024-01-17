/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.DBContext;

public class EmployeeDAO extends DBContext {

    public ArrayList<EmployeeDTO> getEmployeeInfo() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList EmployeeList = new ArrayList();
        if (connection != null) {
            try {
                String sql = " SELECT * FROM Employees ";
                stm = connection.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int employeeID = rs.getInt("EmployeeID");
                    String firstName = rs.getString("FirstName");
                    String middleName = rs.getString("MiddleName");
                    String lastName = rs.getString("LastName");
                    boolean gender = rs.getBoolean("Gender");
                    String email = rs.getString("Email");
                    String password = rs.getString("Password");
                    String cccd = rs.getString("CCCD");
                    String phoneNumber = rs.getString("PhoneNumber");
                    int employeeTypeID = rs.getInt("EmployeeTypeID");
                    int departmentID = rs.getInt("DepartmentID");
                    int roleID = rs.getInt("RoleID");
                    Date startDate = rs.getDate("StartDate");
                    Date endDate = rs.getDate("EndDate");
                    boolean isActived = rs.getBoolean("isActive");
                    EmployeeDTO e = new EmployeeDTO(employeeID, firstName, middleName, lastName, startDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
                    EmployeeList.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return EmployeeList;
    }
    public Boolean checkAccount(String Mail,String PassWord){
        PreparedStatement stm = null;
        ResultSet rs = null;
        if(connection!=null){
            try{
                String sql = "SELECT * FROM Employees where Email = ? and Password=?";
                
                stm = connection.prepareStatement(sql);
                stm.setString(1, Mail);
                stm.setString(2,PassWord);
                rs = stm.executeQuery();
                if(rs.next())
                    return true;
            }catch(Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

}