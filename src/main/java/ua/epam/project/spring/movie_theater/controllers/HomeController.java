package ua.epam.project.spring.movie_theater.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.epam.project.spring.movie_theater.dbview.IndexTableRow;
import ua.epam.project.spring.movie_theater.repositories.MovieRepository;
import ua.epam.project.spring.movie_theater.repositories.SessionRepository;
import ua.epam.project.spring.movie_theater.services.SessionService;

import java.util.List;

import static ua.epam.project.spring.movie_theater.utils.Utils.*;


@Controller
public class HomeController {

    private final SessionService sessionService;

    @Autowired
    public HomeController(MovieRepository movieRepository, SessionRepository sessionRepository, SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/home")
    public String homeTableView(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "sort", required = false) String sort, Model model) {
        page = setValueToZeroIfNotProvidedOrNegative(page);
        List<IndexTableRow> rows = sessionService.getTableViewRows(page, sort);
        Long sessionCount = sessionService.getRowsCount();
        Integer pagesCount = getPagesCount((double) sessionCount);
        setPagingAttributes(page, model, rows, pagesCount);
        return "index_table";
    }

}
