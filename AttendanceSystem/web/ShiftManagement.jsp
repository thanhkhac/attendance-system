<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý ca làm việc</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            .right{
                position: absolute;
                width: 83%;
                right: 0px;
            }
            @media screen and (max-width: 768px) {

                .right{
                    position: absolute;
                    width: 100%;
                    right: auto;
                }
            }
        </style>

    </head>

    <%
        ArrayList<ShiftDTO> shiftList = new ShiftDAO().getActiveShiftDTO();
        request.setAttribute("shiftList", shiftList);
    %>
    <body>
        <div>
            <%@include file="Sidebar.jsp" %>
            <div class="right">
                <div class="text-center">
                    <h2>Quản lý ca làm việc</h2>
                </div>
                <div class="w-50 m-auto mt-5">
                    <form action="InsertShift">
                        <table class="table rounded-3 py-4" style="background-color: #88B04B" >
                            <tr>
                                <td colspan="1" class="fw-bold">
                                    Tên ca
                                </td>
                                <td colspan="3">
                                    <input name="txtShiftName" class="form-control" type="text" required
                                           <c:if test = "${not empty param.txtShiftName}">
                                               value="${param.txtShiftName}" 
                                           </c:if>
                                           >
                                </td>
                            </tr>
                            <tr>
                                <td class="fw-bold">Giờ bắt đầu</td>
                                <td >
                                    <input name="txtShiftStartTime" class="form-control" type="time" required
                                           <c:if test = "${not empty param.txtShiftStartTime}">
                                               value="${param.txtShiftStartTime}" 
                                           </c:if>
                                           >
                                </td>
                                <td class="fw-bold">Giờ kết thúc</td>
                                <td >
                                    <input name="txtShiftEndTime" class="form-control" type="time" required
                                           <c:if test = "${not empty param.txtShiftEndTime}">
                                               value="${param.txtShiftEndTime}" 
                                           </c:if>
                                           >
                                </td>
                            </tr>   
                            <tr>
                                <td class="fw-bold">Cổng mở</td>
                                <td ><input name="txtOpenAt" class="form-control" type="time" required
                                            <c:if test = "${not empty param.txtOpenAt}">
                                                value="${param.txtOpenAt}" 
                                            </c:if>
                                            ></td>
                                <td class="fw-bold">Cổng đóng</td>
                                <td ><input name="txtCloseAt" class="form-control" type="time" required
                                            <c:if test = "${not empty param.txtCloseAt}">
                                                value="${param.txtCloseAt}" 
                                            </c:if>
                                            ></td>
                            </tr>   
                            <tr>
                                <td colspan="4" class="text-center">
                                    <button type="submit" class="btn-danger rounded-3 px-3">
                                        Thêm
                                    </button>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <c:if test = "${not empty param.success}">
                    <div class="alert alert-success mx-4" role="alert">
                        Thành công
                    </div>
                </c:if>
                <c:if test = "${not empty requestScope.error}">
                    <div class="alert alert-danger mx-4" role="alert">
                        ${requestScope.error}
                    </div>
                </c:if>

                <div class="mx-4">
                    <table class="table">
                        <tr style="background-color: #6b5b95" class="text-white ">
                            <th>Tên</th>
                            <th>Giờ bắt đầu</th>
                            <th>Giờ kết thúc</th>
                            <th>Cổng mở</th>
                            <th>Cổng đóng</th>
                            <th>Tùy chỉnh</th>
                        </tr>
                        <c:forEach var="dto" items="${requestScope.shiftList}" varStatus="counter">
                            <tr>
                                <td class="fw-bold">${dto.name}</td>
                                <td>${dto.startTime}</td>
                                <td>${dto.endTime}</td>
                                <td>${dto.openAt}</td>
                                <td>${dto.closeAt}</td>
                                <td>
                                    <button class="btn-warning rounded-3 px-2"
                                            data-bs-toggle="modal" data-bs-target="#updateModal${counter.index}"
                                            >Tùy chỉnh</button>
                                    <div>
                                        <div class="modal fade" id="updateModal${counter.index}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="staticBackdropLabel">
                                                            ${dto.name}
                                                        </h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form action="UpdateShift">
                                                            <table class="table rounded-3 py-4" style="background-color: #88B04B" >
                                                                <tr>
                                                                    <td colspan="1" class="fw-bold">
                                                                        Tên ca
                                                                    </td>
                                                                    <td colspan="3">
                                                                        <input class="form-control" name="udName" type="text" value="${dto.name}">
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="fw-bold">Giờ bắt đầu</td>
                                                                    <td ><input class="form-control" name="udStartTime" type="time" value="${dto.startTime}"></td>
                                                                    <td class="fw-bold">Giờ kết thúc</td>
                                                                    <td ><input class="form-control" name="udEndTime" type="time" value="${dto.endTime}"></td>
                                                                </tr>   
                                                                <tr>
                                                                    <td class="fw-bold">Cổng mở</td>
                                                                    <td ><input class="form-control" name="udOpenAt" type="time" value="${dto.openAt}"></td>
                                                                    <td class="fw-bold">Cổng đóng</td>
                                                                    <td ><input class="form-control" name="udCloseAt" type="time"value="${dto.closeAt}"></td>
                                                                </tr>   
                                                                <tr>
                                                                    <td colspan="4" class="text-center">
                                                                        <button type="button" class="btn-danger rounded-3 px-3 "onclick="return confirmDelete()">
                                                                            <a  class="text-decoration-none text-white" href="DeleteShift?ID=${dto.shiftID}">Xóa</a>
                                                                        </button>
                                                                        <button type="submit" class="btn-warning rounded-3 px-3">
                                                                            Cập nhật
                                                                        </button>
                                                                        <input type="hidden" name="shiftID" value="${dto.shiftID}">
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </form>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </td>

                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div>
                    <div class="modal fade" id="messModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Thông báo</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    ${requestScope.messModal}
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--<script src="assets/Bootstrap5/js/bootstrap.bundle.min.js">-->
        <c:if test = "${not empty requestScope.messModal}">
            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    var myModal = new bootstrap.Modal(document.getElementById('messModal'));
                    myModal.show();
                });
            </script>
        </c:if>

        <script>
            function confirmDelete() {
                return confirm("Bạn có chắc chắn muốn xóa ca làm việc này không?");
            }
        </script>
    </body>
</html>
