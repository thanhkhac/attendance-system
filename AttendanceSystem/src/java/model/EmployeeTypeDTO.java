package model;

public class EmployeeTypeDTO {

    private int employeeTypeId;
    private String name;

    public EmployeeTypeDTO(int employeeTypeID, String name) {
        this.employeeTypeId = employeeTypeID;
        this.name = name;
    }

    public int getEmployeeTypeId() {
        return employeeTypeId;
    }

    
    public int getEmployeeTypeID() {
        return employeeTypeId;
    }

    public void setEmployeeTypeID(int employeeTypeID) {
        this.employeeTypeId = employeeTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EmployeeType{" + "employeeTypeID=" + employeeTypeId + ", name=" + name + '}';
    }

}
