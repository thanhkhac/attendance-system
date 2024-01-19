/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import ultility.EmailModule;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.EmployeeDAO;

/**
 *
 * @author nguye
 */
public class MailServlet extends HttpServlet {

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
        out.println(button);
        out.println(receivemail);
        out.println(otp);

        HttpSession session = request.getSession();
        EmployeeDAO eDao = new EmployeeDAO();
        EmailModule external = new EmailModule();
        
        
        String check_email = eDao.getEmail(receivemail);

        // button Send mail
        if (button.equals("Send")) {
            if (receivemail.isEmpty()) {
                msg = "Enter email address please !";
            } else {
                if (check_email == null || !receivemail.equals(check_email)) {
                    msg = "Email Invalid!";
                } else {
                    msg = "OTP code just send, check your email please !";
                    // Send otp to email
                    String otp_send = external.sendOTP(receivemail);
                    // store otp into session
                    session.setAttribute("OTP", otp_send);
                    // set usetime for that session       
                    session.setMaxInactiveInterval(60);
                }
            }
        } // button Submit form
        else if (button.equals("Submit")) {
            if (receivemail.isEmpty() || otp.isEmpty()) {
                msg = "Enter OTP please !";
            } else {
                String opt_temp = (String) request.getSession().getAttribute("OTP");
                if (otp.equals(opt_temp)) {
                    msg = "OTP Correct";
                    response.sendRedirect("Forgot_PW.jsp");
                    return;
                } else {
                    msg = "OTP Incorrect";
                }
            }
        }
        request.setAttribute("MAIL", receivemail);
        request.setAttribute("MSG", msg);
        request.getRequestDispatcher("Recovery_PW_Page.jsp").forward(request, response);
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
