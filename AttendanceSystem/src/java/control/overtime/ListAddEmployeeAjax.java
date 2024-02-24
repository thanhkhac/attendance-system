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
import java.util.ArrayList;
import model.EmployeeDAO;
import model.EmployeeDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name="ListAddEmployeeAjax", urlPatterns={"/listAddEmployeeAjax"})
public class ListAddEmployeeAjax extends HttpServlet {
   
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
       String txtSearch =request.getParameter("txt");
       //String DanhsachNhanVien =request.getParameter("cc");
       //out.print(DanhsachNhanVien);
       String lastName = "";
       String firstName=""; 
       String Middname = "";
       String [] tachName = txtSearch.split(" ");
       
        lastName = tachName[0];
        
        firstName=""; 
        if(tachName.length>1){
        firstName= tachName[tachName.length-1];   
        }
        Middname = "";
        for(int i=1; i<tachName.length-1;i++){
            Middname +=  tachName[i]+ " ";
        }
        
        Middname = Middname.trim();
       
            
       String phongBan  =request.getParameter("phong");
       String empID =request.getParameter("EmpID");
       String Date =request.getParameter("date");
       String page =request.getParameter("Page");
     
       int Page = 1;
       if(page!=null&&page.length()>0)
       Page = Integer.parseInt(page);
       EmployeeDAO dao =new EmployeeDAO();
       //out.print(firstName+" "+lastName+ " "+Middname);
       out.print(""
               + "<table id=\"mytable\" class=\"table table-bordred table-striped\">\n" +
"\n" +
"                            <thead>\n" +
"\n" +
"                            <th><input type=\"checkbox\" id=\"checkall\" /></th>\n" +
"                            <th>EmployeeID</th>\n" +
"                            <th>Full name</th>   \n" +
"                            <th>CCCD</th>                   \n" +
"                            <th>Email</th>\n" +
"                            <th>Employee Type</th>\n" +
"                            <th>Department</th>\n" +
"\n" +
"                            </thead>\n" +
"                            <tbody >");
       ArrayList<EmployeeDTO> list = dao.getListAddEmployeeOvertimeAjax(Page,Date,phongBan,empID,firstName,lastName,Middname);
       for(EmployeeDTO listemp:list){
           
           out.print("<tr>\n" +
"    <td><input type=\"checkbox\" class=\"checkthis\" /></td>\n" +
"    <td>"+listemp.getEmployeeID()+"</td>\n" +
"    <td>"+listemp.getLastName()+" "+listemp.getMiddleName()+" "+listemp.getFirstName()+"</td>\n" +
"    <td>"+listemp.getCccd()+"</td>\n" +
"    <td>"+listemp.getEmail()+"</td>\n" +
"    <td>"+listemp.getEmployeeTypeID()+"</td>\n" +
"    <td>"+listemp.getDepartmentID()+"</td>\n" +
"</tr>");
           
       }
       out.print("</tbody>\n" +
"\n" +
"                        </table>\n" +
"                    ");  
       int endPage = 0;
            int countPage =dao.countEmployeeOvertime(Date, phongBan, empID, firstName, lastName, Middname);
        if (countPage % 5 == 0) {
            endPage = countPage / 5;
        } else {
            endPage = countPage / 5 + 1;
        }
        if(Page==1){
            out.print("<ul class=\"pagination\" style=\"\n" +
"                justify-content: end;\n" +
"                \">\n" +
"                <li class=\"page-item\"><a  class=\"page-link\" href=\"#\">Trước</a></li>");

        }else{
       out.print("<ul class=\"pagination\" style=\"\n" +
"                justify-content: end;\n" +
"                \">\n" +
"                <li class=\"page-item\"><a data-index=\""+(Page-1)+"\" onclick=\"searchByName(this)\" class=\"page-link\" href=\"#\">Trước</a></li>");
        }
            
            
        for(int i=1;i<=endPage;i++){
            if(i==Page){
                out.print("<li class=\"page-item\"><a style=\"background-color: #cfd5da96;\" class=\"page-link page\" data-index=\""+i+"\" onclick=\"searchByName(this)\" href=\"#\">"+i+"</a><li>");
            }else{
                out.print("<li class=\"page-item\"><a  class=\"page-link page\" data-index=\""+i+"\" onclick=\"searchByName(this)\" href=\"#\">"+i+"</a><li>");
            }
        }
        if(Page==endPage){
                    out.print("<li class=\"page-item\"><a  class=\"page-link\" href=\"#\">Sau</a></li>");

        }else{
        out.print("<li class=\"page-item\"><a data-index=\""+(Page+1)+"\" onclick=\"searchByName(this)\" class=\"page-link\" href=\"#\">Sau</a></li>");
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
