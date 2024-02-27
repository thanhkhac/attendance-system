/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package model.request;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import model.*;
import model.request.*;

/**
 *
 * @author admin
 */
@WebServlet(name = "ScheduleLeaveRequestServlet", urlPatterns = {"/ScheduleLeaveRequestServlet"})
public class ScheduleLeaveRequestServlet extends HttpServlet {

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
        String requestID = request.getParameter("requestID");
        TimesheetDAO timeDAO = new TimesheetDAO();
        OvertimeDAO overtimeDAO = new OvertimeDAO();
        LeaveRequestDAO leaveDAO = new LeaveRequestDAO();
        EmployeeDAO emDAO = new EmployeeDAO();
        ArrayList<TimesheetDTO> listTimeSheet = new ArrayList<>();
        ArrayList<OvertimeDTO> listOverTime = new ArrayList<>();
        LeaveRequestDTO leave = new LeaveRequestDTO();
        EmployeeGeneral employee = new EmployeeGeneral();
        try {
            int id = Integer.parseInt(requestID);
            leave = leaveDAO.getRequestById(id);
            employee = emDAO.getEmployeeGeneral(leave.getEmployeeID());
            listTimeSheet = timeDAO.getTimesheetInRange(leave.getEmployeeID(), leave.getStartDate(), leave.getEndDate());
            listOverTime = overtimeDAO.getOverTimeByRange(leave.getEmployeeID(), leave.getStartDate(), leave.getEndDate());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("employee", employee);
        request.setAttribute("leave", leave);
        request.setAttribute("listTimeSheet", listTimeSheet);
        request.setAttribute("listOverTime", listOverTime);
        request.getRequestDispatcher("ScheduleLeave.jsp").forward(request, response);
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