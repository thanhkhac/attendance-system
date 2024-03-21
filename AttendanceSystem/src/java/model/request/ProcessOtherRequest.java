package model.request;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

@WebServlet(name = "ProcessOtherRequest", urlPatterns = {"/ProcessOtherRequest"})
public class ProcessOtherRequest extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int requestId = Integer.parseInt(request.getParameter("requestId"));
        boolean status = Boolean.parseBoolean(request.getParameter("status")); // status sẽ là true hoặc false, tùy thuộc vào việc duyệt hay từ chối
        String processNote = request.getParameter("processnote");
        int respondedBy = ((EmployeeDTO) request.getSession().getAttribute("ACCOUNT")).getEmployeeId();

        // Gọi phương thức cập nhật trạng thái đơn từ DAO
        RequestDAO requestDAO = new RequestDAO();
        int result = requestDAO.updateRequestStatus(requestId, status, processNote, respondedBy);
        String referer = request.getHeader("referer");
        response.sendRedirect(referer);
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
