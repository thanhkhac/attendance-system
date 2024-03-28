<%-- 
  Document   : SendRequest
  Created on : Feb 19, 2024, 8:12:02 AM
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
        <script src="assets/js/moment.js"></script>

        <title>Hệ thống điểm danh</title>
        <style>
            body{
                font-family: sans-serif;
                background-color: steelblue !important;
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
            .content-note{
                margin-top: 50px;
            }
            .content-note-items{
                margin :0px;
                font-style: italic;
            }
            .content-note-items span{
                color: red;
            }
            .content-request{
                margin-top: 50px;
                padding-bottom: 30px;
                border-bottom: 1px solid grey;
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
            .content-request-body{
                margin-top: 50px;
                /*display: flex;*/
            }
            .content-request-body textArea{
                border: none;
                border-bottom: 2px dotted #0072bb;
                outline: none;
            }
            .body-span{
                width: 300px;
                min-width: 300px;
                margin-bottom: 50px;
                font-weight: 600;
                font-size: larger;
            }
            .request-input-box{
                display: flex;
                margin-bottom: 50px;
            }
            .request-input-box input{
                width: 500px;
                border: none;
                border-bottom: 2px dotted #0072bb;
                padding: 5px;
                outline: none;
            }
            .request-input-box span{
                display: block;
                width: 280px;
                min-width: 250px;
                font-weight: 600;
                font-size: medium;
                margin-right: 50px;
            }


            .request-policy{
                margin: 20px 0;
                font-style: italic;
            }
            .request-policy p{
                margin: 0;
            }
            .request-policy p:first-child{
                color: red;
            }
            #leave-request{
                display: none;
            }
            #resignation-request{
                display: none;
            }
            #iternShipConfirmation-request{
                display: none;
            }
            #overtime-request{
                display: none;
            }
            @media screen and (orientation: portrait) {
                .content{
                    max-width: 90%;
                }

            }
            @media (max-width:1450px) {
                .content{
                }
                .content-request-type select{
                    max-width: 90%;
                }
                .request-input-box{
                    display: flex;
                    flex-direction: column;
                }
                .request-input-box input{
                    max-width: 100%;
                }
                .request-input-box textArea{
                    max-width: 100%;
                }
            }
        </style>
    </head>
    <body>
        <%@include file="Sidebar.jsp" %>
        <c:set var="listTpe" value="${requestScope.listType}" />
        <c:set var="account" value="${sessionScope.ACCOUNT}" />
        <c:set var="listDepartment" value="${requestScope.listDepartment}" />
        <c:set var="listEmployeeType" value="${requestScope.listEmployeeType}" />
        <c:set var="listRole" value="${requestScope.listRole}" />

        <div class="content">
            <h1>Attendance System</h1>
            <div class="content-redirect">
                <p><a href="ThanhCong.html">Home</a> | Send Request</p>
            </div>
            <div class="content-note">
                <h1>Send A Request</h1>
                <p class="content-note-items">
                    <span>Lưu ý:</span> Khi gửi đơn, yêu cầu tới các phòng ban !
                </p>

                <p class="content-note-items">*Bộ phận xử lý đơn / yêu cầu sẽ tiếp nhận và xử lý trong vòng 24h.</p>

                <p class="content-note-items">*Để tránh SPAM, thời gian phản hồi đơn / yêu cầu sẽ được diễn ra theo quy tắc: Nếu gửi N (N&gt;1) đơn / yêu cầu
                    với cùng một mục đích thì thời gian phản hồi sẽ diễn ra trong vòng Nx48h.</p>
                <p class="content-note-items">*Vì vậy hãy cân nhắc kĩ trước khi gửi đơn / yêu cầu !</p>
                <p  class="content-note-items">Trân Trọng !</p>
            </div>
            <div class="content-request">
                <div class="content-request-type">
                    <label for="request-type">Request Type: </label>
                    <select name="requestID" id="request-type" onchange="Tranformation()">
                        <option value="0">Choose Type Of Request (Chọn Loại Yêu Cầu)</option>
                        <c:forEach items="${listType}" var="t">
                            <option id="requestTypeID" value="${t.getRequestTypeID()}">${t.getRequestTypeName()}</option>
                            </c:forEach>
                    </select>
                </div>
            </div>
    </body>
    <script>

        function Tranformation() {
            var requestTypeID = document.getElementById("request-type").value;
            
            
            console.log(requestTypeID);
            if (requestTypeID === "1") {
                window.location.href = "PrepareRequestServlet?requestTypeID=" + requestTypeID;
            } else if (requestTypeID === "2") {
                window.location.href = "PrepareRequestServlet?requestTypeID=" + requestTypeID;
            } else {
                window.location.href = "PrepareRequestServlet?requestTypeID=" + requestTypeID;
            }
        }



        function isAcceptableLeaveDate(startDate_txt, endDate_txt) {
            var startDate = moment(startDate_txt);
            var endDate = moment(endDate_txt);
            var current = moment();
            var startMonth = startDate.month() + 1;
            var startYear = startDate.year();

            var currentYear = current.year();

            var afterStartSixMonths = moment(startDate).add(6, 'months');
            var afterCurrentOneMonth = moment(current).add(1, 'month');

            console.log("StartMonth: " + startMonth);
            console.log("StartDate: " + startDate.format("MM/DD/YYYY"));
            console.log("EndDate: " + endDate.format("MM/DD/YYYY"));
            console.log("Start Date after Six Months: " + afterStartSixMonths.format("MM/DD/YYYY"));

            console.log(startDate.isSameOrBefore(current));
            console.log(endDate.isSameOrBefore(current));
            console.log(startDate.isSameOrBefore(afterCurrentOneMonth));
            console.log(endDate.isSameOrBefore(afterStartSixMonths));
            console.log(startYear <= currentYear + 1);
            console.log(startDate.isSameOrBefore(endDate));
            console.log(startDate.isSameOrBefore(endDate)
                    && startYear <= currentYear + 1
                    && startDate.isSameOrBefore(current)
                    && endDate.isSameOrBefore(current)
                    && startDate.isSameOrBefore(afterCurrentOneMonth)
                    && endDate.isSameOrBefore(afterStartSixMonths));

            if (startDate.isSameOrBefore(endDate)
                    && startYear <= currentYear + 1
                    && startDate.isSameOrAfter(current)
                    && endDate.isSameOrAfter(current)
                    && startDate.isSameOrBefore(afterCurrentOneMonth)
                    && endDate.isSameOrBefore(afterStartSixMonths)) {
                return true;
            } else {
                return false;
            }

        }

        function isAcceptableResignDate(startDate_txt, endDate_txt, extendDate_txt) {
            var startDate = moment(startDate_txt);
            var endDate = moment(endDate_txt);
            var extendDate = moment(extendDate_txt);
            var currentDate = moment();

            console.log("StartDate: " + startDate.format("DD/MM/YYYY"));
            console.log("EndDate: " + endDate.format("DD/MM/YYYY"));
            console.log("CurrentDate: " + currentDate.format("DD/MM/YYYY"));
            console.log("ExtendDate: " + extendDate.format("DD/MM/YYYY"));

            var currentAfter6Months = moment(currentDate).add(6, "months");
            var currnetAfter2Years = moment(currentDate).add(2, "years");
            if (currentDate.isSameOrBefore(endDate) //Gia han truoc khi het han
                    && extendDate.isAfter(startDate) //Ngay Gia han > start, end Date
                    && extendDate.isAfter(endDate)
                    && extendDate.isSameOrAfter(currentAfter6Months) // Gia Han Toi Thieu? 6 thang From now
                    && extendDate.isSameOrBefore(currnetAfter2Years) //Gia Han Toi Da 2 Nam From now
                    ) {
                return true;
            }
            return false;
        }


        function checkInfor() {
            var requestTypeID = document.getElementById("request-type").value;
            if (requestTypeID === "2") {
                var startDate_raw = document.getElementById("leave-startDate").value;
                var endDate_raw = document.getElementById("leave-endDate").value;
                var reason = document.getElementById("leave-reason").value;

                if (startDate_raw !== '' && endDate_raw !== '' && reason !== '') {
                    if (isAcceptableLeaveDate(startDate_raw, endDate_raw)) {
//                        let URL = "/AttendanceSystem/DispatchController?btAction=Gửi&startDate=" + startDate_raw + "&endDate=" + endDate_raw + "&reason=" + reason;
//                        window.location.href = URL;
                        document.getElementById("form-request").submit();
//                    alert("True");
                    } else {
                        alert("Thời gian nghỉ không hợp lệ !\n\Vui lòng kiểm tra lại quy tắc về xin nghỉ. ");
                    }
                } else {
                    alert("Vui Lòng Điền Đầy Đủ Thông Tin !");
                }
            } else if (requestTypeID === "3") {
                var startDate_raw = document.getElementById("resign-startDate").value;
                var endDate_raw = document.getElementById("resign-endDate").value;
                var extendDate_raw = document.getElementById("resign-extensionDate").value;
                var reason = document.getElementById("resign-reason").value;

                if (extendDate_raw !== '' && reason !== '') {
                    if (isAcceptableResignDate(startDate_raw, endDate_raw, extendDate_raw)) {
//                        let URL = "AttendanceSystem/InsertResignRequest?extendDate=" + extendDate_raw + "&reason=" + reason;
//                        window.location.href = URL;
                        alert("true");
                    } else {
                        alert("Thời gian gia hạn không hợp lệ !\n\Vui lòng kiểm tra lại quy tắc về gia hạn hợp đồng. ");
                    }
                } else {
                    alert("Vui Lòng Điền Đầy Đủ Thông Tin !");
                }
            }

        }

    </script>
</html>
