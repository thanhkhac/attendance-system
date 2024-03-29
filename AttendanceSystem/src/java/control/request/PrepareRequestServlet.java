/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.request;

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
 * @author admin
 */
@WebServlet(name = "PrepareRequestServlet", urlPatterns = {"/PrepareRequestServlet"})
public class PrepareRequestServlet extends HttpServlet {

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
        String URL = "";

        ArrayList<RequestTypeDTO> listType = new ArrayList<>();
        ArrayList<DepartmentDTO> listDepartment = new ArrayList<>();
        ArrayList<EmployeeTypeDTO> listEmployeeType = new ArrayList<>();
        ArrayList<RoleDTO> listRole = new ArrayList<>();

        RequestTypeDAO dao = new RequestTypeDAO();
        EmployeeTypeDAO emTypeDao = new EmployeeTypeDAO();
        DepartmentDAO deDao = new DepartmentDAO();
        RoleDAO roleDao = new RoleDAO();
        String requestTypeID = "";
        try {
            requestTypeID = request.getParameter("requestTypeID");
            listType = dao.getRequestTypeList();
            listDepartment = deDao.getListDepartment();
            listEmployeeType = emTypeDao.getEmployeeTypeList();
            listRole = roleDao.getRoleList();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        if (requestTypeID != null) {
            switch (requestTypeID) {
                case "0": {
                    URL = "SendRequest.jsp";
                    break;
                }
                case "1": {
                    URL = "OverTimeRequest.jsp";
                    break;
                }
                case "2": {
                    URL = "LeaveRequest.jsp";
                    break;
                }
                default: {
                    URL = "SendOtherRequest.jsp";
                    break;
                }
            }
        } else {
            URL = "SendRequest.jsp";
        }
        request.setAttribute("listType", listType);
        request.setAttribute("listDepartment", listDepartment);
        request.setAttribute("listEmployeeType", listEmployeeType);
        request.setAttribute("listRole", listRole);
        request.setAttribute("requestTypeID", requestTypeID);
        request.getRequestDispatcher(URL).forward(request, response);
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
