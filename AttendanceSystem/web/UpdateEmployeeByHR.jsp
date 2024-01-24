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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            body {
                margin-top: 20px;
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
        </style>
    </head>
    <body>
        <c:set var="ListDepartment" value="${requestScope.ListDepartment}" />
        <c:set var="ListType" value="${requestScope.ListType}" />
        <c:set var="ListRole" value="${requestScope.ListRole}" />
        <c:set var="Employee" value="${requestScope.Employee}" />
        <div class="container-xxl px-4 mt-4">
            <!-- Account page navigation-->
            <!-- <nav class="nav nav-borders"></nav> -->
            <!-- <hr class="mt-0 mb-4" /> -->
            <div class="row">
                <div class="col-xl-12">
                    <!-- Account details card-->
                    <div class="card mb-4">
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
                                            placeholder="Enter your first name"
                                            name="txt_firstName"
                                            value="${Employee.getFirstName()}"
                                            />
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
                                            placeholder="Enter your middle name"
                                            name="txt_middleName"
                                            value="${Employee.getMiddleName()}"
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
                                            placeholder="Enter your last name"
                                            name="txt_lastName"
                                            value="${Employee.getLastName()}"
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
                                                    value="1"
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
                                                        value="0"
                                                    <c:if test="${Employee.getGender()==false}" >checked</c:if>
                                                        />
                                                    <label
                                                        class="form-check-label"
                                                        for="inputGender-Female"
                                                        >
                                                        Nữ
                                                    </label>
                                                </div>
                                                <div class="form-check-items">
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
                                                </div>
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
                                                value="${Employee.getCccd()}"
                                            />
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
                                            value="${Employee.getEmail()}"
                                            />
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
                                            value="${Employee.getPassword()}"
                                            />
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
                                            value="${Employee.getPhoneNumber()}"
                                            />
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
                                            value="${Employee.getBirthDate()}"
                                            />
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
                                <!-- Save changes button-->
                                <input type="hidden" name="txt_employeeID" value="${Employee.getEmployeeID()}">
                                <input
                                    class="btn btn-primary"
                                    type="submit"
                                    name="btAction"
                                    value="Lưu Thay Đổi"
                                    />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>