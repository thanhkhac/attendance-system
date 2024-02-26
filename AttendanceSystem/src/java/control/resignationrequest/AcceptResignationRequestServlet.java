/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.resignationrequest;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.EmployeeDTO;
import model.request.ResignationRequestDAO;

/**
 *
 * @author nguye
 */
@WebServlet(name = "AcceptResignationRequestServlet", urlPatterns = {"/AcceptResignationRequestServlet"})
public class AcceptResignationRequestServlet extends HttpServlet {

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
        out.println("ACCEPT RESIGNATION SERVLET ");
        String button_value = request.getParameter("btAction");
        String resignationRequestID = request.getParameter("resignationRequestID");

        int resignationRequestID_raw = Integer.parseInt(resignationRequestID);
        out.println("|| 1 Resignation Request ID : " + resignationRequestID_raw);

        ResignationRequestDAO rrDao = new ResignationRequestDAO();
        EmployeeDTO acc = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");

        out.println("|| 2 Role ID : " + acc.getRoleID());
        out.println("|| 3 Employee ID logged in: " + acc.getEmployeeID());

        if (button_value.startsWith("Accept")) {
            out.println("|| 4 Button value : " + button_value);
            
            if (acc.getRoleID() == 4) { // role
                // Duyet don theo role quan li
                out.println(" || 5 Role-ID-4(Quanli) : " + acc.getRoleID());
                if (rrDao.approvalOfResignationByManager(1, acc.getEmployeeID(), resignationRequestID_raw)){  
                    //.................
                    response.sendRedirect("ViewResignationRequestByManager.jsp");
                }
            } else if (acc.getRoleID() == 2) {
                // Duyet don theo role quan li nhan su
                out.println(" || 5 Role-ID-2(Quanlinhansu) : " + acc.getRoleID());
                if (rrDao.approvalOfResignationByHR(1, acc.getEmployeeID(), resignationRequestID_raw)) { //.................
                    response.sendRedirect("ViewResignationRequestByHR.jsp");
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
