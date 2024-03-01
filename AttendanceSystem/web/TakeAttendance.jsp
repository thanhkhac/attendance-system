<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.*"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chấm công</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            .mycell {
                width: 50%;
                border: 1px solid gray;
                border-radius: 10px;
            }

            .mytitle {
                box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
                padding-left: 5px;
            }

            .takeAttendance-block {
                font-size: 1.2em;
                box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
            }

            .mybutton {
                font-size: 1.2em;
                box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
            }
        </style>

        <style>
            .right{
                position: absolute;
                width: 83%;
                right: 0px;
            }

            @media screen and (max-width: 768px) {

                .right{
                    position: absolute;
                    width: 100%;
                    right: auto;
                }
            }
        </style>
    </head>
    <%
        OvertimeDAO overtimeDAO = new OvertimeDAO();
        OvertimeDTO overtime = new OvertimeDAO().getCurrentOvertime(0);
        
        request.setAttribute("overtime" , overtime);
    %>

    <body>
        <%@include file="Sidebar.jsp" %>
        <div class="right">
            <div class="row justify-content-center mt-3">
                <div class="takeAttendance-block col-md-5 col-8 rounded-3 p-0 " style="background-color: #34568B;">
                    <div class="col-12 text-center fs-2 text-white fw-bold">
                        Tăng ca
                    </div>
                    <div>
                        <table class="table bg-white">
                            <tr>
                                <th colspan="4" class="text-center">${requestScope.overtime.date}</th>
                            </tr>
                            <tr>
                                <th class="mytitle">Bắt đầu:</th>
                                <td>7:30:00</td>
                                <th class="mytitle">Kết thúc:</th>
                                <td>17:30:00</td>
                            </tr>
                            <tr>
                                <th class="mytitle">Cổng mở</th>
                                <td>7:30:00</td>
                                <th class="mytitle">Cổng đóng</th>
                                <td>17:30:00</td>
                            </tr>
                            <tr>
                                <th class="mytitle">Giao lao:</th>
                                <td>7:30:00</td>
                                <th class="mytitle">Kết thúc:</th>
                                <td>17:30:00</td>
                            </tr>
                            <tr>
                                <th class="mytitle">Chấm công vào</th>
                                <td>7:30:00</td>
                                <th class="mytitle">Chấm công ra</th>
                                <td>17:30:00</td>
                            </tr>

                        </table>
                    </div>
                    <div class="col-12 text-center mb-3">
                        <button class="btn text-white fw-bold mybutton" style="background-color: #88B04B;">
                            Chấm công
                        </button>
                    </div>
                </div>
            </div>


            <div class="row justify-content-center mt-3">
                <div class="takeAttendance-block col-md-5 col-8 rounded-3 p-0 " style="background-color: #34568B;">
                    <div class="col-12 text-center fs-2 text-white fw-bold">
                        Ca hành chính
                    </div>
                    <div>
                        <table class="table bg-white">
                            <tr>
                                <th colspan="4" class="text-center">27/02/2024</th>
                            </tr>
                            <tr>
                                <th class="mytitle">Bắt đầu:</th>
                                <td>7:30:00</td>
                                <th class="mytitle">Kết thúc:</th>
                                <td>17:30:00</td>
                            </tr>
                            <tr>
                                <th class="mytitle">Cổng mở</th>
                                <td>7:30:00</td>
                                <th class="mytitle">Cổng đóng</th>
                                <td>17:30:00</td>
                            </tr>
                            <tr>
                                <th class="mytitle">Giao lao:</th>
                                <td>7:30:00</td>
                                <th class="mytitle">Kết thúc:</th>
                                <td>17:30:00</td>
                            </tr>
                            <tr>
                                <th class="mytitle">Chấm công vào</th>
                                <td>7:30:00</td>
                                <th class="mytitle">Chấm công ra</th>
                                <td>17:30:00</td>
                            </tr>

                        </table>
                    </div>
                    <div class="col-12 text-center mb-3">
                        <button class="btn text-white fw-bold mybutton" style="background-color: #88B04B;">
                            Chấm công
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
