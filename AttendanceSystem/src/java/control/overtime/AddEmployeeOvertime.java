/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package control.overtime;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import model.EmployeeDAO;
import model.EmployeeDTO;
import model.OvertimeDAO;
import model.OvertimeDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name="AddEmployeeOvertime", urlPatterns={"/addEmployeeOvertime"})
public class AddEmployeeOvertime extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String date = request.getParameter("date");
        String Add = request.getParameter("Add");
        LocalDate Date = LocalDate.parse(date);
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);       
        OvertimeDAO daoOver = new OvertimeDAO();
        ArrayList<OvertimeDTO> listOvertime = daoOver.getOverTimeDTOByDay(Date);
        ArrayList<OvertimeDTO> list = new ArrayList();
        
        
        
        for(OvertimeDTO overtime : listOvertime){
            if(list.size()==0){
                list.add(overtime);
            }
            else{
                int count = 0;
                for(OvertimeDTO check : list){
                    if(check.getStartTime() == overtime.getStartTime()){
                     count =1 ;   
                    }
                }
                if(count==0)
                    list.add(overtime);
            }
        }
        int count = 0;
        
        for(OvertimeDTO Overtime:list){
            if(Overtime.getStartTime().equals(start) && Overtime.getEndTime().equals(end))                
                count = 1;
        } 
        
        if(start.isAfter(end) || start.equals(end)){
        request.setAttribute("LISTOVERTIME", list);
        request.setAttribute("DAY", date);  
        request.setAttribute("ERRORSTARTTIME", "error");
        request.getRequestDispatcher("ViewOvertimeByDay.jsp").forward(request, response);
        }
        
        if(count == 0 || Add !=null ){
            EmployeeDAO dao = new EmployeeDAO();
            ArrayList<EmployeeDTO> listEmp = dao.getListAddEmployeeOvertime(date);
            int countPage =dao.countEmployeeOvertime(date, "", "", "", "", "");
            int endPage = 0;
            
        if (countPage % 10 == 0) {
            endPage = countPage / 10;
        } else {
            endPage = countPage / 10 + 1;
        }
        String EndPage = String.valueOf(endPage);
        
            request.setAttribute("DAY", date);    
            request.setAttribute("STARTTIME", startTime);
            request.setAttribute("ENDTIME", endTime);
            request.setAttribute("LIST", listEmp);
            request.setAttribute("COUNTPAGE", EndPage);
            request.getRequestDispatcher("AddListEmployeeOvertime.jsp").forward(request, response);
        }
        else{
        request.setAttribute("LISTOVERTIME", list);
        request.setAttribute("DAY", date);       
        request.setAttribute("ERROROVERTIME", "Ca này đã tồn tại");
        request.getRequestDispatcher("ViewOvertimeByDay.jsp").forward(request, response);
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
