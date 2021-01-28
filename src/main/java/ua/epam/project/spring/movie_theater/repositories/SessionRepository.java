package ua.epam.project.spring.movie_theater.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.epam.project.spring.movie_theater.dbview.TableView;
import ua.epam.project.spring.movie_theater.entities.Session;

import java.time.LocalDate;
import java.time.LocalTime;

public interface SessionRepository extends PagingAndSortingRepository<Session, Integer> {

    Session getSessionByDayOfWeekAndTimeStart(LocalDate day, LocalTime timeStart);

}
