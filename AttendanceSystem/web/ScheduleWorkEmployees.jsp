<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            .right{
                position: absolute;
                width: 83%;
                right: 0px;
            }

            @media screen and (max-width: 768px) {

                .calendar{
                    position: absolute;
                    width: 100%;
                    right: auto;
                }
            }
        </style>

    </head>
    <body>
        <div>
            <%@include file="Sidebar.jsp" %>
            <div class="right">
                <div class="text-center pt-3" style="font-family: sans-serif; font-weight: 900">
                    <h2>Danh sách nhân viên</h2>
                    <a href="javascript:history.back()" class="btn btn-outline-secondary" style="position: absolute; left: 15px; top: 15px;">
                        <i class="bi bi-arrow-left"></i> Trở lại
                    </a>

                </div>
                <div class="d-flex justify-content-around px-3 py-3 align-middle">
                    <table class="table table-primary">
                        <tr>
                            <th>Tìm kiếm</th>
                            <th>Phòng ban</th>
                            <th>Loại nhân viên</th>
                            <th>Role</th>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" class="form-input form-control" id="input-search">
                            </td>
                            <td>
                                <select class="form-select" id="select-department">
                                    <option value="" selected="">Tất cả</option>
                                    <c:forEach var="dto" items="${listDepartment}" varStatus="counter">
                                        <option value="${dto.name}">${dto.name}</option>
                                    </c:forEach>
                                </select>   
                            </td>
                            <td>
                                <select class="form-select" id="select-employeeType">
                                    <option value="" selected="">Tất cả</option>
                                    <c:forEach var="dto" items="${employeeTypeList}" varStatus="counter">
                                        <option value="${dto.name}">${dto.name}</option>
                                    </c:forEach>
                                </select>   
                            </td>
                            <td>
                                <select class="form-select" id="select-role">
                                    <option value="" selected="">Tất cả</option>
                                    <c:forEach var="dto" items="${roleList}" varStatus="counter">
                                        <option value="${dto.name}">${dto.name}</option>
                                    </c:forEach>
                                </select>   
                            </td>
                        </tr>
                    </table>

                </div>
                <div>
                    <form action="DispatchController" method="POST">
                        <table class="table">
                            <tr>
                                <th>
                                    <button type="button" class="btn btn-sm btn-primary" onclick="selectAllRows(true)">Select</button>
                                    <button type="button" class="btn btn-sm btn-danger" onclick="selectAllRows(false)">Deselect</button>
                                </th>
                                <th>Mã nhân viên</th>
                                <th>Họ và tên</th>
                                <th>CCCD</th>
                                <th>Phòng ban</th>
                                <th>Loại nhân viên</th>
                                <th>Role</th>
                            </tr>
                            <c:forEach var="employee" items="${requestScope.UnscheduleEmployees}" varStatus="counter">
                                <tr class="employee-row">
                                    <td>
                                        <input type="checkbox" class="rowCheckbox" name="chkEmployeeID" value="${employee.employeeId}">
                                    </td>
                                    <td>${employee.employeeId}</td>
                                    <td class="employee-name">${employee.lastName} ${employee.middleName} ${employee.firstName}</td>
                                    <td>${employee.cccd}</td>
                                    <td>
                                        <c:forEach var="dto" items="${listDepartment}" varStatus="counter">
                                            <c:if test="${dto.departmentID eq employee.departmentID}">
                                                ${dto.name}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach var="dto" items="${employeeTypeList}" varStatus="counter">
                                            <c:if test="${dto.employeeTypeId eq employee.employeeTypeID}">
                                                ${dto.name}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach var="dto" items="${roleList}" varStatus="counter">
                                            <c:if test="${dto.roleID eq employee.roleID}">
                                                ${dto.name}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <div class="text-center mt-3">
                            <button type="submit" class="btn btn-primary" id="form-submit-button">Tiếp theo</button>
                            <input type="hidden" name="btAction" value="GetConflicts">
                        </div>
                        <div>
                            <c:forEach var="dto" items="${paramValues.shift}" varStatus="counter">
                                <c:if test = "${dto ne 'no'}">
                                    <input type="hidden" name="shift" value="${dto}">
                                </c:if>
                            </c:forEach>
                        </div>
                    </form>
                </div>
            </div>

        </div>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                function filterEmployees() {
                    var allRows = document.querySelectorAll(".employee-row");
                    allRows.forEach(function (row) {
                        row.style.display = "";
                    });

                    var inputSearch = document.getElementById("input-search").value.toLowerCase();
                    var selectDepartment = document.getElementById("select-department").value;
                    var selectEmployeeType = document.getElementById("select-employeeType").value;
                    var selectRole = document.getElementById("select-role").value;

                    var rows = document.querySelectorAll(".employee-row");

                    rows.forEach(function (row) {
                        var employeeName = row.querySelector(".employee-name").innerText.toLowerCase();
                        var department = row.children[4].innerText;
                        var employeeType = row.children[5].innerText;
                        var role = row.children[6].innerText;

                        var showRow =
                                employeeName.includes(inputSearch) &&
                                (selectDepartment === "" || department === selectDepartment) &&
                                (selectEmployeeType === "" || employeeType === selectEmployeeType) &&
                                (selectRole === "" || role === selectRole);

                        row.style.display = showRow ? "" : "none";
                    });

                }

                document.getElementById("input-search").addEventListener("input", filterEmployees);
                document.getElementById("select-department").addEventListener("change", filterEmployees);
                document.getElementById("select-employeeType").addEventListener("change", filterEmployees);
                document.getElementById("select-role").addEventListener("change", filterEmployees);

                document.getElementById("form-submit-button").addEventListener("click", function (event) {
                    var checkedCheckboxes = document.querySelectorAll(".rowCheckbox:checked");

                    if (checkedCheckboxes.length === 0) {
                        alert("Vui lòng chọn ít nhất một nhân viên");
                        event.preventDefault();
                    }
                });
            });


            function selectAllRows(select) {
                var rowCheckboxes = document.querySelectorAll(".rowCheckbox");

                rowCheckboxes.forEach(function (checkbox) {
                    var parentRow = checkbox.closest(".employee-row");
                    if (parentRow.style.display !== "none") {
                        checkbox.checked = select;
                    }
                });
            }

            document.getElementById("selectAllCheckbox").addEventListener("click", selectAllRows);


        </script>
        <script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
