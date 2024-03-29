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
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import ultility.datetimeutil.DateTimeUtil;

public class EmployeeDAO extends DBContext {

    final public DateTimeUtil DATE_TIME_UTIL = new DateTimeUtil();

    public ArrayList<EmployeeGeneral> getEmployeeInfo() {
        connect();
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
            } finally {
                close();
            }
        }
        return list;
    }

    public EmployeeDTO getEmployeeDTO(int xEmployeeID) {
        connect();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Employees where employeeID = ?";
            connect();
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
        } finally {
            close();
        }
        return null;
    }

    public ArrayList<EmployeeGeneral> filterByAJAX(String searchvalue, String departmentID_raw, String typeID_raw, String order_raw) {
        connect();
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
                        + queryDepartmentID
                        + queryTypeID
                        + order;
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
                close();
            }

        }

        return list;
    }

    public EmployeeDTO checkAccount(String Mail, String PassWord) {
        connect();
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
            } finally {
                close();
            }
        }
        return employee;
    }

    public EmployeeDTO checkEmail(String Mail) {
        connect();
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
            } finally {
                close();
            }
        }
        return employee;
    }

    // NguyenManhDuong - Select email
    public String getEmail(String email) {
        connect();
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
            } finally {
                close();
            }
        }

        return null;
    }

    // NguyenManhDuong - select cccd
    public String getCCCD(String CCCD) {
        connect();
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
            } finally {
                close();
            }
        }

        return null;
    }

    // NguyenManhDuong - select phonenumber
    public String getPhonenumber(String phonenumber) {
        connect();
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
            } finally {
                close();
            }
        }
        return null;
    }

    // NguyenManhDuong - update password
    public boolean updatePassword(String email, String password) {
        connect();
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
            } finally {
                close();
            }

        }
        return false;
    }

    public boolean updateProfileByEmployee(String Phone, int Gender, String Email) {
        connect();
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
            } finally {
                close();
            }
        }
        return false;
    }

    public boolean UpdateEmployeeByHR(String firstName, String middleName,
            String lastName, Boolean gender, String cccd, String phoneNumber,
            String email, String password, String birthDate, int departmentID,
            int typeID, int roleID, int employeeID, Boolean isActive) {
        connect();
        PreparedStatement stm = null;

        if (connection != null) {
            try {
                String sql = "UPDATE Employees "
                        + "SET FirstName = ? , "
                        + "MiddleName = ? , "
                        + "LastName = ? , "
                        + "Gender = ? , "
                        + "Email = ? , "
                        + "PhoneNumber = ? , "
                        + "CCCD = ? , "
                        + "BirthDate = ? , "
                        + "Password = ? , "
                        + "DepartmentID = ? , "
                        + "EmployeeTypeID = ? , "
                        + "RoleID = ?, "
                        + "IsActive = ? "
                        + "WHERE EmployeeID = ? ";
                stm = connection.prepareStatement(sql);
                stm.setString(1, firstName);
                stm.setString(2, middleName);
                stm.setString(3, lastName);
                stm.setBoolean(4, gender);
                stm.setString(5, email);
                stm.setString(6, phoneNumber);
                stm.setString(7, cccd);
                stm.setString(8, birthDate);
                stm.setString(9, password);
                stm.setInt(10, departmentID);
                stm.setInt(11, typeID);
                stm.setInt(12, roleID);
                stm.setBoolean(13, isActive);
                stm.setInt(14, employeeID);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                close();
            }
        }
        return false;
    }

    // NguyenManhDuong - insert employee
    public boolean insertEmployee(String firstName, String middleName, String lastName, boolean gender, LocalDate birthDate, String email, String password,
            String CCCD, String phonenumber, int employeeTypeID, int departmentID, int roleID, LocalDate startDate, LocalDate endDate, boolean isActive) {
        connect();
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
            } finally {
                close();
            }
        }
        return false;
    }

    public ArrayList<EmployeeDTO> getEmployeeByDepartment(int phong) {
        connect();
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
            } finally {
                close();
            }
        }
        return list;
    }

    public EmployeeTypeDTO getEmployeeType(int employeeTypeID) {
        connect();
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
            } finally {
                close();
            }
        }
        return null;
    }

    public ArrayList<EmployeeDTO> SearchEmployeeByName(String Name) {
        connect();
        ArrayList<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                String sql = "select * from Employees\n"
                        + " where LastName like ? or FirstName like ? or MiddleName like ?";
                stm = connection.prepareStatement(sql);
                stm.setNString(1, "%" + Name + "%");
                stm.setNString(2, "%" + Name + "%");
                stm.setNString(3, "%" + Name + "%");
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
            } finally {
                close();
            }
        }
        return list;
    }

    public int getTotalEmployeeByDepartment(int phong, String search, int Ca) {
        connect();
        int count = 0;
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                if (Ca > 0) {
                    String sql = "select count(distinct Employees.EmployeeID) as dem from Employees\n"
                            + "join Timesheet on Timesheet.EmployeeID = Employees.EmployeeID\n"
                            + "where DepartmentID=? and (LastName like ? or FirstName like ? or MiddleName like ?) and Timesheet.ShiftID = ?";
                    stm = connection.prepareStatement(sql);
                    stm.setInt(1, phong);
                    stm.setString(2, "%" + search + "%");
                    stm.setString(3, "%" + search + "%");
                    stm.setString(4, "%" + search + "%");
                    stm.setInt(5, Ca);
                } else {
                    String sql = "select count(distinct Employees.EmployeeID) as dem from Employees\n"
                            + "where DepartmentID=? and (LastName like ? or FirstName like ? or MiddleName like ?) ";
                    stm = connection.prepareStatement(sql);
                    stm.setInt(1, phong);
                    stm.setString(2, "%" + search + "%");
                    stm.setString(3, "%" + search + "%");
                    stm.setString(4, "%" + search + "%");
                }
                rs = stm.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("dem");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                close();
            }
        }
        return count;
    }

    public ArrayList<EmployeeDTO> searchAjaxEmployeeByDepartment(int a, int phong, String name, int ca) {
        connect();
        ArrayList<EmployeeDTO> list = new ArrayList();
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                if (ca > 0) {

                    String sql = "select distinct Employees.EmployeeID,Email,Employees.BirthDate,Employees.FirstName,Employees.LastName\n"
                            + ",Employees.MiddleName,Employees.Gender,Employees.DepartmentID,Employees.StartDate,Employees.EndDate,Employees.EmployeeTypeID,\n"
                            + "Employees.Password,Employees.IsActive,Employees.CCCD,Employees.PhoneNumber,Employees.RoleID from Employees\n"
                            + "join Timesheet on Timesheet.EmployeeID = Employees.EmployeeID\n"
                            + "join Shifts on Timesheet.ShiftID = Shifts.ShiftID\n"
                            + "where DepartmentID=? and (LastName like ? or FirstName like ? or MiddleName like ?) and Shifts.ShiftID = ?\n"
                            + "Order by Employees.EmployeeID\n"
                            + "Offset ? rows fetch next 2 rows only";

                    stm = connection.prepareStatement(sql);
                    stm.setInt(1, phong);
                    stm.setString(2, "%" + name + "%");
                    stm.setString(3, "%" + name + "%");
                    stm.setString(4, "%" + name + "%");
                    stm.setInt(5, ca);
                    stm.setInt(6, (a - 1) * 2);
                    rs = stm.executeQuery();
                } else {

                    String sql = "select * from Employees\n"
                            + "where DepartmentID=? and (LastName like ? or FirstName like ? or MiddleName like ?)\n"
                            + "Order by EmployeeID\n"
                            + "Offset ? rows fetch next 2 rows only";
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
            } finally {
                close();
            }
        }
        return list;
    }

    public ArrayList<EmployeeDTO> GetUnscheduleEmployees(int month, int year) {
        connect();
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeDTO> list = new ArrayList<>();
        if (connection != null) {
            try {
                String sql = ""
                        + "SELECT * \n"
                        + "FROM Employees\n"
                        + "WHERE EmployeeID NOT IN\n"
                        + "(\n"
                        + "SELECT EM.EmployeeID\n"
                        + "FROM Employees EM\n"
                        + "JOIN Timesheet TS ON EM.EmployeeID = TS.EmployeeID\n"
                        + "WHERE \n"
                        + "	MONTH(TS.[Date]) = ?\n"
                        + "	AND YEAR (TS.[Date]) = ?\n"
                        + "	AND TS.[Date] >= CONVERT(Date, GETDATE())"
                        + "       AND TS.CheckIn is null\n"
                        + ")\n"
                        + "AND IsActive = 1;";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, month);
                stm.setInt(2, year);
                rs = stm.executeQuery();
                while (rs.next()) {
                    list.add(getEmployeeDTO(rs));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
        return list;
    }

    public ArrayList<EmployeeDTO> GetAllEmployees() {
        connect();
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeDTO> list = new ArrayList<>();
        if (connection != null) {
            try {
                String sql = ""
                        + "SELECT * \n"
                        + "FROM Employees\n"
                        + "WHERE IsActive = 1;";
                stm = connection.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    list.add(getEmployeeDTO(rs));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
        return list;
    }

    public EmployeeDTO getEmployeeDTO(ResultSet rs) throws SQLException {
        int employeeId = rs.getInt("employeeId");
        String firstName = rs.getNString("firstName");
        String middleName = rs.getNString("middleName");
        String lastName = rs.getNString("lastName");
        Date birthDate = rs.getDate("birthDate");
        Boolean gender = rs.getBoolean("gender");
        String email = rs.getNString("email");
        String password = rs.getNString("password");
        String cccd = rs.getString("cccd");
        String phoneNumber = rs.getString("phoneNumber");
        int employeeTypeID = rs.getInt("employeeTypeID");
        int departmentID = rs.getInt("departmentID");
        int roleID = rs.getInt("roleID");
        Date startDate = rs.getDate("startDate");
        Date endDate = rs.getDate("endDate");
        boolean isActived = rs.getBoolean("isActive");
        EmployeeDTO e = new EmployeeDTO(employeeId, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
        return e;
    }

    public ArrayList<EmployeeDTO> getEmployeeInfoByOvertime(String date, String start, String ends) {
        connect();
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeDTO> list = new ArrayList();
        if (connection != null) {
            try {
                String sql = "select * from Employees\n"
                        + "join Overtimes on Overtimes.EmployeeID = Employees.EmployeeID\n"
                        + "where Overtimes.StartTime = ? and Overtimes.EndTime = ? and Overtimes.Date = ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, start);
                stm.setString(2, ends);
                stm.setString(3, date);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int employeeId = rs.getInt("employeeId");
                    String firstName = rs.getNString("firstName");
                    String middleName = rs.getNString("middleName");
                    String lastName = rs.getNString("lastName");
                    Date birthDate = rs.getDate("birthDate");
                    Boolean gender = rs.getBoolean("gender");
                    String email = rs.getNString("email");
                    String password = rs.getNString("password");
                    String cccd = rs.getString("cccd");
                    String phoneNumber = rs.getString("phoneNumber");
                    int employeeTypeID = rs.getInt("employeeTypeID");
                    int departmentID = rs.getInt("departmentID");
                    int roleID = rs.getInt("roleID");
                    Date startDate = rs.getDate("startDate");
                    Date endDate = rs.getDate("endDate");
                    boolean isActived = rs.getBoolean("isActive");
                    EmployeeDTO e = new EmployeeDTO(employeeId, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                close();
            }
        }
        return list;
    }

    public ArrayList<EmployeeDTO> getEmployeeInfoByOvertimeAjax(String date, String start, String ends, int page, String FirstName, String MidName, String LastName, int phong) {
        connect();
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeDTO> list = new ArrayList();
        if (connection != null) {
            try {
                String sql = "select distinct Employees.EmployeeID,Email,Employees.BirthDate,Employees.FirstName,Employees.LastName\n"
                        + "                ,Employees.MiddleName,Employees.Gender,Employees.DepartmentID,Employees.StartDate,Employees.EndDate,Employees.EmployeeTypeID,\n"
                        + "                            Employees.Password,Employees.IsActive,Employees.CCCD,Employees.PhoneNumber,Employees.RoleID from Employees\n"
                        + "                            join Overtimes on Overtimes.EmployeeID = Employees.EmployeeID\n"
                        + "			Where Date = ? and Overtimes.StartTime = ? and Overtimes.EndTime = ? and (Employees.LastName like  ?  and Employees.FirstName like ? and Employees.MiddleName like ?) and Employees.DepartmentID =? \n"
                        + "                            Order by Employees.EmployeeID\n"
                        + "                            Offset ? rows fetch next 1 rows only";
                stm = connection.prepareStatement(sql);
                stm.setString(1, date);
                stm.setString(2, start);
                stm.setString(3, ends);
                stm.setNString(4, "%" + LastName + "%");
                stm.setNString(5, "%" + FirstName + "%");
                stm.setNString(6, "%" + MidName + "%");
                stm.setInt(8, page);
                stm.setInt(7, phong);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int employeeId = rs.getInt("employeeId");
                    String firstName = rs.getNString("firstName");
                    String middleName = rs.getNString("middleName");
                    String lastName = rs.getNString("lastName");
                    Date birthDate = rs.getDate("birthDate");
                    Boolean gender = rs.getBoolean("gender");
                    String email = rs.getNString("email");
                    String password = rs.getNString("password");
                    String cccd = rs.getString("cccd");
                    String phoneNumber = rs.getString("phoneNumber");
                    int employeeTypeID = rs.getInt("employeeTypeID");
                    int departmentID = rs.getInt("departmentID");
                    int roleID = rs.getInt("roleID");
                    Date startDate = rs.getDate("startDate");
                    Date endDate = rs.getDate("endDate");
                    boolean isActived = rs.getBoolean("isActive");
                    EmployeeDTO e = new EmployeeDTO(employeeId, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                close();
            }
        }
        return list;
    }

    public ArrayList<EmployeeDTO> getScheduledEmployees(int month, int year) {
        connect();
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeDTO> list = new ArrayList<>();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM Employees\n"
                        + "WHERE EmployeeID IN\n"
                        + "(\n"
                        + "SELECT EM.EmployeeID\n"
                        + "FROM \n"
                        + "	Employees EM\n"
                        + "	JOIN Timesheet TS ON EM.EmployeeID = TS.EmployeeID\n"
                        + "WHERE \n"
                        + "	MONTH(TS.Date) = ?\n"
                        + "	AND YEAR(TS.Date) = ?\n"
                        + ")";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, month);
                stm.setInt(2, year);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int employeeId = rs.getInt("employeeId");
                    String firstName = rs.getNString("firstName");
                    String middleName = rs.getNString("middleName");
                    String lastName = rs.getNString("lastName");
                    Date birthDate = rs.getDate("birthDate");
                    Boolean gender = rs.getBoolean("gender");
                    String email = rs.getNString("email");
                    String password = rs.getNString("password");
                    String cccd = rs.getString("cccd");
                    String phoneNumber = rs.getString("phoneNumber");
                    int employeeTypeID = rs.getInt("employeeTypeID");
                    int departmentID = rs.getInt("departmentID");
                    int roleID = rs.getInt("roleID");
                    Date startDate = rs.getDate("startDate");
                    Date endDate = rs.getDate("endDate");
                    boolean isActived = rs.getBoolean("isActive");
                    EmployeeDTO e = new EmployeeDTO(employeeId, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
        return list;
    }

    public ArrayList<EmployeeDTO> getListAddEmployeeOvertime(String date, String start, String end) {
        connect();
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeDTO> list = new ArrayList();
        if (connection != null) {
            try {
                String sql = "SELECT *\n"
                        + "from Employees\n"
                        + "LEFT JOIN Overtimes ON Employees.EmployeeID = Overtimes.EmployeeID and Overtimes.Date = ?\n"
                        + "LEFT JOIN Timesheet ON Timesheet.EmployeeID = Employees.EmployeeID and Timesheet.Date = ?\n"
                        + "LEFT JOIN Shifts ON Timesheet.ShiftID = Shifts.ShiftID\n"
                        + "WHERE Overtimes.Date IS NULL and TimeSheetID is null \n"
                        + "and (Shifts.StartTime is null or CONVERT(TIME,?) > Shifts.EndTime\n"
                        + "or CONVERT(TIME, ?) < Shifts.StartTime)";
                stm = connection.prepareStatement(sql);

                stm.setString(1, date);
                stm.setString(2, date);
                stm.setString(3, start);
                stm.setString(4, end);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int employeeId = rs.getInt("employeeId");
                    String firstName = rs.getNString("firstName");
                    String middleName = rs.getNString("middleName");
                    String lastName = rs.getNString("lastName");
                    Date birthDate = rs.getDate("birthDate");
                    Boolean gender = rs.getBoolean("gender");
                    String email = rs.getNString("email");
                    String password = rs.getNString("password");
                    String cccd = rs.getString("cccd");
                    String phoneNumber = rs.getString("phoneNumber");
                    int employeeTypeID = rs.getInt("employeeTypeID");
                    int departmentID = rs.getInt("departmentID");
                    int roleID = rs.getInt("roleID");
                    Date startDate = rs.getDate("startDate");
                    Date endDate = rs.getDate("endDate");
                    boolean isActived = rs.getBoolean("isActive");
                    EmployeeDTO e = new EmployeeDTO(employeeId, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                close();
            }
        }
        return list;
    }

    public int countEmployeeOvertime(String date, String DepartID, String EmployID, String firstname, String LastName, String MidName) {
        connect();
        int count = 0;
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {

                String sql = "SELECT count(distinct Employees.EmployeeID) as dem \n"
                        + "FROM Employees\n"
                        + "LEFT JOIN Overtimes ON Employees.EmployeeID = Overtimes.EmployeeID\n"
                        + "AND Overtimes.Date = ?\n"
                        + "WHERE  Overtimes.EmployeeID IS NULL and Employees.DepartmentID like ? and Employees.EmployeeTypeID like ?\n"
                        + "and Employees.FirstName like ? and Employees.LastName like ? and Employees.MiddleName like ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, date);
                stm.setString(2, "%" + DepartID + "%");
                stm.setString(3, "%" + EmployID + "%");
                stm.setNString(4, "%" + firstname + "%");
                stm.setNString(5, "%" + LastName + "%");
                stm.setNString(6, "%" + MidName + "%");

                rs = stm.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("dem");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                close();
            }
        }
        return count;
    }

    public ArrayList<EmployeeDTO> getAllAddEmployeeOvertime(String start, String end, String date, String DepartID, String EmployID, String firstname, String LastName, String MidName) {
        connect();
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeDTO> list = new ArrayList();
        if (connection != null) {
            try {
                String sql = "SELECT *\n"
                        + "from Employees\n"
                        + "LEFT JOIN Overtimes ON Employees.EmployeeID = Overtimes.EmployeeID and Overtimes.Date = ?\n"
                        + "LEFT JOIN Timesheet ON Timesheet.EmployeeID = Employees.EmployeeID and Timesheet.Date = ?\n"
                        + "LEFT JOIN Shifts ON Timesheet.ShiftID = Shifts.ShiftID\n"
                        + "WHERE Overtimes.Date IS NULL \n"
                        + "and (Shifts.StartTime is null or CONVERT(TIME,?) > Shifts.EndTime\n"
                        + "or CONVERT(TIME, ?) < Shifts.StartTime) and Employees.DepartmentID like ? and Employees.EmployeeTypeID like ?\n"
                        + "and Employees.FirstName like ? and Employees.LastName like ? and Employees.MiddleName like ?\n"
                        + "\n"
                        + "";
                stm = connection.prepareStatement(sql);
                stm.setString(1, date);
                stm.setString(2, date);
                stm.setString(3, start);
                stm.setString(4, end);
                stm.setNString(5, "%" + DepartID + "%");
                stm.setNString(6, "%" + EmployID + "%");
                stm.setNString(7, "%" + firstname + "%");
                stm.setNString(8, "%" + LastName + "%");
                stm.setNString(9, "%" + MidName + "%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    int employeeId = rs.getInt("employeeId");
                    String firstName = rs.getNString("firstName");
                    String middleName = rs.getNString("middleName");
                    String lastName = rs.getNString("lastName");
                    Date birthDate = rs.getDate("birthDate");
                    Boolean gender = rs.getBoolean("gender");
                    String email = rs.getNString("email");
                    String password = rs.getNString("password");
                    String cccd = rs.getString("cccd");
                    String phoneNumber = rs.getString("phoneNumber");
                    int employeeTypeID = rs.getInt("employeeTypeID");
                    int departmentID = rs.getInt("departmentID");
                    int roleID = rs.getInt("roleID");
                    Date startDate = rs.getDate("startDate");
                    Date endDate = rs.getDate("endDate");
                    boolean isActived = rs.getBoolean("isActive");
                    EmployeeDTO e = new EmployeeDTO(employeeId, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                close();
            }
        }
        return list;
    }

    public ArrayList<EmployeeDTO> getListAddEmployeeOvertimeAjax(String start, String end, int page, String date, String DepartID, String EmployID, String firstname, String LastName, String MidName) {
        connect();
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeDTO> list = new ArrayList();
        if (connection != null) {
            try {
                String sql = "SELECT *\n"
                        + "from Employees\n"
                        + "LEFT JOIN Overtimes ON Employees.EmployeeID = Overtimes.EmployeeID and Overtimes.Date = ?\n"
                        + "LEFT JOIN Timesheet ON Timesheet.EmployeeID = Employees.EmployeeID and Timesheet.Date = ?\n"
                        + "LEFT JOIN Shifts ON Timesheet.ShiftID = Shifts.ShiftID\n"
                        + "WHERE Overtimes.Date IS NULL \n"
                        + "and (Shifts.StartTime is null or CONVERT(TIME,?) > Shifts.EndTime\n"
                        + "or CONVERT(TIME, ?) < Shifts.StartTime) and Employees.DepartmentID like ? and Employees.EmployeeTypeID like ?\n"
                        + "and Employees.FirstName like ? and Employees.LastName like ? and Employees.MiddleName like ?\n"
                        + "Order by Employees.EmployeeID\n"
                        + "Offset ? rows fetch next 10 rows only";
                stm = connection.prepareStatement(sql);
                stm.setString(1, date);
                stm.setString(2, date);
                stm.setString(3, start);
                stm.setString(4, end);
                stm.setNString(5, "%" + DepartID + "%");
                stm.setNString(6, "%" + EmployID + "%");
                stm.setNString(7, "%" + firstname + "%");
                stm.setNString(8, "%" + LastName + "%");
                stm.setNString(9, "%" + MidName + "%");
                stm.setInt(10, (page - 1) * 10);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int employeeId = rs.getInt("employeeId");
                    String firstName = rs.getNString("firstName");
                    String middleName = rs.getNString("middleName");
                    String lastName = rs.getNString("lastName");
                    Date birthDate = rs.getDate("birthDate");
                    Boolean gender = rs.getBoolean("gender");
                    String email = rs.getNString("email");
                    String password = rs.getNString("password");
                    String cccd = rs.getString("cccd");
                    String phoneNumber = rs.getString("phoneNumber");
                    int employeeTypeID = rs.getInt("employeeTypeID");
                    int departmentID = rs.getInt("departmentID");
                    int roleID = rs.getInt("roleID");
                    Date startDate = rs.getDate("startDate");
                    Date endDate = rs.getDate("endDate");
                    boolean isActived = rs.getBoolean("isActive");
                    EmployeeDTO e = new EmployeeDTO(employeeId, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                close();
            }
        }

        return list;
    }

    public ArrayList<EmployeeDTO> getListAddEmployeeOvertimeEachShiftAjax(int page, String date, String DepartID, String firstname, String LastName, String MidName, String start, String end) {
        connect();
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<EmployeeDTO> list = new ArrayList();
        if (connection != null) {
            try {
                String sql = "SELECT * \n"
                        + "FROM Employees\n"
                        + "LEFT JOIN Overtimes ON Employees.EmployeeID = Overtimes.EmployeeID\n"
                        + "AND Overtimes.Date = ?\n"
                        + "WHERE   Employees.DepartmentID like ? \n"
                        + "and Employees.FirstName like ? and Employees.LastName like ? and Employees.MiddleName like ?  and Overtimes.StartTime=? and Overtimes.EndTime=? \n Order by Employees.EmployeeID\n"
                        + "Offset ? rows fetch next 4 rows only"
                        + ";";
                stm = connection.prepareStatement(sql);
                stm.setString(1, date);
                stm.setString(2, "%" + DepartID + "%");
                stm.setNString(3, "%" + firstname + "%");
                stm.setNString(4, "%" + LastName + "%");
                stm.setNString(5, "%" + MidName + "%");
                stm.setString(6, start);
                stm.setString(7, end);
                stm.setInt(8, (page - 1) * 4);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int employeeId = rs.getInt("employeeId");
                    String firstName = rs.getNString("firstName");
                    String middleName = rs.getNString("middleName");
                    String lastName = rs.getNString("lastName");
                    Date birthDate = rs.getDate("birthDate");
                    Boolean gender = rs.getBoolean("gender");
                    String email = rs.getNString("email");
                    String password = rs.getNString("password");
                    String cccd = rs.getString("cccd");
                    String phoneNumber = rs.getString("phoneNumber");
                    int employeeTypeID = rs.getInt("employeeTypeID");
                    int departmentID = rs.getInt("departmentID");
                    int roleID = rs.getInt("roleID");
                    Date startDate = rs.getDate("startDate");
                    Date endDate = rs.getDate("endDate");
                    boolean isActived = rs.getBoolean("isActive");
                    EmployeeDTO e = new EmployeeDTO(employeeId, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                close();
            }
        }

        return list;
    }

    public int countEmployeeOvertimeByShift(String date, String DepartID, String firstname, String LastName, String MidName, String start, String end) {
        connect();
        int count = 0;
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {

                String sql = "SELECT count(distinct Employees.EmployeeID) as dem \n"
                        + "FROM Employees\n"
                        + "LEFT JOIN Overtimes ON Employees.EmployeeID = Overtimes.EmployeeID\n"
                        + "AND Overtimes.Date = ?\n"
                        + "WHERE Employees.DepartmentID like ? \n"
                        + "and Employees.FirstName like ? and Employees.LastName like ? and Employees.MiddleName like ? and Overtimes.StartTime =? and Overtimes.EndTime = ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, date);
                stm.setString(2, "%" + DepartID + "%");
                stm.setNString(3, "%" + firstname + "%");
                stm.setNString(4, "%" + LastName + "%");
                stm.setNString(5, "%" + MidName + "%");
                stm.setNString(6, start);
                stm.setNString(7, end);
                rs = stm.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("dem");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                close();
            }
        }

        return count;
    }

    public EmployeeGeneral getEmployeeGeneral(int id) {
        connect();
        PreparedStatement stm = null;
        ResultSet rs = null;
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
                        + "WHERE EmployeeID = ? ";
                connect();
                stm = connection.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                if (rs.next()) {
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
                    return e;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            } finally {
                close();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        System.out.println(dao.countEmployeeOvertimeByShift("2024-02-25", "", "", "", "", "15:00", "17:00"));
        //System.out.println(dao.getEmployeeInfoByOvertimeAjax("2024-02-23","17:00","19:30",0,"","","",2).size());

        //System.out.println(dao.getEmployeeInfoByOvertimeAjax("2024-02-23","17:00","19:30",0).size());
        //test ham lay ttin
//        System.out.println(dao.getEmail("nguyennduongg039@gmail.com"));`  
//        System.out.println(dao.getCCCD("0000000"));
//        System.out.println(dao.getPhonenumber("454545454"));
//
//        System.out.println("getEmployeeDTO");
////        System.out.println(dao.getEmployeeDTO(1));
////        System.out.println(dao.getEmail("duong@gmail.com"));
//        ArrayList<EmployeeDTO> GetUnscheduleEmployees = dao.GetUnscheduleEmployees(2, 2024);
//        for (EmployeeDTO e : GetUnscheduleEmployees) {
//            System.out.println(e);
//        }
        System.out.println(dao.getEmail("nguyennduongg039@gmail.com"));
        System.out.println(dao.getCCCD("0000000"));
        System.out.println(dao.getPhonenumber("454545454"));

        System.out.println("getEmployeeDTO");
//        System.out.println(dao.getEmployeeDTO(1));
//        System.out.println(dao.getEmail("duong@gmail.com"));
        ArrayList<EmployeeDTO> GetUnscheduleEmployees = dao.GetUnscheduleEmployees(2, 2024);
        for (EmployeeDTO e : GetUnscheduleEmployees) {
            System.out.println(e);
        }
        System.out.println("====");
        ArrayList<EmployeeDTO> scheduleEmployees = dao.getScheduledEmployees(2, 2024);
        for (EmployeeDTO e : scheduleEmployees) {
            System.out.println(e);
        }
    }

}
