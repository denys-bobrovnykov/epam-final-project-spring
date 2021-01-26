package ua.epam.project.spring.movie_theater.dto;


import org.springframework.format.annotation.DateTimeFormat;
import ua.epam.project.spring.movie_theater.entities.Movie;

import java.time.LocalDate;
import java.time.LocalTime;

public class SessionDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayOfWeek;
    @DateTimeFormat(pattern = "kk:mm:ss")
    private LocalTime timeStart;
    private Movie movie;

    public LocalDate getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(LocalDate dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "SessionDTO{" +
                "dayOfWeek=" + dayOfWeek +
                ", timeStart=" + timeStart +
                ", movie=" + movie +
                '}';
    }
}
