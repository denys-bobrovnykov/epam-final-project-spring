package ua.epam.project.spring.movie_theater.entities;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "day_of_week", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayOfWeek;
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

    public Movie getMovie() {
        return movie;
    }

    public Integer getSeatsAvail() {
        return seatsAvail;
    }

    public void setSeatsAvail(Integer seatsBought) {
        this.seatsAvail = seatsBought;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Session() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public static SessionBuilder sessionBuilder() {
        return new SessionBuilder();
    }

    private Session(SessionBuilder builder) {
        this.dayOfWeek = builder.dayOfWeek;
        this.timeStart = builder.timeStart;
        this.movie = builder.movie;
    }

    public static class SessionBuilder {
        private LocalDate dayOfWeek;
        private LocalTime timeStart;
        private Movie movie;

        public Session build() {
            return new Session(this);
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
}
