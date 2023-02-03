package com.project0.controllers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
                postRequest(exchange);
                break;
            case "GET":
                getRequest(exchange);
                break;
            case "PUT":
                break;
            case "DELETE":
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
            while ((c = reader.read()) != -1) 
            {
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
        // InputStream is = exchange.getRequestBody();
        // StringBuilder textBuilder = new StringBuilder();
        // try (Reader reader = new BufferedReader(new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.name())))) {
        //     int c = 0;
        //     while ((c = reader.read()) != -1) 
        //     {
        //         textBuilder.append((char)c);
        //     }
        // } 
        // String jsonCurrentUser = serv.getCurrentEmployee();
        // exchange.sendResponseHeaders(200, jsonCurrentUser.getBytes().length);
        // OutputStream os = exchange.getResponseBody();
        // os.write(jsonCurrentUser.getBytes());
        // os.close();
    }

    private void getAllRequest(HttpExchange exchange) throws IOException 
    {
        String jsonCurrentList = serv.getEmployees();
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
