<%-- 
    Document   : Sidebar
    Created on : Jan 19, 2024, 2:01:56 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Side bar</title>
        <!--<link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css" />-->

        <!--link js drop down-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <!--link js drop down-->

        <style>
            #sidebar {
                height: 100vh;
                width: 16%;
                background-color: #0072bb;
                color: white;
                position: fixed;
                left: 0;
                transition: left 0.3s ease;
            }

            #sidebar a {
                display: block;
                padding: 15px;
                text-decoration: none;
                color: white;
            }

            #sidebar a:hover {
                background-color: #f27227;
            }

            #navbar {
                display: none;
                background-color: #0072bb;
                color: white;
                padding: 10px;
                text-align: center;
            }
            .dropdown{
                max-width: 50px;
            }

            .dropdown-menu {
                display: none;
                position: absolute;
                background-color: #0072bb !important;
                min-width: 160px;
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
                z-index: 2;
            }

            .dropdown-menu button {
                color: white;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
            }

            .dropdown-menu .dropdown-item:hover {
                background-color: #f27227;
                color: white;
            }
            @media screen and (orientation: portrait) {
                #sidebar {
                    left: -250px;
                }
                #navbar {
                    display: block;
                }


            }
        </style>
    </head>
    <body>
        <div id="sidebar">
            <a href="ThanhCong.html">Home</a>
            <a href="#">About</a>
            <a href="#">Services</a>
            <a href="#">Contact</a>
        </div>
        <div id="navbar">
            <div class="dropdown">
                <button
                    class="btn btn-success dropdown-toggle"
                    type="button"
                    data-toggle="dropdown"
                    >
                    Menu
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <button class="dropdown-item" type="button">Home</button>
                    <button class="dropdown-item" type="button">About</button>
                    <button class="dropdown-item" type="button">Service</button>

                    <div class="dropdown-divider"></div>
                    
                    <form action="DispatchController" method="Post">
                        <button name="btAction" class="dropdown-item " type="submit" value="Logout">
                            Logout
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
