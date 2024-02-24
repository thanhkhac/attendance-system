/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.*;

/**
 *
 * @author Admin
 */
@WebServlet(name = "Controller", urlPatterns = {"/DispatchController"})
public class DispatchController extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String button = request.getParameter("btAction");
        String URL = "";
//        EmployeeDAO dao = new EmployeeDAO();
//        ArrayList<EmployeeGeneral> list = dao.getEmployeeInfo();
//        String departmentID = request.getParameter("departmentID");
        try {
            if (button.equals("Login")) {
                URL = "checkLogin";
            } else if (button.equals("Logout")) {
                URL = "logout";
            } else if (button.equals("UpdateProfile")) {
                URL = "updateProfileByEmployee";
            } else if (button.equals("Send")) {
                URL = "RecoveryPasswordServlet";
            } else if (button.equals("Reset")) {
                URL = "RecoveryPasswordServlet";
            } else if (button.equals("Save change")) {
                URL = "ChangePasswordServlet";
            } else if (button.equals("InsertEmployee")) {
                URL = "InsertEmployee.jsp";
            } else if (button.equals("Insert")) {
                URL = "InsertEmployeeServlet";
            } else if (button.equals("ViewEmployee")) {
                URL = "GetAllEmployeeByHRServlet";
            } else if (button.equals("Update")) {
                URL = "GetEmployeeInfoByHRServlet";
            } else if (button.equals("Lưu Thay Đổi")) {
                URL = "UpdateEmployeeByHRServlet";
            } else if (button.equals("viewListByDepartment")) {
                URL = "listByDepartment";
            } else if (button.equals("Assign Manager")) {
                URL = "SendRequestAssignServlet";
            } else if (button.equals("Assign")) {
                URL = "UpdateDepartmentManagerServlet";
            } else if (button.equals("GetUnscheduleEmployees")) {
                URL = "GetUnscheduleEmployees";
            } else if (button.equals("GetConflicts")) {
                URL = "GetConflicts";
            } else if (button.equals("Gửi")) {
                URL = "InsertLeaveRequestServlet";
            }
        } finally {
//            request.setAttribute("departmentID",  departmentID);
//            request.setAttribute("List", list);
            request.getRequestDispatcher(URL).forward(request, response);
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
