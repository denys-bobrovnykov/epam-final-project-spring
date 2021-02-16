package ua.project.spring.movie_theater.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.project.spring.movie_theater.entities.Ticket;

import java.util.List;


public interface TicketRepository extends CrudRepository<Ticket, String> {
    List<Ticket> getAllByUser_Email(String email);
}
