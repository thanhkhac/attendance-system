<%-- 
    Document   : Recovery_PW_Page
    Created on : Jan 18, 2024, 3:54:48 PM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Recovery Password</title>

        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css">
        <!-- <script src="bootstrap-5.3.2-dist/js/bootstrap.bundle.js"></script> -->

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

        <%
            String mail = (String) request.getSession().getAttribute("EMAIL");
            if(mail == null){
                mail = "";
            }
        %>

        <form action="DispatchController" method="post">
            <div class="login-form">
                <div class="form-title">
                    Password Recovery
                </div>

                <div>
                    <p class="text-muted" style="text-align: center; color: #adb5bd !important ">Enter your email address and we'll send you an OTP to reset your password</p>
                </div>

                <div class="form-input">
                    <!--<label for="username">Email Address</label>-->
                    <input class="inp" type="text" name="txtMail" value="<%=mail%>" placeholder="Email address">
                </div>

                <div class="captcha">
                    <!--<label for="captcha-input">OTP</label>-->
                    <div class="captcha-form">
                        <input class="inp" type="text" name="txtOTP" id="captcha-form" placeholder="Enter OTP here">
                        <button class="captcha-refresh inp" name="btAction" value="Send">
                            <i class="fa-regular fa-paper-plane"></i>
                        </button>
                    </div>
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
                    <button class="btn btn-lg btn-primary inp" id="login-btn" name="btAction" value="Reset">Reset Password</button>
                </div>
            </div>
        </form>

        <script src="https://kit.fontawesome.com/c2b5cd9aa7.js" crossorigin="anonymous"></script>
    </body>
</html>

