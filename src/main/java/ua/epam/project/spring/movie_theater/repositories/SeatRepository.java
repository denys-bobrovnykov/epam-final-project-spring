package ua.epam.project.spring.movie_theater.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.epam.project.spring.movie_theater.entities.Seat;

public interface SeatRepository extends PagingAndSortingRepository<Seat, Integer> {

}
