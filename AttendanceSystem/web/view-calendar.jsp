<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.time.ZoneId"%>
<%@page import="java.time.LocalDate"%>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/schedule.css">
    </head>

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
                <c:set var="calendar" value="${requestScope.workingdays}"/>
                <c:forEach var="wkday" items="${workingdays}" varStatus="counter">
                    <c:if test="${counter.index % 7 == 0}">
                        <tr>
                        </c:if>
                        <td>
                            <div class="date-block">

                                <c:if test = "${wkday.date eq requestScope.today}">
                                    <div class="date text-danger" id="today">
                                        ${wkday.date}
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
                                    <c:choose>
                                        <c:when test = "${not empty wkday.leave}">
                                            <div class="shift leave text-center">
                                                <div class="shift__title">
                                                    ${wkday.shift.name}
                                                </div>
                                                <div>
                                                    ${wkday.shift.startTime} - ${wkday.shift.endTime}
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:when test = "${wkday.date < today and empty wkday.timesheet.checkin}">
                                            <div class="shift absent text-center">
                                                <div class="shift__title">
                                                    ${wkday.shift.name}
                                                </div>
                                                <div>
                                                    ${wkday.shift.startTime} - ${wkday.shift.endTime}
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:when test = "${wkday.date < today and not empty wkday.timesheet.checkin}">
                                            <div class="shift attended text-center">
                                                <div class="shift__title">
                                                    ${wkday.shift.name}
                                                </div>
                                                <div>
                                                    ${wkday.shift.startTime} - ${wkday.shift.endTime}
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:when test = "${wkday.date > today}">
                                            <div class="shift notyet text-center">
                                                <div class="shift__title">
                                                    ${wkday.shift.name}
                                                </div>
                                                <div>
                                                    ${wkday.shift.startTime} - ${wkday.shift.endTime}
                                                </div>
                                            </div>
                                        </c:when>
                                    </c:choose>
                                </c:if>
                                <c:if test = "${not empty wkday.overtime}">
                                    <div class="shift overtime text-center">
                                        <div class="shift__title">
                                            Tăng ca
                                        </div>
                                        <div>
                                            ${wkday.overtime.startTime} - ${wkday.overtime.endTime}
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </td>
                        <c:if test="${(counter.index + 1) % 7 == 0}">
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </div>


    </body>

</html>
