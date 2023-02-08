package com.project0.model;
import java.util.ArrayList;
import java.util.List;

public class Employee extends User { 

    private List<Ticket> tickets = new ArrayList<Ticket>();

    public Employee() 
    {
        userRole = "Employee";
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    
}
