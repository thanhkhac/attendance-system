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
                        + "   Employees.RoleID, "
                        + "   Roles.Name AS RoleName, "
                        + "   Employees.EmployeeTypeID AS TypeID, "
                        + "   Departments.Name AS DepartmentName, "
                        + "   EmployeeTypes.Name AS EmployeeTypeName "
                        + "FROM Employees "
                        + "JOIN Departments ON Employees.DepartmentID = Departments.DepartmentID "
                        + "JOIN EmployeeTypes ON Employees.EmployeeTypeID = EmployeeTypes.EmployeeTypeID "
                        + "JOIN Roles ON Employees.RoleID = Roles.RoleID ";
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

    public ArrayList<EmployeeGeneral> filterByAJAX(String searchvalue, String departmentID, String typeID, String order) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeGeneral> list = new ArrayList<>();

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
                        + "   Employees.RoleID, "
                        + "   Roles.Name AS RoleName, "
                        + "   Employees.EmployeeTypeID AS TypeID, "
                        + "   Departments.Name AS DepartmentName, "
                        + "   EmployeeTypes.Name AS EmployeeTypeName "
                        + "FROM Employees "
                        + "JOIN Departments ON Employees.DepartmentID = Departments.DepartmentID "
                        + "JOIN EmployeeTypes ON Employees.EmployeeTypeID = EmployeeTypes.EmployeeTypeID "
                        + "JOIN Roles ON Employees.RoleID = Roles.RoleID "
                        + "WHERE "
                        + "   ( FirstName LIKE ? OR MiddleName LIKE ? OR LastName LIKE ? ) "
                        + "   AND Departments.DepartmentID = ? "
                        + "   AND EmployeeTypes.EmployeeTypeID = ? "
                        + "ORDER BY FirstName " + order;
                stm = connection.prepareStatement(sql);
                stm.setString(1, "%" + searchvalue + "%");
                stm.setString(2, "%" + searchvalue + "%");
                stm.setString(3, "%" + searchvalue + "%");
                stm.setInt(4, Integer.parseInt(departmentID));
                stm.setInt(5, Integer.parseInt(typeID));
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

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        System.out.println(dao.getEmail("duong@gmail.com"));
    }

}
