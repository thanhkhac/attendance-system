package model;


public class DepartmentDTO {
    private int department;
    private String name;
    private int managerId;

    public DepartmentDTO() {
    }

    public DepartmentDTO(int department, String name, int managerId) {
        this.department = department;
        this.name = name;
        this.managerId = managerId;
    }

    
    public int getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    public int getManagerId() {
        return managerId;
    }

    @Override
    public String toString() {
        return "DepartmentDTO{" + "department=" + department + ", name=" + name + ", managerId=" + managerId + '}';
    }

    
    
}
