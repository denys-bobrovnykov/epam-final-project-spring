package ua.project.spring.movie_theater.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.project.spring.movie_theater.entities.MovieSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Integer>, PagingAndSortingRepository<MovieSession, Integer> {

    @Query(value = "SELECT COUNT(*) FROM seat s JOIN seat_session ss ON s.id = ss.seat_id " +
            "LEFT JOIN movie_session ms ON ms.id = ss.session_id " +
            "WHERE day_of_session BETWEEN ?1 AND ?2", nativeQuery = true)
    Integer countAllSeatsBought(LocalDate dayStart, LocalDate dayEnd);

    @Query("SELECT s FROM MovieSession s WHERE s.movie.titleUa LIKE %?1%" +
            "OR s.movie.titleEn LIKE %?1%")
    Page<MovieSession> findAllByTitle(String value, Pageable pageRequest);

    @Query("SELECT s FROM MovieSession s WHERE s.movie.releaseYear = ?1")
    Page<MovieSession> findAllByReleaseYear(Integer value, Pageable pageRequest);

    @Query("SELECT s FROM MovieSession s")
    Page<MovieSession> findAll(Pageable pageRequest);


}
