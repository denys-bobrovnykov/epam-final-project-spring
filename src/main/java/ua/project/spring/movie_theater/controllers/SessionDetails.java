package ua.project.spring.movie_theater.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.project.spring.movie_theater.exceptions.DBexception;
import ua.project.spring.movie_theater.services.MovieSessionService;

/**
 * Session details page controller
 */
@Controller
public class SessionDetails {
    private final Logger logger = LogManager.getLogger(SessionDetails.class);
    private final MovieSessionService sessionService;

    public SessionDetails(MovieSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/home/details/{id}")
    public String sessionDetails(@PathVariable(name = "id") Integer sessionId,
                                   Model model) {
        try {
            model.addAttribute("selectedSession", sessionService.getSessionFromDbById(sessionId));
        } catch (DBexception ex) {
            logger.error("Did not receive movie session from DB", ex);
            model.addAttribute("error", ex);
            return "error";
        }
    return "sessionDetails";
    }
}
