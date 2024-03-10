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
import java.time.LocalDate;
import java.util.ArrayList;
import model.EmployeeDAO;
import model.EmployeeDTO;
import ultility.datetimeutil.DateTimeUtil;

/**
 *
 * @author admin
 */
@WebServlet(name = "InsertImportedEmployeesServlet", urlPatterns = {"/InsertImportedEmployeesServlet"})
public class InsertImportedEmployeesServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        ArrayList<EmployeeDTO> employees = (ArrayList<EmployeeDTO>) session.getAttribute("employees");
        ArrayList<EmployeeDTO> isAcceptable = (ArrayList<EmployeeDTO>) session.getAttribute("isAcceptable");
//        ArrayList<EmployeeDTO> isError = (ArrayList<EmployeeDTO>) session.getAttribute("isError");
        EmployeeDAO emDAO = new EmployeeDAO();
        int count = 0;
        try {
            for (EmployeeDTO e : isAcceptable) {
                boolean rs = emDAO.insertEmployee(
                        e.getFirstName(), e.getMiddleName(), e.getLastName(), e.getGender(),
                        DATE_UTIL.parseSqlDate(e.getBirthDate()), e.getEmail(), e.getPassword(), e.getCccd(), e.getPhoneNumber(),
                        e.getEmployeeTypeID(), e.getDepartmentID(), e.getRoleID(), DATE_UTIL.parseSqlDate(e.getStartDate()),
                        DATE_UTIL.parseSqlDate(e.getEndDate()), false);
                if (rs) {
                    count++;
                    isAcceptable.remove(e);
                    employees.remove(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        session.setAttribute("employees", employees);
        session.setAttribute("isAcceptable", isAcceptable);
        request.setAttribute("SuccessMSG", "Inserted [" + count + "] Employee Successfully !");
        request.getRequestDispatcher("ImportEmployees.jsp").forward(request, response);

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
