package ua.epam.project.spring.movie_theater.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.epam.project.spring.movie_theater.entities.MovieSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface MovieSessionRepository extends PagingAndSortingRepository<MovieSession, Integer> {

    Optional<MovieSession> getSessionById(Integer id);
    MovieSession getSessionByDayOfSessionAndTimeStart(LocalDate day, LocalTime timeStart);
}
