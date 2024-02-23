/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.request;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import model.EmployeeDTO;
import model.ResignationRequestDAO;
import model.SendRequestError;

/**
 *
 * @author admin
 */
@WebServlet(name = "InsertResignationRequestServlet", urlPatterns = {"/InsertResignationRequestServlet"})
public class InsertResignationRequestServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private LocalDate timeAfterNMonths(LocalDate time, int monthToAdd) {
        LocalDate afterNMonth = time.plusMonths(monthToAdd);
        return afterNMonth;
    }

    private LocalDate timeBeforeMonths(LocalDate time, int monthToSub) {
        LocalDate afterNMonth = time.minusMonths(monthToSub);
        return afterNMonth;
    }

    private boolean isAcceptableDate(LocalDate startDateContract, LocalDate endDateContract, LocalDate extendDate) {
        LocalDate current = LocalDate.now();
        if (current.isEqual(endDateContract) || current.isBefore(endDateContract)) { //current>=endDateContract (chua het han hop dong cu)
            System.out.println("1");
            if (current.isAfter(startDateContract) && current.isAfter(timeBeforeMonths(endDateContract, 1))) { //current > startDate, current > endate-1 month, gia han truoc endDate 1 thang
                System.out.println("2");
                if (extendDate.isAfter(timeAfterNMonths(endDateContract, 6))
                        || extendDate.isEqual(timeAfterNMonths(endDateContract, 6))) { // Gia han it nhat 6 thang
                    System.out.println("3");
                    if (extendDate.isEqual(timeAfterNMonths(endDateContract, 24))
                            || extendDate.isBefore(timeAfterNMonths(endDateContract, 24))) { // Gia han nhieu nhat 2 nam
                        System.out.println("4");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        doPost(request, response);
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
        HttpSession session = request.getSession();
        EmployeeDTO account = (EmployeeDTO) session.getAttribute("ACCOUNT");
        LocalDate extendDate = LocalDate.parse(request.getParameter("extensionDate"));
//        String extendDate = request.getParameter("extensionDate");
        String reason = request.getParameter("reason");
        LocalDate startDateContract = new Date(account.getStartDate().getTime()).toLocalDate();
        LocalDate endDateContract = new Date(account.getEndDate().getTime()).toLocalDate();
        LocalDate current = LocalDate.now();
        SendRequestError err = new SendRequestError();
        ResignationRequestDAO dao = new ResignationRequestDAO();
        boolean isErr = false;
        String URL = "Error.jsp";
        if (!isAcceptableDate(startDateContract, endDateContract, extendDate)) {
            isErr = true;
            err.setInvalidDate_error("Khoảng Thời Gian Không Hợp Lệ !");
        }
        if (extendDate == null || reason.length() <= 0) {
            isErr = true;
            err.setNullValue_error("Không Được Bỏ Trống !");
        }
        if (reason.length() < 1 || reason.length() > 250) {
            isErr = true;
            err.setReasonLength_error("Lý Do [1-250] kí tự");
        }
        if (!isErr) {
            boolean rs = dao.insertResignationRequest(account, current, extendDate, reason);
            if (rs) {
                URL = "Success.jsp";
            } else {
                URL = "Error.jsp";
            }
        } else {
            request.setAttribute("extensionDate", extendDate);
            request.setAttribute("reason", reason);
            request.setAttribute("error", err);
            request.setAttribute("msg", "Gửi Yêu Cầu Thất Bại !");
            URL = "PrepareRequestServlet?requestTypeID=3";
        }
        request.getRequestDispatcher(URL).forward(request, response);

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
