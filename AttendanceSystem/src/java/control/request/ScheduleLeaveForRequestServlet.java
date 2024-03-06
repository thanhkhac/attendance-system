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
import model.EmployeeDTO;
import model.EmployeeGeneral;
import model.request.LeaveRequestDAO;
import model.request.LeaveRequestDTO;

/**
 *
 * @author admin
 */
@WebServlet(name = "ScheduleLeaveForRequestServlet", urlPatterns = {"/ScheduleLeaveForRequestServlet"})
public class ScheduleLeaveForRequestServlet extends HttpServlet {

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
//        response.setContentType("text/html;charset=UTF-8");
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
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("ACCOUNT");
        String txt_requestID = request.getParameter("requestID");
        LeaveRequestDAO lrDAO = new LeaveRequestDAO();
        LeaveRequestDTO leave = new LeaveRequestDTO();
        int requestID = 0;
        String URL = "Error.jsp";
        try {
            int leaveID = leave.getLeaveRequestID();
            requestID = Integer.parseInt(txt_requestID);
            leave = lrDAO.getRequestById(requestID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        boolean rs1 = lrDAO.scheduleLeaveForRequest(leave, employee.getEmployeeID());
        if (rs1) {
            boolean rs2 = lrDAO.updateRequestStatus(requestID);
            if (rs2) {
                URL = "Success.jsp";
            }
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
