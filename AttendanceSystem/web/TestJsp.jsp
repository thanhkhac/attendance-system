<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@page
    contentType="text/html" pageEncoding="UTF-8"%>
    <!DOCTYPE html>

    <html lang="en">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1" />
            <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css" />

            <!--link js drop down-->
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
            <!--link js drop down-->

            <title>Bootstrap 5 Dropdown Menu</title>
            <style>
                .tableFixHead {
                    margin-top: 200px;
                    overflow: auto;
                }
                .tableFixHead thead th {
                    position: sticky;
                    top: 0;
                    z-index: 1;
                    background-color: #f27227;
                    color: white;
                }
                tr.space-under > td {
                    padding-bottom: 1em;
                }
                table {
                    border-collapse: separate;
                    border-spacing: 0 10px;
                }

                body {
                    font-family: "Arial", sans-serif;
                    margin: 0;
                    padding: 0;
                }

                #sidebar {
                    height: 100vh;
                    width: 250px;
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

                #content {
                    margin-left: 250px;
                    padding: 16px;
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
                    background-color: #0072bb;
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
                }

                @media (max-width: 768px) {
                    #sidebar {
                        left: -250px;
                    }

                    #content {
                        margin-left: 0;
                    }

                    #navbar {
                        display: block;
                    }

                    /*                .dropdown-menu {
                                display: block;
                            }*/
                }
            </style>
        </head>
        <body>
            <c:set var="List" value="${requestScope.List}" />

            <div id="sidebar">
                <a href="#">Home</a>
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
                        <button class="dropdown-item" type="button">
                            Logout
                        </button>
                    </div>
                </div>
            </div>

            <div id="content">
                <div class="container table-responsive tableFixHead">
                    <table class="table table-hover">
                        <thead>
                        <th>EmployeeID</th>
                        <th>FirstName</th>
                        <th>MiddleName</th>
                        <th>LastName</th>
                        <th>Email</th>
                        </thead>
                        <tbody>
                            <c:forEach items="${List}" var="a">
                                <tr class="table-primary space-under">
                                    <td>${a.getEmployeeID()}</td>
                                    <td>${a.getFirstName()}</td>
                                    <td>${a.getMiddleName()}</td>
                                    <td>${a.getLastName()}</td>
                                    <td>${a.getEmail()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <script src="assets/Bootstrap5/js/bootstrap.min.js"></script>
        </body>
    </html>
