package com.project0;
import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.project0.controllers.EmployeeController;
import com.project0.controllers.ManagerController;
import com.project0.controllers.TicketController;
import com.sun.net.httpserver.HttpHandler;

public final class App {
    private App() {}

    public static void main(String[] args) throws IOException 
    {
        System.out.println("Starting backend server...");
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/manager", new ManagerController());
        server.createContext("/employee", new EmployeeController());
        server.createContext("/ticket", new TicketController());
        server.setExecutor(null);
        server.start();
    }
}
