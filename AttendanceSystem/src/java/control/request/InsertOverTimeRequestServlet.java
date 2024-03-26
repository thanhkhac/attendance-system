/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.request;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import model.EmployeeDTO;
import model.request.OverTimeRequestDAO;
import model.request.SendRequestError;

/**
 *
 * @author admin
 */
@WebServlet(name = "InsertOverTimeRequestServlet", urlPatterns = {"/InsertOverTimeRequestServlet"})
public class InsertOverTimeRequestServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private boolean isAcceptableTime(Time startTime, Time endTime) {
        Time currentTime = new Time(System.currentTimeMillis());
        final Time fixedStartTime = Time.valueOf("00:00:00");
        final Time fixedEndTime = Time.valueOf("23:59:59");

        if (startTime.before(endTime)) {
            if ((startTime.after(fixedStartTime) || startTime.equals(fixedStartTime))
                    && (endTime.before(fixedEndTime) || endTime.equals(fixedEndTime))) {
                return true;
            }
        }
        return false;
    }

    private LocalDate timeAfterNMonths(LocalDate time, int monthToAdd) {
        LocalDate afterNMonth = time.plusMonths(monthToAdd);
        return afterNMonth;
    }

    public boolean isAcceptableDate(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        if (date.isAfter(currentDate) || date.isEqual(currentDate)) { //date >= current
            return true;
        }
        return false;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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
        HttpSession session = request.getSession();
        EmployeeDTO e = (EmployeeDTO) session.getAttribute("ACCOUNT");
        String startTime_raw = request.getParameter("startTime");
        String endTime_raw = request.getParameter("endTime");
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        LocalDate current = LocalDate.now();
        Time startTime = null;
        Time endTime = null;
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        SendRequestError err = new SendRequestError();
        OverTimeRequestDAO dao = new OverTimeRequestDAO();
        boolean isErr = false;
        String URL = "Error.jsp";
        try {
            java.util.Date timeStart = format.parse(startTime_raw);
            java.util.Date timeEnd = format.parse(endTime_raw);
            startTime = new Time(timeStart.getTime());
            endTime = new Time(timeEnd.getTime());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println(isAcceptableTime(startTime, endTime));
        if (!isAcceptableTime(startTime, endTime)) {
            isErr = true;
            err.setInvalidTime_error("Khoảng thời gian không hợp lệ !");
        }
        if (!isAcceptableDate(date)) {
            isErr = true;
            err.setInvalidDate_error("Ngày tăng ca không hợp lệ !");
        }
        if (e.getRoleID() == 4 || e.getRoleID() == 5) {
            isErr = true;
            err.setInvalidRole_error("Không áp dụng tăng ca với một số chức vụ !");
        }
        if (!isErr) {
            boolean rs = dao.insertOverTimeRequest(e, current, date, startTime, endTime, e.getEmployeeID());
            if (rs) {
                URL = "Success.jsp";
            } else {
                URL = "Error.jsp";
            }
        } else {
            request.setAttribute("msg", "Gửi Yêu Cầu Thất Bại !");
            request.setAttribute("date", date);
            request.setAttribute("startTime", startTime_raw);
            request.setAttribute("endTime", endTime_raw);
            request.setAttribute("error", err);
            URL = "PrepareRequestServlet?requestTypeID=1";
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
