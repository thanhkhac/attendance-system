/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.leaverequest;

import authorization.RoleConstants;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import model.EmployeeDTO;
import model.LeaveDAO;
import model.request.LeaveRequestDAO;
import model.request.LeaveRequestDTO;

/**
 *
 * @author nguye
 */
@WebServlet(name = "AcceptLeaveRequestServlet", urlPatterns = {"/AcceptLeaveRequestServlet"})
public class AcceptLeaveRequestServlet extends HttpServlet {

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
        out.println("ACCEPT REQUEST SERVLET ");
        String button_value = request.getParameter("btAction");
        String leaveRequestID = request.getParameter("leaveRequestID");

        int leaveRequestID_raw = Integer.parseInt(leaveRequestID);
        out.println(" || 1 Leave request ID: " + leaveRequestID_raw);

        LeaveRequestDAO lrDAO = new LeaveRequestDAO();
        LeaveRequestDTO lrDTO = new LeaveRequestDTO();
        LeaveDAO lDAO = new LeaveDAO();
        
        EmployeeDTO acc = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");

        lrDTO = lrDAO.getRequestById(leaveRequestID_raw);
        int employeeID = lrDTO.getEmployeeID();
        LocalDate startDate = lrDTO.getStartDate();
        LocalDate endDate = lrDTO.getEndDate();
        String filePath = lrDTO.getFilePath();
        String reason = lrDTO.getReason();
        LocalDate createdDate = lrDTO.getSentDate();
        int createdBy = lrDTO.getCreatedBy();
        
        out.println("|| 2 Role ID : " + acc.getRoleID());
        out.println("|| 3 Employee ID logged in: " + acc.getEmployeeID());
        out.println("|| 4 Button value : " + button_value);
        out.println("|| EmployeeID Leave : " + employeeID);
        out.println("|| StarDate : " + startDate);
        out.println("|| EndDate : " + endDate);
        out.println("|| FilePath : " + filePath);
        out.println("|| Reason : " + reason);
        out.println("|| CreatedDate : " + createdDate);
        out.println("|| CreatedBy : " + createdBy);
        
        if (button_value.startsWith("Accept")) {
            if (acc.getRoleID() == RoleConstants.MANAGER) { // role id = 4 : Quan li
                out.println("|| RoleID-4(Quan li) : " + acc.getRoleID());
                // Duyet don theo role quan li
                if (lrDAO.approvalOfApplicationByManager(1, acc.getEmployeeID(), leaveRequestID_raw)) {
                    response.sendRedirect("ViewLeaveRequestForManager.jsp");
                }
            }else if(acc.getRoleID() == RoleConstants.HR){ // role id = 2 : Quan li nhan su
                out.println("|| RoleID-2(Quan li nhan su) : " + acc.getRoleID());
                // Duyet don theo role quan li nhan su
                if (lrDAO.approvalOfApplicationByHr(1, acc.getEmployeeID(), leaveRequestID_raw)) {
                    lrDAO.updateStatus(1, leaveRequestID_raw);
                    // import Leave khi HR Approve
                    lDAO.insertLeave(employeeID, startDate.toString(), endDate.toString(), filePath, reason, createdDate.toString(), createdBy);
                    response.sendRedirect("ViewLeaveRequestForHR.jsp");
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
