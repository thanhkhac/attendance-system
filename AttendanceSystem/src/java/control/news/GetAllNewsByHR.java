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
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.EmployeeDTO;
import model.NewsDAO;
import model.NewsDTO;

/**
 *
 * @author ADMIN-PC
 */
@WebServlet(name = "GetAllNewsByHR", urlPatterns = {"/GetAllNewsByHR"})
public class GetAllNewsByHR extends HttpServlet {

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
        String successMessage = request.getParameter("successMessage");
        String errorMessage = request.getParameter("errorMessage");
        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
        } else if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }
         HttpSession session = request.getSession();
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("ACCOUNT");
        request.setAttribute("curentEmployee", employee.getFirstName());
        NewsDAO newsdao = new NewsDAO();
        List<EmployeeDTO> optionCreateBy = newsdao.findAllEmployees();
        request.setAttribute("employees", optionCreateBy);

        // Pagination
        int newsPerPage = 5;
        int currentPage = 1;
        String pageParam = request.getParameter("page");

        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        List<NewsDTO> newlist = newsdao.getNewsByPage(currentPage, newsPerPage);
        int totalNews = newsdao.getTotalNewsCount();
        int totalPages = (int) Math.ceil((double) totalNews / newsPerPage);
        request.setAttribute("listN", newlist);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("ManageNews.jsp").forward(request, response);
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