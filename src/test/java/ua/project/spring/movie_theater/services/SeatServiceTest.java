package ua.project.spring.movie_theater.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ua.project.spring.movie_theater.entities.MovieSession;
import ua.project.spring.movie_theater.entities.Seat;
import ua.project.spring.movie_theater.entities.Ticket;
import ua.project.spring.movie_theater.entities.User;
import ua.project.spring.movie_theater.exceptions.DBexception;
import ua.project.spring.movie_theater.repositories.MovieSessionRepository;
import ua.project.spring.movie_theater.repositories.SeatRepository;
import ua.project.spring.movie_theater.repositories.TicketRepository;
import ua.project.spring.movie_theater.repositories.UserRepository;
import ua.project.spring.movie_theater.utils.Utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

public class SeatServiceTest {
    private final SeatRepository seatRepository = Mockito.mock(SeatRepository.class);
    private final MovieSessionRepository movieSessionRepository = Mockito.mock(MovieSessionRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final TicketRepository ticketRepository = Mockito.mock(TicketRepository.class);
    private final Utils utils = Mockito.mock(Utils.class);

    private final SeatService seatService = new SeatService(
            seatRepository,
            movieSessionRepository,
            ticketRepository,
            userRepository);
    private MovieSession testMovieSession = MovieSession.sessionBuilder().dayOfWeek(LocalDate.of(2020,2,10))
            .timeStart(LocalTime.parse("10:00:00")).build();
    private List<Seat> testSeats;
    private User testUser = User.userBuilder().email("test@mal.com").password("password").enabled(true).build();
    private List<Ticket> testTickets;
    @Before
    public void initData() {
        Seat seat1 = new Seat();
        seat1.setSeatRow(1);
        seat1.setSeatNumber(1);
        Seat seat2 = new Seat();
        seat2.setSeatRow(1);
        seat2.setSeatNumber(2);
        testSeats = Stream.of(seat1, seat2).collect(Collectors.toList());
        Ticket ticket1 = new Ticket();
        ticket1.setSeat(seat1);
        ticket1.setMovieSession(testMovieSession);
        ticket1.setUser(testUser);
        Ticket ticket2 = new Ticket();
        ticket2.setSeat(seat2);
        ticket2.setMovieSession(testMovieSession);
        ticket2.setUser(testUser);
        testTickets = Stream.of(ticket1, ticket2).collect(Collectors.toList());
    }

    @Test
    public void getAllSeatsFromDBTest() throws DBexception {
        Mockito.when(seatRepository.findAll()).thenReturn(testSeats);
        Assert.assertEquals(testSeats, seatService.getAllSeatsFromDB());
    }

    @Test
    public void buySeat() throws DBexception {
        Mockito.when(seatRepository.findAllById(any())).thenReturn(testSeats);
        Mockito.when(movieSessionRepository.findById(any())).thenReturn(Optional.ofNullable(testMovieSession));
        Mockito.when(userRepository.findUserByEmail(any())).thenReturn(Optional.ofNullable(testUser));
        Assert.assertEquals(testSeats, seatService.buySeat(1, new Integer[]{1, 2}, testUser.getEmail()));
    }
}