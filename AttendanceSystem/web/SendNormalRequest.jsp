<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.*"%>
<%@page import="model.request.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý ca làm việc</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            .right{
                position: absolute;
                width: 83%;
                right: 0px;
            }
            @media screen and (max-width: 768px) {

                .right{
                    position: absolute;
                    width: 100%;
                    right: auto;
                }
            }
        </style>

    </head>
    <%
        ArrayList<RequestTypeDTO> requestTypeDTOs = new RequestTypeDAO().getRequestTypeList();
        request.setAttribute("requestTypeDTOs", requestTypeDTOs);
    %>
    <body>
        <div>
            <%@include file="Sidebar.jsp" %>
            <div class="right">
                <div>
                    <form action="action">
                        <table class="table">
                            <tr>
                                <th>Người gửi:</th>
                                <td>${sessionScope.ACCOUNT.lastName} ${sessionScope.ACCOUNT.middleName} ${sessionScope.ACCOUNT.firstName}</td>
                            </tr>
                            <tr>
                                <th>Ngày gửi</th>
                                <td>Điền sau</td>
                            </tr>
                            <tr>
                                <th>Loại đơn:</th>
                                <td>
                                    <select name="slTitle" class="form-select" id="id">
                                        <c:forEach var="dto" items="${requestScope.requestTypeDTOs}" varStatus="counter">
                                            <option value="${dto.requestTypeID}"> ${dto.requestTypeName}</option> 

                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>Tiêu đề</th>
                                <td>
                                    <input name="slTitle" class="form-control" type="text">
                                </td>
                            </tr>
                            <tr>
                                <th>Nội dung:</th>
                                <td>
                                    <textarea name="txtContent" class="form-control" id="id"  rows="5" cols="10"></textarea>
                                </td>
                            </tr>
                            <t>
                                <th>File:</th>
                                <td>
                                    <input name="file" class="form-control" type="file">
                                </td>
                            </t>
                        </table>
                    </form>
                </div>
            </div>
        </div>
        <!--<script src="assets/Bootstrap5/js/bootstrap.bundle.min.js">-->
        </script>

    </body>
</html>
