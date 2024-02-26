<%-- 
    Document   : ViewResignationRequestByManager
    Created on : Feb 25, 2024, 1:52:57 PM
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
            .content{
                background-color: white;
                max-width: 65%;
                margin: auto;
                padding: 20px;
                margin-top: 10px;
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
        ResignationRequestDAO rrDao = new ResignationRequestDAO();
        EmployeeDAO dao = new EmployeeDAO();
        EmployeeDTO emDTO = new EmployeeDTO();
        EmployeeDTO managerDTO = new EmployeeDTO();
        EmployeeDTO hrDTO = new EmployeeDTO();
        DepartmentDAO deDao = new DepartmentDAO();
        DepartmentDTO deDTO = new DepartmentDTO();
        
        
        EmployeeDTO acc = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");
        int departmentID = acc.getDepartmentID();
        ArrayList<ResignationRequestDTO> list = rrDao.getRegisnationRequestByDepartment(departmentID);
        deDTO = deDao.getDepartmentById(departmentID);
    %>
    <body>
        <div>
            <div class="content">
                <h1>Thông Báo</h1>
                <div class="content-redirect">
                    <p><a href="ThanhCong.html">Home</a> | <a href="javascript:history.back()">Trở Lại</a> | Result</p>
                </div>  
            </div>
            <div class="text-center">
                <h1 style="margin: 30px">Danh sách đơn Regisnation <%=deDTO.getName()%> (Manager)</h1>
            </div>
            <div>
                <form action="DispatchController" method="POST">
                    <%
                        if(acc.getRoleID() == 4){   //role quản lí 
                                
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
                            <th class="text-center">Người phê duyệt <br> (Manager)</th>
                            <th class="text-center">check</th>
                        </tr>
                        <%
                            for (ResignationRequestDTO rr : list) {
                                emDTO = dao.getEmployeeDTO(rr.getEmployeeID());
                                managerDTO = dao.getEmployeeDTO(rr.getManagerID());
                                hrDTO = dao.getEmployeeDTO(rr.getHrID());
//                                if(rr.getDepartmentID() == departmentID){
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
                                    if(rr.getManagerApprove() != null){
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
                                //}
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