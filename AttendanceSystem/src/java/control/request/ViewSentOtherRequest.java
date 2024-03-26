package control.request;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.EmployeeDAO;
import model.EmployeeDTO;
import model.RequestTypeDAO;
import model.RequestTypeDTO;
import model.request.RequestDAO;
import model.request.RequestDTO;

@WebServlet(name="ViewSentOtherRequest", urlPatterns={"/ViewSentOtherRequest"})
public class ViewSentOtherRequest extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                int page = 1;
        int recordsPerPage = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int employeeID = ((EmployeeDTO) request.getSession().getAttribute("ACCOUNT")).getEmployeeId();
        
        RequestDAO requestDAO = new RequestDAO();
        ArrayList<RequestDTO> requests = requestDAO.getSentRequests((page - 1) * recordsPerPage, recordsPerPage , employeeID);
        ArrayList<EmployeeDTO> employees = new EmployeeDAO().GetAllEmployees();
        ArrayList<RequestTypeDTO> requestTypes = new RequestTypeDAO().getRequestTypeList();
        
        int numberOfRequests = requestDAO.getNumberOfSentRequests(employeeID);
        int numberOfPages = (int) Math.ceil(numberOfRequests * 1.0 / recordsPerPage);

        request.setAttribute("employees", employees);
        request.setAttribute("requestTypes", requestTypes);
        request.setAttribute("requests", requests);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", page);

        request.getRequestDispatcher("ViewSentOtherRequest.jsp").forward(request, response);
    } 
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
// </editor-fold>

}
