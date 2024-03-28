<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Hệ thống điểm danh</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <style>
            body{
                font-family: sans-serif;
                background-color: steelblue;
            }
            .content{
                background-color: white;
                max-width: 65%;
                margin: auto;
                padding: 20px;
                margin-top: 10px;
            }
            .content-redirect{
                background-color: #F5F5F5;
                border-radius: 5px;
                padding: 4px;
            }
            .content-redirect p{
                margin: 0px;
                font-size: large;
            }
            .content-request{
                margin-top: 50px;
                padding-bottom: 30px;
                /*border-bottom: 1px solid grey;*/
            }
            .content-request-type{
                border: 1px solid grey;
                padding: 10px;
                font-size: larger;
                font-weight: 600;
            }
            .content-request-type label{
                padding-right: 10px;
                border-right: 1px solid grey;
            }
            .content-request-type select{
                margin-left: 10px;
                font-size: medium;
                border: 1px solid grey;
                border-radius: 5px;
                padding: 5px;
            }
        </style>

    </head>
    <body>
        <div class="content">
            <div class="content-redirect">
                <p><a href="HomePage.jsp">Trang chủ</a> | Tình trạng đơn</p>
            </div>  
            <div class="text-center">
                <h1 style="margin: 30px">Danh sách đơn đã gửi</h1>
            </div>
            <div class="content-request">
                <div class="content-request-type">
                    <label for="request-type">Loại đơn: </label>
                    <select id="mySelect" name="mySelect">
                        <option value="">Chọn Loại Đơn</option>
                        <option value="ViewSentLeaveRequest.jsp">Đơn nghỉ phép</option>
                        <option value="ViewSentOvertimeRequest.jsp">Đơn làm ngoài giờ</option>
                        <option value="ViewSentOtherRequest">Đơn khác</option>
                    </select>
                </div>
            </div>
            <table class="table table-bordered">
                <thead class="table-primary">
                    <tr>
                        <th>Tiêu đề</th>
                        <th>Ngày gửi</th>
                        <th>Loại đơn</th>
                        <th>Trạng thái</th>
                        <th>Người phản hồi</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requests}" var="request">
                        <tr data-toggle="modal" data-target="#modal${request.requestID}">
                            <!--<td>${request.requestID}</td>-->

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
                                        <i class="fas fa-clock text-warning"></i> Đang chờ
                                    </c:when>
                                    <c:when test="${request.status == true}">
                                        <i class="fas fa-check text-success"></i> Đã duyệt
                                    </c:when>
                                    <c:when test="${request.status == false}">
                                        <i class="fas fa-times text-danger"></i> Từ chối
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
                                    Không có thông tin
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${numberOfPages > 1}">
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="ViewSentOtherRequest?page=${currentPage - 1}" aria-label="Previous">
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
                                        <a class="page-link" href="ViewSentOtherRequest?page=${i}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${currentPage < numberOfPages}">
                            <li class="page-item">
                                <a class="page-link" href="ViewSentOtherRequest?page=${currentPage + 1}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </div>

        <c:forEach items="${requests}" var="request">
            <div  data-backdrop="static" class="modal fade" id="modal${request.requestID}" tabindex="-1" data-bs-backdrop="static" role="dialog" aria-labelledby="modalLabel${request.requestID}" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalLabel${request.requestID}">Chi tiết đơn</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <form action="ProcessOtherRequest" onsubmit="return confirmSubmit()">
                            <div class="modal-body">
                                <table class="table">
                                    <tr>
                                        <th>Nhân viên</th>
                                            <c:forEach var="e" items="${employees}" varStatus="counter">
                                                <c:if test="${e.employeeId eq request.employeeID}">
                                                <td>${e.lastName} ${e.middleName} ${e.firstName}</td>
                                            </c:if>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <th>Tiêu đề</th>
                                        <td>${request.title}</td>
                                    </tr>
                                    <tr>
                                        <th>Ngày gửi</th>
                                        <td>${request.sentDate.toLocalDate()} ${request.sentDate.hour}:${request.sentDate.minute}</td>
                                    </tr>
                                    <tr>
                                        <th>Loại đơn</th>
                                        <td>                            <c:forEach var="rqt" items="${requestTypes}" varStatus="counter">
                                                <c:if test = "${request.typeID eq rqt.requestTypeID}">
                                                    ${rqt.requestTypeName}
                                                </c:if>
                                            </c:forEach></td>
                                    </tr>
                                    <tr>
                                        <th>Nội dung</th>
                                        <td>${request.content}</td>
                                    </tr>
                                    <tr>
                                        <th>Trạng thái</th>
                                        <td>
                                            <c:choose>
                                                <c:when test="${empty request.status}">
                                                    Đang chờ
                                                </c:when>
                                                <c:when test="${request.status == true}">
                                                    Đã duyệt
                                                </c:when>
                                                <c:when test="${request.status == false}">
                                                    Từ chối
                                                </c:when>
                                            </c:choose>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Phản hồi của người duyệt</th>
                                        <td>
                                            <textarea readonly id="id" name="processnote" rows="5" class="form-control" >${request.processNote}</textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Phản hồi bởi</th>
                                        <td>
                                            <c:forEach var="e" items="${employees}" varStatus="counter">
                                                <c:if test="${e.employeeId eq request.respondedBy}">
                                                    ${e.lastName} ${e.middleName} ${e.firstName}
                                                </c:if>
                                            </c:forEach>
                                            <c:if test = "${empty request.respondedBy}">
                                                Không có thông tin
                                            </c:if>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>


        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <script>
                            document.getElementById('mySelect').addEventListener('change', function () {
                                var selectedOption = this.options[this.selectedIndex].value;
                                if (selectedOption !== "") {
//                    window.open(selectedOption, '_blank');
                                    window.location.href = selectedOption;
                                }
                            });
        </script>
    </body>
</html>
