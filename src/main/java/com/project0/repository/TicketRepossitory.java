package com.project0.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.project0.model.Ticket;
import com.project0.utils.ConnectionUtil;

public class TicketRepossitory {

    public void insertTicket(Ticket ticket)
    {
        String sql = "insert into ticket (userEmail, ticketID, amount, description, status, processed) values (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, ticket.getUserEmail());
            prstmt.setString(2, ticket.getTicketID());
            prstmt.setInt(3, ticket.getAmount());
            prstmt.setString(4, ticket.getDescription());
            prstmt.setString(5, ticket.getStatus());
            prstmt.setBoolean(6, ticket.isProcessed());
            prstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Ticket> getAllTickets()    // Get all the pending tickets for managers
    {
        String sql = "select * from ticket where status = 'Pending'";
        List<Ticket> AllTickets = new ArrayList<Ticket>();
        try (Connection con = ConnectionUtil.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Ticket tickets = new Ticket();
                tickets.setUserEmail(rs.getString(1));
                tickets.setTicketID(rs.getString(2));
                tickets.setAmount(rs.getInt(3));
                tickets.setDescription(rs.getString(4));
                tickets.setStatus(rs.getString(5));
                tickets.setProcessed(rs.getBoolean(6));
                AllTickets.add(tickets);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AllTickets;
    }

    public Ticket processTicket(String ticketID, String status)
    {
        Ticket processedTicket = new Ticket();
        String sql = "update ticket set processed = true, status = ? where ticketID = ?";
        if (isProcessed(ticketID) == false) {   // Check if the ticket is already processed
            try (Connection con = ConnectionUtil.getConnection()) {
                PreparedStatement prstmt = con.prepareStatement(sql);
                prstmt.setString(1, status);
                prstmt.setString(2, ticketID);
                ResultSet rs = prstmt.executeQuery();
                rs.close();
                prstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Action Denied");
        }
        processedTicket = findTicket(ticketID);
        return processedTicket;
    }

    public boolean validateManager(String managerEmail, String managerPassword) 
    {
        boolean isManager = false;
        String sql = "select * from employee where userEmail = ?";
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, managerEmail);
            ResultSet rs = prstmt.executeQuery();
            if (!rs.next()) {
                isManager = false;
            }
            else if (managerPassword.equals(rs.getString(2))) {
                isManager = true;
            }
            rs.close();
            prstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isManager;
    }

    private Ticket findTicket(String ticketID)  // Find the ticket by ticket ID
    {
        Ticket foundTicket = new Ticket();
        String sql = "select * from ticket where ticketID = ?";
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, ticketID);
            ResultSet rs = prstmt.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                foundTicket.setUserEmail(rs.getString(1));
                foundTicket.setTicketID(rs.getString(2));
                foundTicket.setAmount(rs.getInt(3));
                foundTicket.setDescription(rs.getString(4));
                foundTicket.setStatus(rs.getString(5));
                foundTicket.setProcessed(rs.getBoolean(6));
            }
            rs.close();
            prstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foundTicket;
    }

    private boolean isProcessed(String ticketID)
    {
        boolean ticketIsProcessed = false;
        Ticket foundTicket = findTicket(ticketID);
        if (foundTicket.isProcessed() == false) {
            ticketIsProcessed = false;
        } else {
            ticketIsProcessed = true;
            System.out.println("Ticket has already been processed");
        }
        return ticketIsProcessed;
    }
    
}
