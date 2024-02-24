<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <title>Update News</title>
    </head>
    <body>
    <c:set var="createByOptions" value="${requestScope.createByOptions}" />
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <%@include file="Sidebar.jsp" %>
            </div>
            <div class="col-md-10">
                <h2 class="mb-4">Update News</h2>

                <form action="updatenews" method="post" onsubmit="return validateUpdateForm()" enctype="multipart/form-data">
                    <div id="updateErrorMessage" class="alert alert-danger" role="alert" style="display: none;"></div>

                    <%-- Retrieve newsupdate attribute values --%>
                    <c:if test="${not empty newsupdate}">
                        <input type="hidden" name="id" value="${newsupdate.newId}">
                        <div class="mb-3">
                            <label for="updateTitle" class="form-label">Title</label>
                            <input type="text" class="form-control" id="updateTitle" name="title" value="${newsupdate.title}" required>
                        </div>
                        <div class="mb-3">
                            <label for="updateContent" class="form-label">Content</label>
                            <textarea class="form-control" id="updateContent" name="content" rows="3" required>${newsupdate.content}</textarea>
                        </div>
                        <div class="mb-3">
                            <label for="updateFilePath" class="form-label">File Path</label>
                            <input type="file" class="form-control" id="updateFilePath" name="filePath">
                        </div>
                        <div class="mb-3">
                            <label for="updateCreateBy" class="form-label">Create By</label>
                            <select class="form-select" id="updateCreateBy" name="updateCreateBy">
                                <c:forEach var="employee" items="${employees}">
                                    <option value="${employee.firstName}" <c:if test="${employee.firstName eq newsupdate.createBy}">selected</c:if>>${employee.firstName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </c:if>

                    <button type="submit" class="btn btn-primary" onclick="confirmUpdate()">Update</button>
                </form>
            </div>
        </div>                 
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
                        function validateUpdateForm() {
                            var updateTitle = document.getElementById('updateTitle').value.trim();

                            if (updateTitle.length > 500) {
                                document.getElementById('updateErrorMessage').innerHTML = 'Nội dung không thể vượt quá 500 ký tự.';
                                document.getElementById('updateErrorMessage').style.display = 'block';
                                return false;
                            } else {
                                document.getElementById('updateErrorMessage').style.display = 'none';
                                return true;
                            }
                        }

                        function confirmUpdate() {
                            return confirm("Do you want to update this news?");
                        }
    </script>

</body>
</html>
