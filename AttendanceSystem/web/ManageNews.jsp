<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.*" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <title>News List</title>
    </head>
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
    </style>
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
    
        <c:if test="${not empty errorDuplicate}">
        <div class="alert alert-danger" role="alert">
            ${errorDuplicate}
        </div>
    </c:if>
    <body>

        <div class="container mt-5">
            <h2 class="mb-4">News List</h2>

            <a href="#" class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#insertModal">
                <i class="fas fa-plus"></i> Insert
            </a>

            <!-- Insert Modal -->
            <div class="modal fade" id="insertModal" tabindex="-1" role="dialog" aria-labelledby="insertModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="insertModalLabel">Insert News</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="insertNews" method="post">
                                <div class="mb-3">
                                    <label for="title" class="form-label">Title</label>
                                    <input type="text" class="form-control" id="title" name="title">
                                </div>
                                <div class="mb-3">
                                    <label for="content" class="form-label">Content</label>
                                    <textarea class="form-control" id="content" name="content" rows="3"></textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="filePath" class="form-label">File Path</label>
                                    <input type="text" class="form-control" id="filePath" name="filePath">
                                </div>
                                <div class="mb-3">
                                    <label for="createBy" class="form-label">Create By</label>
                                    <select name="employee">
                                        <c:forEach var="employee" items="${employees}">
                                            <option value="${employee.firstName}">${employee.firstName}</option> 
                                        </c:forEach>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <table>
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
                            <td>${news.title}</td>
                            <td>${news.content}</td>
                            <td>${news.filePath}</td>
                            <td>${news.formattedDateTime}</td>
                            <td>${news.createBy}</td>
                            <td>
                                <a href="#" class="btn btn-warning" onclick="showUpdateModal('${news.newId}', '${news.title}', '${news.content}', '${news.filePath}', '${news.formattedDateTime}', '${news.createBy}')" data-bs-toggle="modal" data-bs-target="#updateModal">
                                    <i class="fas fa-edit"></i> Update
                                </a>

                                <a href="deletenews?id=${news.newId}" class="btn btn-danger">
                                    <i class="fas fa-trash"></i> Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
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

        <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="updateModalLabel">Update News</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="updatenews" method="post">
                            <input type="hidden" id="updateId" name="id">
                            <div class="mb-3">
                                <label for="updateTitle" class="form-label">Title</label>
                                <input type="text" class="form-control" id="updateTitle" name="title">
                            </div>
                            <div class="mb-3">
                                <label for="updateContent" class="form-label">Content</label>
                                <input type="text" class="form-control" id="updateContent" name="content">
                            </div>
                            <div class="mb-3">
                                <label for="updateFilePath" class="form-label">File Path</label>
                                <input type="text" class="form-control" id="updateFilePath" name="filePath">
                            </div>
                            <div class="mb-3">
                                <label for="updateCreateBy" class="form-label">Create By</label>
                                <select class="form-select" id="updateCreateBy" name="updateCreateBy">
                                    <c:forEach var="employee" items="${employees}">
                                        <option value="${employee.firstName}" <c:if test="${employee.firstName eq createBy}">selected</c:if>>${employee.firstName}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <button type="submit" class="btn btn-primary">Update</button>
                        </form>
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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
