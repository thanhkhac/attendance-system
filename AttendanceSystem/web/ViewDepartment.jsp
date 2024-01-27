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

        <div class="table-container">
            <!-- Add Department Button -->
            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addModal">
                Add Department
            </button>
            <!-- Search Department -->
            <div class="input-group">

                <div class="form-outline" data-mdb-input-init>
                    <input type="search" id="form1" class="form-control" placeholder="Search" />
                </div>
            </div>


            <!-- Department Table -->
            <table class="table table-bordered table-responsive">
                <thead>
                    <tr class="table-dark">
                        <th scope="col">ID</th>
                        <th scope="col">Department Name</th>
                        <th scope="col">Manager ID</th>
                        <th>Actions</th> <!-- Thêm cột Action -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listDepartment}" var="d">
                        <tr class="table-hover">
                            <td>${d.getDepartmentID()}</td>
                            <td>${d.getName()}</td>
                            <td>${d.getManagerID()}</td>
                            <td>
                                <!-- Assign Manager Form -->
                                <form action="DispatchController" method="POST" style="display: inline;">
                                    <input type="hidden" value="${d.getDepartmentID()}" name="departmentID"/>
                                    <input type="submit" value="Assign Manager" name="btAction" class="btn btn-danger"/>
                                </form>

                                <!-- Update Button -->
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editModal${d.getDepartmentID()}">
                                    Update
                                </button>

                                <!-- Delete Button -->
                                <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal${d.getDepartmentID()}">
                                    Delete
                                </button>
                            </td>
                        </tr>

                        <!-- Modal for Update -->
                    <div class="modal fade" id="editModal${d.getDepartmentID()}" tabindex="-1" role="dialog" aria-labelledby="editModalLabel${d.getDepartmentID()}" aria-hidden="true">
                        <!-- Add your update modal content here -->
                    </div>

                    <!-- Modal for Delete -->
                    <div class="modal fade" id="deleteModal${d.getDepartmentID()}" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel${d.getDepartmentID()}" aria-hidden="true">
                        <!-- Add your delete modal content here -->
                    </div>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Modal for Add Department -->
        <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
            <!-- Add your add modal content here -->
        </div>

        <script src="assets/Bootstrap5/js/bootstrap.min.js"/>


    </body>
</html>
