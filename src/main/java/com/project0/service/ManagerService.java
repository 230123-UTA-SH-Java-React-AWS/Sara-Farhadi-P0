package com.project0.service;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.JsonParseException;
import com.project0.model.Employee;
import com.project0.model.Manager;
import com.project0.repository.EmployeeRepository;
import com.project0.repository.ManagerRepossitory;

public class ManagerService {

    private final ManagerRepossitory mr = new ManagerRepossitory();
    private final ObjectMapper mapper = new ObjectMapper();

    public void sendToEmployeeTable(String managerJson)
    {
        try {
            Manager newManager = mapper.readValue(managerJson, Manager.class);
            mr.registration(newManager);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Manager getCurrentManager(Manager currentUser) 
    {
        Manager currentManager = mr.login(currentUser);
        return currentManager;
    }

    public String getEmployees()
    {
        List<Employee> listOfEmployee = mr.getAllEmployee();
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

}
