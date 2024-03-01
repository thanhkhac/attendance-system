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
import java.time.LocalDate;
import java.util.ArrayList;
import model.EmployeeDTO;
import model.StatisticsDAO;
import model.StatisticsDTO;
import ultility.datetimeutil.DateTimeUtil;

/**
 *
 * @author admin
 */
@WebServlet(name = "GetStatisticsByAJAXServlet", urlPatterns = {"/GetStatisticsByAJAXServlet"})
public class GetStatisticsByAJAXServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("ACCOUNT");
        LocalDate startDate = DATE_UTIL.parseSqlDate(employee.getStartDate());
        LocalDate endDate = DATE_UTIL.getVNLocalDateNow();
        int page = 1;
        String page_txt = request.getParameter("Page");
        ArrayList<StatisticsDTO> statistics = new ArrayList<>();
        StatisticsDAO staDAO = new StatisticsDAO();

        try {
            if (request.getParameter("startDate").length() <= 0) {
                startDate = DATE_UTIL.parseSqlDate(employee.getStartDate());
            } else {
                startDate = LocalDate.parse(request.getParameter("startDate"));
            }
            if (request.getParameter("endDate").length() <= 0) {
                endDate = DATE_UTIL.getVNLocalDateNow();
            } else {
                endDate = LocalDate.parse(request.getParameter("endDate"));
            }
            if (page_txt != null || page_txt.length() > 0) {
                page = Integer.parseInt(page_txt);
            }
            statistics = staDAO.getStatisticInRangeByAJAX(employee.getEmployeeID(), startDate, endDate, page);
            out.print("<p>" + startDate + "</p>");
            out.print("<p>" + endDate + "</p>");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        out.print("<table border=\"1\" class=\"table table-bordered table-responsive-md table-hover\">\n"
                + "                <thead class=\"text-center\" >\n"
                + "                    <tr class=\"table-dark\">\n"
                + "                        <th>Date</th>\n"
                + "                        <th>Shift</th>\n"
                + "                        <th>StartTime</th>\n"
                + "                        <th>EndTime</th>\n"
                + "                        <th>CheckIn</th>\n"
                + "                        <th>CheckOut</th>\n"
                + "                        <th>OverTimeStart</th>\n"
                + "                        <th>OverTimeEnd</th>\n"
                + "                        <th>OverTimeCheckIn</th>\n"
                + "                        <th>OverTimeCheckOut</th>\n"
                + "                        <th>Total Hours</th>\n"
                + "                    </tr>\n"
                + "                    <tr class=\"table-active\">\n"
                + "                        <th>YYYY-mm-DD</th>\n"
                + "                        <th></th>\n"
                + "                        <th>HH:MM:SS</th>\n"
                + "                        <th>HH:MM:SS</th>\n"
                + "                        <th>HH:MM:SS</th>\n"
                + "                        <th>HH:MM:SS</th>\n"
                + "                        <th>HH:MM:SS</th>\n"
                + "                        <th>HH:MM:SS</th>\n"
                + "                        <th>HH:MM:SS</th>\n"
                + "                        <th>HH:MM:SS</th>\n"
                + "                        <th>HH:MM:SS</th>\n"
                + "                    </tr>\n"
                + "                </thead>\n"
                + "                <tbody>\n"
                + "                <div class=\"table-container\">\n");
        for (StatisticsDTO s : statistics) {
            out.print("                        <tr class=\"text-center\">\n"
                    + "                            <td>" + s.getDate() + "</td>\n"
                    + "                            <td>" + s.getShiftName() + "</td>\n"
                    + "                            <td>\n"
                    + "                                <c:if test= \"${" + s.getStartTime() + " == null}\">\n"
                    + "                                    <p>--</p>\n"
                    + "                                </c:if>\n"
                    + "                                <c:if test= \"" + s.getStartTime() + " != null}\">\n"
                    + "                                    " + s.getStartTime() + "\n"
                    + "                                </c:if>\n"
                    + "                            </td>\n"
                    + "                            <td>\n"
                    + "                                <c:if test= \"" + s.getEndTime() + " == null}\">\n"
                    + "                                    <p>--</p>\n"
                    + "                                </c:if>\n"
                    + "                                <c:if test= \"" + s.getEndTime() + " != null}\">\n"
                    + "                                    " + s.getEndTime() + "\n"
                    + "                                </c:if>\n"
                    + "                            </td>\n"
                    + "                            <td>\n"
                    + "                                <c:if test= \"" + s.getCheckIn() + " == null}\">\n"
                    + "                                    <p>--</p>\n"
                    + "                                </c:if>\n"
                    + "                                <c:if test= \"" + s.getCheckIn() + " != null}\">\n"
                    + "                                    " + s.getCheckIn() + "}\n"
                    + "                                </c:if>\n"
                    + "                            </td>\n"
                    + "                            <td>\n"
                    + "                                <c:if test= \"${" + s.getCheckOut() + " == null}\">\n"
                    + "                                    <p>--</p>\n"
                    + "                                </c:if>\n"
                    + "                                <c:if test= \"${" + s.getCheckOut() + " != null}\">\n"
                    + "                                    " + s.getCheckOut() + "\n"
                    + "                                </c:if>\n"
                    + "                            </td>\n"
                    + "                            <td>\n"
                    + "                                <c:if test= \"${" + s.getOtStartTime() + " == null}\">\n"
                    + "                                    <p>--</p>\n"
                    + "                                </c:if>\n"
                    + "                                <c:if test= \"${" + s.getOtStartTime() + " != null}\">\n"
                    + "                                    " + s.getOtStartTime() + "\n"
                    + "                                </c:if>\n"
                    + "                            </td>\n"
                    + "                            <td><c:if test= \"${" + s.getOtEndTime() + " == null}\">\n"
                    + "                                    <p>--</p>\n"
                    + "                                </c:if>\n"
                    + "                                <c:if test= \"${" + s.getOtEndTime() + " != null}\">\n"
                    + "                                   " + s.getOtEndTime() + "\n"
                    + "                                </c:if>\n"
                    + "                            </td>\n"
                    + "                            <td><c:if test= \"${" + s.getOtCheckIn() + " == null}\">\n"
                    + "                                    <p>--</p>\n"
                    + "                                </c:if>\n"
                    + "                                <c:if test= \"${" + s.getOtCheckIn() + " != null}\">\n"
                    + "                                    " + s.getOtCheckIn() + "\n"
                    + "                                </c:if>\n"
                    + "                            </td>\n"
                    + "                            <td>\n"
                    + "                                <c:if test= \"${" + s.getOtCheckOut() + " == null}\">\n"
                    + "                                    <p>--</p>\n"
                    + "                                </c:if>\n"
                    + "                                <c:if test= \"${" + s.getOtCheckOut() + " != null}\">\n"
                    + "                                    " + s.getOtCheckOut() + "}\n"
                    + "                                </c:if>\n"
                    + "                            </td>\n"
                    + "                            <td>\n"
                    + "                                ${String.format(\"%.2f\", " + s.getTotalDay().toMinutes() / 60 + "}\n"
                    + "                            </td>\n"
                    + "                        </tr>\n ");
        }

//                + "                    </c:forEach>\n");
        out.print("</div>\n"
                + "                </tbody>\n"
                + "            </table>\n");
        int endPage = 0;
        int countPage = staDAO.getCountStatistics(employee.getEmployeeID(), startDate, endDate);
        if (countPage % 10 == 0) {
            endPage = countPage / 10;
        } else {
            endPage = countPage / 10 + 1;
        }
        out.print("            <div class=\"text-center container\" >\n");
        if (page == 1) {
            out.print("                <ul class=\"pagination\" style=\"justify-content: end;\">\n"
                    + "                    <li class=\"page-item\"><a class=\"page-link\" href=\"#\">Trước</a></li>\n");
        } else {
            out.print("                <ul class=\"pagination\" style=\"justify-content: end;\">\n"
                    + "                <li class=\"page-item\"><a data-index=\"" + (page - 1) + "\" onclick=\"searchByDay(this)\" class=\"page-link\" href=\"#\">Trước</a></li>");
        }
        for (int i = 0; i <= endPage; i++) {
            if (i == page) {
                out.print("<li class=\"page-item\"\"><a style=\"background-color: #cfd5da96;\" class=\"page-link page pageNow\" data-index=\"" + i + "\" onclick=\"searchByDay(this)\" href=\"#\">" + i + "</a><li>");
            } else {
                out.print("<li class=\"page-item\"><a  class=\"page-link page\" data-index=\"" + i + "\" onclick=\"searchByDay(this)\" href=\"#\">" + i + "</a><li>");
            }
        }
        if (page == endPage) {
            out.print("<li class=\"page-item\"><a  class=\"page-link\" href=\"#\">Sau</a></li>");

        } else {
            out.print("<li class=\"page-item\"><a data-index=\"" + (page + 1) + "\" onclick=\"searchByDay(this)\" class=\"page-link\" href=\"#\">Sau</a></li>");
        }
        out.print("                </ul>\n"
                + "            </div>");

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
