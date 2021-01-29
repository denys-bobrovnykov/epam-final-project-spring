package ua.epam.project.spring.movie_theater.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.project.spring.movie_theater.entities.Seat;
import ua.epam.project.spring.movie_theater.entities.MovieSession;
import ua.epam.project.spring.movie_theater.exceptions.DBexception;
import ua.epam.project.spring.movie_theater.repositories.SeatRepository;
import ua.epam.project.spring.movie_theater.repositories.MovieSessionRepository;

import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final MovieSessionRepository movieSessionRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository, MovieSessionRepository movieSessionRepository) {
        this.seatRepository = seatRepository;
        this.movieSessionRepository = movieSessionRepository;
    }

    public Long getTotalSeats() {
        return seatRepository.count();
    }

    public List<Seat> getAllSeatsFromDB() {
        return (List<Seat>) seatRepository.findAll();
    }

    @Transactional
    public boolean buySeat(Integer sessionId, Integer seatId) throws DBexception {
        Seat seatFromDb = seatRepository.findById(seatId).orElseThrow(() -> new DBexception("error.not.found"));
        MovieSession sessionFromDb = movieSessionRepository.findById(sessionId).orElseThrow(() -> new DBexception("error.not.found"));
        sessionFromDb.getSeats().add(seatFromDb);
        seatFromDb.getSessions().add(sessionFromDb);
        seatRepository.save(seatFromDb);
        movieSessionRepository.save(sessionFromDb);
        return true;
    }
}
