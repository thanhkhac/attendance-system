<%-- 
    Document   : Error
    Created on : Feb 21, 2024, 11:00:18 AM
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
                background-color: #f8d7da; 
                border: 1px solid #f5c6cb; 
                padding: 15px;
                margin: 10px 0;
                border-radius: 5px;
            }

            .content-message p {
                color: #721c24; 
                margin: 0;
                font-size: larger;
            }

            .fa-bug {
                color: #721c24; 
                margin-right: 8px; 
            }
        </style>
    </head>
    <body>
        <div class="content">
            <h1>Thông Báo</h1>
            <div class="content-redirect">
                <p><a href="javascript:history.back()">Trở Lại</a> | Error</p>
            </div>
            <div class="content-message">
                <p><i class="fa-solid fa-bug"></i> Có Lỗi Xảy Ra Trong Quá Trình Xử Lí !</p>
            </div>
        </div>
    </body>
</html>
