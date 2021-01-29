package ua.epam.project.spring.movie_theater.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.epam.project.spring.movie_theater.entities.MovieSession;
import ua.epam.project.spring.movie_theater.services.SeatService;
import ua.epam.project.spring.movie_theater.services.MovieSessionService;


import static ua.epam.project.spring.movie_theater.utils.Utils.*;

@Controller
public class HomeController {

    private final MovieSessionService sessionService;

    @Autowired
    public HomeController(MovieSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/home")
    public String homeTableView(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                @RequestParam(value = "sort", required = false, defaultValue = "dayOfWeek,timeStart") String sortParam,
                                @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir,
                                Model model) {
        Page<MovieSession> tablePage = sessionService.getPage(sortParam, sortDir, setValueToZeroIfNotProvidedOrNegative(page));
        setModelParams(page, sortParam, sortDir, model, tablePage);
        return "index_table";
    }

}
