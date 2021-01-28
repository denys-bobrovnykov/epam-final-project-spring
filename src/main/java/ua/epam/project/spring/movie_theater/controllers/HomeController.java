package ua.epam.project.spring.movie_theater.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.epam.project.spring.movie_theater.entities.Session;
import ua.epam.project.spring.movie_theater.services.SeatService;
import ua.epam.project.spring.movie_theater.services.SessionService;


import static ua.epam.project.spring.movie_theater.utils.Utils.*;

@Controller
public class HomeController {

    private final SessionService sessionService;
    private final SeatService seatService;

    @Autowired
    public HomeController(SessionService sessionService, SeatService seatService) {
        this.sessionService = sessionService;
        this.seatService = seatService;
    }

    @GetMapping("/home")
    public String homeTableView(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "sort", required = false) String sort, Model model) {
        Sort orders = getOrdersFromQueryParams(sort);
        page = setValueToZeroIfNotProvidedOrNegative(page);
        Page<Session> rows = sessionService.getSessionsPage(page, orders);
        Long totalSeats = seatService.getTotalSeats();
        Long sessionCount = sessionService.getRowsCount();
        Integer pagesCount = getPagesCount((double) sessionCount);
        setPagingAttributes(page, model, rows, pagesCount);
        model.addAttribute("seats_total", totalSeats);
        return "index_table";
    }



}
