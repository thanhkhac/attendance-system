/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.statistic;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import model.EmployeeDTO;
import model.LeaveDAO;
import model.LeaveDTO;
import model.StatisticsDAO;
import model.StatisticsDTO;
import model.TimesheetDTO;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import ultility.datetimeutil.DateTimeUtil;

/**
 *
 * @author admin
 */
@WebServlet(name = "GetEmployeeStatisticsServlet", urlPatterns = {"/GetEmployeeStatisticsServlet"})
public class GetEmployeeStatisticsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private double getTotalHours(ArrayList<StatisticsDTO> statistics) {
        double total = 0;
        for (StatisticsDTO s : statistics) {
            double hours = (double) s.getTotalDay().toMinutes() / 60;
            total += hours;
        }
        return total;
    }

    private double getTotalOT(ArrayList<StatisticsDTO> statistics) {
        double total = 0;
        for (StatisticsDTO s : statistics) {
            double hours = (double) s.getOtHours().toMinutes() / 60;
            total += hours;
        }
        return total;
    }

    private double getTotalShift(ArrayList<StatisticsDTO> statistics) {
        double total = 0;
        for (StatisticsDTO s : statistics) {
            double hours = (double) s.getShiftHours().toMinutes() / 60;
            total += hours;
        }
        return total;
    }

    private int getTotalScheduledDay(ArrayList<StatisticsDTO> statistics) {
        int count = 0;
        for (StatisticsDTO s : statistics) {
            if (s.getStartTime() != null) {
                count++;
            }
        }
        return count;
    }

    private int getTotalNotWorkedDay(ArrayList<StatisticsDTO> statistics) {
        int count = 0;
        for (StatisticsDTO s : statistics) {
            if (s.getStartTime() != null && s.getCheckIn() == null) {
                count++;
            }
        }
        return count;
    }

    private int getTotalWorkedDays(ArrayList<StatisticsDTO> statistics) {
        int count = 0;
        for (StatisticsDTO s : statistics) {
            if (s.getStartTime() != null && s.getCheckIn() != null) {
                count++;
            }
        }
        return count;
    }

    private int getLeaveDay(LeaveDTO leave) {
        long daysBetween = ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate());
        return Math.toIntExact(daysBetween);
    }

    private int getTotalLeaveDays(ArrayList<LeaveDTO> leaves) {
        int count = 0;
        for (LeaveDTO l : leaves) {
            count += getLeaveDay(l);
        }
        return count + 1;
    }

    private final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("ACCOUNT");
        String startDate_txt = request.getParameter("startDate");
        String endDate_txt = request.getParameter("endDate");
        LocalDate startDate = DATE_UTIL.parseSqlDate(employee.getStartDate());
        LocalDate endDate = DATE_UTIL.getVNLocalDateNow();
        LeaveDAO leaveDAO = new LeaveDAO();
        ArrayList<LeaveDTO> leaves = leaveDAO.getLeaveInRange(employee.getEmployeeID(), startDate, endDate);
        ArrayList<StatisticsDTO> statistics = new ArrayList<>();
        StatisticsDAO staDAO = new StatisticsDAO();
        try {
            if (startDate_txt == null) {
                startDate = DATE_UTIL.parseSqlDate(employee.getStartDate());
            } else {
                startDate = LocalDate.parse(request.getParameter("startDate"));
            }
            if (endDate_txt == null) {
                endDate = DATE_UTIL.getVNLocalDateNow();
            } else {
                endDate = LocalDate.parse(request.getParameter("endDate"));
            }
            statistics = staDAO.getStatistics(employee.getEmployeeID(), startDate, endDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        System.out.println("Total Scheduled Days: " + getTotalScheduledDay(statistics));
        System.out.println("Total worked Days: " + getTotalWorkedDays(statistics));
        System.out.println("Total notworked Days: " + getTotalNotWorkedDay(statistics));
        System.out.println("Total leaves Days: " + getTotalLeaveDays(leaves));

        request.setAttribute("workedDays", getTotalWorkedDays(statistics));
        request.setAttribute("notWorkedDays", getTotalNotWorkedDay(statistics));
        request.setAttribute("leaveDays", getTotalLeaveDays(leaves));
        request.setAttribute("scheduledDays", getTotalScheduledDay(statistics));

        request.setAttribute("totalShift", Double.parseDouble(decimalFormat.format(getTotalShift(statistics))));
        request.setAttribute("totalOT", Double.parseDouble(decimalFormat.format(getTotalOT(statistics))));
        request.setAttribute("totalHour", Double.parseDouble(decimalFormat.format(getTotalHours(statistics))));

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("current", DATE_UTIL.getVNLocalDateNow());
        request.setAttribute("statistics", statistics);
        request.getRequestDispatcher("ViewStatisticsForEmployee.jsp").forward(request, response);
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
