/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.EmployeeDAO;

/**
 *
 * @author nguye
 */
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
        String mail = request.getParameter("txtMail");
        String password = request.getParameter("txtPassword");
        String re_enter_pw = request.getParameter("txtRePassword");

        String msg = "";

        PrintWriter out = response.getWriter();

        out.println(mail);
        out.println(password);
        out.println(re_enter_pw);

        EmployeeDAO eDao = new EmployeeDAO();
        String get_mail = eDao.getEmail(mail);

        if (mail.isEmpty() || password.isEmpty() || re_enter_pw.isEmpty()) {
            msg = "No Empty please !";
        } else {
            if (get_mail == null || !mail.equals(get_mail)) {
                msg = "Email invalid !";
            } else { 
                if (!password.equals(re_enter_pw)) {
                    msg = "Password and re-Password must be the same !";
                } else {
                    msg = "Password and re-Password are the same !";
                    if (eDao.updatePassword(mail, re_enter_pw)) {
                        msg = "Successful";
                        response.sendRedirect("Login.jsp");
                        return;
                    }
                }
            }
        }
        request.setAttribute("MAIL", mail);
        request.setAttribute("MSG", msg);
        request.getRequestDispatcher("Forgot_PW.jsp").forward(request, response);

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
