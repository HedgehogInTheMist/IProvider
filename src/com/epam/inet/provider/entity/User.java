package com.epam.inet.provider.entity;

/**
 * User
 */
public class User extends Entity {

    /**
     * username
     */
    private String username;

    /**
     * hashed password
     */
    private String password;

    /**
     * role name
     */
    private Role role;

    /**
     * Default constructor
     */
    public User(){

    }


    /**
     * get Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * set Username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get Role
     */
    public Role getRole() {
        return role;
    }

    /**
     * set Role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return String.format("User[id: %d, username: %s, role: %s]", getId(), getUsername(), getRole().getRolename());
    }
}
