package control.schedulework;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import model.DepartmentDAO;
import model.DepartmentDTO;
import model.EmployeeDAO;
import model.EmployeeDTO;
import model.EmployeeTypeDAO;
import model.EmployeeTypeDTO;
import model.RoleDAO;
import model.RoleDTO;

@WebServlet(name = "GetUnscheduleEmployees", urlPatterns = {"/GetUnscheduleEmployees"})
public class GetUnscheduleEmployees extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));
        
        ArrayList<EmployeeDTO> UnscheduleEmployees = new EmployeeDAO().GetUnscheduleEmployees(month, year);
        
        ArrayList<EmployeeTypeDTO> employeeTypeList = new EmployeeTypeDAO().getEmployeeTypeList();
        ArrayList<DepartmentDTO> listDepartment = new DepartmentDAO().getListDepartment();
        ArrayList<RoleDTO> roleList = new RoleDAO().getRoleList();
        
        request.setAttribute("UnscheduleEmployees", UnscheduleEmployees);
        request.setAttribute("employeeTypeList", employeeTypeList);
        request.setAttribute("listDepartment", listDepartment);
        request.setAttribute("roleList", roleList);
        request.getRequestDispatcher("ScheduleWorkEmployees.jsp").forward(request, response);
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
// </editor-fold>

}
