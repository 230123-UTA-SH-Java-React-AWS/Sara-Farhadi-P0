package model;
import java.util.ArrayList;
import java.util.List;

public class Employee {

    private String employeeEmail;
    private int employeePassword;

    // public Employee(String employeeEmail, int employeePassword) {
    //     this.employeeEmail = employeeEmail;
    //     this.employeePassword = employeePassword;
    // };

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public int getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(int employeePassword) {
        this.employeePassword = employeePassword;
    }
    
    
}
