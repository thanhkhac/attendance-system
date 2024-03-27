package control.attendance;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.*;

@WebServlet(name = "Attendance", urlPatterns = {"/Attendance"})
public class Attendance extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");
        OvertimeDAO overtimeDAO = new OvertimeDAO();

        OvertimeDTO overtime = new OvertimeDAO().getCurrentOvertimeByDay(employeeDTO.getEmployeeID());
        ArrayList<TimesheetDTO> timesheet = new TimesheetDAO().getCurrentTimesheet(employeeDTO.getEmployeeID());

        ArrayList<ShiftDTO> shifts = new ShiftDAO().getAllShiftDTO();
        request.setAttribute("overtime", overtime);
        request.setAttribute("timesheet", timesheet);
        request.setAttribute("shifts", shifts);
        request.getRequestDispatcher("TakeAttendance.jsp").forward(request, response);
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
