package ua.epam.project.spring.movie_theater.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.project.spring.movie_theater.repositories.SeatRepository;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Long getTotalSeats() {
        return seatRepository.count();
    }
}
