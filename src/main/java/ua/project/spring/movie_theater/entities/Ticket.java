package ua.project.spring.movie_theater.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * Ticket entity
 */
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "movie_session", referencedColumnName = "id")
    private MovieSession movieSession;
    @OneToOne
    @JoinColumn(name = "seat", referencedColumnName = "id")
    private Seat seat;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Ticket() {}

    public Ticket(MovieSession movieSession, User user, Seat seat) {
        this.movieSession = movieSession;
        this.user = user;
        this.seat =seat;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MovieSession getMovieSession() {
        return movieSession;
    }

    public void setMovieSession(MovieSession movieSession) {
        this.movieSession = movieSession;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (!Objects.equals(movieSession, ticket.movieSession))
            return false;
        return Objects.equals(seat, ticket.seat);
    }

    @Override
    public int hashCode() {
        int result = movieSession != null ? movieSession.hashCode() : 0;
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        return result;
    }
}
