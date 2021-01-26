package ua.epam.project.spring.movie_theater.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.epam.project.spring.movie_theater.dbview.FreeSeats;
import ua.epam.project.spring.movie_theater.dbview.IndexTableRow;
import ua.epam.project.spring.movie_theater.entities.Session;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SessionRepository extends PagingAndSortingRepository<Session, Integer> {

    /**
     * Get FreeSeats view for all one day sessions
     * @param dayNumber Weekday number
     * @return List of FreeSeats views
     */
    @Query(value = "select session.id as sessionId, (select count(*) from seat) - COUNT(seat_id) as freeSeats from session left join seat_session ss on session.id = ss.session_id\n" +
            "left join seat s on ss.seat_id = s.id WEEKDAY(where session.day_of_week) = ?\n" +
            "GROUP BY session.id", nativeQuery = true)
    List<FreeSeats> getFreeSeatsForSessions(Integer dayNumber);

    /**
     * Get data for Index with table view in IndexTableRow objects
     * @return rows with columns  => Day, Time, TitleEn, TitleUa, Free Seats
     */
    @Query(value = "select session.id as sessionId, session.day_of_week as dayOfWeek, session.time_start as timeStart, title_en as movieTitleEn, title_ua as movieTitleUa, (select count(*) from seat) - COUNT(seat_id) as freeSeats from session left join seat_session ss on session.id = ss.session_id\n" +
            "    left join seat s on ss.seat_id = s.id\n" +
            "    left join movie m on session.movie_id = m.id\n" +
//            "    GROUP BY session.id ORDER BY ?, ? ASC LIMIT ? OFFSET ?", nativeQuery = true)
            "    GROUP BY session.id ORDER BY dayOfWeek ASC, timeStart ASC LIMIT ? OFFSET ?", nativeQuery = true)
    List<IndexTableRow> getIndexTableViewData(Integer limit, Integer offset);
//    List<IndexTableRow> getIndexTableViewData(String order1, String order2, Integer limit, Integer offset);


    Session getSessionByDayOfWeekAndTimeStart(LocalDate day, LocalTime timeStart);
}
