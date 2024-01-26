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

    public ArrayList<EmployeeGeneral> getEmployeeInfo() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeGeneral> list = new ArrayList();
        if (connection != null) {
            try {
                String sql = "SELECT " +
                        "   EmployeeID, " +
                        "   FirstName, " +
                        "   MiddleName, " +
                        "   LastName, " +
                        "   Password, " +
                        "   Gender, " +
                        "   Email, " +
                        "   BirthDate, " +
                        "   CCCD, " +
                        "   StartDate, " +
                        "   EndDate, " +
                        "   isActive, " +
                        "   PhoneNumber, " +
                        "   Employees.DepartmentID AS EmployeeDepartmentID, " +
                        "   Employees.RoleID, " +
                        "   Roles.Name AS RoleName, " +
                        "   Employees.EmployeeTypeID AS TypeID, " +
                        "   Departments.Name AS DepartmentName, " +
                        "   EmployeeTypes.Name AS EmployeeTypeName " +
                        "FROM Employees " +
                        "JOIN Departments ON Employees.DepartmentID = Departments.DepartmentID " +
                        "JOIN EmployeeTypes ON Employees.EmployeeTypeID = EmployeeTypes.EmployeeTypeID " +
                        "JOIN Roles ON Employees.RoleID = Roles.RoleID ";
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
                    int employeeTypeID = rs.getInt("TypeID");
                    String employeeTypeName = rs.getString("EmployeeTypeName");
                    int departID = rs.getInt("EmployeeDepartmentID");
                    String departmentName = rs.getString("DepartmentName");
                    int roleID = rs.getInt("RoleID");
                    String roleName = rs.getString("RoleName");
                    Date startDate = rs.getDate("StartDate");
                    Date endDate = rs.getDate("EndDate");
                    boolean isActived = rs.getBoolean("isActive");
                    EmployeeGeneral e = new EmployeeGeneral(employeeID,
                            firstName, middleName, lastName, birthDate,
                            gender, email, password, cccd, phoneNumber,
                            employeeTypeID, employeeTypeName, departID,
                            departmentName, roleID, roleName, startDate,
                            endDate, isActived);
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return list;
    }

    public EmployeeDTO getEmployeeDTO(int xEmployeeID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                String sql = "SELECT * FROM Employees where employeeID = ?";
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

    public ArrayList<EmployeeGeneral> filterByAJAX(String searchvalue, String departmentID_raw, String typeID_raw, String order_raw) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeGeneral> list = new ArrayList<>();
        String order = "";
        String queryDepartmentID = " AND Departments.DepartmentID = ? ";
        String queryTypeID = " AND EmployeeTypes.EmployeeTypeID = ? ";
//        int departmentID = 0;
        switch (order_raw) {
            case "1": {
                order = "ORDER BY FirstName ASC";
                break;
            }
            case "2": {
                order = "ORDER BY FirstName DESC";
                break;
            }
            default: {
                order = "";
                break;
            }
        }

        if (connection != null) {
            try {
                if (departmentID_raw.equals("0")) {
                    queryDepartmentID = "";
                } else {
                    queryDepartmentID = " AND Departments.DepartmentID = " + departmentID_raw;
                }
                if (typeID_raw.equals("0")) {
                    queryTypeID = "";
                } else {
                    queryTypeID = " AND EmployeeTypes.EmployeeTypeID = " + typeID_raw;
                }
                String sql = "SELECT " +
                        "   EmployeeID, " +
                        "   FirstName, " +
                        "   MiddleName, " +
                        "   LastName, " +
                        "   Password, " +
                        "   Gender, " +
                        "   Email, " +
                        "   BirthDate, " +
                        "   CCCD, " +
                        "   StartDate, " +
                        "   EndDate, " +
                        "   isActive, " +
                        "   PhoneNumber, " +
                        "   Employees.DepartmentID AS EmployeeDepartmentID, " +
                        "   Employees.RoleID, " +
                        "   Roles.Name AS RoleName, " +
                        "   Employees.EmployeeTypeID AS TypeID, " +
                        "   Departments.Name AS DepartmentName, " +
                        "   EmployeeTypes.Name AS EmployeeTypeName " +
                        "FROM Employees " +
                        "JOIN Departments ON Employees.DepartmentID = Departments.DepartmentID " +
                        "JOIN EmployeeTypes ON Employees.EmployeeTypeID = EmployeeTypes.EmployeeTypeID " +
                        "JOIN Roles ON Employees.RoleID = Roles.RoleID " +
                        "WHERE " +
                        "   ( FirstName LIKE ? OR MiddleName LIKE ? OR LastName LIKE ? ) " +
                        queryDepartmentID +
                        queryTypeID +
                        order;
                stm = connection.prepareStatement(sql);
                stm.setNString(1, "%" + searchvalue + "%");
                stm.setNString(2, "%" + searchvalue + "%");
                stm.setNString(3, "%" + searchvalue + "%");

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
                    String employeeTypeName = rs.getString("EmployeeTypeName");
                    int departID = rs.getInt("EmployeeDepartmentID");
                    String departmentName = rs.getString("DepartmentName");
                    int roleID = rs.getInt("RoleID");
                    String roleName = rs.getString("RoleName");
                    Date startDate = rs.getDate("StartDate");
                    Date endDate = rs.getDate("EndDate");
                    boolean isActived = rs.getBoolean("isActive");
                    EmployeeGeneral e = new EmployeeGeneral(employeeID,
                            firstName, middleName, lastName, birthDate,
                            gender, email, password, cccd, phoneNumber,
                            employeeTypeID, employeeTypeName, departID,
                            departmentName, roleID, roleName, startDate,
                            endDate, isActived);
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
                String sql = "SELECT Email \n" +
                        "FROM Employees\n" +
                        "WHERE Email = ?";
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
                String sql = "UPDATE Employees\n" +
                        "SET Password = ?\n" +
                        "WHERE Email = ?";
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
                String sql = "update Employees\n" +
                        "set PhoneNumber = ? , " +
                        "Gender = ? " +
                        "where Email = ?";
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
                String sql = "select * from Employees \n" +
                        " where DepartmentID = ?";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, phong);
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
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return list;
    }
 public EmployeeTypeDTO getEmployeeType(int employeeTypeID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                String sql = "Select * from EmployeeTypes\n"
                        + "where EmployeeTypeID = ?";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, employeeTypeID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    EmployeeTypeDTO dto = new EmployeeTypeDTO(
                            rs.getInt("EmployeeTypeID"),
                            rs.getString("Name"));
                    return dto;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return null;
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
                stm.setString(1, "%" + Name + "%");
                stm.setString(2, "%" + Name + "%");
                stm.setString(3, "%" + Name + "%");
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
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return list;
    }

    public int getTotalEmployeeByDepartment(int phong, String search) {
        int count = 0;
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                String sql = "select count(*) as dem from Employees where DepartmentID = ? and (LastName like ? or FirstName like ? or MiddleName like ?) ";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, phong);
                stm.setString(2, "%" + search + "%");
                stm.setString(3, "%" + search + "%");
                stm.setString(4, "%" + search + "%");
                rs = stm.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("dem");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return count;
    }

    public ArrayList<EmployeeDTO> searchAjaxEmployeeByDepartment(int a, int phong, String name,int ca) {
        ArrayList<EmployeeDTO> list = new ArrayList();
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                if(ca>0){
                String sql ="select * from Employees\n" +
"join Timesheet on Timesheet.EmployeeID = Employees.EmployeeID\n" +
"join Shifts on Timesheet.ShiftID = Shifts.ShiftID\n" +
"where DepartmentID=? and (LastName like ? or FirstName like ? or MiddleName like ?) and Shifts.ShiftID = ?\n" +
"Order by Employees.EmployeeID\n" +
"Offset ? rows fetch next 2 rows only";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, phong);
                stm.setString(2, "%" + name + "%");
                stm.setString(3, "%" + name + "%");
                stm.setString(4, "%" + name + "%");
                stm.setInt(5, ca);
                stm.setInt(6, (a - 1) * 2);
                rs = stm.executeQuery();
                }else{
                    String sql ="select * from Employees\n" +
"where DepartmentID=? and (LastName like ? or FirstName like ? or MiddleName like ?)\n" +
"Order by EmployeeID\n" +
"Offset ? rows fetch next 2 rows only";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, phong);
                stm.setString(2, "%" + name + "%");
                stm.setString(3, "%" + name + "%");
                stm.setString(4, "%" + name + "%");
                stm.setInt(5, (a - 1) * 2);
                rs = stm.executeQuery();
                }
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
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        return list;
    }

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        System.out.println("getEmployeeDTO");
        System.out.println(dao.getEmployeeDTO(1));
//        System.out.println(dao.getEmail("duong@gmail.com"));
        
    }

}
