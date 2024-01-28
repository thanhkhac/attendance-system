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
import java.time.LocalDate;
import model.DepartmentDAO;
import model.EmployeeDAO;
import model.EmployeeTypeDAO;
import model.RoleDAO;
import utils.email.EmailModule;

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

        PrintWriter out = response.getWriter();

        EmployeeDAO emDao = new EmployeeDAO();
        DepartmentDAO deDao = new DepartmentDAO();
        EmployeeTypeDAO emTypeDao = new EmployeeTypeDAO();
        RoleDAO roDao = new RoleDAO();

        String msg = "";
        String password = "";
        if (firstName.isEmpty() || middleName.isEmpty() || lastName.isEmpty() || birthDate.isEmpty() || email.isEmpty() || cccd.isEmpty() || phonenumber.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            msg = "Vui lòng điền đầy đủ thông tin";
        } else {
            msg = "Dữ liệu đã được nhập đầy đủ";

            request.setAttribute("LASTNAME", lastName);
            request.setAttribute("MIDDLENAME", middleName);
            request.setAttribute("FIRSTNAME", firstName);

            int employee_type_id_raw = emTypeDao.getEmployeeTypeIDByName(employeeTypeID);
            int department_id_raw = deDao.getDepartmentIDByName(departmentID);
            int role_id_raw = roDao.getRoleIDByName(roleID);

            boolean gender_raw = false;
            // cast gender before insert
            switch (gender) {
                case "male":
                    gender_raw = true;
                    break;
                case "female":
                    gender_raw = false;
                    break;
            }
            request.setAttribute("GENDER", gender);
            request.setAttribute("ROLE", roleID);
            request.setAttribute("DEPARTMENT", departmentID);
            request.setAttribute("EMPLOYEETYPE", employeeTypeID);
            try {
                if (emDao.getCCCD(cccd) != null || emDao.getEmail(email) != null || emDao.getPhonenumber(phonenumber) != null) { // 1 trong 3 cái trùng báo lỗi (trùng: tồn tại trong DB)
                    msg = "Email , số điện thoại hoặc CCCD đã tồn tại trong hệ thống";
                } else {
                    if (cccd.matches("^0\\d{11}$")) {
                        msg = "CCCD hợp lệ";
                        request.setAttribute("CCCD", cccd);
                        if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                            msg = "Email hợp lệ";
                            request.setAttribute("EMAIL", email);
                            
                            // send password 
                            EmailModule em = new EmailModule();
                            password = em.sendOTP("default password", email);
                            
                            if (phonenumber.matches("^0[0-9]{9}$")) {
                                msg = "Email , số điện thoại hoặc CCCD được chấp nhận";
                                request.setAttribute("PHONENUMBER", phonenumber);

                                // cast String to LocalDate
                                LocalDate birth_date = LocalDate.parse(birthDate);
                                LocalDate start_date = LocalDate.parse(startDate);
                                LocalDate end_date = LocalDate.parse(endDate);

                                //get month and year
                                int get_start_month = start_date.getMonthValue();
                                int get_end_month = end_date.getMonthValue();
                                int get_start_year = start_date.getYear();
                                int get_end_year = end_date.getYear();
                                int get_birth_year = birth_date.getYear();

                                if (get_start_year - get_birth_year < 18) {
                                    msg = "Chưa đủ 18 tuổi";
                                } else {
                                    if (get_end_year - get_start_year == 0) {
                                        // Thời hạn hợp đồng làm việc của 1 nhân viên ít nhất 6 tháng
                                        if (get_end_month - get_start_month < 6) {
                                            msg = "Thời gian làm việc ít nhất 6 tháng";
                                        } else {
                                            msg = "Thời hạn làm việc phù hợp";
                                            // set trạng thái cho tài khoản mặc định là 'false' (chưa active tài khoản)
                                            boolean activeAcc = false;
                                            if (isActive != null) {
                                                activeAcc = true;
                                            }
                                            // Set trang thai dang nhap mac dinh la 'false'
                                            if (emDao.insertEmployee(firstName, middleName, lastName, gender_raw, birth_date, email, password, cccd, phonenumber, employee_type_id_raw, department_id_raw, role_id_raw, start_date, end_date, activeAcc) == true) {
                                                msg = "Thêm nhân viên thành công";
                                            } else {
                                                msg = "Thêm nhân viên không thành công";
                                            }
                                        }
                                    } else if (get_end_year - get_start_year < 0) {
                                        msg = "Thời hạn làm việc không hợp lệ";
                                    } else {
                                        msg = "Thời hạn làm việc phù hợp";
                                        // set trạng thái cho tài khoản mặc định là 'false' (chưa active tài khoản)
                                        boolean activeAcc = false;
                                        if (isActive != null) {
                                            activeAcc = true;
                                        }
                                        // Set trang thai dang nhap mac dinh la 'false'
                                        if (emDao.insertEmployee(firstName, middleName, lastName, gender_raw, birth_date, email, password, cccd, phonenumber, employee_type_id_raw, department_id_raw, role_id_raw, start_date, end_date, activeAcc) == true) {
                                            msg = "Thêm nhân viên thành công";
                                        } else {
                                            msg = "Thêm nhân viên không thành công";
                                        }
                                    }
                                }
                            } else {
                                msg = "Định dạng SĐT không hợp lệ";
                            }
                        } else {
                            msg = "Lỗi format email";
                        }
                    } else {
                        msg = "CCCD không hợp lệ";
                    }
                }
            } catch (Exception e) {
                msg = "Dữ liệu ngày tháng năm không phù hợp!";
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
