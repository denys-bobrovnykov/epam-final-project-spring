package ua.epam.project.spring.movie_theater.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MovieController {
    @GetMapping("/home/movies/{id}")
    public String movieDetailsPage(@PathVariable(name = "id") Integer movieId,
                                   Model model) {
        model.addAttribute("movie_id", movieId);
    return "movieDetails";
    }
}
