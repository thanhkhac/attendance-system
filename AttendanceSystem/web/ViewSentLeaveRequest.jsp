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
            <div class="right">
                <div class="content-redirect">
                    <p><a href="ThanhCong.html">Home</a> | <a href="javascript:history.back()">Trở Lại</a> | Process Request For Manager</p>
                </div>
                <div class="text-center">
                    <h1 style="margin: 30px">Đơn đã gửi</h1>
                </div>
                <div>
                    <form action="DispatchController" method="POST">
                        <table class="table">
                            <tr style="background-color: #CFE2FF">
                                <th class="text-center">Mã đơn</th>
                                <th class="text-center">Mã nhân viên</th>
                                <th class="text-center">Ngày gửi</th>
                                <th class="text-center">Ngày bắt đầu</th>
                                <th class="text-center">Ngày kết thúc</th>
                                <th class="text-center">Lí do</th>
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
                            </tr>
                            <%
                                }
                            %>
                        </table>
                        <div id="pagination-container">
                            <ul id="pagination" class="pagination justify-content-center"></ul>
                        </div>
                    </form>
                </div>
            </div>

        </div>
        
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
