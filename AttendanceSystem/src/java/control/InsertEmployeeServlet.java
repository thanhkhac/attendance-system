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
import java.time.LocalDate;
import model.EmployeeDAO;

/**
 *
 * @author nguye
 */
@WebServlet(name = "InsertEmployeeServlet", urlPatterns = {"/InsertEmployeeServlet"})
public class InsertEmployeeServlet extends HttpServlet {

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

        String firstName = request.getParameter("txtFirstName");
        String middleName = request.getParameter("txtMiddleName");
        String lastName = request.getParameter("txtLastName");
        String gender = request.getParameter("txtGender");
        String birthDate = request.getParameter("txtBirthDate");
        String email = request.getParameter("txtEmail");
        String cccd = request.getParameter("txtCCCD");
        String phonenumber = request.getParameter("txtPhonenumber");
        String employeeTypeID = request.getParameter("txtEmployeeTypeID");
        String departmentID = request.getParameter("txtDepartmentID");
        String roleID = request.getParameter("txtRoleID");
        String startDate = request.getParameter("txtStartDate");
        String endDate = request.getParameter("txtEndDate");
        String isActive = request.getParameter("txtIsActive");

        // check
        PrintWriter out = response.getWriter();
//        out.println(isActive);

        out.println("gen-" + gender);
        System.out.println("gen-" + gender);

        EmployeeDAO dao = new EmployeeDAO();

        String msg = "";
        if (firstName.isEmpty() || middleName.isEmpty() || lastName.isEmpty() || birthDate.isEmpty() || email.isEmpty() || cccd.isEmpty() || phonenumber.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            msg = "No Empty please!";
        } else {
            msg = "OK";
            int employee_type_id_raw = 0, department_id_raw = 0, role_id_raw = 0;
            boolean gender_raw = false;
            // cast employeeTypeID before insert
            switch (employeeTypeID) {
                case "fulltime":
                    employee_type_id_raw = 1;
                    break;
                case "parttime":
                    employee_type_id_raw = 2;
                    break;
                case "intern":
                    employee_type_id_raw = 3;
                    break;
            }
            // cast departmentID before insert
            switch (departmentID) {
                case "phongNhanSu":
                    department_id_raw = 1;
                    break;
                case "phongTiepThi":
                    department_id_raw = 2;
                    break;
            }
            // cast roleID before insert
            switch (roleID) {
                case "nhanVien":
                    role_id_raw = 1;
                    break;
                case "quanLyNhanSu":
                    role_id_raw = 2;
                    break;
                case "admin":
                    role_id_raw = 3;
                    break;
            }
            // cast gender before insert
            switch (gender) {
                case "male":
                    gender_raw = true;
                    break;
                case "female":
                    gender_raw = false;
                    break;
            }
            out.println("g_r-" + gender_raw);
            System.out.println("g_r-"+gender_raw);
            // cast String to LocalDate
            LocalDate birth_date = LocalDate.parse(birthDate);
            LocalDate start_date = LocalDate.parse(startDate);
            LocalDate end_date = LocalDate.parse(endDate);

            if (dao.getCCCD(cccd) != null || dao.getEmail(email) != null || dao.getPhonenumber(phonenumber) != null) { // 1 trong 3 cai trung thi bao loi
                msg = "Email - Phonenumber - CCCD existed (data duplicate is not accept here !)";
            } else {
                msg = "Email - phonenumber - cccd are accepted !";
                // set mat khau mac dinh la '123456789' ; set trang thai dang nhap mac dinh la 'false'
                if (dao.insertEmployee(firstName, middleName, lastName, gender_raw, birth_date, email, "123456789", cccd, phonenumber, employee_type_id_raw, department_id_raw, role_id_raw, start_date, end_date, false) == true) {
//                    out.println("OK");
                    msg = "add thanh cong";
                } else {
//                    out.println("NO");
                    msg = "add khong thanh cong";
                }
            }
        }
        request.setAttribute("MSG", msg);
        request.getRequestDispatcher("InsertEmployee.jsp").forward(request, response);
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
