/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.news;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import model.NewsDAO;
import model.NewsDTO;

/**
 *
 * @author ADMIN-PC
 */
public class GetDetailNew extends HttpServlet {

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
        String newIdParam = request.getParameter("newId");
        if (newIdParam != null && !newIdParam.isEmpty()) {
            try {
                int newId = Integer.parseInt(newIdParam);
                NewsDAO newdao = new NewsDAO();
                NewsDTO newdto = newdao.getNewsById(newId);
                List<NewsDTO> othernew = newdao.getOtherNews(newId);

                try {
                    String filePath = newdto.getFilePath();
                    byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
                    if (fileBytes.length > 0) {
                        String htmlContent = new String(fileBytes);
                        request.setAttribute("htmlContent", htmlContent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    request.setAttribute("htmlContent", "Error reading file content");
                }

                request.setAttribute("news", newdto);
                request.setAttribute("otherNews", othernew);
                request.getRequestDispatcher("ViewNewsDetail.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid newId format.");
            }
        } else {
            response.getWriter().println("Missing newId parameter.");
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
        processRequest(request, response);
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
