package ua.project.spring.movie_theater.services;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ua.project.spring.movie_theater.dto.MovieDTO;
import ua.project.spring.movie_theater.entities.Movie;
import ua.project.spring.movie_theater.exceptions.DBexception;
import ua.project.spring.movie_theater.repositories.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MovieServiceTest {
    private final MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
    private final MovieService movieService = new MovieService(movieRepository);
    private final Movie movie1 = Movie.movieBuilder().titleEn("Movie 1").titleUa("Фільм 1")
            .releaseYear(2020)
            .runningTime(120)
            .poster("poster.jpg").build();
    private final Movie movie2 = Movie.movieBuilder().titleEn("Movie 2").titleUa("Фільм 2")
            .releaseYear(2020)
            .runningTime(120)
            .poster("poster.jpg").build();
    private final List<Movie> testMovies = Stream.of(movie1, movie2).collect(Collectors.toList());

    @Test
    public void getAllMovies() {
        Mockito.when(movieRepository.findAll()).thenReturn(testMovies);
        Assert.assertTrue(movieService.getAllMovies().containsAll(testMovies));
    }

    @Test
    public void savesNewMovie() throws DBexception {
        Mockito.when(movieRepository.saveMovie(movie1)).thenReturn(java.util.Optional.of(movie1));
        MovieDTO movie1DTO = new MovieDTO();
        movie1DTO.setTitleEn(movie1.getTitleEn());
        movie1DTO.setTitleUa(movie1.getTitleUa());
        movie1DTO.setReleaseYear(movie1.getReleaseYear());
        Assert.assertEquals(movie1, movieService.saveNewMovie(movie1DTO));
    }

    @Test(expected = DBexception.class)
    public void throwsExceptionIfNotSaved() throws DBexception {
        Mockito.when(movieRepository.saveMovie(movie1)).thenReturn(java.util.Optional.empty());
        MovieDTO movie1DTO = new MovieDTO();
        movie1DTO.setTitleEn(movie1.getTitleEn());
        movie1DTO.setTitleUa(movie1.getTitleUa());
        movie1DTO.setReleaseYear(movie1.getReleaseYear());
        movieService.saveNewMovie(movie1DTO);
    }
}