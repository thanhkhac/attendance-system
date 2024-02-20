<%-- 
    Document   : ProcessLeaveRequestForManager
    Created on : Feb 19, 2024, 8:14:44 AM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.time.*" %>
<%@page import="model.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            .right{
                position: absolute;
                width: 83%;
                right: 0px;
            }
            .tdbreak {
                word-break: break-word;
                max-width: 150px;
            }
        </style>

    </head>
    <%
        LeaveRequestDAO lrDao = new LeaveRequestDAO();
        EmployeeDAO dao = new EmployeeDAO();
        EmployeeDTO emDTO = new EmployeeDTO();
        EmployeeDTO managerDTO = new EmployeeDTO();
        EmployeeDTO hrDTO = new EmployeeDTO();
        ArrayList<LeaveRequestDTO> list = lrDao.getLeaveRequest();
    %>
    <body>
        <div>
            <%@include file="Sidebar.jsp" %>
            <div class="right">
                <div class="text-center">
                    <h1 style="margin: 30px">Danh sách đơn</h1>
                    <a href="javascript:history.back()" class="btn btn-outline-secondary" style="position: absolute; left: 15px; top: 15px;">
                        <i class="bi bi-arrow-left"></i> Trở lại
                    </a>
                </div>
                <div>
                    <form action="GetConflicts" method="POST">
                        <table class="table">
                            <tr style="background-color: #CFE2FF">
                                <th class="text-center">Mã đơn</th>
                                <th class="text-center">Mã nhân viên</th>
                                <th class="text-center">Ngày gửi</th>
                                <th class="text-center">Ngày bắt đầu</th>
                                <th class="text-center">Ngày kết thúc</th>
                                <th class="text-center">Lí do</th>
                                <th class="text-center">Trạng thái <br> (Manager)</th>  
                                <th class="text-center">Trạng thái <br> (HR)</th>
                                <th class="text-center">Người phê duyệt <br> (Manager)</th>
                                <th class="text-center">Người phê duyệt <br> (HR)</th>
                            </tr>
                            <%
                                for (LeaveRequestDTO lr : list) {
                                emDTO = dao.getEmployeeDTO(lr.getEmployeeID());
                                managerDTO = dao.getEmployeeDTO(lr.getManagerID());
                                hrDTO = dao.getEmployeeDTO(lr.getHrID());
                            %>
                            <tr class="employee-row">
                                <td class="text-center"><%=lr.getLeaveRequestID()%></td>
                                <td class="tdbreak"><%= emDTO.getLastName() + " " +  emDTO.getMiddleName() + " " + emDTO.getFirstName() %></td>
                                <td class="text-center"><%=lr.getSentDate()%></td>
                                <td class="text-center"><%=lr.getStartDate()%></td>     
                                <td class="text-center"><%=lr.getEndDate()%></td>
                                <td class="tdbreak"><%=lr.getReason()%></td>
                                <td class="text-center">
                                    <%
                                        Boolean managerApprove = lr.getManagerApprove();
                                        if(managerApprove == null){
                                    %>
                                    <p class="fw-bold">Pending</p>
                                    <%
                                        }else if (managerApprove == true){
                                    %>
                                    <p class="text-success fw-bold" >Accepted</p>
                                    <%
                                        }else if (managerApprove == false){
                                    %>
                                    <p class="text-danger fw-bold">Denied</p>
                                    <%
                                        }
                                    %>
                                </td>
                                <td class="text-center font-weight-bold">
                                    <%
                                        Boolean hrApprove = lr.getHrApprove();
                                        if(hrApprove == null){
                                    %>
                                    <p class="fw-bold">Pending</p>
                                    <%
                                        }else if (hrApprove == true){
                                    %>
                                    <p class="text-success fw-bold" >Accepted</p>
                                    <%
                                        }else if (hrApprove == false){
                                    %>
                                    <p class="text-danger fw-bold">Denied</p>
                                    <%
                                        }
                                    %>
                                </td>
                                <td class="tdbreak">
                                    <%
                                        if(managerDTO != null){
                                    %>
                                    <%= managerDTO.getLastName() + " " + managerDTO.getMiddleName() + " " + managerDTO.getFirstName() %>
                                    <%
                                    }else{
                                    %>
                                    <p class="text-center fw-bold">Pending</p>
                                    <%
                                    }
                                        
                                    %>
                                </td>
                                <td class="tdbreak">
                                    <%
                                        if(hrDTO != null){
                                    %>
                                    <%= hrDTO.getLastName() + " " + hrDTO.getMiddleName() + " " + hrDTO.getFirstName() %>
                                    <%
                                    }else{
                                    %>
                                    <p class="text-center fw-bold">Pending</p>
                                    <%
                                    }
                                        
                                    %>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                    </form>
                </div>
            </div>

        </div>
        <script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>
    </body>
</html>