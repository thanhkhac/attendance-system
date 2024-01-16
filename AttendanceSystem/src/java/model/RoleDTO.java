package model;


public class RoleDTO {
    private int roleId;
    private String name;

    public RoleDTO(int roleID, String name) {
        this.roleId = roleID;
        this.name = name;
    }

    public int getRoleID() {
        return roleId;
    }

    public void setRoleID(int roleID) {
        this.roleId = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleDTO{" + "roleID=" + roleId + ", name=" + name + '}';
    }
    
    
    
}
