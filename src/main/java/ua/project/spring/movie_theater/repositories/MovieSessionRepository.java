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

    Optional<MovieSession> getSessionById(Integer id);

    MovieSession getSessionByDayOfSessionAndTimeStart(LocalDate day, LocalTime timeStart);

    Integer countAllByDayOfSessionBetween(LocalDate dayStart, LocalDate dateEnd);

    @Query(value = "SELECT COUNT(*) FROM seat s JOIN seat_session ss ON s.id = ss.seat_id " +
            "LEFT JOIN movie_session ms ON ms.id = ss.session_id " +
            "WHERE day_of_session BETWEEN ?1 AND ?2", nativeQuery = true)
    Integer countAllSeatsBought(LocalDate dayStart, LocalDate dayEnd);

    @Query("SELECT s FROM MovieSession s WHERE s.movie.titleEn LIKE %?1% " +
            "OR s.movie.titleUa LIKE %?1%")
    Page<MovieSession> findAll(String keyword, Pageable pageRequest);

    @Query(value = "SELECT ms.id as id, " +
            "ms.day_of_session as dayOfSession," +
            "ms.time_start as timeStart," +
            "(m.id, m.title_en, m.title_ua, m.release_year, m.running_time, m.poster) as movie, " +
            "(SELECT COUNT(*) FROM seat) - (SELECT COUNT(*) FROM seat_session ss WHERE ss.session_id = ms.id) AS seatsAvail FROM movie_session ms JOIN movie m ON ms.movie_id = m.id",
            nativeQuery = true,
            countQuery = "SELECT * FROM movie_session"
    )
    Page<MovieSession> findAllTwo(Pageable page);


}
