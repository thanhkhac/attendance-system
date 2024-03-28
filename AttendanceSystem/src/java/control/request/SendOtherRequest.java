package control.request;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.EmployeeDTO;
import model.request.RequestDAO;
import model.request.RequestDTO;
import ultility.filehandler.UploadFile;

@WebServlet(name = "SendOtherRequest", urlPatterns = {"/SendOtherRequest"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 100, // 10MB
        maxFileSize = 1024 * 1024 * 500, // 20MB
        maxRequestSize = 1024 * 1024 * 500) // 50MB

public class SendOtherRequest extends HttpServlet {

    public static final String SAVE_DIRECTORY = "RequestFiles";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String r_RequestType = request.getParameter("requestTypeID");
        int employeeID = ((EmployeeDTO) request.getSession().getAttribute("ACCOUNT")).getEmployeeId();
                
        String appPath = request.getServletContext().getRealPath("");
        String fullSavePath = appPath + File.separator + SAVE_DIRECTORY;
        String backUpPath = fullSavePath.replace(File.separator + "build", "");
        ArrayList<String> savePaths = new ArrayList<>();
        savePaths.add(backUpPath);
        savePaths.add(fullSavePath);

        //Đây sẽ là đường dẫn cần lấy và lưu vào database
        String filepath = new UploadFile().saveFile(request, "file", savePaths, SAVE_DIRECTORY);
        
        RequestDAO requestDAO = new RequestDAO();
        
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setContent(content);
        requestDTO.setEmployeeID(employeeID);
        requestDTO.setTitle(title);
        requestDTO.setTypeID(Integer.parseInt(r_RequestType));
        requestDTO.setFilePath(filepath);
        
        requestDAO.insert(requestDTO);
        
        response.sendRedirect("Success.jsp");
        
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
// </editor-fold>

}
