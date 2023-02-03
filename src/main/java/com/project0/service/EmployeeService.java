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

    private final EmployeeRepository repo = new EmployeeRepository();
    private final ObjectMapper mapper = new ObjectMapper();
    
    // public void login()
    // {

    // }

    public void findEmployee()
    {

    }

    public void sendToEmployeeTable(String employeeJson)
    {
        try {
            Employee newEmployee = mapper.readValue(employeeJson, Employee.class);
            repo.registration(newEmployee);
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

    public String getEmployees()
    {
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

    // public Employee getCurrentEmployee(String employeeJson) {
    //     Employee currEmployee = repo.login(employeeJson);
    //     String jsonString = "";
    //     return currEmployee;
    // }

}
