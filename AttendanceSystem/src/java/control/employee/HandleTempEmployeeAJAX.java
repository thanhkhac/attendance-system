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
import model.UpdateInfoError;

/**
 *
 * @author admin
 */
@WebServlet(name = "HandleTempEmployeeAJAX", urlPatterns = {"/HandleTempEmployeeAJAX"})
public class HandleTempEmployeeAJAX extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private EmployeeDTO getEmployeeByTmpID(ArrayList<EmployeeDTO> employee, int id) {
        for (EmployeeDTO e : employee) {
            if (e.getEmployeeID() == id) {
                return e;
            }
        }
        return null;
    }
    
    private LocalDate timeAfterNMonths(LocalDate time, int monthToAdd) {
        LocalDate afterNMonth = time.plusMonths(monthToAdd);
        return afterNMonth;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String txt_firstName = request.getParameter("firstName").trim();
        String txt_middleName = request.getParameter("middleName").trim();
        String txt_lastName = request.getParameter("lastName").trim();
        String txt_gender = request.getParameter("Gender");;
        String txt_cccd = request.getParameter("CCCD").trim();
        String txt_email = request.getParameter("Email").trim();
        String txt_password = request.getParameter("Password").trim();
        String txt_phoneNumber = request.getParameter("Phone").trim();
        String txt_birthday = request.getParameter("BirthDate");
        String txt_departmentID = request.getParameter("Department");
        String txt_typeID = request.getParameter("EmployeeType");
        String txt_roleID = request.getParameter("Role");
        String txt_employeeID = request.getParameter("tmpID");
        String txt_startDate = request.getParameter("StartDate");
        String txt_endDate = request.getParameter("EndDate");
        ArrayList<EmployeeDTO> employees = (ArrayList<EmployeeDTO>) session.getAttribute("employees");
        ArrayList<EmployeeDTO> isAcceptable = (ArrayList<EmployeeDTO>) session.getAttribute("isAcceptable");
        ArrayList<EmployeeDTO> isError = (ArrayList<EmployeeDTO>) session.getAttribute("isError");
        UpdateInfoError err = new UpdateInfoError();
        LocalDate birthDay = LocalDate.now();
        LocalDate startDate = null;
        LocalDate endDate = null;
        
        boolean isErr = false;
        String regexName = "^[^\\d\\p{Punct}]+$";
        String messageNameError = "Thành phần của Tên ko được chứa số hay kí tự đặc biệt !";
        EmployeeDTO e = new EmployeeDTO();
        try {
            int id = Integer.parseInt(txt_employeeID);
            birthDay = LocalDate.parse(txt_birthday);
            e = getEmployeeByTmpID(employees, id);
            startDate = LocalDate.parse(txt_startDate);
            endDate = LocalDate.parse(txt_endDate);
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        
        if (txt_firstName.length() <= 0) {
            err.setNull_error("Điền đầy đủ các thành phần của Tên !");
            isErr = true;
        } else if (!txt_firstName.matches(regexName)) {
            err.setName_format_error(messageNameError);
            isErr = true;
        }
        if (txt_middleName.length() <= 0) {
            err.setNull_error("Điền đầy đủ các thành phần của Tên !");
            isErr = true;
        } else if (!txt_middleName.matches(regexName)) {
            err.setName_format_error(messageNameError);
            isErr = true;
        }
        if (txt_lastName.length() <= 0) {
            err.setNull_error("Điền đầy đủ các thành phần của Tên !");
            isErr = true;
        } else if (!txt_lastName.matches(regexName)) {
            err.setName_format_error(messageNameError);
            isErr = true;
        }
        if (!txt_cccd.matches("^0\\d{11}$")) {
            err.setCccd_format_error("Định dạng căn cước công dân không hợp lệ !");
            isErr = true;
        }
        if (!txt_phoneNumber.matches("^0\\d{9}$")) {
            err.setPhone_format_error("Định dạng SĐT không hợp lệ !");
            isErr = true;
        }
        if (!txt_email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            err.setEmail_format_error("Định dạng Email không hợp lệ !");
            isErr = true;
        }
//        if (!txt_password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d\\s])[A-Za-z\\d@$!%*?&.,]{6,16}$")) {
//            err.setPassword_format_error("Mật khẩu [6-16] kí tự , chứa 1 chữ cái in hoa và 1 kí tự ('@', '$', '%', '.', ',', '?', '&')");
//            isErr = true;
//        }
        if ((LocalDate.now().getYear() - birthDay.getYear() < 18) || LocalDate.now().getYear() - birthDay.getYear() < 0) {
            isErr = true;
            err.setDate_invalid("Ngày sinh không hợp lệ [ " + LocalDate.now().getYear() + " - năm sinh ] >= 18 !");
        }
        
        out.print("<div class=\"modal-body d-flex p-3\">\n"
                + "                                                <form action=\"UpdateTempEmployeeServlet\"\n"
                + "                                                      id=\"update-form-${" + e.getEmployeeID() + "}\n"
                + "                                                      class=\"update-forms\"\n"
                + "                                                      class=\"row g-3\">\n"
                + "                                                    <div class=\"information-items\" style=\"max-width: 350px; margin-left: 10px\">\n"
                + "                                                        <input type=\"hidden\" name=\"tmpID\" value=\"${" + e.getEmployeeID() + "}\">\n"
                + "                                                        <label class=\"modal-label\">Last Name</label><input type=\"text\" name=\"lastName\" value=\"${" + e.getLastName() + "}\"><br>\n"
                + "                                                        <label class=\"modal-label\">Middle Name</label><input type=\"text\" name=\"middleName\" value=\"${" + e.getMiddleName() + "}\"><br>\n"
                + "                                                        <label class=\"modal-label\">First Name</label><input type=\"text\" name=\"firstName\" value=\"${" + e.getFirstName() + "}\"><br>\n");
        if (err.getNull_error() != null) {
            out.print("<p>*Không bỏ trống !</p>");
        }
        out.print("                                                    </div>\n"
                + "                                                    <div class=\"information-items\"  style=\"max-width: 350px;\">\n"
                + "                                                        <label class=\"modal-label\">Email</label><input type=\"text\" name=\"Email\" value=\"${" + e.getEmail() + "}\"><br>\n");
        if (err.getEmail_format_error() != null) {
            out.print("<p>*Không đúng định dạng Email</p>");
        }
        out.print("                                                        <label class=\"modal-label\">CCCD</label><input type=\"text\" name=\"CCCD\" value=\"${" + e.getCccd() + "}\"><br>\n");
        if (err.getCccd_format_error() != null) {
            out.print("<p>*Không đúng định dạng CCCD</p>");
        }
        out.print("                                                        <label class=\"modal-label\">Phone</label><input type=\"text\" name=\"Phone\" value=\"${" + e.getPhoneNumber() + "}\"><br>\n");
        if (err.getPhone_format_error() != null) {
            out.print("<p>*Không đúng định dạng SĐT</p>");
        }
        out.print("                                                    </div>\n"
                + "                                                    <div class=\"information-items\"  style=\"max-width: 350px;\">\n"
                + "                                                        <label class=\"modal-label\">Gender</label><input type=\"text\" name=\"Gender\" value=\"${" + e.getGender() + "?\"Male\":\"Female\"}\"><br>\n"
                + "                                                        <label class=\"modal-label\">BirthDate</label><input type=\"text\" name=\"BirthDate\" value=\"${" + e.getBirthDate() + "}\"><br>\n");
        if (err.getDate_invalid() != null) {
            out.print("<p>*Số tuổi >= 18</p>");
        }
        out.print("                                                        <label class=\"modal-label\">Password</label><input type=\"text\" name=\"Password\" value=\"${" + e.getPassword() + "}\"><br>\n"
                + "                                                    </div>\n"
                + "                                                    <div class=\"information-items\"  style=\"max-width: 350px;\">\n"
                + "                                                        <label class=\"modal-label\">TypeID</label><input type=\"text\" name=\"EmployeeType\" value=\"${" + e.getEmployeeTypeID() + "}\"><br>\n"
                + "                                                        <label class=\"modal-label\">DepartmentID</label><input type=\"text\" name=\"Department\" value=\"${" + e.getDepartmentID() + "}\"><br>\n"
                + "                                                        <label class=\"modal-label\">RoleID</label><input type=\"text\" name=\"Role\" value=\"${" + e.getRoleID() + "}\"><br>\n"
                + "                                                    </div>\n"
                + "                                                    <div class=\"information-items\"  style=\"max-width: 350px;\">\n"
                + "                                                        <label class=\"modal-label\">StartDate</label><input type=\"text\" name=\"StartDate\" value=\"${" + e.getStartDate() + "}\"><br>\n"
                + "                                                        <label class=\"modal-label\">EndDate</label><input type=\"text\" name=\"EndDate\" value=\"${" + e.getEndDate() + "}\"><br>\n");
        if (endDate != null && startDate != null) {
            if (endDate.isBefore(timeAfterNMonths(startDate, 6))) {
                out.print("<p>EndDate > StartDate (ít nhất 6 tháng)</p>");
            }
        }
        out.print("                                                    </div>\n"
                + "                                                </form>\n"
                + "                                            </div>"
        );
        
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
