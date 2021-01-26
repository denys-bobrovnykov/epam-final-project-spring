package ua.epam.project.spring.movie_theater.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.epam.project.spring.movie_theater.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
}
