<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title>TODO supply a title</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <style>
        .login {
            min-height: 100vh;
        }

        .bg-image {
            background-image: url('https://source.unsplash.com/WEQbe2jBg40/600x1200');
            background-size: cover;
            background-position: center;
        }

        .login-heading {
            font-weight: 300;
        }

        .btn-login {
            font-size: 0.9rem;
            letter-spacing: 0.05rem;
            padding: 0.75rem 1rem;
        }

        .LoginGoole {
            padding-top: 40px;
        }

        .iconGoole {
            font-size: 3em;
            text-align: center;
            transition: transform 0.3s ease-in-out; /* Thêm hiệu ứng transform */
            
        }

        .iconGoole:hover {
            transform: translateY(-10px); /* Hiệu ứng khi hover */
        }
        .LoiLogin{
                text-align: center;
                color: red
        }
    </style>
    <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container-fluid ps-md-0">
        <div class="row g-0">
                <div class="d-none d-md-flex col-md-4 col-lg-6 bg-image">
                
            </div>
            <div class="col-md-8 col-lg-6">
                <div class="login d-flex align-items-center py-5">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-9 col-lg-8 mx-auto">
                                <h3 class="login-heading mb-4">Welcome back!</h3>
                                <!-- Sign In Form -->
                                <%
                                String Email = (String)request.getAttribute("Email");
                                String PassWord = (String)request.getAttribute("Password");
                                String Error = (String) request.getAttribute("Error");
                                if(Email==null||PassWord==null){
                                Email=""; PassWord="";
                                    }
                                %>
                                <form action="DispatchController">
                                    <div class="form-floating mb-3">
                                        <input type="email"value="<%= Email %>" class="form-control" id="floatingInput" placeholder="name@example.com" name="txtEmail">
                                        <label for="floatingInput">Email address</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input type="password" value="<%= PassWord %>" class="form-control" id="floatingPassword" placeholder="Password" name="txtPassword">
                                        <label for="floatingPassword">Password</label>
                                    </div>
                                    <div class="form-check mb-3">
                                        <input class="form-check-input" type="checkbox" value="" name="Remember" id="rememberPasswordCheck">
                                        <label class="form-check-label" for="rememberPasswordCheck">
                                            Remember password
                                        </label>
                                    </div>
                                        <div class="form-floating mb-3 LoiLogin">
                                            <%
                                            if(Error!=null){
                                            %>
                                            <p><%=Error%></p>
                                            
                                            <%}%>
                                    </div>
                                    <div class="d-grid">
                                        <button class="btn btn-lg btn-primary btn-login text-uppercase fw-bold mb-2" type="submit" name="btAction" value="Login">Sign in</button>
                                        <div class="text-center">
                                            <a class="small" href="RecoveryPassword.jsp">Forgot password?</a><br> 
                                        </div>
                                        <div class="text-center">
                                            <p class="LoginGoole">Login with Google</p>
                                        </div>
                                        <div class="icon-container iconGoole">
                                            <a class="LinkGoogle" href="https://accounts.google.com/o/oauth2/auth?scope=email%20profile%20openid&redirect_uri=http://localhost:9999/AttendanceSystem/loginGoogle&response_type=code&client_id=310236709220-reloijlvas77i2jdhkh43il56o7ebbn1.apps.googleusercontent.com&approval_prompt=force"><i class="fa-regular fa-envelope"></i></a>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
