/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package control.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;
import model.EmployeeDAO;
import model.EmployeeDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name="CheckLogin", urlPatterns={"/checkLogin"})
public class CheckLoginServlet extends HttpServlet {
   
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
        PrintWriter out = response.getWriter();
        String Email = request.getParameter("txtEmail");
        String PassWord = request.getParameter("txtPassword");
        String Remember = request.getParameter("Remember");        
        try{
        EmployeeDAO dao = new EmployeeDAO();
        EmployeeDTO Account = dao.checkAccount(Email, PassWord);
        //Account dung luu vao session
        if(Account!=null){
            if(Account.isIsActived()){
            HttpSession session = request.getSession();
            session.setAttribute("ACCOUNT", Account);
            //Check nut remember neu tich luu vao Cookie
            if(Remember!=null){
            Cookie mail = new Cookie("EmailCookie",Email);
            Cookie pass = new Cookie("PassWordCookie",PassWord);
            mail.setMaxAge(60*60*24);
            pass.setMaxAge(60*60*24);
       
            response.addCookie(mail);
            response.addCookie(pass);
            }
            response.sendRedirect("ThanhCong.html");
            }else{
                String Error = "Account is currently locked";
                request.setAttribute("Error", Error);
                request.getRequestDispatcher("cookieLogin").forward(request, response);
            }
        }
        else{           
            
            String Error = "Email or password is incorrect, please try again";
            request.setAttribute("Error", Error);
            request.getRequestDispatcher("cookieLogin").forward(request, response);
        }
        
        }catch(Exception e){
            
        }
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
