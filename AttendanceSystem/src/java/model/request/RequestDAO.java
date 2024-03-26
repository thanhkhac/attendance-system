package model.request;

import dbhelper.DAOBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.ShiftDTO;

public class RequestDAO extends DAOBase {

    public int insert(RequestDTO request) {
        connect();
        query = "INSERT INTO Requests (EmployeeID, Title, SentDate, TypeID, Content, FilePath)\n" +
                "VALUES(?, ?, GETDATE(), ? , ?, ?);";
        try {
            super.connect();
            ps = connection.prepareStatement(query);
            ps.setInt(1, request.getEmployeeID());
            ps.setNString(2, request.getTitle());
            ps.setInt(3, request.getTypeID());
            ps.setNString(4, request.getContent());
            ps.setNString(5, request.getFilePath());
            int result = ps.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return 0;
    }

    public int update(RequestDTO request) {
        connect();
        String query = "UPDATE Requests SET EmployeeID = ?, Title = ?, SentDate = ?, TypeID = ?, Content = ?, FilePath = ?, Status = ?, ProcessNote = ?, ResponedBy = ? WHERE RequestID = ?";
        try {
            super.connect();
            ps = connection.prepareStatement(query);
            ps.setInt(1, request.getEmployeeID());
            ps.setString(2, request.getTitle());
            if (request.getSentDate() != null) {
                ps.setObject(3, java.sql.Timestamp.valueOf(request.getSentDate()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setInt(4, request.getTypeID());
            ps.setString(5, request.getContent());
            ps.setString(6, request.getFilePath());
            if (request.getStatus() != null) {
                ps.setBoolean(7, request.getStatus());
            } else {
                ps.setNull(7, Types.BOOLEAN);
            }
            ps.setString(8, request.getProcessNote());
            if (request.getRespondedBy() != null) {
                ps.setInt(9, request.getRespondedBy());
            } else {
                ps.setNull(9, Types.INTEGER);
            }
            ps.setInt(10, request.getRequestID());

            int result = ps.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return 0;
    }

    RequestDTO getRequestDTO(ResultSet rs) throws SQLException {
        RequestDTO request = new RequestDTO();
        request.setRequestID(rs.getInt("RequestID"));
        request.setEmployeeID(rs.getInt("EmployeeID"));
        request.setTitle(rs.getString("Title"));
        request.setSentDate(rs.getTimestamp("SentDate").toLocalDateTime());
        request.setTypeID(rs.getInt("TypeID"));
        request.setContent(rs.getNString("Content"));
        request.setFilePath((String) rs.getObject("FilePath"));
        request.setStatus((Boolean) rs.getObject("Status"));
        request.setProcessNote((String) rs.getObject("ProcessNote"));
        request.setRespondedBy((Integer) rs.getObject("ResponedBy"));
        return request;
    }

    public ArrayList<RequestDTO> getRequests(int startRowIndex, int endRowIndex) {
        ArrayList<RequestDTO> requests = new ArrayList<>();
        connect();
        try {
            String sql = "SELECT * " +
                    "FROM Requests " +
                    "ORDER BY SentDate DESC " +
                    "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, startRowIndex);
            ps.setInt(2, endRowIndex);
            rs = ps.executeQuery();

            while (rs.next()) {
                RequestDTO request = new RequestDTO();
                request.setRequestID(rs.getInt("RequestID"));
                request.setEmployeeID(rs.getInt("EmployeeID"));
                request.setTitle(rs.getString("Title"));
                request.setSentDate(rs.getTimestamp("SentDate").toLocalDateTime());
                request.setTypeID(rs.getInt("TypeID"));
                request.setContent(rs.getNString("Content"));
                request.setFilePath((String) rs.getObject("FilePath"));
                request.setStatus((Boolean) rs.getObject("Status"));
                request.setProcessNote((String) rs.getObject("ProcessNote"));
                request.setRespondedBy((Integer) rs.getObject("ResponedBy"));
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return requests;
    }

    public RequestDTO getRequestByID(int id) {
        ArrayList<RequestDTO> requests = new ArrayList<>();
        connect();
        try {
            String sql = "SELECT * FROM Requests Where RequestID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                return getRequestDTO(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return null;
    }

    public int getNumberOfRequests() {
        connect();
        int numberOfRequests = 0;
        try {
            String sql = "SELECT COUNT(*) AS NumberOfRequests FROM Requests";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                numberOfRequests = rs.getInt("NumberOfRequests");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return numberOfRequests;
    }

    public int updateRequestStatus(int requestId, boolean status, String processNote, int respondedBy) {
        connect();
        String query = "UPDATE Requests SET Status = ?, ProcessNote = ?, ResponedBy = ? WHERE RequestID = ?";
        try {
            super.connect();
            ps = connection.prepareStatement(query);
            ps.setBoolean(1, status);
            ps.setString(2, processNote);
            ps.setInt(3, respondedBy);
            ps.setInt(4, requestId);

            int result = ps.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return 0;
    }

    public static void main(String[] args) {
        // Thiết lập các chỉ số hàng bắt đầu và kết thúc cho việc truy vấn
        int startRowIndex = 1; // Chỉ số hàng bắt đầu
        int endRowIndex = 10; // Chỉ số hàng kết thúc

        // Khởi tạo đối tượng thực hiện việc lấy dữ liệu từ cơ sở dữ liệu
        RequestDAO requestDAO = new RequestDAO();

        // Gọi phương thức để lấy danh sách các yêu cầu từ cơ sở dữ liệu
        ArrayList<RequestDTO> requests = requestDAO.getRequests(startRowIndex, endRowIndex);

        // In thông tin các yêu cầu ra màn hình
        for (RequestDTO request : requests) {
            System.out.println(request);
        }

        System.out.println(requestDAO.getRequestByID(1));

        // Tạo một đối tượng RequestDTO để chèn vào cơ sở dữ liệu
        RequestDTO requestToInsert = new RequestDTO();
        requestToInsert.setEmployeeID(2); // Thiết lập EmployeeID
        requestToInsert.setTitle("Test Request"); // Thiết lập Title
        requestToInsert.setTypeID(1); // Thiết lập TypeID
        requestToInsert.setContent("Test content"); // Thiết lập Content
        requestToInsert.setFilePath("/path/to/file"); // Thiết lập FilePath

        // Tạo một đối tượng RequestDAO để thực hiện việc chèn dữ liệu
        // Thực hiện chèn dữ liệu và kiểm tra kết quả
        int insertResult = requestDAO.insert(requestToInsert);
        if (insertResult > 0) {
            System.out.println("Insert successful");
        } else {
            System.out.println("Insert failed");
        }

        // Tạo một đối tượng RequestDTO để cập nhật vào cơ sở dữ liệu
        RequestDTO requestToUpdate = new RequestDTO();
        requestToUpdate.setRequestID(1); // Thiết lập RequestID của yêu cầu cần cập nhật
        requestToUpdate.setEmployeeID(1); // Thiết lập EmployeeID mới
        requestToUpdate.setSentDate(LocalDateTime.now()); // Thiết lập EmployeeID mới
        requestToUpdate.setTitle("Updated Title"); // Thiết lập Title mới
        requestToUpdate.setTypeID(2); // Thiết lập TypeID mới
        requestToUpdate.setContent("Updated content"); // Thiết lập Content mới
        requestToUpdate.setFilePath("/updated/path/to/file"); // Thiết lập FilePath mới
        requestToUpdate.setStatus(true); // Thiết lập Status mới
        requestToUpdate.setProcessNote("Updated process note"); // Thiết lập ProcessNote mới
        requestToUpdate.setRespondedBy(1); // Thiết lập RespondedBy mới

        // Thực hiện cập nhật dữ liệu và kiểm tra kết quả
        int updateResult = requestDAO.update(requestToUpdate);
        if (updateResult > 0) {
            System.out.println("Update successful");
        } else {
            System.out.println("Update failed");
        }
    }

}
