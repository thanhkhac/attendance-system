<%-- 
    Document   : LeaveRequest
    Created on : Feb 23, 2024, 1:47:25 PM
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
        <title>LeaveRequest</title>
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
            .content-message {
                background-color: #f8d7da;
                border: 1px solid #f5c6cb;
                padding: 10px;
                margin: 20px 0;
                margin-bottom: 50px;
                border-radius: 5px;
            }

            .content-message p {
                color: #721c24;
                margin: 0;
                font-size: larger;
            }
            .fa-xmark {
                color: #721c24;
                margin-right: 8px;
            }
            .content-request-file{
                margin-bottom: 50px;
            }
            .content-request-file input{
                margin-left: 120px;
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
        <c:set var="requestTypeID" value="${requestScope.requestTypeID}" />
        <c:set var="startDate" value="${requestScope.startDate}" />
        <c:set var="endDate" value="${requestScope.endDate}" />
        <c:set var="reason" value="${requestScope.reason}" />
        <c:set var="err" value="${requestScope.error}" />
        <c:set var="msg" value="${requestScope.msg}" />
        <div class="content">
            <h1>Attendance System</h1>
            <div class="content-redirect">
                <p><a href="ThanhCong.html">Home</a> | Send Request</p>
            </div>
            <c:if test="${not empty msg}">
                <div class="content-message">
                    <p><i class="fa-solid fa-xmark"></i>${msg}</p>
                </div>
            </c:if>
            <div class="content-note">
                <h1>Leave Request (Đơn Xin Nghỉ Phép)</h1>
                <p class="content-note-items">
                    <span>Lưu ý:</span> Khi gửi đơn, yêu cầu tới các phòng ban !
                </p>

                <p class="content-note-items">*Bộ phận xử lý đơn / yêu cầu sẽ tiếp nhận và xử lý trong vòng 24h.</p>

                <p class="content-note-items">*Để tránh SPAM, thời gian phản hồi đơn / yêu cầu sẽ được diễn ra theo quy tắc: Nếu gửi N (N&gt;1) đơn / yêu cầu
                    với cùng một mục đích thì thời gian phản hồi sẽ diễn ra trong vòng Nx48h.</p>
                <p class="content-note-items">*Vì vậy hãy cân nhắc kĩ trước khi gửi đơn / yêu cầu !</p>
                <p  class="content-note-items">Trân Trọng !</p>

            </div>
            <form  id="form-request" action="InsertLeaveRequestServlet" method="Post" enctype="multipart/form-data">
                <div class="content-request">
                    <div class="content-request-type">
                        <label for="request-type">Request Type: </label>
                        <select name="requestID" id="request-type" onchange="Tranformation()" >
                            <c:forEach items="${listType}" var="t">
                                <option id="requestTypeID" value="${t.getRequestTypeID()}"
                                        <c:if test="${requestTypeID eq t.getRequestTypeID()}">
                                            selected=""
                                        </c:if>
                                        >${t.getRequestTypeName()}</option>
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
                                <span >Full Name (Tên tôi là): </span>
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
                                <span >Hiện Đang Là (Nhân Viên): </span>
                                <c:forEach items="${listEmployeeType}" var="type">
                                    <c:if test="${account.getEmployeeTypeID() == type.getEmployeeTypeID()}">
                                        <input readonly type="text" name="employeeType" id="employeeType" 
                                               value="${type.getName()}" >
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div class="request-input-box">
                                <span >Công Tác Tại (Phòng / Ban): </span>
                                <c:forEach items="${listDepartment}" var="de">
                                    <c:if test="${account.getDepartmentID() == de.getDepartmentID()}">
                                        <input readonly type="text" name="department" id="department" 
                                               value="${de.getName()}" >
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div class="request-input-box">
                                <span >Với Vai Trò Là (Chức Vụ): </span>
                                <c:forEach items="${listRole}" var="r">
                                    <c:if test="${account.getRoleID() == r.getRoleID()}">
                                        <input readonly type="text" name="role" id="role" 
                                               value="${r.getName()}" >
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div>
                                <div class="request-input-box">
                                    <span style="color: red">StartDate (Từ Ngày): </span>
                                    <input type="date" name="startDate" id="leave-startDate" required=""
                                           <c:if test="${startDate != null}">
                                               value="${startDate}"
                                           </c:if>
                                           >
                                </div>
                                <div class="request-input-box"
                                 <c:if test="${err.getInvalidDate_error() !=null}">
                                     style="margin-bottom: 20px;"
                                 </c:if>
                                 >
                                    <span style="color: red" >EndDate (Đến Ngày): </span>
                                    <input type="date" name="endDate" id="leave-endDate" required=""
                                           <c:if test="${endDate != null}">
                                               value="${endDate}"
                                           </c:if>
                                           >
                                </div>
                                <c:if test="${err.getInvalidDate_error() !=null}">
                                    <div class="content-message">
                                        <p><i class="fa-solid fa-xmark"></i>  ${err.getInvalidDate_error()}</p>
                                    </div>
                                </c:if>
                            </div>
                            <div class="request-input-box" style="margin-top: 50px;">
                                <span style="color: red" >Reason (Lý do): </span>
                                <c:if test="${reason.length()>0}">
                                    <textarea 
                                        name="reason" 
                                        id="leave-reason" 
                                        name="reason" 
                                        rows="5" 
                                        cols="20" 
                                        style="width: 500px; height: 140px" 
                                        required=""
                                        >${reason}</textarea>  
                                </c:if>
                                <c:if test="${reason==null}">
                                    <textarea 
                                        name="reason" 
                                        id="leave-reason" 
                                        name="reason" 
                                        rows="5" 
                                        cols="20" 
                                        style="width: 500px; height: 140px" 
                                        required=""
                                        ></textarea>
                                </c:if>
                            </div>
                            <div class="content-request-file">
                                <span class="body-span">File đính kèm (nếu có):</span>
                                <input type="file" name="file" id="leave-file">
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
            console.log(requestTypeID);
            if (requestTypeID === "1") {
                window.location.href = "PrepareRequestServlet?requestTypeID=" + requestTypeID;
            } else if (requestTypeID === "2") {
                window.location.href = "PrepareRequestServlet?requestTypeID=" + requestTypeID;
            } else {
                window.location.href = "PrepareRequestServlet?requestTypeID=" + requestTypeID;
            }
        }
    </script>
</html>
