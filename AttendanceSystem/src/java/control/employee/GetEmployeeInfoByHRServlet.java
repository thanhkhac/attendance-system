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
import java.util.ArrayList;
import model.*;

/**
 *
 * @author admin
 */
@WebServlet(name = "GetEmployeeInfoByHRServlet", urlPatterns = {"/GetEmployeeInfoByHRServlet"})
public class GetEmployeeInfoByHRServlet extends HttpServlet {

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
        String txt_ID = request.getParameter("EmployeeID");
        EmployeeDAO EmDao = new EmployeeDAO();
        DepartmentDAO DeDao = new DepartmentDAO();
        EmployeeTypeDAO EmTDAO = new EmployeeTypeDAO();
        RoleDAO roleDAO = new RoleDAO();
        ArrayList<DepartmentDTO> listDepartment = new ArrayList<>();
        ArrayList<RoleDTO> listRole = new ArrayList<>();
        ArrayList<EmployeeTypeDTO> listType = new ArrayList<>();
        EmployeeDTO employee = new EmployeeDTO();
        try {
            int id = Integer.parseInt(txt_ID);
            employee = EmDao.getEmployeeDTO(id);
            listDepartment = DeDao.getListDepartment();
            listRole = roleDAO.getRoleList();
            listType = EmTDAO.getEmployeeTypeList();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        request.setAttribute("Employee", employee);
        request.setAttribute("ListDepartment", listDepartment);
        request.setAttribute("ListType", listType);
        request.setAttribute("ListRole", listRole);
        request.getRequestDispatcher("UpdateEmployeeByHR.jsp").forward(request, response);
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
