/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import dbhelper.DBContext;

public class EmployeeDAO extends DBContext {

    public ArrayList<EmployeeDTO> getEmployeeInfo() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeDTO> EmployeeList = new ArrayList<>();
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
                    Date birthDate = rs.getDate("BirthDate");
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
                    EmployeeDTO e = new EmployeeDTO(employeeID, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
                    EmployeeList.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return EmployeeList;
    }

    public EmployeeDTO getEmployeeDTO(int xEmployeeID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                String sql = " SELECT * FROM Employees " +
                        " WHERE employeeID = ?";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, xEmployeeID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int employeeID = rs.getInt("EmployeeID");
                    String firstName = rs.getString("FirstName");
                    String middleName = rs.getString("MiddleName");
                    String lastName = rs.getString("LastName");
                    boolean gender = rs.getBoolean("Gender");
                    Date birthDate = rs.getDate("BirthDate");
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
                    EmployeeDTO e = new EmployeeDTO(employeeID, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
                    return e;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public ArrayList<EmployeeDTO> filterByAJAX(String searchvalue, int departmentID, int typeID, String order) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeDTO> list = new ArrayList<>();
        if (connection != null) {
            try {
                String sql = "SELECT "
                        + "   EmployeeID, "
                        + "   FirstName, "
                        + "   MiddleName, "
                        + "   LastName, "
                        + "   Password, "
                        + "   Gender, "
                        + "   Email, "
                        + "   BirthDate, "
                        + "   CCCD, "
                        + "   StartDate, "
                        + "   EndDate, "
                        + "   isActive, "
                        + "   PhoneNumber, "
                        + "   Employees.DepartmentID AS EmployeeDepartmentID, "
                        + "   RoleID, "
                        + "   Employees.EmployeeTypeID AS TypeID, "
                        + "   Departments.Name AS DepartmentName, "
                        + "   EmployeeTypes.Name AS EmployeeTypeName "
                        + "FROM Employees "
                        + "JOIN Departments ON Employees.DepartmentID = Departments.DepartmentID "
                        + "JOIN EmployeeTypes ON Employees.EmployeeTypeID = EmployeeTypes.EmployeeTypeID "
                        + "WHERE "
                        + "   (FirstName LIKE ? OR MiddleName LIKE ? OR LastName LIKE ? OR CCCD LIKE ? OR Email LIKE ?) "
                        + "   AND Departments.DepartmentID = ? "
                        + "   AND EmployeeTypes.EmployeeTypeID = ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, "%" + searchvalue + "%");
                stm.setString(2, "%" + searchvalue + "%");
                stm.setString(3, "%" + searchvalue + "%");
                stm.setString(4, "%" + searchvalue + "%");
                stm.setString(5, "%" + searchvalue + "%");
                stm.setInt(6, departmentID);
                stm.setInt(7, typeID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int employeeID = rs.getInt("EmployeeID");
                    String firstName = rs.getString("FirstName");
                    String middleName = rs.getString("MiddleName");
                    String lastName = rs.getString("LastName");
                    boolean gender = rs.getBoolean("Gender");
                    Date birthDate = rs.getDate("BirthDate");
                    String email = rs.getString("Email");
                    String password = rs.getString("Password");
                    String cccd = rs.getString("CCCD");
                    String phoneNumber = rs.getString("PhoneNumber");
                    int employeeTypeID = rs.getInt("TypeID");
                    int departID = rs.getInt("EmployeeDepartmentID");
                    int roleID = rs.getInt("RoleID");
                    Date startDate = rs.getDate("StartDate");
                    Date endDate = rs.getDate("EndDate");
                    boolean isActived = rs.getBoolean("isActive");
                    EmployeeDTO e = new EmployeeDTO(employeeID, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departID, roleID, startDate, endDate, isActived);
                    list.add(e);
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
            }

        }
        return list;
    }

    public EmployeeDTO checkAccount(String Mail, String PassWord) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        EmployeeDTO employee = null;
        if (connection != null) {
            try {
                String sql = "SELECT * FROM Employees where Email = ? and Password=?";

                stm = connection.prepareStatement(sql);
                stm.setString(1, Mail);
                stm.setString(2, PassWord);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int employeeID = rs.getInt("EmployeeID");
                    String firstName = rs.getString("FirstName");
                    String middleName = rs.getString("MiddleName");
                    String lastName = rs.getString("LastName");
                    boolean gender = rs.getBoolean("Gender");
                    Date birthDate = rs.getDate("BirthDate");
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
                    EmployeeDTO e = new EmployeeDTO(employeeID, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
                    employee = e;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return employee;
    }

    public EmployeeDTO checkEmail(String Mail) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        EmployeeDTO employee = null;
        if (connection != null) {
            try {
                String sql = "SELECT * FROM Employees where Email = ?";

                stm = connection.prepareStatement(sql);
                stm.setString(1, Mail);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int employeeID = rs.getInt("EmployeeID");
                    String firstName = rs.getString("FirstName");
                    String middleName = rs.getString("MiddleName");
                    String lastName = rs.getString("LastName");
                    boolean gender = rs.getBoolean("Gender");
                    Date birthDate = rs.getDate("BirthDate");
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
                    EmployeeDTO e = new EmployeeDTO(employeeID, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
                    employee = e;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return employee;
    }

    // NguyenManhDuong - Select email
    public String getEmail(String email) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String set_email = "";
        if (connection != null) {
            try {
                String sql = "SELECT Email \n"
                        + "FROM Employees\n"
                        + "WHERE Email = ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    set_email = rs.getString("Email");
                    return set_email;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    // NguyenManhDuong - update password
    public boolean updatePassword(String email, String password) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                String sql = "UPDATE Employees\n"
                        + "SET Password = ?\n"
                        + "WHERE Email = ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, password);
                stm.setString(2, email);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public boolean updateProfileByEmployee(String Phone, int Gender, String Email) {
        PreparedStatement stm = null;

        if (connection != null) {
            try {
                String sql = "update Employees\n"
                        + "set PhoneNumber = ? , "
                        + "Gender = ? "
                        + "where Email = ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, Phone);
                stm.setInt(2, Gender);
                stm.setString(3, Email);
                int a = stm.executeUpdate();
                if (a > 0) {
                    return true;
                }
            } catch (Exception e) {

            }
        }
        return false;
    }
    
    public ArrayList<EmployeeDTO> getEmployeeByDepartment(int phong) {
        ArrayList<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                String sql = "select * from Employees \n"
                        + " where DepartmentID = ?";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, phong);
                rs = stm.executeQuery();
                while(rs.next()){
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
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return list;
    }
    
    public ArrayList<EmployeeDTO> SearchEmployeeByName(String Name) {
        ArrayList<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                String sql = "select * from Employees\n" +
" where LastName like ? or FirstName like ? or MiddleName like ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, "%" + Name +"%");
                stm.setString(2, "%" + Name +"%");
                stm.setString(3, "%" + Name +"%");
                rs = stm.executeQuery();
                while(rs.next()){
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
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return list;
    }
    public int getTotalEmployeeByDepartment(int phong,String search){
         int count =0 ;
         PreparedStatement stm = null;
         ResultSet rs = null;
         if(connection != null){
             try{
                 String sql = "select count(*) as dem from Employees where DepartmentID = ? and (LastName like ? or FirstName like ? or MiddleName like ?) ";
                 stm = connection.prepareStatement(sql);
                 stm.setInt(1, phong);
                 stm.setString(2, "%"+search+"%");
                 stm.setString(3, "%"+search+"%");
                 stm.setString(4, "%"+search+"%");
                 rs = stm.executeQuery();
                 if(rs.next()){
                     count = rs.getInt("dem");
                 }
             }catch(Exception e){
                  e.printStackTrace();
                System.out.println(e.getMessage());
             }
         }
        return count;
    }

    
    public ArrayList<EmployeeDTO> searchAjaxEmployeeByDepartment(int a,int phong,String name){
        ArrayList<EmployeeDTO> list = new ArrayList();
        PreparedStatement stm = null;
         ResultSet rs = null;
         if(connection != null){
             try{
                 String sql = "select * from Employees\n" +
"where DepartmentID=? and (LastName like ? or FirstName like ? or MiddleName like ?)\n" +
"Order by EmployeeID\n" +
"Offset ? rows fetch next 2 rows only";
                 stm = connection.prepareStatement(sql);
                 stm.setInt(1, phong);
                 stm.setString(2, "%"+name+"%");
                 stm.setString(3, "%"+name+"%");
                 stm.setString(4, "%"+name+"%");
                 stm.setInt(5, (a-1)*2);
                 rs = stm.executeQuery();
                 while(rs.next()){
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
                    list.add(e);
                 }
             }catch(Exception e){
                 e.printStackTrace();
                System.out.println(e.getMessage());
             }
         }
        
        return list;
    }

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        System.out.println(dao.getEmail("duong@gmail.com"));
    }

}
