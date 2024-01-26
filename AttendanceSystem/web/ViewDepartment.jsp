<%-- 
    Document   : ViewDepartment
    Created on : Jan 26, 2024, 3:44:59 PM
    Author     : acer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>

        <style>
            .table-container{
                margin-left: 270px;
                max-width: 1200px;
                padding-top: 50px;
            }
            @media (max-width:1450px){
                .table-container{
                    margin: 0 50px;
                    padding-top: 50px;
                }
            }

        </style>
    </head>
    <body>
        <%@include file="Sidebar.jsp" %>
        <c:set  var="listDepartment" value="${requestScope.listDepartment}"/>
        <c:set  var="listEmployee"   value="${requestScope.listEmployee}"/>

        <div class="table-container">
            <table class="table table-bordered table-responsive">
                <thead>
                    <tr class="table-dark">
                        <th scope="col">ID</th>
                        <th scope="col">Department Name</th>
                        <th scope="col">Manager ID</th>
                        <th>Assign Manager</th>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listDepartment}" var="d">

                        <tr class="table-hover">
                            <td>${d.getDepartmentID()}</td>
                            <td>${d.getName()}</td>
                            <td>${d.getManagerID()}</td>
                            <td>
                                <form action="DispathController" method="POST">
                                    <input type="hidden" value="${d.getDepartmentID()}" name="departmentID"/>
                                    <input type="submit" value="Assign Manager" name="btAction" class="btn btn-danger" />
                                </form>
                            </td>

                        </tr>


                    </c:forEach>


                </tbody>
            </table>    
        </div>



        <script src="assets/Bootstrap5/js/bootstrap.min.js"/>

    </body>
</html>
