/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.department;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
@WebServlet(name = "DepartmentServlet", urlPatterns = {"/DepartmentServlet"})
public class DepartmentController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<DepartmentDTO> list = (List<DepartmentDTO>) session.getAttribute("listDepartment");
        if (list == null) {
            DepartmentDAO departmentDAO = new DepartmentDAO();
            list = departmentDAO.getListDepartment();
        }
        EmployeeDAO employeeDAO = new EmployeeDAO();
        ArrayList<EmployeeGeneral> listEmployee = employeeDAO.getEmployeeInfo();
        request.setAttribute("listDepartment", list);
        request.setAttribute("listEmployee", listEmployee);
        request.getRequestDispatcher("ViewDepartment.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        List<DepartmentDTO> list = null;
        switch (action) {
            case "delete":
                list = delete(request, response);
                break;
            case "edit":
                list = edit(request, response);
                break;
            case "add":
                list = add(request, response);
                break;
            case "search":
                list = search(request, response);
                break;
            default:
                list = new DepartmentDAO().getListDepartment();
                break;
        }
        //set to session
        request.getSession().setAttribute("listDepartment", list);
        response.sendRedirect("DepartmentServlet");
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
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
    
    private List<DepartmentDTO> delete(HttpServletRequest request, HttpServletResponse response) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        int departmentId = Integer.parseInt(request.getParameter("id"));
        boolean isDelete = departmentDAO.deleteById(departmentId);
        return departmentDAO.getListDepartment();
    }
    
    private List<DepartmentDTO> edit(HttpServletRequest request, HttpServletResponse response) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        int departmentId = Integer.parseInt(request.getParameter("departmentId"));
        String name = request.getParameter("departmentName");
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentID(departmentId);
        dto.setName(name);
        boolean isEditSuccess = departmentDAO.edit(dto);
        return departmentDAO.getListDepartment();
        
    }
    
    private List<DepartmentDTO> add(HttpServletRequest request, HttpServletResponse response) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        String name = request.getParameter("departmentName");
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName(name);
        boolean isAddSuccess = departmentDAO.addDepartment(dto);
        return departmentDAO.getListDepartment();
    }
    
    private List<DepartmentDTO> search(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("keyword");
        DepartmentDAO departmentDAO = new DepartmentDAO();
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName(keyword);
        List<DepartmentDTO> list = departmentDAO.searchByName(dto);
        System.out.println(list.toString());
        return list;
    }
}
