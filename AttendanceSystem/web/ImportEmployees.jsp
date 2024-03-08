<%-- 
    Document   : ImportEmployees
    Created on : Mar 8, 2024, 5:14:44 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <script src="assets/Bootstrap5/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> 
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <title>JSP Page</title>
        <style>

            .content{
                max-width: 90%;
                margin: auto;
                margin-top: 20px;
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
        </style>
    </head>
    <body>
        <div class="content">
            <h1 class="text-center">Import Employee </h1>
            <div class="content-redirect">
                <p><a href="ThanhCong.html">Home</a> | Import From Excel</p>
            </div>
            
            <div class="content-header">
                <form action="SendEmployeeFormattedFileServlet">
                    <input class="btn btn-success" type="submit" name="btAction" value="Dowload Formatted File Excel">
                </form>
            </div>
        </div>
    </body>
</html>
