package control.schedulework;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.*;
import ultility.datetimeutil.DateTimeUtil;

@WebServlet(name = "ScheduledEmployees", urlPatterns = {"/ScheduledEmployees"})
public class ScheduledEmployees extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DateTimeUtil dateTimeUtil = new DateTimeUtil();

        int year = dateTimeUtil.getVNLocalDateNow().getYear();
        int month = dateTimeUtil.getVNLocalDateNow().getMonthValue();
        String txtyear = request.getParameter("year");
        String txtmonth = request.getParameter("month");
        if (txtyear != null || txtmonth != null) {
            try {
                year = Integer.parseInt(txtyear);
                month = Integer.parseInt(txtmonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                month = Integer.parseInt(txtmonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<EmployeeDTO> EmployeeList = new EmployeeDAO().getScheduledEmployees(month, year);

        ArrayList<EmployeeTypeDTO> employeeTypeList = new EmployeeTypeDAO().getEmployeeTypeList();
        ArrayList<DepartmentDTO> listDepartment = new DepartmentDAO().getListDepartment();
        ArrayList<RoleDTO> roleList = new RoleDAO().getRoleList();

        request.setAttribute("EmployeeList", EmployeeList);
        request.setAttribute("employeeTypeList", employeeTypeList);
        request.setAttribute("listDepartment", listDepartment);
        request.setAttribute("roleList", roleList);
        request.setAttribute("month", month);
        request.setAttribute("year", year);

        request.getRequestDispatcher("ScheduledEmployees.jsp").forward(request, response);

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
