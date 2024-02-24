<%-- 
    Document   : Success
    Created on : Feb 20, 2024, 4:14:09 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <script src="assets/Bootstrap5/js/bootstrap.min.js"></script>
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <title>JSP Page</title>
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
            .content-message {
                background-color: #d4edda; 
                border: 1px solid #c3e6cb; 
                padding: 15px;
                margin: 10px 0;
                border-radius: 5px;
            }

            .content-message p {
                color: #155724; 
                margin: 0;
                font-size: larger;
            }

            .fa-check {
                color: #155724; 
                margin-right: 8px; 
            }
        </style>
    </head>

    <body>
        <div class="content">
            <h1>Thông Báo</h1>
            <div class="content-redirect">
                <p><a href="ThanhCong.html">Home</a> | <a href="javascript:history.back()">Send Request</a> | Result</p>
            </div>
            <div class="content-message">
                <p><i class="fa-solid fa-check"></i> Yêu cầu của bạn đã được gửi tới hệ thống thành công !</p>
            </div>
        </div>



    </body>
</html>
