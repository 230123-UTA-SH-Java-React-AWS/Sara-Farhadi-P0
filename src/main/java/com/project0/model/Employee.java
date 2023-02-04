package com.project0.model;
import java.util.ArrayList;
import java.util.List;

public class Employee extends User { 

    private ArrayList<Ticket> tickets;

    public Employee() 
    {
        userRole = "Employee";
        this.tickets = new ArrayList<Ticket>();
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void createTicket(double amount, String description) {
        Ticket newTicket;
      //  newTicket.setAmount(amount);
      //  newTicket.setDescription(description);
     //   this.tickets.add(newTicket);
    }
    
}
