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
        
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("ACCOUNT");

        ArrayList<WorkDayDetails> workingdays = new ArrayList<>();
        for (LocalDate localDate : calendar) {
            workingdays.add(new WorkDayDetails(localDate, employeeDTO.getEmployeeID()));
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
                                <div class="date">
                                    ${wkday.date}
                                </div>
                            </div>
                            <div class="shift-block">
                                <c:if test = "${not empty wkday.timesheet}">
                                    <div class="button-modal" 
                                         data-bs-toggle="modal" data-bs-target="#ModalBtn${index}">
                                        <div class="shift text-center bg-primary" >
                                            <div class="shift__title">
                                                <div class="shift-name">${wkday.shift.name}</div>
                                                <div class="shift-status"></div>
                                                <div class="date d-none">${wkday.date}</div>
                                                <c:if test = "${not empty wkday.leave}">
                                                    <div class="leave">

                                                    </div>
                                                </c:if>
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
                                                            <h5 class="modal-title" >${wkday.shift.name}</h5>
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
                                                                        <td class="startTime"> ${wkday.shift.startTime}</td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>Giờ ra: </td>
                                                                        <td class="endTime">${wkday.shift.endTime}</td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>Chấm công vào: </td>
                                                                        <td class="checkIn">
                                                                            ${wkday.timesheet.checkin}
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>Chấm công ra: </td>
                                                                        <td class="checkOut">
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
                                    </div>
                                    <c:set var="index" value="${index + 1}"/>
                                </c:if>
                                <c:if test = "${not empty wkday.overtime}">
                                    <%--1. Tất cả các ngày: checkIn not null, checkout null: Màu cam, đã chấm công vào--%>
                                    <div class="button-modal" data-bs-toggle="modal" data-bs-target="#ModalBtn${index}">
                                        <div class="shift overtime text-center">
                                            <div class="shift__title">
                                                Tăng ca
                                            </div>
                                            <div>
                                                ${wkday.overtime.startTime} - ${wkday.overtime.endTime}
                                            </div>
                                            <div class="date d-none">${wkday.date}</div>
                                            <div class="modal fade text-dark text-start" id="ModalBtn${index}" tabindex="-1"
                                                 aria-labelledby="modalPopUp" style="display: none;"
                                                 aria-hidden="true">
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
            document.addEventListener('DOMContentLoaded', function () {
                // Get all shift elements
                let shifts = document.querySelectorAll('.shift');

                shifts.forEach(function (shift) {

                    let date = shift.querySelector('.date').textContent.trim();
                    let startTimeString = shift.querySelector('.startTime').textContent.trim();
                    let endTimeString = shift.querySelector('.endTime').textContent.trim();
                    let checkInString = shift.querySelector('.checkIn').textContent.trim();
                    let checkOutString = shift.querySelector('.checkOut').textContent.trim();
                    let shiftDate = new Date(date + ' ' + startTimeString);
                    let currentDate = new Date();
                    if (shift.querySelector('.leave')) {
                        shift.querySelectorAll('.shift-status').forEach(function (element) {
                            element.textContent = 'Nghỉ';
                        });
                        shift.classList.add('leave');
                    } else {
                        if (shiftDate > currentDate) {

                            shift.querySelectorAll('.shift-status').forEach(function (element) {
                                element.textContent = 'Chưa diễn ra';
                            });
                            shift.classList.add('notyet');
                        } else if (checkInString) {

                            if (checkOutString) {
                                shift.querySelectorAll('.shift-status').forEach(function (element) {
                                    element.textContent = 'Đã chấm công';
                                });
                                shift.classList.add('attended');
                            } else {
                                shift.querySelectorAll('.shift-status').forEach(function (element) {
                                    element.textContent = 'Đã chấm công vào';
                                });
                                shift.classList.add('onlycheckin');
                            }
                        } else {

                            shift.querySelectorAll('.shift-status').forEach(function (element) {
                                element.textContent = 'Vắng';
                            });
                            shift.classList.add('absent');
                        }
                    }

                }
                );
            });


        </script>
        <!--<script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>-->
    </body>


</html>
