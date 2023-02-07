package com.project0.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.project0.model.Employee;
import com.project0.model.Ticket;
import com.project0.repository.EmployeeRepository;

public class EmployeeService {

    private final EmployeeRepository repo = new EmployeeRepository();
    private final ObjectMapper mapper = new ObjectMapper();

    public void sendToEmployeeTable(String employeeJson)
    {
        try {
            Employee newEmployee = mapper.readValue(employeeJson, Employee.class);
            repo.registration(newEmployee);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public Employee getCurrentEmployee (Employee currentUser) 
    {
        Employee currentEmployee = repo.login(currentUser);
        return currentEmployee;
    }

    public List<Ticket> filterTickets (String userEmail, String filter)
    {
        List<Ticket> allTickets = new ArrayList<Ticket>();
        allTickets = repo.getFilteredUserTickets(userEmail, filter);
        return allTickets;
    }

}
