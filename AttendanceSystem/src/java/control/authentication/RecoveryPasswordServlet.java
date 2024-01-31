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
import jakarta.servlet.http.HttpSession;
import model.EmployeeDAO;
import utils.email.EmailModule;

/**
 *
 * @author nguye
 */
@WebServlet(name = "RecoveryPasswordServlet", urlPatterns = {"/RecoveryPasswordServlet"})
public class RecoveryPasswordServlet extends HttpServlet {

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

        // get attibute from request
        String button = request.getParameter("btAction");
        String receivemail = request.getParameter("txtMail");
        String otp = request.getParameter("txtOTP");

        // store messages
        String msg = "";

        // check 
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        EmployeeDAO eDao = new EmployeeDAO();
        EmailModule external = new EmailModule();

        String check_email = eDao.getEmail(receivemail);

        // button Send mail
        if (button.equals("Send")) {
            if (receivemail.isEmpty()) {
                msg = "Vui lòng điền email";
            } else {
                
                if (receivemail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    if (check_email == null || !receivemail.equals(check_email)) {
                        msg = "Email không tồn tại";
                        
                    } else {

                        msg = "Mã OTP vừa được gửi, vui lòng kiểm tra email";
                        // Send otp to email
                        String otp_send = external.sendOTP("Recovery Password", "OTP", check_email);
                        session.setAttribute("EMAIL", check_email);
                        // store otp into session
                        session.setAttribute("OTP", otp_send);
                        // set usetime for that session       
//                    session.setMaxInactiveInterval(60);
                    }
                } else {
                    msg = "Định dạng email không hợp lệ";
                }
            }
        } // button Submit form
        else if (button.equals("Reset")) {
            if (receivemail.isEmpty() || otp.isEmpty()) {
                msg = "Mã OTP chưa được điền";
            } else {
                String opt_temp = (String) request.getSession().getAttribute("OTP");
                if (otp.equals(opt_temp)) {
                    msg = "Mã OTP đúng";
                    response.sendRedirect("ChangePassword.jsp");
                    return;
                } else {
                    msg = "Mã OTP sai";
                }
            }
        }
        request.setAttribute("MSG", msg);
        request.getRequestDispatcher("RecoveryPassword.jsp").forward(request, response);
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
