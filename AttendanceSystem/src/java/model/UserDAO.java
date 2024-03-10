/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dbhelper.DAOBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ADMIN-PC
 */
public class UserDAO extends DAOBase {

    public boolean resetPassword(int employeeID, String newPassword) {
        connect();
        PreparedStatement stm = null;
        try {
            String sql = "UPDATE Employees SET Password = ? WHERE EmployeeID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, newPassword);
            stm.setInt(2, employeeID);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            close();
        }
        return false;
    }

    public boolean checkCurrentPassword(int employeeID, String currentPassword) {
        connect();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(*) FROM Employees WHERE EmployeeID = ? AND Password = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, employeeID);
            stm.setString(2, currentPassword);
            rs = stm.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            close();
        }
        return false;
    }

}
