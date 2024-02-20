<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.time.*"%>
<%@page import="model.*"%>
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
        
        
        ArrayList<LocalDate> calendar = dateTimeUtil.getCalendar(year, month);
        ArrayList<ShiftDTO> shiftList = new ShiftDAO().getActiveShiftDTO();
        LocalDate today = dateTimeUtil.getVNLocalDateNow();
        request.setAttribute("today", today);
        request.setAttribute("calendar", calendar);
        request.setAttribute("month", month);
        request.setAttribute("year", year);
        request.setAttribute("shiftList", shiftList);
    %>
    <body>
        <div class="container">
            <form action="DispatchController" id="myForm" method="POST">
                <input type="hidden" name="month" value="${month}">
                <input type="hidden" name="year" value="${year}">
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
                    <c:forEach var="wkday" items="${requestScope.calendar}" varStatus="counter">
                        <c:if test="${counter.index % 7 == 0}">
                            <tr>
                            </c:if>
                            <td class="mytable-td" style="height: 5rem">
                                <div class="date-block" style="border-bottom: 0px">
                                    <c:if test = "${wkday eq requestScope.today}">
                                        <div class="date text-danger" id="today">
                                            Hôm nay
                                        </div>
                                    </c:if>
                                    <c:if test = "${wkday  ne requestScope.today}">
                                        <div class="date">
                                            ${wkday}
                                        </div>
                                    </c:if>
                                </div>
                                <div class="shift-block">
                                    <c:if test = "${wkday > requestScope.today and wkday.getMonthValue() == month}">
                                        <select class="form-select shift--select" name="shift" id="shift">
                                            <option value="no">Nghỉ</option>
                                            <c:forEach var="shift" items="${shiftList}" varStatus="counter">
                                                <option value="${wkday}#${shift.shiftID}">${shift.name}</option>
                                            </c:forEach>
                                        </select>
                                    </c:if>
                                </div>
                            </td>
                            <c:if test="${(counter.index + 1) % 7 == 0}">
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
                <div class="text-center">
                    <button  type="button" class="btn btn-primary" onclick="validateForm()">Tiếp tục</button>
                    <input type="hidden" name="btAction" value="GetUnscheduleEmployees">
                </div>
            </form>
        </div>

        <!--<script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>-->
        <script>
            function validateForm() {

                var selects = document.querySelectorAll('.shift--select');


                var atLeastOneSelected = Array.from(selects).some(function (select) {
                    return select.value !== 'no';
                });
                console.log(atLeastOneSelected);

                if (!atLeastOneSelected) {
                    alert('Vui lòng chọn ít nhất một ngày');
                } else {
                    document.getElementById('myForm').submit();
                }
            }
        </script>
    </body>


</html>
