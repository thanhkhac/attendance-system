<%-- 
    Document   : ImportEmployees
    Created on : Mar 8, 2024, 5:14:44 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <script src="assets/Bootstrap5/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> 
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <title>Thêm nhân viên</title>
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
            .modal-custom-size{
                min-width: 1250px;
                height: auto;
            }
            .information-items{
                margin-bottom: 10px;
            }
            .information-items label{
                min-width: 100px;
            }
            .information-items input{
                min-width: 200px;
            }
            .information-items select{
                min-width: 200px;
            }
            .modal-label{
                margin-top: 15px;
            }
            .modal-body{
                width: 100%;
            }
            .updated{
                background-color: #d4edda;
            }

            /*            #err-pagination-container
                        {
                            display: flex;
                            justify-content: flex-end;
                            margin-top: 20px;
                        }
                        #acc-pagination-container
                        {
                            display: flex;
                            justify-content: flex-end;
                            margin-top: 20px;
                        }*/

            .pagination-ul {
                list-style: none;
                padding: 0;
                display: flex;
                flex-wrap: wrap;
                justify-content: flex-end;
            }

            .pagination-ul li {
                margin: 5px;
            }

            .pagination-ul li a {
                text-decoration: none;
                padding: 8px 12px;
                border: 1px solid #ddd;
                border-radius: 4px;
                color: #333;
                background-color: #fff;
                cursor: pointer;
            }

            .pagination-ul li a:hover {
                background-color: #ddd;
            }
            .pagination-ul li.active a {
                background-color: #007bff; /* Set the background color for the active page */
                color: #fff; /* Set the text color for the active page */
            }

        </style>
    </head>
    <body>

        <%--<%@include file="Sidebar.jsp" %>--%>

        <c:set var="employee" value="${sessionScope.employees}" />
        <c:set var="isError" value="${sessionScope.isError}" />
        <c:set var="isAcceptable" value="${sessionScope.isAcceptable}" />
        <c:set var="departments" value="${sessionScope.departments}" />
        <c:set var="types" value="${sessionScope.types}" />
        <c:set var="roles" value="${sessionScope.roles}" />

        <div class="content">
            <h1 class="text-center">Import Employee </h1>
            <div class="content-redirect">
                <p><a href="ThanhCong.html">Home</a> | <a href="DispatchController?btAction=ViewEmployee">View All Employee</a> | Import From Excel</p>
            </div>
            <div class="content-file d-flex mt-5 justify-content-end justify-content-between flex-wrap">
                <div class="content-file-upload mt-2">
                    <form action="ReadEmployeeFromFileServlet" method="post" enctype="multipart/form-data">
                        <input class="mt-2" type="file" name="file">
                        <input class="btn btn-primary" type="submit" name="btAction" value="Upload">
                    </form>
                </div>
                <div class="content-file-dowload mt-2">
                    <form action="SendEmployeeFormattedFileServlet">
                        <input class="btn btn-success" type="submit" name="btAction"  value="Dowload Formatted File Excel">
                    </form>
                </div>
            </div>

            <c:if test="${not empty employees}">
                <div class="table-type-btns mt-5">
                    <h5 style="color: green"><i class="fa-regular fa-circle-check"></i> There are ${employees.size()} employee(s) loaded successfully !</h5>
                    <button onclick="Accept_Table()"
                            class="btn btn-success"
                            id="acceptable-table-btn"
                            >Acceptable Data Rows (${isAcceptable.size()})
                    </button>
                    <button onclick="Error_Table()"
                            class="btn btn-danger"
                            id="error-table-btn"
                            >Error Data Rows (${isError.size()})
                    </button>
                </div>
                <div class="content-table mt-3">
                    <table border="1" class="table table-hover table-responsive-md table-bordered">
                        <thead>
                            <tr id="table-header">
                                <th>Last Name</th>
                                <th>Middle Name</th>
                                <th>First Name</th>
                                <th>Phone</th>
                                <th>Email</th>
                                <th>CCCD</th>
                                <th>Gender</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        <div id="table-isAcceptable">
                            <c:forEach items="${isAcceptable}" var="e">
                                <tr class="accept-rows table-success">
                                    <td>${e.getLastName()}</td>
                                    <td>${e.getMiddleName()}</td>
                                    <td>${e.getFirstName()}</td>
                                    <td>${e.getPhoneNumber()}</td>
                                    <td>${e.getEmail()}</td>
                                    <td>${e.getCccd()}</td>
                                    <td>${e.getGender()?"Male":"Female"}</td>
                                    <td>
                                        <button id="${e.getEmployeeID()}" data-bs-toggle="modal" data-bs-target="#popupModal-${e.getEmployeeID()}"
                                                class="btn btn-primary"
                                                >Update</button>
                                    </td>
                                </tr>
                                <div class="modal fade" id="popupModal-${e.getEmployeeID()}" tabindex="-1"
                                     aria-labelledby="exampleModalLabel" aria-hidden="true" data-bs-backdrop="static">
                                    <div class="modal-dialog modal-custom-size modal-dialog-centered">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Employee #${e.getEmployeeID()} Information <span style="color: green">Acceptable</span> </h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div  class="modal-body d-flex p-3">
                                                <form onchange="check()"
                                                      action="UpdateTempEmployeeServlet"
                                                      id="update-form-${e.getEmployeeID()}"
                                                      class="update-forms"
                                                      class="row g-3">
                                                    <div class="information-items" style="max-width: 350px; margin-left: 10px">
                                                        <input type="hidden" name="tmpID" value="${e.getEmployeeID()}">
                                                        <label class="modal-label">Last Name</label><input oninput="checkByChange(event,${e.getEmployeeID()})"  id="lastName-${e.getEmployeeID()}" type="text" name="lastName" value="${e.getLastName()}"><br>
                                                        <label class="modal-label">Middle Name</label><input oninput="checkByChange(event,${e.getEmployeeID()})"  id="middleName-${e.getEmployeeID()}" type="text" name="middleName" value="${e.getMiddleName()}"><br>
                                                        <label class="modal-label">First Name</label><input oninput="checkByChange(event,${e.getEmployeeID()})"  id="firstName-${e.getEmployeeID()}" type="text" name="firstName" value="${e.getFirstName()}"><br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">Email</label><input oninput="checkByChange(event,${e.getEmployeeID()})"  id="email-${e.getEmployeeID()}" type="text" name="Email" value="${e.getEmail()}"><br>
                                                        <label class="modal-label">CCCD</label><input oninput="checkByChange(event,${e.getEmployeeID()})"  id="cccd-${e.getEmployeeID()}" type="text" name="CCCD" value="${e.getCccd()}"><br>
                                                        <label class="modal-label">Phone</label><input oninput="checkByChange(event,${e.getEmployeeID()})"  id="phone-${e.getEmployeeID()}" type="text" name="Phone" value="${e.getPhoneNumber()}"><br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">Gender</label>
                                                        <select onchange="checkByChange(event,${e.getEmployeeID()})" name="Gender" id="gender-${e.getEmployeeID()}">
                                                            <option ${e.getGender() == true?'selected':''} value="true">Nam</option>
                                                            <option ${e.getGender() == false?'selected':''}value="false">Nữ</option>
                                                        </select>

                                                        <br>
                                                        <label class="modal-label">BirthDate</label><input onchange="checkByChange(event,${e.getEmployeeID()})"  id="birthDate-${e.getEmployeeID()}" type="date" name="BirthDate" value="${e.getBirthDate()}" max="2024-01-01"><br>
                                                        <label class="modal-label">Password</label><input oninput="checkByChange(event,${e.getEmployeeID()})"  id="password-${e.getEmployeeID()}" type="text" name="Password" value="${e.getPassword()}"><br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">EmployeType</label>
                                                        <select onchange="checkByChange(event,${e.getEmployeeID()})"  name="EmployeeType" id="type-${e.getEmployeeID()}">
                                                            <c:forEach items="${types}" var="t">
                                                                <option 
                                                                    <c:if test="${e.getEmployeeTypeID() eq t.getEmployeeTypeID()}">selected=""</c:if>
                                                                    value="${t.getEmployeeTypeID()}">${t.getName()}</option>                                                          
                                                            </c:forEach>
                                                        </select>
                                                        <br>
                                                        <label class="modal-label">Department</label>
                                                        <select onchange="checkByChange(event,${e.getEmployeeID()})"  name="Department" id="department-${e.getEmployeeID()}">
                                                            <c:forEach items="${departments}" var="d">
                                                                <option 
                                                                    <c:if test="${e.getDepartmentID() eq d.getDepartmentID()}">selected=""</c:if>
                                                                    value="${d.getDepartmentID()}">${d.getName()}</option>                                                          
                                                            </c:forEach>
                                                        </select>
                                                        <br>
                                                        <label class="modal-label">Role</label>
                                                        <select onchange="checkByChange(event,${e.getEmployeeID()})"  name="Role" id="role-${e.getEmployeeID()}">
                                                            <c:forEach items="${roles}" var="r">
                                                                <option 
                                                                    <c:if test="${e.getRoleID() eq r.getRoleID()}">selected</c:if>
                                                                    value="${r.getRoleID()}">${r.getName()}</option>                                                          
                                                            </c:forEach>
                                                        </select>
                                                        <br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">StartDate</label><input onchange="checkByChange(event,${e.getEmployeeID()})"  id="startDate-${e.getEmployeeID()}" type="date" name="StartDate" value="${e.getStartDate()}" max="2099-01-01"><br>
                                                        <label class="modal-label">EndDate</label><input onchange="checkByChange(event,${e.getEmployeeID()})"  id="endDate-${e.getEmployeeID()}" type="date" name="EndDate" value="${e.getEndDate()}" max="2099-01-01"><br>
                                                    </div>
                                                </form>
                                            </div>
                                            <div id="body-ajax-${e.getEmployeeID()}">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            <div class="d-flex justify-content-end" style="width: 100%">
                                <c:set var="msg" value="${requestScope.SuccessMSG}" />
                                <c:if test="${not empty msg}">
                                    <h6 id="insert-btn"  style="color: green "><i class="fa-regular fa-circle-check"></i> ${msg}</h6>
                                </c:if>
                                </br>

                                <form id="insert_form" action="InsertImportedEmployeesServlet" method="post">

                                </form>
                                <input 
                                    <c:if test="${isAcceptable.size()<=0}">
                                        disabled=""
                                    </c:if>
                                    onclick="insertForm()"
                                    id="insert-btn"
                                    type="submit"
                                    class="btn btn-success d-none mb-3"
                                    value="Insert into Database">

                            </div>

                        </div>
                        <div>
                            <c:forEach items="${isError}" var="e">
                                <tr class="error-rows table-danger">
                                    <td>${e.getLastName()}</td><form action="">
                                    <input id="insert-btn" type="submit" class="btn btn-success d-none" value="Insert into Database">
                                </form>
                                <td>${e.getMiddleName()}</td>
                                <td>${e.getFirstName()}</td>
                                <td>${e.getPhoneNumber()}</td>
                                <td>${e.getEmail()}</td>
                                <td>${e.getCccd()}</td>
                                <td>${e.getGender()?"Male":"Female"}</td>
                                <td>
                                    <button  id="${e.getEmployeeID()}" data-bs-toggle="modal" data-bs-target="#popupModal-${e.getEmployeeID()}"
                                             class="btn btn-primary"
                                             >Update</button>
                                </td>
                                </tr>
                                <div class="modal fade" id="popupModal-${e.getEmployeeID()}" tabindex="-1"
                                     aria-labelledby="exampleModalLabel" aria-hidden="true" data-bs-backdrop="static">
                                    <div class="modal-dialog modal-custom-size modal-dialog-centered">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Employee #${e.getEmployeeID()} Information <span style="color: red">ERROR</span></h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div  class="modal-body d-flex p-3">
                                                <form 
                                                    action="UpdateTempEmployeeServlet"
                                                    id="update-form-${e.getEmployeeID()}"
                                                    class="update-forms row g-3"
                                                    >
                                                    <div class="information-items" style="max-width: 350px; margin-left: 30px">
                                                        <input type="hidden" name="tmpID" value="${e.getEmployeeID()}">
                                                        <label class="modal-label">Last Name</label><input oninput="checkByChange(event,${e.getEmployeeID()})" id="lastName-${e.getEmployeeID()}" type="text" name="lastName" value="${e.getLastName()}"><br>
                                                        <label class="modal-label">Middle Name</label><input oninput="checkByChange(event,${e.getEmployeeID()})"  id="middleName-${e.getEmployeeID()}" type="text" name="middleName" value="${e.getMiddleName()}"><br>
                                                        <label class="modal-label">First Name</label><input oninput="checkByChange(event,${e.getEmployeeID()})"  id="firstName-${e.getEmployeeID()}" type="text" name="firstName" value="${e.getFirstName()}"><br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">Email</label><input oninput="checkByChange(event,${e.getEmployeeID()})"  id="email-${e.getEmployeeID()}" type="text" name="Email" value="${e.getEmail()}"><br>
                                                        <label class="modal-label">CCCD</label><input oninput="checkByChange(event,${e.getEmployeeID()})"  id="cccd-${e.getEmployeeID()}" type="text" name="CCCD" value="${e.getCccd()}"><br>
                                                        <label class="modal-label">Phone</label><input oninput="checkByChange(event,${e.getEmployeeID()})"  id="phone-${e.getEmployeeID()}" type="text" name="Phone" value="${e.getPhoneNumber()}"><br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">Gender</label>
                                                        <select onchange="checkByChange(event, ${e.getEmployeeID()})" name="Gender" id="gender-${e.getEmployeeID()}">
                                                            <option ${e.getGender() == true?'selected':''} value="true">Nam</option>
                                                            <option ${e.getGender() == false?'selected':''}value="false">Nữ</option>
                                                        </select>
                                                        <br>
                                                        <label class="modal-label">BirthDate</label><input  oninput="checkByChange(event,${e.getEmployeeID()})"  id="birthDate-${e.getEmployeeID()}" type="date" name="BirthDate" value="${e.getBirthDate()}" max="2024-01-01"><br>
                                                        <label class="modal-label">Password</label><input  oninput="checkByChange(event,${e.getEmployeeID()})"  id="password-${e.getEmployeeID()}" type="text" name="Password" value="${e.getPassword()}"><br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">EmployeType</label>
                                                        <select onchange="checkByChange(event, ${e.getEmployeeID()})"  name="EmployeeType" id="type-${e.getEmployeeID()}">
                                                            <c:forEach items="${types}" var="t">
                                                                <option 
                                                                    <c:if test="${e.getEmployeeTypeID() eq t.getEmployeeTypeID()}">selected=""</c:if>
                                                                    value="${t.getEmployeeTypeID()}">${t.getName()}</option>                                                          
                                                            </c:forEach>
                                                        </select>
                                                        <br>
                                                        <label class="modal-label">Department</label>
                                                        <select onchange="checkByChange(event, ${e.getEmployeeID()})"  name="Department" id="department-${e.getEmployeeID()}">
                                                            <c:forEach items="${departments}" var="d">
                                                                <option 
                                                                    <c:if test="${e.getDepartmentID() eq d.getDepartmentID()}">selected=""</c:if>
                                                                    value="${d.getDepartmentID()}">${d.getName()}</option>                                                          
                                                            </c:forEach>
                                                        </select>
                                                        <br>
                                                        <label class="modal-label">Role</label>
                                                        <select onchange="checkByChange(event, ${e.getEmployeeID()})"  name="Role" id="role-${e.getEmployeeID()}">
                                                            <c:forEach items="${roles}" var="r">
                                                                <option 
                                                                    <c:if test="${e.getRoleID() eq r.getRoleID()}">selected</c:if>
                                                                    value="${r.getRoleID()}">${r.getName()}</option>                                                          
                                                            </c:forEach>
                                                        </select>
                                                        <br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">StartDate</label><input  onchange="checkByChange(event, ${e.getEmployeeID()})"  id="startDate-${e.getEmployeeID()}" type="date" name="StartDate" value="${e.getStartDate()}" max="2099-01-01"><br>
                                                        <label class="modal-label">EndDate</label><input  onchange="checkByChange(event, ${e.getEmployeeID()})"  id="endDate-${e.getEmployeeID()}" type="date" name="EndDate" value="${e.getEndDate()}" max="2099-01-01"><br>
                                                        <button style="margin-top: 20px" class="btn btn-danger" onclick="checkByChange(event, ${e.getEmployeeID()})">Check</button>
                                                    </div>
                                                </form>
                                            </div>
                                            <div id="body-ajax-${e.getEmployeeID()}"
                                                 class="border-1 border-success" >

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            <div class="d-flex justify-content-end" style="width: 100%">
                                <c:set var="ErrorMsg" value="${requestScope.ErrorMSG}" />
                                <c:if test="${not empty ErrorMsg}">
                                    <h6 id="delete-btn"  style="color: green "><i class="fa-regular fa-circle-check"></i> ${ErrorMsg}</h6>
                                </c:if>
                                </br>
                                <form id="delete_form" action="DeleteTempEmployeesServlet" method="post">

                                </form>
                                <input
                                    <c:if test="${isError.size()<=0}">
                                        disabled=""
                                    </c:if>
                                    onclick="deleteForm()"
                                    id="delete-btn"
                                    type="submit"
                                    class="btn btn-danger d-none mb-3"
                                    value="Delete All">
                            </div>
                        </div>
                        </tbody>
                    </table>
                </div>
                <div id="err-pagination-container">
                    <ul id="err-pagination" class="pagination-ul d-flex"></ul>
                </div>
                <div id="acc-pagination-container">
                    <ul id="acc-pagination" class="pagination-ul d-flex"></ul>
                </div>
            </c:if>
            <c:if test="${employees.size() < 0 or empty employees}">
                <div class="text-center mt-5">
                    <h2>  There is no employee data is loaded !</h2>
                    <h5  style="color: red"><i class="fa-solid fa-triangle-exclamation"></i> Make sure using formatted file to upload data !</h5>
                </div>
            </c:if>
        </div>

    </body>

    <script>


        document.addEventListener('DOMContentLoaded', function () {
            var error_rows = document.getElementsByClassName("error-rows");
            var acceptable_rows = document.getElementsByClassName("accept-rows");
            var table_head = document.getElementById("table-header");
            table_head.style.display = 'none';
            for (var i = 0; i < acceptable_rows.length; i++) {
                acceptable_rows[i].style.display = 'none';
            }
            for (var i = 0; i < error_rows.length; i++) {
                error_rows[i].style.display = 'none';
            }
        });


        function Accept_Table() {
            var error_rows = document.getElementsByClassName("error-rows");
            var acceptable_rows = document.getElementsByClassName("accept-rows");
            var table_head = document.getElementById("table-header");
            var insert_btn = document.getElementById("insert-btn");
            var pag_nav_acc = document.getElementById("acc-pagination-container");
            pag_nav_acc.style.display = 'block';
            var pag_nav_err = document.getElementById("err-pagination-container");
            pag_nav_err.style.display = 'none';
            if (insert_btn !== null) {
                console.log(insert_btn.value);
                insert_btn.classList.remove("d-none");
            }
            var delete_btn = document.getElementById("delete-btn");
            if (delete_btn !== null) {
                console.log(insert_btn.value);
                delete_btn.classList.add("d-none");
            }
            table_head.style.display = 'table-row';
            for (var i = 0; i < acceptable_rows.length; i++) {
                acceptable_rows[i].style.display = 'table-row';
            }
            for (var i = 0; i < error_rows.length; i++) {
                error_rows[i].style.display = 'none';
            }
            showPage_Acc(currentPage_acc);
        }


        function Error_Table() {
            var error_rows = document.getElementsByClassName("error-rows");
            var acceptable_rows = document.getElementsByClassName("accept-rows");
            var table_head = document.getElementById("table-header");
            var pag_nav_acc = document.getElementById("acc-pagination-container");
            pag_nav_acc.style.display = 'none';
            var pag_nav_err = document.getElementById("err-pagination-container");
            pag_nav_err.style.display = 'block';
            table_head.style.display = 'table-row';
            var insert_btn = document.getElementById("insert-btn");
            if (insert_btn !== null) {
                console.log(insert_btn.value);
                insert_btn.classList.add("d-none");
            }
            var delete_btn = document.getElementById("delete-btn");
            if (delete_btn !== null) {
                console.log(insert_btn.value);
                delete_btn.classList.remove("d-none");
            }
            for (var i = 0; i < acceptable_rows.length; i++) {
                acceptable_rows[i].style.display = 'none';
            }
            for (var i = 0; i < error_rows.length; i++) {
                error_rows[i].style.display = 'table-row';
            }
            showPage_Error(currentPage_err);
        }


        function submitForm(button) {
            var ID = button.id;
            console.log("update-forms-" + ID);
            var update_confirm = confirm("Save change ? ");
            console.log(update_confirm);
            if (update_confirm) {
                var update_form = document.getElementById("update-form-" + ID);
                update_form.submit();
            }
        }

        function deleteForm(event) {
            var delete_confirm = confirm("Delete all current data rows ?");
            console.log(delete_confirm);
            if (delete_confirm) {
                var delete_form = document.getElementById("delete_form");
                delete_form.submit();
            }
        }

        function insertForm() {
            var insert_confirm = confirm("Insert all acceptable data rows ?");
            console.log(insert_confirm);
            if (insert_confirm) {
                var insert_form = document.getElementById("insert_form");
                insert_form.submit();
            }
        }


        function check() {
            console.log("Hello");
        }

        function checkByChange(event, ID) {
            event.preventDefault();
            console.log("employee-" + ID);
            var txt_firstName = document.getElementById("firstName-" + ID).value;
            var txt_middleName = document.getElementById("middleName-" + ID).value;
            var txt_lastName = document.getElementById("lastName-" + ID).value;
            var txt_email = document.getElementById("email-" + ID).value;
            var txt_cccd = document.getElementById("cccd-" + ID).value;
            var txt_phone = document.getElementById("phone-" + ID).value;
            var txt_password = document.getElementById("password-" + ID).value;
            var txt_gender = document.getElementById("gender-" + ID).value;
            var txt_birthDate = document.getElementById("birthDate-" + ID).value;
            var txt_departmentID = document.getElementById("department-" + ID).value;
            var txt_typeID = document.getElementById("type-" + ID).value;
            var txt_roleID = document.getElementById("role-" + ID).value;
            var txt_startDate = document.getElementById("startDate-" + ID).value;
            var txt_endDate = document.getElementById("endDate-" + ID).value;
            var txt_id = ID;


            $.ajax({
                url: "/AttendanceSystem/HandleTempEmployeeAJAX",
                type: "get", //send it through get method
                data: {
                    firstName: txt_firstName,
                    middleName: txt_middleName,
                    lastName: txt_lastName,
                    Gender: txt_gender,
                    CCCD: txt_cccd,
                    Email: txt_email,
                    Password: txt_password,
                    Phone: txt_phone,
                    BirthDate: txt_birthDate,
                    Department: txt_departmentID,
                    EmployeeType: txt_typeID,
                    Role: txt_roleID,
                    StartDate: txt_startDate,
                    EndDate: txt_endDate,
                    tmpID: ID
                },
                success: function (data) {
                    var abody = document.getElementById("body-ajax-" + ID);
                    abody.innerHTML = data;
                    console.log("Success ! ");
                },
                error: function (xhr, error) {
                    console.log("Error: ", error);
                }
            });
        }

        var pageSize = 10; // Số lượng dòng mỗi trang
        var currentPage_err = 1; // Trang hiện tại
        var currentPage_acc = 1; // Trang hiện tại


        function showPage_Error(page) {
            event.preventDefault();
            var rows = document.getElementsByClassName('error-rows');
            var pageCount = Math.ceil(rows.length / pageSize);

            // Ẩn tất cả các dòng
            for (var i = 0; i < rows.length; i++) {
                rows[i].style.display = 'none';
            }

            // Hiển thị các dòng của trang hiện tại
            var startIndex = (page - 1) * pageSize;
            var endIndex = startIndex + pageSize;
            for (var i = startIndex; i < endIndex && i < rows.length; i++) {
                rows[i].style.display = 'table-row';
            }

            // Tạo nút điều hướng phân trang
            var paginationElement = document.getElementById('err-pagination');
            paginationElement.innerHTML = '';

            // Previous button
            var prevLi = document.createElement('li');
            var prevA = document.createElement('a');
            prevA.href = '#';
            prevA.innerHTML = 'Previous';
            prevA.addEventListener('click', function () {
                if (currentPage_err > 1) {
                    currentPage_err--;
                    showPage_Error(currentPage_err);
                }
            });
            prevLi.appendChild(prevA);
            paginationElement.appendChild(prevLi);

            // Add ellipsis if there are more than 10 pages
            if (pageCount > 5 && currentPage_err > 5) {
                var ellipsisLi = document.createElement('li');
                var ellipsisSpan = document.createElement('span');
                ellipsisSpan.innerHTML = '...';
                ellipsisLi.appendChild(ellipsisSpan);
                paginationElement.appendChild(ellipsisLi);
            }

            // Hiển thị các trang có thể chọn
            for (var i = Math.max(1, currentPage_err - 3); i <= Math.min(pageCount, currentPage_err + 3); i++) {
                var li = document.createElement('li');
                var a = document.createElement('a');
                a.href = '#';
                a.innerHTML = i;
                if (i === currentPage_err) {
                    li.classList.add('active'); // Add the 'active' class to highlight the current page
                }
                a.addEventListener('click', function (e) {
                    currentPage_err = parseInt(e.target.innerHTML);
                    showPage_Error(currentPage_err);
                });
                li.appendChild(a);
                paginationElement.appendChild(li);
            }

            // Add ellipsis if there are more than 10 pages
            if (pageCount > 4 && currentPage_err < pageCount - 4) {
                var ellipsisLi = document.createElement('li');
                var ellipsisSpan = document.createElement('span');
                ellipsisSpan.innerHTML = '...';
                ellipsisLi.appendChild(ellipsisSpan);
                paginationElement.appendChild(ellipsisLi);
            }

            // Next button
            var nextLi = document.createElement('li');
            var nextA = document.createElement('a');
            nextA.href = '#';
            nextA.innerHTML = 'Next';
            nextA.addEventListener('click', function () {
                if (currentPage_err < pageCount) {
                    currentPage_err++;
                    showPage_Error(currentPage_err);
                }
            });
            nextLi.appendChild(nextA);
            paginationElement.appendChild(nextLi);
        }

        function showPage_Acc(page) {
            event.preventDefault();
            var rows = document.getElementsByClassName('accept-rows');
            var pageCount = Math.ceil(rows.length / pageSize);

            // Ẩn tất cả các dòng
            for (var i = 0; i < rows.length; i++) {
                rows[i].style.display = 'none';
            }

            // Hiển thị các dòng của trang hiện tại
            var startIndex = (page - 1) * pageSize;
            var endIndex = startIndex + pageSize;
            for (var i = startIndex; i < endIndex && i < rows.length; i++) {
                rows[i].style.display = 'table-row';
            }

            // Tạo nút điều hướng phân trang
            var paginationElement = document.getElementById('acc-pagination');
            paginationElement.innerHTML = '';

            // Previous button
            var prevLi = document.createElement('li');
            var prevA = document.createElement('a');
            prevA.href = '#';
            prevA.innerHTML = 'Previous';
            prevA.addEventListener('click', function () {
                if (currentPage_acc > 1) {
                    currentPage_acc--;
                    showPage_Acc(currentPage_acc);
                }
            });
            prevLi.appendChild(prevA);
            paginationElement.appendChild(prevLi);

            // Add ellipsis if there are more than 10 pages
            if (pageCount > 5 && currentPage_acc > 5) {
                var ellipsisLi = document.createElement('li');
                var ellipsisSpan = document.createElement('span');
                ellipsisSpan.innerHTML = '...';
                ellipsisLi.appendChild(ellipsisSpan);
                paginationElement.appendChild(ellipsisLi);
            }

            // Hiển thị các trang có thể chọn
            for (var i = Math.max(1, currentPage_acc - 3); i <= Math.min(pageCount, currentPage_acc + 3); i++) {
                var li = document.createElement('li');
                var a = document.createElement('a');
                a.href = '#';
                a.innerHTML = i;
                if (i === currentPage_acc) {
                    li.classList.add('active'); // Add the 'active' class to highlight the current page
                }
                a.addEventListener('click', function (e) {
                    currentPage_acc = parseInt(e.target.innerHTML);
                    showPage_Acc(currentPage_acc);
                });
                li.appendChild(a);
                paginationElement.appendChild(li);
            }

            // Add ellipsis if there are more than 10 pages
            if (pageCount > 4 && currentPage_acc < pageCount - 4) {
                var ellipsisLi = document.createElement('li');
                var ellipsisSpan = document.createElement('span');
                ellipsisSpan.innerHTML = '...';
                ellipsisLi.appendChild(ellipsisSpan);
                paginationElement.appendChild(ellipsisLi);
            }

            // Next button
            var nextLi = document.createElement('li');
            var nextA = document.createElement('a');
            nextA.href = '#';
            nextA.innerHTML = 'Next';
            nextA.addEventListener('click', function () {
                if (currentPage_acc < pageCount) {
                    currentPage_acc++;
                    showPage_Acc(currentPage_acc);
                }
            });
            nextLi.appendChild(nextA);
            paginationElement.appendChild(nextLi);
        }

    </script>
</html>
