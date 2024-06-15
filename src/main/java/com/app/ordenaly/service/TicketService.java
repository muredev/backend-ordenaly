package com.app.ordenaly.service;

import com.app.ordenaly.model.Ticket;
import com.app.ordenaly.infra.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class TicketService {
  @Autowired
  private TicketRepository ticketRepository;

  public List<Ticket> getAllTickets() {
    return ticketRepository.findAll();
  }

  public Ticket generateTicket() {
    Ticket ticket = new Ticket();
    ticket.setTime(LocalTime.now());
    return ticketRepository.save( ticket );
  }

  public Ticket getTicketById(int id) {
    return ticketRepository.findById(id).get();
  }

}
