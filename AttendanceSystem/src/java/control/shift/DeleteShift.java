package control.shift;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ShiftDAO;

@WebServlet(name = "DeleteShift", urlPatterns = {"/DeleteShift"})
public class DeleteShift extends HttpServlet {

    String URL = "ShiftManagement.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String txtID = request.getParameter("ID");
            int ID = Integer.parseInt(txtID);
            if (new ShiftDAO().deleteShift(ID) > 0) {
                request.setAttribute("messModal", "Thành công");
                request.getRequestDispatcher(URL).forward(request, response);
                return;
            } else {
                request.setAttribute("messModal", "Không thành công");
                request.getRequestDispatcher(URL).forward(request, response);
                return;
            }
        } catch (Exception e) {
            request.setAttribute("messModal", "Có lỗi xảy ra, vui lòng thử lại");
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
