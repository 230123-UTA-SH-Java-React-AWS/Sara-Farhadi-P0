package com.project0.repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.project0.model.Employee;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class EmployeeRepository {

    public void Save(Employee employee){
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
    
}
