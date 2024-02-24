package control.schedulework;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.OvertimeDAO;
import model.OvertimeDTO;

@WebServlet(name = "GetConflicts", urlPatterns = {"/GetConflicts"})
public class GetConflicts extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] raw_shifts = request.getParameterValues("shift");
        String[] raw_employeeIDs = request.getParameterValues("chkEmployeeID");

        OvertimeDAO overtimeDAO = new OvertimeDAO();
        ArrayList<OvertimeDTO> conflicts = new ArrayList<>();
        for (int i = 0; i < raw_shifts.length; i++) {
            int shiftID = Integer.parseInt(raw_shifts[i].split("#")[1]);
            String date = raw_shifts[i].split("#")[0];
            for (int j = 0; j < raw_employeeIDs.length; j++) {
                int employeeID = Integer.parseInt(raw_employeeIDs[j]);
                OvertimeDTO ot = overtimeDAO.getConflicts(employeeID, date, shiftID);
                if (ot != null) {
                    conflicts.add(ot);
                }
            }
        }
        
        request.setAttribute("conflicts", conflicts);
        request.getRequestDispatcher("ScheduleWorkConflicts.jsp").forward(request, response);
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
