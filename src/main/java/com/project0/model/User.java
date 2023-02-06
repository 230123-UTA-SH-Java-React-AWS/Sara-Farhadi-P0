package com.project0.model;
import java.text.SimpleDateFormat;

public class User {

    private String userID;
    private String userEmail;
    private String userPassword;
    protected String userRole;

    protected User() {
        userRole = "Employee";
        userID = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
        return "Employee [User ID: " + userID + ", Email: " + userEmail + ", Password: " + userPassword + ", Role: " + userRole + "]";
    }
    
}
