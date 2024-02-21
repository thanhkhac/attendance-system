<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.time.*"%>
<%@page import="model.TimesheetDAO"%>
<%@page import="model.EmployeeDTO"%>
<%@page import="control.workday.*"%>
<%@page import="ultility.datetimeutil.DateTimeUtil"%>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/view-calendar.css">
        <style>
        </style>
    </head>
    <%
        DateTimeUtil dateTimeUtil = new DateTimeUtil();

        int year = dateTimeUtil.getVNLocalDateNow().getYear();
        int month = dateTimeUtil.getVNLocalDateNow().getMonthValue();

        String txtyear = request.getParameter("year");
        String txtmonth = request.getParameter("month");
        String txtEmployeeID = request.getParameter("employeeID");
        
        int employeeID = Integer.parseInt(txtEmployeeID);
        if (txtyear != null || txtmonth != null) {
            try {
                year = Integer.parseInt(txtyear);
                month = Integer.parseInt(txtmonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                month = Integer.parseInt(txtmonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        TimesheetDAO tsDAO = new TimesheetDAO();
        ArrayList<LocalDate> calendar = dateTimeUtil.getCalendar(year, month);
        
        

        ArrayList<WorkDayDetails> workingdays = new ArrayList<>();
        for (LocalDate localDate : calendar) {
            workingdays.add(new WorkDayDetails(localDate, employeeID));
        }
        LocalDate today = dateTimeUtil.getVNLocalDateNow();
        request.setAttribute("workingdays", workingdays);
        request.setAttribute("today", today);
    %>
    <body>
        <div class="container">
            <table class="table mytable">
                <thead>
                    <tr class="text-center ">
                        <th>SUN</th>
                        <th>MON</th>
                        <th>TUE</th>
                        <th>WED</th>
                        <th>THUS</th>
                        <th>FRI</th>
                        <th>SAT</th>
                    </tr>
                </thead>
                <c:set var="index" value="${0}"/>
                <c:forEach var="wkday" items="${requestScope.workingdays}" varStatus="counter">
                    <c:if test="${counter.index % 7 == 0}">
                        <tr>
                        </c:if>
                        <td class="mytable-td">
                            <div class="date-block">
                                <c:if test = "${wkday.date eq requestScope.today}">
                                    <div class="date text-danger" id="today">
                                        Hôm nay
                                    </div>
                                </c:if>
                                <c:if test = "${wkday.date  ne requestScope.today}">
                                    <div class="date">
                                        ${wkday.date}
                                    </div>
                                </c:if>
                            </div>
                            <div class="shift-block">
                                <c:if test = "${not empty wkday.timesheet}">
                                    <div class="button-modal" data-bs-toggle="modal" data-bs-target="#ModalBtn${index}">
                                        <c:choose>
                                            <%--1. Tất cả các ngày: là ngày nghỉ: Đen, nghỉ--%>
                                            <c:when test = "${not empty wkday.leave}">
                                                <div class="shift leave text-center">
                                                    <div class="shift__title">
                                                        ${wkday.shift.name} <br>
                                                        (Nghỉ)
                                                    </div>
                                                    <div>
                                                        ${wkday.shift.startTime} - ${wkday.shift.endTime}
                                                    </div>
                                                    <div class="modal fade text-dark text-start" id="ModalBtn${index}" tabindex="-1"
                                                         aria-labelledby="modalPopUp" style="display: none;"
                                                         aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="exampleModalLabel"> ${wkday.shift.name}</h5>
                                                                    <button type="button" class="btn-close"
                                                                            data-bs-dismiss="modal" aria-label="Close"></button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <table>
                                                                        <tbody>
                                                                            <tr>
                                                                                <td>Tình trạng </td>
                                                                                <td>Nghỉ</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ vào:</td>
                                                                                <td> ${wkday.shift.startTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ ra: </td>
                                                                                <td>${wkday.shift.endTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công vào: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.timesheet.checkin}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.timesheet.checkin}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công ra: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.timesheet.checkout}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.timesheet.checkout}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Người tạo:
                                                                                </td>

                                                                                <td>${wkday.leaveResponed.lastName} ${wkday.leaveResponed.middleName} ${wkday.leaveResponed.firstName}</td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary"
                                                                            data-bs-dismiss="modal">Close</button>

                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <%--2. Tất cả các ngày: checkin not null, checkout null: Màu cam, đã chấm công vào--%>
                                            <c:when test = "${not empty wkday.timesheet.checkin and empty wkday.timesheet.checkout}">
                                                <div class="shift onlycheckin text-center">
                                                    <div class="shift__title">
                                                        ${wkday.shift.name} <br>
                                                        (Chấm công vào)
                                                    </div>
                                                    <div>
                                                        ${wkday.shift.startTime} - ${wkday.shift.endTime}
                                                    </div>
                                                    <div class="modal fade text-dark text-start" id="ModalBtn${index}" tabindex="-1"
                                                         aria-labelledby="modalPopUp" style="display: none;"
                                                         aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="exampleModalLabel"> ${wkday.shift.name}</h5>
                                                                    <button type="button" class="btn-close"
                                                                            data-bs-dismiss="modal" aria-label="Close"></button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <table>
                                                                        <tbody>
                                                                            <tr>
                                                                                <td>Tình trạng </td>
                                                                                <td>Đã chấm công vào</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ vào:</td>
                                                                                <td> ${wkday.shift.startTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ ra: </td>
                                                                                <td>${wkday.shift.endTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công vào: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.timesheet.checkin}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.timesheet.checkin}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công ra: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.timesheet.checkout}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.timesheet.checkout}
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary"
                                                                            data-bs-dismiss="modal">Close</button>

                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <%--3. Tất cả các ngày: checkin not null, checkout not null: màu xanh lá, đã chấm công--%>
                                            <c:when test = "${not empty wkday.timesheet.checkin and not empty wkday.timesheet.checkout}">
                                                <div class="shift attended text-center">
                                                    <div class="shift__title">
                                                        ${wkday.shift.name}
                                                    </div>
                                                    <div>
                                                        ${wkday.shift.startTime} - ${wkday.shift.endTime}
                                                    </div>
                                                    <div class="modal fade text-dark text-start" id="ModalBtn${index}" tabindex="-1"
                                                         aria-labelledby="modalPopUp" style="display: none;"
                                                         aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="exampleModalLabel"> ${wkday.shift.name}</h5>
                                                                    <button type="button" class="btn-close"
                                                                            data-bs-dismiss="modal" aria-label="Close"></button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <table>
                                                                        <tbody>
                                                                            <tr>
                                                                                <td>Tình trạng </td>
                                                                                <td>Đã chấm công</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ vào:</td>
                                                                                <td> ${wkday.shift.startTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ ra: </td>
                                                                                <td>${wkday.shift.endTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công vào: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.timesheet.checkin}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.timesheet.checkin}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công ra: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.timesheet.checkout}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.timesheet.checkout}
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary"
                                                                            data-bs-dismiss="modal">Close</button>

                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <%--4. Các ngày trước: checkin null và checkout null: Màu đỏ, Vắng --%>
                                            <c:when test = "${wkday.date < today and empty wkday.timesheet.checkin and empty wkday.timesheet.checkout}">
                                                <div class="shift absent text-center">
                                                    <div class="shift__title">
                                                        ${wkday.shift.name} <br>
                                                        (Vắng)
                                                    </div>
                                                    <div>
                                                        ${wkday.shift.startTime} - ${wkday.shift.endTime}
                                                    </div>
                                                    <div class="modal fade text-dark text-start" id="ModalBtn${index}" tabindex="-1"
                                                         aria-labelledby="modalPopUp" style="display: none;"
                                                         aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="exampleModalLabel"> ${wkday.shift.name}</h5>
                                                                    <button type="button" class="btn-close"
                                                                            data-bs-dismiss="modal" aria-label="Close"></button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <table>
                                                                        <tbody>
                                                                            <tr>
                                                                                <td>Tình trạng </td>
                                                                                <td>Vắng</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ vào:</td>
                                                                                <td> ${wkday.shift.startTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ ra: </td>
                                                                                <td>${wkday.shift.endTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công vào: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.timesheet.checkin}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.timesheet.checkin}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công ra: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.timesheet.checkout}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.timesheet.checkout}
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary"
                                                                            data-bs-dismiss="modal">Close</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <%--5. Các ngày sau hoặc hôm nay: checkin null và checkout null: màu xanh dương, chưa diễn ra --%>
                                            <c:when test = "${wkday.date > today and empty wkday.timesheet.checkin and empty wkday.timesheet.checkout}">
                                                <div class="shift notyet text-center">
                                                    <div class="shift__title">
                                                        ${wkday.shift.name} <br>
                                                        (Chưa diễn ra)
                                                    </div>
                                                    <div>
                                                        ${wkday.shift.startTime} - ${wkday.shift.endTime}
                                                    </div>
                                                    <div class="modal fade text-dark text-start" id="ModalBtn${index}" tabindex="-1"
                                                         aria-labelledby="modalPopUp" style="display: none;"
                                                         aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="exampleModalLabel"> ${wkday.shift.name}</h5>
                                                                    <button type="button" class="btn-close"
                                                                            data-bs-dismiss="modal" aria-label="Close"></button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <table>
                                                                        <tbody>
                                                                            <tr>
                                                                                <td>Tình trạng </td>
                                                                                <td>Chưa diễn ra</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ vào:</td>
                                                                                <td> ${wkday.shift.startTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ ra: </td>
                                                                                <td>${wkday.shift.endTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công vào: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.timesheet.checkin}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.timesheet.checkin}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công ra: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.timesheet.checkout}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.timesheet.checkout}
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary"
                                                                            data-bs-dismiss="modal">Close</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:when>
                                        </c:choose>
                                        <c:set var="index" value="${index + 1}"/>
                                    </div>
                                </c:if>
                                <c:if test = "${not empty wkday.overtime}">
                                    <c:choose>
                                        <%--1. Tất cả các ngày: checkIn not null, checkout null: Màu cam, đã chấm công vào--%>
                                        <c:when test = "${not empty wkday.overtime.checkIn and empty wkday.overtime.checkOut }">
                                            <div class="button-modal" data-bs-toggle="modal" data-bs-target="#ModalBtn${index}">
                                                <div class="shift overtime onlycheckin text-center">
                                                    <div class="shift__title">
                                                        Tăng ca <br>
                                                        (Chấm công vào)
                                                    </div>
                                                    <div>
                                                        ${wkday.overtime.startTime} - ${wkday.overtime.endTime}
                                                    </div>
                                                    <div class="modal fade text-dark text-start" id="ModalBtn${index}" tabindex="-1"
                                                         aria-labelledby="modalPopUp" style="display: none;"
                                                         aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="exampleModalLabel">Tăng ca</h5>
                                                                    <button type="button" class="btn-close"
                                                                            data-bs-dismiss="modal" aria-label="Close"></button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <table>
                                                                        <tbody>
                                                                            <tr>
                                                                                <td>Tình trạng </td>
                                                                                <td>Đã chấm công vào</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ vào:</td>
                                                                                <td> ${wkday.overtime.startTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ ra: </td>
                                                                                <td>${wkday.overtime.endTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công vào: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.checkIn}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.checkIn}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công ra: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.checkOut}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.checkOut}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Cổng chấm công mở trước: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.openAt}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.openAt}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Cổng chấm công đóng sau: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.closeAt}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.closeAt}
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary"
                                                                            data-bs-dismiss="modal">Close</button>

                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:when>
                                        <%--2. Tất cả các ngày: checkIn not null, checkOut not null: màu xanh lá, đã chấm công--%>
                                        <c:when test = "${not empty wkday.overtime.checkIn and not empty wkday.overtime.checkOut }">
                                            <div class="button-modal" data-bs-toggle="modal" data-bs-target="#ModalBtn${index}">
                                                <div class="shift overtime attended text-center">
                                                    <div class="shift__title">
                                                        Tăng ca <br>
                                                        (Đã chấm công)
                                                    </div>
                                                    <div>
                                                        ${wkday.overtime.startTime} - ${wkday.overtime.endTime}
                                                    </div>
                                                    <div class="modal fade text-dark text-start" id="ModalBtn${index}" tabindex="-1"
                                                         aria-labelledby="modalPopUp" style="display: none;"
                                                         aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="exampleModalLabel">Tăng ca</h5>
                                                                    <button type="button" class="btn-close"
                                                                            data-bs-dismiss="modal" aria-label="Close"></button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <table>
                                                                        <tbody>
                                                                            <tr>
                                                                                <td>Tình trạng </td>
                                                                                <td>Đã chấm công</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ vào:</td>
                                                                                <td> ${wkday.overtime.startTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ ra: </td>
                                                                                <td>${wkday.overtime.endTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công vào: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.checkIn}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.checkIn}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công ra: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.checkOut}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.checkOut}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Cổng chấm công mở trước: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.openAt}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.openAt}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Cổng chấm công đóng sau: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.closeAt}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.closeAt}
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary"
                                                                            data-bs-dismiss="modal">Close</button>

                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:when>
                                        <%--3. Các ngày trước: checkin null và checkout null: màu đỏ, vắng--%>
                                        <c:when test = "${wkday.date < today and empty wkday.overtime.checkIn and empty wkday.overtime.checkOut }">
                                            <div class="button-modal" data-bs-toggle="modal" data-bs-target="#ModalBtn${index}">
                                                <div class="shift overtime absent text-center">
                                                    <div class="shift__title">
                                                        Tăng ca
                                                        (Vắng)
                                                    </div>
                                                    <div>
                                                        ${wkday.overtime.startTime} - ${wkday.overtime.endTime}
                                                    </div>
                                                    <div class="modal fade text-dark text-start" id="ModalBtn${index}" tabindex="-1"
                                                         aria-labelledby="modalPopUp" style="display: none;"
                                                         aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="exampleModalLabel">Tăng ca</h5>
                                                                    <button type="button" class="btn-close"
                                                                            data-bs-dismiss="modal" aria-label="Close"></button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <table>
                                                                        <tbody>
                                                                            <tr>
                                                                                <td>Tình trạng </td>
                                                                                <td>Vắng</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ vào:</td>
                                                                                <td> ${wkday.overtime.startTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ ra: </td>
                                                                                <td>${wkday.overtime.endTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công vào: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.checkIn}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.checkIn}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công ra: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.checkOut}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.checkOut}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Cổng chấm công mở trước: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.openAt}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.openAt}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Cổng chấm công đóng sau: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.closeAt}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.closeAt}
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary"
                                                                            data-bs-dismiss="modal">Close</button>

                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:when>
                                        <%--4. Các ngày sau hoặc hôm nay: checkin null và checkout null: màu xanh dương, chưa diễn ra--%>
                                        <c:when test = "${wkday.date >= today and empty wkday.overtime.checkIn and empty wkday.overtime.checkOut }">
                                            <div class="button-modal" data-bs-toggle="modal" data-bs-target="#ModalBtn${index}">
                                                <div class="shift overtime absent text-center">
                                                    <div class="shift__title">
                                                        Tăng ca
                                                        (Chưa diễn ra)
                                                    </div>
                                                    <div>
                                                        ${wkday.overtime.startTime} - ${wkday.overtime.endTime}
                                                    </div>
                                                    <div class="modal fade text-dark text-start" id="ModalBtn${index}" tabindex="-1"
                                                         aria-labelledby="modalPopUp" style="display: none;"
                                                         aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="exampleModalLabel">Tăng ca</h5>
                                                                    <button type="button" class="btn-close"
                                                                            data-bs-dismiss="modal" aria-label="Close"></button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <table>
                                                                        <tbody>
                                                                            <tr>
                                                                                <td>Tình trạng </td>
                                                                                <td>Chưa diễn ra</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ vào:</td>
                                                                                <td> ${wkday.overtime.startTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Giờ ra: </td>
                                                                                <td>${wkday.overtime.endTime}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công vào: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.checkIn}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.checkIn}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Chấm công ra: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.checkOut}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.checkOut}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Cổng chấm công mở trước: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.openAt}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.openAt}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>Cổng chấm công đóng sau: </td>
                                                                                <td>
                                                                                    <c:if test = "${empty wkday.overtime.closeAt}">
                                                                                        Chưa có thông tin
                                                                                    </c:if>
                                                                                    ${wkday.overtime.closeAt}
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary"
                                                                            data-bs-dismiss="modal">Close</button>

                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:when>
                                    </c:choose>


                                    <c:set var="index" value="${index + 1}"/>
                                </c:if>
                            </div>
                        </td>
                        <c:if test="${(counter.index + 1) % 7 == 0}">
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </div>

        <!--<script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>-->
    </body>


</html>
