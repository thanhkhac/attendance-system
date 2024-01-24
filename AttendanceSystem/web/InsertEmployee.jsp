<%-- 
    Document   : InsertEmployee
    Created on : Jan 21, 2024, 9:11:23 PM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert Employee</title>

        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            .gradient-custom {
                /* fallback for old browsers */
                background: #f5f5f5;
                /* Chrome 10-25, Safari 5.1-6 */
                /*background: -webkit-linear-gradient(to bottom right, rgba(240, 147, 251, 1), rgba(245, 87, 108, 1));*/

                /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
                /*background: linear-gradient(to bottom right, rgba(240, 147, 251, 1), rgba(245, 87, 108, 1))*/
            }

            .card-registration .select-input.form-control[readonly]:not([disabled]) {
                font-size: 1rem;
                line-height: 2.15;
                padding-left: .75em;
                padding-right: .75em;
            }
            .card-registration .select-arrow {
                top: 13px;
            }
            .display-color{
                color: #999;
            }
        </style>

    </head>
    <body>
        <%--
                <form action="DispatchController" method="get">
                    <table>
                        <tr>
                            <td>First Name</td>
                            <td><input type="text" name="txtFirstName"></td>//////////////
                        </tr>
                        <tr>
                            <td>Middle Name</td>
                            <td><input type="text" name="txtMiddleName"></td>//////////////
                        </tr>
                        <tr>
                            <td>Last Name</td>
                            <td><input type="text" name="txtLastName"></td>//////////////
                        </tr>
                        <tr>
                            <td>Gender</td>
                            <td>
                                <select name="txtGender">////////////////////////////
                                    <option value="male">Male</option>
                                    <option value="female">Female</option>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>Birth Date</td>//////////////////////////////////////////
                            <td><input type="date" name="txtBirthDate"></td>
                        </tr>
                        <tr>
                            <td>Email</td>////////////////////////////////////////////////////////
                            <td><input type="text" name="txtEmail"></td>
                        </tr>
                        <tr>
                            <td>CCCD</td>//////////////////////////////////////////////////////////////////////
                            <td><input type="number" name="txtCCCD"></td>
                        </tr>
                        <tr>
                            <td>Phonenumber</td>////////////////////////////////////////////////////////
                            <td><input type="number" name="txtPhonenumber"></td>
                        </tr>
                        <tr>
                            <td>Employee Type ID</td>////////////////////////////////////////////////////////
                            <td>
                                <select name="txtEmployeeTypeID">
                                    <option value="fulltime">Full time</option>
                                    <option value="parttime">Part time</option>
                                    <option value="intern">Intern</option>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>DepartmentID</td>//////////////////////////////////////////////////////////////////////
                            <td>
                                <select name="txtDepartmentID">
                                    <option value="phongNhanSu">Phòng nhân sự</option>
                                    <option value="phongTiepThi">Phòng tiếp thị</option>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>RoleID</td>//////////////////////////////////////////////////////////////////////
                            <td>
                                <select name="txtRoleID">
                                    <option value="nhanVien">Nhân viên</option>
                                    <option value="quanLyNhanSu">Quản lý nhân viên</option>
                                    <option value="admin">Admin</option>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>Start Date</td>////////////////////////////////////////////////////////
                            <td><input type="date" name="txtStartDate"></td>
                        </tr>
                        <tr>
                            <td>End Date</td>////////////////////////////////////////////////////////
                            <td><input type="date" name="txtEndDate"></td>
                        </tr>
                        <tr>
                            <td>Is Active</td>////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            <td><input type="checkbox" name="txtIsActive"></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <button name="btAction" value="Insert">Insert</button>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <%
                                String msg = (String) request.getAttribute("MSG");
                                    if(msg != null){
                                %>
                                <p style="color: red"><%=msg%></p>
                                <%
                                    }
                                %>
                            </td>
                        </tr>
                    </table>
                </form>
        --%>


        <form action="DispatchController">
            <section class="vh-100 gradient-custom">
                <div class="container py-5 h-100">
                    <div class="row justify-content-center align-items-center h-100">
                        <div class="col-12 col-lg-9 col-xl-7" style="width: 900px">
                            <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                                <div class="card-body p-4 p-md-5">
                                    <h3 class="mb-4 pb-2 pb-md-0 mb-md-5">Insert Employee</h3>
                                    <form>

                                        <div class="row">

                                            <div class="col-md-4 mb-4">

                                                <div class="form-outline">
                                                    <input type="text" id="lastName" class="form-control form-control-lg" name="txtLastName"/>
                                                    <label class="form-label" for="lastName">Họ</label>
                                                </div>

                                            </div>

                                            <div class="col-md-4 mb-4">

                                                <div class="form-outline">
                                                    <input type="text" id="firstName" class="form-control form-control-lg" name="txtMiddleName"/>
                                                    <label class="form-label" for="firstName">Tên Đệm</label>
                                                </div>

                                            </div>

                                            <div class="col-md-4 mb-4">

                                                <div class="form-outline">
                                                    <input type="text" id="firstName" class="form-control form-control-lg" name="txtFirstName"/>
                                                    <label class="form-label" for="firstName">Tên Riêng</label>
                                                </div>

                                            </div>

                                        </div>

                                        <div class="row">
                                            <div class="col-md-4 mb-4 d-flex align-items-center">

                                                <div class="form-outline datepicker w-100">
                                                    <input type="date" class="form-control form-control-lg display-color" id="birthdayDate" name="txtBirthDate" "/>
                                                    <label for="birthdayDate" class="form-label">Ngày sinh</label>
                                                </div>

                                            </div>

                                            <div class="col-md-4 mb-4 pb-2">

                                                <div class="form-outline">
                                                    <input type="tel" id="phoneNumber" class="form-control form-control-lg" name="txtCCCD"/>
                                                    <label class="form-label" for="phoneNumber">Căn cước công dân</label>
                                                </div>

                                            </div>

                                            <div class="col-md-4 mb-4">

                                                <h6 class="mb-2 pb-1">Giới tính</h6>

                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="txtGender" id="femaleGender"
                                                           value="male" checked />
                                                    <label class="form-check-label" for="femaleGender">Nam</label>
                                                </div>

                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="txtGender" id="maleGender"
                                                           value="female" />
                                                    <label class="form-check-label" for="maleGender">Nữ</label>
                                                </div>

                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-6 mb-4 pb-2">

                                                <div class="form-outline">
                                                    <input type="email" id="emailAddress" class="form-control form-control-lg" name="txtEmail"/>
                                                    <label class="form-label" for="emailAddress">Email</label>
                                                </div>

                                            </div>
                                            <div class="col-md-6 mb-4 pb-2">

                                                <div class="form-outline">
                                                    <input type="tel" id="phoneNumber" class="form-control form-control-lg" name="txtPhonenumber"/>
                                                    <label class="form-label" for="phoneNumber">Số điện thoại</label>
                                                </div>

                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-4 mb-4">

                                                <select class="select form-control-lg display-color" name="txtEmployeeTypeID" " >
                                                    <option value="" disabled>Choose option</option>
                                                    <option value="fulltime">Toàn thời gian</option>
                                                    <option value="parttime">Bán thời gian</option>
                                                    <option value="intern">Thực tập</option>
                                                </select> 
                                                <label class="form-label select-label">Loại nhân viên</label>

                                            </div>

                                            <div class="col-md-4 mb-4">

                                                <select class="select form-control-lg display-color" name="txtDepartmentID" ">
                                                    <option value="" disabled>Choose option</option>
                                                    <option value="phongNhanSu">Phòng nhân sự</option>
                                                    <option value="phongTiepThi">Phòng tiếp thị</option>
                                                </select> 
                                                <label class="form-label select-label">Phòng ban</label>

                                            </div>

                                            <div class="col-md-4 mb-4">

                                                <select class="select form-control-lg display-color" name="txtRoleID" ">
                                                    <option value="1" disabled>Choose option</option>
                                                    <option value="nhanVien">Nhân viên</option>
                                                    <option value="quanLyNhanSu">Quản lý nhân viên</option>
                                                    <option value="admin">Admin</option>
                                                </select> 
                                                <label class="form-label select-label">Chức vụ</label>

                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-6 mb-4 d-flex align-items-center">

                                                <div class="form-outline datepicker w-100">
                                                    <input type="date" class="form-control form-control-lg display-color" id="startDate" name="txtStartDate" "/>
                                                    <label for="birthdayDate" class="form-label">Ngày bắt đầu</label>
                                                </div>

                                            </div>

                                            <div class="col-md-6 mb-4 d-flex align-items-center">

                                                <div class="form-outline datepicker w-100">
                                                    <input type="date" class="form-control form-control-lg display-color" id="enđate" name="txtEndDate" "/>
                                                    <label for="birthdayDate" class="form-label">Ngày kết thúc</label>
                                                </div>

                                            </div>
                                        </div>


                                        <div class="row">

                                            <div class="col-md-6 form-check form-switch">
                                                <input class="form-check-input" type="checkbox" id="switchDefault" name="txtIsActive">
                                                <label class="form-check-label" for="switchDefault">Kích hoạt tài khoản</label>
                                            </div>

                                            <div class="col-md-6 form-check form-switch">
                                                <%
                                                    String msg = (String) request.getAttribute("MSG");
                                                    if(msg != null){
                                                %>
                                                <div class="row">

                                                    <div class="col-md-1">
                                                        <i class="fa-solid fa-triangle-exclamation"></i>
                                                    </div>

                                                    <div class="col-md-11">
                                                        <p style="color: red"><%=msg%></p>
                                                    </div>

                                                </div>

                                                <%
                                                    }
                                                %>
                                            </div>
                                        </div>

                                        <div class="mt-4 pt-2">
                                            <button class="btn btn-primary btn-lg" name="btAction" value="Insert">Thêm</button>
                                        </div>

                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </form>


        <script src="https://kit.fontawesome.com/c2b5cd9aa7.js" crossorigin="anonymous"></script>
    </body>
</html>
