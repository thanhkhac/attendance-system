<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" integrity="sha384-qO7qc2UBT/pTnmsDMz94Q3LZK2b3SjU+gW80a/iQ7IbbVZZxEdaOX1AyOvP4+DwJ" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
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
            margin-top: 20px;
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

.pagination-link {
    border: 2px solid black;
    padding: 5px 10px;
    margin: 0 4px;
    border-radius: 4px;
    color: black; /* Set text color to black */
}

.pagination-link.active {
    background-color: #007bff;
    color: #fff;
    border-color: #007bff;
}

.sidebar {
    background-color: black; /* Set sidebar background color to black */
    border-radius: 8px;
    padding: 20px;
}

    </style>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2">
                    <%@include file="Sidebar.jsp" %>
                </div>
                <div class="col-md-10">
                    <!-- Nội dung chính -->
                    <div class="container">
                        <h2 class="mb-4">News List - Page ${currentPage}</h2>
                        <form action="SearchAllDetailNews" method="get" class="col-md-10 row" id="yourFormId">
                            <div class="mb-4 row">
                                <div class="col-md-3 mb-2">
                                    <label for="fromDate" class="form-label">From Date:</label>
                                    <input type="date" class="form-control" id="fromDate" name="fromDate" placeholder="Select From Date">
                                </div>

                                <div class="col-md-3 mb-2">
                                    <label for="toDate" class="form-label">To Date:</label>
                                    <input type="date" class="form-control" id="toDate" name="toDate" placeholder="Select To Date" >
                                </div>

                                <div class="col-md-2 mb-2 d-flex align-items-end">
                                    <button type="button" class="btn btn-primary" onclick="submitForm()">Submit</button>
                                </div>
                            </div>
                        </form>
                        <!-- Nội dung tin tức -->
                        <c:if test="${not empty listN}">
                            <c:set var="newsList" value="${listN}" />
                            <c:forEach var="news" items="${newsList}">
                                <div class="mb-3">
                                    <h5 class="title">
                                        <a><img style="width: 20px; margin-bottom:5px; margin-right: 5px" src="assets/img/next.png" alt="Right Arrow"/></a>
                                        <span class="text-muted">${news.formattedDateTime}</span>
                                        <c:url var="detailUrl" value="/getdetailnew">
                                            <c:param name="newId" value="${news.newId}" />
                                        </c:url>
                                        <a href="${detailUrl}" class="text-decoration-none">${news.title}</a>
                                    </h5>

                                </div>
                            </c:forEach>
                        </c:if>

                        <!-- Thông báo khi không có tin tức -->
                        <c:if test="${empty listN}">
                            <p class="text-muted">Không có tin tức.</p>
                        </c:if>

                        <!-- Phân trang -->
                        <!-- Phân trang -->
                        <div class="pagination-container">
                            <c:if test="${totalPages > 1}">
                                <ul class="pagination justify-content-center text-center">
                                    <c:url var="searchUrl" value="SearchAllDetailNews">
                                        <c:param name="fromDate" value="${param.fromDate}" />
                                        <c:param name="toDate" value="${param.toDate}" />
                                    </c:url>

                                    <c:url var="getAllUrl" value="getallnews">

                                    </c:url>

                                    <c:forEach var="currentPage" begin="1" end="${totalPages}" step="1">
                                        <c:url var="pageUrl" value="${searchUrl}">
                                            <c:param name="page" value="${currentPage}" />
                                        </c:url>
                                        <li class="page-item">
                                            <a class="page-link pagination-link ${currentPage == param.page && currentPage == 1 ? 'active' : ''} ${currentPage < 10 ? 'text-center' : 'text-left'}" href="${pageUrl}">${currentPage}</a>
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
            function submitForm() {
                document.getElementById("yourFormId").submit();
            }
            document.getElementById("fromDate").addEventListener("change", function () {
                sessionStorage.setItem("fromDateValue", this.value);
            });

         
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
