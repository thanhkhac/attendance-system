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
import model.EmployeeDTO;
import model.request.LeaveRequestDAO;

/**
 *
 * @author nguye
 */
@WebServlet(name="DenyLeaveRequestServlet", urlPatterns={"/DenyLeaveRequestServlet"})
public class DenyLeaveRequestServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        out.println("DENY REQUEST SERVLET");
        String button_value = request.getParameter("btAction");
        String leaveRequestID = request.getParameter("leaveRequestID");

        int leaveRequestID_raw = Integer.parseInt(leaveRequestID);
        out.println(" || 1 Leave request ID: " + leaveRequestID_raw);

        LeaveRequestDAO lrDao = new LeaveRequestDAO();
        EmployeeDTO acc = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");

        out.println("|| 2 Role ID : " + acc.getRoleID());
        out.println("|| 3 Employee ID logged in: " + acc.getEmployeeID());
        out.println("|| 4 Button value : " + button_value);
        
        if (button_value.startsWith("Deny")) {
            if (acc.getRoleID() == RoleConstants.MANAGER) { // role id = 4 : Quan li
                out.println("|| RoleID-4(Quan li) : " + acc.getRoleID());
                // Duyet don theo role quan li
                if (lrDao.approvalOfApplicationByManager(0, acc.getEmployeeID(), leaveRequestID_raw)) {
                    response.sendRedirect("ViewLeaveRequestForManager.jsp");
                }
            }else if(acc.getRoleID() == RoleConstants.HR){ // role id = 2 : Quan li nhan su
                out.println("|| RoleID-2(Quan li nhan su) : " + acc.getRoleID());
                // Duyet don theo role quan li nhan su
                if (lrDao.approvalOfApplicationByHr(0, acc.getEmployeeID(), leaveRequestID_raw)) {
                    lrDao.updateStatus(0, leaveRequestID_raw);
                    response.sendRedirect("ViewLeaveRequestForHR.jsp");
                }
            }
        } else {
            out.println("Button value != Deny");
        }
        
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
