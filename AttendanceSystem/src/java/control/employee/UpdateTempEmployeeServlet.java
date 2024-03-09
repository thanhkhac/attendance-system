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
@WebServlet(name = "UpdateTempEmployeeServlet", urlPatterns = {"/UpdateTempEmployeeServlet"})
public class UpdateTempEmployeeServlet extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        EmployeeDAO dao = new EmployeeDAO();
        String msg = "";
        int departmentID = 0;
        int typeID = 0;
        int roleID = 0;
        int employeeID = 0;
        Boolean gender = null;
        boolean isErr = false;
        String regexName = "^[^\\d\\p{Punct}]+$";
        String messageNameError = "Thành phần của Tên ko được chứa số hay kí tự đặc biệt !";
        try {
            birthDay = LocalDate.parse(txt_birthday);
            departmentID = Integer.parseInt(txt_departmentID);
            typeID = Integer.parseInt(txt_typeID);
            roleID = Integer.parseInt(txt_roleID);
            gender = Boolean.parseBoolean(txt_gender);
            employeeID = Integer.parseInt(txt_employeeID);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        if (txt_firstName.length() <= 0) {
            err.setNull_error("Điền đầy đủ các thành phần của Tên !");
            isErr = true;
            txt_firstName = "Null";
            request.setAttribute("txt_firstName", txt_firstName);

        } else if (!txt_firstName.matches(regexName)) {
            err.setName_format_error(messageNameError);
            isErr = true;
            request.setAttribute("txt_firstName", txt_firstName);
        } else { //isTrue
            request.setAttribute("txt_firstName", txt_firstName);
        }

        if (txt_middleName.length() <= 0) {
            err.setNull_error("Điền đầy đủ các thành phần của Tên !");
            isErr = true;
            txt_middleName = "Null";
            request.setAttribute("txt_middleName", txt_middleName);
        } else if (!txt_middleName.matches(regexName)) {
            err.setName_format_error(messageNameError);
            isErr = true;
            request.setAttribute("txt_middleName", txt_middleName);
        } else { //isTrue
            request.setAttribute("txt_middleName", txt_middleName);
        }

        if (txt_lastName.length() <= 0) {
            err.setNull_error("Điền đầy đủ các thành phần của Tên !");
            isErr = true;
            txt_lastName = "Null";
            request.setAttribute("txt_lastName", txt_lastName);

        } else if (!txt_lastName.matches(regexName)) {
            err.setName_format_error(messageNameError);
            isErr = true;
            request.setAttribute("txt_lastName", txt_lastName);
        } else { //isTrue
            request.setAttribute("txt_lastName", txt_lastName);
        }

        if (!txt_cccd.matches("^0\\d{11}$")) {
            err.setCccd_format_error("Định dạng căn cước công dân không hợp lệ !");
            isErr = true;
            request.setAttribute("txt_cccd", txt_cccd);
        }
        if (!txt_phoneNumber.matches("^0\\d{9}$")) {
            err.setPhone_format_error("Định dạng SĐT không hợp lệ !");
            isErr = true;
            request.setAttribute("txt_phoneNumber", txt_phoneNumber);

        }
        if (!txt_email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            err.setEmail_format_error("Định dạng Email không hợp lệ !");
            isErr = true;
            request.setAttribute("txt_email", txt_email);

        }
        if (!txt_password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d\\s])[A-Za-z\\d@$!%*?&.,]{6,16}$")) {
            err.setPassword_format_error("Mật khẩu [6-16] kí tự , chứa 1 chữ cái in hoa và 1 kí tự ('@', '$', '%', '.', ',', '?', '&')");
            isErr = true;
            request.setAttribute("txt_password", txt_password);

        }
        if ((LocalDate.now().getYear() - birthDay.getYear() < 18) || LocalDate.now().getYear() - birthDay.getYear() < 0) {
            isErr = true;
            err.setDate_invalid("Ngày sinh không hợp lệ [ " + LocalDate.now().getYear() + " - năm sinh ] >= 18 !");
            request.setAttribute("txt_birthDate", txt_birthday);

        }
        if (txt_typeID.equals("3") && !txt_roleID.equals("1")) {
            msg = "Vai trò hiện tại không thể giao cho nhân viên Intern !";
            isErr = true;
        }
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
