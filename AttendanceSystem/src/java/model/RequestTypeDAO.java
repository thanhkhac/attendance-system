/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dbhelper.DAOBase;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class RequestTypeDAO extends DAOBase {

    public ArrayList<RequestTypeDTO> getRequestTypeList() {
        connect();
        ArrayList<RequestTypeDTO> list = new ArrayList<>();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM RequestsType ";
                ps = connection.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("TypeID");
                    String name = rs.getNString("Name");

                    list.add(new RequestTypeDTO(id, name));
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        closeAll();
        return list;
    }
    public static void main(String[] args) {
        ArrayList<RequestTypeDTO> requestTypeDTOs = new RequestTypeDAO().getRequestTypeList();
        requestTypeDTOs.forEach( (t) -> {
            System.out.println(t.getRequestTypeID());
             System.out.println(t.getRequestTypeName());
        });
    }
}
