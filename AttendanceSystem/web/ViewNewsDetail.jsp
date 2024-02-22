<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.*" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
        </style>
        <title>News Detail</title>
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="mb-4">Chi tiết tin tức</h2>

            <%-- Lấy đối tượng tin tức từ requestScope --%>
            <% NewsDTO newsdto = (NewsDTO) request.getAttribute("news"); %>

            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">
                        <span class="text-muted"><%= newsdto.getFormattedDateTime() %></span>
                        <%= newsdto.getTitle() %>
                    </h5>
                    <p class="card-text">
                        <%= newsdto.getContent() %>
                    </p>
                    <p class="card-text">
                        Create by: <%= newsdto.getCreateBy() %>
                    </p>
                </div>
            </div>

            <h2 class="mb-4">Tin tức khác</h2>

            <c:forEach var="otherNews" items="${otherNews}">
                <div class="mb-3">
                    <h5 class="title">
                        <span class="text-muted">${otherNews.formattedDateTime}</span>
                        <c:url var="otherNewsDetailUrl" value="/getdetailnew">
                            <c:param name="newId" value="${otherNews.newId}" />
                        </c:url>
                        <a href="${otherNewsDetailUrl}" class="text-decoration-none">
                            ${otherNews.title}
                        </a>
                    </h5>
                </div>
            </c:forEach>

        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
