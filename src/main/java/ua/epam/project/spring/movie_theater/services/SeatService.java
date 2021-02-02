package ua.epam.project.spring.movie_theater.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.project.spring.movie_theater.entities.Seat;
import ua.epam.project.spring.movie_theater.entities.MovieSession;
import ua.epam.project.spring.movie_theater.entities.Ticket;
import ua.epam.project.spring.movie_theater.entities.User;
import ua.epam.project.spring.movie_theater.exceptions.DBexception;
import ua.epam.project.spring.movie_theater.repositories.SeatRepository;
import ua.epam.project.spring.movie_theater.repositories.MovieSessionRepository;
import ua.epam.project.spring.movie_theater.repositories.TicketRepository;
import ua.epam.project.spring.movie_theater.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ua.epam.project.spring.movie_theater.utils.Utils.getUserNameFromSecurity;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final MovieSessionRepository movieSessionRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository, MovieSessionRepository movieSessionRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.seatRepository = seatRepository;
        this.movieSessionRepository = movieSessionRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public List<Seat> getAllSeatsFromDB() {
        return (List<Seat>) seatRepository.findAll();
    }

    @Transactional
    public List<Seat> buySeat(Integer sessionId, Integer[] seatId) throws DBexception {
        List<Seat> seatsFromDb = (List<Seat>) seatRepository.findAllById(Arrays.asList(seatId));
        MovieSession sessionFromDb = movieSessionRepository.findById(sessionId)
                .orElseThrow(() -> new DBexception("error.not.found"));
        sessionFromDb.getSeats().addAll(seatsFromDb);
        seatsFromDb.forEach(seat -> seat.getSessions().add(sessionFromDb));
        String userName = getUserNameFromSecurity();
        User userFromDb = userRepository.findUserByEmail(userName).orElseThrow(() -> new DBexception("User not found"));
        List<Ticket> tickets = seatsFromDb.stream().map(seat -> new Ticket(sessionFromDb, userFromDb, seat))
                .collect(Collectors.toList());
        ticketRepository.saveAll(tickets);
        seatRepository.saveAll(seatsFromDb);
        movieSessionRepository.save(sessionFromDb);
        return seatsFromDb;
    }

}
