<%-- 
    Document   : NavigateSentRequest
    Created on : Mar 11, 2024, 9:43:15 AM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Chọn Tùy Chọn và Điều Hướng</title>
</head>
<body>
    <form id="myForm" action="" method="post">
        <select id="mySelect" name="mySelect">
            <option value="">Đơn</option>
            <option value="ViewSentLeaveRequest.jsp">Leave request</option>
            <option value="ViewSentOvertimeRequest.jsp">Overtime Request</option>
        </select>
    </form>

    <script>
        document.getElementById('mySelect').addEventListener('change', function() {
            var selectedOption = this.options[this.selectedIndex].value;
            if (selectedOption !== "") {
                window.open(selectedOption, '_blank');
            }
        });
    </script>
</body>
</html>
