<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>

        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-beta1/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>

        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <style>
            body {
                height: 100vh;
                overflow-x: hidden;
            }

            .avatar__wrapper {
                border-radius: 100%;
                overflow: hidden;
            }

            .info td:nth-child(2) {
                font-weight: bold;
            }

            .info i{
                color: #1055A2;
            }

            .line {
                height: 3px;
                background-color: #4D4D4D;
                border-radius: 5%;
            }

            .left-part{
                border: 2px rgb(139, 139, 139) solid;
                border-radius: 10px;
                box-shadow:  0px 0px 5px 2px #999999;
                background-color: #F2752A;
            }

            .right-part{
                border: 2px rgb(139, 139, 139) solid;
                border-radius: 10px;
                box-shadow:  0px 0px 5px 2px #999999;
                background-color: white;
            }

        </style>
    </head>

    <body>
        <c:set var = "employee" value="${requestScope.employee}"/>
        <c:set var = "roleDTO" value="${requestScope.roleDTO}"/>
        <c:set var = "departmentDTO" value="${requestScope.departmentDTO}"/>
        <c:set var = "employeeTypeDTO" value="${requestScope.employeeTypeDTO}"/>


        <div class="col-md-9 container h-100 pt-5">
            <div class="row h-75 justify-content-around">
                <div class="col-md-4 left-part  text-white">
                    <div class="p-4">
                        <div class="avatar__wrapper">
                            <img src="https://t4.ftcdn.net/jpg/03/49/49/79/360_F_349497933_Ly4im8BDmHLaLzgyKg2f2yZOvJjBtlw5.jpg" class="w-100" alt="">
                        </div>
                    </div>
                    <div class="text-center fw-bold fs-3">
                        ${employee.getFirstName()}&nbsp;${employee.getMiddleName()}&nbsp;${employee.getLastName()}
                    </div>
                    <div class="text-center fw-bolder fs-5">
                        <i class="fa-solid fa-building"></i> : ${departmentDTO.getName()}
                    </div>
                    <div class="text-center fw-bolder fs-5">
                        Chức vụ: ${roleDTO.getName()}
                    </div>
                </div>

                <div class="col-md-7 fs-5 right-part mt-sm-3 mt-md-0">
                    <h3 class="h3 pt-3 text-md-start text-center">Thông tin</h3>
                    <div class="line"></div>
                    <div class="info p-3">
                        <table class="table">
                            <tr>
                                <td><i class="fa-regular fa-face-grin-wide"></i></td>
                                <td>Mã nhân viên</td>
                                <td>${employee.getEmployeeID()}</td>
                            </tr>
                            <tr>
                                <td><i class="fa-solid fa-signature"></i></td>
                                <td>Họ và tên</td>
                                <td>
                                    ${employee.getFirstName()}&nbsp;${employee.getMiddleName()}&nbsp;${employee.getLastName()}
                                </td>
                            </tr>
                            <tr>
                                <td><i class="fa-solid fa-envelope"></i></td>
                                <td>Email</td>
                                <td>${employee.getEmail()}</td>
                            </tr>
                            <tr>
                                <td><i class="fa-solid fa-cake-candles"></i></td>
                                <td>Ngày sinh</td>
                                <td>
                                    <fmt:formatDate value="${employee.getBirthDate()}" pattern="dd/MM/yyyy" />
                                </td>
                            </tr>
                            <tr>
                                <td><i class="fa-regular fa-id-card"></i></td>
                                <td>CCCD</td>
                                <td>${employee.getCccd()}</td>
                            </tr>
                            <tr>
                                <td><i class="fa-solid fa-phone"></i> </td>
                                <td>Số điện thoại</td>
                                <td>${employee.getPhoneNumber()}</td>
                            </tr>
                            <tr>
                                <td><i class="fa-solid fa-person-half-dress"></i></td>
                                <td>Giới tính</td>
                                <td>${employee.getGender() eq true ? 'Nam' : 'Nữ'}</td>
                            </tr>
                            <tr>
                                <td><i class="fa-solid fa-user"></i></td>
                                <td>Loại nhân viên</td>
                                <td>${employeeTypeDTO.getName()}</td>
                            </tr>
                            <tr>
                                <td><i class="fa-solid fa-business-time"></i></td>
                                <td>Thời hạn</td>
                                <td>
                                    <fmt:formatDate value="${employee.getStartDate()}" pattern="dd/MM/yyyy" /> - 
                                    <fmt:formatDate value="${employee.getEndDate()}" pattern="dd/MM/yyyy" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-beta1/js/bootstrap.bundle.min.js"></script>
    </body>

</html>