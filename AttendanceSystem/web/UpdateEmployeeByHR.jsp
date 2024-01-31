<%-- 
    Document   : UpdateEmployeeByHR
    Created on : Jan 22, 2024, 10:24:23 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">-->
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <style>
            body {
                /*margin-top: 20px;*/
                background-color: #f2f6fc;
                font-family: sans-serif;
            }

            .card {
                box-shadow: 0 0.15rem 1.75rem 0 rgb(33 40 50 / 15%);
            }
            .card .card-header {
                font-weight: 600;
                background-color: #f27227;
                color: white;
            }
            .card-header:first-child {
                border-radius: 0.35rem 0.35rem 0 0;
                margin-bottom: 20px;
            }

            .card-header {
                padding: 1rem 1.35rem;
                margin-bottom: 0;
                background-color: rgba(33, 40, 50, 0.03);
                border-bottom: 1px solid rgba(33, 40, 50, 0.125);
            }
            .form-control {
                display: block;
                width: 100%;
                padding: 0.875rem 1.125rem;
                font-size: 0.875rem;
                font-weight: 400;
                line-height: 1;
                color: #69707a;
                background-color: #E8E8E8;
                background-clip: padding-box;
                border: 1px solid #c5ccd6;
                -webkit-appearance: none;
                -moz-appearance: none;
                appearance: none;
                border-radius: 0.35rem;
                transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
            }
            .form-select{
                background-color: #fff;
            }
            .select-items select{
                margin-bottom: 10px;
            }
            .input-name input{
                margin-bottom: 10px;
            }
            .form-output{
                display:flex;
            }
            .form-output{
                margin-left: 150px;
            }
            .form-output p{
                color: blue;
            }
            .err{
                color: red;
            }
            .err i{
                margin-right: 10px;
            }
            .whole-content{
                padding-left: 150px;
            }
            @media screen and (orientation: portrait) {
                .whole-content{
                    padding: 0;
                }
                .form-output-error{
                    margin-left: 50px;
                }
            }
            @media (min-width:1000px) {
                .whole-content{
                    padding-left: 200px;
                    ;
                }

            }
        </style>
        <%@include file="Sidebar.jsp" %>
    </head>
    <body>
        <c:set var="ListDepartment" value="${requestScope.ListDepartment}" />
        <c:set var="ListType" value="${requestScope.ListType}" />
        <c:set var="ListRole" value="${requestScope.ListRole}" />
        <c:set var="Employee" value="${requestScope.Employee}" />
        <c:set var="Err" value="${requestScope.Err}" />
        <c:set var="msg" value="${requestScope.msg}" />


        <c:set var="txt_firstName" value="${requestScope.txt_firstName}" />
        <c:set var="txt_middleName" value="${requestScope.txt_middleName}" />
        <c:set var="txt_lastName" value="${requestScope.txt_lastName}" />
        <c:set var="txt_email" value="${requestScope.txt_email}" />
        <c:set var="txt_phoneNumber" value="${requestScope.txt_phoneNumber}" />
        <c:set var="txt_password" value="${requestScope.txt_password}" />
        <c:set var="txt_cccd" value="${txt_cccd}" />
        <c:set var="txt_birthDate" value="${txt_birthDate}" />



        <div class="container-xxl px-4">
            <!-- Account page navigation-->
            <!-- <nav class="nav nav-borders"></nav> -->
            <!-- <hr class="mt-0 mb-4" /> -->
            <div class="row whole-content">
                <div class="col-xl-12">
                    <!-- Account details card-->
                    <div class="card mb-4 mt-4">
                        <div class="card-header">Employee Information</div>
                        <div class="card-body">
                            <form action="DispatchController" method="Post">

                                <!-- Form Row-->
                                <div class="row gx-3 mb-3">
                                    <!-- Form Group (first name)-->
                                    <div class="col-md-4 input-name">
                                        <label class="small mb-1" for="inputFirstName"
                                               >First name</label
                                        >
                                        <input
                                            class="form-control"
                                            id="inputFirstName"
                                            type="text"
                                            name="txt_firstName"
                                            <c:if test="${empty txt_firstName}" >
                                                value="${Employee.getFirstName()}"
                                            </c:if>
                                            <c:if test="${not empty txt_firstName && txt_firstName ne 'Null' }" >
                                                value="${txt_firstName}"
                                            </c:if>
                                            <c:if test="${txt_firstName eq 'Null'}" >
                                                value=""
                                            </c:if>
                                            />
                                        <c:if test="${Err.getNull_error() !=null}" >
                                            <p class="err"><i class="fa-solid fa-triangle-exclamation"></i>${Err.getNull_error()}</p>
                                            </c:if>
                                            <c:if test="${Err.getName_format_error() !=null}" >
                                            <p class="err"><i class="fa-solid fa-triangle-exclamation"></i>${Err.getName_format_error()}</p>
                                            </c:if>
                                    </div>

                                    <!-- Form Group (middle name)-->
                                    <div class="col-md-4 input-name">
                                        <label class="small mb-1" for="inputLastName"
                                               >Middle name</label
                                        >
                                        <input
                                            class="form-control"
                                            id="inputLastName"
                                            type="text"
                                            name="txt_middleName"
                                            <c:if test="${empty txt_middleName}" >
                                                value="${Employee.getMiddleName()}"
                                            </c:if>
                                            <c:if test="${not empty txt_middleName && txt_middleName ne 'Null' }" >
                                                value="${txt_middleName}"
                                            </c:if>
                                            <c:if test="${txt_middleName eq 'Null'}" >
                                                value=""
                                            </c:if>
                                            />
                                    </div>
                                    <!-- Form Group (last name)-->
                                    <div class="col-md-4 input-name">
                                        <label class="small mb-1" for="inputLastName"
                                               >Last name</label
                                        >
                                        <input
                                            class="form-control"
                                            id="inputLastName"
                                            type="text"
                                            name="txt_lastName"
                                            <c:if test="${empty txt_lastName}" >
                                                value="${Employee.getLastName()}"
                                            </c:if>
                                            <c:if test="${not empty txt_lastName && txt_lastName ne 'Null' }" >
                                                value="${txt_lastName}"
                                            </c:if>
                                            <c:if test="${txt_lastName eq 'Null'}" >
                                                value=""
                                            </c:if>
                                            />
                                    </div>
                                </div>
                                <!-- Form Row        -->
                                <div class="row gx-3 mb-3">
                                    <!-- Form Group (gender)-->
                                    <div class="col-md-4">
                                        <label class="small mb-1">Gender</label>
                                        <div class="form-check">
                                            <div class="form-check-items">
                                                <input
                                                    class="form-check-input"
                                                    type="radio"
                                                    id="inputGender-Male"
                                                    name="txt_gender"
                                                    value="true"
                                                    <c:if test="${Employee.getGender()==true}" >checked</c:if>
                                                        />
                                                    <label class="form-check-label" for="inputGender-Male">
                                                        Nam
                                                    </label>
                                                </div>
                                                <div class="form-check-items">
                                                    <input
                                                        class="form-check-input"
                                                        type="radio"
                                                        id="inputGender-Female"
                                                        name="txt_gender"
                                                        value="false"
                                                    <c:if test="${Employee.getGender()==false}" >checked</c:if>
                                                        />
                                                    <label
                                                        class="form-check-label"
                                                        for="inputGender-Female"
                                                        >
                                                        Nữ
                                                    </label>
                                                </div>
                                                <!--                                                <div class="form-check-items">
                                                                                                    <input
                                                                                                        class="form-check-input"
                                                                                                        type="radio"
                                                                                                        id="inputGender-other"
                                                                                                        name="txt_gender"
                                                                                                        value="null"
                                            <c:if test="${Employee.getGender() == null}" >checked</c:if>
                                                />
                                            <label class="form-check-label" for="inputGender-other">
                                                Khác
                                            </label>
                                        </div>-->
                                            </div>
                                        </div>

                                        <!-- Form Group (location)-->
                                        <div class="col-md-4">
                                            <label class="small mb-1" for="inputCccd"
                                                   >Căn Cước Công Dân
                                            </label>
                                            <input
                                                class="form-control"
                                                id="inputLocation"
                                                type="text"
                                                placeholder="CCCD"
                                                name="txt_cccd"
                                            <c:if test="${empty txt_cccd}" >
                                                value="${Employee.getCccd()}"
                                            </c:if>
                                            <c:if test="${not empty txt_cccd}" >
                                                value="${txt_cccd}"
                                            </c:if>
                                            />
                                        <c:if test="${Err.getCccd_format_error() !=null}" >
                                            <p class="err"><i class="fa-solid fa-triangle-exclamation"></i>${Err.getCccd_format_error()}</p>
                                            </c:if>
                                    </div>
                                </div>
                                <!-- Form Group (email address)-->
                                <div class="row gx-3 mb-3">
                                    <div class="col-md-6">
                                        <label class="small mb-1" for="inputEmailAddress"
                                               >Email address</label
                                        >
                                        <input
                                            class="form-control"
                                            id="inputEmailAddress"
                                            type="email"
                                            placeholder="Enter your email address"
                                            name="txt_email"
                                            <c:if test="${empty txt_email}" >
                                                value="${Employee.getEmail()}"
                                            </c:if>
                                            <c:if test="${not empty txt_email}" >
                                                value="${txt_email}"
                                            </c:if>
                                            />
                                        <c:if test="${Err.getEmail_format_error() !=null}" >
                                            <p class="err"><i class="fa-solid fa-triangle-exclamation"></i>${Err.getEmail_format_error()}</p>
                                            </c:if>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="small mb-1" for="inputPassword"
                                               >Pasword</label
                                        >
                                        <input
                                            class="form-control"
                                            id="inputPasword"
                                            type="text"
                                            placeholder="Password"
                                            name="txt_password"
                                            <c:if test="${empty txt_password}" >
                                                value="${Employee.getPassword()}"
                                            </c:if>
                                            <c:if test="${not empty txt_password}" >
                                                value="${txt_password}"
                                            </c:if>
                                            />
                                        <c:if test="${Err.getPassword_format_error() !=null}" >
                                            <p class="err"><i class="fa-solid fa-triangle-exclamation"></i>${Err.getPassword_format_error()}</p>
                                            </c:if>
                                    </div>
                                </div>
                                <!-- Form Row-->
                                <div class="row gx-3 mb-3">
                                    <!-- Form Group (phone number)-->
                                    <div class="col-md-6">
                                        <label class="small mb-1" for="inputPhone"
                                               >Phone number</label
                                        >
                                        <input
                                            class="form-control"
                                            id="inputPhone"
                                            type="tel"
                                            placeholder="Enter your phone number"
                                            name="txt_phoneNumber"
                                            <c:if test="${empty txt_phoneNumber}" >
                                                value="${Employee.getPhoneNumber()}"
                                            </c:if>
                                            <c:if test="${not empty txt_phoneNumber}" >
                                                value="${txt_phoneNumber}"
                                            </c:if>
                                            />
                                        <c:if test="${Err.getPhone_format_error() !=null}" >
                                            <p class="err"><i class="fa-solid fa-triangle-exclamation"></i>${Err.getPhone_format_error()}</p>
                                            </c:if>
                                    </div>
                                    <!-- Form Group (birthday)-->
                                    <div class="col-md-6">
                                        <label class="small mb-1" for="inputBirthday"
                                               >Birthday</label
                                        >
                                        <input
                                            class="form-control"
                                            id="inputBirthday"
                                            type="date"
                                            name="txt_birthday"
                                            placeholder="Enter your birthday"
                                            <c:if test="${empty txt_birthDate}" >
                                                value="${Employee.getBirthDate()}"
                                            </c:if>
                                            <c:if test="${not empty txt_birthDate}" >
                                                value="${txt_birthDate}"
                                            </c:if>
                                            />
                                        <c:if test="${Err.getDate_invalid() !=null}" >
                                            <p class="err"><i class="fa-solid fa-triangle-exclamation"></i>${Err.getDate_invalid()}</p>
                                            </c:if>
                                    </div>
                                </div>
                                <div class="row gx-3 mb-3">
                                    <div class="card-header">Specification</div>
                                    <!-- Form Select (department)-->
                                    <div class="col-md-4 select-items">
                                        <label class="large mb-1" for="department"
                                               >Department</label
                                        >
                                        <select
                                            class="form-select form-select-sm form-control-lg"
                                            aria-label=".form-select-lg "
                                            id="department"
                                            name="txt_departmentID"
                                            >
                                            <c:forEach items="${ListDepartment}" var="d">
                                                <option 
                                                    <c:if test="${Employee.getDepartmentID() == d.getDepartmentID()}" > 
                                                        selected</c:if>
                                                    value="${d.getDepartmentID()}">${d.getName()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-4 select-items">
                                        <label class="large mb-1" for="department">Type</label>
                                        <select
                                            class="form-select form-select-sm form-control-lg"
                                            aria-label=".form-select-lg "
                                            id="Type"
                                            name="txt_typeID"
                                            >
                                            <c:forEach items="${ListType}" var="t">
                                                <option 
                                                    <c:if test="${Employee.getEmployeeTypeID() == t.getEmployeeTypeID()}" > 
                                                        selected</c:if>
                                                    value="${t.getEmployeeTypeID()}">${t.getName()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-4 select-items">
                                        <label class="large mb-1" for="department">Role</label>
                                        <select
                                            class="form-select form-select-sm form-control-lg"
                                            aria-label=".form-select-lg "
                                            id="role"
                                            name="txt_roleID"
                                            >
                                            <c:forEach items="${ListRole}" var="r">
                                                <option 
                                                    <c:if test="${Employee.getRoleID() == r.getRoleID()}" > 
                                                        selected</c:if>
                                                    value="${r.getRoleID()}">${r.getName()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-output">
                                    <div class="form-output-button">
                                        <input type="hidden" name="txt_employeeID" value="${Employee.getEmployeeID()}">
                                        <input
                                            class="btn btn-primary"
                                            type="submit"
                                            name="btAction"
                                            value="Lưu Thay Đổi"
                                            />
                                    </div>
                                    <div class="form-output">
                                        <c:if test="${msg!=null}" >
                                            <p class="sucess">${msg}</p>
                                        </c:if>
                                    </div>
                                </div>
                                <!-- Save changes button-->

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
