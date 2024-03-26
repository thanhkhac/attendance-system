<%-- 
    Document   : ViewSentLeaveRequest
    Created on : Feb 28, 2024, 10:06:21 AM
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
            body{
                font-family: sans-serif;
                background-color: steelblue;
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

            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0,0,0,0.4);
            }

            .modal-content {
                background-color: #fefefe;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
            }

            .close {
                color: #aaaaaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
            }

            .close:hover,
            .close:focus {
                color: #000;
                text-decoration: none;
                cursor: pointer;
            }
            .content-request{
                margin-top: 50px;
                padding-bottom: 30px;
                /*border-bottom: 1px solid grey;*/
            }
            .content-request-type{
                border: 1px solid grey;
                padding: 10px;
                font-size: larger;
                font-weight: 600;
            }
            .content-request-type label{
                padding-right: 10px;
                border-right: 1px solid grey;
            }
            .content-request-type select{
                margin-left: 10px;
                font-size: medium;
                border: 1px solid grey;
                border-radius: 5px;
                padding: 5px;
            }
        </style>
    </head>
    <%
        LeaveRequestDAO lrDao = new LeaveRequestDAO();
        EmployeeDAO dao = new EmployeeDAO();
        EmployeeDTO emDTO = new EmployeeDTO();
        EmployeeDTO managerDTO = new EmployeeDTO();
        EmployeeDTO hrDTO = new EmployeeDTO();
        DepartmentDAO deDao = new DepartmentDAO();
        DepartmentDTO deDTO = new DepartmentDTO();
        
        EmployeeDTO acc = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");
        int employeeID = acc.getEmployeeID();
        ArrayList<LeaveRequestDTO> list = lrDao.getLeaveRequestByEmployeeID(employeeID);
    %>
    <body>
        <div>
            <div class="content">
                <%--<h1>Thông Báo</h1>--%>
                <div class="content-redirect">
                    <p><a href="HomePage.jsp">Trang chủ</a> | Tình trạng đơn</p>
                </div>
                <div class="text-center">
                    <h1 style="margin: 30px">Đơn nghỉ phép đã gửi</h1>
                </div>
                <div class="content-request">
                    <div class="content-request-type">
                        <label for="request-type">Loại đơn: </label>
                        <select id="mySelect" name="mySelect">
                            <option value="">Chọn Loại Đơn</option>
                            <option value="ViewSentLeaveRequest.jsp">Đơn nghỉ phép</option>
                            <option value="ViewSentOvertimeRequest.jsp">Đơn làm ngoài giờ</option>
                        </select>
                    </div>
                </div>
                <form action="DispatchController" method="POST">
                    <table class="table" style="width: 100%; margin: auto;">
                        <tr style="background-color: #CFE2FF">
                            <th class="text-center">Mã đơn</th>
                            <th class="text-center">Họ và tên</th>
                            <th class="text-center">Ngày gửi</th>
                            <th style="display: none" class="text-center">Ngày bắt đầu</th>
                            <th style="display: none" class="text-center">Ngày kết thúc</th>
                            <th style="display: none" class="text-center">Lí do</th>
                            <th class="text-center">Trạng thái</th>
                        </tr>
                        <%
                            for (LeaveRequestDTO lr : list) {
                                emDTO = dao.getEmployeeDTO(lr.getEmployeeID());
                                managerDTO = dao.getEmployeeDTO(lr.getManagerID());
                                hrDTO = dao.getEmployeeDTO(lr.getHrID());
                        %>
                        <tr class="employee-row" onclick="showDetailPopup(this)">
                            <td class="text-center"><%=lr.getLeaveRequestID()%></td>
                            <td class="text-center tdbreak"><%= emDTO.getLastName() + " " +  emDTO.getMiddleName() + " " + emDTO.getFirstName() %></td>
                            <td class="text-center"><%=lr.getSentDate()%></td>
                            <td style="display: none" class="text-center"><%=lr.getStartDate()%></td>     
                            <td style="display: none" class="text-center"><%=lr.getEndDate()%></td>
                            <td style="display: none" class="tdbreak"><%=lr.getReason()%></td>
                            <td class="text-center tdbreak">
                                <%
                                    Boolean status = lr.getHrApprove();
                                    Boolean statusMana = lr.getManagerApprove();
                                    if(statusMana != null && statusMana == false){
                                %>
                                <p class="text-danger fw-bold">Từ chối</p>
                                <%    
                                    }else if(status == null){
                                %>
                                <p class="text-center fw-bold">Chờ xử lý</p>
                                <%
                                    }else if(status == true){
                                %>
                                <p class="text-success fw-bold" >Chấp nhận</p>
                                <%        
                                    }else if(status == false){
                                %>
                                <p class="text-danger fw-bold">Từ chối</p>
                                <%
                                    }
                                %>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                    <div id="pagination-container">
                        <ul id="pagination" class="pagination justify-content-center"></ul>
                    </div>
                    <div id="myModal" class="modal">
                        <div class="modal-content" style="width: 25%">
                            <div id="modal-body">

                            </div>
                        </div>
                    </div>
                </form>
            </div>

        </div>

        <script>
            var pageSize = 10; // Số lượng dòng mỗi trang
            var currentPage = 1; // Trang hiện tại

            function showPage(page) {
                var rows = document.getElementsByClassName('employee-row');
                var pageCount = Math.ceil(rows.length / pageSize);

                for (var i = 0; i < rows.length; i++) {
                    rows[i].style.display = 'none';
                }

                var startIndex = (page - 1) * pageSize;
                var endIndex = startIndex + pageSize;
                for (var i = startIndex; i < endIndex && i < rows.length; i++) {
                    rows[i].style.display = 'table-row';
                }

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

        <script>
            function showDetailPopup(row) {
                var modal = document.getElementById("myModal");
                var modalBody = document.getElementById("modal-body");

                var requestID = row.cells[0].innerHTML; // Mã đơn
                var employeeName = row.cells[1].innerHTML; // Họ và tên nhân viên
                var sentDate = row.cells[2].innerHTML; // Ngày gửi
                var startDate = row.cells[3].innerHTML; // Giờ bắt đầu
                var endDate = row.cells[4].innerHTML; // Giờ kết thúc
                var reason = row.cells[5].innerText; // Trạng thái (Manager)
                var status = row.cells[6].innerText; // Người phê duyệt (Manager)

                modalBody.innerHTML = `
                    <h2 style="border-bottom: 1px solid black;" >Chi Tiết Đơn Ngoài Giờ</h2>
                        <div class="details">
                            <div class="detail-row">
                                <span class="label">Mã Đơn:</span>
                                <span class="value">\${requestID}</span>
                            </div>
                            <div class="detail-row">
                                <span class="label">Họ và tên:</span>
                                <span class="value">\${employeeName}</span>
                            </div>
                            <div class="detail-row">
                                <span class="label">Ngày gửi: </span>
                                <span class="value">\${sentDate}</span>
                            </div>
                            <div class="row detail-row">
                                <div class="col-md-6">
                                    <span class="label">Ngày bắt đầu: </span>
                                    <span class="value">\${startDate}</span>
                                </div>
                                <div class="col-md-6">
                                    <span class="label">Ngày kết thúc: </span>
                                    <span class="value">\${endDate}</span>
                                </div>
                            </div>
                            <div class="detail-row">
                                <span class="label">Lí do: </span>
                                <span class="value">\${reason}</span>
                            </div>
                            <div class="detail-row">
                                <span class="label">Trạng thái: </span>
                                <span class="value">\${status}</span>
                            </div>
                        </div>
                `;
                modal.style.display = "block";
            }

            function closeModal() {
                var modal = document.getElementById("myModal");
                modal.style.display = "none";
            }

            window.onclick = function (event) {
                var modal = document.getElementById("myModal");
                if (event.target === modal) {
                    modal.style.display = "none";
                }
            }
        </script>
        <script>
            document.getElementById('mySelect').addEventListener('change', function () {
                var selectedOption = this.options[this.selectedIndex].value;
                if (selectedOption !== "") {
//                    window.open(selectedOption, '_blank');
                    window.location.href = selectedOption;
                }
            });
        </script>
        <script src="https://kit.fontawesome.com/c2b5cd9aa7.js" crossorigin="anonymous"></script>
        <script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
