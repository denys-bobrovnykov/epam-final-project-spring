package ua.epam.project.spring.movie_theater.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.project.spring.movie_theater.dto.MovieDTO;
import ua.epam.project.spring.movie_theater.entities.Movie;
import ua.epam.project.spring.movie_theater.repositories.MovieRepository;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovie(MovieDTO movie) {
        return movieRepository.getMovieByTitleEn(movie.getTitleEn());
    }

    public void saveNewMovie(Movie newMovie) {
        movieRepository.save(newMovie);
    }
}
