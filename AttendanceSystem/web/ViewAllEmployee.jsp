<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
              <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css" />
        <!--
                link js drop down
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
                link js drop down-->

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
                border-radius: 5px;
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
                border-radius: 5px;
                height: 500px;
                margin-top: 10px;
            }
            .tableFixHead thead th {
                position: sticky;
                top: 0;
                z-index: 0;
                background-color: #f27227;
                color: white;
            }


            tr.space-under > td {
                padding-bottom: 1em;
                cursor: pointer;
            }
            table {
                /* thằng !important là BỐ */
                border-collapse: separate !important;
                border-spacing: 0 15px;
            }
            table tr{
                border: 1px solid black;
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


            #popup {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.7);
            }

            .popup-content {
                width: 1100px;
                height: 700px;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background: #fff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
                display: flex;
            }
            .popup-content-left{
                background-color: #f27227;
                padding: 15px 15px;
                border-radius: 5px;
            }
            .popup-content-left img{
                max-width: 100%;
                /*                height: 80%;*/

            }
            .popup-content-right{
                background-color: white;
                margin-left: 10px;
                border-radius: 5px;
                padding: 15px 15px;
            }
            .popupBody-title{
                text-decoration: underline;
            }
            .popupBody-content{
                display: flex;
            }
            .content-items{
                margin-bottom: 15px;
            }
            .content-items label{
                max-width: 140px;
                color: #f27227;
                font-size: larger;
                font-weight: 600;
            }
            .content-items input{
                width: 100%;
                height: 40px;
                padding: 3px 5px;
                border: none;
                border-radius: 5px;
                background-color: #E8E8E8;
                font-weight: 600;
                font-size: large;
            }
            input[type="info"]:focus {
                outline: 1px solid #0072bb;
            }
            .popupBody-content-left{
                min-width: 50%;
                margin-top: 20px;
                padding-right: 10px;
            }
            .popupBody-content-right{
                min-width: 50%;
                margin-top: 20px;
                padding-left: 15px;
                padding-top: 150px;
                border-left: 1px solid #f27227;
            }
            .close {
                width: 40px;
                height: 30px;
                position: absolute;
                top: 30px;
                right: 20px;
                font-size: 50px;
                cursor: pointer;
                font-size: 18px;
                border-radius: 5px;
            }
            .update-button{
                padding-bottom: 10px;
                border-radius: 5px;
                width: 150px;
                height: 38px;
                font-size: 20px;
                margin-top: 43px;
                margin-left: 70px;
            }
            .add-button{
                margin-top: 20px;
            }
            .add-button button{
                width: 200px;
                height: 40px;
                border: none;
                background-color: #0072bb;
                color: white;
                font-size: larger;
                font-weight: 600;
                border-radius: 5px;
            }
            .add-button button:hover{
                background-color: white;
                border: 3px solid #0072bb;
                color: #0072bb;
            }

            @media screen and (orientation: portrait) {


                #content {
                    margin-left: 0;
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
                .popup-content{
                    max-width: 100%;
                }
                .popup-content-left{
                    display: none;
                }
                .popupBody-content{
                    display: block;
                    flex-direction: column;
                    overflow-y: auto;
                    max-height: 600px;
                }
                .popupBody-content-right{
                    border: none;
                    padding: 0;
                }
                .update-button{
                    margin-top: 10px;
                    margin-left: 90px;
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
        <c:set var="ListE" value="${requestScope.ListE}" />
        <%@include file="Sidebar.jsp" %>
        <div id="content">
            <div>
                <form action="FilterAJAXServlet" class="search-filter-bar" >
                    <div class="input-group mb-3 search-bar">
                        <span class="input-group-text" id="basic-addon1">Search...</span>
                        <input 
                            type="text" 
                            class="form-control" 
                            placeholder="Name" 
                            aria-label="Name" 
                            aria-describedby="basic-addon1"
                            name="txtSearchValue"
                            >
                    </div>
                    <div class="input-group mb-3 select-bar">
                        <label class="input-group-text" for="inputGroupSelect01">Department</label>
                        <select class="form-select" id="inputGroupSelect01" name="txtDepartment">
                            <option selected>Choose...</option>
                            <option value="1">HR</option>
                            <option value="2">Marketing</option>
                            <option value="3">IT</option>
                        </select>
                    </div>
                    <div class="input-group mb-3 select-bar">
                        <label class="input-group-text" for="inputGroupSelect01">Type</label>
                        <select class="form-select" id="inputGroupSelect02" name="txtType">
                            <option selected>Choose...</option>
                            <option value="1">Part-Time</option>
                            <option value="2">Full-Time</option>
                            <option value="3">Intern</option>

                        </select>
                    </div>
                    <div class="input-group mb-3 select-bar">
                        <label class="input-group-text" for="inputGroupSelect01">Order</label>
                        <select class="form-select" id="inputGroupSelect03" name="txtOrder">
                            <option selected>Choose...</option>
                            <option value="1">A -> Z</option>
                            <option value="2">Z -> A</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="container table-responsive tableFixHead">
                <table class="table table-hover">
                    <thead>
                    <th>EmployeeID</th>
                    <th>LastName</th>
                    <th>MiddleName</th>
                    <th>FirstName</th>
                    <th>Email</th>
                    </thead>
                    <tbody>
                        <c:forEach items="${List}" var="a">
                        <div class="table-row-container">
                            <tr class="table-primary space-under employeeRow" data-employee-id="${a.getEmployeeID()}">
                                <td>${a.getEmployeeID()}</td>
                                <td>${a.getLastName()}</td>
                                <td>${a.getMiddleName()}</td>
                                <td>${a.getFirstName()}</td>
                                <td>${a.getEmail()}</td>
                                <td style="display: none">${a.getGender()}</td>
                                <td style="display: none">${a.getBirthDate()}</td>
                                <td style="display: none">${a.getCccd()}</td>
                                <td style="display: none">${a.getPhoneNumber()}</td>
                                <td style="display: none">${a.getEmployeeTypeID()}</td>
                                <td style="display: none">${a.getDepartmentID()}</td>
                                <td style="display: none">${a.getRoleID()}</td>
                                <td style="display: none">${a.getStartDate()}</td>
                                <td style="display: none">${a.getEndDate()}</td>
                            </tr>
                        </div>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="add-button">
                <button>+ Add Employee ...</button>
            </div>
            <div id="popup" class="popup">
                <div id="popupContent" class="popup-content">
                    <button class="btn-danger close" onclick="closePopup()">X</button>
                    <div id="popupPicture" class="popup-content-left col-lg-4">
                        <img src="https://t4.ftcdn.net/jpg/03/49/49/79/360_F_349497933_Ly4im8BDmHLaLzgyKg2f2yZOvJjBtlw5.jpg" alt="DefautPicture"/>
                    </div>
                    <div id="popupBody"  class="popup-content-right col-lg-8"></div>
                </div>
            </div>
        </div>

        <script src="assets/Bootstrap5/js/bootstrap.min.js"></script>
        <script>
                        document.addEventListener("DOMContentLoaded", function () {
                            const employeeRows = document.querySelectorAll(".employeeRow");
                            employeeRows.forEach((row) => {
                                row.addEventListener("click", function () {
                                    const id = row.getAttribute("data-employee-id");
                                    const firstName = row.cells[3].innerText;
                                    const middleName = row.cells[2].innerText;
                                    const lastName = row.cells[1].innerText;
                                    const email = row.cells[4].innerText;
                                    const gender = row.cells[5].innerText;
                                    const birthDate = row.cells[6].innerText;
                                    const cccd = row.cells[7].innerText;
                                    const phoneNumber = row.cells[8].innerText;
                                    const employeeType = row.cells[9].innerText;
                                    const department = row.cells[10].innerText;
                                    const role = row.cells[11].innerText;
                                    const startDate = row.cells[12].innerText;
                                    const endDate = row.cells[13].innerText;
                                    console.log("Clicked row:", id, firstName, middleName, lastName, email, gender, birthDate, cccd, phoneNumber, employeeType, department, role, startDate, endDate);
                                    displayPopup(id, firstName, middleName, lastName, email, gender, birthDate, cccd, phoneNumber, employeeType, department, role, startDate, endDate);
                                });
                            });


                        });
                        function displayPopup(id, firstName, middleName,
                                lastName, email, gender, birthDate, cccd, phoneNumber, employeeType, department, role, startDate, endDate) {
                            const fullName = lastName + " " + middleName + " " + firstName;
                            const popup = document.getElementById("popup");
                            const popupTitle = document.getElementById("popupTitle");
                            const popupBody = document.getElementById("popupBody");
//                            popupTitle.innerText = `EmployeeID : \${id}`;
                            popupBody.innerHTML = `<div class="popupBody-container">
                                                        <h3 class="popupBody-title">Employee Information</h3>
                                                        <div class="popupBody-content">
                                                            <div class="popupBody-content-left">
                                                                <div class="content-items">
                                                                    <label>Full Name:</label>
                                                                    <input type="info" value="\${fullName}" readonly>
                                                                </div>
                                                                <div class="content-items">
                                                                    <label>Gender: </label>
                                                                    <input type="info" value="\${gender}" readonly>
                                                                </div>
                                                                <div class="content-items">
                                                                    <label>Email: </label>
                                                                    <input type="info" value="\${email}" readonly>
                                                                </div>
                                                                <div class="content-items">
                                                                    <label>CCCD: </label>
                                                                    <input type="info" value="\${cccd}" readonly>
                                                                </div>
                                                                <div class="content-items">
                                                                    <label>Phone: </label>
                                                                    <input type="info" value="\${phoneNumber}" readonly>
                                                                </div>
                                                                <div class="content-items">
                                                                    <label>Birth Day: </label>
                                                                    <input type="info" value="\${birthDate}" readonly>
                                                                </div>
                                                                <button class="btn-success update-button">Update</button>
                                                            </div>
                                                            
                                                            
                                                            <div class="popupBody-content-right">
                                                                <div class="content-items">
                                                                    <label>Type: </label>
                                                                    <input type="info" value="\${employeeType}" readonly>
                                                                </div>
                                                                <div class="content-items">
                                                                    <label>Department: </label>
                                                                    <input type="info" value="\${department}" readonly>
                                                                </div>
                                                                <div class="content-items">
                                                                    <label>Role: </label>
                                                                    <input type="info" value="\${role}" readonly>
                                                                </div>
                                                                <div class="content-items">
                                                                    <label>Start Date: </label>
                                                                    <input type="info" value="\${startDate}" readonly>
                                                                </div>
                                                                <div class="content-items">
                                                                    <label>End Date: </label>
                                                                    <input type="info" value="\${endDate}" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                   </div> `;
                            popup.style.display = "block";
//                            console.log("Data: ", id,fullName, email, gender, birthDate, cccd, phoneNumber);
                        }
                        function closePopup() {
                            const popup = document.getElementById("popup");
                            popup.style.display = "none";
                        }
        </script>
    </body>
</html>
