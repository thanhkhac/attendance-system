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
import java.sql.SQLException;
import java.time.LocalDate;
import ultility.datetimeutil.DateTimeUtil;

public class EmployeeDAO extends DBContext {

    final public DateTimeUtil DATE_TIME_UTIL = new DateTimeUtil();

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
                    set_email = rs.getNString("Email");
                    return set_email;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    // NguyenManhDuong - select cccd
    public String getCCCD(String CCCD) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String set_cccd = "";
        if (connection != null) {
            try {
                String sql = "SELECT CCCD\n"
                        + "FROM Employees\n"
                        + "WHERE CCCD = ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, CCCD);
                rs = stm.executeQuery();
                if (rs.next()) {
                    set_cccd = rs.getString("CCCD");
                    return set_cccd;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    
    // NguyenManhDuong - select phonenumber
    public String getPhonenumber(String phonenumber) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String set_phonenumbe = "";
        if (connection != null) {
            try {
                String sql = "SELECT PhoneNumber\n"
                        + "FROM Employees\n"
                        + "WHERE PhoneNumber = ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, phonenumber);
                rs = stm.executeQuery();
                if (rs.next()) {
                    set_phonenumbe = rs.getString("PhoneNumber");
                    return set_phonenumbe;
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
                stm.setNString(1, password);
                stm.setNString(2, email);
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
                e.printStackTrace();
                System.out.println(e);
            }
        }
        return false;
    }

    // NguyenManhDuong - insert employee
    public boolean insertEmployee(String firstName, String middleName, String lastName, boolean gender, LocalDate birthDate, String email, String password,
            String CCCD, String phonenumber, int employeeTypeID, int departmentID, int roleID, LocalDate startDate, LocalDate endDate, boolean isActive) {
        PreparedStatement stm = null;
        if (connection != null) {
            try {
                String sql = "INSERT INTO Employees(FirstName, MiddleName, LastName, Gender, BirthDate, Email, [Password], CCCD, PhoneNumber, EmployeeTypeID, DepartmentID, RoleID, StartDate, EndDate , IsActive) "
                        + "VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )";
                stm = connection.prepareStatement(sql);

                stm.setNString(1, firstName);
                stm.setNString(2, middleName);
                stm.setNString(3, lastName);
                stm.setBoolean(4, gender);
                stm.setString(5, birthDate.toString());
                stm.setNString(6, email);
                stm.setNString(7, password);
                stm.setNString(8, CCCD);
                stm.setNString(9, phonenumber);
                stm.setInt(10, employeeTypeID);
                stm.setInt(11, departmentID);
                stm.setInt(12, roleID);
                stm.setString(13, startDate.toString());
                stm.setString(14, endDate.toString());
                stm.setBoolean(15, isActive);

                int row = stm.executeUpdate();
                System.out.println(row);
                if (row > 0) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        
        //test ham lay ttin
        System.out.println(dao.getEmail("nguyennduongg039@gmail.com"));
        System.out.println(dao.getCCCD("0000000"));
        System.out.println(dao.getPhonenumber("454545454"));
    }

}
