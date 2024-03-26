package control.attendance;

import authorization.PortConstants;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.EmployeeDTO;
import model.OvertimeDAO;
import model.OvertimeDTO;

@WebServlet(name = "TakeAttendanceOvertime", urlPatterns = {"/TakeAttendanceOvertime"})
public class TakeAttendanceOvertime extends HttpServlet {

    private static final String URL = "TakeAttendance.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getServerPort() == PortConstants.ONLINE) {
            request.setAttribute("modalMessage", "<a href=\"http://localhost:8081/AttendanceSystem/TakeAttendance.jsp\">Vui lòng truy cập vào trang nội bộ</a>");
            request.getRequestDispatcher(URL).forward(request, response);
            return;
        }

        int id = ((EmployeeDTO) request.getSession().getAttribute("ACCOUNT")).getEmployeeID();
        OvertimeDAO overtimeDAO = new OvertimeDAO();
        OvertimeDTO currentOT = overtimeDAO.getCurrentOvertimeByDay(id);

        if (overtimeDAO.getCurrentOvertime(id) == null) {
            request.setAttribute("modalMessage", "Cổng chấm công của ca này hiện tại đang đóng");
            request.getRequestDispatcher(URL).forward(request, response);
            return;
        }
        boolean isInserted = false;
        if (currentOT.getCheckIn() == null) {
            isInserted = overtimeDAO.UpdateCheckIn(id);
        } else {
            isInserted = overtimeDAO.UpdateCheckOut(id);
        }
        if (isInserted) {
            request.setAttribute("modalMessage", "Thành công");
            request.getRequestDispatcher(URL).forward(request, response);
            return;
        } else {
            request.setAttribute("modalMessage", "Không thành công");
            request.getRequestDispatcher(URL).forward(request, response);
            return;
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
