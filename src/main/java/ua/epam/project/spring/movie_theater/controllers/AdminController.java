package ua.epam.project.spring.movie_theater.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.epam.project.spring.movie_theater.exceptions.DBexception;
import ua.epam.project.spring.movie_theater.message.MessageFactory;
import ua.epam.project.spring.movie_theater.services.MovieService;
import ua.epam.project.spring.movie_theater.dto.MovieDTO;
import ua.epam.project.spring.movie_theater.dto.SessionDTO;
import ua.epam.project.spring.movie_theater.entities.Movie;
import ua.epam.project.spring.movie_theater.entities.Session;
import ua.epam.project.spring.movie_theater.repositories.MovieRepository;
import ua.epam.project.spring.movie_theater.services.SeatService;
import ua.epam.project.spring.movie_theater.services.SessionService;

import javax.validation.Valid;
import java.util.List;

import static ua.epam.project.spring.movie_theater.utils.Utils.*;

@Controller
public class AdminController {

    private final SessionService sessionService;
    private final MovieService movieService;
    private final SeatService seatService;

    @Autowired
    public AdminController(MovieRepository movieRepository, SessionService sessionService, MovieService movieService, SeatService seatService) {
        this.sessionService = sessionService;
        this.movieService = movieService;
        this.seatService = seatService;
    }

    @GetMapping("/admin")
    public String adminPage(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "sort", required = false) String sort,
                            Model model) {
        // Sessions
        Sort orders = getOrdersFromQueryParams(sort);
        page = setValueToZeroIfNotProvidedOrNegative(page);
        Page<Session> rows = sessionService.getSessionsPage(page, orders);
        Long totalSeats = seatService.getTotalSeats();
        Long sessionCount = sessionService.getRowsCount();
        Integer pagesCount = getPagesCount((double) sessionCount);
        setPagingAttributes(page, model, rows, pagesCount);
        model.addAttribute("seats_total", totalSeats);
        // Movies
        List<Movie> movieList;
        movieList = movieService.getAllMovies();
        model.addAttribute("movies", movieList);
        return "adminPage";
    }

    @PostMapping("/admin/sessions")
    public String createNewSession(@Valid SessionDTO sessionDTO, BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return handleRedirectOnValidation(bindingResult, redirectAttributes);
        }
        try {
            sessionService.saveSession(sessionDTO);
        } catch (DBexception dBexception) {
            return handleException(redirectAttributes, dBexception);
        }
        redirectAttributes.addFlashAttribute("success", MessageFactory.getMessage("session created"));
        return "redirect:/admin";
    }

    @PostMapping("/admin/movies")
    public String createNewMovie(@Valid MovieDTO movie, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return handleRedirectOnValidation(bindingResult, redirectAttributes);
        }
        try {
            movieService.saveNewMovie(movie);
        } catch (DBexception dBexception) {
            return handleException(redirectAttributes, dBexception);
        }
        redirectAttributes.addFlashAttribute("success", MessageFactory.getMessage("success.movie.created"));
            return "redirect:/admin";
    }

    @DeleteMapping("/admin/sessions/{id}")
    public String deleteSession(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            sessionService.deleteSession(id);
        } catch (DBexception dBexception) {
            return handleException(redirectAttributes, dBexception);
        }
        redirectAttributes.addFlashAttribute("success", MessageFactory.getMessage("success.session.deleted"));
        return "redirect:/admin";
    }

    private String handleRedirectOnValidation(BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
        return "redirect:/admin";
    }

    private String handleException(RedirectAttributes redirectAttributes, DBexception dBexception) {
        redirectAttributes.addFlashAttribute("error", dBexception);
        return "redirect:/admin";
    }
}
