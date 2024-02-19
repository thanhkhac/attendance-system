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
                display: flex;
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
            #leave-request{
                /*display: none;*/
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
            <div class="content-request">
                <form action="http://localhost:8080/AttendanceSystem/DispatchController" method="Post">
                    <div class="content-request-type">
                        <label for="request-type">Request Type: </label>
                        <select id="request-type">
                            <option value="0">Choose Type Of Request (Chọn Loại Yêu Cầu)</option>
                            <c:forEach items="${listType}" var="t">
                                <option value="${t.getRequestTypeID()}">${t.getRequestTypeName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="content-request-body" id="requset-body">
                        <!--                        <span class="body-span">Lý Do: </span> 
                                                <textarea id="reason" name="reason" rows="5" cols="20" required="" style="width: 800px; height: 140px">                           
                                                </textarea>-->
                        <div id="leave-request">
                            <div class="request-input-box">
                                <span >Full Name (Họ Và Tên): </span>
                                <input type="text" name="fullName" id="name" value="${account.getLastName()} ${account.getMiddleName()} ${account.getFirstName()} ">
                            </div>
                            <div class="request-input-box">
                                <span >Email: </span>
                                <input type="text" name="email" id="email" value="${account.getEmail()}">
                            </div>
                            <div class="request-input-box">
                                <span >Phone(Số Điện Thoại): </span>
                                <input type="text" name="phoneNumber" id="phoneNumber" value="${account.getPhoneNumber()}">
                            </div>
                            <div class="request-input-box">
                                <span >StartDate (Ngày Bắt Đầu): </span>
                                <input type="date" name="startDate" id="startDate">
                            </div>
                            <div class="request-input-box">
                                <span >EndDate (Ngày Kết Thúc): </span>
                                <input type="date" name="endDate" id="endDate">
                            </div>
                            <div class="request-input-box">
                                <span >Reason (Lý do): </span>
                                <textarea id="reason" name="reason" rows="5" cols="20" style="width: 500px; height: 140px"></textarea>
                            </div>

                        </div>

                    </div>
                    <div class="content-request-file">
                        <span class="body-span">File đính kèm (nếu có):</span>
                        <label for="file">Upload File Here | <i class="fa-solid fa-arrow-up-from-bracket" aria-hidden="true"></i></label> 
                        <input type="file" name="file" id="file">
                    </div>
                    <input class="btn btn-success" type="submit" name="sendRequest" value="Gửi">
                </form>
            </div>

        </div>
    </body>
</html>
