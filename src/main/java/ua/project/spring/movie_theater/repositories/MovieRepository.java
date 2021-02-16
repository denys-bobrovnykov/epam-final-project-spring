package ua.project.spring.movie_theater.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.spring.movie_theater.entities.Movie;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Optional<Movie> getMovieByTitleEn(String titleEn);
}
