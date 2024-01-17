<%-- 
    Document   : TestJsp
    Created on : Jan 16, 2024, 10:17:55 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <title>JSP Page</title>
        <style>
            .tableFixHead {
                margin-top: 200px;
                overflow: auto;
                
            }
            .tableFixHead thead th{
                position: sticky;
                top: 0;
                z-index: 1;
            }
            tr.space-under>td{
                padding-bottom: 1em;
            }
            table {
                border-collapse: separate;
                border-spacing: 0 10px;
            }
        </style>
    </head>
    <body>
        <c:set var="List" value="${requestScope.List}" />

        <div class="container table-responsive tableFixHead"  >

            <table class="table table-hover ">
                <thead>
                <th>EmployeeID</th>
                <th>FirstName</th>
                <th>MiddleName</th>
                <th>LastName</th>
                <th>Email</th>

                </thead>
                <tbody>
                    <c:forEach items="${List}" var="a">
                        <tr class="table-primary space-under ">
                            <td>${a.getEmployeeID()}</td>
                            <td>${a.getFirstName()}</td>
                            <td>${a.getMiddleName()}</td>
                            <td>${a.getLastName()}</td>
                            <td>${a.getEmail()}</td>
                        </tr>
                    </c:forEach>
                </tbody>

            </table>

        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    </body>
</html>
