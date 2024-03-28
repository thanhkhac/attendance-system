<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.*" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" />
        <style>
            body {
                background-color: #f8f9fa;
                font-family: 'Roboto', sans-serif;
            }

            .container {
                background-color: #ffffff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                padding: 30px;
                margin-top: 20px;
            }

            .card {
                margin-bottom: 20px;
                border: 1px solid #ddd;
                border-radius: 8px;
                overflow: hidden;
            }

            .card-body {
                padding: 20px;
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

            .no-scroll {
                white-space: pre-line;
            }

            #viewMoreBtn {
                background-color: #007bff;
                border: none;
            }

            #viewMoreBtn:hover {
                background-color: #0056b3;
            }

            .smaller-content {
                transform: scale(9.0);
                transform-origin: top;
                font-size: 20px;
            }

            .list-group {
                display: flex;
                flex-direction: column;
            }

            .list-group-item {
                display: flex;
                flex-direction: column;
                align-items: flex-start;
            }

            .title {
                margin-bottom: 5px;
            }
        </style>
        <title>Hệ thống điểm danh</title>
    </head>

    <body>
        <div class="container-fluid p-0">
            <div class="row">
                <div class="col-md-2">
                    <!-- Sidebar -->
                    <%@include file="Sidebar.jsp" %>
                </div>
                <div class="col-md-10">
                    <!-- Nội dung chính -->
                    <div class="container mt-5">
                        <h2 class="mb-4">Details</h2>

                        <%-- Lấy đối tượng tin tức từ requestScope --%>
                        <c:set var="newsdto" value="${news}" />

                        <div class="card mb-3">
                            <div class="card-body">
                                <h1 class="card-title">
                                    <span class="text-muted">${newsdto.formattedDateTime}</span>
                                    ${newsdto.title}
                                </h1>
                                <p class="card-text" style="font-style: italic;">Create by: ${newsdto.createBy}</p>
                                <p class="card-text no-scroll" style="font-size: 14px;">${newsdto.content}</p>
                                <c:if test="${not empty htmlContent}">
                                    <p class="smaller-content">${htmlContent}</p>
                                </c:if>
                                <c:if test="${empty htmlContent}">
                                    <p>Content is empty or null</p>
                                </c:if>
                            </div>
                        </div>
                        <div>
                            <h2 class="mb-4">Other News</h2>
                            <div id="otherNewsList">
                                <c:forEach var="otherNews" items="${otherNews}" varStatus="loop">
                                    <div class="mb-3" style="display: ${loop.index < 5 ? 'block' : 'none'};">
                                        <h5 class="title">
                                            <span class="text-muted">${otherNews.formattedDateTime}</span>
                                            <c:url var="otherNewsDetailUrl" value="/getdetailnew">
                                                <c:param name="newId" value="${otherNews.newId}" />
                                            </c:url>
                                            <a href="${otherNewsDetailUrl}" class="text-decoration-none">${otherNews.title}</a>
                                        </h5>
                                    </div>
                                </c:forEach>
                            </div>
                            <button id="viewMoreBtn" class="btn btn-primary mb-3" onclick="showMore()">Xem thêm</button>
                        </div>
                    </div>
                </div>
            </div>
            <script>
                function showMore() {
                    var otherNewsList = document.getElementById('otherNewsList');
                    var viewMoreBtn = document.getElementById('viewMoreBtn');
                    for (var i = 5; i < otherNewsList.children.length; i++) {
                        otherNewsList.children[i].style.display = 'block';
                    }
                    viewMoreBtn.style.display = 'none'; // Ẩn nút "Xem thêm" sau khi đã hiển thị tất cả các phần tử
                }
            </script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>

</html>
