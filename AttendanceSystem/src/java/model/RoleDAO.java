/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dbhelper.*;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class RoleDAO extends DAOBase {

    public ArrayList<RoleDTO> getRoleList() {
        ArrayList<RoleDTO> list = new ArrayList<>();
        if (con != null) {
            try {
                query = "SELECT * FROM Roles ";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int roleId = rs.getInt("RoleID");
                    String name = rs.getString("Name");
                    RoleDTO r = new RoleDTO(roleId, name);
                    list.add(r);
                }
            } catch (Exception e) {
            } finally {
            }
        }
        return list ;
    }
    
}
