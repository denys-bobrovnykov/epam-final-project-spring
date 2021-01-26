package ua.epam.project.spring.movie_theater.entities;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int seatRow;
    @Column(nullable = false)
    private int seatNumber;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "seat_session",
            joinColumns = { @JoinColumn(name = "seat_id") },
            inverseJoinColumns = { @JoinColumn(name = "session_id") }
    )
    Set<Session> sessions = new HashSet<>();

    public Seat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }
}
