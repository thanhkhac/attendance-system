/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package control.employee;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.EmployeeDAO;
import model.EmployeeDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name="UpdateProfileByEmployee", urlPatterns={"/updateProfileByEmployee"})
public class UpdateProfileByEmployeeServlet extends HttpServlet {
   
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
        String FirstName = request.getParameter("txtFirstName");
        String LastName = request.getParameter("txtLastName");
        String Phone = request.getParameter("txtPhone");
        String Birth = request.getParameter("txtBirth");
        String Email = request.getParameter("txtEmail");
        String CCCD = request.getParameter("txtCCCD");
        String Gender = request.getParameter("txtGender");
        String Address = request.getParameter("txtAddress");
        EmployeeDAO dao = new EmployeeDAO();
        int count =0;
        String check = null;
        String checkPhone = null;
        for(int i=0;i<Phone.length();i++){
            if(Phone.charAt(i)<48||Phone.charAt(i)>57)
                count++;
        }
        try{
        if(count==0&&Phone.length()==10){
        int gender = 1;
        if(Gender.equals("Male"))
            gender = 0;
        boolean checkUpdate = dao.updateProfileByEmployee(Phone, gender, Email);
        if(checkUpdate){
             HttpSession session = request.getSession();
             EmployeeDTO Account = dao.checkEmail(Email);
             session.setAttribute("ACCOUNT", Account);
            check = "Update thành công!!!";
        }
        }
        else {
           checkPhone = "Số điện thoại phải bao gồm 10 chữ số"; 
           
        }
        }finally{
            request.setAttribute("CHECK", check);
            request.setAttribute("CHECKPHONE", checkPhone);
            request.getRequestDispatcher("UpdateEmployeeProfile.jsp").forward(request, response);
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
