package authorization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathConstants {

    public static final List<String> EMPLOYEE_PATH_LIST;

    static {
        ArrayList<String> employeePaths = new ArrayList<>();
        employeePaths.add("/ThanhCong.html");
        employeePaths.add("/Error.jsp");
        employeePaths.add("/HomePage.jsp");
        employeePaths.add("/NavigateSentRequest.jsp");
        employeePaths.add("/OvertimeSuccess.jsp");
        employeePaths.add("/SendRequest.jsp");
        employeePaths.add("/Sidebar.jsp");
        employeePaths.add("/Success.jsp");
        employeePaths.add("/TakeAttendance.jsp");
        employeePaths.add("/ViewCalendar.jsp");
        employeePaths.add("/ViewEmployeeProfile.jsp");
        employeePaths.add("/ViewNews.jsp");
        employeePaths.add("/ViewNewsDetail.jsp");
        employeePaths.add("/ViewSentLeaveRequest.jsp");
        employeePaths.add("/ViewSentOtherRequest.jsp");
        employeePaths.add("/ViewSentOvertimeRequest.jsp");
        employeePaths.add("/WorkSchedule.jsp");
        employeePaths.add("/DenyAccess.html");

        employeePaths.add("/TakeAttendance");
        employeePaths.add("/TakeAttendanceOvertime");
        employeePaths.add("/changepassforuser");
        employeePaths.add("/ChangePasswordServlet");
        employeePaths.add("/viewProfile");
        employeePaths.add("/updateProfileByEmployee");
        employeePaths.add("/getallnews");
        employeePaths.add("/SearchAllDetailNews");
        employeePaths.add("/InsertLeaveRequestServlet");
        employeePaths.add("/InsertOverTimeRequestServlet");
        employeePaths.add("/SendOtherRequest");
        employeePaths.add("/ViewSentOtherRequest");
        employeePaths.add("/logout");
        employeePaths.add("/dispatchcontroller");
        employeePaths.add("/UpdateEmployeeProfile.jsp");
        employeePaths.add("/PrepareRequestServlet");
        
        for (String employeePath : employeePaths) {
            employeePath = employeePath.toLowerCase();
        }
        EMPLOYEE_PATH_LIST = Collections.unmodifiableList(employeePaths);
        
        
    }


}
