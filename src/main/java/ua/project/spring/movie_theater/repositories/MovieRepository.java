package ua.project.spring.movie_theater.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ua.project.spring.movie_theater.entities.Movie;

import java.util.Optional;

/**
 * Move JPA repository.
 */
public interface MovieRepository extends JpaRepository<Movie, Integer>, CrudRepository<Movie, Integer> {

    /**
     * Created default method to wrap save() return value in Optional
     * @param movie Movie entity
     * @return Optional.of(Movie)
     */
    default Optional<Movie> saveMovie(Movie movie) {
        return Optional.of(this.save(movie));
    }
}
