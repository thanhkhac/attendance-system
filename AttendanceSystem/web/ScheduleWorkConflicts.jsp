<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

            @media screen and (max-width: 768px) {

                .calendar{
                    position: absolute;
                    width: 100%;
                    right: auto;
                }
            }
        </style>

    </head>
    <body>
        <div>
            <%@include file="Sidebar.jsp" %>
            <div class="right">
                <div class="text-center pt-3" style="font-family: sans-serif; font-weight: 900">
                    <h2>Danh sách lịch làm việc trùng lặp</h2>
                    <a href="javascript:history.back()" class="btn btn-outline-secondary" style="position: absolute; left: 15px; top: 15px;">
                        <i class="bi bi-arrow-left"></i> Trở lại
                    </a>
                </div>
                <form action="InsertTimesheetServlet" method="POST">
                    <table class="table">
                        <tr>
                            <th>Ngày tăng ca</th>
                            <th>Mã nhân viên</th>
                            <th>Họ và tên</th>
                            <th>Bắt đầu</th>
                            <th>Kết thúc</th>
                        </tr>
                        <c:forEach var="dto" items="${requestScope.conflicts}" varStatus="counter">
                            <tr class="conflict-row">
                                <td>${dto.date}</td>
                                <td>${dto.employeeID}</td>
                                <td>${dto.employeeID}</td>
                                <td>${dto.startTime}</td>
                                <td>${dto.endTime}</td>
                            </tr>
                        </c:forEach>

                    </table>
                    <c:if test="${fn:length(requestScope.conflicts) == 0}">
                        <div id="validation-message" style="" class="alert alert-success mt-3">
                            Thông tin hợp lệ, bạn có thể tiếp tục.
                        </div>
                    </c:if>
                    <div class="text-center mt-3">
                        <button type="submit" class="btn btn-primary" id="form-submit-button">Hoàn thành</button>
                    </div>
                    <div>
                        <c:forEach var="dto" items="${paramValues.shift}" varStatus="counter">
                            <c:if test = "${dto ne 'no'}">
                                <input type="hidden" name="shift" value="${dto}">
                            </c:if>
                        </c:forEach>
                    </div>
                    <div>
                        <c:forEach var="dto" items="${paramValues.chkEmployeeID}" varStatus="counter">
                            <input type="hidden" name="chkEmployeeID" value="${dto}">
                        </c:forEach>
                    </div>
                </form>
            </div>

        </div>
        <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
                        <a href="ScheduleWork.jsp">
                            <button class="btn btn-info" type="button">
                                Trở về trang xếp lịch
                            </button>
                        </a>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>




        <script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>
        <script>
        </script>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script>
            $(document).ready(function () {
                $("#form-submit-button").click(function (event) {
                    event.preventDefault();
                    var conflictRows = document.querySelectorAll(".conflict-row");

                    if (conflictRows.length > 0) {

                        alert("Có lịch làm việc trùng lặp. Vui lòng kiểm tra lại.");
                        event.preventDefault();
                        return;
                    }

                    var formData = $("form").serialize();

                    $.ajax({
                        type: "POST",
                        url: "InsertTimesheetServlet",
                        data: formData,
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
