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
public class EmployeeTypeDAO extends DAOBase {

    public ArrayList<EmployeeTypeDTO> getEmployeeTypeList() {
        connect();
        ArrayList<EmployeeTypeDTO> list = new ArrayList<>();

        if (connection != null) {
            try {
                query = "SELECT * FROM EmployeeTypes ";
                ps = connection.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int employeeTypeId = rs.getInt("EmployeeTypeID");
                    String name = rs.getString("Name");
                    EmployeeTypeDTO e = new EmployeeTypeDTO(employeeTypeId, name);
                    list.add(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeAll();
            }
        }
        return list;
    }

    public int getEmployeeTypeIDByName(String employeeTypeName) {
        connect();
        PreparedStatement stm = null;
        ResultSet rs = null;
        int id = 0;
        if (connection != null) {
            try {
                String sql = "SELECT EmployeeTypeID \n" +
                         "FROM EmployeeTypes \n" +
                         "WHERE [Name] = ? ";
                stm = connection.prepareStatement(sql);
                stm.setNString(1, employeeTypeName);
                rs = stm.executeQuery();

                if (rs.next()) {
                    id = rs.getInt("EmployeeTypeID");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeAll();
            }
        }
        return id;
    }
    public String getEmployeeTypeIDByID(int TypeID) {
        connect();
        String name = "";
        PreparedStatement stm = null;
        ResultSet rs = null;
        int id = 0;
        if (connection != null) {
            try {
                String sql = "SELECT Name \n" +
                         "FROM EmployeeTypes \n" +
                         "WHERE EmployeeTypeID = ? ";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, TypeID);
                rs = stm.executeQuery();

                if (rs.next()) {
                    name = rs.getString("Name");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
        return name;
    }

}
