/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dbhelper.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class RoleDAO extends DAOBase {

    public ArrayList<RoleDTO> getRoleList() {
        ArrayList<RoleDTO> list = new ArrayList<>();
//        if (connection != null) {
        try {
            query = "SELECT * FROM Roles ";
            connect();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                int roleId = rs.getInt("RoleID");
                String name = rs.getString("Name");
                RoleDTO r = new RoleDTO(roleId, name);
                list.add(r);
            }
        } catch (Exception e) {
        } finally {
            closeAll();
        }
//        }
        return list;
    }

    public RoleDTO getRoleById(int roleID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
//        if (connection != null) {
        try {
            String sql = "SELECT * "
                    + //
                    "  FROM Roles "
                    + //
                    "  where RoleID = ?";
            connect();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, roleID);
            rs = stm.executeQuery();
            if (rs.next()) {
                RoleDTO dto = new RoleDTO(
                        rs.getInt("RoleID"),
                        rs.getString("Name"));
                return dto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return null;
    }

    public int getRoleIDByName(String roleName) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        int id = 0;
//        if (connection != null) {
        try {
            String sql = "SELECT RoleID \n"
                    + "FROM Roles \n"
                    + "WHERE [Name] = ? ";
            connect();
            stm = connection.prepareStatement(sql);
            stm.setNString(1, roleName);
            rs = stm.executeQuery();

            if (rs.next()) {
                id = rs.getInt("RoleID");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeAll();
        }
//        }
        return id;
    }

}
