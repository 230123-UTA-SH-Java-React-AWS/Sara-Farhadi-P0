package com.project0.model;

public class Ticket {
    private int ticketID;
    private double amount;
    private String description;
    private String status;
    private boolean processed;
    public static final String[] statusOptions = {"Pending", "Approved", "Denied"};

    public Ticket(){
        //ticketID = null;
        this.status = statusOptions[0];
        this.processed = false;
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

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

}
