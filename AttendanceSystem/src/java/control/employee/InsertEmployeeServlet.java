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
    final String INSERT_PAGE = "InsertEmployee.jsp";

    final String NO_EMPTY = "Không bỏ trống";
    final String ERR_FORMAT_NAME = "Sai định dạng tên";
    final String ERR_FORMAT_EMAIL = "Sai định dạng email";
    final String ERR_FORMAT_CCCD = "Sai định dạng CCCD";
    final String ERR_FORMAT_SDT = "Sai định dạng SĐT";
    final String ERR_FORMAT_DATE = "Sai định dạng ngày tháng năm";
    
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

        EmailModule em = new EmailModule();
        EmployeeDAO emDao = new EmployeeDAO();
        DepartmentDAO deDao = new DepartmentDAO();
        EmployeeTypeDAO emTypeDao = new EmployeeTypeDAO();
        RoleDAO roDao = new RoleDAO();

        String msg = "";
        String defaultPW = "";
        String email_check = emDao.getEmail(email);
        String cccd_check = emDao.getCCCD(cccd);
        String phonenumber_check = emDao.getPhonenumber(phonenumber);
        
        LocalDate birth_date = null;
        LocalDate start_date = null;
        LocalDate end_date = null;

        boolean isErr = false;

        ////////////////////////////////////////////////////////////////////////////////////     
        if (firstName.isEmpty()) {
            request.setAttribute("ERR_FNAME", NO_EMPTY);
            isErr = true;
        } else if (!firstName.matches("^[A-ZĐÀ-Ỹa-zà-ỹ\\s]*$")) {
            request.setAttribute("ERR_FNAME", ERR_FORMAT_NAME);
            isErr = true;
        }

        if (middleName.isEmpty()) {
            request.setAttribute("ERR_MNAME", NO_EMPTY);
            isErr = true;
        } else if (!middleName.matches("^[A-ZĐÀ-Ỹa-zà-ỹ\\s]*$")) {
            request.setAttribute("ERR_MNAME", ERR_FORMAT_NAME);
            isErr = true;
        }

        if (lastName.isEmpty()) {
            request.setAttribute("ERR_LNAME", NO_EMPTY);
            isErr = true;
        } else if (!lastName.matches("^[A-ZĐÀ-Ỹa-zà-ỹ\\s]*$")) {
            request.setAttribute("ERR_LNAME", ERR_FORMAT_NAME);
            isErr = true;
        }

        if (birthDate.isEmpty()) {
            request.setAttribute("ERR_B_DATE", NO_EMPTY);
            isErr = true;
        }

        if (email.isEmpty()) {
            request.setAttribute("ERR_EMAIL", NO_EMPTY);
            isErr = true;
        } else if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            request.setAttribute("ERR_EMAIL", ERR_FORMAT_EMAIL);
            isErr = true;
        } else if(email.equals(email_check)){
            request.setAttribute("ERR_EMAIL", "Email đã tồn tại trong hệ thống");
            isErr = true;
        }

        if (cccd.isEmpty()) {
            request.setAttribute("ERR_CCCD", NO_EMPTY);
            isErr = true;
        } else if (!cccd.matches("^0\\d{11}$")) {
            request.setAttribute("ERR_CCCD", ERR_FORMAT_CCCD);
            isErr = true;
        } else if(cccd.equals(cccd_check)){
            request.setAttribute("ERR_CCCD", "Căn cước công dân đã tồn tại trong hệ thống");
            isErr = true;
        }

        if (phonenumber.isEmpty()) {
            request.setAttribute("ERR_PHONE", NO_EMPTY);
            isErr = true;
        } else if (!phonenumber.matches("^0\\d{9}$")) {
            request.setAttribute("ERR_PHONE", ERR_FORMAT_SDT);
            isErr = true;
        } else if(phonenumber.equals(phonenumber_check)){
            request.setAttribute("ERR_PHONE", "Số điện thoại đã tồn tại trong hệ thống");
            isErr = true;
        }

        if (startDate.isEmpty()) {
            request.setAttribute("ERR_S_DATE", NO_EMPTY);
            isErr = true;
        }

        if (endDate.isEmpty()) {
            request.setAttribute("ERR_E_DATE", NO_EMPTY);
            isErr = true;
        }

        try {
            birth_date = LocalDate.parse(birthDate);
        } catch (Exception e) {
            request.setAttribute("ERR_B_DATE", ERR_FORMAT_DATE);
            isErr = true;
        }
        try {
            start_date = LocalDate.parse(startDate);
        } catch (Exception e) {
            request.setAttribute("ERR_S_DATE", ERR_FORMAT_DATE);
            isErr = true;
        }

        try {
            end_date = LocalDate.parse(endDate);
        } catch (Exception e) {
            request.setAttribute("ERR_E_DATE", ERR_FORMAT_DATE);
            isErr = true;
        }

        ////////////////////////////////////////////////////////////////////////////////////     
        Boolean gender_raw = null;
        // cast gender before insert
        switch (gender) {
            case "male":
                gender_raw = true;
                break;
            case "female":
                gender_raw = false;
                break;
        }

        boolean activeAcc = false;
        if (isActive != null) {
            activeAcc = true;
        }

        int employee_type_id_raw = emTypeDao.getEmployeeTypeIDByName(employeeTypeID);
        int department_id_raw = deDao.getDepartmentIDByName(departmentID);
        int role_id_raw = roDao.getRoleIDByName(roleID);

        ////////////////////////////////////////////////////////////////////////////////////     
        if (!isErr) {
            int get_start_month = start_date.getMonthValue();
            int get_end_month = end_date.getMonthValue();
            int get_start_year = start_date.getYear();
            int get_end_year = end_date.getYear();
            int get_birth_year = birth_date.getYear();

            if (get_start_year - get_birth_year < 18) { // chưa đủ 18 tuổi
                msg = "Chưa đủ tuổi làm việc";
            } else {  // đủ 18 tuổi
                if (get_end_year - get_start_year < 0) {  // năm kết thúc bé hơn năm bắt đầu
                    msg = "Thời gian làm việc không hợp lệ";
                } else if (get_end_year - get_start_year == 0) {   // năm kết thúc cùng năm bắt đầu
                    if (get_end_month - get_start_month < 6) {    // thời hạn làm việc ít nhất là 6 tháng
                        msg = "Thời gian làm việc ít nhất 6 tháng";
                    } else {  // thời hạn làm việc >= 6 tháng
                        defaultPW = em.sendOTP("Default Password" , "default password", email);
                        if (emDao.insertEmployee(firstName, middleName, lastName, gender_raw, birth_date, email, defaultPW, cccd, phonenumber, employee_type_id_raw, department_id_raw, role_id_raw, start_date, end_date, activeAcc) == true) {
                            msg = "Thêm nhân viên thành công";
                        } else {
                            msg = "Thêm nhân viên không thành công";
                        }
                    }
                } else {  // năm kêt thúc lớn hơn năm bắt đầu
                    defaultPW = em.sendOTP("Default Password" , "default password", email);
                    if (emDao.insertEmployee(firstName, middleName, lastName, gender_raw, birth_date, email, defaultPW, cccd, phonenumber, employee_type_id_raw, department_id_raw, role_id_raw, start_date, end_date, activeAcc) == true) {
                        msg = "Thêm nhân viên thành công";
                    } else {
                        msg = "Thêm nhân viên không thành công";
                    }
                }
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////   
        //////////// REFILL DATA
        request.setAttribute("LASTNAME", lastName);
        request.setAttribute("MIDDLENAME", middleName);
        request.setAttribute("FIRSTNAME", firstName);
        request.setAttribute("CCCD", cccd);
        request.setAttribute("EMAIL", email);
        request.setAttribute("PHONENUMBER", phonenumber);
        request.setAttribute("BIRTHDATE", birth_date);
        request.setAttribute("STARTDATE", start_date);
        request.setAttribute("ENDDATE", end_date);

        request.setAttribute("EMPLTYPEID", employeeTypeID);
        request.setAttribute("DEPARTMENTID", departmentID);
        request.setAttribute("ROLEID", roleID);
        
        request.setAttribute("MSG", msg);
        request.getRequestDispatcher(INSERT_PAGE).forward(request, response);
        
        
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
