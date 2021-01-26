package ua.epam.project.spring.movie_theater.dbview;

import java.sql.Date;
import java.sql.Time;

public interface IndexTableRow {
    Integer getSessionId();
    Date getDayOfWeek();
    Time getTimeStart();
    String getMovieTitleEn();
    String getMovieTitleUa();
    Integer getFreeSeats();
}
