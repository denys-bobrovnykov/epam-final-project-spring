package ua.project.spring.movie_theater.entities;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Movie session entity
 */
@Entity
@Table(name = "movie_session")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "day_of_session", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayOfSession;
    @DateTimeFormat(pattern = "kk:mm:ss")
    @Column(name = "time_start", nullable = false)
    private LocalTime timeStart;
    @ManyToMany(mappedBy = "sessions")
    private Set<Seat> seats = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
    @Column(name = "seats_available", columnDefinition = "INT NOT NULL DEFAULT 0")
    private Integer seatsAvail;

    public MovieSession() {
    }

    private MovieSession(SessionBuilder builder) {
        this.dayOfSession = builder.dayOfWeek;
        this.timeStart = builder.timeStart;
        this.movie = builder.movie;
    }

    public static SessionBuilder sessionBuilder() {
        return new SessionBuilder();
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getSeatsAvail() {
        return seatsAvail;
    }

    public void setSeatsAvail(Integer seatsBought) {
        this.seatsAvail = seatsBought;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDayOfSession() {
        return dayOfSession;
    }

    public void setDayOfSession(LocalDate dayOfWeek) {
        this.dayOfSession = dayOfWeek;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public static class SessionBuilder {
        LocalDate dayOfWeek;
        LocalTime timeStart;
        Movie movie;

        public MovieSession build() {
            return new MovieSession(this);
        }

        public SessionBuilder dayOfWeek(LocalDate dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public SessionBuilder timeStart(LocalTime timeStart) {
            this.timeStart = timeStart;
            return this;
        }

        public SessionBuilder movie(Movie movie) {
            this.movie = movie;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieSession that = (MovieSession) o;

        if (!Objects.equals(dayOfSession, that.dayOfSession)) return false;
        return Objects.equals(timeStart, that.timeStart);
    }

    @Override
    public int hashCode() {
        int result = dayOfSession != null ? dayOfSession.hashCode() : 0;
        result = 31 * result + (timeStart != null ? timeStart.hashCode() : 0);
        return result;
    }
}
