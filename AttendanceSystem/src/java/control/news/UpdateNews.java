/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.news;

import com.aspose.words.Document;
import com.aspose.words.HtmlSaveOptions;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import model.NewsDAO;
import model.NewsDTO;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author ADMIN-PC
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 10 * 1024 * 1024, // 10 MB
        maxRequestSize = 50 * 1024 * 1024 // 50 MB
)
@WebServlet(name = "UpdateNews", urlPatterns = {"/updatenews"})
public class UpdateNews extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateNews</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateNews at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String pathToGet = "";
            Part filePart = request.getPart("filePath");
            int newId = 0;
            String id = request.getParameter("id");
            try {
                newId = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
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

                        // Process the single file part
                        String fileName = getFileName(filePart);
                        InputStream fileContent = filePart.getInputStream();
                        Files.copy(fileContent, new File(uploadPath + File.separator + fileName).toPath(),
                                StandardCopyOption.REPLACE_EXISTING);

                        response.getWriter().println("File uploaded successfully!");
                        response.getWriter().println(uploadPath);

                        String htmlPath = "/htmlfile/";
                        String uploadHtmlPath = rootPath + File.separator + htmlPath;
                        File uploadHtmlDir = new File(uploadHtmlPath);
                        if (!uploadHtmlDir.exists()) {
                            uploadHtmlDir.mkdir();
                        }

                        // Process the single file part for HTML conversion
                        Document doc = new Document(uploadPath + File.separator + fileName);
                        HtmlSaveOptions saveOptions = new HtmlSaveOptions();
                        saveOptions.setExportImagesAsBase64(true);
                        doc.save(uploadHtmlPath + File.separator + fileName.replaceFirst("[.][^.]+$", "") + ".html", saveOptions);

                        String finalpath = uploadHtmlPath + File.separator + fileName.replaceFirst("[.][^.]+$", "") + ".html";
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
            NewsDAO news = new NewsDAO();
            if (filePart.getSize() == 0) {
                NewsDTO newdto = news.getNewsById(newId);
                pathToGet = newdto.getFilePath();
            }

            System.out.println("path update" + pathToGet);
            String createBy = request.getParameter("updateCreateBy");
            LocalDateTime currentDateTime = LocalDateTime.now();
            Timestamp dateTime = Timestamp.valueOf(currentDateTime);
            boolean duplicateTitle = news.isTitleDuplicate(title);
            boolean updateResult = news.updateNews(newId, title, content, pathToGet, dateTime, createBy);

            if (updateResult) {
                response.sendRedirect("GetAllNewsByHR?successMessage=News update successfully!");
            } else {
                response.sendRedirect("GetAllNewsByHR?errorMessage=Failed to update news. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
