import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import com.sun.net.httpserver.HttpServer;
import controllers.EmployeeController;
import model.Employee;
import repository.EmployeeRepository;
import com.sun.net.httpserver.HttpHandler;

public final class App {
    private App() {
    }
    public static void main(String[] args) throws IOException {


        // Employee ann = new Employee("abc@ab.com", 123);
        // Employee amy = new Employee("abd@ab.com", 143);
        // List<Employee> emps = new ArrayList<Employee>();
        // emps.add(ann);
        // emps.add(amy);
        // System.out.println(amy.getEmployeeEmail());
        // System.out.println(emps);

        System.out.println("Starting backend server...");
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/employee", new EmployeeController());
        server.setExecutor(null);
        server.start();
    }
}
