<%-- 
    Document   : include_Statistics
    Created on : Feb 29, 2024, 5:56:36 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.*" %>
<%@page import="java.time.*"%>
<%@page import="ultility.datetimeutil.DateTimeUtil"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <script src="assets/Bootstrap5/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> 
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <title>JSP Page</title>
        <style>

        </style>
    </head>
    <body>
        <%
            DateTimeUtil dateTimeUtil = new DateTimeUtil();
            LocalDate current = dateTimeUtil.getVNLocalDateNow();
            request.setAttribute("current", current);
        %>
        <c:set var="statistics" value="${requestScope.statistics}" />
        <c:set var="employee" value="${sessionScope.ACCOUNT}" />
        <c:set var="endPage" value="${requestScope.endPage}" />
        <%--<c:if test="${statistics.size()>0}">--%>
        <%--<%@include file="include_Statistics.jsp" %>--%>
        <%--</c:if>--%>
        <div class="content-table" id="content-table">
            <table border="1" class="table table-bordered table-responsive-md table-hover">
                <thead class="text-center" >
                    <tr class="table-dark">
                        <th>Date</th>
                        <th>Shift</th>
                        <th>StartTime</th>
                        <th>EndTime</th>
                        <th>CheckIn</th>
                        <th>CheckOut</th>
                        <th>OverTimeStart</th>
                        <th>OverTimeEnd</th>
                        <th>OverTimeCheckIn</th>
                        <th>OverTimeCheckOut</th>
                        <th>Total Hours</th>
                    </tr>
                    <tr class="table-active">
                        <th>YYYY-mm-DD</th>
                        <th></th>
                        <th>HH:MM:SS</th>
                        <th>HH:MM:SS</th>
                        <th>HH:MM:SS</th>
                        <th>HH:MM:SS</th>
                        <th>HH:MM:SS</th>
                        <th>HH:MM:SS</th>
                        <th>HH:MM:SS</th>
                        <th>HH:MM:SS</th>
                        <th>HH:MM:SS</th>
                    </tr>
                </thead>
                <tbody>
                <div class="table-container">
                    <c:forEach items="${statistics}" var="s">
                        <tr class="text-center">
                            <td>${s.getDate()}</td>
                            <td>${s.getShiftName()}</td>
                            <td>
                                <c:if test= "${s.getStartTime() == null}">
                                    <p>--</p>
                                </c:if>
                                <c:if test= "${s.getStartTime() != null}">
                                    ${s.getStartTime()}
                                </c:if>
                            </td>
                            <td>
                                <c:if test= "${s.getEndTime() == null}">
                                    <p>--</p>
                                </c:if>
                                <c:if test= "${s.getEndTime() != null}">
                                    ${s.getEndTime()}
                                </c:if>
                            </td>
                            <td>
                                <c:if test= "${s.getCheckIn() == null}">
                                    <p>--</p>
                                </c:if>
                                <c:if test= "${s.getCheckIn() != null}">
                                    ${s.getCheckIn()}
                                </c:if>
                            </td>
                            <td>
                                <c:if test= "${s.getCheckOut() == null}">
                                    <p>--</p>
                                </c:if>
                                <c:if test= "${s.getCheckOut() != null}">
                                    ${s.getCheckOut()}
                                </c:if>
                            </td>
                            <td>
                                <c:if test= "${s.getOtStartTime() == null}">
                                    <p>--</p>
                                </c:if>
                                <c:if test= "${s.getOtStartTime() != null}">
                                    ${s.getOtStartTime()}
                                </c:if>
                            </td>
                            <td><c:if test= "${s.getOtStartTime() == null}">
                                    <p>--</p>
                                </c:if>
                                <c:if test= "${s.getOtStartTime() != null}">
                                    ${s.getOtEndTime()}
                                </c:if>
                            </td>
                            <td><c:if test= "${s.getOtStartTime() == null}">
                                    <p>--</p>
                                </c:if>
                                <c:if test= "${s.getOtStartTime() != null}">
                                    ${s.getOtCheckIn()}
                                </c:if>
                            </td>
                            <td>
                                <c:if test= "${s.getOtStartTime() == null}">
                                    <p>--</p>
                                </c:if>
                                <c:if test= "${s.getOtStartTime() != null}">
                                    ${s.getOtCheckOut()}
                                </c:if>
                            </td>
                            <td>
                                ${String.format("%.2f", s.getTotalDay().toMinutes()/60)}
                            </td>
                        </tr>
                    </c:forEach>
                </div>
                </tbody>
            </table>
            <div class="text-center container" >
                <ul class="pagination" id="pagination" style="justify-content: end;">
                    <li class="page-item"><a class="page-link" href="#">Trước</a></li>
                        <c:forEach begin="1" end="${endPage}" var="i">
                            <c:if test="${i==1}">
                            <li class="page-item"><a style="background-color: #cfd5da96;" class="page-link page pageNow" data-index="${i}" onclick="searchByDay(this)" href="#">${i}</a><li>
                            </c:if>
                            <c:if test="${i>1 || i<1}">
                            <li class="page-item"><a  class="page-link page" data-index="${i}" onclick="searchByDay(this)" href="#">${i}</a><li>
                            </c:if>
                        </c:forEach>
                        <c:if test="${endPage>1}">
                        <li class="page-item"><a data-index="<%=2%>" onclick="searchByDay(this)" class="page-link" href="#">Sau</a></li>
                        </c:if>
                        <c:if test="${endPage<=1}">
                        <li class="page-item"><a data-index="<%=1%>" onclick="searchByDay(this)" class="page-link" href="#">Sau</a></li>
                        </c:if>
                </ul>
            </div>
        </div>
        <script>
            function searchByDay(param) {
                var pageNow = $(this).closest(".content-table").find(".pageNow");
                var startDate_raw = document.getElementById("startDate").value;
                var endDate_raw = document.getElementById("endDate").value;
                var Page = param.dataset.index;
                console.log(Page);
                $.ajax({
                    url: "/AttendanceSystem/GetStatisticsByAJAXServlet",
                    type: "get",
                    data: {
                        startDate: startDate_raw,
                        endDate: endDate_raw,
                        Page: Page
                    },
                    success: function (data) {
                        var tbody = $("#content-table");
                        tbody.html(data);
                        console.log("success");
                    },
                    error: function (xhr, error) {
                        console.log("Error: ", error);
                    }
                });
            }
        </script>
    </body>
</html>
