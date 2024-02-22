
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
            },
            error: function () {
                $("#result").html("Error occurred.");
            }
        });
    }
});

