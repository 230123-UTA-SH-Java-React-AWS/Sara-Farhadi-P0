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
        String sql = "insert into ticket (userID, ticketID, amount, description, status, processed) values (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
           // prstmt.setString(1, ticket.getUserID());
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

    public List<Ticket> getAllTickets() 
    {
        String sql = "select * from ticket";
        List<Ticket> AllTickets = new ArrayList<Ticket>();
        try (Connection con = ConnectionUtil.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Ticket tickets = new Ticket();
                tickets.setUserID(rs.getString(1));
                tickets.setTicketID(rs.getString(2));
                tickets.setAmount(rs.getInt(3));
                tickets.setDescription(rs.getString(4));
                tickets.setStatus(rs.getString(5));
                tickets.setProcessed(rs.getBoolean(6));
              //  newPokemon.setAbilities(getAbilityByPokeId(newPokemon.getPokeId()));
              AllTickets.add(tickets);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AllTickets;
    }
    
}
