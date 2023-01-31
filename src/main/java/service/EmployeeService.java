package service;
import java.io.IOException;
import model.Employee;
import repository.EmployeeRepository;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class EmployeeService {
    
    public void login()
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

    public void findEmployee()
    {

    }

}
