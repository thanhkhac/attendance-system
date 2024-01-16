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
public class EmployeeDTO {

    private int employeeID;
    private String firstName;
    private String middleName;
    private String lastName;
    private boolean gender;
    private String email;
    private String password;
    private String cccd;
    private String phoneNumber;
    private EmployeeTypeDTO employeeType;
    private DepartmentDTO department;
    private RoleDTO role;
    private Date startDate;
    private Date endDate;
    private boolean isActived;

    public EmployeeDTO() {
    }

    public EmployeeDTO(int employeeID, String firstName, String middleName, String lastName, boolean gender, String email, String password, String cccd, String phoneNumber, EmployeeTypeDTO employeeType, DepartmentDTO department, RoleDTO role, Date startDate, Date endDate, boolean isActived) {
    this.employeeID = employeeID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.cccd = cccd;
        this.phoneNumber = phoneNumber;
        this.employeeType = employeeType;
        this.department = department;
        this.role = role;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActived = isActived;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EmployeeTypeDTO getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeTypeDTO employeeType) {
        this.employeeType = employeeType;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isIsActived() {
        return isActived;
    }

    public void setIsActived(boolean isActived) {
        this.isActived = isActived;
    }
    
    

    @Override
    public String toString() {
        return "EmployeeDTO{" + "employeeID=" + employeeID + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", gender=" + gender + ", email=" + email + ", password=" + password + ", cccd=" + cccd + ", phoneNumber=" + phoneNumber + ", employeeType=" + employeeType + ", department=" + department + ", role=" + role + ", startDate=" + startDate + ", endDate=" + endDate + ", isActived=" + isActived + '}';
    }


    


    
    

}
