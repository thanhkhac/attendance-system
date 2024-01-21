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
                    int department = rs.getInt("DepartmentID");
                    String name = rs.getString("Name");
                    int managerID = rs.getInt("ManagerID");
                    DepartmentDTO de = new DepartmentDTO(department, name, managerID);
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

}
