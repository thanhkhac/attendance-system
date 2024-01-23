package model;

public class DepartmentDTO {

    private int departmentID;
    private String name;
    private int managerID;

    public DepartmentDTO() {
    }

    public DepartmentDTO(int departmentID, String name, int managerID) {
        this.departmentID = departmentID;
        this.name = name;
        this.managerID = managerID;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    
}
