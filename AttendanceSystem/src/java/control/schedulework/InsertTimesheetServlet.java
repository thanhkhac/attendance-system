package control.schedulework;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.EmployeeDTO;
import model.OvertimeDAO;
import model.OvertimeDTO;
import model.TimesheetDAO;

@WebServlet(name = "InsertTimesheetServlet", urlPatterns = {"/InsertTimesheetServlet"})
public class InsertTimesheetServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] raw_shifts = request.getParameterValues("shift");
        String[] raw_employeeIDs = request.getParameterValues("chkEmployeeID");
        EmployeeDTO User = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");

        boolean isInserted = new TimesheetDAO().insertTimesheet(raw_shifts, raw_employeeIDs, User.getEmployeeID());
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
