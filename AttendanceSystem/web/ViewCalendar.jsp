<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.time.*"%>
<%@page import="model.TimesheetDAO"%>
<%@page import="model.*"%>
<%@page import="control.workday.*"%>
<%@page import="ultility.datetimeutil.DateTimeUtil"%>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Hệ thống điểm danh</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/view-calendar.css">
        <style>
        </style>
    </head>
    <%
        //DateTimeUtil dateTimeUtil = new DateTimeUtil();
//
        //int year = dateTimeUtil.getVNLocalDateNow().getYear();
        //int month = dateTimeUtil.getVNLocalDateNow().getMonthValue();
//
        //String txtyear = request.getParameter("year");
        //String txtmonth = request.getParameter("month");
//
        //if (txtyear != null || txtmonth != null) {
        //    try {
        //        year = Integer.parseInt(txtyear);
        //        month = Integer.parseInt(txtmonth);
        //    } catch (Exception e) {
        //        e.printStackTrace();
        //    }
        //    try {
        //        month = Integer.parseInt(txtmonth);
        //    } catch (Exception e) {
        //        e.printStackTrace();
        //    }
        //}
        //
        //
        //TimesheetDAO tsDAO = new TimesheetDAO();
        //ArrayList<LocalDate> calendar = dateTimeUtil.getCalendar(year, month);
        //
        //EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");
//
        //ArrayList<WorkDayDetails> workingdays = new ArrayList<>();
        //for (LocalDate localDate : calendar) {
        //    workingdays.add(new WorkDayDetails(localDate, employeeDTO.getEmployeeID()));
        //}
        //
        //HashMap<Integer, ShiftDTO> shiftMap = new ShiftDAO().getAllShiftHashMap();
        //LocalDate today = dateTimeUtil.getVNLocalDateNow();
        //request.setAttribute("workingdays", workingdays);
        //request.setAttribute("today", today);
        //request.setAttribute("shiftMap", shiftMap);
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
                                <c:if test = "${wkday.date eq today}">
                                    <div class="date text-danger" >
                                        Hôm nay
                                    </div>
                                </c:if>
                                <c:if test = "${wkday.date ne today}">
                                    <div class="date">
                                        ${wkday.date}
                                    </div>
                                </c:if>
                            </div>
                            <div class="shift-block">
                                <c:forEach var="timesheet" items="${wkday.timesheet}" varStatus="counter">
                                    <div class="shift text-center bg-primary" >
                                        <div class="button-modal" 
                                             data-bs-toggle="modal" data-bs-target="#ModalBtn${index}">
                                            <div class="shift__title">
                                                <div class="shift-name">${shiftMap[timesheet.shiftID].name}</div>
                                                <div class="shift-status"></div>
                                                <div class="date d-none">${wkday.date}</div>
                                                <c:if test = "${not empty wkday.leave}">
                                                    <div class="leave">
                                                    </div>
                                                </c:if>
                                            </div>
                                            <div class="shift-time">
                                                ${shiftMap[timesheet.shiftID].startTime} - ${shiftMap[timesheet.shiftID].endTime}
                                            </div>
                                        </div>
                                        <div class="modal fade text-dark text-start" id="ModalBtn${index}" tabindex="-1"
                                             aria-labelledby="staticBackdropLabel" style="display: none;"
                                             aria-hidden="true"
                                             data-bs-backdrop="static">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" >${shiftMap[timesheet.shiftID].name}</h5>
                                                        <button type="button" class="btn-close"
                                                                data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <table>
                                                            <tbody>
                                                                <tr>
                                                                    <td>Tình trạng </td>  
                                                                    <td class="shift-status"></td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Giờ vào:</td>
                                                                    <td class="startTime"> ${requestScope.shiftMap[timesheet.shiftID].startTime}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Giờ ra: </td>
                                                                    <td class="endTime">${requestScope.shiftMap[timesheet.shiftID].endTime}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Chấm công vào: </td>
                                                                    <td class="checkIn">
                                                                        ${timesheet.checkin}
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Chấm công ra: </td>
                                                                    <td class="checkOut">
                                                                        ${timesheet.checkout}
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
                                    <c:set var="index" value="${index + 1}"/>
                                </c:forEach>

                                <c:if test = "${not empty wkday.overtime}">
                                    <%--1. Tất cả các ngày: checkIn not null, checkout null: Màu cam, đã chấm công vào--%>
                                    <div class="shift overtime text-center">
                                        <div class="button-modal" data-bs-toggle="modal" data-bs-target="#ModalBtn${index}">
                                            <div class="shift__title">
                                                Tăng ca
                                            </div>
                                            <div class="shift-status"></div>
                                            <div>
                                                ${wkday.overtime.startTime} - ${wkday.overtime.endTime}
                                            </div>
                                        </div>
                                        <div class="date d-none">${wkday.date}</div>
                                        <div class="modal fade text-dark text-start" id="ModalBtn${index}" tabindex="-1"
                                             aria-labelledby="staticBackdropLabel" style="display: none;"
                                             aria-hidden="true"
                                             data-bs-backdrop="static">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">Tăng ca</h5>
                                                        <button type="button" class="btn-close"
                                                                data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <table>
                                                            <tbody>
                                                                <tr>
                                                                    <td>Tình trạng </td>           
                                                                    <td class="shift-status"></td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Giờ vào:</td>
                                                                    <td class="startTime"> ${wkday.overtime.startTime}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Giờ ra: </td>
                                                                    <td class="endTime">${wkday.overtime.endTime}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Chấm công vào: </td>
                                                                    <td class="checkIn">
                                                                        ${wkday.overtime.checkIn}
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Chấm công ra: </td>
                                                                    <td class="checkOut">
                                                                        ${wkday.overtime.checkOut}
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Cổng chấm công mở trước: </td>
                                                                    <td class="opentAt">
                                                                        ${wkday.overtime.openAt}
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Cổng chấm công đóng sau: </td>
                                                                    <td class="closeAt">
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
        <script>


        </script>
        <!--<script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>-->
    </body>


</html>