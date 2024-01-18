<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

        <title>Attendance_Checking_System</title>
        <style>

            body {
                font-family: "Arial", sans-serif;
                margin: 0;
                padding: 0;
            }

            .tableFixHead::-webkit-scrollbar {
                width: 10px;
            }
            .tableFixHead::-webkit-scrollbar-track {
                background-color: white; /* Change to background color */
            }
            /* Style the thumb (the draggable part of the scroll bar) */
            .tableFixHead::-webkit-scrollbar-thumb {
                background-color: #16B14B; /* Change to thumb color */
                border-radius: 6px;    /*Add rounded corners to the thumb*/
            }

            .tableFixHead {
                /*margin-top: 100px;*/
                overflow: auto;
                border: 1px solid #f27227;
                height: 500px;
                margin-top: 10px;
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
                color: white;
            }

            .search-filter-bar{
                display: flex;
                justify-content: space-between;
                background-color: #f27227;
                max-height: 53px;
                padding: 6px 6px;
                border-radius: 5px;
                flex-wrap: wrap;
            }
            .search-bar{
                max-width: 400px;
                height: 40px;
            }
            .select-bar{
                max-width: 250px;
                height: 40px;
            }
            @media screen and (orientation: portrait) {
                #sidebar {
                    left: -250px;
                }

                #content {
                    margin-left: 0;
                }

                #navbar {
                    display: block;
                }

                .tableFixHead{
                    margin-top: 20px;
                    max-width: 100%;
                }

                .search-filter-bar{
                    max-height: 500px;
                    padding-top: 24px;
                }
                .search-bar{
                    max-width: 350px;
                }
                .select-bar{
                    max-width: 350px;
                }
                .input-group{
                    flex-basis: 100%;
                }
            }
            @media (max-width:1450px) {
                .tableFixHead{
                    margin-top: 20px;
                    max-width: 100%;
                }
                .search-filter-bar{
                    max-height: 400px;
                    justify-content: space-evenly;
                    padding-top: 22px;
                }
                .input-group{
                    flex-basis: 100%;
                    max-width: 400px;
                }

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
            <div class="search-filter-bar">
                <div class="input-group mb-3 search-bar">
                    <span class="input-group-text" id="basic-addon1">Search...</span>
                    <input 
                        type="text" 
                        class="form-control" 
                        placeholder="Name" 
                        aria-label="Name" 
                        aria-describedby="basic-addon1"
                        >
                </div>
                <div class="input-group mb-3 select-bar">
                    <label class="input-group-text" for="inputGroupSelect01">Department</label>
                    <select class="form-select" id="inputGroupSelect01">
                        <option selected>Choose...</option>
                        <option value="1">HR</option>
                        <option value="2">Marketing</option>
                        <option value="3">IT</option>
                    </select>
                </div>
                <div class="input-group mb-3 select-bar">
                    <label class="input-group-text" for="inputGroupSelect01">Type</label>
                    <select class="form-select" id="inputGroupSelect02">
                        <option selected>Choose...</option>
                        <option value="1">Part-Time</option>
                        <option value="2">Full-Time</option>
                    </select>
                </div>
                <div class="input-group mb-3 select-bar">
                    <label class="input-group-text" for="inputGroupSelect01">Order</label>
                    <select class="form-select" id="inputGroupSelect03">
                        <option selected>Choose...</option>
                        <option value="1">A -> Z</option>
                        <option value="2">Z -> A</option>
                    </select>
                </div>
            </div>
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
