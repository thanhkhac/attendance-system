/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.employee;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
@WebServlet(name = "GetAllEmployeeByHRServlet", urlPatterns = {"/GetAllEmployeeByHRServlet"})
public class GetAllEmployeeByHRServlet extends HttpServlet {

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
        EmployeeDAO dao = new EmployeeDAO();
        DepartmentDAO deDao = new DepartmentDAO();
        EmployeeTypeDAO employeeTypeDao = new EmployeeTypeDAO();
        RoleDAO roleDao = new RoleDAO();
        ArrayList<DepartmentDTO> listDepartment = deDao.getListDepartment();
        ArrayList<EmployeeTypeDTO> listEmType = employeeTypeDao.getEmployeeTypeList();
        ArrayList<RoleDTO> listRole = roleDao.getRoleList();
        ArrayList<EmployeeGeneral> list = dao.getEmployeeInfo();

        request.setAttribute("departments", listDepartment);
        request.setAttribute("types", listEmType);
        request.setAttribute("roles", listRole);
        request.setAttribute("List", list);
        request.getRequestDispatcher("ViewAllEmployee.jsp").forward(request, response);

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
