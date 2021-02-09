package ua.epam.project.spring.movie_theater.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.epam.project.spring.movie_theater.dto.StatsDTO;
import ua.epam.project.spring.movie_theater.exceptions.DBexception;
import ua.epam.project.spring.movie_theater.message.MessageFactory;
import ua.epam.project.spring.movie_theater.services.MovieService;
import ua.epam.project.spring.movie_theater.dto.MovieDTO;
import ua.epam.project.spring.movie_theater.dto.SessionDTO;
import ua.epam.project.spring.movie_theater.entities.Movie;
import ua.epam.project.spring.movie_theater.entities.MovieSession;
import ua.epam.project.spring.movie_theater.services.MovieSessionService;

import javax.validation.Valid;
import java.util.List;

import static ua.epam.project.spring.movie_theater.config.Constants.DEFAULT_SORT;
import static ua.epam.project.spring.movie_theater.utils.Utils.*;

@Controller
public class AdminController {
    private final Logger logger = LogManager.getLogger(AdminController.class);
    private final MovieSessionService sessionService;
    private final MovieService movieService;

    @Autowired
    public AdminController(MovieSessionService sessionService, MovieService movieService) {
        this.sessionService = sessionService;
        this.movieService = movieService;
    }

    @GetMapping("/admin")
    public String adminPage(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                            @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT) String sortParam,
                            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir,
                            @RequestParam(value = "filterBy", required = false) String filterParam,
                            @RequestParam(value = "sort", required = false) String keyword,
                            StatsDTO statsDTO,
                            SessionDTO sessionDTO,
                            MovieDTO movie,
                            Model model) {
        Page<MovieSession> tablePage = sessionService.getPage(sortParam, sortDir, setValueToZeroIfNotProvidedOrNegative(page), filterParam);
        setModelParams(page, sortParam, sortDir, model, tablePage, keyword);
        model.addAttribute("movies", movieService.getAllMovies());
        return "adminPage";
    }

    @PostMapping("/admin/sessions")
    public String createNewSession(@Valid SessionDTO sessionDTO, BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return handleRedirectOnValidation(bindingResult, redirectAttributes);
        }
        try {
            redirectAttributes.addFlashAttribute("successSession", sessionService.saveSession(sessionDTO));
        } catch (DBexception dBexception) {
            return handleException(redirectAttributes, dBexception);
        }
        logger.info("Session created: {}", sessionDTO);
        return "redirect:/admin";
    }

    @PostMapping("/admin/movies")
    public String createNewMovie(@Valid MovieDTO movie, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return handleRedirectOnValidation(bindingResult, redirectAttributes);
        }
        try {
            redirectAttributes.addFlashAttribute("successMovie", movieService.saveNewMovie(movie));
            logger.info("Movie created: {}", movie);
        } catch (DBexception dBexception) {
            return handleException(redirectAttributes, dBexception);
        }
            return "redirect:/admin";
    }

    @DeleteMapping("/admin/sessions/{id}")
    public String deleteSession(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            sessionService.deleteSession(id);
        } catch (DBexception dBexception) {
            return handleException(redirectAttributes, dBexception);
        }
        logger.info("Session deleted id: {}", id);
        redirectAttributes.addFlashAttribute("success", MessageFactory.getMessage("success.session.deleted"));
        return "redirect:/home";
    }

    @GetMapping("/admin/stats")
    public String showStatistics(@Valid StatsDTO statsDTO, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
           return  handleRedirectOnValidation(bindingResult, redirectAttributes);
        }
        try {
            redirectAttributes.addFlashAttribute("stats", sessionService.getStats(statsDTO));
            redirectAttributes.addFlashAttribute("period", statsDTO);
        } catch (DBexception ex) {
            handleException(redirectAttributes, ex);
        }
        return "redirect:/admin";
    }

    private String handleRedirectOnValidation(BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        logger.error("Validation failed {}", bindingResult);
        redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
        return "redirect:/admin";
    }

    private String handleException(RedirectAttributes redirectAttributes, DBexception dBexception) {
        logger.error("Error occurred with exception", dBexception);
        redirectAttributes.addFlashAttribute("error", dBexception);
        return "redirect:/admin";
    }
}
