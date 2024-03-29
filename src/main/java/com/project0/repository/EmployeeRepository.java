package com.project0.repository;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.project0.model.Employee;
import com.project0.model.Ticket;
import com.project0.utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    public void SaveToJson(Employee employee)
    {
        ObjectMapper mapper = new ObjectMapper();
        String jsonObject = "";
        try{
            jsonObject = mapper.writeValueAsString(employee);
            File employeeFile = new File("./src/main/java/com/project0/repository/employee.json");
            employeeFile.createNewFile();
            FileWriter writer = new FileWriter("./src/main/java/com/project0/repository/employee.json");
            writer.write(jsonObject);
            writer.close();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registration(Employee employee)
    {
        String sql = "insert into employee (userEmail, userPassword, userRole, userID) values (?, ?, ?, ?)";
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            if (employeeExist(employee) == false){  //Check if employee already exist or not
                prstmt.setString(1, employee.getUserEmail());
                prstmt.setString(2, employee.getUserPassword());
                prstmt.setString(3, employee.getUserRole());
                prstmt.setString(4, employee.getUserID());
            } else {
                System.out.println("Employee Already Exist!");
            }
            prstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Employee login(Employee employee) 
    {
        String sql = "select * from employee where userEmail = ?";
        Employee CurrentUser = new Employee();
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, employee.getUserEmail());
            ResultSet rs = prstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Employee does not exist!");
                return null;
            }   // check if the password is correct
            else if (employee.getUserPassword().equals(rs.getString(2))) {
                CurrentUser.setUserEmail(rs.getString("userEmail"));
                CurrentUser.setUserPassword(rs.getString("userPassword"));
                CurrentUser.setUserRole(rs.getString("userRole"));
                CurrentUser.setUserID(rs.getString("userID"));
                CurrentUser.setTickets(getUserTickets(CurrentUser.getUserEmail()));
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

    public List<Ticket> getUserTickets (String userEmailID)
    {
        String sql = "select * from ticket where userEmail = ?";
        List<Ticket> allTickets = new ArrayList<Ticket>();
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, userEmailID);
            ResultSet rs = prstmt.executeQuery();
            while (rs.next()){
                Ticket userTicket = new Ticket();
                userTicket.setUserEmail(rs.getString("userEmail"));
                userTicket.setAmount(rs.getInt("amount"));
                userTicket.setDescription(rs.getString("description"));
                userTicket.setStatus(rs.getString("status"));
                userTicket.setProcessed(rs.getBoolean("processed"));
                allTickets.add(userTicket);
            }
            rs.close();
            prstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allTickets;
    }

    public List<Ticket> getFilteredUserTickets (String userEmailID, String filter)
    {
        String sql = "select * from ticket where userEmail = ? and status = ?";
        List<Ticket> allTickets = new ArrayList<Ticket>();
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, userEmailID);
            prstmt.setString(2, filter);
            ResultSet rs = prstmt.executeQuery();
            while (rs.next()){
                Ticket userTicket = new Ticket();
                userTicket.setUserEmail(rs.getString("userEmail"));
                userTicket.setAmount(rs.getInt("amount"));
                userTicket.setDescription(rs.getString("description"));
                userTicket.setStatus(rs.getString("status"));
                userTicket.setProcessed(rs.getBoolean("processed"));
                allTickets.add(userTicket);
            }
            rs.close();
            prstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allTickets;
    }

    private boolean employeeExist (Employee employee) throws SQLException
    {
        boolean existingUser = true;
        Connection con = ConnectionUtil.getConnection();
        String sql = "select userEmail from employee where userEmail= ? ";
        PreparedStatement prepStmt = con.prepareStatement(sql);
        prepStmt.setString(1, employee.getUserEmail());
        ResultSet rs = prepStmt.executeQuery();
        if (rs.next()){
            existingUser = true;
        } else {
            existingUser = false;
        }
        return existingUser;
    }
    
}
