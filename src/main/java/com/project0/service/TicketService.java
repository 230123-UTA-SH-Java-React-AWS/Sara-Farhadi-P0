package com.project0.service;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.project0.model.Ticket;
import com.project0.repository.TicketRepossitory;

public class TicketService {
    
    private final TicketRepossitory tr = new TicketRepossitory();
    private final ObjectMapper mapper = new ObjectMapper();

    public void addToTickets(String ticketJson)
    {
        try {
            Ticket newTicket = mapper.readValue(ticketJson, Ticket.class);
            tr.insertTicket(newTicket);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTickets()
    {
        List<Ticket> listOfTickets = tr.getAllTickets();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(listOfTickets);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public Ticket updateTicket(String managerEmail, String managerPassword, String ticketID, String status)
    {
        Ticket processedTicket = new Ticket();
        boolean managerValidation = tr.validateManager(managerEmail,managerPassword);
        if (managerValidation == true) {    // Check if Manager exist and password is correct
            processedTicket = tr.processTicket(ticketID, status);
            System.out.println("Ticket Processed");
        } else {
            System.out.println("Unable to process ticket");
        }
        return processedTicket;
    }

}
