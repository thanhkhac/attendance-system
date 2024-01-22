/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author admin
 */
public class EmployeeGeneral extends EmployeeDTO {

    private String departmentName;
    private String employeeTypeName;
    private String roleName;

    public EmployeeGeneral(String departmentName, String employeeTypeName, String roleName) {
        this.departmentName = departmentName;
        this.employeeTypeName = employeeTypeName;
        this.roleName = roleName;
    }

    public EmployeeGeneral(int employeeId, String firstName, String middleName, String lastName, Date birthDate, Boolean gender, String email, String password, String cccd, String phoneNumber, int employeeTypeID, String employeeTypeName, int departmentID, String departmentName, int roleID, String roleName, Date startDate, Date endDate, boolean isActived) {
        super(employeeId, firstName, middleName, lastName, birthDate, gender, email, password, cccd, phoneNumber, employeeTypeID, departmentID, roleID, startDate, endDate, isActived);
        this.departmentName = departmentName;
        this.employeeTypeName = employeeTypeName;
        this.roleName = roleName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEmployeeTypeName() {
        return employeeTypeName;
    }

    public void setEmployeeTypeName(String employeeTypeName) {
        this.employeeTypeName = employeeTypeName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    
}
