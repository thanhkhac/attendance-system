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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        ArrayList<EmployeeDTO> employees = (ArrayList<EmployeeDTO>) session.getAttribute("employees");
        ArrayList<EmployeeDTO> isAcceptable = (ArrayList<EmployeeDTO>) session.getAttribute("isAcceptable");
        ArrayList<EmployeeDTO> isError = (ArrayList<EmployeeDTO>) session.getAttribute("isError");
        if (isError == null) {
            isError = new ArrayList<>();
        }
        ArrayList<EmployeeDTO> employees_inserted = new ArrayList<>();

        EmployeeDAO emDAO = new EmployeeDAO();

        int count = 0;
        int countErr = 0;
        try {
            for (EmployeeDTO e : employees) {
                employees_inserted.add(e);
            }

            for (EmployeeDTO e : isAcceptable) {
                if (isErrorEmployee(e)) {
                    isError.add(e);
                    countErr++;
                } else {
                    boolean rs = emDAO.insertEmployee(
                            e.getFirstName(), e.getMiddleName(), e.getLastName(), e.getGender(),
                            DATE_UTIL.parseSqlDate(e.getBirthDate()), e.getEmail(), e.getPassword(), e.getCccd(), e.getPhoneNumber(),
                            e.getEmployeeTypeID(), e.getDepartmentID(), e.getRoleID(), DATE_UTIL.parseSqlDate(e.getStartDate()),
                            DATE_UTIL.parseSqlDate(e.getEndDate()), false);
                    if (rs) {
                        employees_inserted.remove(e);
                        count++;
                    }
                }
            }

//            for (EmployeeDTO e : isAcceptable) {
//                if (!isErrorEmployee(e)) {
//                    employees_inserted.remove(e);
//                }
//            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        session.setAttribute("employees", employees_inserted);
        session.setAttribute("isAcceptable", null);
        session.setAttribute("isError", isError);
        request.setAttribute("SuccessMSG", "Inserted [" + count + "] employee Successfully \n"
                + "and found [" + countErr + "] employees error while inserting !");

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
