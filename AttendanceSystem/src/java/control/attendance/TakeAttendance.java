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

    private static final String URL = "TakeAttendance.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String modalMessage = "";

        if (request.getServerPort() != PortConstants.LOCAL) {
            modalMessage = ("<a href=\"http://localhost:8081/AttendanceSystem/TakeAttendance.jsp\">Vui lòng truy cập vào trang nội bộ</a>");
            request.setAttribute("modalMessage", modalMessage);
            request.getRequestDispatcher(URL).forward(request, response);
            return;
        }

        try {
            int employeeID = ((EmployeeDTO) request.getSession().getAttribute("ACCOUNT")).getEmployeeID();
            int shiftID = Integer.parseInt(request.getParameter("shiftID"));
            TimesheetDAO timesheetDAO = new TimesheetDAO();
            TimesheetDTO currentTS = timesheetDAO.getTimesheet(employeeID, shiftID);
            if (!timesheetDAO.isValid(employeeID, shiftID)) {
                modalMessage = ("Cổng chấm công của ca này hiện tại đang đóng");
                request.setAttribute("modalMessage", modalMessage);
                request.getRequestDispatcher(URL).forward(request, response);
                return;
            }

            //Khai báo biến Inserted để kiểm tra xem đã insert thành công chưa
            boolean isInserted = false;
            //Nếu chưa checkin => check in
            if (currentTS != null) {
                if (currentTS.getCheckin() == null) {
                    isInserted = timesheetDAO.UpdateCheckIn(employeeID, shiftID);
                } else {
                    isInserted = timesheetDAO.UpdateCheckOut(employeeID, shiftID);
                }
            }

            if (isInserted) {
                modalMessage = ("Thành công");
                request.setAttribute("modalMessage", modalMessage);
            } else {
                modalMessage = ("Có lỗi xảy ra, vui lòng thử lại");
                request.setAttribute("modalMessage", modalMessage);
            }

        } catch (Exception e) {
            modalMessage = "Có lỗi xảy ra, vui lòng thử lại";
            e.printStackTrace();
        }
        request.setAttribute("modalMessage", modalMessage);
        request.getRequestDispatcher(URL).forward(request, response);

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
