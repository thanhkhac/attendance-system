/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.request;

import static control.request.SendOtherRequest.SAVE_DIRECTORY;
import java.io.IOException;
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
import java.util.ArrayList;
import model.EmployeeDTO;
import model.request.LeaveRequestDAO;
import model.request.SendRequestError;
import ultility.filehandler.UploadFile;

/**
 *
 * @author admin
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
@WebServlet(name = "InsertLeaveRequestServlet", urlPatterns = {"/InsertLeaveRequestServlet"})
public class InsertLeaveRequestServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    public static final String SAVE_DIRECTORY = "Leave-Request-AttachedFiles";
    
    public InsertLeaveRequestServlet() {
        super();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String extractFileName(Part part) {
        // form-data; name="file"; filename="C:\file1.zip"
        // form-data; name="file"; filename="C:\Note\file2.zip"
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                // C:\file1.zip
                // C:\Note\file2.zip
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');
                // file1.zip
                // file2.zip
                return clientFileName.substring(i + 1);
            }
        }
        return null;
    }
    
    private LocalDate timeAfterNMonths(LocalDate time, int monthToAdd) {
        LocalDate afterNMonth = time.plusMonths(monthToAdd);
        return afterNMonth;
    }
    
    private boolean isAcceptableDate(LocalDate startDate, LocalDate endDate) {
        LocalDate current = LocalDate.now();
//        if (startDate.isEqual(endDate) || startDate.isBefore(endDate)) {
//            if ((startDate.isEqual(timeAfterNMonths(current, 12)) || startDate.isBefore(timeAfterNMonths(current, 12)))) {
//                if ((startDate.isEqual(current) || startDate.isAfter(current))
//                        && (endDate.isEqual(current) || endDate.isAfter(current))) {
//                    if ((startDate.isEqual(timeAfterNMonths(current, 1)) || startDate.isBefore(timeAfterNMonths(current, 1)))
//                            && (endDate.isEqual(timeAfterNMonths(startDate, 6)) || endDate.isBefore(timeAfterNMonths(startDate, 6)))) {
//                        return true;
//                    }
//                }
//            }
//        }
        if (endDate.isAfter(startDate) || endDate.isEqual(startDate)) {
            if (startDate.isAfter(current) || startDate.isEqual(current)) {
                return true;
            }
        }
        
        return false;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
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
        
        
        String appPath = request.getServletContext().getRealPath("");
        String fullSavePath = appPath + File.separator + SAVE_DIRECTORY;
        String backUpPath = fullSavePath.replace(File.separator + "build", "");
        ArrayList<String> savePaths = new ArrayList<>();
        savePaths.add(backUpPath);
        savePaths.add(fullSavePath);

        //Đây sẽ là đường dẫn cần lấy và lưu vào database
        String filepath = new UploadFile().saveFile(request, "file", savePaths, SAVE_DIRECTORY);
        
        
        
        HttpSession session = request.getSession();
        LeaveRequestDAO dao = new LeaveRequestDAO();
        String URL = "Error.jsp";
        SendRequestError err = new SendRequestError();
        boolean isErr = false;
        EmployeeDTO account = (EmployeeDTO) session.getAttribute("ACCOUNT");
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
        String reason = request.getParameter("reason");
        LocalDate sentDate = LocalDate.now();
        if (startDate == null || endDate == null || reason.length() <= 0) {
            isErr = true;
            err.setNullValue_error("Không được bỏ trống !");
        }
        if (!isAcceptableDate(startDate, endDate)) {
            isErr = true;
            err.setInvalidDate_error("Khoảng thời gian không hợp lệ !");
        }
        if (reason.length() <= 0 || reason.length() > 250) {
            isErr = true;
            err.setReasonLength_error("Lý Do [1-250] kí tự");
        }
        
        if (!isErr) {
            boolean rs = dao.InsertLeaveRequest(account, sentDate, startDate, endDate, reason, filepath, account.getEmployeeId());
            if (rs) {
                URL = "Success.jsp";
            } else {
                URL = "Error.jsp";
            }
        } else {
            request.setAttribute("msg", "Gửi Yêu Cầu Thất Bại !");
            request.setAttribute("error", err);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("reason", reason);
            URL = "PrepareRequestServlet?requestTypeID=2";
        }
        request.getRequestDispatcher(URL).forward(request, response);
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
