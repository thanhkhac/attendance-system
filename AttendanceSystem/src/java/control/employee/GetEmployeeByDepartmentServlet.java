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
import java.util.ArrayList;
import model.DepartmentDAO;
import model.DepartmentDTO;
import model.EmployeeDAO;
import model.EmployeeDTO;
import model.ShiftDAO;
import model.ShiftDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name="listByDepartment", urlPatterns={"/listByDepartment"})
public class GetEmployeeByDepartmentServlet extends HttpServlet {
   
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
        String sttPhong = request.getParameter("department");
        String listOfPage = request.getParameter("listOfPage");
        int page = 1;
        if(listOfPage!=null){
            page = Integer.parseInt(listOfPage);
        }
        request.setAttribute("PHONG", sttPhong);
        int phong = Integer.parseInt(sttPhong);
        DepartmentDAO depatDAO = new DepartmentDAO();
        DepartmentDTO department = depatDAO.getDepartmentById(phong);
        EmployeeDAO dao = new EmployeeDAO();
        ArrayList<EmployeeDTO> list = dao.searchAjaxEmployeeByDepartment(page, phong, "",0);
        String position = department.getName();
        request.setAttribute("LIST", list);
        request.setAttribute("POSITION", position);       
        int count = dao.getTotalEmployeeByDepartment(phong,"",0);
        int endPage = 0;
        if(count%2==0)
            endPage = count/2;
        else {endPage = count/2 +1;}
        ShiftDAO shiftdao = new ShiftDAO();
 
        ArrayList<ShiftDTO> listhift = shiftdao.getAllShiftDTO();
        request.setAttribute("TENPHONG", position);
        request.setAttribute("LISTSHIFT", listhift);
        request.setAttribute("ENDPAGE", endPage);    
        request.getRequestDispatcher("viewEmployeesByManager.jsp").forward(request, response);
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
