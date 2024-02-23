package control.attendance;

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = ((EmployeeDTO) request.getSession().getAttribute("ACCOUNT")).getEmployeeID();
        OvertimeDAO overtimeDAO = new OvertimeDAO();
        OvertimeDTO currentOT = overtimeDAO.getCurrentOvertime(id);
        //Khai báo biến Inserted để kiểm tra xem đã insert thành công chưa
        boolean isInserted = false;
        //Nếu chưa checkin => check in
        if (currentOT.getCheckIn() == null) {
            isInserted = overtimeDAO.UpdateCheckIn(id);
        } else {
            isInserted = overtimeDAO.UpdateCheckOut(id);
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
