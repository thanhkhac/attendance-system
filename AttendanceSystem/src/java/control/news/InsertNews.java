package control.news;

import com.aspose.words.Document;
import com.aspose.words.HtmlSaveOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.NewsDAO;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 10 * 1024 * 1024, // 10 MB
        maxRequestSize = 50 * 1024 * 1024 // 50 MB
)
public class InsertNews extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Your existing processRequest method logic
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String pathToGet = "";

        Part filePart = request.getPart("filePath");

        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                try {
                    String relativePath = "/docxfile/";
                    String rootPath = getServletContext().getRealPath("");
                    String uploadPath = rootPath + relativePath;
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }

                    String fileName = getFileName(filePart);
                    String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                    String extension = fileName.substring(fileName.lastIndexOf('.'));
                    String uniqueFileName = getUniqueFileName(uploadPath, baseName, extension);
                    Path targetPath = new File(uploadPath + File.separator + uniqueFileName).toPath();

                    InputStream fileContent = filePart.getInputStream();
                    Files.copy(fileContent, targetPath, StandardCopyOption.REPLACE_EXISTING);

                    String htmlPath = "/htmlfile/";
                    String uploadHtmlPath = rootPath + File.separator + htmlPath;
                    File uploadHtmlDir = new File(uploadHtmlPath);
                    if (!uploadHtmlDir.exists()) {
                        uploadHtmlDir.mkdir();
                    }

                    // Process the single file part for HTML conversion
                    Document doc = new Document(uploadPath + File.separator + uniqueFileName);
                    HtmlSaveOptions saveOptions = new HtmlSaveOptions();
                    saveOptions.setExportImagesAsBase64(true);
                    doc.save(uploadHtmlPath + File.separator + uniqueFileName.replaceFirst("[.][^.]+$", "") + ".html", saveOptions);

                    String finalpath = uploadHtmlPath + File.separator + uniqueFileName.replaceFirst("[.][^.]+$", "") + ".html";
                    System.out.println(finalpath);
                    pathToGet = finalpath;
                    System.out.println("path:" + pathToGet);
                } catch (AccessDeniedException e) {
                    e.getMessage();
                }
            } else {
                response.getWriter().println("Invalid request!");
            }
        } catch (Exception e) {
            log("Error during file processing: " + e.getMessage(), e);
        }

        if (filePart == null) {
            pathToGet = "";
        }
        String employeeName = request.getParameter("employee");
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp dateTime = Timestamp.valueOf(currentDateTime);
        NewsDAO news = new NewsDAO();
        boolean duplicateTitle = news.isTitleDuplicate(title);

        if (duplicateTitle) {
            request.setAttribute("errorDuplicate", "Your title is duplicate, please input a new one");
            request.getRequestDispatcher("GetAllNewsByHR").forward(request, response);
            return;
        }

        int employId = news.findEmployeeIdByFirstName(employeeName);
        boolean insertionResult = news.insertNews(title, content, pathToGet, dateTime, employId);
        String redirectURL = request.getContextPath() + "/GetAllNewsByHR";

        if (insertionResult) {
            redirectURL += "?successMessage=" + URLEncoder.encode("News successfully added!", "UTF-8");
        } else {
            redirectURL += "&errorMessage=" + URLEncoder.encode("Failed to add news. Please try again.", "UTF-8");
        }

        response.sendRedirect(redirectURL);
    }

    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public static String getUniqueFileName(String directory, String baseFileName, String fileExtension) {
        String uniqueFileName = baseFileName + fileExtension;
        int count = 1;

        File file = new File(directory, uniqueFileName);
        while (file.exists()) {
            uniqueFileName = baseFileName + "_" + count + fileExtension;
            file = new File(directory, uniqueFileName);
            count++;
        }

        return uniqueFileName;
    }

    @Override
    public String getServletInfo() {
        return "InsertNews Servlet";
    }
}
