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
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <script src="assets/Bootstrap5/js/bootstrap.min.js"></script>
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <script src="https://momentjs.com/downloads/moment.min.js"></script>

        <title>JSP Page</title>
        <style>
            body{
                font-family: sans-serif;
                background-color: steelblue;
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
            .content-request-file{
                margin-top: 50px;
                display: flex;
                max-height: 50px;
            }
            .content-request-file input{
                display: none;
            }
            .content-request-file label{
                padding: 10px;
                background-color: white;
                border-radius: 5px;
                color: #0072bb;
                border: 3px solid #0072bb;
                font-weight: 600;
                font-size: large;
                cursor: pointer;
            }
            .content-request-file label:hover{
                background-color: #0072bb;
                color: white;
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
        </style>
    </head>
    <body>
        <c:set var="listTpe" value="${requestScope.listType}" />
        <c:set var="account" value="${sessionScope.ACCOUNT}" />
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

                <p class="content-note-items">Bộ phận xử lý đơn / yêu cầu sẽ tiếp nhận và xử lý trong vòng 24h.</p>

                <p class="content-note-items">Để tránh SPAM, thời gian phản hồi đơn / yêu cầu sẽ được diễn ra theo quy tắc: Nếu gửi N (N&gt;1) đơn / yêu cầu
                    với cùng một mục đích thì thời gian phản hồi sẽ diễn ra trong vòng Nx48h.</p>
                <p class="content-note-items">Vì vậy hãy cân nhắc kĩ trước khi gửi đơn / yêu cầu !</p>
                <p  class="content-note-items">Trân Trọng !</p>

            </div>
            <form action="DispatchController" method="Post">
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
                    <div class="content-request-body" id="request-body">
                        <div id="leave-request">
                            <div class="request-policy">
                                <p>Quy định ngày nghỉ : </p>
                                <p>Năm: xử lý tối đa trong 1 năm tới.</p>
                                <p>Tháng: Nghỉ tối đa 6 tháng (Thai Sản).</p>
                                <p>Ngày: Bắt đầu từ 1 tháng sau trở về thời điểm hiện tại.</p>
                            </div>
                            <div class="request-input-box">
                                <span >Full Name (Họ Và Tên): </span>
                                <input readonly type="text" name="fullName" id="name" value="${account.getLastName()} ${account.getMiddleName()} ${account.getFirstName()} ">
                            </div>
                            <div class="request-input-box">
                                <span >Email: </span>
                                <input readonly type="text" name="email" id="email" value="${account.getEmail()}">
                            </div>
                            <div class="request-input-box">
                                <span >Phone(Số Điện Thoại): </span>
                                <input readonly type="text" name="phoneNumber" id="phoneNumber" value="${account.getPhoneNumber()}">
                            </div>
                            <div class="request-input-box">
                                <span >StartDate (Từ Ngày): </span>
                                <input type="date" name="startDate" id="startDate" required="">
                            </div>
                            <div class="request-input-box">
                                <span >EndDate (Đến Ngày): </span>
                                <input type="date" name="endDate" id="endDate" required="">
                            </div>
                            <div class="request-input-box">
                                <span >Reason (Lý do): </span>
                                <textarea name="reason" id="reason" name="reason" rows="5" cols="20" style="width: 500px; height: 140px" required=""></textarea>
                            </div>
                            <div class="content-request-file">
                                <span class="body-span">File đính kèm (nếu có):</span>
                                <label for="file">Upload File Here | <i class="fa-solid fa-arrow-up-from-bracket" aria-hidden="true"></i></label> 
                                <input type="file" name="file" id="file">
                            </div>
                            <input onclick="checkInfor()" class="btn btn-success" type="submit" name="btAction" value="Gửi">

                        </div>
                        <div id="resignation-request">
                            <div class="request-input-box">
                                <span >Full Name (Họ Và Tên): </span>
                                <input readonly type="text" name="fullName" id="name" value="${account.getLastName()} ${account.getMiddleName()} ${account.getFirstName()} ">
                            </div>
                            <div class="request-input-box">
                                <span >Email: </span>
                                <input readonly type="text" name="email" id="email" value="${account.getEmail()}">
                            </div>
                            <div class="request-input-box">
                                <span >Phone(Số Điện Thoại): </span>
                                <input readonly type="text" name="phoneNumber" id="phoneNumber" value="${account.getPhoneNumber()}">
                            </div>
                            <div class="request-input-box">
                                <span >StartDate (Từ Ngày): </span>
                                <input type="date" name="startDate" id="startDate" required="">
                            </div>
                            <div class="request-input-box">
                                <span >EndDate (Đến Ngày): </span>
                                <input type="date" name="endDate" id="endDate" required="">
                            </div>
                            <div class="request-input-box">
                                <span >Reason (Lý do): </span>
                                <textarea  name="reason" id="reason" name="reason" rows="5" cols="20" style="width: 500px; height: 140px" required=""></textarea>
                            </div>
                            <div class="content-request-file">
                                <span class="body-span">File đính kèm (nếu có):</span>
                                <label for="file">Upload File Here | <i class="fa-solid fa-arrow-up-from-bracket" aria-hidden="true"></i></label> 
                                <input type="file" name="file" id="file">
                            </div>
                            <input onclick="checkInfor()" class="btn btn-success" type="submit" name="btAction" value="Gửi">
                        </div>
                        <div id="iternShipConfirmation-request">
                            <div class="request-input-box">
                                <span >Full Name (Họ Và Tên): </span>
                                <input readonly type="text" name="fullName" id="name" value="${account.getLastName()} ${account.getMiddleName()} ${account.getFirstName()} ">
                            </div>
                            <div class="request-input-box">
                                <span >Email: </span>
                                <input readonly type="text" name="email" id="email" value="${account.getEmail()}">
                            </div>
                            <div class="request-input-box">
                                <span >Phone(Số Điện Thoại): </span>
                                <input readonly type="text" name="phoneNumber" id="phoneNumber" value="${account.getPhoneNumber()}">
                            </div>
                            <div class="request-input-box">
                                <span >Number (Số Lượng): </span>
                                <input type="number" name="number" id="number" min="1" max="20" value="1">
                            </div>

                            <div class="request-input-box">
                                <span >Reason (Lý do): </span>
                                <textarea name="reason" id="reason" name="reason" rows="5" cols="20" style="width: 500px; height: 140px"  required=""></textarea>
                            </div>
                            <div class="content-request-file">
                                <span class="body-span">File đính kèm (nếu có):</span>
                                <label for="file">Upload File Here | <i class="fa-solid fa-arrow-up-from-bracket" aria-hidden="true"></i></label> 
                                <input type="file" name="file" id="file">
                            </div>
                            <input onclick="checkInfor()" class="btn btn-success" type="submit" name="btAction" value="Gửi">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </body>
    <script>

        function Tranformation() {
            var requestTypeID = document.getElementById("request-type").value;
            var requestBody = document.getElementById("request-body");
            var leave_requestHTML = document.getElementById("leave-request");
            var resignation_requestHTML = document.getElementById("resignation-request");
            var intern_requestHTML = document.getElementById("iternShipConfirmation-request");

            resignation_requestHTML.style.display = 'none';
            leave_requestHTML.style.display = 'none';
            intern_requestHTML.style.display = 'none';
            console.log(requestTypeID);
            if (requestTypeID === "1") {
                leave_requestHTML.style.display = 'block';
            } else if (requestTypeID === "2") {
                resignation_requestHTML.style.display = 'block';
            } else if (requestTypeID === "4") {
                intern_requestHTML.style.display = 'block';
            }
        }



        function isAcceptableDate(startDate_txt, endDate_txt) {
            var startDate = moment(startDate_txt);
            var endDate = moment(endDate_txt);
            var current = moment();
            var startMonth = startDate.month() + 1;
            var startYear = startDate.year();

            var endMonth = endDate.month() + 1;
            var endYear = endDate.year();

            var currentMonth = current.month() + 1;
            var currentYear = current.year();

            var afterSixMonths = moment(startDate).add(6, 'months');
            var afterOneMonth = moment(current).add(1, 'month');

            console.log("StartMonth: " + startMonth);
            console.log("StartDate: " + startDate.format("MM/DD/YYYY"));
            console.log("EndDate: " + endDate.format("MM/DD/YYYY"));
            console.log("Start Date after Six Months: " + afterSixMonths.format("MM/DD/YYYY"));
            
            console.log(endDate.isSameOrBefore(afterSixMonths));
            console.log(startDate.isSameOrBefore(afterOneMonth));
            if (endDate.isSameOrAfter(startDate)
                    && startYear <= currentYear + 1
                    && startDate.isSameOrAfter(current)
                    && endDate.isSameOrAfter(current)
                    && startDate.isSameOrBefore(afterOneMonth)
                    && endDate.isSameOrBefore(afterSixMonths)) {
                return true;
            }

            return false;
        }



        function checkInfor() {
            var startDate_raw = document.getElementById("startDate").value;
            var endDate_raw = document.getElementById("endDate").value;
            var reason = document.getElementById("reason").value;
            if (startDate_raw !== '' && endDate_raw !== '' && reason !== '') {
                if (isAcceptableDate(startDate_raw, endDate_raw)) {
                    let URL = "/AttendanceSystem/InsertLeaveRequestServlet?startDate=" + startDate_raw + "&endDate=" + endDate_raw + "&reason=" + reason;
                    window.location.href = URL;
//                    alert("True");
                } else {
                    alert("Thời gian nghỉ không hợp lệ !\n\Vui lòng kiểm tra lại quy tắc về xin nghỉ. ");
                }
            } else {
                alert("Vui Lòng Điền Đầy Đủ Thông Tin !");
            }
        }

    </script>
</html>
