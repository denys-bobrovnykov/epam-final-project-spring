package ua.project.spring.movie_theater.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.spring.movie_theater.entities.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
}
