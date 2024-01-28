<%-- 
    Document   : viewEmployeesByManager
    Created on : Jan 19, 2024, 10:33:09 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.EmployeeDTO"%>
<%@page import="model.ShiftDTO"%>
<!DOCTYPE html>
<html>

    <head>
        <title>TODO supply a title</title>
        <meta charset="utf-9">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link rel="stylesheet" href="styleTest.css">
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> 
        <style>
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
                height: 3.5rem;
                width: 3.5rem;
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
            .badge-soft-Notsuccess {
                color: #ce0f0f !important;
                background-color: rgb(255 52 18 / 10%);
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

            .scrollable-content {
                max-height: 80%; /* Đặt chiều cao tối đa cho nội dung có thể cuộn */
                overflow: auto; /* Kích hoạt thanh cuộn nếu nội dung vượt quá chiều cao tối đa */
            }
            .img-radius{
                width: 5em;
                height: 5em;
            }
            .card-block{
                padding: 2px;
                background-color: #73bbfad4;
                border-radius:8px 0px 0px 8px;
                height: 100%;
            }

            .info{
                border-bottom: 1px solid #e0e0e0;
            }
            .m-b-10 {
                margin-bottom: 10px;
            }
            .f-w-600     {
                font-weight: 600;
            }
            .bg-c-lite-green {
                background: -webkit-gradient(linear, left top, right top, from(#f29263), to(#ee5a6f));
                background: linear-gradient(to right, #ee5a6f, #f29263);
            }
            .text-wrap {
                word-wrap: break-word;
            }
        </style>
    </head>

    <body>
        <%
          String phong = (String) request.getAttribute("PHONG");
        %>
        <input id="phongBan" type="hidden" value="<%=phong%>" name="name">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <h6 style ="    font-size: larger;
                        <%String tenPhong = (String) request.getAttribute("TENPHONG");%>
                        font-family: system-ui;" class="fs-16 mb-0"><%=tenPhong%></h6>
                </div>
                <div class="col-md-4 filler-job-form">
                    <i class="uil uil-briefcase-alt"></i><input  oninput="searchByName(this)" name="txtSearch" id="txtSearch" placeholder="Employee's name" type="text" class="form-control filler-job-input-box form-control" />
                </div>
                <div class="col-md-2" style="display:flex">
                    <div class="col-md-4">
                        <label style="padding-top: 7px;" for="form-select">Ca làm</label>
                    </div>
                    <div class="col-md-8">
                        <select onchange="searchByName(this)" id="form-select" class="form-select" aria-label="Default select example">
                            <option value="0" selected>Tất cả</option>
                            <%
                                               ArrayList<ShiftDTO> listhift =(ArrayList<ShiftDTO>) request.getAttribute("LISTSHIFT");
                                               for (ShiftDTO shift: listhift){
                            %>
                            <option value=<%=shift.getShiftID()%>><%=shift.getName()%></option>
                            <%}%>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="">
                        <div class="table-responsive" id="listEmployee">



                            <table class="table project-list-table table-nowrap align-middle table-borderless">
                                <thead>                               
                                <th scope="col">Name</th>
                                <th scope="col">Position</th>
                                <th scope="col">Email</th>
                                <th scope="col">Phone number</th>
                                <th scope="col" style="width: 200px;">Active</th>
                                </tr>
                                </thead>
                                <tbody id="listEmployee">
                                    <%
                                       String position = (String) request.getAttribute("POSITION");
                                       ArrayList<EmployeeDTO> list = (ArrayList<EmployeeDTO>)request.getAttribute("LIST");
                                       if(list!=null)
                                       for(EmployeeDTO employee :list){


                                    %>                               
                                    <tr>
                                <input id="phongBan" type="hidden" value="<%=employee.getDepartmentID()%>" name="name">
                                <td><img src="https://t4.ftcdn.net/jpg/03/49/49/79/360_F_349497933_Ly4im8BDmHLaLzgyKg2f2yZOvJjBtlw5.jpg" alt=""
                                         class="avatar-sm rounded-circle me-2" /><a href="#" class="text-body show-div-link"> 
                                        <%=employee.getLastName()+" "+employee.getMiddleName()+" "+employee.getFirstName()%></a>
                                    <!--div class "overlay"-> box an khii an vao ten trong list se hien thong tin -->                       
                                    <div class="overlay" style="z-index:2000">
                                        <div class="centered-div" >
                                            <button class="close-button"><img  style="width: 10px;" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSkBKbhGamdqx3Bab4sQqUwkljoYvQ-WasvA&usqp=CAU" alt=""></button>
                                            <div class="row">
                                                <div class="col-md-4 ">

                                                    <div class="text-center card-block">
                                                        <div class="mb-4 mt-4" >
                                                            <img src="https://bootdey.com/img/Content/avatar/avatar1.png" class="img-radius rounded-circle" alt="User-Profile-Image">
                                                        </div>
                                                        <div class="mt-3 text-wrap">
                                                            <%=employee.getLastName()+" "+employee.getMiddleName()+" "+employee.getFirstName()%>
                                                        </div>
                                                        <p><%=position%></p>
                                                        ID
                                                        <p><%=employee.getCccd()%></p>
                                                    </div>

                                                </div>

                                                <div class="col-md-8 card-info mt-3" style="padding-left: 20px;">
                                                    <p style="width: 400px;"></p>
                                                    <p class="info">Information</p>
                                                    <div class="row mb-3">
                                                        <div class="col-sm-6 text-wrap">
                                                            <p  class="m-b-10 f-w-600 ">Email</p>
                                                            <div class="text-muted text-wrap"><%=employee.getEmail()%></div> 
                                                        </div>
                                                        <div class="col-sm-6 text-wrap">
                                                            <p  class="m-b-10 f-w-600">Phone</p>
                                                            <h6  class="text-muted text-wrap"><%=employee.getPhoneNumber()%></h6>
                                                        </div>
                                                    </div>

                                                    <div class="row mb-3">
                                                        <div class="col-sm-6 text-wrap">
                                                            <p  class="m-b-10 f-w-600 ">Birth</p>
                                                            <div class="text-muted text-wrap"><%=employee.getStartDate()%></div> 
                                                        </div>
                                                        <div class="col-sm-6 text-wrap">
                                                            <p  class="m-b-10 f-w-600">Gender</p>
                                                            <h6  class="text-muted text-wrap"><%=employee.getGender()%></h6>
                                                        </div>
                                                    </div>

                                                    <div class="row mb-3">
                                                        <div class="col-sm-6 text-wrap">
                                                            <p  class="m-b-10 f-w-600 ">Start date</p>
                                                            <div class="text-muted text-wrap"><%=employee.getStartDate()%></div> 
                                                        </div>
                                                        <div class="col-sm-6 text-wrap">
                                                            <p  class="m-b-10 f-w-600">End date</p>
                                                            <h6  class="text-muted text-wrap"><%=employee.getEndDate()%></h6>
                                                        </div>
                                                    </div>

                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td><span class="badge badge-soft-success mb-0"><%=position%></span></td>
                                <td><%=employee.getEmail()%></td>
                                <td><%=employee.getPhoneNumber()%></td>
                                <%
                                   if(employee.isIsActived()){
                                %>
                                <td><span class="badge badge-soft-success mb-0">Active</span></td>
                                <%} else{%>
                                <td><span class="badge badge-soft-Notsuccess mb-0">Inactive</span></td>
                                <%}%>
                                </tr>

                                <%}%>
                                </tbody>
                            </table>
                            <div clas="row">

                                <div class="text-center container" >
                                    <ul class="pagination" style="
                                        justify-content: end;
                                        ">
                                        <li class="page-item"><a class="page-link" href="#">Trước</a></li>
                                            <%
                                               int endPage = (int) request.getAttribute("ENDPAGE");
                                               if(endPage>0){
                                               for(int i=1; i<=endPage;i++){
                                                   if(i==1){ 
                                            %>

                                        <li class="page-item"><a style="background-color: #cfd5da96;" class="page-link page" data-index="<%=i%>" onclick="searchByName(this)" href="#"><%=i%></a><li>
                                            <%}else{%>
                                        <li class="page-item"><a  class="page-link page" data-index="<%=i%>" onclick="searchByName(this)" href="#"><%=i%></a><li>
                                            <%}}}%>
                                        <li class="page-item"><a data-index="<%=2%>" onclick="searchByName(this)" class="page-link" href="#">Sau</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <script>

            var showDivLinks = document.querySelectorAll(".show-div-link");
            // khi an vao link co ten class show-div-link se hien ra box an
            showDivLinks.forEach(function (link) {
                link.addEventListener("click", function (e) {
                    e.preventDefault();//an vao link ko chuyen huong toi dia chi khac
                    var overlay = this.nextElementSibling;
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
                var txtSearch = $("#txtSearch").val();
                var phongBan = $("#phongBan").val();
                var Page = param.dataset.index;
                var Ca = $("#form-select").val();
                $.ajax({
                    url: "/AttendanceSystem/searchEmployeeByAjax",
                    type: "get",
                    data: {
                        txt: txtSearch,
                        phong: phongBan,
                        Page: Page,
                        CaLam: Ca
                    },
                    success: function (data) {
                        var row = $("#listEmployee");
                        row.html(data);

                        // Gọi lại sự kiện hoặc hàm JS bạn muốn chạy vi khi dung ajax lay du lieu tu serverlet se mat ket noi
                        initializeYourFunctions();
                    },
                    error: function (xhr) {
                        console.log("Error:", xhr);
                    }
                });
            }

            function initializeYourFunctions() {
                showDivLinks.forEach(function (link) {
                    link.addEventListener("click", function (e) {
                        e.preventDefault();//an vao link ko chuyen huong toi dia chi khac
                        var overlay = this.nextElementSibling;
                        overlay.style.display = "block";

                        var closeButton = overlay.querySelector(".close-button");
                        closeButton.addEventListener("click", function () {
                            overlay.style.display = "none";
                        });
                    });
                });

                function searchByName(param) {
                    var phongBan = $("#phongBan").val();
                    var txtSearch = $("#txtSearch").val();
                    var Page = param.dataset.index;
                    var Ca = $("#form-select").val();
                    $.ajax({
                        url: "/AttendanceSystem/searchEmployeeByAjax",
                        type: "get",
                        data: {
                            txt: txtSearch,
                            phong: phongBan,
                            Page: Page,
                            CaLam: Ca
                        },
                        success: function (data) {
                            var row = $("#listEmployee");
                            row.html(data);

                            // Gọi lại sự kiện hoặc hàm JS bạn muốn chạy vi khi dung ajax lay du lieu tu serverlet se mat ket noi
                            initializeYourFunctions();
                        },
                        error: function (xhr) {
                            console.log("Error:", xhr);
                        }
                    });
                }

            }

        </script>                            
    </body>

</html> 
