package ua.epam.project.spring.movie_theater.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.epam.project.spring.movie_theater.entities.MovieSession;
import ua.epam.project.spring.movie_theater.services.MovieSessionService;


import static ua.epam.project.spring.movie_theater.config.Constants.DEFAULT_SORT;
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
                                @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT) String sortParam,
                                @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir,
                                @RequestParam(value = "search", required = false) String keyword,
                                Model model) {
        Page<MovieSession> tablePage = sessionService.getPage(sortParam,
                sortDir, setValueToZeroIfNotProvidedOrNegative(page), keyword);
        setModelParams(page, sortParam, sortDir, model, tablePage, keyword);
        return "index_table";
    }

}
