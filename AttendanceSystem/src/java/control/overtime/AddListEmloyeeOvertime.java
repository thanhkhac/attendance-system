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
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import model.EmployeeDTO;
import model.OvertimeDAO;

/**
 *
 * @author Admin
 */
@WebServlet(name="AddListEmloyeeOvertime", urlPatterns={"/addListEmloyeeOvertime"})
public class AddListEmloyeeOvertime extends HttpServlet {
   
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
        String date = request.getParameter("Date");
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String list = request.getParameter("listEmployeeAdd");
        String[] listID = list.split("\\|");
        //out.print(listID[1]);
        int listIDInt [] = new int [listID.length];
        int count=0;
        for(int i=0;i<listID.length;i++){
            if(listID[i].length()>0){
               listIDInt[count] = Integer.parseInt(listID[i]);
               count++;
            }
        }
        if(listIDInt.length>0){
        HttpSession session = request.getSession();
        EmployeeDTO acc =  (EmployeeDTO)  session.getAttribute("ACCOUNT");
        LocalTime StartTime = LocalTime.parse(start).minusMinutes(30);
        LocalTime EndTime = LocalTime.parse(end).plusMinutes(30);
        String open = StartTime.toString();
        String checkOut = EndTime.toString();
        OvertimeDAO daoOver = new OvertimeDAO();
        boolean check = false;
        for(int i=0; i<listIDInt.length;i++){
            check = daoOver.insertOvertime(date, listIDInt[i], start, end, open, checkOut, null, null, acc.getEmployeeID());
        }
        request.setAttribute("THANHCONG", "Đã thêm thành công " + listIDInt.length + " nhân viên tăng ca từ "+ start +" đến "+ end +" vào ngày "+ date);
        request.setAttribute("START", start);
        request.setAttribute("END", end);
        request.setAttribute("DATE", date);
        request.getRequestDispatcher("OvertimeSuccess.jsp").forward(request, response);
        }else{
            request.setAttribute("START", start);
        request.setAttribute("END", end);
        request.setAttribute("DATE", date);
            request.setAttribute("THATBAI", "Chưa chọn nhân viên nào tăng ca vui lòng chọn lại");
            request.getRequestDispatcher("OvertimeSuccess.jsp").forward(request, response);
        }
        //out.print(date+"/"+start+"/"+end+"/"+list);
        //out.println(list);
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
