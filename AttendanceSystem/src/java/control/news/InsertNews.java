/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.news;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import model.NewsDAO;

/**
 *
 * @author ADMIN-PC
 */
@WebServlet(name = "InsertNews", urlPatterns = {"/insertNews"})
public class InsertNews extends HttpServlet {

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
            out.println("<title>Servlet InsertNews</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InsertNews at " + request.getContextPath() + "</h1>");
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
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String filePath = request.getParameter("filePath");
        String employeeName = request.getParameter("employee");
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp dateTime = Timestamp.valueOf(currentDateTime);
        NewsDAO news = new NewsDAO();
        boolean duplicateTitle = news.isTitleDuplicate(title);
        if(duplicateTitle){
            request.setAttribute("errorDuplicate", "Your tile is duplicate pls input new one");
            request.getRequestDispatcher("GetAllNewsByHR").forward(request, response);
            return;
        }
        int employId = news.findEmployeeIdByFirstName(employeeName);
        boolean insertionResult = news.insertNews(title, content, filePath, dateTime, employId);
        String redirectURL = request.getContextPath() + "/GetAllNewsByHR";
        if (insertionResult) {
            redirectURL += "?successMessage=" + URLEncoder.encode("News successfully added!", "UTF-8");
        } else {
            redirectURL += "&errorMessage=" + URLEncoder.encode("Failed to add news. Please try again.", "UTF-8");
        }

        response.sendRedirect(redirectURL);
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
