package ua.project.spring.movie_theater.services;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ua.project.spring.movie_theater.dto.SessionDTO;
import ua.project.spring.movie_theater.dto.StatsDTO;
import ua.project.spring.movie_theater.entities.Movie;
import ua.project.spring.movie_theater.entities.MovieSession;
import ua.project.spring.movie_theater.exceptions.DBexception;
import ua.project.spring.movie_theater.repositories.MovieSessionRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class MovieSessionServiceTest {

    private final MovieSessionRepository movieSessionRepository = Mockito.mock(MovieSessionRepository.class);
    private final MovieSessionService movieSessionService = new MovieSessionService(movieSessionRepository);
    private final MovieSession movieSession1 = MovieSession.sessionBuilder()
            .dayOfWeek(LocalDate.of(2020, 02, 10))
            .timeStart(LocalTime.parse("10:00:00"))
            .movie(new Movie()).build();
    private final MovieSession movieSession2 = MovieSession.sessionBuilder()
            .dayOfWeek(LocalDate.of(2020, 02, 12))
            .timeStart(LocalTime.parse("10:00:00"))
            .movie(new Movie()).build();

    @Test
    public void getSessionFromDbByIdReturnsSession() throws DBexception {
        Mockito.when(movieSessionRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(movieSession1));
        Assert.assertEquals(movieSession1, movieSessionService.getSessionFromDbById(1));
    }

    @Test(expected = DBexception.class)
    public void throwsExceptionIfNotFound() throws DBexception {
        Mockito.when(movieSessionRepository.findById(1)).thenReturn(java.util.Optional.empty());
        movieSessionService.getSessionFromDbById(1);
    }

    @Test
    public void getPageReturnsPageDefault() {
        Page<MovieSession> movieSessionList = new PageImpl<>(Stream.of(movieSession1, movieSession2).collect(Collectors.toList()));
        Mockito.when(movieSessionRepository.findAll(PageRequest.of(0, 5, Sort.by("sort").ascending()))).thenReturn(movieSessionList);
        Page<MovieSession> expected = movieSessionService.getPage("sort", "asc", 0, null, null);
        Assert.assertEquals(movieSessionList, expected);
    }

    @Test
    public void getPageReturnsPageFilterMovieTitle() {
        Page<MovieSession> movieSessionList = new PageImpl<>(Stream.of(movieSession1, movieSession2).collect(Collectors.toList()));
        Mockito.when(movieSessionRepository.findAllByTitle("test", PageRequest.of(0, 5, Sort.by("sort").ascending()))).thenReturn(movieSessionList);
        Page<MovieSession> expected = movieSessionService.getPage("sort", "asc", 0, "movie.title", "test");
        Assert.assertEquals(movieSessionList, expected);
    }

    @Test
    public void getPageReturnsPageFilterReleaseYear() {
        Page<MovieSession> movieSessionList = new PageImpl<>(Stream.of(movieSession1, movieSession2).collect(Collectors.toList()));
        Mockito.when(movieSessionRepository.findAllByReleaseYear(2020, PageRequest.of(0, 5, Sort.by("sort").ascending()))).thenReturn(movieSessionList);
        Page<MovieSession> expected = movieSessionService.getPage("sort", "asc", 0, "movie.releaseYear", "2020");
        Assert.assertEquals(movieSessionList, expected);
    }

    @Test
    public void saveSession() throws DBexception {
        Movie testMovie = new Movie();
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setDayOfSession(movieSession1.getDayOfSession());
        sessionDTO.setTimeStart(movieSession1.getTimeStart());
        sessionDTO.setMovie(testMovie);
        MovieSession movieSession = MovieSession.sessionBuilder()
                .dayOfWeek(sessionDTO.getDayOfSession())
                .timeStart(sessionDTO.getTimeStart())
                .movie(sessionDTO.getMovie())
                .build();
        Mockito.when(movieSessionRepository.save(movieSession)).thenReturn(movieSession);
        Assert.assertEquals(movieSession, movieSessionService.saveSession(sessionDTO));
    }

    @Test
    public void deleteSessionReturnsTrue() throws DBexception {
        Assert.assertTrue(movieSessionService.deleteSession(1));
    }

    @Test(expected = Exception.class)
    public void deleteSessionThrowsException() throws DBexception {
        Mockito.doThrow(Exception.class).when(movieSessionRepository).deleteById(1);
        movieSessionService.deleteSession(1);
    }

    @Test
    public void getStatsReturnsStats() throws DBexception {
        StatsDTO statsDTO = new StatsDTO();
        statsDTO.setDayStart(movieSession1.getDayOfSession());
        statsDTO.setDayEnd(movieSession2.getDayOfSession());
        Mockito.when(movieSessionRepository.countAllSeatsBought(statsDTO.getDayStart(), statsDTO.getDayEnd())).thenReturn(10);
        assertEquals(5, (long) movieSessionService.getStats(statsDTO));
    }

    @Test(expected = Exception.class)
    public void getStatsThrowsException() throws DBexception {
        StatsDTO statsDTO = new StatsDTO();
        statsDTO.setDayStart(movieSession1.getDayOfSession());
        statsDTO.setDayEnd(movieSession2.getDayOfSession());
        Mockito.when(movieSessionRepository.countAllSeatsBought(statsDTO.getDayStart(), statsDTO.getDayEnd())).thenThrow(Exception.class);
        movieSessionService.getStats(statsDTO);
    }
}