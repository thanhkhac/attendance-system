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


        <title>JSP Page</title>
        <style>
            #pagination-container {
                display: flex;
                justify-content: flex-end;
                margin-top: 20px;
            }

            .pagination-ul {
                list-style: none;
                padding: 0;
                display: flex;
                flex-wrap: wrap;
            }

            .pagination-ul li {
                margin: 5px;
            }

            .pagination-ul li a {
                text-decoration: none;
                padding: 8px 12px;
                border: 1px solid #ddd;
                border-radius: 4px;
                color: #333;
                background-color: #fff;
                cursor: pointer;
            }

            .pagination-ul li a:hover {
                background-color: #ddd;
            }
            .pagination-ul li.active a {
                background-color: #007bff; /* Set the background color for the active page */
                color: #fff; /* Set the text color for the active page */
            }

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
                        <tr class="text-center statistics-row" >
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
            <div id="pagination-container">
                <ul id="pagination" class="pagination-ul"></ul>
            </div>
            <!--            <div class="text-center container" >
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
</div>-->
        </div>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/paginationjs/2.1.5/pagination.min.js"></script>
        <script src="assets/Bootstrap5/js/bootstrap.min.js"></script>
        <script>
            function searchByDay(param) {
//                var pageNow = $(this).closest(".content-table").find(".pageNow");
                var startDate_raw = document.getElementById("startDate").value;
                var endDate_raw = document.getElementById("endDate").value;
                var Page = param.dataset.index;
//                console.log(Page);
                $.ajax({
                    url: "/AttendanceSystem/GetStatisticsByAJAXServlet",
                    type: "get",
                    data: {
                        startDate: startDate_raw,
                        endDate: endDate_raw
//                        Page: Page
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
            var pageSize = 10; // Số lượng dòng mỗi trang
            var currentPage = 1; // Trang hiện tại

            function showPage(page) {
                var rows = document.getElementsByClassName('statistics-row');
                var pageCount = Math.ceil(rows.length / pageSize);

                // Ẩn tất cả các dòng
                for (var i = 0; i < rows.length; i++) {
                    rows[i].style.display = 'none';
                }

                // Hiển thị các dòng của trang hiện tại
                var startIndex = (page - 1) * pageSize;
                var endIndex = startIndex + pageSize;
                for (var i = startIndex; i < endIndex && i < rows.length; i++) {
                    rows[i].style.display = 'table-row';
                }

                // Tạo nút điều hướng phân trang
                var paginationElement = document.getElementById('pagination');
                paginationElement.innerHTML = '';

                // Previous button
                var prevLi = document.createElement('li');
                var prevA = document.createElement('a');
                prevA.href = '#';
                prevA.innerHTML = 'Previous';
                prevA.addEventListener('click', function () {
                    if (currentPage > 1) {
                        currentPage--;
                        showPage(currentPage);
                    }
                });
                prevLi.appendChild(prevA);
                paginationElement.appendChild(prevLi);

                // Add ellipsis if there are more than 10 pages
                if (pageCount > 5 && currentPage > 5) {
                    var ellipsisLi = document.createElement('li');
                    var ellipsisSpan = document.createElement('span');
                    ellipsisSpan.innerHTML = '...';
                    ellipsisLi.appendChild(ellipsisSpan);
                    paginationElement.appendChild(ellipsisLi);
                }

                // Hiển thị các trang có thể chọn
                for (var i = Math.max(1, currentPage - 3); i <= Math.min(pageCount, currentPage + 3); i++) {
                    var li = document.createElement('li');
                    var a = document.createElement('a');
                    a.href = '#';
                    a.innerHTML = i;
                    if (i === currentPage) {
                        li.classList.add('active'); // Add the 'active' class to highlight the current page
                    }
                    a.addEventListener('click', function (e) {
                        currentPage = parseInt(e.target.innerHTML);
                        showPage(currentPage);
                    });
                    li.appendChild(a);
                    paginationElement.appendChild(li);
                }

                // Add ellipsis if there are more than 10 pages
                if (pageCount > 4 && currentPage < pageCount - 4) {
                    var ellipsisLi = document.createElement('li');
                    var ellipsisSpan = document.createElement('span');
                    ellipsisSpan.innerHTML = '...';
                    ellipsisLi.appendChild(ellipsisSpan);
                    paginationElement.appendChild(ellipsisLi);
                }

                // Next button
                var nextLi = document.createElement('li');
                var nextA = document.createElement('a');
                nextA.href = '#';
                nextA.innerHTML = 'Next';
                nextA.addEventListener('click', function () {
                    if (currentPage < pageCount) {
                        currentPage++;
                        showPage(currentPage);
                    }
                });
                nextLi.appendChild(nextA);
                paginationElement.appendChild(nextLi);
            }

// Hiển thị trang đầu tiên khi trang được tải
            document.addEventListener('DOMContentLoaded', function () {
                showPage(currentPage);
            });

        </script>


    </body>
</html>
