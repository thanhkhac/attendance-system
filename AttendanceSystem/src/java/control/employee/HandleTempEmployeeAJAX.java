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
import model.DepartmentDAO;
import model.DepartmentDTO;
import model.EmployeeDAO;
import model.EmployeeDTO;
import model.EmployeeTypeDAO;
import model.EmployeeTypeDTO;
import model.RoleDAO;
import model.RoleDTO;
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

    private LocalDate timeAfterNYears(LocalDate time, int yearToAdd) {
        LocalDate afterNYears = time.plusYears(yearToAdd);
        return afterNYears;
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

        DepartmentDAO deDAO = new DepartmentDAO();
        EmployeeTypeDAO emDAO = new EmployeeTypeDAO();
        RoleDAO roleDAO = new RoleDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        ArrayList<DepartmentDTO> department = deDAO.getListDepartment();
        ArrayList<EmployeeTypeDTO> employeeType = emDAO.getEmployeeTypeList();
        ArrayList<RoleDTO> roles = roleDAO.getRoleList();

        int departmentID = 0;
        int typeID = 0;
        int roleID = 0;
        boolean gender = false;

        boolean isEmailExist = txt_email.equals(employeeDAO.getEmail(txt_email));
        boolean isCCCDExist = txt_cccd.equals(employeeDAO.getCCCD(txt_cccd));
        boolean isPhoneNumberExist = txt_phoneNumber.equals(employeeDAO.getPhonenumber(txt_phoneNumber));

        boolean isErr = false;
        String regexName = "^[^\\d\\p{Punct}]+$";
        String messageNameError = "Thành phần của Tên ko được chứa số hay kí tự đặc biệt !";
        EmployeeDTO e = new EmployeeDTO();
        try {
            int id = Integer.parseInt(txt_employeeID);
            if (txt_birthday != null && txt_birthday.length() > 0) {
                birthDay = LocalDate.parse(txt_birthday);
            }
            e = getEmployeeByTmpID(employees, id);
            startDate = LocalDate.parse(txt_startDate);
            endDate = LocalDate.parse(txt_endDate);

            departmentID = Integer.parseInt(txt_departmentID);
            typeID = Integer.parseInt(txt_typeID);
            roleID = Integer.parseInt(txt_roleID);

            gender = Boolean.parseBoolean(txt_gender);
            e.setGender(gender);
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
        if (LocalDate.now().isBefore(timeAfterNYears(birthDay, 19))
                || LocalDate.now().getYear() - birthDay.getYear() >= 60
                || birthDay.isAfter(LocalDate.now())
                || birthDay.getYear() > LocalDate.now().getYear()) {
            isErr = true;
            err.setDate_invalid("Ngày sinh không hợp lệ [ " + LocalDate.now().getYear() + " - năm sinh ] >= 18 !");
        }
        out.print("<div class=\"text-center\">\n"
                + "                                                <h2><i class=\"fa-solid fa-up-down fa-2xl\"></i></h2>\n"
                + "                                            </div>");
        out.print("<div id=\"body-ajax-" + e.getEmployeeID() + "\" class=\"modal-body d-flex p-3\">\n"
                + "                                                <form action=\"UpdateTempEmployeeServlet\"\n"
                + "                                                      id=\"update-form-" + e.getEmployeeID() + "\n"
                + "                                                      class=\"update-forms\"\n"
                + "                                                      class=\"row g-3\">\n"
                + "                                                    <div class=\"information-items\" style=\"max-width: 350px; margin-left: 10px\">\n"
                + "                                                        <input type=\"hidden\" name=\"tmpID\" value=\"" + e.getEmployeeID() + "\">\n"
                + "                                                        <label class=\"modal-label\">Last Name</label><input oninput=\"checkByChange(event," + e.getEmployeeID() + ")\" id=\"lastName-" + e.getEmployeeID() + "\" type=\"text\" name=\"lastName\" value=\"" + txt_lastName + "\"><br>\n"
                + "                                                        <label class=\"modal-label\">Middle Name</label><input oninput=\"checkByChange(event," + e.getEmployeeID() + ")\"  id=\"middleName-" + e.getEmployeeID() + "\" type=\"text\" name=\"middleName\" value=\"" + txt_middleName + "\"><br>\n"
                + "                                                        <label class=\"modal-label\">First Name</label><input oninput=\"checkByChange(event," + e.getEmployeeID() + ")\"  id=\"firstName-" + e.getEmployeeID() + "\" type=\"text\" name=\"firstName\" value=\"" + txt_firstName + "\"><br>\n");
        if (err.getNull_error() != null) {
            out.print("<p style=\"color:red;\" >*Không bỏ trống !</p>");
        }
        if (err.getName_format_error() != null) {
            out.print("<p style=\"color:red;\" >*Tên chỉ chứa chữ cái !</p>");
        }
        out.print("                                                    </div>\n"
                + "                                                    <div class=\"information-items\"  style=\"max-width: 350px;\">\n"
                + "                                                        <label class=\"modal-label\">Email</label><input oninput=\"checkByChange(event," + e.getEmployeeID() + ")\"  id=\"email-" + e.getEmployeeID() + "\" type=\"text\" name=\"Email\" value=\"" + txt_email + "\"><br>\n");
        if (err.getEmail_format_error() != null) {
            out.print("<p style=\"color:red;\" >*Không đúng định dạng Email</p>");
        } else if (isEmailExist) {
            out.print("<p style=\"color:red;\" >*Email đã tồn tại</p>");
        }
        out.print("                                                        <label class=\"modal-label\">CCCD</label><input oninput=\"checkByChange(event," + e.getEmployeeID() + ")\"  id=\"cccd-" + e.getEmployeeID() + "\"  type=\"text\" name=\"CCCD\" value=\"" + txt_cccd + "\"><br>\n");
        if (err.getCccd_format_error() != null) {
            out.print("<p style=\"color:red;\" >*Không đúng định dạng CCCD</p>");
        } else if (isCCCDExist) {
            out.print("<p style=\"color:red;\" >*CCCD đã tồn tại</p>");
        }
        out.print("                                                        <label class=\"modal-label\">Phone</label><input oninput=\"checkByChange(event," + e.getEmployeeID() + ")\"  id=\"phone-" + e.getEmployeeID() + "\"  type=\"text\" name=\"Phone\" value=\"" + txt_phoneNumber + "\"><br>\n");
        if (err.getPhone_format_error() != null) {
            out.print("<p style=\"color:red;\" >*Không đúng định dạng SĐT</p>");
        } else if (isPhoneNumberExist) {
            out.print("<p style=\"color:red;\" >*SĐT đã tồn tại</p>");
        }
        out.print("                                                    </div>\n"
                + "                                                    <div class=\"information-items\"  style=\"max-width: 350px;\">\n"
                + "                                                        <label class=\"modal-label\">Gender</label>");
        out.print("<input oninput=\"checkByChange(event," + e.getEmployeeID() + ")\" id=\"gender-" + e.getEmployeeID() + "\" type=\"text\" name=\"Gender\" value=\"" + (e.getGender() ? "Nam" : "Nữ") + "\"><br>\n"
                //                + "<select  disable  onchange=\"checkByChange(event," + e.getEmployeeID() + ")\"  name=\"Gender\" id=\"gender-" + e.getEmployeeID() + "\">\n"
                //                + "<option " + e.getGender() + " == true?'selected':'' value=\"true\">Nam</option>\n"
                //                + "<option " + e.getGender() + " == false?'selected':''value=\"false\">Nữ</option>\n"
                //                + "</select><br>\n"

                + "                                                        <label class=\"modal-label\">BirthDate</label><input onchange=\"checkByChange(event," + e.getEmployeeID() + ")\"  id=\"birthDate-" + e.getEmployeeID() + "\" type=\"date\" name=\"BirthDate\" value=\"" + txt_birthday + "\"><br>\n");
        if (err.getDate_invalid() != null) {
            out.print("<p style=\"color:red;\" >*Số tuổi >= 18</p>");
        }
        out.print("                                                        <label class=\"modal-label\">Password</label><input oninput=\"checkByChange(event," + e.getEmployeeID() + ")\"  id=\"password-" + e.getEmployeeID() + "\" type=\"text\" name=\"Password\" value=\"" + txt_password + "\"><br>\n"
                + "                                                    </div>\n"
                + "                                                    <div class=\"information-items\"  style=\"max-width: 350px;\">\n"
                + "                                                        <label class=\"modal-label\">TypeID</label><select disable onchange=\"checkByChange(event," + e.getEmployeeID() + ")\"  name=\"EmployeeType\" id=\"type-" + e.getEmployeeID() + "\">\n");

        for (EmployeeTypeDTO t : employeeType) {
            out.print("                                                                <option \n");
            if (t.getEmployeeTypeID() == typeID) {
                out.print(" selected ");
            }
            out.print("                                                                    value=\"" + t.getEmployeeTypeID() + "\">" + t.getName() + "");
            out.print("</option>\n");
        }
        out.print("                                                        </select><br>\n"
                + "                                                        <label class=\"modal-label\">DepartmentID</label><select disable onchange=\"checkByChange(event," + e.getEmployeeID() + ")\"  name=\"Department\" id=\"department-" + e.getEmployeeID() + "\">\n");
        for (DepartmentDTO d : department) {
            out.print("                                                                <option \n");
            if (d.getDepartmentID() == departmentID) {
                out.print(" selected ");
            }
            out.print("                                                                    value=\"" + d.getDepartmentID() + "\">" + d.getName() + "");
            out.print("</option>\n");
        }
        out.print("                                                        </select><br>\n"
                + "                                                        <label class=\"modal-label\">RoleID</label><select disable  onchange=\"checkByChange(event," + e.getEmployeeID() + ")\"  name=\"Role\" id=\"role-" + e.getEmployeeID() + "\">\n");
        for (RoleDTO r : roles) {
            out.print("                                                                <option \n");
            if (r.getRoleID() == roleID) {
                out.print(" selected ");
            }
            out.print("                                                                    value=\"" + r.getRoleID() + "\">" + r.getName() + "");
            out.print("</option>\n");
        }
        out.print("                                                        </select><br>\n"
                + "                                                    </div>\n"
                + "                                                    <div class=\"information-items\"  style=\"max-width: 350px;\">\n"
                + "                                                        <label class=\"modal-label\">StartDate</label><input onchange=\"checkByChange(event," + e.getEmployeeID() + ")\" id=\"startDate-" + e.getEmployeeID() + "\" type=\"date\" name=\"StartDate\" value=\"" + txt_startDate + "\"><br>\n"
                + "                                                        <label class=\"modal-label\">EndDate</label><input onchange=\"checkByChange(event," + e.getEmployeeID() + ")\" id=\"endDate-" + e.getEmployeeID() + "\" type=\"date\" name=\"EndDate\" value=\"" + txt_endDate + "\"><br>\n");
        if (endDate != null && startDate != null) {
            if (endDate.isBefore(timeAfterNMonths(startDate, 6))) {
                out.print("<p style=\"color:red;\" >EndDate > StartDate (ít nhất 6 tháng)</p>");
            }
        }
        out.print("                                                    </div>\n"
                + "                                                </form>\n"
                + "                                            </div>"
                + "<div class=\"modal-footer\">\n");

        if (!isErr) {
            out.print("<h5 style=\"margin: 0px 10px; color: green;\"><i class=\"fa-regular fa-circle-check\"></i> Is Acceptable</h5>\n");
            out.print("                                                    <button onclick=\"submitForm(this)\"\n"
                    + "                                                            id=\"" + e.getEmployeeID() + "\"\n"
                    + "                                                            class=\"btn btn-success\"\n"
                    + "                                                            >Save Change</button>\n"
                    + "                                                </div>");
        }
        session.setAttribute("departments", department);
        session.setAttribute("types", employeeType);
        session.setAttribute("roles", roles);
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
