<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.time.*"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="ultility.datetimeutil.DateTimeUtil"%>
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
    <%
    DateTimeUtil dateTimeUtil = new DateTimeUtil();

    
    int year = dateTimeUtil.getVNLocalDateNow().getYear();
    int month = dateTimeUtil.getVNLocalDateNow().getMonthValue();
    String txtyear = request.getParameter("year");
    String txtmonth = request.getParameter("month");
        if (txtyear != null || txtmonth != null) {
            try {
                year = Integer.parseInt(txtyear);
                month = Integer.parseInt(txtmonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                month = Integer.parseInt(txtmonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    ArrayList<EmployeeDTO> EmployeeList = new EmployeeDAO().getScheduledEmployees(month, year);
        
    ArrayList<EmployeeTypeDTO> employeeTypeList = new EmployeeTypeDAO().getEmployeeTypeList();
    ArrayList<DepartmentDTO> listDepartment = new DepartmentDAO().getListDepartment();
    ArrayList<RoleDTO> roleList = new RoleDAO().getRoleList();
        
    request.setAttribute("EmployeeList", EmployeeList);
    request.setAttribute("employeeTypeList", employeeTypeList);
    request.setAttribute("listDepartment", listDepartment);
    request.setAttribute("roleList", roleList);
    request.setAttribute("month", month);
    request.setAttribute("year", year);
    %>
    <body>
        <div>
            <div >
                <div class="text-center pt-3" style="font-family: sans-serif; font-weight: 900">
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
                            <th>Đã chọn</th>
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
                            <td>
                                <select class="form-select" id="select-status">
                                    <option value="" selected="">Tất cả</option>
                                    <option value="selected">Đã chọn</option>
                                    <option value="unselected">Chưa chọn</option>
                                </select>   
                            </td>
                        </tr>
                    </table>

                </div>
                <div>
                    <form action="DeleteTimesheetForEmployees" method="POST">
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
                            <c:forEach var="employee" items="${requestScope.EmployeeList}" varStatus="counter">
                                <tr class="employee-row">
                                    <td>
                                        <input type="checkbox" class="rowCheckbox" name="chkEmployeeID" value="${employee.employeeId}">
                                    </td>
                                    <td>${employee.employeeId}</td>
                                    <td>
                                        <a class="employee-name" target="_blank" href="ViewEmployeeWorkSchedule.jsp?employeeID=${employee.employeeId}">
                                            ${employee.lastName} ${employee.middleName} ${employee.firstName}
                                        </a>
                                    </td>
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
                        <div class=" my-3">
                            <button type="submit" class="btn btn-danger" id="form-submit-button">Xóa</button>
                            <input type="hidden" name="btAction" value="DeleteMultipleTimesheet">
                            <input type="hidden" name="month" value="${requestScope.month}">
                            <input type="hidden" name="year" value="${requestScope.year}">
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
                    var selectStatus = document.getElementById("select-status").value; // Thêm dòng này

                    var rows = document.querySelectorAll(".employee-row");

                    rows.forEach(function (row) {
                        var employeeName = row.querySelector(".employee-name").innerText.toLowerCase();
                        var department = row.children[4].innerText;
                        var employeeType = row.children[5].innerText;
                        var role = row.children[6].innerText;

                        var isChecked = row.querySelector(".rowCheckbox").checked;

                        var showRow =
                                employeeName.includes(inputSearch) &&
                                (selectDepartment === "" || department === selectDepartment) &&
                                (selectEmployeeType === "" || employeeType === selectEmployeeType) &&
                                (selectRole === "" || role === selectRole) &&
                                (selectStatus === "" || (selectStatus === "selected" && isChecked) || (selectStatus === "unselected" && !isChecked));

                        row.style.display = showRow ? "" : "none";
                    });

                }

                document.getElementById("input-search").addEventListener("input", filterEmployees);
                document.getElementById("select-department").addEventListener("change", filterEmployees);
                document.getElementById("select-employeeType").addEventListener("change", filterEmployees);
                document.getElementById("select-role").addEventListener("change", filterEmployees);
                document.getElementById("select-status").addEventListener("change", filterEmployees); // Thêm dòng này

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

            $(document).ready(function () {
                $("#form-submit-button").click(function (event) {
                    event.preventDefault();
                    var checkedCheckboxes = document.querySelectorAll(".rowCheckbox:checked");

                    if (checkedCheckboxes.length === 0) {
                        alert("Vui lòng chọn ít nhất một nhân viên");
                        return;
                    }

                    var formData = $("form").serialize();

                    $.ajax({
                        type: "POST",
                        url: "DeleteTimesheetForEmployees",
                        data: formData,
                        success: function (result) {
                            if (result === "success") {
                                $("#successModal").modal('show');
                                var selectedMonth = $("#month").val();
                                var selectedYear = $("#year").val();
                                $.ajax({
                                    type: "POST",
                                    url: "ScheduledEmployeeList.jsp",
                                    data: {
                                        month: selectedMonth,
                                        year: selectedYear
                                    },
                                    success: function (response) {
                                        $("#result").html(response);
                                    },
                                    error: function () {
                                        $("#result").html("Error occurred.");
                                    }
                                });
                            } else {
                                alert("Có lỗi xảy ra. Vui lòng thử lại.");
                            }
                        },
                        error: function () {
                            alert("Có lỗi xảy ra. Vui lòng thử lại.");
                        }
                    });
                });
            });
        </script>
        <script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
