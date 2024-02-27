/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.request;

import java.sql.Time;
import java.time.LocalDate;
import model.EmployeeDTO;

/**
 *
 * @author admin
 */
public class OverTimeRequestDAO extends dbhelper.DAOBase {

    public boolean insertOverTimeRequest(EmployeeDTO e, LocalDate sentDate, LocalDate date, Time startTime, Time endTime, int createdBy) {
        if (connection != null) {
            try {
                String sql = "INSERT INTO OvertimeRequests(Date, EmployeeID, SentDate, StartTime , EndTime, CreatedBy) "
                        + "VALUES (?,?,?,?,?,?)";
                ps = connection.prepareStatement(sql);
                ps.setString(1, date.toString());
                ps.setInt(2, e.getEmployeeID());
                ps.setString(3, sentDate.toString());
                ps.setTime(4, startTime);
                ps.setTime(5, endTime);
                ps.setInt(6, createdBy);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    return true;
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            } finally {
                closeAll();
            }
        }
        return false;
    }
}
