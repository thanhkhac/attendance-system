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
        <div class="content">
            <h1 class="text-center">View Statistics</h1>
            <div class="content-redirect">
                <p><a href="ThanhCong.html">Home</a> | View Statistics</p>
            </div>
            <div class="menu">

            </div>


            <input type="hidden" id="workedDays" value="${workedDays}">
            <input type="hidden" id="notWorkedDays" value="${notWorkedDays}">
            <input type="hidden" id="leaveDays" value="${leaveDays}">

            <input type="hidden" id="totalShift" value="${totalShift}">
            <input type="hidden" id="totalOT" value="${totalOT}">

            <form  action="DownloadWorkSheet" method="Post">
                <div class="form-statistic">
                    <div class="statistic-items">
                        <label for="startDate">Ngày Bắt Đầu: </label>
                        <input type="date" name="startDate" id="startDate" value="2022-01-01">
                    </div>
                    <div class="statistic-items">
                        <label for="endDate">Đến: </label>
                        <input type="date" name="endDate" id="startDate" value="2025-01-01">
                    </div>
                    <div>
                        <div class="exportButton">
                            <button type="submit" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                Export To Excel
                            </button>
                        </div>
                    </div>
                </div>
            </form>

    </body>

</html>
