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
        <title>JSP Page</title>
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
                min-width: 1200px;
                height: auto;
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
        </style>
    </head>
    <body>

        <c:set var="employee" value="${sessionScope.employees}" />
        <c:set var="isError" value="${sessionScope.isError}" />
        <c:set var="isAcceptable" value="${sessionScope.isAcceptable}" />
        <c:set var="departments" value="${requestScope.departments}" />
        <c:set var="types" value="${requestScope.types}" />
        <c:set var="roles" value="${requestScope.roles}" />

        <div class="content">
            <h1 class="text-center">Import Employee </h1>
            <div class="content-redirect">
                <p><a href="ThanhCong.html">Home</a> | Import From Excel</p>
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
                                        <button  data-bs-toggle="modal" data-bs-target="#popupModal-${e.getEmployeeID()}"
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
                                            <div class="modal-body d-flex p-3">
                                                <form action="UpdateTempEmployeeServlet"
                                                      id="update-form-${e.getEmployeeID()}"
                                                      class="update-forms"
                                                      class="row g-3">
                                                    <div class="information-items" style="max-width: 350px; margin-left: 10px">
                                                        <input type="hidden" name="tmpID" value="${e.getEmployeeID()}">
                                                        <label class="modal-label">Last Name</label><input type="text" name="lastName" value="${e.getLastName()}"><br>
                                                        <label class="modal-label">Middle Name</label><input type="text" name="middleName" value="${e.getMiddleName()}"><br>
                                                        <label class="modal-label">First Name</label><input type="text" name="firstName" value="${e.getFirstName()}"><br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">Email</label><input type="text" name="Email" value="${e.getEmail()}"><br>
                                                        <label class="modal-label">CCCD</label><input type="text" name="CCCD" value="${e.getCccd()}"><br>
                                                        <label class="modal-label">Phone</label><input type="text" name="Phone" value="${e.getPhoneNumber()}"><br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">Gender</label>
                                                        <!--<input type="text" name="Gender" value="${e.getGender()?"Male":"Female"}">-->
                                                        <select name="Gender">
                                                            <option ${e.getGender() == true?'selected':''} value="${e.getGender()}">Nam</option>
                                                            <option ${e.getGender() == false?'selected':''}value="${e.getGender()}">Nữ</option>
                                                        </select>

                                                        <br>
                                                        <label class="modal-label">BirthDate</label><input type="text" name="BirthDate" value="${e.getBirthDate()}"><br>
                                                        <label class="modal-label">Password</label><input type="text" name="Password" value="${e.getPassword()}"><br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">EmployeType</label>
                                                        <select name="EmployeeType">
                                                            <c:forEach items="${types}" var="t">
                                                                <option 
                                                                    <c:if test="${e.getEmployeeTypeID() eq t.getEmployeeTypeID()}">selected=""</c:if>
                                                                    value="${t.getEmployeeTypeID()}">${t.getName()}</option>                                                          
                                                            </c:forEach>
                                                        </select>
                                                        <br>
                                                        <label class="modal-label">Department</label>
                                                        <select name="Department">
                                                            <c:forEach items="${departments}" var="d">
                                                                <option 
                                                                    <c:if test="${e.getDepartmentID() eq d.getDepartmentID()}">selected=""</c:if>
                                                                    value="${d.getDepartmentID()}">${d.getName()}</option>                                                          
                                                            </c:forEach>
                                                        </select>
                                                        <br>
                                                        <label class="modal-label">Role</label>
                                                        <select name="Role">
                                                            <c:forEach items="${roles}" var="r">
                                                                <option 
                                                                    <c:if test="${e.getRoleID() eq r.getRoleID()}">selected</c:if>
                                                                    value="${r.getRoleID()}">${r.getName()}</option>                                                          
                                                            </c:forEach>
                                                        </select>
                                                        <br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">StartDate</label><input type="text" name="StartDate" value="${e.getStartDate()}"><br>
                                                        <label class="modal-label">EndDate</label><input type="text" name="EndDate" value="${e.getEndDate()}"><br>
                                                    </div>
                                                </form>
                                            </div>
                                            <div class="modal-footer">
                                                <button onclick="submitForm(this)"
                                                        id="${e.getEmployeeID()}"
                                                        class="btn btn-success"
                                                        >Save Change</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div>
                            <c:forEach items="${isError}" var="e">
                                <tr class="error-rows table-danger">
                                    <td>${e.getLastName()}</td>
                                    <td>${e.getMiddleName()}</td>
                                    <td>${e.getFirstName()}</td>
                                    <td>${e.getPhoneNumber()}</td>
                                    <td>${e.getEmail()}</td>
                                    <td>${e.getCccd()}</td>
                                    <td>${e.getGender()?"Male":"Female"}</td>
                                    <td>
                                        <button  data-bs-toggle="modal" data-bs-target="#popupModal-${e.getEmployeeID()}"
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
                                            <div class="modal-body d-flex p-3">
                                                <form action="UpdateTempEmployeeServlet"
                                                      id="update-form-${e.getEmployeeID()}"
                                                      class="update-forms"
                                                      class="row g-3">
                                                    <div class="information-items" style="max-width: 350px; margin-left: 10px">
                                                        <input type="hidden" name="tmpID" value="${e.getEmployeeID()}">
                                                        <label class="modal-label">Last Name</label><input type="text" name="lastName" value="${e.getLastName()}"><br>
                                                        <label class="modal-label">Middle Name</label><input type="text" name="middleName" value="${e.getMiddleName()}"><br>
                                                        <label class="modal-label">First Name</label><input type="text" name="firstName" value="${e.getFirstName()}"><br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">Email</label><input type="text" name="Email" value="${e.getEmail()}"><br>
                                                        <label class="modal-label">CCCD</label><input type="text" name="CCCD" value="${e.getCccd()}"><br>
                                                        <label class="modal-label">Phone</label><input type="text" name="Phone" value="${e.getPhoneNumber()}"><br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">Gender</label>
                                                        <select name="Gender">
                                                            <option ${e.getGender() == true?'selected':''} value="${e.getGender()}">Nam</option>
                                                            <option ${e.getGender() == false?'selected':''}value="${e.getGender()}">Nữ</option>
                                                        </select>
                                                        <br>
                                                        <label class="modal-label">BirthDate</label><input type="text" name="BirthDate" value="${e.getBirthDate()}"><br>
                                                        <label class="modal-label">Password</label><input type="text" name="Password" value="${e.getPassword()}"><br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">EmployeeType</label>
                                                        <select name="EmployeeType">
                                                            <c:forEach items="${types}" var="t">
                                                                <option 
                                                                    <c:if test="${e.getEmployeeTypeID() eq t.getEmployeeTypeID()}">selected=""</c:if>
                                                                    value="${t.getEmployeeTypeID()}">${t.getName()}</option>                                                          
                                                            </c:forEach>
                                                        </select>
                                                        <br>
                                                        <label class="modal-label">Department</label>
                                                        <select name="Department">
                                                            <c:forEach items="${departments}" var="d">
                                                                <option 
                                                                    <c:if test="${e.getDepartmentID() eq d.getDepartmentID()}">selected=""</c:if>
                                                                    value="${d.getDepartmentID()}">${d.getName()}</option>                                                          
                                                            </c:forEach>
                                                        </select>
                                                        <br>
                                                        <label class="modal-label">Role</label>
                                                        <select name="Role">
                                                            <c:forEach items="${roles}" var="r">
                                                                <option 
                                                                    <c:if test="${e.getRoleID() eq r.getRoleID()}">selected</c:if>
                                                                    value="${r.getRoleID()}">${r.getName()}</option>                                                          
                                                            </c:forEach>
                                                        </select>
                                                        <br>
                                                    </div>
                                                    <div class="information-items"  style="max-width: 350px;">
                                                        <label class="modal-label">StartDate</label><input type="text" name="StartDate" value="${e.getStartDate()}"><br>
                                                        <label class="modal-label">EndDate</label><input type="text" name="EndDate" value="${e.getEndDate()}"><br>
                                                    </div>
                                                </form>
                                            </div>
                                            <div class="modal-footer">
                                                <button onclick="submitForm(this)"
                                                        id="${e.getEmployeeID()}"
                                                        class="btn btn-success"
                                                        >Save Change</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        </tbody>
                    </table>
                </div>
            </c:if>
            <c:if test="${empty employees}">
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
            table_head.style.display = 'table-row';
            for (var i = 0; i < acceptable_rows.length; i++) {
                acceptable_rows[i].style.display = 'table-row';
            }
            for (var i = 0; i < error_rows.length; i++) {
                error_rows[i].style.display = 'none';
            }
        }
        function Error_Table() {
            var error_rows = document.getElementsByClassName("error-rows");
            var acceptable_rows = document.getElementsByClassName("accept-rows");
            var table_head = document.getElementById("table-header");
            table_head.style.display = 'table-row';
            for (var i = 0; i < acceptable_rows.length; i++) {
                acceptable_rows[i].style.display = 'none';
            }
            for (var i = 0; i < error_rows.length; i++) {
                error_rows[i].style.display = 'table-row';
            }
        }


        function submitForm(button) {
            var ID = button.id;
            console.log("update-forms-" + ID);
            var update_form = document.getElementById("update-form-" + ID);
            update_form.submit();
            // Set a flag in localStorage to indicate that the popup has been displayed
            localStorage.setItem('popupDisplayed', 'true');
        }
        window.addEventListener('DOMContentLoaded', function () {
            if (localStorage.getItem('popupDisplayed') !== 'true') {
                openPopup();
            }
        });
        
        function searchByChange(event) {
                            event.preventDefault();
                            var txt_Search = document.getElementById("txtSearchValue").value;
                            var txt_Department = document.getElementById("txtDepartment").value;
                            var txt_Type = document.getElementById("txtType").value;
                            var txt_Order = document.getElementById("txtOrder").value;
                            console.log("Value: " + txt_Search + ", " + txt_Department + ", " + txt_Type + ", " + txt_Order);
                            $.ajax({
                                url: "/AttendanceSystem/ViewAllEmployeeAjax",
                                type: "get", //send it through get method
                                data: {
                                    txtSearchValue: txt_Search,
                                    txtDepartment: txt_Department,
                                    txtType: txt_Type,
                                    txtOrder: txt_Order
                                },
                                success: function (data) {
                                    var tbody = document.getElementById("table-container");
                                    tbody.innerHTML = data;
                                    console.log("Success ! ");
                                    initializePopup();
                                },
                                error: function (xhr, error) {
                                    console.log("Error: ", error);
                                }
                            });
                        }
    </script>
</html>
