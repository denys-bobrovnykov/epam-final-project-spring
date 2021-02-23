package ua.project.spring.movie_theater.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.spring.movie_theater.dto.SessionDTO;
import ua.project.spring.movie_theater.dto.StatsDTO;
import ua.project.spring.movie_theater.entities.MovieSession;
import ua.project.spring.movie_theater.exceptions.DBexception;
import ua.project.spring.movie_theater.repositories.MovieSessionRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static ua.project.spring.movie_theater.config.Constants.PAGE_SIZE;
import static ua.project.spring.movie_theater.utils.Utils.getOrdersFromQueryParams;


/**
 * Movie session service
 */
@Service
public class MovieSessionService {
    private final Logger logger = LogManager.getLogger(MovieSessionService.class);
    private final MovieSessionRepository movieSessionRepository;

    public MovieSessionService(MovieSessionRepository movieSessionRepository) {
        this.movieSessionRepository = movieSessionRepository;
    }

    public MovieSession getSessionFromDbById(Integer id) throws DBexception {
        return movieSessionRepository.findById(id).orElseThrow(() -> new DBexception("error.session.not.exist"));
    }

    public Page<MovieSession> getPage(String sort, String sortDir, Integer page, String keyword, String value) {
        Sort orders = getOrdersFromQueryParams(sort, sortDir);
        Pageable pageRequest = PageRequest.of(page, PAGE_SIZE, orders);
        if (keyword != null) {
            switch (keyword) {
                case "movie.title": return movieSessionRepository.findAllByTitle(value, pageRequest);
                case "movie.releaseYear": return movieSessionRepository.findAllByReleaseYear(Integer.valueOf(value), pageRequest);
                default: break;
            }
        }
        return movieSessionRepository.findAll(pageRequest);
    }

    public MovieSession saveSession(SessionDTO session) throws DBexception {
        return movieSessionRepository.save(MovieSession.sessionBuilder()
                .dayOfWeek(session.getDayOfSession())
                .timeStart(session.getTimeStart())
                .movie(session.getMovie())
                .build()
        );
    }

    @Transactional
    public boolean deleteSession(Integer id) throws DBexception {
        try {
            movieSessionRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Could not delete session", ex);
            throw new DBexception("error.session.not.exist");
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
