<%-- 
    Document   : ViewResignationRequestForManager
    Created on : Feb 27, 2024, 10:24:00 PM
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
        <title>Hệ thống điểm danh</title>
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

            #pagination-container {
                margin-top: 20px;
                text-align: right;
            }

            #pagination {
                display: inline-block;
                padding-left: 0;
                margin: 20px 0;
                border-radius: .25rem;
            }

            #pagination li {
                display: inline;
                margin-right: 5px;
            }

            #pagination li a {
                position: relative;
                float: left;
                padding: .5rem .75rem;
                margin-left: -1px;
                line-height: 1.25;
                color: #007bff;
                text-decoration: none;
                background-color: #fff;
                border: 1px solid #dee2e6;
            }

            #pagination li.active a {
                z-index: 1;
                color: #fff;
                background-color: #007bff;
                border-color: #007bff;
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
                <h1 style="margin: 30px">Danh sách đơn gia hạn hợp đồng của <%=deDTO.getName()%> (Manager)</h1>
            </div>
            <div>
                <form action="DispatchController" method="POST">
                    <table class="table mx-auto">
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
                            }
                        %>
                    </table>
                    <div id="pagination-container">
                        <ul id="pagination" class="pagination justify-content-center"></ul>
                    </div>
                    
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

        <script>
            var pageSize = 3; // Số lượng dòng mỗi trang
            var currentPage = 1; // Trang hiện tại

            function showPage(page) {
                var rows = document.getElementsByClassName('employee-row');
                var pageCount = Math.ceil(rows.length / pageSize);

                // Ẩn tất cả các dòng
                for (var i = 0; i < rows.length; i++) {
                    rows[i].style.display = 'none';
                }

                // Hiển thị các dòng của trang hiện tại
                var startIndex = (page - 1) * pageSize;
                var endIndex = startIndex + pageSize;
                for (var i = startIndex; i < endIndex && i < rows.length; i++) {
                    rows[i].style.display = 'table-row';
                }

                // Tạo nút điều hướng phân trang
                var paginationElement = document.getElementById('pagination');
                paginationElement.innerHTML = '';
                for (var i = 1; i <= pageCount; i++) {
                    var li = document.createElement('li');
                    var a = document.createElement('a');
                    a.href = '#';
                    a.innerHTML = i;
                    a.addEventListener('click', function (e) {
                        currentPage = parseInt(e.target.innerHTML);
                        showPage(currentPage);
                    });
                    li.appendChild(a);
                    paginationElement.appendChild(li);
                }
            }

            // Hiển thị trang đầu tiên khi trang được tải
            document.addEventListener('DOMContentLoaded', function () {
                showPage(currentPage);
            });
        </script>

        <script src="https://kit.fontawesome.com/c2b5cd9aa7.js" crossorigin="anonymous"></script>
        <script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
