package ua.epam.project.spring.movie_theater.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.project.spring.movie_theater.entities.Ticket;
import ua.epam.project.spring.movie_theater.repositories.TicketRepository;

import java.util.List;

import static ua.epam.project.spring.movie_theater.utils.Utils.getUserNameFromSecurity;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getTicketsForCurrentUser() {
        return ticketRepository.getAllByUser_Email(getUserNameFromSecurity());
    }
}
