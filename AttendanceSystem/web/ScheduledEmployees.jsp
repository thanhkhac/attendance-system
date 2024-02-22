<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.*"%>
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
                    <h2>Kế Hoạch Làm Việc Cho Nhân Viên</h2>
                    <h6 class="text-danger">*Danh sách nhân viên đã có lịch làm việc</h6>
                </div>
                <div class="d-flex justify-content-around px-3 py-3 align-middle">
                    <select id="month" class="form-select">
                    </select>

                    <select id="year" class="form-select"></select>

                    <button class="form-control" id="previous">Trước</button>
                    <button class="form-control" id="next">Sau</button>
                </div>
                <div id="result">
                    <%@include file="ScheduledEmployeeList.jsp" %>
                </div>
            </div>

            <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Thành công</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            Thành công
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>



        </div>
        <script>
//            Hiển thị ra dropdown của năm, nếu là năm hiện tại => Selected
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

            //            Hiển thị ra dropdown của tháng, nếu là tháng hiện tại => Selected         
            var monthDropdown = document.getElementById("month");
            var currentMonth = new Date().getMonth();
            console.log(currentYear);

            for (var i = 0; i < 12; i++) {
                var option = document.createElement("option");
                option.value = i + 1;
                option.text = "Tháng " + (i + 1);
                if (i === currentMonth) {
                    option.selected = true;
                }
                monthDropdown.appendChild(option);
            }


            $(document).ready(function () {
                $("#month, #year").change(function () {
                    var selectedMonth = $("#month").val();
                    var selectedYear = $("#year").val();
                    $.ajax({
                        type: "POST",
                        url: "ScheduledEmployeeList.jsp",
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
                        url: "ScheduledEmployeeList.jsp",
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
