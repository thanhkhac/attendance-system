<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.*"%>
<%@page import="authorization.*"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>W3.CSS</title>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            .xSidebar {
                display: block;
                z-index: 5;
                width: 17% !important;
                background-color: #498CB6;
                float: left;
            }


            @media screen and (max-width: 768px) {
                .xSidebar {
                    display: none;
                    width: 50% !important;
                }
                .closebutton{
                    display: block;
                }
            }
        </style>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>

    <%
        HashMap<Integer, DepartmentDTO> departments =  new DepartmentDAO().getAllDepartment();
        request.setAttribute("departments", departments);
        int roleId = ((EmployeeDTO) request.getSession().getAttribute("ACCOUNT")).getRoleID();
        
        int roleEmployee = RoleConstants.EMPLOYEE;
        int roleHR = RoleConstants.HR;
        int roleManager = RoleConstants.MANAGER;
        int roleManagerHr = RoleConstants.MANAGER_HR;
        
        request.setAttribute("roleId", roleId);
        request.setAttribute("roleEmployee", roleEmployee);
        request.setAttribute("roleManager", roleManager);
        request.setAttribute("roleManagerHr", roleManagerHr);
        request.setAttribute("roleHR", roleHR);
        
    %>
    <body>
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
        <div class="w3-sidebar w3-bar-block xSidebar fs-6" id="mySidebar">
            <button class="w-100 w3-button closebutton text-white" onclick="w3_close()">&#9776;</button>
            <div  class="w3-bar-item  fw-bold text-white" style="background-color: #4096F1">
                <div class="row">
                    <div class="col-3">
                        <img class="rounded-circle w-100" src="https://t4.ftcdn.net/jpg/03/49/49/79/360_F_349497933_Ly4im8BDmHLaLzgyKg2f2yZOvJjBtlw5.jpg" alt="Avatar">
                    </div>
                    <div class="col-9">
                        <div>
                            <div class="dropdown">
                                <div style="cursor: pointer" class=" dropdown-toggle" data-bs-toggle="dropdown">
                                    ${account.lastName} ${account.middleName} ${account.firstName}
                                </div>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="UpdateEmployeeProfile.jsp">Tài khoản</a></li>
                                    <li><a class="dropdown-item" href="ChangePasswordForUser.jsp">Đổi mật khẩu</a></li>
                                    <li><a class="dropdown-item" href="logout">Đăng xuất</a></li>
                                </ul>
                            </div>
                        </div>

                        <div style="" class="fw-normal">
                            ${departments[account.departmentID].name}
                        </div>
                    </div>
                </div>
            </div>
            <a href="HomePage.jsp" class="w3-bar-item w3-button fw-bold  text-white">
                <i class="fa-solid fa-house"></i>
                Trang chủ
            </a>
            <a href="getallnews" class="w3-bar-item w3-button fw-bold  text-white">
                <i class="fa-solid fa-newspaper"></i>
                Tin tức
            </a>
            <a href="Attendance" class="w3-bar-item w3-button fw-bold  text-white">
                <i class="fa-solid fa-check-to-slot"></i>
                Chấm công
            </a>
            <a href="WorkSchedule" class="w3-bar-item w3-button fw-bold  text-white"><i class="fa-solid fa-calendar"></i> Lịch làm việc</a>
            <a href="#" class="w3-bar-item w3-button fw-bold  text-white" data-toggle="collapse" data-target="#submenu3">
                <i class="fa-solid fa-envelope"></i> Đơn từ
                <i class="fa fa-caret-down float-end"></i>
            </a>
            <div class="collapse ps-4" id="submenu3">
                <a href="PrepareRequestServlet" class="w3-bar-item w3-button fw-bold  text-white">Gửi đơn</a>
                <a href="NavigateSentRequest.jsp" class="w3-bar-item w3-button fw-bold text-white">Đơn đã gửi</a>
            </div>

            <c:if test = "${requestScope.roleId == requestScope.roleHR or requestScope.roleId == requestScope.roleManagerHr}">
                <a href="#" class="w3-bar-item w3-button fw-bold  text-white" data-toggle="collapse" data-target="#submenu1">
                    <i class="fa-solid fa-users"></i> Quản lý nhân viên
                    <i class="fa fa-caret-down float-end"></i>
                </a>
                <div class="collapse ps-4" id="submenu1">
                    <a href="DispatchController?btAction=ViewEmployee" class="w3-bar-item w3-button fw-bold text-white">Nhân viên</a>
                    <a href="DepartmentServlet" class="w3-bar-item w3-button fw-bold  text-white">Phòng ban</a>
                </div>
                <a href="#" class="w3-bar-item w3-button fw-bold  text-white" data-toggle="collapse" data-target="#submenu2">
                    <i class="fa-regular fa-calendar-days"></i> Quản lý lịch làm việc
                    <i class="fa fa-caret-down float-end"></i>
                </a>
                <div class="collapse ps-4" id="submenu2">
                    <a href="ShiftManagement" class="w3-bar-item w3-button fw-bold  text-white">Ca làm</a>
                    <a href="ScheduleWork" class="w3-bar-item w3-button fw-bold  text-white">Xếp lịch cho nhân viên</a>
                    <a href="ScheduledEmployees" class="w3-bar-item w3-button fw-bold text-white">Danh sách nhân viên được xếp lịch</a>
                    <a href="Overtime.jsp" class="w3-bar-item w3-button fw-bold text-white">Xếp lịch tăng ca</a>
                </div>
            </c:if>


            <c:if test = "${requestScope.roleId != requestScope.roleEmployee}">
                <a href="#" class="w3-bar-item w3-button fw-bold  text-white" data-toggle="collapse" data-target="#submenu5">
                    <i class="fa-solid fa-list-check"></i> Quản lý đơn từ
                    <i class="fa fa-caret-down float-end"></i>
                </a>
                <div class="collapse ps-4" id="submenu5">
                    <c:if test = "${requestScope.roleId == requestScope.roleManagerHr}">
                        <a href="NavigateRequestForHR.jsp" class="w3-bar-item w3-button fw-bold  text-white">Tất cả</a>
                        <a href="NavigateRequestForManager.jsp" class="w3-bar-item w3-button fw-bold  text-white">Phòng ban</a>
                    </c:if>
                    <c:if test = "${requestScope.roleId == requestScope.roleManager}">
                        <a href="NavigateRequestForManager.jsp" class="w3-bar-item w3-button fw-bold  text-white">Phòng ban</a>
                    </c:if>

                    <c:if test = "${requestScope.roleId == requestScope.roleHR}">
                        <a href="NavigateRequestForHR.jsp" class="w3-bar-item w3-button fw-bold  text-white">Tất cả</a>
                    </c:if>
                </div>
            </c:if>

            <a href="#" class="w3-bar-item w3-button fw-bold  text-white" data-toggle="collapse" data-target="#submenu6">
                <i class="fa-solid fa-chart-simple"></i> Khác
                <i class="fa fa-caret-down float-end"></i>
            </a>
            <div class="collapse ps-4" id="submenu6">
                <a href="GetEmployeeStatisticsServlet" class="w3-bar-item w3-button fw-bold  text-white">Xuất dữ liệu cá nhân</a>
                <c:if test = "${requestScope.roleId == requestScope.roleHR or requestScope.roleId == requestScope.roleManagerHr}">
                    <a href="DownloadWorksheet.jsp" class="w3-bar-item w3-button fw-bold  text-white">Xuất dữ liệu của nhân viên</a>
                    <a href="GetAllNewsByHR" class="w3-bar-item w3-button fw-bold  text-white">Quản lý tin tức</a>
                </c:if>
            </div>





        </div>

        <div class="openSideBar">
            <button class="w3-button w3-white" onclick="w3_open()">&#9776;</button>
        </div>

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>

        <script>
                function w3_open() {
                    document.getElementById("mySidebar").style.display = "block";
                }

                function w3_close() {
                    document.getElementById("mySidebar").style.display = "none";
                }
        </script>
    </body>
</html>