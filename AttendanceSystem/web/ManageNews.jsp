<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Quản lý tin tức</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" integrity="sha384-qO7qc2UBT/pTnmsDMz94Q3LZK2b3SjU+gW80a/iQ7IbbVZZxEdaOX1AyOvP4+DwJ" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    </head>
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

    .table {
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
        background-color: #343a40;
        color: white;
    }

    tr:nth-child(even) td {
        background-color: white;
    }

    tr:nth-child(odd) td {
        background-color: #FFF7F1;
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
        margin: 0 5px;
    }

    .pagination-link {
        display: inline-block;
        border: 2px solid black;
        padding: 5px 10px;
        margin: 0 4px;
        border-radius: 4px;
        color: black;
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
<body>
    <div class="container-fluid ps-0">
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
                <div class="container">
                    <h2 class="mb-4">List Management</h2>
                    <div class="row align-items-end">
                        <form action="SearchNewsByHR" method="get" class="col-md-10 row">
                            <div class="col-md-3 mb-2">
                                <label for="searchTitle" class="form-label">Title:</label>
                                <input type="text" class="form-control" id="searchTitle" value="${param.title}" name="title" placeholder="Enter Title">
                            </div>

                            <div class="col-md-3 mb-2">
                                <label for="fromDate" class="form-label">From Date:</label>
                                <input type="date" class="form-control" id="fromDate" value="${param.fromDate}" name="fromDate" placeholder="Select From Date" onchange="saveDateToStorage('fromDate', this.value)">
                            </div>

                            <div class="col-md-3 mb-2">
                                <label for="toDate" class="form-label">To Date:</label>
                                <input type="date" class="form-control" id="toDate" name="toDate" value="${param.toDate}" placeholder="Select To Date" onchange="saveDateToStorage('toDate', this.value)">
                            </div>


                            <div class="col-md-2 mb-2 d-flex align-items-end">
                                <button type="submit" class="btn btn-primary">Search</button>
                            </div>
                        </form>
                    </div>
                    <!--                    <div class="col-md-10 row">
                                            <form id="combinedForm" action="FilteringByHR" method="get" class="row g-3">
                                                <div class="col-md-5 mb-3">
                                                    <label for="createBy" class="form-label">Create By</label>
                                                    <select name="employee" class="form-select form-select-sm w-100">
                                                        <option value="" selected disabled>Select an employee</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-5 mb-3">
                                                    <label for="sortOrder" class="form-label">Sort Order:</label>
                                                    <select name="sortOrder" class="form-select form-select-sm w-100">
                                                        <option value="desc">Descending</option>
                                                        <option value="asc">Ascending</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-2 mb-3 d-flex align-items-end">
                                                    <button type="submit" class="btn btn-primary">Submit</button>
                                                </div>
                                            </form>
                                        </div>-->
                </div>

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
                                <div class="col-md-2 mb-3">
                                        <label for="createBy" class="form-label">Create By</label>
                                        <input type="text" class="form-control" id="employeeSelect" name="employee" value="${sessionScope.ACCOUNT.firstName}" readonly="">
                                    </div>
                                    <button type="submit" class="btn btn-primary" onclick="confirmInsert()">Submit</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- News List Table -->    
                <div class="container mt-1">
                    <a href="#" class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#insertModal">
                        <i class="fas fa-plus"></i> Insert
                    </a>
                    <c:if test="${empty listN}">
                        <div class="alert alert-info" role="alert">
                            No articles found.
                        </div>
                    </c:if>
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
                </div>
                <!-- Pagination -->
                <div class="pagination-container">
                    <c:if test="${totalPages > 1}">
                        <ul class="pagination">
                            <c:url var="searchPageUrl" value="SearchNewsByHR">
                                <c:param name="title" value="${param.title}" />
                                <c:param name="fromDate" value="${param.fromDate}" />
                                <c:param name="toDate" value="${param.toDate}" />
                            </c:url>

                            <c:url var="filterPageUrl" value="FilteringByHR">
                                <c:param name="sortOrder" value="${param.sortOrder}" />
                                <c:param name="employee" value="${param.employee}" />
                            </c:url>

                            <c:forEach var="page" begin="1" end="${totalPages}" step="1">
                                <c:url var="pageUrl" value="${searchPageUrl}">
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
        var employeeSelect = document.getElementById('employeeSelect');

        if (title.length > 500) {
            document.getElementById('insertErrorMessage').innerHTML = 'Nội dung không thể vượt quá 50 ký tự.';
            document.getElementById('insertErrorMessage').style.display = 'block';
            return false;
        } else if (title === '' || content === '') {
            document.getElementById('insertErrorMessage').innerHTML = 'Title và Content không thể để trống.';
            document.getElementById('insertErrorMessage').style.display = 'block';
            return false;
        } else if (employeeSelect.value === "") {
            document.getElementById('insertErrorMessage').innerHTML = 'Vui lòng chọn một nhân viên.';
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
    function submitForms() {
        document.getElementById("filterForm").submit();
        document.getElementById("sortForm").submit();
    }


</script>
</script>
<script type="text/javascript">
    $(function () {
        $('#datepicker').datepicker();
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>