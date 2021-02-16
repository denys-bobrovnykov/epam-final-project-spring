package ua.project.spring.movie_theater.entities;

import javax.persistence.*;

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
}
