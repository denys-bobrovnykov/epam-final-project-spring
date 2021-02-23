package ua.project.spring.movie_theater.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.project.spring.movie_theater.dto.StatsDTO;
import ua.project.spring.movie_theater.exceptions.DBexception;
import ua.project.spring.movie_theater.message.MessageFactory;
import ua.project.spring.movie_theater.services.MovieService;
import ua.project.spring.movie_theater.dto.MovieDTO;
import ua.project.spring.movie_theater.dto.SessionDTO;
import ua.project.spring.movie_theater.services.MovieSessionService;

import javax.validation.Valid;

/**
 * Admin controller
 */
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
    public String adminPage(MovieDTO movieDTO,
                            StatsDTO statsDTO,
                            SessionDTO sessionDTO,
                            Model model) {
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
