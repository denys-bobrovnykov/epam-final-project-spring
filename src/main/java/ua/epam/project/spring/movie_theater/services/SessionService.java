package ua.epam.project.spring.movie_theater.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.epam.project.spring.movie_theater.dbview.IndexTableRow;
import ua.epam.project.spring.movie_theater.entities.Session;
import ua.epam.project.spring.movie_theater.dbview.FreeSeats;
import ua.epam.project.spring.movie_theater.repositories.SessionRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ua.epam.project.spring.movie_theater.config.Constants.PAGE_SIZE;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    // Table view
    public List<IndexTableRow> getTableViewRows(Integer page, String sort) {
//        Integer start = page * PAGE_SIZE;
        Integer end = page * PAGE_SIZE;
        String order1 = null;
        String order2 = null;
        if (sort == null) {
            order1 = "dayOfWeek";
            order2 = "timeStart";
        } else {
            order1 = sort;
            order2 = null;
        }
        return sessionRepository.getIndexTableViewData(PAGE_SIZE, end);
//        return sessionRepository.getIndexTableViewData("dayOfWeek", order2, PAGE_SIZE, end);
    }

    public Session getSessionByDayTime(LocalDate day, LocalTime time) {
        return sessionRepository.getSessionByDayOfWeekAndTimeStart(day, time);
    }

    public void saveSession(Session session) {
        sessionRepository.save(session);
    }

    public List<FreeSeats> getFreeSeatsForSession(Integer day) {
        return sessionRepository.getFreeSeatsForSessions(day);
    }

    public void save(Session session) {
        sessionRepository.save(session);
    }

    public Page<Session> getSessionsPage(Integer page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        return sessionRepository.findAll(pageRequest);
    }

    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }

    public Session findSessionById(Integer id) {
        return sessionRepository.findById(id).orElse(null);
    }

    public Long getRowsCount() {
        return sessionRepository.count();
    }
}
