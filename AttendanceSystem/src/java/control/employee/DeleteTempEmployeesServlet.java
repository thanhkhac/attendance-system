/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.employee;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.EmployeeDTO;

/**
 *
 * @author admin
 */
@WebServlet(name = "DeleteTempEmployeesServlet", urlPatterns = {"/DeleteTempEmployeesServlet"})
public class DeleteTempEmployeesServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        ArrayList<EmployeeDTO> employees = (ArrayList<EmployeeDTO>) session.getAttribute("employees");
        ArrayList<EmployeeDTO> isError = (ArrayList<EmployeeDTO>) session.getAttribute("isError");
        ArrayList<EmployeeDTO> isAcceptable = (ArrayList<EmployeeDTO>) session.getAttribute("isAcceptable");
        ArrayList<EmployeeDTO> isError_deleted = new ArrayList<>();
        ArrayList<EmployeeDTO> employees_deleted = new ArrayList<>();

        for (EmployeeDTO e : employees) {
            employees_deleted.add(e);
        }
        for(EmployeeDTO e: isError){
            employees_deleted.remove(e);
        }

        session.setAttribute("employees", employees_deleted);
        session.setAttribute("isError", isError_deleted);
        request.setAttribute("ErrorMSG", "Deleted [" + isError.size() + "] Employee Successfully !");
        request.getRequestDispatcher("ImportEmployees.jsp").forward(request, response);
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
