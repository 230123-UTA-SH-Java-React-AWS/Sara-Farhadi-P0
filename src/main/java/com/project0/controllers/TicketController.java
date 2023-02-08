package com.project0.controllers;
import java.io.IOException;
import com.project0.model.Employee;
import com.project0.model.Ticket;
import com.project0.service.EmployeeService;
import com.project0.service.TicketService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class TicketController implements HttpHandler {

    private final TicketService ts = new TicketService();
    private final ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonDoc;

    @Override
    public void handle(HttpExchange exchange) throws IOException 
    {
        String verb = exchange.getRequestMethod(); 
        switch (verb) {
            case "POST":
                postRequest(exchange);  // Employee user create a new ticket
                break;
            case "GET":
                getRequest(exchange);   // All the pending tickets of all the users
                break;
            case "PUT":
                putProcessTicketRequest(exchange);  // Managers process tickets
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
        OutputStream os = exchange.getResponseBody();
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char)c);
            }
        } 
        exchange.sendResponseHeaders(200, textBuilder.toString().getBytes().length);
        ts.addToTickets(textBuilder.toString());
        os.write(textBuilder.toString().getBytes());
        os.close();
        os.close();
    }

    private void getRequest(HttpExchange exchange) throws IOException 
    {
        String jsonCurrentList = ts.getTickets();
        exchange.sendResponseHeaders(200, jsonCurrentList.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(jsonCurrentList.getBytes());
        os.close();
    }

    private void putProcessTicketRequest(HttpExchange exchange) throws IOException
    {
        String managerEmail;
        String managerPassword;
        String ticketID;
        String status;
        String response = "";
        JsonNode jsonDoc;
        Ticket processTicket = new Ticket();
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
        managerEmail = (jsonDoc.get("userEmail").toString());
        managerEmail = managerEmail.replace("\"", "");
        managerPassword = (jsonDoc.get("userPassword").toString());
        managerPassword = managerPassword.replace("\"", "");
        ticketID = (jsonDoc.get("ticketID").toString());
        ticketID = ticketID.replace("\"", "");
        status = (jsonDoc.get("status").toString());
        status = status.replace("\"", "");
        processTicket = ts.updateTicket(managerEmail, managerPassword, ticketID, status);
        response = mapper.writeValueAsString(processTicket);
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
