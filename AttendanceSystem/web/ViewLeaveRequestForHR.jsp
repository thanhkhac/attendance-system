<%-- 
    Document   : ProcessLeaveRequestForManager
    Created on : Feb 19, 2024, 8:14:44 AM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.time.*" %>
<%@page import="model.*" %>
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
        </style>

    </head>
    <%
        LeaveRequestDAO lrDao = new LeaveRequestDAO();
        EmployeeDAO dao = new EmployeeDAO();
        EmployeeDTO emDTO = new EmployeeDTO();
        ArrayList<LeaveRequestDTO> list = lrDao.getLeaveRequest();
    %>
    <body>
        <div>
            <%@include file="Sidebar.jsp" %>
            <div class="right">
                <div class="text-center pt-3" style="font-family: sans-serif; font-weight: 900">
                    <h2>Danh sách đơn</h2>
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
                    <form action="GetConflicts" method="POST">
                        <table class="table">
                            <tr>
                                <th>
                                    <button type="button" class="btn btn-sm btn-primary" onclick="selectAllRows(true)">Select</button>
                                    <button type="button" class="btn btn-sm btn-danger" onclick="selectAllRows(false)">Deselect</button>
                                </th>
                                <th class="text-center">Mã đơn</th>
                                <th class="text-center">Mã nhân viên</th>
                                <th class="text-center">Ngày gửi</th>
                                <th class="text-center">Ngày bắt đầu</th>
                                <th class="text-center">Ngày kết thúc</th>
                                <th class="text-center">Lí do</th>
                                <th class="text-center">Trạng thái <br> (Manager)</th>  
                                <th class="text-center">Trạng thái <br> (HR)</th>
                                <th class="text-center">Người phê duyệt <br> (Manager)</th>
                                <th class="text-center">Người phê duyệt <br> (HR)</th>
                            </tr>
                            <%
                                for (LeaveRequestDTO lr : list) {
                                emDTO = dao.getEmployeeDTO(lr.getEmployeeID());
                            %>
                                <tr class="employee-row">
                                    <td>
                                        <input type="checkbox" class="rowCheckbox" name="chkEmployeeID" value="${employee.employeeId}">
                                    </td>
                                    <td class="text-center"><%=lr.getLeaveRequestID()%></td>
                                    <td class="text-center"><%= emDTO.getLastName() + " " +  emDTO.getMiddleName() + " " + emDTO.getFirstName()%></td>
                                    <td class="text-center"><%=lr.getSentDate()%></td>
                                    <td class="text-center"><%=lr.getStartDate()%></td>
                                    <td class="text-center"><%=lr.getEndDate()%></td>
                                    <td class="text-center"><%=lr.getReason()%></td>
                                    <td class="text-center"><%=lr.getManagerApprove()%></td>
                                    <td class="text-center"><%=lr.getHrApprove()%></td>
                                    <td class="text-center"><%=lr.getManagerID()%></td>
                                    <td class="text-center"><%=lr.getHrID()%></td>
                                </tr>
                            <%
                                }
                            %>
                        </table>
                        <div class="text-center mt-3">
                            <button type="submit" class="btn btn-primary" id="form-submit-button">Tiếp theo</button>
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
                        alert("Vui lòng chọn ít nhất một nhân viên trước khi submit.");
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