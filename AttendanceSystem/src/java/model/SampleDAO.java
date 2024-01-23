package model;

import dbhelper.DAOBase;
import java.time.LocalTime;
import static model.ShiftDAO.DATE_UTIL;

public class SampleDAO extends DAOBase {

    public ShiftDTO getShiftDTO(int xShiftID) {
        query = "";
        try {
            //Mở kết nối
            super.connect();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Đóng PrepareStatement, ResultSet, Connection
            super.closeAll();
        }
        return null;
    }

}
