/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.request;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.EmployeeDTO;
import model.LeaveRequestDAO;

/**
 *
 * @author admin
 */
//@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, //2MB
//        maxFileSize = 1024 * 1024 * 10, //10MB
//        maxRequestSize = 1024 * 1024 * 50)//50MB
//@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
//        maxFileSize = 1024 * 1024 * 10,
//        maxRequestSize = 1024 * 1024 * 50)
@WebServlet(name = "InsertLeaveRequestServlet", urlPatterns = {"/InsertLeaveRequestServlet"})
public class InsertLeaveRequestServlet extends HttpServlet {

//    private static final long serialVersionUID = 1L;
//    public static final String SAVE_DIRECTORY = "Leave-Request-AttachedFiles";
//
//    public InsertLeaveRequestServlet() {
//        super();
//    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //    private String extractFileName(Part part) {
//        // form-data; name="file"; filename="C:\file1.zip"
//        // form-data; name="file"; filename="C:\Note\file2.zip"
//        String contentDisp = part.getHeader("content-disposition");
//        String[] items = contentDisp.split(";");
//        for (String s : items) {
//            if (s.trim().startsWith("filename")) {
//                // C:\file1.zip
//                // C:\Note\file2.zip
//                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
//                clientFileName = clientFileName.replace("\\", "/");
//                int i = clientFileName.lastIndexOf('/');
//                // file1.zip
//                // file2.zip
//                return clientFileName.substring(i + 1);
//            }
//        }
//        return null;
//    }
    private LocalDate timeAfterNMonths(LocalDate time, int monthToAdd) {
        LocalDate afterNMonth = time.plusMonths(monthToAdd);
        return afterNMonth;
    }

    private boolean isAcceptableDate(LocalDate startDate, LocalDate endDate) {
        LocalDate current = LocalDate.now();
        if (startDate.isEqual(endDate) || startDate.isBefore(endDate)) {
//            System.out.println("1");
            if ((startDate.isEqual(timeAfterNMonths(current, 12)) || startDate.isBefore(timeAfterNMonths(current, 12)))) {
//                System.out.println("2");
                if ((startDate.isEqual(current) || startDate.isAfter(current))
                        && (endDate.isEqual(current) || endDate.isAfter(current))) {
//                    System.out.println("3");
                    if ((startDate.isEqual(timeAfterNMonths(current, 1)) || startDate.isBefore(timeAfterNMonths(current, 1)))
                            && (endDate.isEqual(timeAfterNMonths(startDate, 6)) || endDate.isBefore(timeAfterNMonths(startDate, 6)))) {
//                        System.out.println("4");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        LeaveRequestDAO dao = new LeaveRequestDAO();
        String URL = "Error.jsp";
        try {
            EmployeeDTO account = (EmployeeDTO) session.getAttribute("ACCOUNT");
            LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
            LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
            String reason = request.getParameter("reason");
            LocalDate sentDate = LocalDate.now();
            boolean isValid = isAcceptableDate(startDate, endDate);
            System.out.println(isValid);
            if (isValid) {
                boolean rs = dao.InsertLeaveRequest(account, sentDate, startDate, endDate, reason);
                if (rs) {
                    URL = "Success.jsp";
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.getRequestDispatcher(URL).forward(request, response);
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
