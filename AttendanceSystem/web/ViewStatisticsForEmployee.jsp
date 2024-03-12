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
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/apexcharts@3.29.0/dist/apexcharts.min.css">
        <script src="assets/Bootstrap5/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> 
        <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
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
            }
            .employee-infor p{
                margin: 0;
            }
            .content-table{
                margin-top: 10px;
            }
            .menu{
                margin-top: 20px;
                display: flex;
                align-items: center;
                justify-content: space-between;
                padding: 10px;
            }
            .exportButton input{

            }
            .fa-download{
                font-size: large;
                margin-left: 10px;
            }
            #statisticsForm {
                max-width: 800px;
                margin: 20px auto;
                padding: 10px;
                background-color: #f5f5f5;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .form-statistic {
                display: flex;
                gap: 20px;
                align-items: center;
                justify-content: space-around;
            }
            .form-statistic label {
                font-weight: 600;
                min-width: 250px;
                display: inline;
            }
            .form-statistic input {
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
                min-width: 250px;
            }
            .statistic-items input{
                outline: none;
                border: none;
                font-size: 20px;
                font-weight: 600
            }
            .font-monospace{
                font-size: large;
            }
            @media (max-width: 768px) {
                .form-statistic {
                    flex-direction: column;
                }
                .form-statistic label{
                    margin-right: 100px;
                    min-width: 250px;
                }
                .form-statistic input{
                    margin-right: 100px;
                    min-width: 250px;
                }
            }

        </style>
    </head>
    <body>
        <c:set var="statistics" value="${requestScope.statistics}" />
        <c:set var="employee" value="${sessionScope.ACCOUNT}" />
        <c:set var="startDate" value="${requestScope.startDate}" />
        <c:set var="endDate" value="${requestScope.endDate}" />

        <c:set var="workedDays" value="${requestScope.workedDays}" />
        <c:set var="notWorkedDays" value="${requestScope.notWorkedDays}" />
        <c:set var="leaveDays" value="${requestScope.leaveDays}" />
        <c:set var="scheduledDays" value="${requestScope.scheduledDays}" />

        <c:set var="totalShift" value="${requestScope.totalShift}" />
        <c:set var="totalOT" value="${requestScope.totalOT}" />
        <c:set var="totalHour" value="${requestScope.totalHour}" />
        <div class="content">
            <h1 class="text-center">View Statistics</h1>
            <div class="content-redirect">
                <p><a href="ThanhCong.html">Home</a> | View Statistics</p>
            </div>
            <div class="menu">
                <div class="employee-infor">
                    <p class="font-monospace">Nhân viên : ${employee.getLastName()} ${employee.getMiddleName()} ${employee.getFirstName()}</p>
                    <p class="font-monospace">Thời Hạn: ${employee.getStartDate()} - ${employee.getEndDate()}</p>
                </div>
                <div class="exportButton">
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                        Export To Excel
                    </button>
                </div>
            </div>


            <input type="hidden" id="workedDays" value="${workedDays}">
            <input type="hidden" id="notWorkedDays" value="${notWorkedDays}">
            <input type="hidden" id="leaveDays" value="${leaveDays}">

            <input type="hidden" id="totalShift" value="${totalShift}">
            <input type="hidden" id="totalOT" value="${totalOT}">



            <form id="statisticsForm" action="GetEmployeeStatisticsServlet" method="Post">
                <div class="form-statistic">
                    <div class="statistic-items">
                        <label for="startDate">Ngày Bắt Đầu: </label>
                        <input type="date" name="startDate" id="startDate" 
                               min="${employee.getStartDate()}"
                               max="${requestScope.current}"
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
                               min="${employee.getStartDate()}"
                               max="${requestScope.current}"

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
            <div class="d-flex justify-content-lg-around m-5">
                <div class="statistic-items">
                    <label for="scheduledDays">Tổng ngày có lịch làm việc: </label>
                    <input readonly="" type="text" id="scheduledDays" value="${scheduledDays}">
                </div>
                <div class="statistic-items">
                    <label for="totalHour">Tổng số giờ đã làm</label>
                    <input readonly="" type="text" id="totalHour" value="${totalHour}">
                </div>
            </div>
            <div class="d-flex">
                <div id="chart_days" class="col-md-6"></div>
                <div id="chart_hours" class="col-md-6"></div>
            </div>
            <div class="d-flex justify-content-around">
                <p>Days</p>
                <p>Hours</p>
            </div>

            <%@include  file="Include_Statistics.jsp" %>

        </div>
        <div class="modal fade" id="exampleModal" tabindex="-1"
             aria-labelledby="exampleModalLabel" aria-hidden="true" data-bs-backdrop="static">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Export To Excel</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="export-form" action="ExportExcelFileServlet" class="row g-3">
                            <div class="col-md-6">
                                <label for="startDate" class="form-label">Start Date</label>
                                <input type="date" name="startDate" class="form-control" id="startDate" 
                                       <c:if test="${startDate != null}">
                                           value="${startDate}"
                                       </c:if>
                                       <c:if test="${startDate == null}">
                                           value="${employee.getStartDate()}"
                                       </c:if>
                                       >
                            </div>
                            <div class="col-md-6">
                                <label for="endDate" class="form-label">End Date</label>
                                <input type="date" name="endDate" class="form-control" id="endDate"
                                       <c:if test="${endDate != null}">
                                           value="${endDate}"
                                       </c:if>
                                       <c:if test="${endDate == null}">
                                           value="${requestScope.current}"
                                       </c:if>
                                       >
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button onclick="showConfirm()" id="confirmation" type="button" class="btn btn-outline-primary ">Confirm</button>
                        <button style="display: none" onclick="submitForm()" id="dowload-file" type="button" class="btn btn-success"> Dowload File <i class="fa-solid fa-download "></i></button>
                    </div>
                </div>
            </div>
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
        function openPopup() {
            document.getElementById("popupContainer").style.display = "flex";
        }

        function closePopup() {
            document.getElementById("popupContainer").style.display = "none";
        }
        function showConfirm() {
            console.log("hello");
            var cfm_btn = document.getElementById("confirmation");
            var dowload_btn = document.getElementById("dowload-file");
            if (confirm("Are you sure !")) {
                dowload_btn.style.display = 'block';
                cfm_btn.style.display = 'none';
            } else {
                alert("Action Cancelled !");
            }
        }
        function submitForm() {
            var form = document.getElementById("export-form");
            form.submit();
        }
        // Sample data


        var totalShift = document.getElementById("totalShift").value;
        var totalOT = document.getElementById("totalOT").value;
        var totalHour = document.getElementById("totalHour").value;

        var workedDays = document.getElementById("workedDays").value;
        var notWorkedDays = document.getElementById("notWorkedDays").value;
        var leaveDays = document.getElementById("leaveDays").value;
        var scheduledDays = document.getElementById("scheduledDays").value;

        var options_days = {
            series: [
                parseFloat(((workedDays / scheduledDays) * 100).toFixed(2)),
                parseFloat(((leaveDays / scheduledDays) * 100).toFixed(2)),
                parseFloat(((notWorkedDays / scheduledDays) * 100).toFixed(2))
            ],
            chart: {
                type: 'pie',
                height: 350
            },
            labels: [
                'Ngày đi làm: ' + workedDays,
                'Ngày xin nghỉ: ' + leaveDays,
                'Ngày không đi làm: ' + notWorkedDays
            ],
            colors: ['#19C37D', '#FEB019', '#202124'],
            responsive: [{
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 200
                        },
                        legend: {
                            position: 'bottom'
                        }
                    }
                }]
        };
        var options_hours = {
            series: [
                parseFloat(((totalShift / totalHour) * 100).toFixed(2)),
                parseFloat(((totalOT / totalHour) * 100).toFixed(2))
            ],
            chart: {
                type: 'pie',
                height: 350
            },
            labels: ['Ca Thường: ' + totalShift + ' Hrs',
                'OverTime: ' + totalOT + ' Hrs'],
            colors: ['#FF5722', '#202124'],
            responsive: [{
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 200
                        },
                        legend: {
                            position: 'bottom'
                        }
                    }
                }]
        };


        // Create a pie chart using ApexCharts
        var chart_days = new ApexCharts(document.querySelector("#chart_days"), options_days);
        chart_days.render();
        var chart_hours = new ApexCharts(document.querySelector("#chart_hours"), options_hours);
        chart_hours.render();

    </script>
</html>
