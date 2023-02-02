package com.project0.model;

public class Ticket {
    private int ticketID;
    private double amount;
    private String description;
    private String status;

    public Ticket(){
        //ticketID = null;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public int getTicketID() {
        return ticketID;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

}
