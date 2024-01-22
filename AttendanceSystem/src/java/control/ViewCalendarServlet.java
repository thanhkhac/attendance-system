//package control;
//
//import java.io.IOException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import model.TimesheetDAO;
//import model.WorkingDay;
//import ultility.datetimeutil.DateTimeUtil;
//
//public class ViewCalendarServlet extends HttpServlet {
//
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        DateTimeUtil dateTimeUtil = new DateTimeUtil();
//
//        int year = dateTimeUtil.getVNLocalDateNow().getYear();
//        int month = dateTimeUtil.getVNLocalDateNow().getMonthValue();
//
//        String txtyear = request.getParameter("year");
//        String txtmonth = request.getParameter("month");
//
//        if (txtyear != null || txtmonth != null) {
//            try {
//                year = Integer.parseInt(txtyear);
//                month = Integer.parseInt(txtmonth);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                month = Integer.parseInt(txtmonth);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        int employeeID = 1;
//        
//        
//        TimesheetDAO tsDAO = new TimesheetDAO();
//        ArrayList<LocalDate> calendar = dateTimeUtil.getCalendar(year, month);
//        
//        ArrayList<WorkingDay> workingdays = new ArrayList<>();
//        for (LocalDate localDate : calendar) {
//            workingdays.add(new WorkingDay(localDate, employeeID));
//        }
//        LocalDate today = dateTimeUtil.getVNLocalDateNow();
//        request.setAttribute("workingdays", workingdays);
//        request.setAttribute("today", today);
//        request.getRequestDispatcher("view-calendar.jsp").forward(request, response);
//    }
//// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        processRequest(request, response);
//    }
//// </editor-fold>
//
//}
