/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.*;

/**
 *
 * @author admin
 */
public class ViewAllEmployeeServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        String requestAssignManager = (String) session.getAttribute("RequestAssignManager");
        String searchValue = request.getParameter("txtSearchValue");
        String departmentID = request.getParameter("txtDepartment");
        String typeID = request.getParameter("txtType");
        String order = request.getParameter("txtOrder");
        EmployeeDAO dao = new EmployeeDAO();
        ArrayList<EmployeeGeneral> list;
        list = dao.filterByAJAX(searchValue, departmentID, typeID, order);

        PrintWriter out = response.getWriter();
        if (requestAssignManager.length() > 0) {
            out.print("<table class=\"table table-hover\">\n"
                    + "                    <thead>\n"
                    + "                    <th>EmployeeID</th>\n"
                    + "                    <th>LastName</th>\n"
                    + "                    <th>MiddleName</th>\n"
                    + "                    <th>FirstName</th>\n"
                    + "                    <th>Email</th>\n"
                    + "                    <th>Apply</th>\n"
                    + "                    </thead>\n"
                    + "                    <tbody>"
                    + "<div id=\"list-content\" >");
        } else {
            out.print("<table class=\"table table-hover\">\n"
                    + "                    <thead>\n"
                    + "                    <th>EmployeeID</th>\n"
                    + "                    <th>LastName</th>\n"
                    + "                    <th>MiddleName</th>\n"
                    + "                    <th>FirstName</th>\n"
                    + "                    <th>Email</th>\n"
                    + "                    </thead>\n"
                    + "                    <tbody>"
                    + "<div id=\"list-content\" >");
        }

        if (requestAssignManager.length() > 0) {
            for (EmployeeGeneral e : list) {
                out.print(" <div class=\"table-row-container\">\n"
                        + "                            <tr class=\"table-primary space-under employeeRow\" data-employee-id=\"" + e.getEmployeeID() + "\">\n"
                        + "                                <td>" + e.getEmployeeID() + "</td>\n"
                        + "                                <td>" + e.getLastName() + "</td>\n"
                        + "                                <td>" + e.getMiddleName() + "</td>\n"
                        + "                                <td>" + e.getFirstName() + "</td>\n"
                        + "                                <td>" + e.getEmail() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getGender() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getBirthDate() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getCccd() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getPhoneNumber() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getEmployeeTypeName() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getDepartmentName() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getRoleName() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getStartDate() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getEndDate() + "</td>\n"
                        + "                                <td>"
                        + "                                   <form>"
                        + "                                       <input type=\"hidden\" name=\"ManagerIDAssigned\" value=\"" + e.getEmployeeID() + "\">\n"
                        + "                                       <input class=\"btn btn-success\" type=\"submit\" name=\"btAction\" value=\"Assign\">"
                        + "                                   </form>"
                        + "                               </td>"
                        + "                            </tr>\n"
                        + "</div>");
            }
        } else {
            for (EmployeeGeneral e : list) {
                out.print(" <div class=\"table-row-container\">\n"
                        + "                            <tr class=\"table-primary space-under employeeRow\" data-employee-id=\"" + e.getEmployeeID() + "\">\n"
                        + "                                <td>" + e.getEmployeeID() + "</td>\n"
                        + "                                <td>" + e.getLastName() + "</td>\n"
                        + "                                <td>" + e.getMiddleName() + "</td>\n"
                        + "                                <td>" + e.getFirstName() + "</td>\n"
                        + "                                <td>" + e.getEmail() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getGender() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getBirthDate() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getCccd() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getPhoneNumber() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getEmployeeTypeName() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getDepartmentName() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getRoleName() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getStartDate() + "</td>\n"
                        + "                                <td style=\"display: none\">" + e.getEndDate() + "</td>\n"
                        + "                            </tr>\n"
                        + "</div>");
            }
        }
        out.print(" </div>\n"
                + "                    </tbody>\n"
                + "                </table>");

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
