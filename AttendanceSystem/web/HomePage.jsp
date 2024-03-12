<%-- 
    Document   : HomePage
    Created on : Mar 16, 2024, 9:37:38 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.NewsDTO" %>
<%@page import="model.NewsDAO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <script src="assets/Bootstrap5/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> 
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2.0.5/FileSaver.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.17.4/xlsx.full.min.js"></script>

        <style>
            #carouselExampleCaptions {
                margin: 0 auto; /* This centers the carousel horizontally */
                border-radius: 20px;
            }

            .chart-container {
                width: 80%;
                margin: 50px auto;
            }

            canvas {
                width: 100%;
                height: auto;
            }
            #totalTime{
                background-color: #f4f4f4;
                padding: 10px;
                border-radius: 13px;
                font-size: smaller;
                margin-top: 74px;
            }
            .button {
                background-color: #04AA6D; /* Green */
                border: none;
                color: white;
                padding: 5px 32px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                transition-duration: 0.8s;
                cursor: pointer;
            }
            .button2 {
                background-color: white;
                color: black;
                border: 2px solid #008CBA;
            }

            .button2:hover {
                background-color: #008CBA;
                color: white;
            }


            .b {
                padding: 0;
                margin: 0;
                box-sizing: border-box;
                font-size: 16px;
                color: #000;
                font-family: consolas, monaco, menlo, courier, monospace;
                border: 0;
                line-height: normal;
            }

            .c {
                text-align: center;
                margin: auto;
            }

            .ib {
                display: inline-block;
            }
            .circle {
                height: 300px;
                width: 300px;
                border: 10px solid #333;
                background-color: #ddd;
                background-image: url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiIHZpZXdCb3g9IjAgMCAxIDEiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPgo8cmFkaWFsR3JhZGllbnQgaWQ9ImczNzMiIGdyYWRpZW50VW5pdHM9InVzZXJTcGFjZU9uVXNlIiBjeD0iNTAlIiBjeT0iNTAlIiByPSIyMDAlIj4KPHN0b3Agc3RvcC1jb2xvcj0iI0ZGRkZGRiIgb2Zmc2V0PSIwIi8+PHN0b3Agc3RvcC1jb2xvcj0iIzg4OCIgb2Zmc2V0PSIxIi8+CjwvcmFkaWFsR3JhZGllbnQ+CjxyZWN0IHg9Ii01MCIgeT0iLTUwIiB3aWR0aD0iMTAxIiBoZWlnaHQ9IjEwMSIgZmlsbD0idXJsKCNnMzczKSIgLz4KPC9zdmc+);
                background-position: center;
                background-repeat: no-repeat;
                border-radius: 20px;
                overflow: hidden;
                display: none;
                transition: border-radius 0.25s;
            }
            .n {
                position: absolute;
                width: 280px;
                height: 22px;
                margin-top: 129px;
                transform-origin: center center;
                z-index: 2;
            }
            .br {
                position: absolute;
                margin-top: 80px;
                margin-left: 85px;
                font-size: 24px;
                width: 100px;
                color: #bbb;
                text-shadow: 1px 1px 2px #fff;
            }
            .dn {
                display: none;
                margin-top: 10px;
            }
            .n:first-child {
                transform: rotate(-60deg)
            }
            .n:first-child > .tn {
                transform: rotate(60deg);
            }
            .n:nth-child(2) {
                transform: rotate(-30deg)
            }
            .n:nth-child(2) > .tn {
                transform: rotate(30deg);
            }
            .n:nth-child(3) {
                transform: rotate(0)
            }
            .n:nth-child(3) > .tn {
                transform: rotate(0deg);
            }
            .n:nth-child(4) {
                transform: rotate(30deg)
            }
            .n:nth-child(4) > .tn {
                transform: rotate(-30deg);
            }
            .n:nth-child(5) {
                transform: rotate(60deg)
            }
            .n:nth-child(5) > .tn {
                transform: rotate(-60deg);
            }
            .n:nth-child(6) {
                transform: rotate(90deg)
            }
            .n:nth-child(6) > .tn {
                transform: rotate(-90deg);
            }
            .tn {
                float: right;
                text-shadow: 1px 1px 2px #fff;
                color: #888;
                padding: 4px;
                height: 22px;
                width: 22px;
                font-size: 12px;
                border-radius: 50%;
                transform-origin: center center;
            }
            .tn:first-of-type {
                float: left;
            }
            .needle {
                position: absolute;
                height: 4px;
                background: #333;
                border-radius: 100% 5px 5px 100%;
                width: 145px;
                margin-top: 138px;
                margin-left: 12px;
                transform-origin: 128px 50%;
                background: brown;
                z-index: 5;
            }
            .medium {
                height: 8px;
                width: 120px;
                margin-top: 136px;
                margin-left: 37px;
                background: rosybrown;
                z-index: 4;
                transform-origin: 102px 50%;
            }
            .short {
                height: 10px;
                width: 100px;
                margin-top: 136px;
                margin-left: 57px;
                background: #333;
                transform-origin: 83px 50%;
                z-index: 3;
            }
            .pivot {
                height: 16px;
                width: 16px;
                border-radius: 50%;
                border: 4px solid brown;
                background: #fff;
                position: relative;
                float: right;
                right: 10px;
                top: -6px;
                box-shadow: 0 0 4px #000 inset;
            }
            .circle:hover {
                cursor: pointer;
                border-radius: 50%;
            }
            .content{
                text-align: center;
                color: #498CB6;
                font-weight: revert;
                font-size: -webkit-xxx-large;
                font-size: 48px;
            }
            .chucNang{
                width: auto ;
                height: 140px;
                background-color: red;
                margin-right: 60px;
                background: #fff;
                box-shadow: 0 0 12px #c5c5c5;
                border-radius: 15px;
                text-align: center;
                transition: transform 0.5s ease;
            }
            .chucNang:hover {
                transform: translate(0, -10px); /* Dịch chuyển lên trên 10px khi hover */
            }
            .image{
                width: 50px;
                height: 50px
            }
            .text{
                font-size: 19px;
                font-weight: 500;
                color: #498CB6;
            }
            .tinTuc{
                padding-bottom: 117px;

                height: 100px;
                background-color: gainsboro;
                width: 95%;
                margin-left: 40px;
                border-radius: 15px;
                box-shadow: 0 6px 5px #c5c5c5;
            }
            .title{
                margin-left: 10px;
                padding-top: 6px;
            }
            .contentNews{
                margin-left: 10px;
            }
        </style>
    </head>

    <body>
        <% 
        String Start = (String) request.getAttribute("START");
        String End = (String) request.getAttribute("END"); 
        String listTotal = (String ) request.getAttribute("listTotal");
        String listDate = (String ) request.getAttribute("listDate");
        String totalChinhThuc = (String ) request.getAttribute("totalChinhThuc");
        String Total = (String ) request.getAttribute("TOTAL");
        String listTotalOver = (String ) request.getAttribute("listTotalOver");
        String totalOver = (String ) request.getAttribute("totalOver");
        
        if(Total == null){
           Total = "" + 0;
            }
         if(totalChinhThuc == null){
           totalChinhThuc = "" + 0;
            }
         if(totalOver == null){
           totalOver = "" + 0;
            }
         if(listDate == null){
           listDate = "" + 0;
            }
        %>
        <div class="row">
            <div class="col-md-2">
                <%@include file="Sidebar.jsp" %>
            </div>
            <div class="col-md-10 mt-3">   



                <div class="row">
                    <div >
                        <div style="background-color: #b3d8f8;
                             padding-top: 29px;padding-bottom:  19px;
                             margin-top: -16px;" class="b c outer">
                            <noscript>
                            <div class="b c" style="color:red;font-size:24px">This clock needs JavaScript</div>
                            </noscript>
                            <div class="b c circle" id="off">        
                                <div class="b n">
                                    <span class="b ib tn">7</span>
                                    <span class="b ib tn">1</span>
                                </div>
                                <div class="b n">            
                                    <span class="b ib tn">8</span>
                                    <span class="b ib tn">2</span>
                                </div>
                                <div class="b n">
                                    <span class="b ib tn">9</span>
                                    <span class="b ib tn">3</span>
                                </div>
                                <div class="b n">
                                    <span class="b ib tn">10</span>
                                    <span class="b ib tn">4</span>
                                </div>
                                <div class="b n">
                                    <span class="b ib tn">11</span>
                                    <span class="b ib tn">5</span>
                                </div>
                                <div class="b n">
                                    <span class="b ib tn">12</span>
                                    <span class="b ib tn">6</span>
                                </div>
                                <div class="b c needle short" id="hour"></div>
                                <div class="b c needle medium" id="minute"></div>
                                <div class="b c needle long" id="second">
                                    <div class="b c pivot"></div>
                                </div>
                                <div class="b c br">CLOCK</div>
                            </div>
                            <p style="font-size: large;color: aliceblue;" class="b c dn" id="note"><small>Click clock to mute.</small></p>    
                        </div>
                    </div>                              
                </div>



                <div>
                    <h1 class="mt-5 md-3 content">Hệ thống chấm công</h1>
                </div>


                <div style="padding-left:20px;margin-top: 75px;" class="row justify-content-center">

                    <div class="col-md-2 chucNang" >
                        <a style="text-decoration: auto;" href="WorkSchedule.jsp" target="target">
                            <img class="mt-3 image" src="assets/img/timetable.png" alt="alt"/>  
                            <p class="text">Lịch làm việc</p>
                            <p>Nhân viên xem lịch làm việc</p>
                        </a>
                    </div>

                    <div class="col-md-2 chucNang" >
                        <a style="text-decoration: auto;" href="TakeAttendance.jsp" target="target">
                            <img class="mt-3 image" src="assets/img/business.png" alt="alt"/>  
                            <p class="text">Chấm công</p>
                            <p>Chấm công khi vào ca làm</p>
                        </a>

                    </div>

                    <div class="col-md-2 chucNang" >
                        <a style="text-decoration: auto;" href="WorkSchedule.jsp" target="target">
                            <img class="mt-3 image" src="assets/img/overtime.png" alt="alt"/>  
                            <p class="text">Lịch tăng ca</p>
                            <p>Nhân viên xem lịch tăng ca</p>
                        </a>
                    </div>

                    <div class="col-md-2 chucNang">
                        <a style="text-decoration: auto;" href="PrepareRequestServlet" target="target">
                            <img class="mt-3 image" src="assets/img/submit.png" alt="alt"/>  
                            <p class="text">Gửi đơn</p>
                            <p>Nhân viên gửi đơn yêu cầu</p>
                        </a>
                    </div>
                </div>

                <div style="margin-top: 70px;">
                    <div style="" class="mt-4">
                        <h2 style="text-align: center;color: #498CB6;">Thống kê giờ làm</h2>   
                    </div>
                    <div style="margin-left: 30px;margin-right: 30px" class="chart-container">

                        <form action="totalTimeWork">
                            <div style="text-align: center;" class="row mt-4 md-3">
                                <div class="col-md-4 mt-2">
                                    <label for="startTime">Từ ngày</label>
                                    <input type="Date" id="startTime" name="start" value="<%=Start%>" >
                                </div>
                                <div class="col-md-4 mt-2">
                                    <label for="endTime">Đến ngày</label>
                                    <input type="Date" id="endTime" name="end" value="<%=End%>">
                                </div>
                                <div class="col-md-4">       
                                    <button type="submit" id="thongKe" class="button button2 thongKe">Thống kê</button> 
                                    <button id="exportExcelBtn" class="button button2">Export</button> 

                                </div>
                        </form>
                    </div>  
                    <div class="row" style="width: 120%;   ">
                        <div id="bieuDo" class="col-md-10 mt-1 md-3">
                            <canvas class="mt-4" id="areaChart"></canvas>
                            <input type="hidden" value="<%=listDate%>" name="ngayChinhThuc">
                            <input type="hidden" value="<%=listTotal%>" name="totalChinhThuc">
                            <input type="hidden"  value="<%=listTotalOver%>"   name="totalOver">
                        </div>   
                        <div class="col-md-2">
                            <div id="totalTime">
                                <p>Giờ làm chính thức: <span><%=totalChinhThuc%> giờ</span></p>
                                <p>Giờ làm tăng ca: <span><%=totalOver%> giờ</span></p>
                                <p>Tổng giờ làm: <span><%=Total%> giờ</span></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div> 
            <div class="md-4">
                <h4 style="color: #498CB6;padding-left: 40px">Tin tức gần đây</h4>
            </div>
            <div class="justify-content-center">
                <%
                ArrayList<NewsDTO> listNews = new NewsDAO().get5News();
                for(NewsDTO newss:listNews ){                         
                    String content = newss.getContent();
                    if (content.length() > 150) {
                        content = content.substring(0, 150) + "...";
                    }
                %>
                <div class="mt-4 tinTuc">
                    <a style="text-decoration: auto" href="/AttendanceSystem/getdetailnew?newId=<%=newss.getNewId()%>" target="target"><p class="title">Title: <%=newss.getTitle()%></p></a>
                    <p class="contentNews">Content: <%=content%></p>
                    <p class="contentNews"> Date: <%=newss.getDateTime()%> (By <%=newss.getCreateBy()%>)</p>
                </div>
                <%}%>
                <div style="text-align: center;" class="mt-4">
                    <a style="font-size: larger;" href="getallnews" target="target">Xem tất cả tin tức</a>
                </div>
                <div class="mt-4" style="height: 100px">
                     
                </div>







            </div>
        </div>

        <script>


            $(function () {
                "use strict";
                var $sec = $("#second");
                var $min = $("#minute");
                var $hour = $("#hour");
                // This is the audio in base64 URI
                // Tool for conversion: http://portraptor.johanpaul.net/2016/05/base64-audiovideo-converter.html
                var uso = "data:audio/mp3;base64,SUQzAwAAAAAfdlBSSVYAAAAOAABQZWFrVmFsdWUA/n8AAFBSSVYAAAARAABBdmVyYWdlTGV2ZWwAkicAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA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JEeBvT5xkWdEoF/asrq3xbePjW6v965r0jpZwxSOZkHJW2t6i29vr3i8FRGICBwHAAomU1lxsDsclCRRbBEKn5dj/rDx6bYcsXtkQAAkJEpEpUXGw5Xkpj49JVG0oA+1rmNesuW9ztqj03ZiIzC+eRMoUBHBYTPAjTjAo6lr5NDNE49p1aswlT1JY1u3ur2NU9lQAQAiJqpcwyK8tj86RAVbrebCRgLDJ3B/cfm4n4nu2tRuMhcjScbrLSAmS7Vsz/n9+5mW+ZLzup6sNAH0BaZpprwXyYrLmMwqm3ef8///vlmKk//95jkQCAiRKRKUFh9OwkhHwdSqgIys6KVlR6Ps1t/L88/7ld7fIrfl0cPQ4CPDm0gtB1pWt0Elu40Xpegm629lUsL2L4j35WpARAhJCq6mK2EEVaQh3av/oQK+MkEeJJNTGLbLQVosnUpFfQI/QCW7MFEjO6OlZWFIBEx0EKwynXJ0HCiMNdavkff/+pNEchJvgAKxKVTx6zLaRiM6jzFmSQsE20nGNGthMwzpfJYhJFNiOGhwwoBCpJQZCbA0eyK+RqW60teKqgJGRptpt4JNgqSWDwSshJCdoEXbNUerOLstOrM0j0mtrANgXM9MrE9mnSY8YFDYUBpq9YTSdEQGnVBVy8Kiz26JVxIGNT/VmRFLcXwECm1VKAfbihWB9DifuGNH8c4CSBomrd6+s+nexYL3Va6smFQRM1GKqFlUUsqopWqzaEs+1pw2NXGVLImrzygRItNy7LCuKKwhsJ435QX///yf53Gkz7fwKaUaAJJKSRMYGfWcDoRoWSUkU4BmPMtqEtzl7Zi+uvHxxE8hZWRIc0qGWbjG49VCk1v955fLVkHQFPK+Isi3Cgdyvhzlixv///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////qTRMOQjIAC1CZLYe9KWkSkCU0x6UgAAAEuAAAAIAAAJcAAAAT///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP/6k0R6YfqAC5AAS4AAAAgAAAlwAAABAAABLgAAACAAACXAAAAEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABUQUcAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgDA==";
                var audio;
                var ct = -1;
                function run(e) {
                    // This one is the clock value from your computer's time n date
                    function getTime() {
                        var today = new Date();
                        var h = today.getHours();
                        var m = today.getMinutes();
                        var s = today.getSeconds();
                        return [h, m, s];
                    }
                    function needleAnimation() {
                        // Init the clock display
                        if ($("#off").css("display") === "none") {
                            $("#off").fadeIn(300, function () {
                                if (!e && $("#note").css("display") === "none") {
                                    $("#note").fadeIn(300);
                                }
                            });
                        }
                        var time = getTime();
                        if (!e) { // Run with audio
                            ct += 1; // ct is the index of created "Audio" object
                            if (ct > 4) {
                                ct = 0;
                            }
                            audio[ct].play();
                        }
                        var s_value = time[2] * 6 + 90;
                        var m_value = time[1] * 6 + 90;
                        var h_value = (time[0] % 12) * 30 + 90 + (time[1] / 60) * 30;
                        var secS = ((Math.cos(s_value / 180 * Math.PI)) * 3) + "px " + ((Math.sin(s_value / 180 * Math.PI)) * 3) + "px 3px rgba(0, 0, 0, 0.25)";
                        var minS = ((Math.cos(m_value / 180 * Math.PI)) * 2) + "px " + ((Math.sin(m_value / 180 * Math.PI)) * 2) + "px 2px rgba(0, 0, 0, 0.8)";
                        // This is for the needles rotation
                        $sec.css({transform: "rotate(" + s_value + "deg)", "box-shadow": secS});
                        $min.css({transform: "rotate(" + m_value + "deg)", "box-shadow": minS});
                        $hour.css({transform: "rotate(" + h_value + "deg)"});
                    }
                    // 1000 ms = 1 s interval period
                    setInterval(needleAnimation, 1000);
                }
                // For mute and changing the color
                function clickOff() {
                    function turnOn(v) {
                        v.volume = 0.2;
                    }
                    function turnOff(v) {
                        v.volume = 0;
                    }
                    if (audio[0].volume === 0) {
                        audio.forEach(turnOn);
                        $("#off").css({
                            "background-image": "url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiIHZpZXdCb3g9IjAgMCAxIDEiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPgo8cmFkaWFsR3JhZGllbnQgaWQ9ImczNzMiIGdyYWRpZW50VW5pdHM9InVzZXJTcGFjZU9uVXNlIiBjeD0iNTAlIiBjeT0iNTAlIiByPSIyMDAlIj4KPHN0b3Agc3RvcC1jb2xvcj0iI0ZGRkZGRiIgb2Zmc2V0PSIwIi8+PHN0b3Agc3RvcC1jb2xvcj0iIzg4OCIgb2Zmc2V0PSIxIi8+CjwvcmFkaWFsR3JhZGllbnQ+CjxyZWN0IHg9Ii01MCIgeT0iLTUwIiB3aWR0aD0iMTAxIiBoZWlnaHQ9IjEwMSIgZmlsbD0idXJsKCNnMzczKSIgLz4KPC9zdmc+)",
                            "background-color": "#ddd"
                        });
                        $("#note").html("<small>Click clock to mute.</small>");
                        $(".tn").css("color", "#888");
                    } else {
                        audio.forEach(turnOff);
                        $("#off").css({
                            "background-image": "url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiIHZpZXdCb3g9IjAgMCAxIDEiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPgo8cmFkaWFsR3JhZGllbnQgaWQ9Imc1MTUiIGdyYWRpZW50VW5pdHM9InVzZXJTcGFjZU9uVXNlIiBjeD0iNTAlIiBjeT0iNTAlIiByPSI1MCUiPgo8c3RvcCBzdG9wLWNvbG9yPSIjRkZGRkZGIiBvZmZzZXQ9IjAiLz48c3RvcCBzdG9wLWNvbG9yPSIjODdjZWZhIiBvZmZzZXQ9IjEiLz4KPC9yYWRpYWxHcmFkaWVudD4KPHJlY3QgeD0iLTUwIiB5PSItNTAiIHdpZHRoPSIxMDEiIGhlaWdodD0iMTAxIiBmaWxsPSJ1cmwoI2c1MTUpIiAvPgo8L3N2Zz4=)",
                            "background-color": "lightskyblue"
                        });
                        $("#note").html("<small style='color:brown;letter-spacing:1px;font-weight:bold;'>MUTED</small>");
                        $(".tn").css("color", "#555");
                    }
                }
                /*
                 ABOUT REPEATEDLY PLAYING AUDIO
                 ---------------------------
                 A. Using audio file:
                 ---------------------
                 1. The trick to play over and over the same (short) audio is to create more than 1 "Audio" object. Then repeat the play() invocation for each different object.
                 2. Or use 1 object, attach a listener (ended event), then chain that to your other trigger if the app something stuff nanana. This one is less efficient. You'll have no "buffer".
                 ---------------------
                 B. Using web audio API:
                 ---------------------
                 Synthesize it using web audio API. That takes superposition of multifrequency, phase and oscillation types and maybe filter(s) to mimic the ticking sound. Boy that's something.
                 */
                (function () {
                    var i = 0;
                    if (Audio !== undefined) {
                        audio = new Audio(uso);
                        if (audio.canPlayType("audio/mp3") !== "") {
                            $("#off").click(clickOff); // Muting trigger
                            audio = [];
                            // Here, I create 5 "Audio" objects
                            while (i < 5) {
                                audio.push(new Audio(uso));
                                audio[i].volume = 0.2;
                                i += 1;
                            }
                            run(0); // Run with audio
                        } else {
                            run(1); // Run without audio
                        }
                    } else {
                        run(1); // Run without audio
                    }
                }());
            });

            document.addEventListener('DOMContentLoaded', function () {
                console.log(1);
                var ctx = document.getElementById('areaChart').getContext('2d');
                var listDate = document.querySelector('input[name="ngayChinhThuc"]').value.split(",");
                var listTotal = document.querySelector('input[name="totalChinhThuc"]').value.split(",");
                var listTotalOver = document.querySelector('input[name="totalOver"]').value.split(",");

                // Sample data
                var data = {
                    labels: listDate,
                    datasets: [
                        {
                            label: 'Số giờ làm việc',
                            data: listTotal.map(Number), // Chuyển đổi từ chuỗi thành số
                        },
                        {
                            label: 'Số giờ làm tăng ca',
                            data: listTotalOver.map(Number), // Để tạm thời rỗng, bạn có thể cung cấp dữ liệu sau
                        }
                    ]
                };

                var options = {
                    scales: {
                        yAxes: [{
                                ticks: {
                                    beginAtZero: true
                                }
                            }]
                    },
                    tooltips: {
                        mode: 'index',
                        intersect: false,
                        callbacks: {
                            label: function (tooltipItem, data) {
                                var datasetLabel = data.datasets[tooltipItem.datasetIndex].label || '';
                                return datasetLabel + ': ' + tooltipItem.yLabel + ' giờ';
                            }
                        }
                    }
                };

                var areaChart = new Chart(ctx, {
                    type: 'line',
                    data: data,
                    options: options
                });

                // Export data to Excel
                document.getElementById('exportExcelBtn').addEventListener('click', function () {
                    var wb = XLSX.utils.book_new();
                    wb.SheetNames.push('Chart Data');

                    // Extracting data from the chart and calculating the sum
                    var labels = data.labels;
                    var dataset1 = data.datasets[0].data;
                    var dataset2 = data.datasets[1].data;
                    var sumData = labels.map(function (label, index) {
                        return [label, dataset1[index], dataset2[index], dataset1[index] + dataset2[index]];
                    });

                    // Calculate the sum of all columns
                    var totalRow = ['Total'];
                    for (var i = 1; i < sumData[0].length; i++) {
                        var sum = sumData.reduce(function (total, current) {
                            return total + current[i];
                        }, 0);
                        totalRow.push(sum);
                    }

                    sumData.push(totalRow);

                    sumData.unshift(['Date', 'Work Hours', 'Overtime Hours', 'Total Hours']); // Adding the header row

                    var ws = XLSX.utils.aoa_to_sheet(sumData);
                    wb.Sheets['Chart Data'] = ws;

                    // Generate and save the Excel file
                    var wbout = XLSX.write(wb, {bookType: 'xlsx', type: 'array'});
                    saveAs(new Blob([wbout], {type: 'application/octet-stream'}), 'chart_data.xlsx');
                });
            });

        </script>

    </body>
</html>
