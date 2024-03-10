package control.attendance;

import authorization.PortConstants;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

@WebServlet(name = "TakeAttendance", urlPatterns = {"/TakeAttendance"})
public class TakeAttendance extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getServerPort() == PortConstants.ONLINE ) {
            response.getWriter().write("invalidPort");
            return;
        }

        int id = ((EmployeeDTO) request.getSession().getAttribute("ACCOUNT")).getEmployeeID();
        TimesheetDAO timesheetDAO = new TimesheetDAO();
        TimesheetDTO currentTS = timesheetDAO.getCurrentTimesheet(id);

        //Khai báo biến Inserted để kiểm tra xem đã insert thành công chưa
        boolean isInserted = false;
        //Nếu chưa checkin => check in
        if (currentTS != null) {
            if (currentTS.getCheckin() == null) {
                isInserted = timesheetDAO.UpdateCheckIn(id);
            } else {
                isInserted = timesheetDAO.UpdateCheckOut(id);
            }
        }

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
