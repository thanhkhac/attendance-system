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

    private int employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
//    private boolean gender;
    private Date birthDate;
    private Boolean gender;
    private String email;
    private String password;
    private String cccd;
    private String phoneNumber;
    private int employeeTypeID;
    private int departmentID;
    private int roleID;
    private Date startDate;
    private Date endDate;
    private boolean isActived;

    public EmployeeDTO() {
    }

    public EmployeeDTO(int employeeId, String firstName, String middleName, String lastName, Date birthDate, Boolean gender, String email, String password, String cccd, String phoneNumber, int employeeTypeID, int departmentID, int roleID, Date startDate, Date endDate, boolean isActived) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.cccd = cccd;
        this.phoneNumber = phoneNumber;
        this.employeeTypeID = employeeTypeID;
        this.departmentID = departmentID;
        this.roleID = roleID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActived = isActived;
    }

    public int getEmployeeID() {
        return employeeId;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    
    

    public void setEmployeeID(int employeeId) {
        this.employeeId = employeeId;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
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

    public int getEmployeeTypeID() {
        return employeeTypeID;
    }

    public void setEmployeeTypeID(int employeeTypeID) {
        this.employeeTypeID = employeeTypeID;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
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
        return "EmployeeDTO{" + "employeeId=" + employeeId + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", birthDate=" + birthDate + ", gender=" + gender + ", email=" + email + ", password=" + password + ", cccd=" + cccd + ", phoneNumber=" + phoneNumber + ", employeeTypeID=" + employeeTypeID + ", departmentID=" + departmentID + ", roleID=" + roleID + ", startDate=" + startDate + ", endDate=" + endDate + ", isActived=" + isActived + '}';
    }

}
