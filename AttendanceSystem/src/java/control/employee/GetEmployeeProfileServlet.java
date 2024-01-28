package control.employee;

import java.io.IOException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import model.DepartmentDAO;
import model.DepartmentDTO;
import model.EmployeeDAO;
import model.EmployeeDTO;
import model.EmployeeTypeDTO;
import model.RoleDAO;
import model.RoleDTO;

@WebServlet(name = "ViewProfileServlet", urlPatterns = {"/viewProfile"})
public class GetEmployeeProfileServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        RoleDAO roleDAO = new RoleDAO();
        HttpSession session = request.getSession();
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("ACCOUNT");
        if (employee != null) {
            EmployeeTypeDTO employeeTypeDTO
                    = employeeDAO.getEmployeeType(employee.getEmployeeTypeID());
            DepartmentDTO departmentDTO
                    = departmentDAO.getDepartmentById(employee.getDepartmentID());
            RoleDTO roleDTO
                    = roleDAO.getRoleById(employee.getRoleID());

            request.setAttribute("departmentDTO", departmentDTO);
            request.setAttribute("employeeTypeDTO", employeeTypeDTO);
            request.setAttribute("roleDTO", roleDTO);
            request.setAttribute("employee", employee);
             request.getRequestDispatcher("ViewEmployeeProfile.jsp").forward(request, response);
        } else {
            response.sendRedirect("Login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
