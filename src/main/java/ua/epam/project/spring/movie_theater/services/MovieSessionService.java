package ua.epam.project.spring.movie_theater.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.project.spring.movie_theater.dto.SessionDTO;
import ua.epam.project.spring.movie_theater.entities.MovieSession;
import ua.epam.project.spring.movie_theater.exceptions.DBexception;
import ua.epam.project.spring.movie_theater.repositories.MovieSessionRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static ua.epam.project.spring.movie_theater.config.Constants.PAGE_SIZE;

@Service
public class MovieSessionService {
    private final MovieSessionRepository movieSessionRepository;

    public MovieSessionService(MovieSessionRepository movieSessionRepository) {
        this.movieSessionRepository = movieSessionRepository;
    }


    public MovieSession getSessionByDayTime(LocalDate day, LocalTime time) {
        return movieSessionRepository.getSessionByDayOfWeekAndTimeStart(day, time);
    }

    public MovieSession getSessionFromDbById(Integer id) {
        return movieSessionRepository.findById(id).orElse(null);
    }

    @Transactional
    public MovieSession saveSession(SessionDTO session) throws DBexception {
        MovieSession sessionFromDb = getSessionByDayTime(session.getDayOfWeek(), session.getTimeStart());
        if (sessionFromDb != null) {
            throw new DBexception("error.session.exists");
        }
        return movieSessionRepository.save(MovieSession.sessionBuilder()
                .dayOfWeek(session.getDayOfWeek())
                .timeStart(session.getTimeStart())
                .movie(session.getMovie())
                .build()
        );
    }

    public Page<MovieSession> getSessionsPage(Integer page, Sort orders) {
        Pageable pageRequest = PageRequest.of(page, PAGE_SIZE, orders);
        return movieSessionRepository.findAll(pageRequest);
    }


    @Transactional
    public boolean deleteSession(Integer id) throws DBexception {
        MovieSession sessionFromDB = movieSessionRepository.findById(id).orElse(null);
        if (sessionFromDB == null) {
            throw new DBexception("error.session.not.exist");
        }
        try {
            movieSessionRepository.deleteById(id);
        } catch (Exception ex) {
            // log
        }
        return true;
    }

    public Long getRowsCount() {
        return movieSessionRepository.count();
    }
}
