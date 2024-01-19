
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.time.ZoneId"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Calendar"%>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css">
        <style>
            th {
                padding: 5em;
            }

            .week td {
                height: 7em;
                background-color: rgb(231, 255, 179);
            }

            .week td:nth-child(-n+6) {
                border-right: 1px solid rgb(137, 137, 137);
            }

            .week__day-number {
                color: white;
                font-weight: 500;
                background-color: #73c422;
                border-radius: 20px;
            }

            @media (max-width: 768px) {
                th {
                    display: none;
                }

                .week td {
                    display: block;
                    width: 15em;
                    /* box-sizing: border-box; */
                }

                tr {
                    /* background-color: #f2f2f2; */
                    /* margin-bottom: 8px; */
                }
                .week td:nth-child(-n+6) {
                    border-right: none;
                }
                .emptyday{
                    display: none !important;
                }
            }
        </style>
    </head>

    <% String txtYear = request.getParameter("year");%>
    <% String txtMonth = request.getParameter("month");%>
    <% ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh"); %>
    <% LocalDate localDate = LocalDate.now(zoneId); %>

    <%
        int year = (txtYear == null) ? (localDate.getYear()) : (Integer.parseInt(txtYear));
        int month = (txtMonth == null) ? (localDate.getMonth().getValue()) : (Integer.parseInt(txtMonth));
        month = month - 1;
        Calendar calendar = Calendar.getInstance(); 
        calendar.set(year, month, 1); 
        int firstDatePos =  calendar.get(Calendar.DAY_OF_WEEK) - 1;
    %>

    <body>
        <div>Month <%= year %></div>
        <div>Month <%= month %></div>

        <div class="container">
            <table class="table rounded-3 overflow-hidden">
                <tr class="table-dark">
                    <th>Chủ Nhật</th>
                    <th>Thứ Hai</th>
                    <th>Thứ Ba</th>
                    <th>Thứ tư</th>
                    <th>Thứ Năm</th>
                    <th>Thứ Sáu</th>
                    <th>Thứ Bảy</th>
                </tr>
                <tr class="week text-center">
                    <%
                        for (int i = 0; i < firstDatePos; i++) {
                    %>
                    <td class="week__day emptyday">
                        <div>
                            <div class="week__day-number w-100">
                            </div>
                            <div class="daytime">
                            </div>
                        </div>
                    </td>
                    <%
                        }   
                    %>

                    <%
                        while((calendar.get(Calendar.DAY_OF_WEEK)) != Calendar.SUNDAY){
                    %>
                    <td class="week__day">
                        <div>
                            <div class="week__day-number w-100">
                                <%= calendar.get(Calendar.DAY_OF_MONTH) %>
                            </div>
                            <div class="daytime">
                                7:30 - 17:30
                            </div>
                        </div>
                    </td>
                    <%
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        }
                    %>
                </tr>
                <%
                    while (calendar.get(Calendar.MONTH) == month){
                %>
                <%
                    if((calendar.get(Calendar.DAY_OF_WEEK)) == Calendar.SUNDAY){
                %>
                <tr class="week text-center">
                    <%
                        }
                    %>
                    <td class="week__day">
                        <div>
                            <div class="week__day-number w-100">
                                <%= calendar.get(Calendar.DAY_OF_MONTH) %>
                            </div>
                            <div class="daytime">
                                7:30 - 17:30
                            </div>
                        </div>
                    </td>

                    <%
                        if((calendar.get(Calendar.DAY_OF_WEEK)) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(calendar.DAY_OF_MONTH)){
                    %>
                </tr>
                <%
                    }
                %>
                <%
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
                %>


            </table>
        </div>
    </body>

</html>
