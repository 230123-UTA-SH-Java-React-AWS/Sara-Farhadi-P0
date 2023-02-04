package com.project0.model;

public class User {
    
    private String userEmail;
    private String userPassword;
    protected String userRole;

    protected User() {
        userRole = "Employee";
    }
    
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "Employee [Email: " + userEmail + ", Password: " + userPassword + ", Role: " + userRole + "]";
    }
    
}
