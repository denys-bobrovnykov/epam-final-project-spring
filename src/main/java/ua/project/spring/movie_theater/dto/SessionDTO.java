package ua.project.spring.movie_theater.dto;


import org.springframework.format.annotation.DateTimeFormat;
import ua.project.spring.movie_theater.dto.validators.StartTimeConstraint;
import ua.project.spring.movie_theater.entities.Movie;
import ua.project.spring.movie_theater.entities.Seat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class SessionDTO implements Serializable {
    @NotNull(message = "must.be.present.date")
    @FutureOrPresent(message = "alert.field.past.today")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayOfSession;
    @NotNull(message = "must.be.present.time")
    @DateTimeFormat(pattern = "kk:mm:ss")
    @StartTimeConstraint(message = "error.field.start.time")
    private LocalTime timeStart;
    @NotNull(message = "must.be.present.movie")
    private Movie movie;
    private Set<Seat> seats;
    private long seatsBought;
    private String movieTitleEn;
    private String movieTitleUa;

    public SessionDTO() {
    }


    public String getMovieTitleEn() {
        return movieTitleEn;
    }

    public void setMovieTitleEn(String movieTitleEn) {
        this.movieTitleEn = movieTitleEn;
    }

    public String getMovieTitleUa() {
        return movieTitleUa;
    }

    public void setMovieTitleUa(String movieTitleUa) {
        this.movieTitleUa = movieTitleUa;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public long getSeatsBought() {
        return seatsBought;
    }

    public void setSeatsBought(long seatsBought) {
        this.seatsBought = seatsBought;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public LocalDate getDayOfSession() {
        return dayOfSession;
    }

    public void setDayOfSession(LocalDate dayOfSession) {
        this.dayOfSession = dayOfSession;
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
                "dayOfWeek=" + dayOfSession +
                ", timeStart=" + timeStart +
                ", movie=" + movie +
                '}';
    }
}
