package control.schedulework;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.EmployeeDTO;
import model.TimesheetDAO;

@WebServlet(name = "DeleteTimesheetForEmployees", urlPatterns = {"/DeleteTimesheetForEmployees"})
public class DeleteTimesheetForEmployees extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String[] raw_employeeIDs = request.getParameterValues("chkEmployeeID");
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));
        EmployeeDTO User = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");
        
        boolean isInserted = new TimesheetDAO().deleteTimesheets(raw_employeeIDs, month, year);
        if (isInserted) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("error");
        }
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
