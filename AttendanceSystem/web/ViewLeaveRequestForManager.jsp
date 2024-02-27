<%-- 
    Document   : ViewLeaveRequestForManager
    Created on : Feb 23, 2024, 9:57:59 PM
    Author     : nguye
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.time.*" %>
<%@page import="model.*" %>
<%@page import="model.request.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            .right{
                /*                position: absolute;
                                width: 83%;
                                right: 0px;*/
                padding: 30px;

            }
            .tdbreak {
                word-break: break-word;
                max-width: 150px;
            }
            .content-redirect{
                background-color: #F5F5F5;
                border-radius: 5px;
                padding: 4px;
            }
            .content-redirect p{
                margin: 0px;
                font-size: large;
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
        EmployeeDTO acc = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");
    %>
    <body>
        <div>
            <%--<%@include file="Sidebar.jsp" %>--%>
            <div class="right">
                <div class="content-redirect">
                    <p><a href="ThanhCong.html">Home</a> | <a href="javascript:history.back()">Trở Lại</a> | Process Request For Manager</p>
                </div>
                <div class="text-center">
                    <h1 style="margin: 30px">Danh sách đơn (Manager)</h1>
                    <!--                    <a href="javascript:history.back()" class="btn btn-outline-secondary" style="position: absolute; left: 15px; top: 15px;">
                                            <i class="bi bi-arrow-left"></i> Trở lại
                                        </a>-->
                </div>
                <div>
                    <form action="DispatchController" method="POST">
                        <%
                            if(acc.getRoleID() == 4){ // role quản lí(manager)
                        %>
                        <table class="table">
                            <tr style="background-color: #CFE2FF">
                                <th class="text-center">Mã đơn</th>
                                <th class="text-center">Mã nhân viên</th>
                                <th class="text-center">Ngày gửi</th>
                                <th class="text-center">Ngày bắt đầu</th>
                                <th class="text-center">Ngày kết thúc</th>
                                <th class="text-center">Lí do</th>
                                <th class="text-center">Trạng thái <br> (Manager)</th>  
                                <th class="text-center">Người phê duyệt <br> (Manager)</th>
                                <th class="text-center">Check</th>
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
                                <td class="text-center tdbreak">
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
                                <td class="text-center">
                                    <%
                                        if(lr.getManagerApprove() != null){
                                        }else{
                                    %>
                                    <button onclick="xacNhan('Accept', '<%= lr.getLeaveRequestID() %>', event)" class="border bg-success" type="submit" name="btAction" value="Accept<%= lr.getLeaveRequestID() %>">
                                        <i class="fa-solid fa-check" style="color: #FFFFFF"></i>
                                    </button>
                                    <button onclick="xacNhan('Deny', '<%= lr.getLeaveRequestID() %>', event)" class="border bg-danger" type="submit" name="btAction" value="Deny<%= lr.getLeaveRequestID() %>">
                                        <i class="fa-solid fa-x" style="color: #FFFFFF"></i>
                                    </button>
                                </td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </table>
                        <%
                        }else{
                        %>
                        <p class="text-center">Tài khoản này không phải Quản lí</p>
                        <%
                        }
                        %>
                    </form>
                </div>
            </div>

        </div>
        <script>
            function xacNhan(action, leaveRequestID, event) {
                var xacNhan = confirm("Bạn có chắc chắn muốn thực hiện hành động này không?");
                if (xacNhan) {
                    alert("Hành động đã được xác nhận!");
                    // Thực hiện hành động khi xác nhận
                    if (action === 'Accept') {
                        window.location.href = "AcceptLeaveRequestServlet?leaveRequestID=" + leaveRequestID + "&btAction=Accept";
                    } else if (action === 'Deny') {
                        window.location.href = "DenyLeaveRequestServlet?leaveRequestID=" + leaveRequestID + "&btAction=Deny";
                    }
                } else {
                    alert("Hành động đã bị hủy bỏ!");
                    //Thực hiện hành động khi hủy bỏ
                }
                event.preventDefault();
            }
        </script>
        <script src="https://kit.fontawesome.com/c2b5cd9aa7.js" crossorigin="anonymous"></script>
        <script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
