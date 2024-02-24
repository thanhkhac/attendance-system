<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <title>News List</title>
        <style>
            body {
                background-color: #f8f9fa;
            }

            .container {
                background-color: #ffffff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                padding: 20px;

            }


            .card {
                margin-bottom: 20px;
            }

            .card-title {
                font-size: 1.5rem;
                margin-bottom: 10px;
            }

            .card-text {
                color: #555555;
            }

            .text-muted {
                color: #888888;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            th, td {
                border: 1px solid #dddddd;
                text-align: left;
                padding: 8px;
            }

            th {
                background-color: #f2f2f2;
            }

            .pagination-container {
                display: flex;
                justify-content: center;
                margin-top: 20px;
            }

            .pagination {
                display: flex;
                list-style: none;
                padding: 0;
                margin: 0;
            }

            .pagination-item {
                margin: 0 5px; /* Adjust margin between pagination links */
            }

            .pagination-link {
                display: inline-block;
                padding: 0.5rem 0.75rem;
                border: 1px solid #dee2e6;
                text-decoration: none;
                color: #007bff;
                background-color: #fff;
                border-radius: 0.25rem;
            }

            .pagination-link.active {
                z-index: 3;
                color: #fff;
                background-color: #007bff;
                border-color: #007bff;
            }
            .truncate {
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                max-width: 26ch;
                cursor: pointer;
            }
            .full-content {
                white-space: normal;
                overflow: visible;
                text-overflow: initial;
                max-width: none;
            }
            .ellipsis {
                display: block;
                margin-top: 1em;
            }
            #newsTitle${news.newId}.truncate {
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2">
                    <%@include file="Sidebar.jsp" %>
                </div>
                <div class="col-md-10">
                    <div>
                        <c:set var="createByOptions" value="${requestScope.createByOptions}" />
                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success" role="alert">
                                ${successMessage}
                            </div>
                        </c:if>

                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger" role="alert">
                                ${errorMessage}
                            </div>
                        </c:if>
                        <c:if test="${not empty successUpdate}">
                            <div class="alert alert-success" role="alert">
                                ${successUpdate}
                            </div>
                        </c:if>

                        <c:if test="${not empty errorUpdate}">
                            <div class="alert alert-danger" role="alert">
                                ${errorUpdate}
                            </div>
                        </c:if>
                        <c:if test="${not empty errorDuplicate}">
                            <div class="alert alert-danger" role="alert">
                                ${errorDuplicate}
                            </div>
                        </c:if>
                        <c:if test="${not empty titleEmpty}">
                            <div class="alert alert-danger" role="alert">
                                ${titleEmpty}
                            </div>
                        </c:if>

                        <c:if test="${not empty contentEmpty}">
                            <div class="alert alert-danger" role="alert">
                                ${contentEmpty}
                            </div>
                        </c:if>    
                    </div>
                    <div class="container mt-5">
                        <h2 class="mb-4">News List</h2>
                        <a href="#" class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#insertModal">
                            <i class="fas fa-plus"></i> Insert
                        </a>
                        <!-- Insert Modal -->
                        <div class="modal fade" id="insertModal" tabindex="-1" role="dialog" aria-labelledby="insertModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg" role="document">
                                <div class="modal-content" style="max-height: 800px;">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="insertModalLabel">Insert News</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="insertNews" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                                            <div id="insertErrorMessage" class="alert alert-danger" role="alert" style="display: none;"></div>
                                            <div class="mb-3">
                                                <label for="title" class="form-label">Title</label>
                                                <input type="text" class="form-control" id="title" name="title" required>
                                            </div>
                                            <div class="mb-3">
                                                <label for="content" class="form-label">Content</label>
                                                <textarea class="form-control" id="content" name="content" rows="3" style="max-height: 200px; overflow-y: auto;" required></textarea>
                                            </div>

                                            <div class="mb-3">
                                                <label for="filePath" class="form-label">File Path</label>
                                                <input type="file" class="form-control" id="filePath" name="filePath">
                                            </div>
                                            <div class="mb-3">
                                                <label for="createBy" class="form-label">Create By</label>
                                                <select name="employee">
                                                    <c:forEach var="employee" items="${employees}">
                                                        <option value="${employee.firstName}">${employee.firstName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-primary" onclick="confirmInsert()">Submit</button>
                                        </form>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- News List Table -->
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Content</th>
                                    <th>FilePath</th>
                                    <th>DateTime</th>
                                    <th>CreateBy</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="news" items="${listN}">
                                    <tr>
                                        <td>${news.newId}</td>
                                        <td id="newsTitle${news.newId}" class="truncate" onclick="toggleContent1('newsTitle${news.newId}')">
                                            ${news.title}
                                        </td>

                                        <td id="newsContent${news.newId}" class="truncate" onclick="toggleContent(document.getElementById('newsContent${news.newId}'))">
                                            ${news.content}
                                        </td>
                                        <td style="max-width: 400px; word-wrap: break-word;" title="${news.filePath}">
                                            ${news.filePath}
                                        </td>


                                        <td>${news.formattedDateTime}</td>
                                        <td>${news.createBy}</td>
                                        <td>
                                            <a href="GetInfoNews?id=${news.newId}" class="btn btn-warning" >
                                                <i class="fas fa-edit"></i> Update
                                            </a>
                                            <a href="deletenews?id=${news.newId}" class="btn btn-danger" onclick="confirmDelete()">
                                                <i class="fas fa-trash"></i> Delete
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <!-- Pagination -->
                        <div class="pagination-container">
                            <c:if test="${totalPages > 1}">
                                <ul class="pagination">
                                    <c:forEach var="page" begin="1" end="${totalPages}" step="1">
                                        <c:url var="pageUrl" value="GetAllNewsByHR">
                                            <c:param name="page" value="${page}" />
                                        </c:url>
                                        <li class="page-item">
                                            <a class="page-link pagination-link ${page == currentPage ? 'active' : ''}" href="${pageUrl}">${page}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function showUpdateModal(id, title, content, filePath, dateTime, createBy) {
                document.getElementById('updateId').value = id;
                document.getElementById('updateTitle').value = title;
                document.getElementById('updateContent').value = content;
                document.getElementById('updateFilePath').value = filePath;
                document.getElementById('updateCreateBy').value = createBy;
                $('#updateModal').modal('show');
            }
        </script>
        <script>
            function confirmInsert() {
                return confirm("Do you want to add this new?");
            }

            function confirmDelete() {
                if (confirm("Do you want to delete this new?")) {
                    console.log("Delete action confirmed.");
                }
            }
            function validateForm() {
                var content = document.getElementById('content').value.trim();
                var title = document.getElementById('title').value.trim();

                if (title.length > 500) {
                    document.getElementById('insertErrorMessage').innerHTML = 'Nội dung không thể vượt quá 50 ký tự.';
                    document.getElementById('insertErrorMessage').style.display = 'block';
                    return false;
                } else if (title === '' || content === '') {
                    document.getElementById('insertErrorMessage').innerHTML = 'Title và Content không thể để trống.';
                    document.getElementById('insertErrorMessage').style.display = 'block';
                    return false;
                } else {
                    document.getElementById('insertErrorMessage').style.display = 'none';
                    return true;
                }
            }

            function confirmUpdate() {
                return confirm("Do you want to update this new?");
            }
            function toggleContent(element) {
                var content = element.textContent.trim();

                if (content.length > 10) {
                    element.classList.toggle('truncate');
                    element.classList.toggle('full-content');
                    var ellipsisSpan = element.querySelector('.ellipsis');
                    if (ellipsisSpan) {
                        ellipsisSpan.style.display = 'inline';
                    }
                }
            }
            function toggleContent1(elementId) {
                var element = document.getElementById(elementId);
                var isTruncated = element.classList.contains('truncate');

                if (isTruncated) {
                    // Expand the content
                    element.classList.remove('truncate');
                } else {
                    // Truncate the content
                    element.classList.add('truncate');
                }
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
