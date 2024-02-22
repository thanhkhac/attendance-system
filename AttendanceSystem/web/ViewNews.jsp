<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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

        .pagination {
            margin-top: 20px;
        }

        .pagination-link {
            width: 30px;
            text-align: center;
            margin-right: 5px;
        }

        .pagination-container {
            max-width: 300px;
            margin: auto;
        }

    </style>
    <body>
        <div class="container">
            <h2 class="mb-4">News List - Page ${currentPage}</h2>

            <c:if test="${not empty listN}">
                <c:set var="newsList" value="${listN}" />
                <c:forEach var="news" items="${newsList}">
                    <div class="mb-3">
                        <h5 class="title">
                            <span class="text-muted">${news.formattedDateTime}</span>
                            <c:url var="detailUrl" value="/getdetailnew">
                                <c:param name="newId" value="${news.newId}" />
                            </c:url>
                            <a href="${detailUrl}" class="text-decoration-none">${news.title}</a>
                        </h5>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${empty listN}">
                <p class="text-muted">Không có tin tức.</p>
            </c:if>

            <div class="pagination-container">
                <c:if test="${totalPages > 1}">
                    <ul class="pagination">
                        <c:forEach var="page" begin="1" end="${totalPages}" step="1">
                            <c:url var="pageUrl" value="getallnews">
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



        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
