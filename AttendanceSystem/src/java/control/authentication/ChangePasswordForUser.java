/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.authentication;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.EmployeeDTO;
import model.UserDAO;

/**
 *
 * @author ADMIN-PC
 */
@WebServlet(name = "ChangePasswordForUser", urlPatterns = {"/changepassforuser"})
public class ChangePasswordForUser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangePasswordForUser</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePasswordForUser at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,16}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    private boolean validatePassword(String password) {
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("ACCOUNT");

        if (employee == null) {
            request.setAttribute("errorNoEmployee", "Session expired or user not logged in");
            request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
            return;
        }

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        if (!validatePassword(newPassword)) {
            request.setAttribute("errorInvalidPassword", "Mật khẩu mới không hợp lệ. Yêu cầu: [6-16] kí tự, ít nhất 1 chữ cái in hoa, 1 số và 1 kí tự đặc biệt.");
            request.getRequestDispatcher("ChangePasswordForUser.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();
        boolean checkOldpass = userDAO.checkCurrentPassword(employee.getEmployeeID(), currentPassword);

        if (checkOldpass) {

            boolean resetPassword = userDAO.resetPassword(employee.getEmployeeID(), newPassword);

            if (resetPassword) {
                request.setAttribute("successResetPassword", "Thay đổi mật khẩu thành công");
                RequestDispatcher dispatcher = request.getRequestDispatcher("cookieLogin");
                dispatcher.forward(request, response);
                return;
            } else {
                request.setAttribute("errorResetPassword", "Thay đổi mật khẩu thất bại");
            }
        } else {
            request.setAttribute("errorCurrentPassword", "Mật khẩu cũ không chính xác");
        }
        request.getRequestDispatcher("ChangePasswordForUser.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
