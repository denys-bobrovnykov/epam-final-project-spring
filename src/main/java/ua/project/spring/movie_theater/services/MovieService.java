package ua.project.spring.movie_theater.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.project.spring.movie_theater.dto.MovieDTO;
import ua.project.spring.movie_theater.entities.Movie;
import ua.project.spring.movie_theater.exceptions.DBexception;
import ua.project.spring.movie_theater.repositories.MovieRepository;

import java.util.List;

/**
 * Movie service
 */
@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie saveNewMovie(MovieDTO movie) throws DBexception {
        return movieRepository.saveMovie(Movie.movieBuilder()
                .titleEn(movie.getTitleEn())
                .titleUa(movie.getTitleUa())
                .releaseYear(movie.getReleaseYear())
                .runningTime(movie.getRunningTime())
                .poster(movie.getPoster())
                .build()
        ).orElseThrow(() -> new DBexception("error.movie.exist") );
    }
}
