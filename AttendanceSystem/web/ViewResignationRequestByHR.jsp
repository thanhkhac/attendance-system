<%-- 
    Document   : ViewRegisnationRequest
    Created on : Feb 24, 2024, 10:52:51 PM
    Author     : nguye
--%>

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
//        LeaveRequestDAO lrDao = new LeaveRequestDAO();
        ResignationRequestDAO rrDao = new ResignationRequestDAO();
        EmployeeDAO dao = new EmployeeDAO();
        EmployeeDTO emDTO = new EmployeeDTO();
        EmployeeDTO managerDTO = new EmployeeDTO();
        EmployeeDTO hrDTO = new EmployeeDTO();
        
//        ArrayList<LeaveRequestDTO> list = lrDao.getLeaveRequest();
        ArrayList<ResignationRequestDTO> list = rrDao.getRegisnationRequest();
        EmployeeDTO acc = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");
    %>
    <body>
        <div>
            <%@include file="Sidebar.jsp" %>
            <div class="right">
                <div class="text-center">
                    <h1 style="margin: 30px">Danh sách đơn Regisnation (HR)</h1>
                    <a href="javascript:history.back()" class="btn btn-outline-secondary" style="position: absolute; left: 15px; top: 15px;">
                        <i class="bi bi-arrow-left"></i> Trở lại
                    </a>
                </div>
                <div>
                    <form action="DispatchController" method="POST">
                        <%
                            if(acc.getRoleID() == 2){   //role quản lí nhân sự 
                                
                        %>
                        <table class="table">
                            <tr style="background-color: #CFE2FF">
                                <th class="text-center">Mã đơn</th>
                                <th class="text-center">Mã nhân viên</th>
                                <th class="text-center">Ngày gửi</th>
                                <th class="text-center">Ngày bắt đầu làm việc</th>
                                <th class="text-center">Ngày kết thúc làm việc</th>
                                <th class="text-center">Ngày gia hạn</th>
                                <th class="text-center">Lí do</th>
                                <th class="text-center">Trạng thái <br> (Manager)</th>  
                                <th class="text-center">Trạng thái <br> (HR)</th>
                                <th class="text-center">Người phê duyệt <br> (Manager)</th>
                                <th class="text-center">Người phê duyệt <br> (HR)</th>
                                <th class="text-center">Status</th>
                                <th class="text-center">check</th>
                            </tr>
                            <%
                                for (ResignationRequestDTO rr : list) {
                                    emDTO = dao.getEmployeeDTO(rr.getEmployeeID());
                                    managerDTO = dao.getEmployeeDTO(rr.getManagerID());
                                    hrDTO = dao.getEmployeeDTO(rr.getHrID());
                                    if(rr.getManagerApprove() != null && rr.getManagerApprove()){
                            %>
                            <tr class="employee-row">
                                <td class="text-center"><%=rr.getResignationRequestID()%></td>
                                <td class="tdbreak"><%= emDTO.getLastName() + " " +  emDTO.getMiddleName() + " " + emDTO.getFirstName() %></td>
                                <td class="text-center"><%=rr.getSentDate()%></td>
                                <td class="text-center"><%=rr.getStartDateContract()%></td>     
                                <td class="text-center"><%=rr.getEndDateContract()%></td>
                                <td class="text-center"><%=rr.getExtendDate()%></td>
                                <td class="tdbreak"><%=rr.getReason()%></td>
                                <td class="text-center">
                                    <%
                                        Boolean managerApprove = rr.getManagerApprove();
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
                                        Boolean hrApprove = rr.getHrApprove();
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
                                <td class="text-center tdbreak">
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
                                <td class="text-center tdbreak">
                                    <%
                                        Boolean status = rr.getStatus();
                                        if(status == null){
                                    %>
                                        <p class="fw-bold">Pending</p>
                                    <%
                                        }else if (status == true){
                                    %>
                                        <p class="text-success fw-bold" >Accepted</p>
                                    <%
                                        } else if (status == false){
                                    %>
                                        <p class="text-danger fw-bold">Denied</p>
                                    <%
                                        }
                                    %>
                                </td>
                                <td class="text-center">
                                    <%
                                        if(rr.getManagerApprove() != null && rr.getHrApprove() != null){
                                        }else{
                                    %>
                                    <button onclick="xacNhan('Accept', '<%= rr.getResignationRequestID() %>', event)" class="border bg-success" type="submit" name="btAction" value="Accept<%= rr.getResignationRequestID() %>">
                                        <i class="fa-solid fa-check" style="color: #FFFFFF"></i>
                                    </button>
                                    <button onclick="xacNhan('Deny', '<%= rr.getResignationRequestID() %>', event)" class="border bg-danger" type="submit" name="btAction" value="Deny<%= rr.getResignationRequestID() %>">
                                        <i class="fa-solid fa-x" style="color: #FFFFFF"></i>
                                    </button>
                                </td>
                            </tr>
                            <%  
                                        }
                                    } // en if
                                } // end foreach
                            %>
                        </table>
                        <%
                        }else{
                        %>
                        <p class="text-center">Tài khoản này không phải Quản lí nhân sự</p>
                        <%
                        }
                        %>
                    </form>
                </div>
            </div>

        </div>
        <script>
            function xacNhan(action, resignationRequestID, event) {
                var xacNhan = confirm("Bạn có chắc chắn muốn thực hiện hành động này không?");
                if (xacNhan) {
                    alert("Hành động đã được xác nhận!");
                    // Thực hiện hành động khi xác nhận
                    if (action === 'Accept') {
                        window.location.href = "AcceptResignationRequestServlet?resignationRequestID=" + resignationRequestID + "&btAction=Accept";
                    } else if (action === 'Deny') {
                        window.location.href = "DenyResignationRequestServlet?resignationRequestID=" + resignationRequestID + "&btAction=Deny";
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