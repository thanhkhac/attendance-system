<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.*"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chấm công</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            .mycell {
                width: 50%;
                border: 1px solid gray;
                border-radius: 10px;
            }

            .mytitle {
                box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
                padding-left: 5px;
            }

            .takeAttendance-block {
                font-size: 1.2em;
                box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
            }

            .mybutton {
                font-size: 1.2em;
                box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
            }
        </style>

        <style>
            .right{
                position: absolute;
                width: 83%;
                right: 0px;
            }

            @media screen and (max-width: 768px) {

                .right{
                    position: absolute;
                    width: 100%;
                    right: auto;
                }
            }
        </style>
    </head>
    <%
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");
        OvertimeDAO overtimeDAO = new OvertimeDAO();
        
        OvertimeDTO overtime = new OvertimeDAO().getCurrentOvertime(employeeDTO.getEmployeeID());
        TimesheetDTO timesheet = new TimesheetDAO().getCurrentTimesheet(employeeDTO.getEmployeeID());
        
        ArrayList<ShiftDTO> shifts = new ShiftDAO().getAllShiftDTO();
        request.setAttribute("overtime" , overtime);
        request.setAttribute("timesheet" , timesheet);
        request.setAttribute("shifts" , shifts);
    %>

    <body>
        <%@include file="Sidebar.jsp" %>
        <div class="right">
            <h1 class="text-center mt-3 mb-4">Chấm công</h1>
            <c:if test = "${not empty requestScope.timesheet}">
                <div class="row justify-content-center mt-3">
                    <div class="takeAttendance-block col-md-5 col-8 rounded-3 p-0 " style="background-color: #34568B;">
                        <div class="col-12 text-center fs-2 text-white fw-bold">
                            <c:forEach var="dto" items="${requestScope.shifts}">
                                <c:if test = "${dto.shiftID eq requestScope.timesheet.shiftID}">
                                    ${dto.name}
                                </c:if>
                            </c:forEach>
                        </div>
                        <div>
                            <table class="table bg-white">
                                <tr>
                                    <th colspan="4" class="text-center">${requestScope.timesheet.date}</th>
                                </tr>
                                <c:forEach var="dto" items="${requestScope.shifts}">
                                    <c:if test = "${dto.shiftID eq requestScope.timesheet.shiftID}">
                                        <tr>
                                            <th class="mytitle">Bắt đầu:</th>
                                            <td>
                                                ${dto.startTime}
                                            </td>
                                            <th class="mytitle">Kết thúc:</th>
                                            <td>
                                                ${dto.endTime}
                                            </td>
                                        </tr>
                                        <tr>
                                            <th class="mytitle">Cổng mở</th>
                                            <td>${dto.openAt}</td>
                                            <th class="mytitle">Cổng đóng</th>
                                            <td>${dto.closeAt}</td>
                                        </tr>
                                        <c:if test = "${not empty dto.breakStartTime}">
                                            <tr>
                                                <th class="mytitle">Nghỉ:</th>
                                                <td>${dto.breakStartTime}</td>
                                                <th class="mytitle">Kết thúc:</th>
                                                <td>${dto.breakEndTime}</td>
                                            </tr>
                                        </c:if>
                                        <tr>
                                            <th class="mytitle">Chấm công vào</th>
                                            <td>
                                                <c:if test = "${empty requestScope.timesheet.checkin}">
                                                    Chưa có
                                                </c:if>
                                                ${requestScope.timesheet.checkin}
                                            </td>
                                            <th class="mytitle">Chấm công ra</th>
                                            <td>
                                                <c:if test = "${empty requestScope.timesheet.checkout}">
                                                    Chưa có
                                                </c:if>
                                                ${requestScope.timesheet.checkout}
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </div>
                        <div class="col-12 text-center mb-3">
                            <button id="takeAttendance" class="btn text-white fw-bold mybutton" style="background-color: #88B04B;">
                                Chấm công
                            </button>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test = "${not empty requestScope.overtime}">
                <div class="row justify-content-center mt-3">
                    <div class="takeAttendance-block col-md-5 col-8 rounded-3 p-0 " style="background-color: #34568B;">
                        <div class="col-12 text-center fs-2 text-white fw-bold">
                            Tăng ca
                        </div>
                        <div>
                            <table class="table bg-white">
                                <tr>
                                    <th colspan="4" class="text-center">${requestScope.overtime.date}</th>
                                </tr>
                                <tr>
                                    <th class="mytitle">Bắt đầu:</th>
                                    <td>${requestScope.overtime.startTime}</td>
                                    <th class="mytitle">Kết thúc:</th>
                                    <td>${requestScope.overtime.endTime}</td>
                                </tr>
                                <tr>
                                    <th class="mytitle">Cổng mở:</th>
                                    <td>${requestScope.overtime.openAt}</td>
                                    <th class="mytitle">Cổng đóng:</th>
                                    <td>${requestScope.overtime.closeAt}</td>
                                </tr>
                                <tr>
                                    <th class="mytitle">Chấm công vào:</th>
                                    <td>
                                        <c:if test = "${empty requestScope.overtime.checkIn}">
                                            Chưa có
                                        </c:if>
                                        ${requestScope.overtime.checkIn}
                                    </td>
                                    <th class="mytitle">Chấm công ra:</th>
                                    <td>
                                        <c:if test = "${empty requestScope.overtime.checkOut}">
                                            Chưa có
                                        </c:if>
                                        ${requestScope.overtime.checkOut}
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="col-12 text-center mb-3">
                            <button id="TakeAttendanceOvertime" class="btn text-white fw-bold mybutton" style="background-color: #88B04B;">
                                Chấm công
                            </button>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>

        <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" data-bs-backdrop="static">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Thành công</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Thành công
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-info" type="button" onclick="location.reload();">
                            Đóng
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script>
                            $(document).ready(function () {
                                $("#takeAttendance").click(function (event) {
                                    $.ajax({
                                        type: "POST",
                                        url: "TakeAttendance",
                                        success: function (result) {
                                            if (result === "success") {
                                                $("#successModal").modal('show');
                                            } else {
                                                alert("Có lỗi xảy ra. Vui lòng thử lại.");
                                            }
                                        },
                                        error: function () {
                                            alert("Có lỗi xảy ra. Vui lòng thử lại.");
                                        }
                                    });
                                });

                                $("#TakeAttendanceOvertime").click(function (event) {
                                    $.ajax({
                                        type: "POST",
                                        url: "TakeAttendanceOvertime",
                                        success: function (result) {
                                            if (result === "success") {
                                                $("#successModal").modal('show');
                                            } else {
                                                alert("Có lỗi xảy ra. Vui lòng thử lại.");
                                            }
                                        },
                                        error: function () {
                                            alert("Có lỗi xảy ra. Vui lòng thử lại.");
                                        }
                                    });
                                });
                            });
        </script>
    </body>
</html>
