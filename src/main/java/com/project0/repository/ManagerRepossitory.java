package com.project0.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.project0.model.Employee;
import com.project0.model.Manager;
import com.project0.utils.ConnectionUtil;

public class ManagerRepossitory {
    
    public void registration(Manager manager)
    {
        String sql = "insert into employee (userEmail, userPassword, userRole, userID) values (?, ?, ?, ?)";
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            if (managerExist(manager) == false){    //Check if manager already exist or not
                prstmt.setString(1, manager.getUserEmail());
                prstmt.setString(2, manager.getUserPassword());
                prstmt.setString(3, manager.getUserRole());
                prstmt.setString(4, manager.getUserID());
            } else {
                System.out.println("Manager Already Exist!");
            }
            prstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Manager login(Manager manager) 
    {
        String sql = "select * from employee where userEmail = ?";
        Manager CurrentUser = new Manager();
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, manager.getUserEmail());
            ResultSet rs = prstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Employee does not exist!");
                return null;
            }   // check if the password is correct
            else if (manager.getUserPassword().equals(rs.getString(2))) {
                CurrentUser.setUserEmail(rs.getString("userEmail"));
                CurrentUser.setUserPassword(rs.getString("userPassword"));
                CurrentUser.setUserRole(rs.getString("userRole"));
                CurrentUser.setUserID(rs.getString("userID"));
            } else {
                System.out.println("Wrong Password!");
                return null;
            }
            rs.close();
            prstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CurrentUser;
    }

    public List<Employee> getAllEmployee() 
    {
        String sql = "select * from employee";
        List<Employee> listOfEmployee = new ArrayList<Employee>();
        try (Connection con = ConnectionUtil.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Employee employees = new Employee();
                employees.setUserID(rs.getString(1));
                employees.setUserEmail(rs.getString(2));
                employees.setUserPassword(rs.getString(3));
                employees.setUserRole(rs.getString(4));
                listOfEmployee.add(employees);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfEmployee;
    }

    private boolean managerExist (Manager manager) throws SQLException
    {
        boolean existingUser = true;
        Connection con = ConnectionUtil.getConnection();
        String sql = "select userEmail from employee where userEmail= ? ";
        PreparedStatement prepStmt = con.prepareStatement(sql);
        prepStmt.setString(1, manager.getUserEmail());
        ResultSet rs = prepStmt.executeQuery();
        if (rs.next()){
            existingUser = true;
        } else {
            existingUser = false;
        }
        return existingUser;
    }

}
