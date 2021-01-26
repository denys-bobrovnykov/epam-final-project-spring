package ua.epam.project.spring.movie_theater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MovieTheaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieTheaterApplication.class, args);
    }

}
