package com.project0.model;

public class Ticket {
    private int ticketID;
    private int amount;
    private String description;
    private String status;

    public Ticket(){
        //ticketID = null;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAmount() {
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
