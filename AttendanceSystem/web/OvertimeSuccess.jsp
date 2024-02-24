<%-- 
    Document   : OvertimeSuccess
    Created on : Feb 23, 2024, 4:24:07 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.0/css/boxicons.min.css" integrity="sha512-pVCM5+SN2+qwj36KonHToF2p1oIvoU3bsqxphdOIWMYmgr4ZqD3t5DjKvvetKhXGc/ZG5REYTT6ltKfExEei/Q==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/MaterialDesign-Webfont/5.3.45/css/materialdesignicons.css" integrity="sha256-NAxhqDvtY0l4xn+YVa6WjAcmd94NNfttjNsDmNatFVc=" crossorigin="anonymous" />
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> 
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <style>





            .overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.7);
                z-index: 1;
                display: block; /* Luôn hiển thị overlay */
            }

            .centered-div {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: #fff;
                padding: 20px;
                border: 1px solid #ccc;
                text-align: center;
                height: 275px;
                border-radius: 9px;
            }

            .myButton {
                background-color: black;
                color: white;
                border-radius: 14px;
                border: none;
                padding: 10px 20px;
                font-size: 16px;
                cursor: pointer;
                transition: transform 0.3s ease;
                height: 50px;
                width: 153px;
            }

            .myButton:hover {
                transform: translateY(-5px);
            }
        </style>
    </head>
    <body>
        <%
          String ThanhCong = (String)request.getAttribute("THANHCONG");
          String ThatBai = (String)request.getAttribute("THATBAI");
          String Start = (String)request.getAttribute("START");
          String End = (String)request.getAttribute("END");
          String Date = (String)request.getAttribute("DATE");
          if(ThanhCong!=null){
        %>

        <div class="overlay">
            <div class="centered-div">
                <h2 style="font-size: 25px;
                    "><%=ThanhCong%></h2>
<div style="margin-top: 107px;">
    <form action="addEmployeeOvertime">
        <input type="hidden" name="startTime" value="<%=Start%>">
        <input type="hidden" name="endTime" value="<%=End%>">
        <input type="hidden" name="date" value="<%=Date%>">
        <input type="hidden" name="Add" value="Add">
                    <button style="position: absolute;left:67px" class="myButton">Thêm nhân viên</button>
    </form>
        <a href="Overtime.jsp" target="target">
        <button  style="position: absolute;right:67px" class="myButton">Lịch tăng ca</button>
        </a>
                </div>
            </div>

        </div>
        <%}if(ThatBai!=null){%>
        <div class="overlay">
            <div class="centered-div" >
                <h2 style="font-weight: 700;font-family: cursive;"><%=ThatBai%></h2>
<div style="margin-top: 107px;">
                    <form action="addEmployeeOvertime">
        <input type="hidden" name="startTime" value="<%=Start%>">
        <input type="hidden" name="endTime" value="<%=End%>">
        <input type="hidden" name="date" value="<%=Date%>">
                    <button style="position: absolute;left:67px" class="myButton">Thêm nhân viên</button>
    </form>
                    <a href="Overtime.jsp" target="target">
        <button  style="position: absolute;right:67px" class="myButton">Lịch tăng ca</button>
        </a>
                </div>
            </div>
        </div>
        <%}%>
        
        <script>
        
        </script>
    </body>
</html>
