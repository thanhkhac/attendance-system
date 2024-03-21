<%-- 
    Document   : ProcessLeaveRequestForManager
    Created on : Feb 19, 2024, 8:14:44 AM
    Author     : nguye
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.time.*" %>
<%@page import="model.*" %>
<%@page import="model.request.*" %>
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
        
        ArrayList<LeaveRequestDTO> list = lrDao.getLeaveRequestForHR(1);
        EmployeeDTO acc = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");
    %>
    <body>
        <div>
            <div class="content">
                <h1>Thông Báo</h1>
                <div class="content-redirect">
                    <p><a href="HomePage.jsp">Trang chủ</a> | Duyệt đơn nghỉ phép </p>
                </div>
                <div class="text-center">
                    <h1 style="margin: 30px">Danh sách đơn xin nghỉ phép (HR)</h1>
                </div>
                <div class="content-request">
                    <div class="content-request-type">
                        <label for="request-type">Loại đơn: </label>
                        <select id="mySelect" name="mySelect">
                            <option value="">Chọn Loại Đơn</option>
                            <option value="ViewLeaveRequestForHR.jsp">Đơn nghỉ phép</option>
                            <option value="ViewOverTimeRequestForHR.jsp">Đơn làm ngoài giờ</option>
                        </select>
                    </div>
                </div>
            <!--</div>-->chController" method="POST">
                <table class="table" style="width: 100%; margin: auto;">
                    <tr style="background-color: #CFE2FF">
                        <th class="text-center">Mã đơn</th>
                        <th class="text-center">Phòng ban</th>
                        <th class="text-center">Họ và tên</th>
                        <th style="display: none" class="text-center">Ngày gửi</th>
                        <th style="display: none" class="text-center">Ngày bắt đầu</th>
                        <th style="display: none" class="text-center">Ngày kết thúc</th>
                        <th style="display: none" class="text-center">Lí do</th>
                        <th style="display: none" class="text-center">Trạng thái <br> (Manager)</th>  
                        <th class="text-center">Trạng thái <br> (HR)</th>
                        <th style="display: none" class="text-center">Người phê duyệt <br> (Manager)</th>
                        <th class="text-center">Người phê duyệt <br> (HR)</th>
                        <th class="text-center">Phê duyệt</th>
                    </tr>
                    <%
                        for (LeaveRequestDTO lr : list) {
                            emDTO = dao.getEmployeeDTO(lr.getEmployeeID());
                            managerDTO = dao.getEmployeeDTO(lr.getManagerID());
                            hrDTO = dao.getEmployeeDTO(lr.getHrID());
                                    
                            int departmentID = lr.getDepartmentID();
                            deDTO = deDao.getDepartmentById(departmentID);
                    %>
                    <tr class="employee-row" onclick="showDetailPopup(this)">
                        <td class="text-center"><%=lr.getLeaveRequestID()%></td>
                        <td class="text-center"><%=deDTO.getName()%></td>
                        <td class="text-center tdbreak"><%= emDTO.getLastName() + " " +  emDTO.getMiddleName() + " " + emDTO.getFirstName() %></td>
                        <td style="display: none" class="text-center"><%=lr.getSentDate()%></td>
                        <td style="display: none" class="text-center"><%=lr.getStartDate()%></td>     
                        <td style="display: none" class="text-center"><%=lr.getEndDate()%></td>
                        <td style="display: none" class="tdbreak"><%=lr.getReason()%></td>
                        <td style="display: none" class="text-center">
                            <%
                                Boolean managerApprove = lr.getManagerApprove();
                                if(managerApprove == null){
                            %>
                            <p class="fw-bold">Chờ xử lý</p>
                            <%
                                }else if (managerApprove == true){
                            %>
                            <p class="text-success fw-bold" >Chấp nhận</p>
                            <%
                                }else if (managerApprove == false){
                            %>
                            <p class="text-danger fw-bold">Từ chối</p>
                            <%
                                }
                            %>
                        </td>
                        <td class="text-center font-weight-bold">
                            <%
                                Boolean hrApprove = lr.getHrApprove();
                                if(hrApprove == null){
                            %>
                            <p class="fw-bold">Chờ xử lý</p>
                            <%
                                }else if (hrApprove == true){
                            %>
                            <p class="text-success fw-bold" >Chấp nhận</p>
                            <%
                                }else if (hrApprove == false){
                            %>
                            <p class="text-danger fw-bold">Từ chối</p>
                            <%
                                }
                            %>
                        </td>
                        <td style="display: none" class="text-center tdbreak">
                            <%
                                if(managerDTO != null){
                            %>
                            <%= managerDTO.getLastName() + " " + managerDTO.getMiddleName() + " " + managerDTO.getFirstName() %>
                            <%
                                }else{
                            %>
                            <p class="text-center fw-bold">Chờ xử lý</p>
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
                            <p class="text-center fw-bold">Chờ xử lý</p>
                            <%
                                }
                            %>
                        </td>
                        <td class="text-center">
                            <%
                                if( lr.getHrApprove() != null && lr.getHrApprove()){
                                    if(!lr.getStatus()){
                            %>
                            <form action="DispatchController" method="Post">
                                <input type="hidden" name="requestID" value="<%=lr.getLeaveRequestID()%>">
                                <button type="submit" name="btAction" class="btn btn-primary" value="Schedule">Xếp Lịch</button>  
                            </form>
                            <% 
                                    }
                                }else if(lr.getHrApprove()==null){
                            %>
                            <button onclick="xacNhan('Accept', '<%= lr.getLeaveRequestID() %>', event)" class="border bg-success" type="submit" name="btAction" value="Accept<%= lr.getLeaveRequestID() %>">
                                <i class="fa-solid fa-check" style="color: #FFFFFF"></i>
                            </button>
                            <button onclick="xacNhan('Deny', '<%= lr.getLeaveRequestID() %>', event)" class="border bg-danger" type="submit" name="btAction" value="Deny<%= lr.getLeaveRequestID() %>">
                                <i class="fa-solid fa-x" style="color: #FFFFFF"></i>
                            </button>
                            <%  
                                        }
                                }
                            %>
                        </td>

                    </tr>
                </table>
                <div id="pagination-container">
                    <ul id="pagination" class="pagination justify-content-center"></ul>
                </div>
                <div id="myModal" class="modal">
                    <div class="modal-content" style="width: 30%">
                        <div id="modal-body">

                        </div>
                    </div>
                </div>
            </form>
        </div>

    </div>
    <script>
        function xacNhan(action, leaveRequestID, event) {
            var xacNhan = confirm("Bạn có chắc chắn muốn thực hiện hành động này không?");
            if (xacNhan) {
                alert("Hành động đã được xác nhận!");
                if (action === 'Accept') {
                    window.location.href = "AcceptLeaveRequestServlet?leaveRequestID=" + leaveRequestID + "&btAction=Accept";
                } else if (action === 'Deny') {
                    window.location.href = "DenyLeaveRequestServlet?leaveRequestID=" + leaveRequestID + "&btAction=Deny";
                }
            } else {
                alert("Hành động đã bị hủy bỏ!");
            }
            event.preventDefault();
        }
    </script>

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

        document.addEventListener('DOMContentLoaded', function () {
            showPage(currentPage);
        });
    </script>

    <script>
        function showDetailPopup(row) {
            var modal = document.getElementById("myModal");
            var modalBody = document.getElementById("modal-body");

            var leaveRequestID = row.cells[0].innerHTML; // Mã đơn
            var department = row.cells[1].innerHTML; // Phòng ban
            var employeeName = row.cells[2].innerHTML; // Họ và tên nhân viên
            var sentDate = row.cells[3].innerHTML; // Ngày gửi
            var startDate = row.cells[4].innerHTML; // Ngày bắt đầu
            var endDate = row.cells[5].innerHTML; // Ngày kết thúc
            var reason = row.cells[6].innerHTML; // Lí do
            var managerStatus = row.cells[7].innerText; // Trạng thái (Manager)
            var hrStatus = row.cells[8].innerText; // Trạng thái (HR)
            var managerName = row.cells[9].innerText; // Người phê duyệt (Manager)
            var hrName = row.cells[10].innerText; // Người phê duyệt (HR)

            modalBody.innerHTML = `
                <h2 style="border-bottom: 1px solid black;" >Chi Tiết Đơn Nghỉ Phép</h2>
                    <div class="details">
                        <div class="detail-row">
                            <span class="label">Mã Đơn:</span>
                            <span class="value">\${leaveRequestID}</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Phòng ban: </span>
                            <span class="value">\${department}</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Họ và tên:</span>
                            <span class="value">\${employeeName}</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Ngày gửi: </span>
                            <span class="value">\${sentDate}</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Quản lí phê duyệt: </span>
                            <span class="value">\${managerName}</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Lí do: </span>
                            <span class="value">\${reason}</span>
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
            if (event.target == modal) {
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