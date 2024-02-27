<%-- 
    Document   : AddListEmployeeOvertime
    Created on : Feb 22, 2024, 12:11:27 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.EmployeeDAO" %>
<%@page import="model.EmployeeDTO" %>
<%@page import="model.DepartmentDAO" %>
<%@page import="model.DepartmentDTO" %>
<%@page import="model.EmployeeTypeDAO" %>
<%@page import="model.EmployeeTypeDTO" %>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Bootstrap Example</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> 
        <style>

        </style>
    </head>
    <body>
        <% String Date = (String) request.getAttribute("DAY");
        String startTime = (String) request.getAttribute("STARTTIME");
        String endTime = (String) request.getAttribute("ENDTIME");
        %>
<input type="hidden" id="Date" value="<%=Date%>">
        <div class="container">
            <div class="row">


                <div class="col-md-12">

                    <div class="text-center pt-3" style="font-family: sans-serif; font-weight: 900">
                        <h2>Danh sách nhân viên</h2>
                        <a href="javascript:history.back()" class="btn btn-outline-secondary" style="position: absolute; left: 15px; top: 15px;">
                            <i class="bi bi-arrow-left"></i> Trở lại
                        </a>

                        <a onclick="prepareAndSubmit()" href="#" class="btn btn-outline-secondary" style="position: absolute; right: 15px; top: 15px;">
                            <i class="bi bi-arrow-left"></i> Tiếp theo 
                        </a>
                        
                        
                    </div>
                    <div class="d-flex justify-content-around  py-3 align-middle">
                        <table class="table table-primary">
                            <tr>
                                <th>Tìm kiếm</th>
                                <th>Phòng ban</th>
                                <th>Loại nhân viên</th>

                            </tr>
                            <tr>
                                <td>
                                    <input oninput="searchByName(this)" id="txtSearch" type="text" class="form-input form-control" id="input-search">
                                </td>
                                <td>
                                    <%
                                      ArrayList<DepartmentDTO> listDepart = new DepartmentDAO().getListDepartment();
                                  
                                    %>
                                    <select id="phongBan" onchange="searchByName(this)" class="form-select" id="select-department">
                                        <option value="" selected="">Tất cả</option>
                                        <%
                                         for(DepartmentDTO listdepart :listDepart){
                                        %>
                                        <option value="<%=listdepart.getDepartmentID()%>" ><%=listdepart.getName()%></option>
                                        <%}%>
                                    </select>   
                                </td>
                                <%
                                      ArrayList<EmployeeTypeDTO> listEmpType = new EmployeeTypeDAO().getEmployeeTypeList();
                                  
                                %>
                                <td>
                                    <select id="empID" onchange="searchByName(this)" class="form-select" id="select-employeeType">
                                        <option value="" selected="">Tất cả</option>
                                        <%
                                         for(EmployeeTypeDTO listempType :listEmpType){
                                        %>
                                        <option value="<%=listempType.getEmployeeTypeId()%>" ><%=listempType.getName()%></option>
                                        <%}%>
                                    </select>   
                                </td>

                            </tr>
                        </table>

                    </div>
                    <div id="listEmployee" class="table-responsive">
                    <form action="addListEmloyeeOvertime" id="MyForm">
                            <input type="hidden" id="dateHidden" name="Date" value="<%=Date%>">
                            <input type="hidden" id="startHidden" name="start" value="<%=startTime%>">
                            <input type="hidden" id="endHidden" name="end" value="<%=endTime%>">
                            <input type="hidden" id="listEmployeeAdd"  name="listEmployeeAdd">
                            
                       </form>

                        <table id="mytable" class="table table-bordred table-striped">

                            <thead>

                            <th><input  type="checkbox" id="checkall" /></th>
                            <th>EmployeeID</th>
                            <th>Full name</th>   
                            <th>CCCD</th>                   
                            <th>Email</th>
                            <th>Employee Type</th>
                            <th>Department</th>

                            </thead>
                            <tbody >
                                <%
                                   
                                  String EndPage = (String) request.getAttribute("COUNTPAGE");
                                  int endPage = Integer.parseInt(EndPage);
                                  ArrayList<EmployeeDTO> list =(ArrayList<EmployeeDTO>) request.getAttribute("LIST");
                                  if(list.size()>0){
                                  DepartmentDTO departEmp = new DepartmentDTO();
                                   int count =0;
                                   String TypeName="";
                                  for(EmployeeDTO emp :list){
                                  departEmp = new DepartmentDAO().getDepartmentById(emp.getDepartmentID());
                                  TypeName = new EmployeeTypeDAO().getEmployeeTypeIDByID(emp.getEmployeeTypeID());
                                %>
                           
                                <tr>
                                    <td><input  type="checkbox" class="checkthis" /></td>
                                    <td><%=emp.getEmployeeId()%></td>
                                    <td><%=emp.getLastName()%> <%=emp.getMiddleName()%> <%=emp.getFirstName()%></td>
                                    <td><%=emp.getCccd()%></td>
                                    <td><%=emp.getEmail()%></td>
                                    <td><%=TypeName%></td>
                                    <td><%=departEmp.getName()%></td>
                                </tr>
                                <%
                                    count++;
                                    if(count==10)
                                    break;
                                    }}%>
                               
                            </tbody>

                        </table>
                                <div class="text-center container" >
            <ul class="pagination" style="
                justify-content: end;
                ">
                <li class="page-item"><a class="page-link" href="#">Trước</a></li>

                <%
                 for(int i=1;i<=endPage;i++){
                                         
                 if(i==1){
                %>
                <li class="page-item"><a style="background-color: #cfd5da96;" class="page-link page pageNow" data-index="<%=i%>" onclick="searchByName(this)" href="#"><%=i%></a><li>
                    <%}else{%>

                <li class="page-item"><a  class="page-link page" data-index="<%=i%>" onclick="searchByName(this)" href="#"><%=i%></a><li>

                    <%}}if(endPage>1){%>       
                <li class="page-item"><a data-index="<%=2%>" onclick="searchByName(this)" class="page-link" href="#">Sau</a></li>
<%}else{%>
                <li class="page-item"><a data-index="<%=1%>" onclick="searchByName(this)" class="page-link" href="#">Sau</a></li>
                <%}%>
            </ul>
        </div>
                    </div>
 
                </div>
            </div>
        </div>
 
        


        <script>
            
            function areAllCheckboxesChecked() {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (!checkboxes[i].checked) {
            return false;
        }
    }
    return true;
}

            
            var selectedEmployeeIDs = "";// Biến lưu trữ chuỗi employeeID của các ô được chọn
       $(document).ready(function () {
     
    var checkAllClicked = false; // Biến đánh dấu xem nút "Chọn tất cả" đã được click hay chưa

    // Xử lý sự kiện khi ô "#checkall" được chọn/deselect
    $("#mytable #checkall").click(function () {
        if ($(this).is(':checked')) {
             // Nếu ô "#checkall" bị deselect, deselect tất cả các ô khác
            $("#mytable input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
                var employeeID = $(this).closest("tr").find("td:eq(1)").text();
                selectedEmployeeIDs = "";
                
            });
            checkAllClicked = false;
            
            
            // Nếu ô "#checkall" được chọn, chọn tất cả các ô khác
            if (!checkAllClicked) {
                $("#mytable input[type=checkbox]").each(function () {
                    $(this).prop("checked", true);
                    if($(this).attr("id") !== "checkall"){
                    var employeeID = $(this).closest("tr").find("td:eq(1)").text();
                    selectedEmployeeIDs += employeeID + "|";
                    
                }
                });
                checkAllClicked = true;
            }
        } else {
            
            $("#mytable input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
                var employeeID = $(this).closest("tr").find("td:eq(1)").text();
                selectedEmployeeIDs = "";
                
            });
            checkAllClicked = false;
        }
       
        console.log(selectedEmployeeIDs);
    });

    // Xử lý sự kiện khi mỗi ô checkbox được chọn/deselect
    $("#mytable input[type=checkbox]").click(function() {
        var employeeID = $(this).closest("tr").find("td:eq(1)").text();
        if ($(this).is(":checked")) {
            if($(this).attr("id") !== "checkall"){
            selectedEmployeeIDs += employeeID + "|";}
        } else {
            if($(this).attr("id") !== "checkall"){
            selectedEmployeeIDs = selectedEmployeeIDs.replace(employeeID + "|", "");}
        }
        console.log(selectedEmployeeIDs);
    });

    $("[data-toggle=tooltip]").tooltip();
});



                function searchByName(param) {
                var checkBox = document.getElementById("checkall");
                var CheckALL = "";                
                if(checkBox.checked&&areAllCheckboxesChecked()){
                    CheckALL = "daCheck";
                }
                else{
                    CheckALL = "";
                }
                
                var txtSearch = $("#txtSearch").val();
                var startx = $("#startHidden").val();
                var endx = $("#endHidden").val();                
                var Page = param.dataset.index ;
                console.log(Page);
                var phongBan = $("#phongBan").val();
                var Date = $("#Date").val();
                var empID = $("#empID").val();
                
                $.ajax({
                    url: "/AttendanceSystem/listAddEmployeeAjax",
                    type: "get",
                    data: {
                        Check:CheckALL,
                        Startx:startx,
                        Endx:endx,
                        listEmpp:selectedEmployeeIDs,                       
                        txt: txtSearch,
                        phong: phongBan,
                        EmpID: empID,
                        Page: Page,
                        date:Date
                        
                    },
                    success: function (data) {                       
                        var row = $("#listEmployee");
                        row.html(data);
                        selectedEmployeeIDs = document.getElementById("listEmployeeAdd").value;
$(document).ready(function () {
     
    var checkAllClicked = false; // Biến đánh dấu xem nút "Chọn tất cả" đã được click hay chưa

    // Xử lý sự kiện khi ô "#checkall" được chọn/deselect
    $("#mytable #checkall").click(function () {
        if ($(this).is(':checked')) {
             // Nếu ô "#checkall" bị deselect, deselect tất cả các ô khác
            $("#mytable input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
                var employeeID = $(this).closest("tr").find("td:eq(1)").text();
                selectedEmployeeIDs = "";
            });
            checkAllClicked = false;
            
            
            // Nếu ô "#checkall" được chọn, chọn tất cả các ô khác
            if (!checkAllClicked) {
                $("#mytable input[type=checkbox]").each(function () {
                    $(this).prop("checked", true);
                    if($(this).attr("id") !== "checkall"){
                    var employeeID = $(this).closest("tr").find("td:eq(1)").text();
                    selectedEmployeeIDs += employeeID + "|";
                    
                }
                });
                checkAllClicked = true;
            }
        } else {
            
            $("#mytable input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
                var employeeID = $(this).closest("tr").find("td:eq(1)").text();
                selectedEmployeeIDs = "";
                
            });
            checkAllClicked = false;
        }
       
        console.log(selectedEmployeeIDs);
    });

    // Xử lý sự kiện khi mỗi ô checkbox được chọn/deselect
    $("#mytable input[type=checkbox]").click(function() {
        var employeeID = $(this).closest("tr").find("td:eq(1)").text();
        if ($(this).is(":checked")&&$(this).attr("id") !== "checkall") {
            selectedEmployeeIDs += employeeID + "|";
        } else {
            if($(this).attr("id") !== "checkall"){
            selectedEmployeeIDs = selectedEmployeeIDs.replace(employeeID + "|", "");}
        }
        console.log(selectedEmployeeIDs);
    });

    $("[data-toggle=tooltip]").tooltip();
});

                        // Gọi lại sự kiện hoặc hàm JS bạn muốn chạy vi khi dung ajax lay du lieu tu serverlet se mat ket noi
                        //initializeYourFunctions();
                    },
                    error: function (xhr) {
                        console.log("Error:", xhr);
                    }
                });
            }
            function prepareAndSubmit() {
  document.getElementById("listEmployeeAdd").value = selectedEmployeeIDs;

  document.getElementById("MyForm").submit();
}


$(document).on("click", "#checkall", function () {
       if(areAllCheckboxesChecked()){
       var PageNow = $(this).closest(".table-responsive").find(".pageNow");
        var checkBox = document.getElementById("checkall");
                var CheckALL = "";                
                if(checkBox.checked){
                    CheckALL = "daCheck";
                }
                else{
                    CheckALL = "";
                }
                
                var txtSearch = $("#txtSearch").val();
                var startx = $("#startHidden").val();
                var endx = $("#endHidden").val();                
                var Page = PageNow.attr("data-index") ;
                console.log(Page);
                var phongBan = $("#phongBan").val();
                var Date = $("#Date").val();
                var empID = $("#empID").val();
                
                $.ajax({
                    url: "/AttendanceSystem/listAddEmployeeAjax",
                    type: "get",
                    data: {
                        Check:CheckALL,
                        Startx:startx,
                        Endx:endx,
                        listEmpp:selectedEmployeeIDs,                       
                        txt: txtSearch,
                        phong: phongBan,
                        EmpID: empID,
                        Page: Page,
                        date:Date
                        
                    },
                    success: function (data) {                       
                        var row = $("#listEmployee");
                        row.html(data);
                        selectedEmployeeIDs = document.getElementById("listEmployeeAdd").value;
$(document).ready(function () {
     
    var checkAllClicked = false; // Biến đánh dấu xem nút "Chọn tất cả" đã được click hay chưa

    // Xử lý sự kiện khi ô "#checkall" được chọn/deselect
    $("#mytable #checkall").click(function () {
        if ($(this).is(':checked')) {
             // Nếu ô "#checkall" bị deselect, deselect tất cả các ô khác
            $("#mytable input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
                var employeeID = $(this).closest("tr").find("td:eq(1)").text();
                selectedEmployeeIDs = "";
            });
            checkAllClicked = false;
            
            
            // Nếu ô "#checkall" được chọn, chọn tất cả các ô khác
            if (!checkAllClicked) {
                $("#mytable input[type=checkbox]").each(function () {
                    $(this).prop("checked", true);
                    if($(this).attr("id") !== "checkall"){
                    var employeeID = $(this).closest("tr").find("td:eq(1)").text();
                    selectedEmployeeIDs += employeeID + "|";
                    
                }
                });
                checkAllClicked = true;
            }
        } else {
            
            $("#mytable input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
                var employeeID = $(this).closest("tr").find("td:eq(1)").text();
                selectedEmployeeIDs = "";
                
            });
            checkAllClicked = false;
        }
       
        console.log(selectedEmployeeIDs);
    });

    // Xử lý sự kiện khi mỗi ô checkbox được chọn/deselect
    $("#mytable input[type=checkbox]").click(function() {
        var employeeID = $(this).closest("tr").find("td:eq(1)").text();
        if ($(this).is(":checked")&&$(this).attr("id") !== "checkall") {
            selectedEmployeeIDs += employeeID + "|";
        } else {
            if($(this).attr("id") !== "checkall"){
            selectedEmployeeIDs = selectedEmployeeIDs.replace(employeeID + "|", "");}
        }
        console.log(selectedEmployeeIDs);
    });

    $("[data-toggle=tooltip]").tooltip();
});

                        // Gọi lại sự kiện hoặc hàm JS bạn muốn chạy vi khi dung ajax lay du lieu tu serverlet se mat ket noi
                        //initializeYourFunctions();
                    },
                    error: function (xhr) {
                        console.log("Error:", xhr);
                    }
                });
            }
    });

           
        </script>
    </body>
</html>

