<%-- 
    Document   : ScheduleLeave
    Created on : Feb 26, 2024, 3:07:00 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <script src="assets/Bootstrap5/js/bootstrap.min.js"></script>
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <title>JSP Page</title>
        <style>
            .content{
                max-width: 90%;
                margin: auto;
                margin-top: 20px;
            }
            .content-con{
                display: flex;
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
            .content-request{
                border-right: 3px solid red;
            }
            .content-request-detail{
                margin-top: 20px;
                border: 1px dashed red;
                padding: 10px;
                width: 480px;
            }
            .detail-items{
                margin: 10px 0;

            }
            .detail-items label{
                width: 200px;
                margin-right: 30px;
            }
            .detail-items input{
                width: 200px;
                border: none;
                outline: none;
                border-bottom: 2px dotted blue;
            }
            .detail-date{
                font-size: large;
                font-weight: 600;
            }
            .content-working-details{
                margin-left: 20px;
                /*border: 1px solid red;*/
                padding: 0 10px;

            }
            .content-working-details span{
            }
            .working-details-days{
                margin: 20px 0;
                display: flex;
                flex-wrap: wrap;
                justify-content: space-around;
            }
            .details-days-container{
                margin: 10px;
                /*border: 1px solid #c3e6cb;*/
                border-radius: 5px;
                transition: transform 0.35s ease;
            }
            .details-days-container:hover{
                transform: translateY(-10px);
                cursor: pointer;
            }
            .details-days-lv, .details-days-ot{
                padding: 3px;
                font-size: large;
                margin: 0;
                color: #155724;
                background-color: #d4edda;
                border-radius: 4px;
            }
            .confirm-button-container{
            }
            .confirm-button{
                max-width: 250px;
                margin: auto;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <c:set var="listTimeSheets" value="${requestScope.listTimeSheet}" />
        <c:set var="listOverTimes" value="${requestScope.listOverTime}" />
        <c:set var="leave" value="${requestScope.leave}" />
        <c:set var="employee" value="${requestScope.employee}" />
        <div class="content">
            <div class="content-redirect">
                <p><a href="ThanhCong.html">Home</a> | Send Request</p>
            </div>
            <div class="content-con row">

                <div class="content-request col-md-5">
                    <h2 style="margin: 20px 0;">Thông tin chi tiết yêu cầu nghỉ</h2>
                    <div class="content-request-detail">
                        <div class="detail-items">
                            <label for="id">EmployeeID: </label>
                            <input readonly type="text" id="id" value="${leave.getEmployeeID()}">
                        </div>
                        <div class="detail-items">
                            <label for="name">EmployeeName: </label>
                            <input readonly type="text" id="name" value="${employee.getLastName()} ${employee.getMiddleName()} ${employee.getFirstName()}">
                        </div>
                        <div class="detail-items">
                            <label for="type">Type (Loại Nhân Viên): </label>
                            <input readonly type="text" id="type" value="${employee.getEmployeeTypeName()}">
                        </div>
                        <div class="detail-items">
                            <label for="department">Department (Phòng Ban): </label>
                            <input readonly type="text" id="department" value="${employee.getDepartmentName()}">
                        </div>
                        <div class="detail-items">
                            <label for="role">Role (Chức vụ): </label>
                            <input readonly type="text" id="role" value="${employee.getRoleName()}">
                        </div>
                        <hr>
                        <div class="detail-items">
                            <label for="startDate">StartDate (Từ Ngày): </label>
                            <input class="detail-date btn btn-primary" readonly type="text" id="startDate" value="${leave.getStartDate()}">
                        </div>
                        <div class="detail-items">
                            <label for="endDate">EndDate (Đến Ngày): </label>
                            <input class="detail-date btn btn-danger" readonly type="text" id="endDate" value="${leave.getEndDate()}">
                        </div>
                        <div class="detail-items">
                            <label for="reason">Reason(Lý do): </label>
                            <textarea 
                                readonly
                                name="reason" 
                                id="reason" 
                                rows="5" 
                                cols="20" 
                                style="width: 95%; height: 140px; padding: 5px;;" 
                                >${leave.getReason()}</textarea>
                        </div>
                    </div>
                </div>
                <div class="content-working-details col-md-6">
                    <h2 style="margin: 20px 0;">Thông tin ngày làm việc</h2>
                    <h4>Trong khoảng thời gian từ <span style="color: blue">${leave.getStartDate()}</span> đến <span style="color: red">${leave.getEndDate()}</span> người này có: </h4>
                    <h4> - ${listTimeSheet.size()} Ca Làm Việc (Giờ Hành Chính)</h4>
                    <div class="working-details-days">
                        <c:forEach items="${listTimeSheets}" var="ts">
                            <div class="details-days-container">
                                <p class="details-days-lv">
                                    ${ts.getDate()}
                                </p>
                            </div>
                        </c:forEach>
                    </div>
                    <h4> - ${listOverTime.size()} Ca Overtime (Tăng Ca)</h4>
                    <div class="working-details-days">
                        <c:forEach items="${listOverTime}" var="ot">
                            <div class="details-days-container">
                                <p class="details-days-ot">
                                    ${ot.getDate()}
                                </p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <!--<div class="confirm-button-container col-md-5">-->
                <!--<form action="ScheduleLeaveForRequestServlet" method="Post" class="confirm-button-container">-->
                <input type="hidden" name="requestID" id="requestID" value="${leave.getLeaveRequestID()}">
                <input onclick="confirmation()" class="btn btn-success confirm-button" type="submit" name="" value="Chấp Nhận Đơn Nghỉ Phép">
                <!--</form>--> 
                <!--</div>-->
            </div>
        </div>
    </body>
    <script>
        function confirmation() {
            var cf = confirm("Bạn có chắc chắn thực hiện hành động này ?");
            var requestID = document.getElementById("requestID").value;
            if (cf) {
                alert("Hành động được xác nhận !");
                window.location.href = "ScheduleLeaveForRequestServlet?requestID=" + requestID;
            } else {
                alert("Hủy hành động !");
            }
        }
    </script>
</html>
