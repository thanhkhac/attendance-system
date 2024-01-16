package model;


public class RoleDTO {
    private int roleID;
    private String name;

    public RoleDTO(int roleID, String name) {
        this.roleID = roleID;
        this.name = name;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleDTO{" + "roleID=" + roleID + ", name=" + name + '}';
    }
    
    
    
}
