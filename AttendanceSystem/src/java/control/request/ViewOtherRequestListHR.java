package control.request;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.*;
import model.request.RequestDAO;
import model.request.RequestDTO;

@WebServlet(name = "ViewOtherRequestListHR", urlPatterns = {"/ViewOtherRequestListHR"})
public class ViewOtherRequestListHR extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        RequestDAO requestDAO = new RequestDAO();
        ArrayList<RequestDTO> requests = requestDAO.getRequests((page - 1) * recordsPerPage, recordsPerPage );
        ArrayList<EmployeeDTO> employees = new EmployeeDAO().GetAllEmployees();
        ArrayList<RequestTypeDTO> requestTypes = new RequestTypeDAO().getRequestTypeList();
        
        int numberOfRequests = requestDAO.getNumberOfRequests();
        int numberOfPages = (int) Math.ceil(numberOfRequests * 1.0 / recordsPerPage);

        request.setAttribute("employees", employees);
        request.setAttribute("requestTypes", requestTypes);
        request.setAttribute("requests", requests);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", page);

        request.getRequestDispatcher("ViewOtherRequestListHR.jsp").forward(request, response);
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
