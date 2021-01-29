package ua.epam.project.spring.movie_theater.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.epam.project.spring.movie_theater.entities.MovieSession;
import ua.epam.project.spring.movie_theater.message.MessageFactory;
import ua.epam.project.spring.movie_theater.services.MovieSessionService;

@Controller
public class SessionDetails {
    private final MovieSessionService sessionService;

    public SessionDetails(MovieSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/home/details/{id}")
    public String sessionDetails(@PathVariable(name = "id") Integer sessionId,
                                   Model model) {
        MovieSession sessionFromDb = sessionService.getSessionFromDbById(sessionId);
        if( sessionFromDb == null) {
            model.addAttribute("error", MessageFactory.getMessage("error.session.not.exist"));
            return "sessionDetails";
        }
        model.addAttribute("selectedSession", sessionFromDb);
    return "sessionDetails";
    }
}
