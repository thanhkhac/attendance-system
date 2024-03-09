/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.employee;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ultility.exportFile.ExportEmployeeFormattedFile;
import ultility.exportFile.ExportFileModule;

/**
 *
 * @author admin
 */
@WebServlet(name = "SendEmployeeFormattedFileServlet", urlPatterns = {"/SendEmployeeFormattedFileServlet"})
public class SendEmployeeFormattedFileServlet extends HttpServlet {

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
        ServletContext context = getServletContext();
        String relativePath = "";
        String absolutePath = context.getRealPath(relativePath);
        String excelFilePath = absolutePath + "EmployeeFormatted.xlsx";
        System.out.println(excelFilePath);
        return excelFilePath;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        final String excelFilePath = getFilePath();

        try {
            ExportEmployeeFormattedFile.writeExcel(excelFilePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error - Write LocalFile");
            e.printStackTrace();
        }

        // Adjust the minimum inflate ratio before reading the Excel file
        ZipSecureFile.setMinInflateRatio(0.0005);// Lowering the minimum inflate ratio

        try ( FileInputStream fis = new FileInputStream(new File(excelFilePath)); //generate file input
                  Workbook workbook = new XSSFWorkbook(fis)) { //generate file to send through response
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=Employees.xlsx");
            // Write the workbook content to the response output stream
            workbook.write(response.getOutputStream());

            workbook.close();  //try - auto close resource when end try block
            fis.close();
        } catch (Exception e) {
            System.out.println("Error Generate File !");
            e.printStackTrace();
        } finally {
            // Reset the minimum inflate ratio to the default value after processing the file
            ZipSecureFile.setMinInflateRatio(0.01);   // Reset to the default value
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
