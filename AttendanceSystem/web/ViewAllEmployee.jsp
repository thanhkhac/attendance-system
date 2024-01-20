<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <!--        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css" />
        
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
                background-color: #E8E8E8;
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
            .popupBody-content h5{
                border-bottom: 1px solid #f27227;
                margin-bottom: 20px;
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
                padding-top: 200px;
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
                background-color: red;
                border-radius: 5px;
            }
            .update-button{
                padding-bottom: 10px;
                border-radius: 5px;
                width: 150px;
                height: 38px;
                font-size: 20px;
                margin-top: 100px;
                margin-left: 70px;
            }

        </style>
    </head>
    <body>

        <c:set var="List" value="${requestScope.List}" />
        <c:set var="ListDE" value="${requestScope.ListDE}" />
        <%@include file="Sidebar.jsp" %>
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
                        <option value="3">Intern</option>

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
                            <tr class="table-primary space-under employeeRow" data-employee-id="${a.getEmployeeID()}">
                                <td>${a.getEmployeeID()}</td>
                                <td>${a.getFirstName()}</td>
                                <td>${a.getMiddleName()}</td>
                                <td>${a.getLastName()}</td>
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
                        </c:forEach>
                    </tbody>
                </table>
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
                                    const firstName = row.cells[1].innerText;
                                    const middleName = row.cells[2].innerText;
                                    const lastName = row.cells[3].innerText;
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
                                                        <h4 class="popupBody-title">Employee Information</h4>
                                                        <div class="popupBody-content">
                                                            <div class="popupBody-content-left">
                                                                <h5>FullName: \${fullName}</h5>
                                                                <h5>Gender: \${gender}</h5>
                                                                <h5>Email: \${email}</h5>
                                                                <h5>CCCD: \${cccd}</h5>
                                                                <h5>Birth Day: \${birthDate}</h5>
                                                                <h5>Phone Number: \${phoneNumber}</h5>
                                                                <button class="btn-success update-button">Update</button>
                                                            </div>
                                                            <div class="popupBody-content-right">
                                                                <h5>EmployeeType: Part-Time</h5>
                                                                <h5>Department: Phong Tiep Thi</h5>
                                                                <h5>Role: Department Manager</h5>
                                                                <h5>Start Date: \${startDate}</h5>
                                                                <h5>End Date: \${endDate}</h5>
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
