package model;

public class EmployeeTypeDTO {

    private int employeeTypeID;
    private String name;

    public EmployeeTypeDTO(int employeeTypeID, String name) {
        this.employeeTypeID = employeeTypeID;
        this.name = name;
    }

    public int getEmployeeTypeID() {
        return employeeTypeID;
    }

    public void setEmployeeTypeID(int employeeTypeID) {
        this.employeeTypeID = employeeTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EmployeeType{" + "employeeTypeID=" + employeeTypeID + ", name=" + name + '}';
    }

//    
}
