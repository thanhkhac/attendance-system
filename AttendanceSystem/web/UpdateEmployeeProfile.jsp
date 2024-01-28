<%-- 
    Document   : UpdateProfile
    Created on : Jan 18, 2024, 6:20:40 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.EmployeeDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link rel="stylesheet" href="updateTest.css">
        <script src="https://kit.fontawesome.com/cec63a414e.js" crossorigin="anonymous"></script>
        <style>
            body{
                background-color:#f2f6fc;
                color:#69707a;
            }
            .img-profile{
                height: 9em;
            }
            .card {
                box-shadow: 0 0.15rem 1.75rem 0 rgb(33 40 50 / 15%);
            }
            .card-header {
                font-weight: 500;
                padding: 1em 1.5em;
            }
            .avatar img{
                height: 7em;

            }
            .department{
                background-color: #5bc0de;
                color: white;
                font-weight: 700;
                font-size: smaller;
                width: max-content;
                padding: 4px 9px;
                border-radius: 9px;
            }

        </style>
    </head>
    <body>
        <div class="row">
            <div class="col-md-2">
        <%@include file="Sidebar.jsp" %>
        </div>
        <div class="container-xl px-4 mt-4 col-md-10">
            <%
               EmployeeDTO employee =(EmployeeDTO) session.getAttribute("ACCOUNT");
               if(employee!=null){
               String Department = "";
                if(employee.getDepartmentID()==1)
                 Department = "Phòng nhân sự";
                 else Department = "Phòng tiếp thị";
            %>
            <div class="row">
                <div class="col-xl-4">
                    <div class="card"> 
                        <div class="card-header">
                            Employee profile
                        </div>
                        <div>
                            <div class="text-center">
                                <img class="img-profile rounded-circle mb-2 mt-4" src="https://t4.ftcdn.net/jpg/03/49/49/79/360_F_349497933_Ly4im8BDmHLaLzgyKg2f2yZOvJjBtlw5.jpg" alt="">
                                <div class="font-italic text-muted mb-2"><%=employee.getLastName()+" "+employee.getMiddleName()+" "+employee.getFirstName()%></div>
                                <div class="d-flex justify-content-center align-items-center"> <p class="department"><%=Department%></p></div>
                                <div class="font-italic text-muted"><a href="#"><p><%=employee.getEmail()%></p></a></div>
                                <div class="small font-italic text-muted mb-2">Date of starting work: <%=employee.getStartDate()%></div>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="col-xl-8">
                    <div class="card">
                        <div class="card-header">
                            Account Details
                        </div>
                        <div class="card-body">
                            <form action="DispatchController">
                                <div class="mb-2">

                                    <div class="row form-group avatar">
                                        <figure class="figure col-md-3 col-sm-3 col-xs-12">
                                            <img class="img-rounded rounded-circle img-responsive" src="https://t4.ftcdn.net/jpg/03/49/49/79/360_F_349497933_Ly4im8BDmHLaLzgyKg2f2yZOvJjBtlw5.jpg" alt="">
                                        </figure>
                                        <div class="form-inline col-md-9 col-sm-9 col-xs-12 mt-4 ">
                                            <input type="file" class="file-uploader pull-left mt-4 mb-2">
                                            <button type="submit" class="btn btn-primary">Update Image</button>
                                        </div>
                                    </div>
                                </div>
                                <div class="mb-2">
                                    <label class="small mb-1" for="txtName">Full name</label>
                                    <input class="form-control" type="text" name="txtName" id="txtName" value="<%=employee.getLastName()+" "+employee.getMiddleName()+" "+employee.getFirstName()%>" readonly >
                                </div>
                                <div class="row mb-2">
                                    <div class="col-md-6">
                                        <label class="small mb-1" for="txtFirstName">First name</label>
                                        <input class="form-control" type="text" name="txtFirstName" id="txtFirstName" value="<%=employee.getFirstName()%>" readonly >
                                    </div>
                                    <div class="col-md-6">
                                        <label class="small mb-1" for="txtLastName">Last name</label>
                                        <input class="form-control" type="text" name="txtLastName" id="txtLastName" value="<%=employee.getLastName()%>" readonly >
                                    </div>
                                </div>
                                <div class="row mb-2">
                                    <div class="col-md-6">
                                        <label class="small mb-1" for="txtPhone">Phone number</label>
                                        <input class="form-control" type="text" name="txtPhone" id="txtPhone" value="<%=employee.getPhoneNumber()%>" required >
                                    <%
                                              String checkPhone = (String) request.getAttribute("CHECKPHONE");
                                              if(checkPhone!=null){
                                            %>
                                            <p style="margin-left: 3px;
                                               color: red;"><%=checkPhone%></p>
                                            <%}%>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="small mb-1" for="txtBirth">Birthday</label>
                                        <input class="form-control" type="text" name="txtBirth" value="<%=employee.getBirthDate()%>" id="txtBirth" readonly >
                                    </div>
                                </div>
                                <div class="mb-2">
                                    <label class="small mb-1" for="txtEmail">Email address</label>
                                    <input class="form-control" type="text" name="txtEmail" value="<%=employee.getEmail()%>" id="txtEmail" readonly >
                                </div>
                                <div class="row mb-2">
                                    <div class="col-md-6">
                                        <label class="small mb-1" for="txtCCCD">CCCD</label>
                                        <input class="form-control" type="text" name="txtCCCD" id="txtCCCD" value="<%=employee.getCccd()%>"  readonly >
                                    </div>
                                    <div class="col-md-6">
                                        <label class="small mb-1" for="txtGender">Gender</label>
                                        <%
                                          if(employee.getGender()){
                                        %>
                                        <select class="form-control" name="txtGender" id="txtGender">
                                            <option value="Male">Male</option>
                                            <option value="Female" selected="">Female</option>
                                        </select>
                                        <%
                                            } else{
                                        %>
                                        <select class="form-control" name="txtGender" id="txtGender">
                                            <option value="Male" selected="">Male</option>
                                            <option value="Female" >Female</option>
                                        </select>
                                        <%}%>
                                    </div>
                                    <div class="">
                                        <label class="small " for="txtAddress">Address</label>
                                        <input class="form-control" type="text" name="txtAddress" id="txtAddress" value=""  >
                                    </div>
                                </div>
                                <%
                                     String check = (String) request.getAttribute("CHECK");
                                     if(check!=null){
                                    %>
                                    <div style ="color: #35cf13;
                                         font-weight: 600;
                                         font-family: system-ui;" class="">
                                        Update thành công!!!
                                    </div>
                                    <%}%>
                                <div class="">
                                    <button class="btn btn-primary mt-3" name="btAction" value="UpdateProfile" type="submit">Save changes</button>
                                </div>

                            </form>
                        </div>
                    </div>

                </div>

            </div>
            <%}%>
        </div>
        </div>
    </body>
</html>
