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
            ps.setString(3, filePath);
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
    
    public ArrayList<NewsDTO> get5News() {   
        ArrayList<NewsDTO> newsList = new ArrayList<>();
        NewsDAO n = new NewsDAO();
        String sql = "select top 5 * from News\n" +
"Order by DateTime Desc";

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

    public List<NewsDTO> getNewsByPageWithDate(int pageNumber, int newsPerPage, String fromDate, String toDate) {
        List<NewsDTO> newsList = new ArrayList<>();
        NewsDAO n = new NewsDAO();

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM News WHERE 1=1");

        if (fromDate != null && toDate != null && !fromDate.isEmpty() && !toDate.isEmpty()) {
            sqlBuilder.append(" AND DateTime >= ? AND DateTime < DATEADD(DAY, 1, ?)");
        }

        sqlBuilder.append(" ORDER BY NewsID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try ( PreparedStatement ps = connection.prepareStatement(sqlBuilder.toString())) {
            // Set parameters
            int parameterIndex = 1;
            if (fromDate != null && toDate != null && !fromDate.isEmpty() && !toDate.isEmpty()) {
                ps.setString(parameterIndex++, fromDate);
                ps.setString(parameterIndex++, toDate);
            }

            int offset = (pageNumber - 1) * newsPerPage;
            ps.setInt(parameterIndex++, offset);
            ps.setInt(parameterIndex++, newsPerPage);

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

    public int getTotalNewsCount1(String fromDate, String toDate) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM News WHERE 1=1");

        if (fromDate != null && toDate != null && !fromDate.isEmpty() && !toDate.isEmpty()) {
            sqlBuilder.append(" AND DateTime >= ? AND DateTime < DATEADD(DAY, 1, ?)");
        }

        String sql = sqlBuilder.toString();

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set parameters
            int parameterIndex = 1;
            if (fromDate != null && toDate != null && !fromDate.isEmpty() && !toDate.isEmpty()) {
                ps.setString(parameterIndex++, fromDate);
                ps.setString(parameterIndex++, toDate);
            }

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
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

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setInt(2, newsId);

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

    public List<NewsDTO> getNewsByDate(Timestamp date) {
        List<NewsDTO> newsList = new ArrayList<>();
        NewsDAO n = new NewsDAO();
        String sql = "SELECT * FROM News WHERE CAST(DateTime AS DATE) = CAST(? AS DATE) ORDER BY NewsID DESC";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, date);

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

    public List<NewsDTO> getNewsByEmployeeAndSortOrder(String employee, String sortOrder) {
        List<NewsDTO> newsList = new ArrayList<>();
        NewsDAO n = new NewsDAO();

        String baseSql = "SELECT * FROM News WHERE 1=1";

        if (employee != null && !employee.isEmpty()) {
            baseSql += " AND CreatedBy = ?";
        }

        baseSql += " ORDER BY NewsID " + (sortOrder != null && sortOrder.equalsIgnoreCase("asc") ? "ASC" : "DESC");

        try ( PreparedStatement ps = connection.prepareStatement(baseSql)) {
            int parameterIndex = 1;

            if (employee != null && !employee.isEmpty()) {
                int creatorId = n.findEmployeeIdByFirstName(employee);
                ps.setInt(parameterIndex++, creatorId);
            }

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

    public List<NewsDTO> searchNews(String id, String title, String content, String filePath) {
        List<NewsDTO> newsList = new ArrayList<>();
        NewsDAO n = new NewsDAO();

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM News WHERE 1=1");

        if (id != null && !id.isEmpty()) {
            sqlBuilder.append(" AND NewsID = ?");
        }

        if (title != null && !title.isEmpty()) {
            sqlBuilder.append(" AND Title LIKE ?");
        }

        if (content != null && !content.isEmpty()) {
            sqlBuilder.append(" AND Content LIKE ?");
        }

        if (filePath != null && !filePath.isEmpty()) {
            sqlBuilder.append(" AND FilePath LIKE ?");
        }

        String sql = sqlBuilder.toString();

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            int parameterIndex = 1;

            if (id != null && !id.isEmpty()) {
                ps.setString(parameterIndex++, id);
            }

            if (title != null && !title.isEmpty()) {
                ps.setString(parameterIndex++, "%" + title + "%");
            }

            if (content != null && !content.isEmpty()) {
                ps.setString(parameterIndex++, "%" + content + "%");
            }

            if (filePath != null && !filePath.isEmpty()) {
                ps.setString(parameterIndex++, "%" + filePath + "%");
            }

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

    public int getTotalSearchNewsCount(String id, String title, String content, String filePath, String fromDate, String toDate) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM News WHERE 1=1");

        if (!fromDate.isEmpty() && !toDate.isEmpty()) {
            sqlBuilder.append(" AND DateTime >= ? AND DateTime < DATEADD(DAY, 1, ?)");
        }

        if (id != null && !id.isEmpty()) {
            sqlBuilder.append(" AND NewsID = ?");
        }

        if (title != null && !title.isEmpty()) {
            sqlBuilder.append(" AND Title LIKE ?");
        }

        if (content != null && !content.isEmpty()) {
            sqlBuilder.append(" AND Content LIKE ?");
        }

        if (filePath != null && !filePath.isEmpty()) {
            sqlBuilder.append(" AND FilePath LIKE ?");
        }

        try ( PreparedStatement ps = connection.prepareStatement(sqlBuilder.toString())) {
            int parameterIndex = 1;

            if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                ps.setString(parameterIndex++, fromDate);
                ps.setString(parameterIndex++, toDate);
            }

            if (id != null && !id.isEmpty()) {
                ps.setString(parameterIndex++, id);
            }

            if (title != null && !title.isEmpty()) {
                ps.setString(parameterIndex++, "%" + title + "%");
            }

            if (content != null && !content.isEmpty()) {
                ps.setString(parameterIndex++, "%" + content + "%");
            }

            if (filePath != null && !filePath.isEmpty()) {
                ps.setString(parameterIndex++, "%" + filePath + "%");
            }

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

public List<NewsDTO> getNewsByPageWithSearch(int pageNumber, int newsPerPage, String id, String title, String content, String filePath, String fromDate, String toDate) {
    List<NewsDTO> newsList = new ArrayList<>();
    NewsDAO n = new NewsDAO();

    StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM News WHERE 1=1");

    if (!fromDate.isEmpty() && !toDate.isEmpty()) {
        sqlBuilder.append(" AND DateTime >= ? AND DateTime < DATEADD(DAY, 1, ?)");
    }

    if (!id.isEmpty()) {
        sqlBuilder.append(" AND NewsID = ?");
    } else {
        sqlBuilder.append(" AND 1=1");
    }

    if (!title.isEmpty()) {
        sqlBuilder.append(" AND Title LIKE ?");
    }

    if (!content.isEmpty()) {
        sqlBuilder.append(" AND Content LIKE ?");
    }

    if (!filePath.isEmpty()) {
        sqlBuilder.append(" AND FilePath LIKE ?");
    }

    sqlBuilder.append(" ORDER BY NewsID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

    try (PreparedStatement ps = connection.prepareStatement(sqlBuilder.toString())) {
        int parameterIndex = 1;

        if (!fromDate.isEmpty() && !toDate.isEmpty()) {
            ps.setString(parameterIndex++, fromDate);
            ps.setString(parameterIndex++, toDate);
        }

        if (!id.isEmpty()) {
            ps.setString(parameterIndex++, id);
        }

        if (!title.isEmpty()) {
            ps.setString(parameterIndex++, "%" + title + "%");
        }

        if (!content.isEmpty()) {
            ps.setString(parameterIndex++, "%" + content + "%");
        }

        if (!filePath.isEmpty()) {
            ps.setString(parameterIndex++, "%" + filePath + "%");
        }

        int offset = (pageNumber - 1) * newsPerPage;
        ps.setInt(parameterIndex++, offset);
        ps.setInt(parameterIndex++, newsPerPage);

        try (ResultSet rs = ps.executeQuery()) {
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


    public List<NewsDTO> getNewsByPageWithOrderAndEmployee(int pageNumber, int newsPerPage, String employee, String sortOrder) {
        List<NewsDTO> newsList = new ArrayList<>();
        NewsDAO n = new NewsDAO();

        String baseSql = "SELECT * FROM News WHERE 1=1";

        if (employee != null && !employee.isEmpty()) {
            baseSql += " AND CreatedBy = ?";
        }

        baseSql += " ORDER BY NewsID " + (sortOrder != null && sortOrder.equalsIgnoreCase("asc") ? "ASC" : "DESC");
        baseSql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try ( PreparedStatement ps = connection.prepareStatement(baseSql)) {
            int parameterIndex = 1;

            if (employee != null && !employee.isEmpty()) {
                int creatorId = n.findEmployeeIdByFirstName(employee);
                ps.setInt(parameterIndex++, creatorId);
            }

            int offset = (pageNumber - 1) * newsPerPage;
            ps.setInt(parameterIndex++, offset);
            ps.setInt(parameterIndex++, newsPerPage);

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

    public int getTotalNewsCountByEmployeeAndOrder(String employee, String sortOrder) {
        String baseSql = "SELECT COUNT(*) FROM News WHERE 1=1";

        if (employee != null && !employee.isEmpty()) {
            baseSql += " AND CreatedBy = ?";
        }

        String sql = baseSql;

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            int parameterIndex = 1;

            if (employee != null && !employee.isEmpty()) {
                int creatorId = findEmployeeIdByFirstName(employee);
                ps.setInt(parameterIndex++, creatorId);
            }

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void main(String[] args) {
    NewsDAO newsDAO = new NewsDAO();

    // Test getNewsByPageWithSearch
    int pageNumberSearch = 1;           // Adjust the page number based on your requirements
    int newsPerPageSearch = 5;          // Adjust the number of news per page based on your requirements
    String idSearch = "";               // Adjust the id for searching (empty for all)
    String titleSearch = "S";     // Adjust the title for searching
    String contentSearch = "";          // Adjust the content for searching
    String filePathSearch = "";         // Adjust the file path for searching
    String fromDateSearch = "";         // Adjust the from date for searching
    String toDateSearch = "";           // Adjust the to date for searching

    List<NewsDTO> newsByPageWithSearch = newsDAO.getNewsByPageWithSearch(
        pageNumberSearch, newsPerPageSearch, idSearch, titleSearch, contentSearch, filePathSearch, fromDateSearch, toDateSearch
    );

    System.out.println("News on page " + pageNumberSearch + " with search:");
    for (NewsDTO news : newsByPageWithSearch) {
        System.out.println(news.toString());
    }

    // Test getTotalSearchNewsCount
    int totalSearchNewsCount = newsDAO.getTotalSearchNewsCount(
        idSearch, titleSearch, contentSearch, filePathSearch, fromDateSearch, toDateSearch
    );
    System.out.println("Total number of news with search: " + totalSearchNewsCount);
}

}
