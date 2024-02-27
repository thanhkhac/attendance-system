package dbhelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOBase  extends DBContext{

    public PreparedStatement ps = null;
    public ResultSet rs = null;
    public String query = null;


    public void closeAll() {
        if (rs != null) {
            try {
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close();
    }
}
