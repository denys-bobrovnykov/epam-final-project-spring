package ua.epam.project.spring.movie_theater.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.epam.project.spring.movie_theater.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Movie getMovieByTitleEn(String titleEn);
}
