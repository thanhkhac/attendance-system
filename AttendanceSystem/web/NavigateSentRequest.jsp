<%-- 
    Document   : navigate
    Created on : Mar 21, 2024, 5:47:54 PM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            body{
                font-family: sans-serif;
                background-color: steelblue;
            }
            .content{
                background-color: white;
                max-width: 65%;
                margin: auto;
                padding: 20px;
                margin-top: 10px;
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
            .content-request{
                margin-top: 50px;
                padding-bottom: 30px;
                /*border-bottom: 1px solid grey;*/
            }
            .content-request-type{
                border: 1px solid grey;
                padding: 10px;
                font-size: larger;
                font-weight: 600;
            }
            .content-request-type label{
                padding-right: 10px;
                border-right: 1px solid grey;
            }
            .content-request-type select{
                margin-left: 10px;
                font-size: medium;
                border: 1px solid grey;
                border-radius: 5px;
                padding: 5px;
            }
        </style>

    </head>
    <body>
        <div>
            <div class="content">
                <h1>Thông Báo</h1>
                <div class="content-redirect">
                    <p><a href="HomePage.jsp">Trang chủ</a> | Xem thông tin đơn</p>
                </div>
                <div class="text-center">
                    <h1 style="margin: 30px">Danh sách đơn chờ phê duyệt</h1>
                </div>
                <div class="content-request">
                    <div class="content-request-type">
                        <label for="request-type">Loại đơn: </label>
                        <select id="mySelect" name="mySelect">
                            <option value="">Chọn Loại Đơn</option>
                            <option value="ViewSentLeaveRequest.jsp">Đơn nghỉ phép</option>
                            <option value="ViewSentOvertimeRequest.jsp">Đơn làm ngoài giờ</option>
                        </select>
                    </div>
                </div>
            </div>

        </div>
        <script>
            document.getElementById('mySelect').addEventListener('change', function () {
                var selectedOption = this.options[this.selectedIndex].value;
                if (selectedOption !== "") {
//                    window.open(selectedOption, '_blank');
                    window.location.href = selectedOption;
                }
            });
        </script>
    </body>
</html>
