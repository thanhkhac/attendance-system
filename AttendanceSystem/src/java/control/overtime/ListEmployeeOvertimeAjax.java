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
import model.DepartmentDAO;
import model.DepartmentDTO;
import model.EmployeeDAO;
import model.EmployeeDTO;
import model.EmployeeTypeDAO;
import model.OvertimeDAO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ListEmployeeOvertimeAjax", urlPatterns = {"/listEmployeeOvertimeAjax"})
public class ListEmployeeOvertimeAjax extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String DeleteStart = request.getParameter("deleteStart");
        String DeleteEnd = request.getParameter("deleteEnd");
        String DeleteDate = request.getParameter("deleteDate");
        String QUA = request.getParameter("QUA");       
        String DeleteID = request.getParameter("deleteID");
        String DeleteAll = request.getParameter("deleteAll");
        if (DeleteStart != null && DeleteEnd != null && DeleteDate != null && DeleteID != null) {
            int id = Integer.parseInt(DeleteID);
            boolean check = new OvertimeDAO().deleteOvertime(DeleteDate, DeleteStart, DeleteEnd, id);

        }
        String txtName = request.getParameter("txt");
        String Page = request.getParameter("Page");
        int page = 1;
        if (Page != null && Page.length() > 0) {
            page = Integer.parseInt(Page);
        }
        String Date = request.getParameter("date");
        String startTime = request.getParameter("StartEnd");
        String endTime = request.getParameter("EndTime");
        String Phong = request.getParameter("Phong");
        if (Phong == null) {
            Phong = "";
        }
        EmployeeDAO dao = new EmployeeDAO();
        String lastName = "";
        String firstName = "";
        String Middname = "";
        String[] tachName = txtName.split(" ");

        lastName = tachName[0];
        if (tachName.length == 1) {
            lastName = tachName[tachName.length - 1];
        } else if (tachName.length == 2) {
            lastName = tachName[0];
            Middname = tachName[1];
        } else {
            lastName = tachName[0];
            firstName = tachName[tachName.length - 1];
            for (int i = 1; i < tachName.length - 1; i++) {
                Middname += tachName[i] + " ";
                Middname = Middname.trim();
            }
        }
        
        if (DeleteAll != null) {
            boolean checkDelete = false;
            ArrayList<EmployeeDTO> listEmpp = new EmployeeDAO().getEmployeeInfoByOvertime(Date, startTime, endTime);
            for (EmployeeDTO emp : listEmpp) {
                checkDelete = new OvertimeDAO().deleteOvertime(Date, startTime, endTime, emp.getEmployeeID());
            }
        }
        //out.print(startTime+"-"+endTime+" "+Phong+lastName+Middname+firstName +Date+"-"+Phong);
        out.print("<table class=\"table project-list-table table-nowrap align-middle table-borderless\">\n"
                + "                                                            <thead>\n"
                + "                                                                <tr>\n"
                + "\n"
                + "                                                                    <th scope=\"col\">Name</th>\n"
                + "                                                                    <th scope=\"col\">Position</th>\n"
                + "                                                                    <th scope=\"col\">Email</th>\n"
                + "                                                                    <th scope=\"col\">Employee ID</th>\n"
                + "                                                                    <th scope=\"col\" style=\"width: 200px;\">Action</th>\n"
                + "                                                                </tr>\n"
                + "                                                            </thead>\n"
                + "                                                            <tbody >");

        ArrayList<EmployeeDTO> listemp = dao.getListAddEmployeeOvertimeEachShiftAjax(page, Date, Phong, firstName, lastName, Middname, startTime, endTime);
        int countPage = dao.countEmployeeOvertimeByShift(Date, Phong, firstName, lastName, Middname, startTime, endTime);
        int endPage = 1;

        if (countPage % 4 == 0) {
            endPage = countPage / 4;
        } else {
            endPage = countPage / 4 + 1;
        }
        DepartmentDTO demp = null;
        for (EmployeeDTO list : listemp) {
            demp = new DepartmentDAO().getDepartmentById(list.getDepartmentID());                      
            out.print("<tr>\n"
                    + "                                                                    <td class=\"name-column\" ><img src=\"https://bootdey.com/img/Content/avatar/avatar1.png\" alt=\"\" class=\"avatar-sm rounded-circle me-2\" /><a href=\"#\" class=\"text-body\">" + list.getLastName() + " " + list.getMiddleName() + " " + list.getFirstName() + "</a></td>\n"
                    + "                                                                    <td><span class=\"badge badge-soft-success mb-0\">" + demp.getName() + "</span></td>\n"
                    + "                                                                    <td>" + list.getEmail() + "</td>\n"
                    + "                                                                    <td>" + list.getEmployeeId() + "</td>\n");
            if(QUA.equals("CHUAQUA")){
          
                
                    out.print("                                                                    <td>\n"
                    + "                                                                        "
                    + "<ul class=\"list-inline mb-0\">\n"
                    + "  <li class=\"list-inline-item\">                                                                          \n"
                    + " <a style=\"color:red\" href=\"javascript:void(0);\" value=\""+list.getEmployeeId()+"\" class=\"deleteEmp\"  title=\"Delete\" class=\"px-2 text-danger\"><i class=\"bx bx-trash-alt font-size-18\"></i></a>\n"
                    + "                   </li>                                                        \n"
                    + "                                                                            \n"
                    + "\n"
                    + "                                                                        </ul>\n"
                    + "                                                                    </td>\n");
            }
            else {
                out.print("<td></td>");
            }
            
            out.print("<input type=\"hidden\" class=\"deleteStart\" value=\""+startTime+"\">\n"
                    + "                                                            <input type=\"hidden\" class=\"deleteEnd\" value=\""+endTime+"\">\n"
                    + "                                                            <input type=\"hidden\" class=\"deleteDate\" value=\""+Date+"\">\n"
                    + "                                                            <input type=\"hidden\" class=\"deleteID\" value=\""+list.getEmployeeId()+"\">");
            out.print(" </tr>");
        }
        out.print("</tbody>\n"
                + "                                                        </table>");
        if(QUA.equals("CHUAQUA")){
        out.print("<a href=\"#\" style=\"position: absolute;left:22px;    background-color: #d61a1a;\" class=\"btn btn-primary deleteAll\">Xóa ca</a>");}
        out.print("<ul class=\"pagination\" style=\"\n"
                + "                justify-content: end;\n"
                + "                \">");
        if (page <= 1) {
            out.print("<li class=\"page-item click_page\"><a style=\"color: black;\" class=\"page-link\" href=\"#\">Trước</a></li>");
        } else {
            out.print("<li class=\"page-item click_page\"><a style=\"color: black;\" data-index=\"" + (page - 1) + "\"  class=\"page-link\" href=\"#\">Trước</a></li>");
        }
        for (int i = 1; i <= endPage; i++) {
            if (i == page) {
                out.print("<li class=\"page-item click_page\"><a  style=\"background-color: #cfd5da96;color: black;\" class=\"page-link page\" data-index=\"" + i + "\"  href=\"#\">" + i + "</a><li>");

            } else {
                out.print("<li class=\"page-item click_page\"><a style=\"color: black;\" class=\"page-link page\" data-index=\"" + i + "\"  href=\"#\">" + i + "</a><li>");
            }
        }
        if (page < endPage && endPage > 1) {
            out.print("<li class=\"page-item click_page\"><a style=\"color: black;\" data-index=\"" + (page + 1) + "\"  class=\"page-link\" href=\"#\">Sau</a></li>");
        } else {
            out.print("<li class=\"page-item click_page\"><a style=\"color: black;\" data-index=\"" + (page) + "\" class=\"page-link\" href=\"#\">Sau</a></li>");
        }
        out.print(" </ul>\n"
                + "                                                    </div>");
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
