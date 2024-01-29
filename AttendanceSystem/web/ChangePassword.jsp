<%-- 
    Document   : Forgot_PW
    Created on : Jan 18, 2024, 9:57:39 PM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Pasword</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>

        <style>
            * {
                margin: 0px;
                padding: 0px;
                box-sizing: border-box;
            }

            body {
                font-family: "roboto", sans-serif;
                background-color: #f5f5f5;
                height: 100vh;
                margin: 0;
                background-image: url("assets/img/fpt-background.JPG");
                background-size: cover;
                background-position: center bottom;
                background-repeat: no-repeat;
            }

            .login-form {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                width: 90%;
                max-width: 450px;
                background: #fff;
                padding: 20px 30px;
                box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.3);
                border-radius: 10px;
            }

            .login-form .form-title {
                font-family: "Montserrat", sans-serif;
                text-align: center;
                font-size: 30px;
                font-weight: 600;
                margin: 20px 0px 30px;
                color: #111;
            }

            .login-form .form-input {
                margin: 10px 0px;
            }

            .login-form .form-input label,
            .login-form .captcha label {
                display: block;
                font-size: 15px;
                color: #111;
                margin-bottom: 5px;
            }

            .login-form .form-input input {
                width: 100%;
                padding: 10px;
                border: 1px solid #888;
                font-size: 15px;
            }

            .login-form .captcha {
                margin: 15px 0px;
            }

            .login-form .captcha .preview {
                color: #555;
                width: 100%;
                text-align: center;
                height: 40px;
                line-height: 40px;
                letter-spacing: 8px;
                border: 1px dashed #888;
                font-family: "monospace";
            }

            .login-form .captcha .preview span {
                display: inline-block;
                user-select: none;
            }

            .login-form .captcha .captcha-form {
                display: flex;
            }

            .login-form .captcha .captcha-form input {
                width: 100%;
                padding: 8px;
                border: 1px solid #888;
            }

            .login-form .captcha .captcha-form .captcha-refresh {
                width: 40px;
                border: none;
                outline: none;
                background: #888;
                color: #eee;
                cursor: pointer;
            }

            .login-form #login-btn {
                margin-top: 5px;
                width: 100%;
                padding: 10px;
                border: none;
                outline: none;
                font-size: 15px;
                text-transform: uppercase;
                background: #4c81ff;
                color: #fff;
                cursor: pointer;
            }
            .inp{
                border-radius: 10px;
            }
        </style>

    </head>
    <body>

        <%--
            String mail = (String) request.getAttribute("MAIL");
            if(mail == null){
                mail = "";
            }
        --%>

        <form action="DispatchController" method="post">
            <div class="login-form">
                <div class="form-title">
                    Forgot Password
                </div>
                <%--
                                <div class="form-input">
                                    <label for="username">Email Address</label>
                                    <input class="inp" type="text" name="txtMail" value="<%=mail%>">
                                </div>
                --%>
                <div class="form-input">
                    <label for="name">Password</label>
                    <input class="inp" type="password" name="txtPassword" value="">
                </div> 

                <div class="form-input">
                    <label for="name">Re-Enter Password</label>
                    <input class="inp" type="password" name="txtRePassword" value="">
                </div>

                <%
                    String msg = (String) request.getAttribute("MSG");
                    if(msg != null){
                %>
                <div class="row">

                    <div class="col-md-1">
                        <i class="fa-solid fa-triangle-exclamation"></i>
                    </div>

                    <div class="col-md-11">
                        <p style="color: red"><%=msg%></p>
                    </div>

                </div>
                <%
                    }
                %>

                <div class="form-input">
                    <button class="inp" id="login-btn" name="btAction" value="Save change">Save Change</button>
                </div>
            </div>
        </form>

        <script src="https://kit.fontawesome.com/c2b5cd9aa7.js" crossorigin="anonymous"></script>
    </body>
</html>
