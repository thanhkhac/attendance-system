
function ReloadStyle() {
        // Get all shift elements
        let shifts = document.querySelectorAll('.shift');

        shifts.forEach(function (shift) {

            let date = shift.querySelector('.date').textContent.trim();
            let startTimeString = shift.querySelector('.startTime').textContent.trim();
            let checkInString = shift.querySelector('.checkIn').textContent.trim();
            let checkOutString = shift.querySelector('.checkOut').textContent.trim();
            let shiftDate = new Date(date + ' ' + startTimeString);
            let currentDate = new Date();
            if (shift.querySelector('.leave')) {
                shift.querySelectorAll('.shift-status').forEach(function (element) {
                    element.textContent = 'Nghỉ';
                });
                shift.classList.add('leave');
            } else {
                if (shiftDate > currentDate) {

                    shift.querySelectorAll('.shift-status').forEach(function (element) {
                        element.textContent = 'Chưa diễn ra';
                    });
                    shift.classList.add('notyet');
                } else if (checkInString) {

                    if (checkOutString) {
                        shift.querySelectorAll('.shift-status').forEach(function (element) {
                            element.textContent = 'Đã chấm công';
                        });
                        shift.classList.add('attended');
                    } else {
                        shift.querySelectorAll('.shift-status').forEach(function (element) {
                            element.textContent = 'Đã chấm công vào';
                        });
                        shift.classList.add('onlycheckin');
                    }
                } else {

                    shift.querySelectorAll('.shift-status').forEach(function (element) {
                        element.textContent = 'Vắng';
                    });
                    shift.classList.add('absent');
                }
            }

        }
        );
    }
    
    
    ReloadStyle();

$(document).ready(function () {
    $("#month, #year").change(function () {
        var selectedMonth = $("#month").val();
        var selectedYear = $("#year").val();
        $.ajax({
            type: "POST",
            url: "ViewCalendar.jsp",
            data: {
                month: selectedMonth,
                year: selectedYear
            },
            success: function (response) {
                $("#result").html(response);
                ReloadStyle();
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
            url: "ViewCalendar.jsp",
            data: {
                month: selectedMonth,
                year: selectedYear
            },
            success: function (response) {
                $("#result").html(response);
                ReloadStyle();
            },
            error: function () {
                $("#result").html("Error occurred.");
            }
        });
    }
});

