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
        String sql = "insert into employee (userID, userEmail, userPassword, userRole) values (?, ?, ?, ?)";
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            if (employeeExist(employee) == false){
                prstmt.setString(1, employee.getUserID());
                prstmt.setString(2, employee.getUserEmail());
                prstmt.setString(3, employee.getUserPassword());
                prstmt.setString(4, employee.getUserRole());
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
                return null;
            }
            else if (employee.getUserPassword().equals(rs.getString(3))) {
                CurrentUser.setUserID(rs.getString("userID"));
                CurrentUser.setUserEmail(rs.getString("userEmail"));
                CurrentUser.setUserPassword(rs.getString("userPassword"));
                CurrentUser.setUserRole(rs.getString("userRole"));
                //CurrentUser.setTicket(getTicketByUserEmail(CurrentUser.setUserEmail()));
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
              //  newPokemon.setAbilities(getAbilityByPokeId(newPokemon.getPokeId()));
                listOfEmployee.add(employees);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfEmployee;
    }

    // private List<Ticket> getTicketByPokeId(int pokeid)
    // {
    //     //Implementation details
    //     return new ArrayList<>();
    // }

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
