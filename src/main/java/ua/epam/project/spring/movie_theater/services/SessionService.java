package ua.epam.project.spring.movie_theater.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.project.spring.movie_theater.dto.SessionDTO;
import ua.epam.project.spring.movie_theater.entities.Session;
import ua.epam.project.spring.movie_theater.exceptions.DBexception;
import ua.epam.project.spring.movie_theater.repositories.SessionRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static ua.epam.project.spring.movie_theater.config.Constants.PAGE_SIZE;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }


    public Session getSessionByDayTime(LocalDate day, LocalTime time) {
        return sessionRepository.getSessionByDayOfWeekAndTimeStart(day, time);
    }

    @Transactional
    public Session saveSession(SessionDTO session) throws DBexception {
        Session sessionFromDb = getSessionByDayTime(session.getDayOfWeek(), session.getTimeStart());
        if (sessionFromDb != null) {
            throw new DBexception("error.session.exists");
        }
        return sessionRepository.save(Session.sessionBuilder()
                .dayOfWeek(session.getDayOfWeek())
                .timeStart(session.getTimeStart())
                .movie(session.getMovie())
                .build()
        );
    }

    public Page<Session> getSessionsPage(Integer page, Sort orders) {
        Pageable pageRequest = PageRequest.of(page, PAGE_SIZE, orders);
        return sessionRepository.findAll(pageRequest);
    }


    @Transactional
    public boolean deleteSession(Integer id) throws DBexception {
        Session sessionFromDB = sessionRepository.findById(id).orElse(null);
        if (sessionFromDB == null) {
            throw new DBexception("error.session.not.exist");
        }
        try {
            sessionRepository.deleteById(id);
        } catch (Exception ex) {
            // log
        }
        return true;
    }

    public Long getRowsCount() {
        return sessionRepository.count();
    }
}
