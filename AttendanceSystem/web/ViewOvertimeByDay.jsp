<%-- 
    Document   : ViewOvertimeByDay
    Created on : Feb 21, 2024, 9:14:24 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.OvertimeDTO"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.OvertimeDAO" %>
<%@page import="model.EmployeeDAO" %>
<%@page import="model.EmployeeDTO" %>
<%@page import="model.DepartmentDAO" %>
<%@page import="model.DepartmentDTO" %>
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
            body{
                margin-top:20px;
                background:rgba(241,246,253);
            }
            .rounded-circle {
                border-radius: 50%!important;
            }
            .border-5 {
                border-width: 5px;
            }

            .border-white {
                border-opacity: 1;
                border-color: rgba(255,255,255, 1) !important;
            }
            .rounded-circle {
                border-radius: 50%!important;
            }

            .hover-top-in .hover-top--in {
                transition: transform 0.35s ease !important;

            }

            .hover-top-in:hover .hover-top--in {
                transform: translateY(-10px); /* Di chuyển lên 10px khi hover */
            }

            .me-1 {
                margin-right: 0.25rem!important;
            }

            .fw-700 {
                font-weight: 700!important;
            }
            .mb-1 {
                margin-bottom: 0.25rem!important;
            }

            .z-index-1 {
                z-index: 1!important;
            }

            .pt-6 {
                padding-top: 2.5rem!important;
            }
            .p-4 {
                padding: 1.5rem!important;
            }
            .mt-n4 {
                margin-top: -1.5rem!important;
            }

            .shadow {
                box-shadow: 0 0.375rem 1.5rem 0 rgba(var(--bs-gray-700-rgb),.125)!important;
            }
            .px-5 {
                padding-right: 2rem!important;
                padding-left: 2rem!important;
            }
            .position-relative {
                position: relative!important;
            }
            .overflow-hidden {
                overflow: hidden!important;
            }
            .linkOvertime{
                text-decoration: auto;
                color : black
            }
            .overlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.7);
                z-index: 1;
            }

            .centered-div {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: #fff;
                padding: 20px;
                border: 1px solid #ccc;
                padding: 0px;
                border-radius: 10px;
            }

            .close-button {
                position: absolute;
                top: -10px;
                right: -10px;
                cursor: pointer;
            }
            body{
                margin-top:20px;
                background-color:#eee;
            }
            .project-list-table {
                border-collapse: separate;
                border-spacing: 0 12px
            }

            .project-list-table tr {
                background-color: #fff
            }

            .table-nowrap td, .table-nowrap th {
                white-space: nowrap;
            }
            .table-borderless>:not(caption)>*>* {
                border-bottom-width: 0;
            }
            .table>:not(caption)>*>* {
                padding: 0.75rem 0.75rem;
                background-color: var(--bs-table-bg);
                border-bottom-width: 1px;
                box-shadow: inset 0 0 0 9999px var(--bs-table-accent-bg);
            }

            .avatar-sm {
                height: 2rem;
                width: 2rem;
            }
            .rounded-circle {
                border-radius: 50%!important;
            }
            .me-2 {
                margin-right: 0.5rem!important;
            }
            img, svg {
                vertical-align: middle;
            }

            a {
                color: #3b76e1;
                text-decoration: none;
            }

            .badge-soft-danger {
                color: #f56e6e !important;
                background-color: rgba(245,110,110,.1);
            }
            .badge-soft-success {
                color: #63ad6f !important;
                background-color: rgba(99,173,111,.1);
            }

            .badge-soft-primary {
                color: #3b76e1 !important;
                background-color: rgba(59,118,225,.1);
            }

            .badge-soft-info {
                color: #57c9eb !important;
                background-color: rgba(87,201,235,.1);
            }

            .avatar-title {
                align-items: center;
                background-color: #3b76e1;
                color: #fff;
                display: flex;
                font-weight: 500;
                height: 100%;
                justify-content: center;
                width: 100%;
            }
            .bg-soft-primary {
                background-color: rgba(59,118,225,.25)!important;
            }
            .KoCa{
                margin-bottom: 10em;
                margin-top: 10em;
                text-align: center;
            }
            .textKoCa{
                font-weight: 630;
                color: #b1b0afe6;
            }
        </style>
    </head>
    <body>
        <%
         String error = (String) request.getAttribute("ERROROVERTIME");
         if(error!=null&&error.length()>0){
        %>
        <script>
            var result = window.confirm("Ca đã tồn tại vui lòng chọn lại");
        </script>

        <%}%>

        <%
         String error2 = (String) request.getAttribute("ERRORSTARTTIME");
         if(error2!=null&&error2.length()>0){
        %>
        <script>
            var result2 = window.confirm("StarTtime phải nhỏ hơn EndTime vui lòng nhập lại ");
        </script>

        <%}%>
        <%
           String Day = (String) request.getAttribute("DAY");
           ArrayList<OvertimeDTO> list = (ArrayList<OvertimeDTO>) request.getAttribute("LISTOVERTIME"); 
        %>
        <input type="hidden" id="date" value="<%=Day%>">
        <section id="team" class="section bg-gray-100">
            <div class="container">
                <div class="row section-heading justify-content-center text-center wow fadeInUp" data-wow-duration="0.3s" data-wow-delay="0.3s" style="visibility: visible; animation-duration: 0.3s; animation-delay: 0.3s; animation-name: fadeInUp;">
                    <div class="col-lg-8 col-xl-6">
                        <a href="javascript:history.back()" class="btn btn-outline-secondary" style="    width: 106px;position: absolute; left: 15px; top: 15px;">
                            <i class="bi bi-arrow-left"></i> Trở lại
                        </a>
                        <h3 class="h2 bg-primary-after after-50px pb-3 mb-3">Các ca tăng ca trong ngày <%=Day%></h3>
                    </div>
                </div>
                <div class="row">
                    <%
                         ArrayList<EmployeeDTO> listEmployee = new ArrayList();
                        if(list.size()>0){                                              
                        for(OvertimeDTO overtime : list){
                       listEmployee = new EmployeeDAO().getEmployeeInfoByOvertime(Day,overtime.getStartTime().toString(),overtime.getEndTime().toString());
                    %>

                    <div class="col-lg-3 col-sm-6 my-3 wow fadeInUp" data-wow-duration="0.3s" data-wow-delay="0.3s" style="visibility: visible; animation-duration: 0.3s; animation-delay: 0.3s; animation-name: fadeInUp;">
                        <div class="hover-top-in text-center">

                            <a class="linkOvertime" href="#">
                                <div style="padding-top: 23px" class="overflow-hidden z-index-1 position-relative px-5 hover-top--in mt-3 mb-3">
                                    <div style="border-radius: 12px;" class="mx-2 mx-xl-3 shadow mt-n4 bg-white p-4 pt-6 mx-4 text-center ">
                                        <h6 class="fw-700 dark-color mb-1"><%=overtime.getStartTime()%> - <%=overtime.getEndTime()%></h6><small>Số nhân viên tăng ca: <%=listEmployee.size()%></small>
                                    </div>                      
                                </div>
                            </a>
                            <div class="overlay" style="z-index:2000">
                                <div class="centered-div" >
                                    <button class="close-button"><img  style="width: 10px;" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSkBKbhGamdqx3Bab4sQqUwkljoYvQ-WasvA&usqp=CAU" alt=""></button>
                                    <div class="container">
                                        <div class="container row align-items-center">
                                            <div class="col-md-6">
                                                <div class="mt-4">
                                                    <div class="input-group rounded">
                                                        <input oninput="searchByName(this)"  type="search" class="form-control rounded txtSearch" placeholder="Search" />

                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-2"></div>
                                            <div style="width: 240px;" class="col-md-4">
                                                <div class="mt-4">
                                                    <select onchange="searchByName(this)" id="form-select" class="form-select" >
                                                        <%
                                                          ArrayList<DepartmentDTO> listDepart = new DepartmentDAO().getListDepartment();
                                                          for(DepartmentDTO depart:listDepart ){
                                                        %>
                                                        <option value="3">Three</option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="">
                                                    <div class="table-responsive">
                                                        <table class="table project-list-table table-nowrap align-middle table-borderless">
                                                            <thead>
                                                                <tr>

                                                                    <th scope="col">Name</th>
                                                                    <th scope="col">Position</th>
                                                                    <th scope="col">Email</th>
                                                                    <th scope="col">Employee ID</th>
                                                                    <th scope="col" style="width: 200px;">Action</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody class = "listEmployee">
                                                                <%
                                                                   DepartmentDTO demp = new DepartmentDTO();
                                                                   for(EmployeeDTO listemp : listEmployee){
                                                                    
                                                                    demp = new DepartmentDAO().getDepartmentById(listemp.getDepartmentID());
                                                                %>
                                                                <tr>
                                                                    <td><img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="" class="avatar-sm rounded-circle me-2" /><a href="#" class="text-body"><%=listemp.getLastName()%> <%=listemp.getMiddleName()%> <%=listemp.getFirstName()%></a></td>
                                                                    <td><span class="badge badge-soft-success mb-0"><%=demp.getName() %></span></td>
                                                                    <td><%=listemp.getEmail()%></td>
                                                                    <td><%=listemp.getEmployeeId()%></td>
                                                                    <td>
                                                                        <ul class="list-inline mb-0">

                                                                            <li class="list-inline-item">
                                                                                <a href="javascript:void(0);"  title="Delete" class="px-2 text-danger"><i class="bx bx-trash-alt font-size-18"></i></a>
                                                                            </li>

                                                                        </ul>
                                                                    </td>
                                                                </tr>
                                                                <%}%>


                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row g-0 align-items-center pb-4">
                                            <div class="col-sm-6">
                                            </div>
                                            <div class="col-sm-6">
                                                <div class="float-sm-end">
                                                    <ul class="pagination mb-sm-0">
                                                        <li class="page-item disabled">
                                                            <a href="#" class="page-link"><i class="mdi mdi-chevron-left"></i></a>
                                                        </li>
                                                        <li class="page-item active"><a onclick="searchByName(this)" href="#" class="page-link">1</a></li>

                                                        <li class="page-item">
                                                            <a href="#" class="page-link"><i class="mdi mdi-chevron-right"></i></a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="d-flex flex-wrap align-items-center justify-content-center gap-2 mb-3">

                                            <div>

                                                <a href="addEmployeeOvertime?startTime=<%=overtime.getStartTime()%>&&endTime=<%=overtime.getEndTime()%>&&date=<%=overtime.getDate()%>&&Add=add" class="btn btn-primary"><i class="bx bx-plus me-1"></i> Thêm nhân viên</a>

                                            </div>

                                        </div>

                                    </div>
                                </div>
                            </div>
                            <input type="hidden" class="startEnd" value="<%=overtime.getStartTime()%>-<%=overtime.getEndTime()%>">
                        </div>
                    </div>


                    <%}}else{%>
                    <div class="KoCa">
                        <h2 class="textKoCa">Chưa có ca nào</h2>
                    </div>

                    <%}%>
                    <div style="text-align: center;"  class="container mt-3">

                        <button style="background-color: #000000c9;
                                border: 0px;
                                width: 152px;
                                height: 47px;    font-size: larger;
                                font-weight: 400;" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal">
                            Thêm ca
                        </button>
                    </div>
                    <form action="addEmployeeOvertime">
                        <input type="hidden" name="date" value="<%=Day%>">
                        <!-- The Modal -->
                        <div class="modal" id="myModal">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">

                                    <!-- Modal Header -->
                                    <div class="modal-header">
                                        <h4 class="modal-title">Chọn thời gian tăng ca</h4>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>

                                    <!-- Modal body -->
                                    <div class="modal-body row">
                                        <div class="col-sm-6 mt-3">
                                            <label for="startTime">Start time</label>
                                            <input type="time" id="startTime" name="startTime"  required >
                                        </div>

                                        <div class="col-sm-6 mt-3">
                                            <label for="endTime">End time</label>
                                            <input type="time" id="endTime" name="endTime" required >
                                        </div>
                                    </div>

                                    <!-- Modal footer -->
                                    <div class="modal-footer mt-2" style="">
                                        <form action="addEmployeeOvertime">

                                            <button type="submit" style="    background: #000000d1;height: 39px;margin-top: 10px;
                                                    border: 0px;" type="button" class="btn btn-danger" data-bs-dismiss="modal">Chọn nhân viên</button>

                                    </div>

                                </div>
                            </div>
                        </div>
                    </form>


                </div>
                </a>
            </div>

        </div>
    </div>

</section>
<script>

    var startEnd;
    var showDivLinks = document.querySelectorAll(".linkOvertime");
// khi an vao link co ten class show-div-link se hien ra box an
    showDivLinks.forEach(function (link) {
        link.addEventListener("click", function (e) {

            var overlay = this.nextElementSibling;
            startEnd = overlay.nextElementSibling.value;
            console.log(startEnd);
            overlay.style.display = "block";

            var closeButton = overlay.querySelector(".close-button");
            closeButton.addEventListener("click", function () {
                overlay.style.display = "none";
            });
        });
    });



    $(document).on("click", ".show-div-link", function (e) {
        e.preventDefault();
        var overlay = $(this).next(".overlay");
        overlay.css("display", "block");

        var closeButton = overlay.find(".close-button");
        closeButton.on("click", function () {
            overlay.css("display", "none");
        });
    });

    function searchByName(param) {
        var txtSearch = $(".txtSearch").val();
        var phongBan = $("#phongBan").val();
        var Date = $("#date").val();
        $.ajax({
            url: "/AttendanceSystem/listEmployeeOvertimeAjax",
            type: "get",
            data: {
                txt: txtSearch,
                phong: phongBan,
                date: Date,
                StartEnd: startEnd
            },
            success: function (data) {
                var row = $(".listEmployee");
                row.html(data);

                // Gọi lại sự kiện hoặc hàm JS bạn muốn chạy vi khi dung ajax lay du lieu tu serverlet se mat ket noi
                //initializeYourFunctions();
            },
            error: function (xhr) {
                console.log("Error:", xhr);
            }
        });
    }
</script>              
</body>
</html>
