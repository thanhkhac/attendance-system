package control.schedulework;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import model.*;
import ultility.datetimeutil.DateTimeUtil;

@WebServlet(name="ScheduleWork", urlPatterns={"/ScheduleWork"})
public class ScheduleWork extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DateTimeUtil dateTimeUtil = new DateTimeUtil();

        int year = dateTimeUtil.getVNLocalDateNow().getYear();
        int month = dateTimeUtil.getVNLocalDateNow().getMonthValue();

        String txtyear = request.getParameter("year");
        String txtmonth = request.getParameter("month");

        if (txtyear != null || txtmonth != null) {
            try {
                year = Integer.parseInt(txtyear);
                month = Integer.parseInt(txtmonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                month = Integer.parseInt(txtmonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        ArrayList<LocalDate> calendar = dateTimeUtil.getCalendar(year, month);
        ArrayList<ShiftDTO> shiftList = new ShiftDAO().getActiveShiftDTO();
        
        HashMap<String, ShiftDTO> shiftMap = new HashMap<>();
        for (ShiftDTO shift : shiftList) {
            shiftMap.put(String.valueOf(shift.getShiftID()), shift);
        }
        
        LocalDate today = dateTimeUtil.getVNLocalDateNow();
        request.setAttribute("today", today);
        request.setAttribute("calendar", calendar);
        request.setAttribute("month", month);
        request.setAttribute("year", year);
        request.setAttribute("shiftList", shiftList);
        request.setAttribute("shiftMap", shiftMap);
        
        request.getRequestDispatcher("ScheduleWork.jsp").forward(request, response);
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
