package com.project0.model;
import java.text.SimpleDateFormat;

public class Ticket {

    private String userEmail; 
    private String ticketID ;
    private int amount ;
    private String description;
    private String status ;
    private boolean processed;

    public static final String[] statusOptions = {"Pending", "Approved", "Denied"};

    public Ticket(){
        this.status = statusOptions[0];
        this.processed = false;
        ticketID = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
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

    @Override
    public String toString() {
        return "Ticket [Created by User Email: " + userEmail + ", Ticket ID: " + ticketID + ", Amount: " + amount + ", Description: " + description + ", Status: " + status + ", Processed: " + processed + "]";
    }

}
