package model;

public class DepartmentDTO {

    private int department;
    private String name;
    private int managerID;

    public DepartmentDTO() {
    }

    public DepartmentDTO(int department, String name, int managerID) {
        this.department = department;
        this.name = name;
        this.managerID = managerID;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
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
