<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            .table-container {
                margin-left: 270px;
                max-width: 1200px;
                padding-top: 50px;
            }

            @media (max-width:1450px) {
                .table-container {
                    margin: 0 50px;
                    padding-top: 50px;
                }
            }
            .input-group {
                display: flex;
                align-items: center;
                width: 130px; /* Điều chỉnh chiều rộng của ô tìm kiếm */
                margin-left: 300px;
                margin-left: 1070px;



            }

            .search-bar{
                margin-left: 900px;
            }

            .input-group .form-outline {
                flex-grow: 1;
                margin-left: -1px; /* Điều chỉnh khoảng cách giữa nút tìm kiếm và ô nhập */
            }

        </style>
    </head>
    <body>
        <%@include file="Sidebar.jsp" %>
        <c:set  var="listDepartment" value="${requestScope.listDepartment}"/>   
        <c:set  var="listEmployee"   value="${requestScope.listEmployee}"/>
        <c:set var="msg" value="${msg}" />
        <c:if test="${not empty msg}" >
            <p id="msg">${msg}</p>
        </c:if>
        <div class="table-container">
            <h1 class="h3 mb-2 text-gray-800">Department</h1>

            <!-- Add Department Button -->
            <c:if test="${sessionScope.ACCOUNT != null and sessionScope.ACCOUNT.roleID == 3}">
                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addDepartment">
                    Add Department
                </button>
            </c:if>

            <!-- Search Department -->
            <form class="row g-3" action="DepartmentServlet?action=search" method="POST">
                <!--<div class="input-group">-->

                <div class="col-auto search-bar" >
                    <input type="search" id="form1" name="keyword"
                           class="form-control" placeholder="Search" />
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary mb-3">Search</button>
                </div>
                <!--</div>-->
            </form>

            <!-- Department Table -->
            <table class="table table-bordered table-responsive">
                <thead>
                    <tr class="table-dark">
                        <th scope="col">ID</th>
                        <th scope="col">Department Name</th>
                        <th scope="col">Manager's name</th>
                        <th>Total Employees</th>
                            <c:if test="${sessionScope.ACCOUNT != null and sessionScope.ACCOUNT.roleID == 3}">
                            <th>Action</th>
                            </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listDepartment}" var="d">
                        <tr class="table-hover">
                            <td name="id">${d.getDepartmentID()}</td>
                            <td name="name"><a style="color: black;" href="DispatchController?department=${d.getDepartmentID()}&&btAction=viewListByDepartment" target="target">${d.getName()}</a></td>
                            <td>
                                <c:forEach items="${listEmployee}" var="employee">
                                    <c:if test="${employee.getEmployeeID() == d.managerID}">
                                        ${employee.firstName} ${employee.middleName}
                                        ${employee.lastName}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <!-- Đếm số nhân viên trong mỗi department -->
                            <td>
                                <c:set var="employeeCount" value="0" />
                                <c:forEach items="${listEmployee}" var="employee">
                                    <c:if  test="${employee.departmentID == d.departmentID}">
                                        <c:set var="employeeCount"
                                               value="${employeeCount + 1}" />
                                    </c:if>
                                </c:forEach>
                                ${employeeCount}

                            </td>
                            <c:if test="${sessionScope.ACCOUNT != null and sessionScope.ACCOUNT.roleID == 3}">
                                <td>
                                    <!-- Assign Manager Form -->
                                    <form action="DispatchController" method="POST" style="display: inline;">
                                        <input type="hidden" value="${d.getDepartmentID()}" name="departmentID"/>
                                        <input type="submit" value="Assign Manager" name="btAction" class="btn btn-danger"/>
                                    </form>

                                    <!-- Update Button -->
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editDepartmentModal"
                                            onclick="editDepartment(this)">
                                        Update
                                    </button>

                                    <!-- Delete Button -->
                                    <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal"
                                            onclick="deleteDepartment(${d.getDepartmentID()})">
                                        Delete
                                    </button>
                                </td>
                            </c:if>
                        </tr>

                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="modal fade" id="editDepartmentModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editDepartmentModalLabel">Edit Department</h5>
                        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="editDepartmentForm" action="DepartmentServlet?action=edit" method="POST">
                            <!-- Department ID (ẩn) -->
                            <div class="form-group" style="display: none">
                                <input type="text" class="form-control" id="departmentIdEditInput"
                                       name="departmentId" value="">
                            </div>
                            <!-- Department Name -->
                            <div class="form-group">
                                <label for="departmentName">Department Name:</label>
                                <input type="text" class="form-control" id="departmentNameEditInput"
                                       name="departmentName">
                            </div>
                            <!-- Thêm các trường khác tùy theo thông tin của Department -->
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary" form="editDepartmentForm">Update</button>
                    </div>
                </div>
            </div>
        </div>


        <!--Modal Add-->
        <div class="modal fade" id="addDepartment" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editDepartmentModalLabel">Add Department</h5>
                        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="DepartmentServlet?action=add" method="POST">
                        <div class="modal-body">

                            <!-- Department Name -->
                            <div class="form-group">
                                <label for="departmentNameAdd">Department Name:</label>
                                <input type="text" class="form-control" id="departmentNameAdd"
                                       name="departmentName">
                            </div>
                            <!-- Thêm các trường khác tùy theo thông tin của Department -->
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary" >Add</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" 
             aria-labelledby="deleteModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteModalLabel">Xác nhận xóa</h5>
                        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="DepartmentServlet?action=delete" method="POST">
                        <div class="modal-body">
                            Bạn có chắc chắn muốn xóa không?
                        </div>
                        <div class="modal-footer">
                            <input type="hidden" class="form-control" id="idDeleteInput" name="id">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-primary">Xóa</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script>
                                                const msg = document.getElementById("msg").innerText;
                                                if (msg !== null && msg !== '') {
                                                    console.log(msg);
                                                    alert(msg);
                                                }

                                                function deleteDepartment(id) {
                                                    $('#idDeleteInput').val(id);
                                                }

                                                function editDepartment(e) {
                                                    let tr = e.closest('tr');
                                                    // Lấy thông tin từ các ô trong table
                                                    let departmentId = tr.querySelector('td[name="id"]').innerText;
                                                    let departmentName = tr.querySelector('td[name="name"]').innerText;
                                                    console.log(departmentId);
                                                    // Gán thông tin vào form
                                                    $('#departmentIdEditInput').val(departmentId);
                                                    $('#departmentNameEditInput').val(departmentName);
                                                }
        </script>

    </body>
</html>
