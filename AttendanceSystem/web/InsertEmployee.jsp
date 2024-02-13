<%-- 
    Document   : InsertEmployee
    Created on : Jan 21, 2024, 9:11:23 PM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.time.*" %>
<%@page import="model.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert Employee</title>

        <link rel="stylesheet" href="assets/Bootstrap5/css/bootstrap.min.css"/>
        <style>
            .card-registration .select-input.form-control[readonly]:not([disabled]) {
                font-size: 1rem;
                line-height: 2.15;
                padding-left: .75em;
                padding-right: .75em;
            }
            .card-registration .select-arrow {
                top: 13px;
            }
            .display-color{
                color: #999;
            }
        </style>
        <%@include file="Sidebar.jsp" %>
    </head>

    <%
        String lastname = (String) request.getAttribute("LASTNAME");
        String middlename = (String) request.getAttribute("MIDDLENAME");
        String firstname = (String) request.getAttribute("FIRSTNAME");
        String cccd = (String) request.getAttribute("CCCD");
        String email = (String) request.getAttribute("EMAIL");    
        String phonenumber = (String) request.getAttribute("PHONENUMBER");  
        String employeetypeid = (String) request.getAttribute("EMPLTYPEID");
        String departmentid = (String) request.getAttribute("DEPARTMENTID");
        String roleid = (String) request.getAttribute("ROLEID");
        LocalDate birthdate = (LocalDate) request.getAttribute("BIRTHDATE");
        LocalDate startdate = (LocalDate) request.getAttribute("STARTDATE");
        LocalDate enddate = (LocalDate) request.getAttribute("ENDDATE");
        String msg = (String) request.getAttribute("MSG");

        
        String err_fname = (String) request.getAttribute("ERR_FNAME");
        String err_mname = (String) request.getAttribute("ERR_MNAME");
        String err_lname = (String) request.getAttribute("ERR_LNAME");
        String err_birthdate = (String) request.getAttribute("ERR_B_DATE");
        String err_email = (String) request.getAttribute("ERR_EMAIL");
        String err_phonenum = (String) request.getAttribute("ERR_PHONE");
        String err_cccd = (String) request.getAttribute("ERR_CCCD");
        String err_s_date = (String) request.getAttribute("ERR_S_DATE");
        String err_e_date = (String) request.getAttribute("ERR_E_DATE");
        
        
        
        if(lastname == null){
            lastname = "";
        }
        if(middlename == null){
            middlename = "";
        }
        if(firstname == null){
            firstname = "";
        }
        if(cccd == null){
            cccd = "";
        }
        if(email == null){
            email = "";
        }
        if(phonenumber == null){
            phonenumber = "";
        }
        
        if(err_fname == null){
            err_fname = "";
        }
        if(err_mname == null){
            err_mname = "";
        }
        if(err_lname == null){
            err_lname = "";
        }
        if(err_birthdate == null){
            err_birthdate = "";
        }
        if(err_email == null){
            err_email = "";
        }
        if(err_phonenum == null){
            err_phonenum = "";
        }
        if(err_cccd == null){
           err_cccd = "";
        }
        if(err_s_date == null){
            err_s_date = "";
        }
        if(err_e_date == null){
            err_e_date = "";
        }
        
        EmployeeTypeDAO emTypeDao = new EmployeeTypeDAO();
        DepartmentDAO deDao = new DepartmentDAO();
        RoleDAO roDAO = new RoleDAO();
        List<EmployeeTypeDTO> listEmployeeType = emTypeDao.getEmployeeTypeList();
        List<DepartmentDTO> listDepartment = deDao.getListDepartment();
        List<RoleDTO> listRole = roDAO.getRoleList();
        
    %>
    <body>
            <section class="vh-100 gradient-custom">
                <div class="container py-5 h-100">
                    <div class="row justify-content-center align-items-center h-100">
                        <div class="col-12 col-lg-9 col-xl-7" style="width: 900px">
                            <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                                <div class="card-body p-4 p-md-5">
                                    <h3 class="mb-4 pb-2 pb-md-0 mb-md-5">Insert Employee</h3>
                                    <form action="DispatchController">

                                        <div class="row">

                                            <div class="col-md-4 mb-4">

                                                <div class="form-outline">
                                                    <input type="text" id="lastName" class="form-control form-control-lg" name="txtLastName" value="<%=lastname%>"/>
                                                    <label class="form-label" for="lastName">Họ</label>
                                                </div>

                                                <div class="form-outline">
                                                    <%
                                                    if( err_lname.length() > 0){
                                                    %>
                                                    <p style="color: #ff0000"><i class="fa-solid fa-triangle-exclamation"></i><%=err_lname%></p>
                                                        <%
                                                        }
                                                        %>
                                                </div>

                                            </div>

                                            <div class="col-md-4 mb-4">

                                                <div class="form-outline">
                                                    <input type="text" id="middleName" class="form-control form-control-lg" name="txtMiddleName" value="<%=middlename%>"/>
                                                    <label class="form-label" for="middleName">Tên Đệm</label>
                                                </div>

                                                <div class="form-outline">
                                                    <%
                                                    if(err_mname.length() > 0){
                                                    %>
                                                    <p style="color: #ff0000"><i class="fa-solid fa-triangle-exclamation"></i><%=err_mname%></p>
                                                        <%
                                                        }
                                                        %>
                                                </div>

                                            </div>

                                            <div class="col-md-4 mb-4">

                                                <div class="form-outline">
                                                    <input type="text" id="firstName" class="form-control form-control-lg" name="txtFirstName" value="<%=firstname%>"/>
                                                    <label class="form-label" for="firstName">Tên Riêng</label>
                                                </div>

                                                <div class="form-outline">
                                                    <%
                                                    if(err_fname.length() > 0){
                                                    %>
                                                    <p style="color: #ff0000"><i class="fa-solid fa-triangle-exclamation"></i><%=err_fname%></p>
                                                        <%
                                                        }
                                                        %>
                                                </div>

                                            </div>

                                        </div>

                                        <div class="row">
                                            <div class="col-md-4 mb-4">

                                                <div class="form-outline datepicker w-100">
                                                    <input type="date" class="form-control form-control-lg display-color" id="birthdayDate" name="txtBirthDate" value="${BIRTHDATE}"/>
                                                    <label for="birthdayDate" class="form-label">Ngày sinh</label>
                                                </div>

                                                <div class="form-outline">
                                                    <%
                                                    if(err_birthdate.length() > 0){
                                                    %>
                                                    <p style="color: #ff0000"><i class="fa-solid fa-triangle-exclamation""></i><%=err_birthdate%></p>
                                                    <%
                                                    }
                                                    %>
                                                </div>

                                            </div>

                                            <div class="col-md-4 mb-4 pb-2">

                                                <div class="form-outline">
                                                    <input type="tel" id="cccd" class="form-control form-control-lg" name="txtCCCD" value="<%=cccd%>"/>
                                                    <label class="form-label" for="cccd">Căn cước công dân</label>
                                                </div>

                                                <div class="form-outline">
                                                    <%
                                                    if(err_cccd.length() > 0){
                                                    %>
                                                    <p style="color: #ff0000"><i class="fa-solid fa-triangle-exclamation"></i><%=err_cccd%></p>
                                                        <%
                                                        }
                                                        %>
                                                </div>

                                            </div>

                                            <div class="col-md-4 mb-4">

                                                <h6 class="mb-2 pb-1">Giới tính</h6>

                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="txtGender" id="femaleGender"
                                                           value="male" checked />
                                                    <label class="form-check-label" for="femaleGender">Nam</label>
                                                </div>

                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="txtGender" id="maleGender"
                                                           value="female" />
                                                    <label class="form-check-label" for="maleGender">Nữ</label>
                                                </div>

                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-6 mb-4 pb-2">

                                                <div class="form-outline">
                                                    <input type="email" id="email" class="form-control form-control-lg" name="txtEmail" value="<%=email%>"/>
                                                    <label class="form-label" for="emailAddress">Email</label>
                                                </div>

                                                <div class="form-outline">
                                                    <%
                                                    if(err_email.length() > 0){
                                                    %>
                                                    <p style="color: #ff0000"><i class="fa-solid fa-triangle-exclamation"></i><%=err_email%></p>
                                                        <%
                                                        }
                                                        %>
                                                </div>

                                            </div>
                                            <div class="col-md-6 mb-4 pb-2">

                                                <div class="form-outline">
                                                    <input type="tel" id="phoneNumber" class="form-control form-control-lg" name="txtPhonenumber" value="<%=phonenumber%>"/>
                                                    <label class="form-label" for="phoneNumber">Số điện thoại</label>
                                                </div>

                                                <div class="form-outline">
                                                    <%
                                                    if(err_phonenum.length() > 0){
                                                    %>
                                                    <p style="color: #ff0000"><i class="fa-solid fa-triangle-exclamation"></i><%=err_phonenum%></p>
                                                        <%
                                                        }
                                                        %>
                                                </div>

                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-4 mb-4">

                                                <select class="select form-control-lg display-color" name="txtEmployeeTypeID" " >
                                                    <option value="" disabled>Choose option</option>

                                                    <%
                                                    for (EmployeeTypeDTO employeeTypeDTO : listEmployeeType) {
                                                        if(employeetypeid != null && employeetypeid.equals(employeeTypeDTO.getName())){
                                                    
                                                    %>
                                                    <option selected value="<%=employeeTypeDTO.getName()%>"><%=employeeTypeDTO.getName()%></option>
                                                    <%
                                                        }else{
                                                    %>
                                                    <option value="<%=employeeTypeDTO.getName()%>"><%=employeeTypeDTO.getName()%></option>
                                                    <%
                                                        }
                                                    }
                                                    %>
                                                </select> 
                                                <label class="form-label select-label">Loại nhân viên</label>

                                            </div>

                                            <div class="col-md-4 mb-4">

                                                <select class="select form-control-lg display-color" name="txtDepartmentID" ">
                                                    <option value="" disabled>Choose option</option>
                                                    <%
                                                    for (DepartmentDTO departmentDTO : listDepartment) {
                                                        if(departmentid != null && departmentid.equals(departmentDTO.getName())){
                                                    %>
                                                    <option selected value="<%=departmentDTO.getName()%>"><%=departmentDTO.getName()%></option>
                                                    <%   
                                                        }else{
                                                    %>
                                                    <option value="<%=departmentDTO.getName()%>"><%=departmentDTO.getName()%></option>
                                                    <%
                                                        }
                                                    }
                                                    %>
                                                </select> 
                                                <label class="form-label select-label">Phòng ban</label>

                                            </div>

                                            <div class="col-md-4 mb-4">

                                                <select class="select form-control-lg display-color" name="txtRoleID" ">
                                                    <option value="1" disabled>Choose option</option>
                                                    <%
                                                    for (RoleDTO roleDTO : listRole) {
                                                        if(roleid != null && roleid.equals(roleDTO.getName())){
                                                    %>
                                                    <option selected="" value="<%=roleDTO.getName()%>"><%=roleDTO.getName()%></option>
                                                    <%
                                                        }else{
                                                    %>
                                                    <option value="<%=roleDTO.getName()%>"><%=roleDTO.getName()%></option>
                                                    <%
                                                        }
                                                    }
                                                    %>
                                                </select> 
                                                <label class="form-label select-label">Chức vụ</label>

                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-6 mb-4">

                                                <div class="form-outline datepicker w-100">
                                                    <input type="date" class="form-control form-control-lg display-color" id="startDate" name="txtStartDate" value="${STARTDATE}"/>
                                                    <label for="startDate" class="form-label">Ngày bắt đầu</label>
                                                </div>

                                                <div class="form-outline">
                                                    <%
                                                    if(err_s_date.length() > 0){
                                                    %>
                                                    <p style="color: #ff0000"><i class="fa-solid fa-triangle-exclamation"></i><%=err_s_date%></p>
                                                        <%
                                                        }
                                                        %>
                                                </div>

                                            </div>

                                            <div class="col-md-6 mb-4">

                                                <div class="form-outline datepicker w-100">
                                                    <input type="date" class="form-control form-control-lg display-color" id="endate" name="txtEndDate" value="${ENDDATE}""/>
                                                    <label for="endDate" class="form-label">Ngày kết thúc</label>
                                                </div>

                                                <div class="form-outline">
                                                    <%
                                                    if(err_e_date.length() > 0){
                                                    %>
                                                    <p style="color: #ff0000"><i class="fa-solid fa-triangle-exclamation"></i><%=err_e_date%></p>
                                                        <%
                                                        }
                                                        %>
                                                </div>

                                            </div>
                                        </div>

                                        <div class="row">

                                            <div class="col-md-6 mb-4 form-check form-switch">
                                                <input class="form-check-input" type="checkbox" id="isActive" name="txtIsActive">
                                                <label class="form-check-label" for="isActive">Kích hoạt tài khoản</label>
                                            </div>

                                            <div class="col-md-6 mb-4 form-check form-switch">
                                                <%
                                                if(msg != null && msg.length() > 0){
                                                %>
                                                <p style="color: #ff0000"><i class="fa-solid fa-triangle-exclamation"></i><%=msg%></p>
                                                <%
                                                }
                                                %>
                                            </div>
                                        </div>
                                        <div class="mt-4 pt-2">
                                            <button class="btn btn-primary btn-lg" name="btAction" value="Insert">Thêm</button>
                                        </div>

                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>


        <script src="https://kit.fontawesome.com/c2b5cd9aa7.js" crossorigin="anonymous"></script>
    </body>
</html>
