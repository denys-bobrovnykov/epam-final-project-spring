package ua.epam.project.spring.movie_theater.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.project.spring.movie_theater.dto.SessionDTO;
import ua.epam.project.spring.movie_theater.dto.StatsDTO;
import ua.epam.project.spring.movie_theater.entities.MovieSession;
import ua.epam.project.spring.movie_theater.exceptions.DBexception;
import ua.epam.project.spring.movie_theater.repositories.MovieSessionRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static ua.epam.project.spring.movie_theater.config.Constants.PAGE_SIZE;
import static ua.epam.project.spring.movie_theater.utils.Utils.getOrdersFromQueryParams;

@Service
public class MovieSessionService {
    private final Logger logger = LogManager.getLogger(MovieSessionService.class);
    private final MovieSessionRepository movieSessionRepository;

    public MovieSessionService(MovieSessionRepository movieSessionRepository) {
        this.movieSessionRepository = movieSessionRepository;
    }


    public MovieSession getSessionByDayTime(LocalDate day, LocalTime time) {
        return movieSessionRepository.getSessionByDayOfSessionAndTimeStart(day, time);
    }

    public MovieSession getSessionFromDbById(Integer id) throws DBexception {
        return movieSessionRepository.findById(id).orElseThrow(() -> new DBexception("error.session.not.exist"));
    }


    public Page<MovieSession> getPage(String sort, String sortDir, Integer page, String keyword) {
        Sort orders = getOrdersFromQueryParams(sort, sortDir);
        Pageable pageRequest = PageRequest.of(page, PAGE_SIZE, orders);
        if (keyword != null) {
            return movieSessionRepository.findAll(keyword, pageRequest);
        }
        return movieSessionRepository.findAll(pageRequest);
    }

    @Transactional
    public MovieSession saveSession(SessionDTO session) throws DBexception {
        // TODO
        // Remove find session
        MovieSession sessionFromDb = getSessionByDayTime(session.getDayOfSession(), session.getTimeStart());
        if (sessionFromDb != null) {
            throw new DBexception("error.session.exists");
        }
        return movieSessionRepository.save(MovieSession.sessionBuilder()
                .dayOfWeek(session.getDayOfSession())
                .timeStart(session.getTimeStart())
                .movie(session.getMovie())
                .build()
        );
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
            logger.error("Could not delete session", ex);
        }
        return true;
    }

    public Long getStats(StatsDTO statsDTO) throws DBexception {
        Long daysCount = Math.abs(statsDTO.getDayEnd().toEpochDay() - statsDTO.getDayStart().toEpochDay());
        try {
            return  daysCount == 0
                    ? movieSessionRepository.countAllSeatsBought(statsDTO.getDayStart(), statsDTO.getDayEnd())
                    : movieSessionRepository.countAllSeatsBought(statsDTO.getDayStart(), statsDTO.getDayEnd()) / daysCount;
        } catch (Exception ex) {
            throw new DBexception("Could not get stats from db", ex);
        }
    }
}
