package model;


public class DepartmentDTO {
    private int department;
    private String name;
    private EmployeeDTO manager;

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

    public EmployeeDTO getManager() {
        return manager;
    }

    public void setManager(EmployeeDTO manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "DepartmentDTO{" + "department=" + department + ", name=" + name + ", manager=" + manager + '}';
    }
    
    
}
