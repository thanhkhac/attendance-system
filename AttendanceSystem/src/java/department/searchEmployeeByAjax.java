/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package department;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.EmployeeDAO;
import model.EmployeeDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name="searchEmployeeByAjax", urlPatterns={"/searchEmployeeByAjax"})
public class searchEmployeeByAjax extends HttpServlet {
   
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
        EmployeeDAO dao = new EmployeeDAO();
        String txtSearch = request.getParameter("txt");
        String phongBan = request.getParameter("phong");
        String tenPhong = null;         
        int phong = Integer.parseInt(phongBan);
         if(phong==1)
             tenPhong = "Phòng nhân sự";
         else
             tenPhong = "Phòng tiếp thị";
        ArrayList<EmployeeDTO> list = dao.SearchEmployeeByName(txtSearch);       
        for(EmployeeDTO employee: list){
           if(employee.getDepartmentID()==phong){
               String fullName = employee.getLastName()+" "+employee.getMiddleName()+" "+employee.getFirstName();
               out.print("<tr>\n" +
"                                <input id=\"phongBan\" type=\"hidden\" value=\""+employee.getDepartmentID()+"\" name=\"name\">\n" +
"                                        <td><img src=\"https://bootdey.com/img/Content/avatar/avatar1.png\" alt=\"\"\n" +
"                                                 class=\"avatar-sm rounded-circle me-2\" /><a href=\"#\" class=\"text-body show-div-link\"> \n" +
"                                                "+fullName+"</a>\n" +
"                                            <!--div class \"overlay\"-> box an khii an vao ten trong list se hien thong tin -->                       \n" +
"                                            <div class=\"overlay\" style=\"z-index:2000\">\n" +
"                                                <div class=\"centered-div\" >\n" +
"                                                    <button class=\"close-button\"><img  style=\"width: 10px;\" src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSkBKbhGamdqx3Bab4sQqUwkljoYvQ-WasvA&usqp=CAU\" alt=\"\"></button>\n" +
"                                                    <div class=\"row\">\n" +
"                                                        <div class=\"col-md-4 \">\n" +
"\n" +
"                                                            <div class=\"text-center card-block\">\n" +
"                                                                <div class=\"mb-4 mt-4\" >\n" +
"                                                                    <img src=\"https://bootdey.com/img/Content/avatar/avatar1.png\" class=\"img-radius rounded-circle\" alt=\"User-Profile-Image\">\n" +
"                                                                </div>\n" +
"                                                                <div class=\"mt-3 text-wrap\">\n" +
"                                                                    "+fullName+"\n" +
"                                                                </div>\n");
               out.print("<p>"+tenPhong+"</p>\n" +
"                                                                ID\n" +
"                                                                <p>"+employee.getCccd()+"</p>\n" +
"                                                            </div>\n" +
"\n" +
"                                                        </div>\n" +
"\n" +
"                                                        <div class=\"col-md-8 card-info mt-3\" style=\"padding-left: 20px;\">\n" +
"                                                            <p style=\"width: 400px;\"></p>\n" +
"                                                            <p class=\"info\">Information</p>\n" +
"                                                            <div class=\"row mb-3\">\n" +
"                                                                <div class=\"col-sm-6 text-wrap\">\n" +
"                                                                    <p  class=\"m-b-10 f-w-600 \">Email</p>\n" +
"                                                                    <div class=\"text-muted text-wrap\">"+employee.getEmail()+"</div> \n" +
"                                                                </div>\n" +
"                                                                <div class=\"col-sm-6 text-wrap\">\n" +
"                                                                    <p  class=\"m-b-10 f-w-600\">Phone</p>\n" +
"                                                                    <h6  class=\"text-muted text-wrap\">"+employee.getPhoneNumber()+"</h6>\n" +
"                                                                </div>\n" +
"                                                            </div>\n" +
"\n" +
"                                                            <div class=\"row mb-3\">\n" +
"                                                                <div class=\"col-sm-6 text-wrap\">\n" +
"                                                                    <p  class=\"m-b-10 f-w-600 \">Birth</p>\n" +
"                                                                    <div class=\"text-muted text-wrap\">"+employee.getStartDate()+"</div> \n" +
"                                                                </div>\n" +
"                                                                <div class=\"col-sm-6 text-wrap\">\n" +
"                                                                    <p  class=\"m-b-10 f-w-600\">Gender</p>\n" +
"                                                                    <h6  class=\"text-muted text-wrap\">"+employee.getGender()+"</h6>\n" +
"                                                                </div>\n" +
"                                                            </div>\n" +
"\n" +
"                                                            <div class=\"row mb-3\">\n" +
"                                                                <div class=\"col-sm-6 text-wrap\">\n" +
"                                                                    <p  class=\"m-b-10 f-w-600 \">Start date</p>\n" +
"                                                                    <div class=\"text-muted text-wrap\">"+employee.getStartDate()+"</div> \n" +
"                                                                </div>\n" +
"                                                                <div class=\"col-sm-6 text-wrap\">\n" +
"                                                                    <p  class=\"m-b-10 f-w-600\">End date</p>\n" +
"                                                                    <h6  class=\"text-muted text-wrap\">"+employee.getEndDate()+"</h6>\n" +
"                                                                </div>\n" +
"                                                            </div>\n" +
"\n" +
"                                                        </div>\n" +
"\n" +
"                                                    </div>\n" +
"                                                </div>\n" +
"                                            </div>\n" +
"                                        </td>\n" +
"                                        <td><span class=\"badge badge-soft-success mb-0\">"+tenPhong+"</span></td>\n" +
"                                        <td>"+employee.getEmail()+"</td>\n" +
"                                        <td>"+employee.getPhoneNumber()+"</td>");
               
            if(employee.isIsActived())
                out.print("<td><span class=\"badge badge-soft-success mb-0\">Active</span></td>");
            else out.print("<td><span class=\"badge badge-soft-Notsuccess mb-0\">Inactive</span></td> \n </tr>");
            
           }
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
