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

@WebServlet(name = "InsertShift", urlPatterns = {"/InsertShift"})
public class InsertShift extends HttpServlet {

    String URL = "ShiftManagement";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        

        String txtShiftName = request.getParameter("txtShiftName");
        String txtShiftStartTime = request.getParameter("txtShiftStartTime");
        String txtShiftEndTime = request.getParameter("txtShiftEndTime");
        String txtOpenAt = request.getParameter("txtOpenAt");
        String txtCloseAt = request.getParameter("txtCloseAt");

        LocalTime startTime = LocalTime.parse(txtShiftStartTime);
        LocalTime endTime = LocalTime.parse(txtShiftEndTime);
        LocalTime openAt = LocalTime.parse(txtOpenAt);
        LocalTime closeAt = LocalTime.parse(txtCloseAt);

        if (openAt.isBefore(startTime) &&
                startTime.isBefore(endTime) &&
                endTime.isBefore(closeAt)) {

            ShiftDTO shift = new ShiftDTO(0, txtShiftName, startTime, endTime, openAt, closeAt, true);
            ShiftDAO shiftDAO = new ShiftDAO();
            if (shiftDAO.checkConflict(shift) > 0) {
                request.setAttribute("error", "Ca làm trong thời gian này đã tồn tại. Vui lòng kiểm tra lại thông tin");
                request.getRequestDispatcher(URL).forward(request, response);
                return;
            }

            int result = shiftDAO.insertShift(shift);
            if (result > 0) {
                response.sendRedirect(URL + "?success=\"\"");
                return;
            } else {
                request.setAttribute("error", "Không thành công");
                request.getRequestDispatcher(URL).forward(request, response);
                return;
            }
        } else {
            request.setAttribute("error", "Thông tin không hợp lệ, vui lòng kiểm tra lại");
            request.getRequestDispatcher(URL).forward(request, response);
            return;
        }

//        outWriter.print(txtShiftName);
//        outWriter.print(txtShiftStartTime);
//        outWriter.print(txtShiftEndTime);
//        outWriter.print(txtOpenAt);
//        outWriter.print(txtCloseAt);
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
