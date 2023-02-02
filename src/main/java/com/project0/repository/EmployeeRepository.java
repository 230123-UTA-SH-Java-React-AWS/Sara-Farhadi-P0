package com.project0.repository;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.project0.model.Employee;
import com.project0.utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void Save(Employee employee)
    {
        String sql = "insert into employee (employeeEmail, employeePassword) values (?, ?)";
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, employee.getEmail());
            prstmt.setString(2, employee.getPassword());
            prstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getAllEmployee() 
    {
        String sql = "select * from employee";
        List<Employee> listOfEmployee = new ArrayList<Employee>();
        try (Connection con = ConnectionUtil.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Employee newEmployee = new Employee();
                newEmployee.setEmail(rs.getString(1));
                newEmployee.setPassword(rs.getString(2));
                listOfEmployee.add(newEmployee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfEmployee;
    }
    
}
