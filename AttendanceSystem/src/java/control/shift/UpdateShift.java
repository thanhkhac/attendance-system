package control.shift;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalTime;
import model.ShiftDAO;
import model.ShiftDTO;

@WebServlet(name = "UpdateShift", urlPatterns = {"/UpdateShift"})
public class UpdateShift extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String URL = "ShiftManagement.jsp";

        String txtShiftID = request.getParameter("shiftID");
        String txtShiftName = request.getParameter("udName");
        String txtShiftStartTime = request.getParameter("udStartTime");
        String txtShiftEndTime = request.getParameter("udEndTime");
        String txtOpenAt = request.getParameter("udOpenAt");
        String txtCloseAt = request.getParameter("udCloseAt");

        int ShiftID = Integer.parseInt(txtShiftID);
        LocalTime startTime = LocalTime.parse(txtShiftStartTime);
        LocalTime endTime = LocalTime.parse(txtShiftEndTime);
        LocalTime openAt = LocalTime.parse(txtOpenAt);
        LocalTime closeAt = LocalTime.parse(txtCloseAt);

        if (openAt.isBefore(startTime) &&
                startTime.isBefore(endTime) &&
                endTime.isBefore(closeAt)) {

            ShiftDTO shift = new ShiftDTO(ShiftID, txtShiftName, startTime, endTime, openAt, closeAt, true);
            ShiftDAO shiftDAO = new ShiftDAO();
            if (shiftDAO.checkConflict(shift) > 0) {
                request.setAttribute("messModal", "Ca làm trong thời gian này đã tồn tại. Vui lòng kiểm tra lại thông tin");
                request.getRequestDispatcher(URL).forward(request, response);
                return;
            }

            shiftDAO.deleteShift(ShiftID);
            int result = shiftDAO.insertShift(shift);
            if (result > 0) {
                request.setAttribute("messModal", "Thành công");
                request.getRequestDispatcher(URL).forward(request, response);
                return;
            } else {
                request.setAttribute("messModal", "Không thành công");
                request.getRequestDispatcher(URL).forward(request, response);
                return;
            }
        } else {
            request.setAttribute("messModal", "Thông tin không hợp lệ, vui lòng nhập lại");
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
