/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.employee;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import model.*;
import model.UpdateInfoError;
import ultility.ImportFile.ReadFileModule;

/**
 *
 * @author admin
 */
@WebServlet(name = "ReadEmployeeFromFileServlet", urlPatterns = {"/ReadEmployeeFromFileServlet"})
@MultipartConfig
public class ReadEmployeeFromFileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private boolean isErrorEmployee(EmployeeDTO e) {
        boolean isErr = false;
        EmployeeDAO emDAO = new EmployeeDAO();
        String regexName = "^[^\\d\\p{Punct}]+$";
        if (e.getFirstName() == null
                || e.getMiddleName() == null
                || e.getLastName() == null) {
            isErr = true;
        } else if (e.getFirstName().length() <= 0
                || e.getMiddleName().length() <= 0
                || e.getLastName().length() <= 0) {
            isErr = true;
        } else if (!e.getFirstName().matches(regexName)
                || !e.getMiddleName().matches(regexName)
                || !e.getLastName().matches(regexName)) {
            isErr = true;
        }
        if (!e.getCccd().matches("^0\\d{11}$")
                || e.getCccd() == null
                || e.getCccd().equals(emDAO.getCCCD(e.getCccd()))) {
            isErr = true;
        }
        if (!e.getPhoneNumber().matches("^0\\d{9}$")
                || e.getPhoneNumber() == null
                || e.getPhoneNumber().equals(emDAO.getPhonenumber(e.getPhoneNumber()))) {
            isErr = true;
        }
        if (!e.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
                || e.getEmail() == null
                || e.getEmail().equals(emDAO.getEmail(e.getEmail()))) {
            isErr = true;
        }
        if (!e.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d\\s])[A-Za-z\\d@$!%*?&.,]{6,16}$")
                || e.getPassword() == null) {
            isErr = true;
        }
        if (e.getBirthDate() != null) {
            if (LocalDate.now().getYear() - e.getBirthDate().getYear() < 18
                    || LocalDate.now().getYear() - e.getBirthDate().getYear() < 0) {
                isErr = true;
            }
        } else {
            isErr = true;
        }
        if (e.getStartDate() != null && e.getEndDate() != null) {
            if (e.getStartDate().after(e.getEndDate())) {
                isErr = true;
            }
        } else {
            isErr = true;
        }

        return isErr;
    }

    private ArrayList<EmployeeDTO> assignTempID(ArrayList<EmployeeDTO> employees) {
        ArrayList<EmployeeDTO> newEmployees = employees;
        int i = 1;
        for (EmployeeDTO e : employees) {
            e.setEmployeeID(i);
            i++;
        }
        return newEmployees;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");

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
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        DepartmentDAO deDAO = new DepartmentDAO();
        EmployeeTypeDAO emDAO = new EmployeeTypeDAO();
        RoleDAO roleDAO = new RoleDAO();

        ArrayList<EmployeeDTO> employees = new ArrayList<>();
        ArrayList<EmployeeDTO> isAcceptable = new ArrayList<>();
        ArrayList<EmployeeDTO> isError = new ArrayList<>();
        ArrayList<DepartmentDTO> department = deDAO.getListDepartment();
        ArrayList<EmployeeTypeDTO> employeeType = emDAO.getEmployeeTypeList();
        ArrayList<RoleDTO> roles = roleDAO.getRoleList();

        ReadFileModule readFileModule = new ReadFileModule();
        HttpSession session = request.getSession();

        try {
            //get file from request
            Part file = request.getPart("file");
            //get file from input stream
            InputStream fileContent = file.getInputStream();
            employees = readFileModule.readExcel(fileContent);
            if (employees.size() > 0) {
                employees = assignTempID(employees);
                for (EmployeeDTO e : employees) {
                    if (isErrorEmployee(e)) {
                        isError.add(e);
                    } else {
                        isAcceptable.add(e);
                    }
                }
            }
            if (isAcceptable.size() <= 0 && isError.size() <= 0) {

            } else {
                session.setAttribute("employees", employees);
                session.setAttribute("isAcceptable", isAcceptable);
                session.setAttribute("isError", isError);
            }
//            System.out.println(employees.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        session.setAttribute("departments", department);
        session.setAttribute("types", employeeType);
        session.setAttribute("roles", roles);

        request.getRequestDispatcher("ImportEmployees.jsp").forward(request, response);
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
