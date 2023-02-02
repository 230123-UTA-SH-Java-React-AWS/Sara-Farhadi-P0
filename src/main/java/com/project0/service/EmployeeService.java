package com.project0.service;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.project0.model.Employee;
import com.project0.repository.EmployeeRepository;

public class EmployeeService {
    
    public void login()
    {

    }

    public void findEmployee()
    {

    }

    public void saveToEmployeeBox(String employeeJson)
    {
        EmployeeRepository repo = new EmployeeRepository();
        ObjectMapper mapper = new ObjectMapper();
        try {
            Employee newEmployee = mapper.readValue(employeeJson, Employee.class);
            repo.Save(newEmployee);
        } catch (JsonParseException e) {
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

    public String getAllEmployee()
    {
        EmployeeRepository repo = new EmployeeRepository();
        List<Employee> listOfEmployee = repo.getAllEmployee();
        ObjectMapper map = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = map.writeValueAsString(listOfEmployee);

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
        return jsonString;
    }

}
