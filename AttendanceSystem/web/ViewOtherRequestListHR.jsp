<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Request List</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <table class="table table-bordered">
            <thead class="table-primary">
                <tr>
                    <!--<th>Request ID</th>-->
                    <th>Employee</th>
                    <th>Title</th>
                    <th>Sent Date</th>
                    <th>Type</th>
                    <th>Status</th>
                    <th>Responded By</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requests}" var="request">
                    <tr data-toggle="modal" data-target="#modal${request.requestID}">
                        <!--<td>${request.requestID}</td>-->

                        <c:forEach var="e" items="${employees}" varStatus="counter">
                            <c:if test="${e.employeeId eq request.employeeID}">
                                <td>${e.lastName} ${e.middleName} ${e.firstName}</td>
                            </c:if>
                        </c:forEach>

                        <td>${request.title}</td>

                        <td>${request.sentDate.toLocalDate()}</td>

                        <td>
                            <c:forEach var="rqt" items="${requestTypes}" varStatus="counter">
                                <c:if test = "${request.typeID eq rqt.requestTypeID}">
                                    ${rqt.requestTypeName}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${empty request.status}">
                                    <i class="fas fa-clock text-warning"></i> Waiting
                                </c:when>
                                <c:when test="${request.status == true}">
                                    <i class="fas fa-check text-success"></i> Approved
                                </c:when>
                                <c:when test="${request.status == false}">
                                    <i class="fas fa-times text-danger"></i> Denied
                                </c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:forEach var="e" items="${employees}" varStatus="counter">
                                <c:if test="${e.employeeId eq request.respondedBy}">
                                    ${e.lastName} ${e.middleName} ${e.firstName}
                                </c:if>
                            </c:forEach>
                            <c:if test = "${empty request.respondedBy}">
                                No information
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Modal Bootstrap -->
        <c:forEach items="${requests}" var="request">
            <div  data-backdrop="static" class="modal fade" id="modal${request.requestID}" tabindex="-1" data-bs-backdrop="static" role="dialog" aria-labelledby="modalLabel${request.requestID}" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalLabel${request.requestID}">Request Details</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <form action="ProcessOtherRequest" onsubmit="return confirmSubmit()">
                            <div class="modal-body">
                                <table class="table">
                                    <tr>
                                        <th>Employee</th>
                                            <c:forEach var="e" items="${employees}" varStatus="counter">
                                                <c:if test="${e.employeeId eq request.employeeID}">
                                                <td>${e.lastName} ${e.middleName} ${e.firstName}</td>
                                            </c:if>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <th>Title</th>
                                        <td>${request.title}</td>
                                    </tr>
                                    <tr>
                                        <th>Sent Date</th>
                                        <td>${request.sentDate.toLocalDate()} ${request.sentDate.hour}:${request.sentDate.minute}</td>
                                    </tr>
                                    <tr>
                                        <th>Type</th>
                                        <td>                            <c:forEach var="rqt" items="${requestTypes}" varStatus="counter">
                                                <c:if test = "${request.typeID eq rqt.requestTypeID}">
                                                    ${rqt.requestTypeName}
                                                </c:if>
                                            </c:forEach></td>
                                    </tr>
                                    <tr>
                                        <th>Content</th>
                                        <td>${request.content}</td>
                                    </tr>
                                    <tr>
                                        <th>File Path</th>
                                        <td>
                                            <c:if test = "${not empty request.filePath}">
                                                <a href="${request.filePath}">Attachment file</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Status</th>
                                        <td>
                                            <c:choose>
                                                <c:when test="${empty request.status}">
                                                    Waiting
                                                </c:when>
                                                <c:when test="${request.status == true}">
                                                    Approved
                                                </c:when>
                                                <c:when test="${request.status == false}">
                                                    Denied
                                                </c:when>
                                            </c:choose>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Process Note</th>
                                        <td>
                                            <textarea id="id" name="processnote" rows="5" class="form-control" >${request.processNote}</textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Responded By</th>
                                        <td>
                                            <c:forEach var="e" items="${employees}" varStatus="counter">
                                                <c:if test="${e.employeeId eq request.respondedBy}">
                                                    ${e.lastName} ${e.middleName} ${e.firstName}
                                                </c:if>
                                            </c:forEach>
                                            <c:if test = "${empty request.respondedBy}">
                                                No information
                                            </c:if>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" name="status" value="true" class="btn btn-success">Approve</button>
                                <button type="submit" name="status" value="false" class="btn btn-danger">Deny</button>
                                <input name="requestId" type="hidden" value="${request.requestID}">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>

        <c:if test="${numberOfPages > 1}">
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="ViewOtherRequestListHR?page=${currentPage - 1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">Previous</span>
                            </a>
                        </li>
                    </c:if>
                    <c:forEach begin="1" end="${numberOfPages}" var="i">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <li class="page-item active">
                                    <span class="page-link">${i}</span>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item">
                                    <a class="page-link" href="ViewOtherRequestListHR?page=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${currentPage < numberOfPages}">
                        <li class="page-item">
                            <a class="page-link" href="ViewOtherRequestListHR?page=${currentPage + 1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                                <span class="sr-only">Next</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </c:if>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
                            function confirmSubmit() {
                                return confirm("Please confirm processing of this application");
                            }
        </script>
    </body>
</html>
