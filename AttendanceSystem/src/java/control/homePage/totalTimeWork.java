/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package control.homePage;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.EmployeeDTO;
import model.TotalTimeWorkDAO;
import model.TotalTimeWorkDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name="totalTimeWork", urlPatterns={"/totalTimeWork"})
public class totalTimeWork extends HttpServlet {
   
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
       String start = request.getParameter("start");
       String end = request.getParameter("end");
       HttpSession session = request.getSession();
       EmployeeDTO acc = (EmployeeDTO)session.getAttribute("ACCOUNT");
       TotalTimeWorkDAO dao = new TotalTimeWorkDAO();
       ArrayList<TotalTimeWorkDTO> list = dao.getTotalTimeWork(acc.getEmployeeID(), start, end);
       String listTotalCT = "";
       String listDateCT = "";
       float totalChinhThuc = 0;
       int i =0;
       for(TotalTimeWorkDTO listt: list){
           totalChinhThuc += listt.getTotalTime()/60;
           listTotalCT += (listt.getTotalTime()/60)+",";
           listDateCT += listt.getDate() + ",";
           i++;
       }
       String totalCT = "" + totalChinhThuc;
       ArrayList<TotalTimeWorkDTO> listOver = dao.getTotalOvertime(acc.getEmployeeID(), start, end);
       String listTotalOver = "";
       String listDateOver = "";
       float totalOverTime = 0;
       int j =0;
       for(TotalTimeWorkDTO listt: listOver){
           totalOverTime += listt.getTotalTime()/60;
           listTotalOver += (listt.getTotalTime()/60)+",";
           listDateOver += listt.getDate() + ",";
           i++;
       }
       String totalOver = "" + totalOverTime;
       float total = totalChinhThuc + totalOverTime;
       String Total = "" + total;
       request.setAttribute("TOTAL", Total);
       request.setAttribute("totalChinhThuc", totalCT);
       request.setAttribute("listTotal", listTotalCT);
       request.setAttribute("listDate", listDateCT);
       request.setAttribute("listTotalOver", listTotalOver);
       request.setAttribute("listDateOver", listDateOver);
       request.setAttribute("totalOver", totalOver);
       request.setAttribute("START", start);
       request.setAttribute("END", end);
       request.getRequestDispatcher("HomePage.jsp").forward(request, response);
       
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
