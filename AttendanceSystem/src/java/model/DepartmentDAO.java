/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dbhelper.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class DepartmentDAO extends DBContext {

    public ArrayList<DepartmentDTO> getListDepartment() {

        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<DepartmentDTO> list = new ArrayList<>();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM Departments ";
                stm = connection.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int departmentID = rs.getInt("DepartmentID");
                    String name = rs.getString("Name");
                    int managerID = rs.getInt("ManagerID");
                    DepartmentDTO de = new DepartmentDTO(departmentID, name, managerID);
                    list.add(de);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                //close
            }
        }
        return list;
    }

    public boolean updateManager(String managerID, String departmentID) {
        PreparedStatement stm = null;

        if (connection != null) {
            try {
                String sql = "UPDATE Departments "
                        + "SET ManagerID = ? "
                        + "WHERE DepartmentID = ? ";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, Integer.parseInt(managerID));
                stm.setInt(2, Integer.parseInt(departmentID));
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
            }
        }
        return false;
    }

    public DepartmentDTO getDepartmentById(int departmentID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                String sql = "SELECT *\r\n"
                        + //
                        "  FROM [Attendance_DB].[dbo].[Departments]\r\n"
                        + //
                        "  where DepartmentID = ?";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, departmentID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    DepartmentDTO dto = new DepartmentDTO(
                            rs.getInt("DepartmentID"),
                            rs.getString("Name"),
                            rs.getInt("ManagerID"));
                    return dto;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public boolean deleteById(int departmentId) {
        String sql = "DELETE FROM [dbo].[Departments]\n"
                + "      WHERE DepartmentID = ? ";
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                stm = connection.prepareStatement(sql);
                stm.setInt(1, departmentId);
                stm.executeUpdate();
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();

            }
        }
        return false;
    }

    public boolean edit(DepartmentDTO dto) {
        String sql = "UPDATE [dbo].[Departments]\r\n"
                + //
                "   SET [Name] = ?\r\n"
                + //
                " WHERE DepartmentID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                stm = connection.prepareStatement(sql);
                stm.setString(1, dto.getName());
                stm.setInt(2, dto.getDepartmentID());
                stm.executeUpdate();
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();

            }
        }
        return false;
    }

    public boolean addDepartment(DepartmentDTO dto) {
        String sql = "INSERT INTO [dbo].[Departments]\n"
                + "           ([Name])           \n"
                + "     VALUES\n"
                + "           (?)";
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                stm = connection.prepareStatement(sql);
                stm.setString(1, dto.getName());
                stm.executeUpdate();
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<DepartmentDTO> searchByName(DepartmentDTO dto) {

        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<DepartmentDTO> list = new ArrayList<>();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM Departments where Name like ?";
                stm = connection.prepareStatement(sql);
                stm.setObject(1, "%" + dto.getName() + "%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    int departmentID = rs.getInt("DepartmentID");
                    String name = rs.getString("Name");
                    int managerID = rs.getInt("ManagerID");
                    DepartmentDTO de = new DepartmentDTO(departmentID, name, managerID);
                    list.add(de);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                //close
            }
        }
        return list;
    }

    public int getDepartmentIDByName(String departmentName) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        int id = 0;
        if (connection != null) {
            try {
                String sql = "SELECT DepartmentID \n"
                        + "FROM Departments \n"
                        + "WHERE [Name] = ? ";
                stm = connection.prepareStatement(sql);
                stm.setNString(1, departmentName);
                rs = stm.executeQuery();

                if (rs.next()) {
                    id = rs.getInt("DepartmentID");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return id;
    }

    public static void main(String[] args) {
        DepartmentDAO deDao = new DepartmentDAO();
        int a = deDao.getDepartmentIDByName("Phòng nhân sự");
        System.out.println(a);
    }

}
