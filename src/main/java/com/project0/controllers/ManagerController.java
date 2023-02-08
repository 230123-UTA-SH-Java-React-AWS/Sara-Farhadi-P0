package com.project0.controllers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.codehaus.jackson.map.ObjectMapper;
import com.project0.model.Manager;
import com.project0.service.ManagerService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ManagerController implements HttpHandler {

    private final ManagerService ms = new ManagerService();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException 
    {
        String verb = exchange.getRequestMethod(); 
        switch (verb) {
            case "POST":
                postRequest(exchange);  // Manager User Registration
                break;
            case "GET":
                getRequest(exchange);   // Manager User Login
                break;
            case "PUT":
                getAllRequest(exchange);    // Manager request list of all employee users
                break;
            case "DELETE":
                notSupported(exchange);
                break;
            default:
                notSupported(exchange);
                break;
        }
        System.out.println();
    }

    private void postRequest(HttpExchange exchange) throws IOException 
    {
        InputStream is = exchange.getRequestBody();
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char)c);
            }
        } 
        exchange.sendResponseHeaders(200, textBuilder.toString().getBytes().length);
        ms.sendToEmployeeTable(textBuilder.toString());
        OutputStream os = exchange.getResponseBody();
        os.write(textBuilder.toString().getBytes());
        os.close();
    }

    private void getRequest(HttpExchange exchange) throws IOException 
    {
        String response = "";
        InputStream is = exchange.getRequestBody();
        StringBuilder textBuilder = new StringBuilder();
        OutputStream os = exchange.getResponseBody();
        try (Reader reader = new BufferedReader(new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char)c);
            }
        } 
        Manager userInfo = mapper.readValue(textBuilder.toString(), Manager.class);
        Manager currentUser = ms.getCurrentManager(userInfo);
        if (currentUser == null) {
            response = "Incorrect Email or Password";
            exchange.sendResponseHeaders(404, response.getBytes().length);
            os.write(response.getBytes());
        }
        else if (currentUser != null) {
            response = mapper.writeValueAsString(currentUser);
            exchange.sendResponseHeaders(200, response.getBytes().length);
            os.write(response.getBytes());
        }
        os.close();
    }

    private void getAllRequest(HttpExchange exchange) throws IOException 
    {
        String jsonCurrentList = ms.getEmployees();
        exchange.sendResponseHeaders(200, jsonCurrentList.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(jsonCurrentList.getBytes());
        os.close();
    }
    
    private void notSupported(HttpExchange exchange) throws IOException 
    {
        String noResponse = "HTTP Not Supported";
        exchange.sendResponseHeaders(404, noResponse.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(noResponse.getBytes());
        os.close();
    }

}
