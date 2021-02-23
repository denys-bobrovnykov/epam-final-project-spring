package ua.project.spring.movie_theater.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.project.spring.movie_theater.entities.*;
import ua.project.spring.movie_theater.repositories.TicketRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

public class TicketServiceTest {
    private final TicketRepository ticketRepository = Mockito.mock(TicketRepository.class);
    private final TicketService ticketService = new TicketService(ticketRepository);
    private final User user = User.userBuilder().email("test@mail.com").password("password")
            .role(Role.USER).enabled(true).build();

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
    public void getTicketsForCurrentUserTest() {
        Mockito.when(ticketRepository.getAllByUser_Email(user.getEmail())).thenReturn(testTickets);
        Assert.assertEquals(testTickets, ticketService.getTicketsForCurrentUser(user.getEmail()));
    }
}