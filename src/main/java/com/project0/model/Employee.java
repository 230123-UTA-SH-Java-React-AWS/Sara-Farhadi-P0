package com.project0.model;
import java.util.ArrayList;
import java.util.List;

public class Employee extends User { 

    private ArrayList<Ticket> tickets = new ArrayList<Ticket>();

    public Employee() 
    {
        userRole = "Employee";
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void createTicket(int amount, String description) {
        Ticket newTicket = new Ticket();
        newTicket.setAmount(amount);
        newTicket.setDescription(description);
        this.tickets.add(newTicket);
    }
    
}
