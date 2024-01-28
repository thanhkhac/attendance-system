/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package control.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.EmployeeDAO;

/**
 *
 * @author nguye
 */
@WebServlet(name="ChangePasswordServlet", urlPatterns={"/ChangePasswordServlet"})
public class ChangePasswordServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String password = request.getParameter("txtPassword");
        String re_enter_pw = request.getParameter("txtRePassword");
        String mail_session = (String) request.getSession().getAttribute("EMAIL");

        String msg = "";
        PrintWriter out = response.getWriter();
        EmployeeDAO eDao = new EmployeeDAO();

        if (password.isEmpty() || re_enter_pw.isEmpty()) {
            msg = "Vui lòng điền đầy đủ thông tin";
        } else {
            if (!password.equals(re_enter_pw)) {
                msg = "Password và re-Password phải đồng nhất";
            } else {
                msg = "Password and re-Password đồng nhất";
                if (!password.matches("^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])(.{6,16})$")) {
                    msg = "Mật khẩu [6-16] kí tự , chứa 1 chữ cái in hoa, 1 số và 1 kí tự đặc biệt";
                } else {
                    if (eDao.updatePassword(mail_session, re_enter_pw)) {
                        msg = "Đổi mật khẩu thành công";
                        response.sendRedirect("Login.jsp");
                        return;
                    }else{
                        msg = "Đổi mật khẩu không thành công";
                    }
                }
            }
        }
        request.setAttribute("MSG", msg);
        request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
