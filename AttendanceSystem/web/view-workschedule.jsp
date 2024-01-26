
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            .calendar{
                position: absolute;
                width: 83%;
                right: 0px;
            }

            @media screen and (max-width: 768px) {

                .calendar{
                    position: absolute;
                    width: 100%;
                    right: auto;
                }
            }
        </style>

    </head>
    <body>
        <div>
            <%@include file="Sidebar.jsp" %>
            <div class="calendar">
                <div class="text-center pt-3" style="font-family: sans-serif; font-weight: 900">
                    <h2>Lịch làm việc</h2>
                </div>
                <div class="d-flex justify-content-around px-3 py-3 align-middle">
                    <select id="shift-select" class="form-select">
                        <option value="">Tất cả</option>
                        <option value="shif-1">Ca sáng</option>
                        <option value="shift-2">Ca chiều</option>
                        <option value="overtime">Tăng ca</option>
                        <option value="leave">Ngày nghỉ</option>
                    </select>
                    <select id="month" class="form-select">
                        <option value="1">January</option>
                        <option value="2">February</option>
                        <option value="3">March</option>
                        <option value="4">April</option>
                        <option value="5">May</option>
                        <option value="6">June</option>
                        <option value="7">July</option>
                        <option value="8">August</option>
                        <option value="9">September</option>
                        <option value="10">October</option>
                        <option value="11">November</option>
                        <option value="12">December</option>
                    </select>

                    <select id="year" class="form-select">
                        <option value="2022">2022</option>
                        <option value="2023">2023</option>
                        <option value="2024">2024</option>
                        <option value="2024">2025</option>
                        <option value="2024">2026</option>
                    </select>
                    <button class="form-control">Trước</button>
                    <button class="form-control">Sau</button>
                </div>
                <div>
                    <%@include file="view-calendar.jsp" %>
                </div>
            </div>

        </div>

        <script src="assets/calendar/view-workschedule.js"></script>
    </body>
</html>
