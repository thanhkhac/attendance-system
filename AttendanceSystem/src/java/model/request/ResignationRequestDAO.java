/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.request;

import java.time.LocalDate;
import model.EmployeeDTO;

/**
 *
 * @author admin
 */
public class ResignationRequestDAO extends dbhelper.DAOBase {

    public boolean insertResignationRequest(EmployeeDTO e, LocalDate sentDate, LocalDate extendDate, String reason) {
        if (con != null) {
            try {
                String sql = "INSERT INTO ResignationRequests(EmployeeID, SentDate, StartDateContract, EndDateContract, ExtendDate, reason) "
                        + "VALUES (?, ?, ?, ?, ? ,?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, e.getEmployeeID());
                ps.setString(2, sentDate.toString());
                ps.setString(3, e.getStartDate().toString());
                ps.setString(4, e.getEndDate().toString());
                ps.setString(5, extendDate.toString());
                ps.setNString(6, reason);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            } finally {
                closeAll();
            }
        }
        return false;
    }
}
