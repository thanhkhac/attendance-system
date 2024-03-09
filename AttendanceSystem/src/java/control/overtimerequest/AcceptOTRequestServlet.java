/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.overtimerequest;

import authorization.RoleConstants;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalTime;
import model.*;
import model.request.*;

/**
 *
 * @author nguye
 */
@WebServlet(name = "AcceptOTRequestServlet", urlPatterns = {"/AcceptOTRequestServlet"})
public class AcceptOTRequestServlet extends HttpServlet {

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

        PrintWriter out = response.getWriter();
        out.println("ACCEPT SERVLET OT REQUEST");

        String button_value = request.getParameter("btAction");
        String overTimeRequestID = request.getParameter("overTimeRequestID");

        int overTimeRequestID_raw = Integer.parseInt(overTimeRequestID);
        out.println(" || 1 Leave request ID: " + overTimeRequestID_raw);

        OverTimeRequestDAO otrqDAO = new OverTimeRequestDAO();
        OverTimeRequestDTO otrqDTO = new OverTimeRequestDTO();
        OvertimeDTO otDTO = new OvertimeDTO();
        OvertimeDAO otDAO = new OvertimeDAO();
        EmployeeDTO acc = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");

        otrqDTO = otrqDAO.getOverTimeRequestByID(overTimeRequestID_raw);
        String DateOT = otrqDTO.getDate().toString();
        int employeeID_OT = otrqDTO.getEmployeeID();
        LocalTime startTimeOT = otrqDTO.getStartTime();
        LocalTime endTimeOT = otrqDTO.getEndTime();

        LocalTime Open = startTimeOT.minusMinutes(30);
        LocalTime Close = endTimeOT.plusMinutes(30);

        if (Close.isAfter(LocalTime.parse("23:29"))) {
            Close = LocalTime.parse("23:59");
        }
        if (Open.isBefore(LocalTime.parse("00:30"))) {
            Open = LocalTime.parse("00:00");
        }

        out.println("|| 2 Role ID : " + acc.getRoleID());
        out.println("|| 3 Employee ID logged in: " + acc.getEmployeeID());
        out.println("|| 4 Button value : " + button_value);
        out.println("|| Ngay OT: " + DateOT);
        out.println("|| employeeID OT: " + employeeID_OT);
        out.println("|| OT Start Time : " + startTimeOT);
        out.println("|| OT End Time : " + endTimeOT);

        if (button_value.startsWith("Accept")) {
            if (acc.getRoleID() == RoleConstants.MANAGER) { // role id = 4 : Quan li
                out.println("|| RoleID-4(Quan li) : " + acc.getRoleID());
                // Duyet don theo role quan li
                if (otrqDAO.overtimeRequestApproveByManager(1, acc.getEmployeeID(), overTimeRequestID_raw)) {
                    response.sendRedirect("ViewOverTimeRequestForManager.jsp");
                }
            } else if (acc.getRoleID() == RoleConstants.HR) { // role id = 2 : Quan li nhan su
                out.println("|| RoleID-2(Quan li nhan su) : " + acc.getRoleID());
                // Duyet don theo role quan li nhan su
                if (otrqDAO.overtimeRequestApproveByHr(1, acc.getEmployeeID(), overTimeRequestID_raw)) {
                    otDAO.insertOvertime(DateOT, employeeID_OT, startTimeOT.toString(), endTimeOT.toString(), Open.toString(), Close.toString(), null, null, acc.getEmployeeID());
                    out.println("Insert into OverTime successful");
                    response.sendRedirect("ViewOverTimeRequestForHR.jsp");
                }
            } else if (acc.getRoleID() == RoleConstants.MANAGER_HR) { //role id = 5 : quan li kiem quan li nhan su
                out.println("|| RoleID-5(HR_Manager)");
                if (otrqDAO.overtimeRequestApproveByManager(1, acc.getEmployeeID(), overTimeRequestID_raw)) {
                    response.sendRedirect("ViewOverTimeRequestForManager.jsp");
                } else if (otrqDAO.overtimeRequestApproveByHr(1, acc.getEmployeeID(), overTimeRequestID_raw)) {
                    otDAO.insertOvertime(DateOT, employeeID_OT, startTimeOT.toString(), endTimeOT.toString(), Open.toString(), Close.toString(), null, null, acc.getEmployeeID());
                    out.println("Insert into OverTime successful");
                    response.sendRedirect("ViewOverTimeRequestForHR.jsp");
                }
            }
        } else {
            out.println("Button value != Accept");
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
