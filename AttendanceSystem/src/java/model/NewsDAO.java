/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN-PC
 */
public class NewsDAO extends dbhelper.DBContext {

    public boolean insertNews(String title, String content, String filePath, Timestamp dateTime, int createdBy) {
        String sql = "INSERT INTO News (Title, Content, FilePath, DateTime, CreatedBy) VALUES (?, ?, ?, ?, ?)";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, content);
            ps.setNString(3, filePath);
            ps.setTimestamp(4, dateTime);
            ps.setInt(5, createdBy);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<NewsDTO> getAllNews() {
        List<NewsDTO> newsList = new ArrayList<>();
        NewsDAO n = new NewsDAO();
        String sql = "SELECT * FROM News ORDER BY NewsID DESC";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NewsDTO news = new NewsDTO();
                    news.setNewId(rs.getInt("NewsID"));
                    news.setTitle(rs.getString("Title"));
                    news.setContent(rs.getString("Content"));
                    news.setFilePath(rs.getString("FilePath"));
                    news.setDateTime(rs.getTimestamp("DateTime"));
                    news.setCreateBy(n.getEmployeeById(rs.getInt("CreatedBy")).getFirstName());
                    newsList.add(news);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newsList;
    }

    public NewsDTO getNewsById(int newsId) {
        String sql = "SELECT * FROM News WHERE NewsID = ?";
        NewsDTO news = new NewsDTO();
        NewsDAO n = new NewsDAO();
        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, newsId);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    news.setNewId(rs.getInt("NewsID"));
                    news.setTitle(rs.getString("Title"));
                    news.setContent(rs.getString("Content"));
                    news.setFilePath(rs.getString("FilePath"));
                    news.setDateTime(rs.getTimestamp("DateTime"));
                    news.setCreateBy(n.getEmployeeById(rs.getInt("CreatedBy")).getFirstName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return news;
    }

    public List<NewsDTO> getOtherNews(int newsId) {
        List<NewsDTO> otherNewsList = new ArrayList<>();
        NewsDAO n = new NewsDAO();
        String sql = "SELECT * FROM News WHERE NewsID != ? ORDER BY NewsID DESC";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, newsId);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NewsDTO news = new NewsDTO();
                    news.setNewId(rs.getInt("NewsID"));
                    news.setTitle(rs.getString("Title"));
                    news.setContent(rs.getString("Content"));
                    news.setFilePath(rs.getString("FilePath"));
                    news.setDateTime(rs.getTimestamp("DateTime"));
                    news.setCreateBy(n.getEmployeeById(rs.getInt("CreatedBy")).getFirstName());
                    otherNewsList.add(news);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return otherNewsList;
    }

    public EmployeeDTO getEmployeeById(int employeeId) {
        String sql = "SELECT [FirstName], [MiddleName], [LastName] FROM Employees WHERE EmployeeID = ?";

        EmployeeDTO employee = new EmployeeDTO();

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, employeeId);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    employee.setFirstName(rs.getString("FirstName"));
                    employee.setMiddleName(rs.getString("MiddleName"));
                    employee.setLastName(rs.getString("LastName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    public int findEmployeeIdByFirstName(String firstName) {
        String sql = "SELECT [EmployeeID] FROM Employees WHERE [FirstName] = ?";
        int employeeId = -1;

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, firstName);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    employeeId = rs.getInt("EmployeeID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeId;
    }

    public List<EmployeeDTO> findAllEmployees() {
        String sql = "SELECT [EmployeeID], [FirstName] FROM Employees";
        List<EmployeeDTO> employees = new ArrayList<>();

        try ( PreparedStatement ps = connection.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EmployeeDTO employee = new EmployeeDTO();
                employee.setEmployeeID(rs.getInt("EmployeeID"));
                employee.setFirstName(rs.getString("FirstName"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public boolean updateNews(int newsId, String title, String content, String filePath, Timestamp dateTime, String createBy) {
        String sql = "UPDATE News SET Title = ?, Content = ?, FilePath = ?, DateTime = ?, CreatedBy = ? WHERE NewsID = ?";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, content);
            ps.setString(3, filePath);
            ps.setTimestamp(4, dateTime);

            NewsDAO n = new NewsDAO();
            int createById = n.findEmployeeIdByFirstName(createBy);

            ps.setInt(5, createById);
            ps.setInt(6, newsId);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteNews(int newsId) {
        String sql = "DELETE FROM News WHERE NewsID = ?";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, newsId);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<NewsDTO> getNewsByPage(int pageNumber, int newsPerPage) {
        List<NewsDTO> newsList = new ArrayList<>();
        NewsDAO n = new NewsDAO();
        String sql = "SELECT * FROM News ORDER BY NewsID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            int offset = (pageNumber - 1) * newsPerPage;
            ps.setInt(1, offset);
            ps.setInt(2, newsPerPage);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NewsDTO news = new NewsDTO();
                    news.setNewId(rs.getInt("NewsID"));
                    news.setTitle(rs.getString("Title"));
                    news.setContent(rs.getString("Content"));
                    news.setFilePath(rs.getString("FilePath"));
                    news.setDateTime(rs.getTimestamp("DateTime"));
                    news.setCreateBy(n.getEmployeeById(rs.getInt("CreatedBy")).getFirstName());
                    newsList.add(news);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newsList;
    }

    public int getTotalNewsCount() {
        String sql = "SELECT COUNT(*) FROM News";

        try ( PreparedStatement ps = connection.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean isTitleDuplicate(String title) {
        String sql = "SELECT COUNT(*) FROM News WHERE Title = ?";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean isTitleDuplicateExcludeId(String title, int newsId) {
    String sql = "SELECT COUNT(*) FROM News WHERE Title = ? AND NewsID != ?";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, title);
        ps.setInt(2, newsId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}


    public static void main(String[] args) {
        NewsDAO newsDAO = new NewsDAO();
        String title = "Sample News Title";
        String content = "This is a sample news content.";
        String filePath = "C:\\Users\\ADMIN-PC\\Desktop\\swp jsp\\news sp\\attendance-system\\AttendanceSystem\\docxfile\\Report.docx";
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        String createdBy = "Thành"; // Assuming employee ID for the creator
        int id = newsDAO.findEmployeeIdByFirstName(createdBy);
        System.out.println("ten cua to: " + id);
        // Insert a new news record
        boolean insertionResult = newsDAO.insertNews(title, content, filePath, dateTime, id);

        if (insertionResult) {
            System.out.println("News inserted successfully!");
        } else {
            System.out.println("Failed to insert news.");
        }
        List<EmployeeDTO> e = newsDAO.findAllEmployees();
        for (EmployeeDTO e1 : e) {
            System.out.println(e1.getEmployeeID());

        }

//        int pageNumber = 1;
//        int newsPerPage = 5;
//
//        List<NewsDTO> newsList = newsDAO.getNewsByPage(pageNumber, newsPerPage);
//
//        // In ra danh sách tin tức
//        System.out.println("News on Page " + pageNumber + ":");
//        for (NewsDTO news : newsList) {
//            System.out.println("Title: " + news.getTitle());
//            System.out.println("Content: " + news.getContent());
//            System.out.println("File Path: " + news.getFilePath());
//            System.out.println("DateTime: " + news.getDateTime());
//            System.out.println("Created By: " + news.getCreateBy());
//            System.out.println("----------");
//        }
    }

}
