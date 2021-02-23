package ua.project.spring.movie_theater.entities;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Seat entity
 */
@Entity
@Table(name = "seat")
public class Seat {
    @ManyToMany(cascade = {CascadeType.REMOVE})
    @JoinTable(
            name = "seat_session",
            joinColumns = {@JoinColumn(name = "seat_id")},
            inverseJoinColumns = {@JoinColumn(name = "session_id")}
    )
    Set<MovieSession> sessions = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int seatRow;
    @Column(nullable = false)
    private int seatNumber;

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

    public Set<MovieSession> getSessions() {
        return sessions;
    }

    public void setSessions(Set<MovieSession> sessions) {
        this.sessions = sessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (seatRow != seat.seatRow) return false;
        return seatNumber == seat.seatNumber;
    }

    @Override
    public int hashCode() {
        int result = seatRow;
        result = 31 * result + seatNumber;
        return result;
    }
}
