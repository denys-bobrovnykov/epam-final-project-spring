package ua.project.spring.movie_theater.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.project.spring.movie_theater.entities.Seat;

/**
 * Seat JPA repository
 */
public interface SeatRepository extends PagingAndSortingRepository<Seat, Integer> {
}
