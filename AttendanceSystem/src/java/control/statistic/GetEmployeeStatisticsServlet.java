/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.statistic;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import model.EmployeeDTO;
import model.StatisticsDAO;
import model.StatisticsDTO;
import ultility.datetimeutil.DateTimeUtil;

/**
 *
 * @author admin
 */
@WebServlet(name = "GetEmployeeStatisticsServlet", urlPatterns = {"/GetEmployeeStatisticsServlet"})
public class GetEmployeeStatisticsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("ACCOUNT");
        LocalDate startDate = DATE_UTIL.parseSqlDate(employee.getStartDate());
        LocalDate endDate = DATE_UTIL.getVNLocalDateNow();
        ArrayList<StatisticsDTO> statistics = new ArrayList<>();
        StatisticsDAO staDAO = new StatisticsDAO();
        try {
            statistics = staDAO.getStatisticInRangeByAJAX(employee.getEmployeeID(), startDate, endDate, 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        int endPage = statistics.size() / 5;
        request.setAttribute("endPage", endPage);
        request.setAttribute("statistics", statistics);
        request.getRequestDispatcher("ViewStatisticsForEmployee.jsp").forward(request, response);
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
