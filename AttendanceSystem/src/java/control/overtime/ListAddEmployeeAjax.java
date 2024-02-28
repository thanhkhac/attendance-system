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

/**
 *
 * @author Admin
 */
@WebServlet(name = "ListAddEmployeeAjax", urlPatterns = {"/listAddEmployeeAjax"})
public class ListAddEmployeeAjax extends HttpServlet {

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
        String txtSearch = request.getParameter("txt");
        String listEmpp = request.getParameter("listEmpp");
        String checkAll = request.getParameter("Check");
        
        String[] listID = new String[0];
        int listIDInt[] = new int[0];
        
        if (listEmpp != null && listEmpp.length() > 0) {
            listID = listEmpp.split("\\|");
            listIDInt = new int[listID.length];
            int count = 0;
            for (int i = 0; i < listID.length; i++) {
                if (listID[i].length() > 0) {
                    listIDInt[count] = Integer.parseInt(listID[i]);
                    count++;
                }
            }
        }
        //String DanhsachNhanVien =request.getParameter("cc");
        //out.print(DanhsachNhanVien);
        String lastName = "";
        String firstName = "";
        String Middname = "";
        String[] tachName = txtSearch.split(" ");

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

        String phongBan = request.getParameter("phong");
        String empID = request.getParameter("EmpID");
        String Date = request.getParameter("date");
        String page = request.getParameter("Page");
        String startx = request.getParameter("Startx");
        String endx = request.getParameter("Endx");
        int Page = 1;
        if (page != null && page.length() > 0) {
            Page = Integer.parseInt(page);
        }
        EmployeeDAO dao = new EmployeeDAO();
        ArrayList<EmployeeDTO> list = dao.getListAddEmployeeOvertimeAjax(startx,endx,Page, Date, phongBan, empID, firstName, lastName, Middname);
        if (checkAll != null && checkAll.length() > 0) {
                    ArrayList<EmployeeDTO> listAll = dao.getAllAddEmployeeOvertime(startx,endx, Date, phongBan, empID, firstName, lastName, Middname);

            listEmpp = "";
            for (EmployeeDTO listemp : listAll) {
                listEmpp += "" + listemp.getEmployeeID() + "|";
            }
        }
        //out.print(firstName+" "+lastName+ " "+Middname);
        out.print("<form action=\"addListEmloyeeOvertime\" id=\"MyForm\">\n"
                + "                            <input type=\"hidden\" id=\"dateHidden\" name=\"Date\" value=\"" + Date + "\">\n"
                + "                            <input type=\"hidden\" id=\"startHidden\" name=\"start\" value=\"" + startx + "\">\n"
                + "                            <input type=\"hidden\" id=\"endHidden\" name=\"end\" value=\"" + endx + "\">\n"
                + "                            <input type=\"hidden\" id=\"listEmployeeAdd\" value=\"" + listEmpp + "\" name=\"listEmployeeAdd\">\n"
                + "                       </form>");
        
        out.print(""
                + "<table id=\"mytable\" class=\"table table-bordred table-striped\">\n"
                + "\n"
                + "                            <thead>\n"
                + "\n");
        if (checkAll != null && checkAll.length() > 0) {
            out.print("                            <th><input checked type=\"checkbox\" id=\"checkall\" /></th>\n");
        } else {
            out.print("                            <th><input  type=\"checkbox\" id=\"checkall\" /></th>\n");
        }
        out.print(
                "                            <th>EmployeeID</th>\n"
                + "                            <th>Full name</th>   \n"
                + "                            <th>CCCD</th>                   \n"
                + "                            <th>Email</th>\n"
                + "                            <th>Employee Type</th>\n"
                + "                            <th>Department</th>\n"
                + "\n"
                + "                            </thead>\n"
                + "                            <tbody >");

        DepartmentDTO departEmp = new DepartmentDTO();
        int countCheckBox = 0;
        String TypeName ="";
        
        for (EmployeeDTO listemp : list) {
            countCheckBox = 0;
            departEmp = new DepartmentDAO().getDepartmentById(listemp.getDepartmentID());
            TypeName = new EmployeeTypeDAO().getEmployeeTypeIDByID(listemp.getEmployeeTypeID());
            if (checkAll != null && checkAll.length() > 0) {
                out.print("<tr>\n"
                        + "    <td><input type=\"checkbox\" class=\"checkthis\" checked /></td>\n");
            } else {
                for (int i = 0; i < listIDInt.length; i++) {
                    if (listemp.getEmployeeID() == listIDInt[i]) {
                        out.print("<tr>\n"
                                + "    <td><input type=\"checkbox\" class=\"checkthis\" checked /></td>\n");
                        countCheckBox = 1;
                    }
                }
                if (countCheckBox == 0) {
                    out.print("<tr>\n"
                            + "    <td><input type=\"checkbox\" class=\"checkthis\" /></td>\n");
                }
            }
            out.print("    <td>" + listemp.getEmployeeID() + "</td>\n"
                    + "    <td>" + listemp.getLastName() + " " + listemp.getMiddleName() + " " + listemp.getFirstName() + "</td>\n"
                    + "    <td>" + listemp.getCccd() + "</td>\n"
                    + "    <td>" + listemp.getEmail() + "</td>\n"
                    + "    <td>" + TypeName + "</td>\n"
                    + "    <td>" + departEmp.getName() + "</td>\n"
                    + "</tr>");

        }
        out.print("</tbody>\n"
                + "\n"
                + "                        </table>\n"
                + "                    ");
        int endPage = 0;
        int countPage = dao.countEmployeeOvertime(Date, phongBan, empID, firstName, lastName, Middname);
        if (countPage % 10 == 0) {
            endPage = countPage / 10;
        } else {
            endPage = countPage / 10 + 1;
        }
        if (Page == 1) {
            out.print("<ul class=\"pagination\" style=\"\n"
                    + "                justify-content: end;\n"
                    + "                \">\n"
                    + "                <li class=\"page-item\"><a  class=\"page-link\" href=\"#\">Trước</a></li>");

        } else {
            out.print("<ul class=\"pagination\" style=\"\n"
                    + "                justify-content: end;\n"
                    + "                \">\n"
                    + "                <li class=\"page-item\"><a data-index=\"" + (Page - 1) + "\" onclick=\"searchByName(this)\" class=\"page-link\" href=\"#\">Trước</a></li>");
        }

        for (int i = 1; i <= endPage; i++) {
            if (i == Page) {
                out.print("<li class=\"page-item\"\"><a style=\"background-color: #cfd5da96;\" class=\"page-link page pageNow\" data-index=\"" + i + "\" onclick=\"searchByName(this)\" href=\"#\">" + i + "</a><li>");
            } else {
                out.print("<li class=\"page-item\"><a  class=\"page-link page\" data-index=\"" + i + "\" onclick=\"searchByName(this)\" href=\"#\">" + i + "</a><li>");
            }
        }
        if (Page == endPage) {
            out.print("<li class=\"page-item\"><a  class=\"page-link\" href=\"#\">Sau</a></li>");

        } else {
            out.print("<li class=\"page-item\"><a data-index=\"" + (Page + 1) + "\" onclick=\"searchByName(this)\" class=\"page-link\" href=\"#\">Sau</a></li>");
        }
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
