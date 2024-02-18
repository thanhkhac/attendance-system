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
                    <select id="month" class="form-select">
                        <option value="1">Tháng 1</option>
                        <option value="2">Tháng 2</option>
                        <option value="3">Tháng 3</option>
                        <option value="4">Tháng 4</option>
                        <option value="5">Tháng 5</option>
                        <option value="6">Tháng 6</option>
                        <option value="7">Tháng 7</option>
                        <option value="8">Tháng 8</option>
                        <option value="9">Tháng 9</option>
                        <option value="10">Tháng 10</option>
                        <option value="11">Tháng 11</option>
                        <option value="12">Tháng 12</option>
                    </select>

                    <select id="year" class="form-select"></select>

                    <button class="form-control" id="previous">Trước</button>
                    <button class="form-control" id="next">Sau</button>
                </div>
                <div id="result">
                    <%@include file="ScheduleWorkCalendar.jsp" %>
                </div>
            </div>

        </div>
        <script>
            var yearDropdown = document.getElementById("year");
            var currentYear = new Date().getFullYear();
            for (var i = currentYear - 5; i <= currentYear + 5; i++) {
                var option = document.createElement("option");
                option.value = i;
                option.text = i;
                if (i === currentYear) {
                    option.selected = true;
                }
                yearDropdown.appendChild(option);
            }


            $(document).ready(function () {
                $("#month, #year").change(function () {
                    var selectedMonth = $("#month").val();
                    var selectedYear = $("#year").val();
                    $.ajax({
                        type: "POST",
                        url: "ScheduleWorkCalendar.jsp",
                        data: {
                            month: selectedMonth,
                            year: selectedYear
                        },
                        success: function (response) {
                            $("#result").html(response);
                        },
                        error: function () {
                            $("#result").html("Error occurred.");
                        }
                    });
                });

                $("#previous").click(function () {
                    changeMonthYear(-1);
                });

                $("#next").click(function () {
                    changeMonthYear(1);
                });

                function changeMonthYear(change) {
                    var selectedMonth = $("#month").val();
                    var selectedYear = $("#year").val();

                    var newMonth = parseInt(selectedMonth) + change;
                    if (newMonth < 1) {
                        newMonth = 12;
                        selectedYear--;
                    } else if (newMonth > 12) {
                        newMonth = 1;
                        selectedYear++;
                    }

                    $("#month").val(newMonth);
                    $("#year").val(selectedYear);

                    updateCalendar();
                }

                function updateCalendar() {
                    var selectedMonth = $("#month").val();
                    var selectedYear = $("#year").val();

                    $.ajax({
                        type: "POST",
                        url: "ScheduleWorkCalendar.jsp",
                        data: {
                            month: selectedMonth,
                            year: selectedYear
                        },
                        success: function (response) {
                            $("#result").html(response);
                        },
                        error: function () {
                            $("#result").html("Error occurred.");
                        }
                    });
                }
            });

        </script>
        <script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
