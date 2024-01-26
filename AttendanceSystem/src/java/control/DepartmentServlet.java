/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package control;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.DepartmentDAO;
import model.DepartmentDTO;
import model.EmployeeDAO;
import model.EmployeeDTO;
import model.EmployeeGeneral;

/**
 *
 * @author acer
 */
@WebServlet(name="DepartmentServlet", urlPatterns={"/DepartmentServlet"})
public class DepartmentServlet extends HttpServlet {
   
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<DepartmentDTO> listDepartment = departmentDAO.getListDepartment();
        ArrayList<EmployeeGeneral> listEmployee = employeeDAO.getEmployeeInfo();

        request.setAttribute("listDepartment", listDepartment);
        request.setAttribute("listEmployee", listEmployee);
        request.getRequestDispatcher("ViewDepartment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "delete":
                delete(request, response);
                break;
            case "edit":
                edit(request, response);
                break;
            case "add":
                add(request, response);
                break;
            default:

        }
        response.sendRedirect("department");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DepartmentController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DepartmentController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        int departmentId = Integer.parseInt(request.getParameter("id"));
        boolean isDelete = departmentDAO.deleteById(departmentId);
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        int departmentId = Integer.parseInt(request.getParameter("departmentId"));
        String name = request.getParameter("departmentName");
         DepartmentDTO dto = new DepartmentDTO();
//        dto.setDepartmentID(departmentId);
//        dto.setName(name);
        boolean isEditSuccess = departmentDAO.edit(dto);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        String name = request.getParameter("departmentName");
        DepartmentDTO dto = new DepartmentDTO();
//        dTO.setName(name);
        boolean isAddSuccess = departmentDAO.addDepartment(dto);
    }
}
