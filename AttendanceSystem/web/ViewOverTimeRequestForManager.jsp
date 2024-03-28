<%-- 
    Document   : ViewOverTimeRequestForManager
    Created on : Feb 29, 2024, 3:08:12 PM
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
        OverTimeRequestDAO otDAO = new OverTimeRequestDAO();
        EmployeeDAO dao = new EmployeeDAO();
        EmployeeDTO emDTO = new EmployeeDTO();
        EmployeeDTO managerDTO = new EmployeeDTO();
        EmployeeDTO hrDTO = new EmployeeDTO();
        DepartmentDAO deDao = new DepartmentDAO();
        DepartmentDTO deDTO = new DepartmentDTO();
        
        
        EmployeeDTO acc = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");
        int departmentID = acc.getDepartmentID();
        ArrayList<OverTimeRequestExtendDTO> list = otDAO.getOverTimeListByDepartment(departmentID);
        deDTO = deDao.getDepartmentById(departmentID);
    %>
    <body>
        <div>
            <div class="content">
                <%--<h1>Thông Báo</h1>--%>
                <div class="content-redirect">
                    <p><a href="HomePage.jsp">Trang chủ</a> | Duyệt đơn làm thêm</p>
                </div>  
                <div class="text-center">
                    <h1 style="margin: 30px">Danh sách đơn đăng kí làm ngoài giờ của <%=deDTO.getName()%> (Manager)</h1>
                </div>
                <div class="content-request">
                    <div class="content-request-type">
                        <label for="request-type">Loại đơn: </label>
                        <select id="mySelect" name="mySelect">
                            <option value="">Chọn Loại Đơn</option>
                            <option value="ViewLeaveRequestForManager.jsp">Đơn nghỉ phép</option>
                            <option value="ViewOverTimeRequestForManager.jsp">Đơn làm ngoài giờ</option>
                        </select>
                    </div>
                </div>
                <form action="DispatchController" method="POST">
                    <table class="table" style="width: 100%; margin: auto;">
                        <tr style="background-color: #CFE2FF">
                            <th class="text-center">Mã đơn</th>
                            <th class="text-center">Họ và tên</th>
                            <th style="display: none" class="text-center">Ngày gửi</th>
                            <th style="display: none" class="text-center">Ngày OT</th>
                            <th style="display: none" class="text-center">Giờ bắt đầu</th>
                            <th style="display: none" class="text-center">Giờ kết thúc</th>
                            <th class="text-center">Trạng thái <br> (Manager)</th>  
                            <th class="text-center">Người phê duyệt <br> (Manager)</th>
                            <th class="text-center">Phê duyệt</th>
                        </tr>
                        <%
                            for (OverTimeRequestExtendDTO otrq : list) {
                                emDTO = dao.getEmployeeDTO(otrq.getEmployeeID());
                                managerDTO = dao.getEmployeeDTO(otrq.getManagerID());
                                hrDTO = dao.getEmployeeDTO(otrq.getHrID());
                        %>
                        <tr class="employee-row" onclick="showDetailPopup(this)">
                            <td class="text-center"><%=otrq.getOverTimeRequestID()%></td>
                            <td class="text-center tdbreak"><%= emDTO.getLastName() + " " +  emDTO.getMiddleName() + " " + emDTO.getFirstName() %></td>
                            <td style="display: none" class="text-center"><%=otrq.getSentDate()%></td>
                            <td style="display: none" class="text-center"><%=otrq.getDate()%></td>
                            <td style="display: none" class="text-center"><%=otrq.getStartTime()%></td>     
                            <td style="display: none" class="text-center"><%=otrq.getEndTime()%></td>
                            <td class="text-center">
                                <%
                                    Boolean managerApprove = otrq.getManagerApprove();
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
                            <td class="text-center tdbreak">
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
                            <td class="text-center">
                                <%
                                    if(otrq.getManagerApprove() != null){
                                    }else if(otrq.getConflict() != null && otrq.getConflict().equalsIgnoreCase("conflict")){
                                %>
                                <p class="text-danger" title="Conflicts with existing work shifts"><i class="fa-solid fa-triangle-exclamation"></i></p>
                                    <%
                                        }else{
                                    %>
                                <button onclick="xacNhan('Accept', '<%= otrq.getOverTimeRequestID() %>', event)" class="border bg-success" type="submit" name="btAction" value="Accept<%= otrq.getOverTimeRequestID() %>">
                                    <i class="fa-solid fa-check" style="color: #FFFFFF"></i>
                                </button>
                                <button onclick="xacNhan('Deny', '<%= otrq.getOverTimeRequestID() %>', event)" class="border bg-danger" type="submit" name="btAction" value="Deny<%= otrq.getOverTimeRequestID() %>">
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
            function xacNhan(action, OverTimeRequestID, event) {
                var xacNhan = confirm("Bạn có chắc chắn muốn thực hiện hành động này không?");
                if (xacNhan) {
                    alert("Hành động đã được xác nhận!");
                    if (action === 'Accept') {
                        window.location.href = "AcceptOTRequestServlet?overTimeRequestID=" + OverTimeRequestID + "&btAction=Accept";
                    } else if (action === 'Deny') {
                        window.location.href = "DenyOTRequestServlet?overTimeRequestID=" + OverTimeRequestID + "&btAction=Deny";
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

                var overtimeRequestID = row.cells[0].innerHTML; // Mã đơn
                var employeeName = row.cells[1].innerHTML; // Họ và tên nhân viên
                var sentDate = row.cells[2].innerHTML; // Ngày gửi
                var date = row.cells[3].innerHTML; // Ngày OT
                var startTime = row.cells[4].innerHTML; // Giờ bắt đầu
                var endTime = row.cells[5].innerHTML; // Giờ kết thúc
                var managerStatus = row.cells[6].innerText; // Trạng thái (Manager)
                var managerName = row.cells[7].innerText; // Người phê duyệt (Manager)

                modalBody.innerHTML = `
                    <h2 style="border-bottom: 1px solid black;" >Chi Tiết Đơn Ngoài Giờ</h2>
                        <div class="details">
                            <div class="detail-row">
                                <span class="label">Mã Đơn:</span>
                                <span class="value">\${overtimeRequestID}</span>
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
                                <span class="label">Ngày làm: </span>
                                <span class="value">\${date}</span>
                            </div>
                            <div class="row detail-row">
                                <div class="col-md-6">
                                    <span class="label">Giờ bắt đầu: </span>
                                    <span class="value">\${startTime}</span>
                                </div>
                                <div class="col-md-6">
                                    <span class="label">Giờ kết thúc: </span>
                                    <span class="value">\${endTime}</span>
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

