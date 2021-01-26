package ua.epam.project.spring.movie_theater.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.epam.project.spring.movie_theater.services.MovieService;
import ua.epam.project.spring.movie_theater.dbview.IndexTableRow;
import ua.epam.project.spring.movie_theater.dto.MovieDTO;
import ua.epam.project.spring.movie_theater.dto.SessionDTO;
import ua.epam.project.spring.movie_theater.entities.Movie;
import ua.epam.project.spring.movie_theater.entities.Session;
import ua.epam.project.spring.movie_theater.repositories.MovieRepository;
import ua.epam.project.spring.movie_theater.services.SessionService;

import java.util.List;

import static ua.epam.project.spring.movie_theater.config.Constants.PAGE_SIZE;
import static ua.epam.project.spring.movie_theater.utils.Utils.*;

@Controller
public class AdminController {

    private final SessionService sessionService;
    private final MovieService movieService;

    @Autowired
    public AdminController(MovieRepository movieRepository, SessionService sessionService, MovieService movieService) {
        this.sessionService = sessionService;
        this.movieService = movieService;
    }

    @GetMapping("/admin")
    public String adminPage(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "sort", required = false) String sort, Model model) {
        // Sessions
        page = setValueToZeroIfNotProvidedOrNegative(page);
        List<IndexTableRow> rows = sessionService.getTableViewRows(page, sort);
        Long sessionCount = sessionService.getRowsCount();
        Integer pagesCount = getPagesCount((double) sessionCount);
        setPagingAttributes(page, model, rows, pagesCount);
        // Movies
        List<Movie> movieList;
        movieList = movieService.getAllMovies();
        model.addAttribute("movies", movieList);
        return "adminPage";
    }

    @PostMapping("/admin/sessions")
    public String createNewSession(SessionDTO session, RedirectAttributes redirectAttributes) {
        Session sessionFromDb = sessionService.getSessionByDayTime(session.getDayOfWeek(), session.getTimeStart());
        if (sessionFromDb == null) {
            Session newSession = new Session();
            newSession.setTimeStart(session.getTimeStart());
            newSession.setDayOfWeek(session.getDayOfWeek());
            newSession.setMovie(session.getMovie());
            sessionService.save(newSession);
            redirectAttributes.addAttribute("success", "session created");
            return "redirect:/admin";
        }
        redirectAttributes.addAttribute("error", "exists");
        return "redirect:/admin";
    }

    @PostMapping("/admin/movies")
    public String createNewMovie(MovieDTO movie, RedirectAttributes redirectAttributes) {
        Movie movieFromDB = movieService.getMovie(movie);
        if (movieFromDB == null) {
            Movie newMovie = new Movie();
            newMovie.setTitleEn(movie.getTitleEn());
            newMovie.setTitleUa(movie.getTitleUa());
            newMovie.setReleaseYear(movie.getReleaseYear());
            movieService.saveNewMovie(newMovie);
            redirectAttributes.addAttribute("success", "movie created");
            return "redirect:/admin";
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/sessions/{id}")
    public String deleteSession(@PathVariable("id") Integer id) {
        Session foundSession = sessionService.findSessionById(id);
        sessionService.deleteSession(foundSession);
        return "redirect:/admin";
    }
}
