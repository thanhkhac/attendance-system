<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.time.*"%>
<%@page import="model.*"%>
<%@page import="model.EmployeeDTO"%>
<%@page import="control.workday.*"%>
<%@page import="ultility.datetimeutil.DateTimeUtil"%>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/view-calendar.css">
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <style>
            .selected-option {
                margin-top: 5px;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 5px;
                margin-right: 5px;
                background-color: #f5f5f5;
                position: relative;
            }

            .delete-button {
                position: absolute;
                top: 0;
                right: 0;
                padding: 5px;
                cursor: pointer;
                color: #ff0000;
            }
        </style>
    </head>
    <%
        DateTimeUtil dateTimeUtil = new DateTimeUtil();

        int year = dateTimeUtil.getVNLocalDateNow().getYear();
        int month = dateTimeUtil.getVNLocalDateNow().getMonthValue();

        String txtyear = request.getParameter("year");
        String txtmonth = request.getParameter("month");

        if (txtyear != null || txtmonth != null) {
            try {
                year = Integer.parseInt(txtyear);
                month = Integer.parseInt(txtmonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                month = Integer.parseInt(txtmonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        ArrayList<LocalDate> calendar = dateTimeUtil.getCalendar(year, month);
        ArrayList<ShiftDTO> shiftList = new ShiftDAO().getActiveShiftDTO();
        
        HashMap<String, ShiftDTO> shiftMap = new HashMap<>();
        for (ShiftDTO shift : shiftList) {
            shiftMap.put(String.valueOf(shift.getShiftID()), shift);
        }
        
        LocalDate today = dateTimeUtil.getVNLocalDateNow();
        request.setAttribute("today", today);
        request.setAttribute("calendar", calendar);
        request.setAttribute("month", month);
        request.setAttribute("year", year);
        request.setAttribute("shiftList", shiftList);
        request.setAttribute("shiftMap", shiftMap);
    %>
    <body>
        <div class="container">
            <form action="DispatchController" id="myForm" method="POST">
                <input type="hidden" name="month" value="${month}">
                <input type="hidden" name="year" value="${year}">
                <table class="table mytable">
                    <thead>
                        <tr class="text-center ">
                            <th>SUN</th>
                            <th>MON</th>
                            <th>TUE</th>
                            <th>WED</th>
                            <th>THUS</th>
                            <th>FRI</th>
                            <th>SAT</th>
                        </tr>
                        <tr>
                            <c:forEach var="dto" begin="1" end="7">
                                <th>
                                    <select class="form-select col-select" name="shift" onchange="selectcol(this)">
                                        <option value="">Chọn ca</option>
                                        <c:forEach var="shift" items="${shiftList}" varStatus="counter">
                                            <option value="${shift.shiftID}">${shift.name}</option>
                                        </c:forEach>
                                    </select>
                                </th>
                            </c:forEach>
                        </tr>
                    </thead>
                    <c:set var="index" value="${0}"/>
                    <c:forEach var="wkday" items="${requestScope.calendar}" varStatus="counter">
                        <c:if test="${counter.index % 7 == 0}">
                            <tr>
                            </c:if>
                            <td class="mytable-td" style="height: 5rem">
                                <div class="date-block" style="border-bottom: 0px">
                                    <c:if test = "${wkday eq requestScope.today}">
                                        <div class="date text-danger" id="today">
                                            Hôm nay
                                        </div>
                                    </c:if>
                                    <c:if test = "${wkday ne requestScope.today}">
                                        <div class="date">
                                            ${wkday}
                                        </div>
                                    </c:if>
                                </div>
                                <div class="shift-block">
                                    <c:if test = "${wkday > requestScope.today and wkday.getMonthValue() == month}">
                                        <select class="form-select shift--select" name="shift" id="shift" onchange="newSelect(this)">
                                            <option value="">Chọn ca</option>
                                            <c:forEach var="shift" items="${shiftList}" varStatus="counter">
                                                <option value="${wkday}#${shift.shiftID}">${shift.name}</option>
                                            </c:forEach>
                                        </select>
                                    </c:if>
                                    <c:forEach var="paramshift" items="${paramValues.shift}" varStatus="counter">
                                        <c:if test = "${fn:contains(paramshift, wkday)}">
                                            <c:set var="key" value="${fn:substringAfter(paramshift, '#')}" />
                                            <div class="selected-option" id="${paramshift}">
                                                ${requestScope.shiftMap[key].name}
                                                <span class="delete-button"><i class="fa-solid fa-delete-left" aria-hidden="true" onclick="selfDelete(this)"></i></span>
                                                <input type="hidden" name="shift" value="${paramshift}">
                                            </div>
                                        </c:if>
                                    </c:forEach>

                                </div>
                            </td>
                            <c:if test="${(counter.index + 1) % 7 == 0}">
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
                <div class="text-center">
                    <button  type="button" class="btn btn-primary" onclick="validateForm()">Tiếp tục</button>
                    <input type="hidden" name="btAction" value="GetUnscheduleEmployees">
                </div>
            </form>
        </div>

        <!--<script src="assets/Bootstrap5/js/bootstrap.bundle.min.js"></script>-->
        <script>
            function selfDelete(button) {
                var selectedOption = button.closest('.selected-option');
                selectedOption.remove();
            }

            function validateForm() {

                var selects = document.querySelectorAll('.selected-option');

                if (selects.length > 0) {
                    document.getElementById('myForm').submit();
                } else {
                    alert('Vui lòng chọn ca làm việc');
                }
            }

            function newSelect(selectElement) {
                var selectedOption = selectElement.options[selectElement.selectedIndex];
                var selectedText = selectedOption.text;

                var selectedOptions = document.querySelectorAll('.selected-option');

                for (var i = 0; i < selectedOptions.length; i++) {
                    if (selectedOptions[i].id === selectedOption.value) {
                        selectElement.selectedIndex = 0;
                        return;
                    }
                }
                // Create a new div element
                var divElement = document.createElement('div');
                divElement.className = 'selected-option';
                divElement.innerHTML = selectedText;
                divElement.id = selectedOption.value;

                var hiddenInput = document.createElement('input');
                hiddenInput.type = 'hidden';
                hiddenInput.name = 'shift';
                hiddenInput.value = selectedOption.value;


                //Thêm một nút xóa
                var deleteButton = document.createElement('span');
                deleteButton.className = 'delete-button';
                deleteButton.innerHTML = '<i class="fa-solid fa-delete-left"></i>'; // You can customize the delete button content

                // Thêm nút xóa và input hidden vào bên trong thẻ div
                divElement.appendChild(deleteButton);
                divElement.appendChild(hiddenInput);
                selectElement.parentNode.appendChild(divElement);
                selectElement.selectedIndex = 0;

                // Thêm event vào nút xóa của div
                deleteButton.onclick = function () {
                    divElement.remove();
                };
            }

        </script>

        <script>
            function selectcol(colSelect) {
                // Lấy index của cột đã chọn
                var columnIndex = colSelect.parentNode.cellIndex;

                // Lấy giá trị đã chọn trong col-select
                var selectedValue = colSelect.selectedIndex;

                // Lấy tất cả các ô select trong cùng cột
                var selectsInColumn = document.querySelectorAll('.mytable tr td:nth-child(' + (columnIndex + 1) + ') select');

                // Đặt giá trị của tất cả các ô select trong cùng cột
                selectsInColumn.forEach(function (select) {
                    select.selectedIndex = selectedValue;
                    newSelect(select);
                });
            }
        </script>
    </body>


</html>
