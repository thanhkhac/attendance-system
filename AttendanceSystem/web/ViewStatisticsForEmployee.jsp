<%-- 
    Document   : ViewStatisticsForEmployee
    Created on : Feb 29, 2024, 3:11:59 PM
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
            .content{
                max-width: 90%;
                margin: auto;
                margin-top: 20px;
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
            .employee-infor {
                margin-top: 20px;
            }
            .employee-infor p{
                margin: 0;
            }
            .content-table{
                margin-top: 10px;

            }
            /*            .content-table {
                            display: block;
                            height: 600px;
                            overflow: auto;
                            border: 1px solid #ccc;
                            box-sizing: border-box;
                        }
            
                        .content-table thead{
                            width: 100%;
                            table-layout: fixed;
                            position: sticky;
                            top: 0;
                            background-color: #fff;
                            z-index: 1;
                        }*/
            .form-statistic{
                display: flex;
                justify-content: space-evenly;
            }
        </style>
    </head>
    <body>
        <%
            
        %>
        <c:set var="statistics" value="${requestScope.statistics}" />
        <c:set var="employee" value="${sessionScope.ACCOUNT}" />
        <c:set var="startDate" value="${requestScope.startDate}" />
        <c:set var="endDate" value="${requestScope.endDate}" />
        <div class="content">
            <!--            <h1 class="text-center">View Statistics</h1>
                        <div class="content-redirect">
                            <p><a href="ThanhCong.html">Home</a> | View Statistics</p>
                        </div>
                        <div class="employee-infor">
                            <p>Nhân viên : ${employee.getLastName()} ${employee.getMiddleName()} ${employee.getFirstName()}</p>
                            <p>Thời Hạn: ${employee.getStartDate()} - ${employee.getEndDate()}</p>
                        </div>-->

            <form id="statisticsForm" action="GetEmployeeStatisticsServlet" method="Post">
                <div class="form-statistic">
                    <div class="statistic-items">
                        <label for="startDate">Ngày Bắt Đầu: </label>
                        <input type="date" name="startDate" id="startDate" 
                               <c:if test="${startDate != null}">
                                   value="${startDate}"
                               </c:if>
                               <c:if test="${startDate == null}">
                                   value="${employee.getStartDate()}"
                               </c:if>
                               >
                    </div>
                    <div class="statistic-items">
                        <label for="endDate">Đến: </label>
                        <input type="date" name="endDate" id="endDate" 
                               <c:if test="${endDate != null}">
                                   value="${endDate}"
                               </c:if>
                               <c:if test="${endDate == null}">
                                   value="${requestScope.current}"
                               </c:if>
                               >
                    </div>
                </div>
            </form>
            <%--<c:if test="${statistics.size()>0}">--%>
            <%--<%@include file="include_Statistics.jsp" %>--%>
            <%--</c:if>--%>
            <%@include  file="Include_Statistics.jsp" %>

        </div>
    </body>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            var form = document.getElementById('statisticsForm');
            var startDateInput = document.getElementById('startDate');
            var endDateInput = document.getElementById('endDate');

            startDateInput.addEventListener('change', function () {
                form.submit();
            });
            endDateInput.addEventListener('change', function () {
                form.submit();
            });
        });
    </script>
</html>
