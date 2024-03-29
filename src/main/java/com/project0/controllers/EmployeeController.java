package com.project0.controllers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.project0.model.Employee;
import com.project0.model.Ticket;
import com.project0.service.EmployeeService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class EmployeeController implements HttpHandler {

    private final EmployeeService serv = new EmployeeService();

    @Override
    public void handle(HttpExchange exchange) throws IOException 
    {
        String verb = exchange.getRequestMethod(); 
        switch (verb) {
            case "POST":
                postRequest(exchange);  // Employee User Registration
                break;
            case "GET":
                getRequest(exchange);   // Employee User Login and view all their tickets
                break;
            case "PUT":
                putRequest(exchange);   // Employee User to filter their own tickets by status (Pending, Approved, Denied)
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
        EmployeeService employeeService = new EmployeeService();
        employeeService.sendToEmployeeTable(textBuilder.toString());
        OutputStream os = exchange.getResponseBody();
        os.write(textBuilder.toString().getBytes());
        os.close();
    }

    private void getRequest(HttpExchange exchange) throws IOException 
    {
        String response = "";
        InputStream is = exchange.getRequestBody();
        StringBuilder textBuilder = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();
        OutputStream os = exchange.getResponseBody();
        try (Reader reader = new BufferedReader(new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char)c);
            }
        } 
        Employee userInfo = mapper.readValue(textBuilder.toString(), Employee.class);
        Employee currentUser = serv.getCurrentEmployee(userInfo);
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

    private void putRequest(HttpExchange exchange) throws IOException
    {
        String userEmail;
        String filter;
        String response = "";
        JsonNode jsonDoc;
        List<Ticket> allTickets = new ArrayList<Ticket>();
        InputStream is = exchange.getRequestBody();
        StringBuilder textBuilder = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();
        OutputStream os = exchange.getResponseBody();
        try (Reader reader = new BufferedReader(new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char)c);
            }
        }
        jsonDoc = mapper.readTree(textBuilder.toString()); 
        userEmail = (jsonDoc.get("userEmail").toString());
        userEmail = userEmail.replace("\"", "");
        filter = (jsonDoc.get("filter").toString());
        filter = filter.replace("\"", "");
        allTickets = serv.filterTickets(userEmail, filter);
        response = mapper.writeValueAsString(allTickets);
        exchange.sendResponseHeaders(200, response.getBytes().length);
        os.write(response.getBytes());
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
