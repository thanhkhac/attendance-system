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
    private boolean gender;
    private Date birthDate;
    private String email;
    private String password;
    private String cccd;
    private String phoneNumber;
    private int employeeTypeId;
    private int departmentId;
    private int role;
    private Date startDate;
    private Date endDate;
    private boolean isActived;

    public EmployeeDTO(int employeeID, String firstName, String middleName, String lastName, boolean gender, Date birthDate, String email, String password, String cccd, String phoneNumber, int EmployeeTypeID, int DepartmentID, int role, Date startDate, Date endDate, boolean isActived) {
        this.employeeId = employeeID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.cccd = cccd;
        this.phoneNumber = phoneNumber;
        this.employeeTypeId = EmployeeTypeID;
        this.departmentId = DepartmentID;
        this.role = role;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActived = isActived;
    }

    public EmployeeDTO() {
    }
    

    public int getEmployeeID() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCccd() {
        return cccd;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getEmployeeTypeID() {
        return employeeTypeId;
    }

    public int getDepartmentID() {
        return departmentId;
    }

    public int getRole() {
        return role;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isIsActived() {
        return isActived;
    }
   

    
    

}
