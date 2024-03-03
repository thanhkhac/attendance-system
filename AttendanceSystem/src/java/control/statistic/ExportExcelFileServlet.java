/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.statistic;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import model.EmployeeDTO;
import model.StatisticsDAO;
import model.StatisticsDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ultility.datetimeutil.DateTimeUtil;
import ultility.exportFile.ExportFileModule;

/**
 *
 * @author admin
 */
@WebServlet(name = "ExportExcelFileServlet", urlPatterns = {"/ExportExcelFileServlet"})
public class ExportExcelFileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String getFilePath() {
//        String projectPath = System.getProperty("user.dir"); // Current working directory
//        String relativePath = "\\build\\web"; // Adjust the relative path based on your project structure
//        String excelFilePath = projectPath + relativePath + "\\Statistics.xlsx";
        ServletContext context = getServletContext();
        String relativePath = "";
        String absolutePath = context.getRealPath(relativePath);
        String excelFilePath = absolutePath + "\\Statistics.xlsx";
        System.out.println(excelFilePath);
        return excelFilePath;
    }

    public static void main(String[] args) {
        
    }

    private final DateTimeUtil DATE_UTIL = new DateTimeUtil();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        StatisticsDAO staDAO = new StatisticsDAO();
        HttpSession session = request.getSession();
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("ACCOUNT");
        ArrayList<StatisticsDTO> statistics = new ArrayList<>();
        String startDate_txt = request.getParameter("startDate");
        String endDate_txt = request.getParameter("endDate");
        LocalDate startDate = DATE_UTIL.parseSqlDate(employee.getStartDate());
        LocalDate endDate = DATE_UTIL.getVNLocalDateNow();
        final String excelFilePath = getFilePath(); //get LocalFile path to store temp excel file
        Workbook localWorkbook = new XSSFWorkbook();
        try {
            if (startDate_txt.length() > 0) {
                startDate = LocalDate.parse(startDate_txt);
            }
            if (endDate_txt.length() > 0) {
                endDate = LocalDate.parse(endDate_txt);
            }
            statistics = staDAO.getStatistics(employee.getEmployeeID(), startDate, endDate);
            ExportFileModule.writeExcel(statistics, excelFilePath); //write to LocalFile
        } catch (Exception e) {
            System.out.println("Error - Write LocalFile");
        }
        //read from localFile and write to response 
        try ( FileInputStream fis = new FileInputStream(new File(excelFilePath)); //generate file input
                  Workbook workbook = new XSSFWorkbook(fis)) { //generate file to send through response
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=Statistics.xlsx");

            // Write the workbook content to the response output stream
            workbook.write(response.getOutputStream());

            workbook.close();
            fis.close();
        } catch (Exception e) {
            System.out.println("Error Generate File !");
            e.printStackTrace();
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
