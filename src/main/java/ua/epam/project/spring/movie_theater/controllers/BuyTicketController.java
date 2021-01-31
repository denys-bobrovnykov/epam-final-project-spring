package ua.epam.project.spring.movie_theater.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.epam.project.spring.movie_theater.entities.Seat;
import ua.epam.project.spring.movie_theater.exceptions.DBexception;
import ua.epam.project.spring.movie_theater.services.SeatService;
import ua.epam.project.spring.movie_theater.services.MovieSessionService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BuyTicketController {
    private final Logger logger = LogManager.getLogger(BuyTicketController.class);
    private final SeatService seatService;
    private final MovieSessionService sessionService;

    public BuyTicketController(SeatService seatService, MovieSessionService sessionService) {
        this.seatService = seatService;
        this.sessionService = sessionService;
    }

    @GetMapping("/buy/{id}")
    public String buyTicketPage(@PathVariable(name = "id") Integer sessionId, Model model) {
        try {
            model.addAttribute("selectedSession", sessionService.getSessionFromDbById(sessionId));
        } catch (DBexception ex) {
            logger.error("Did not get movie session from DB", ex);
        }
        List<Seat> seatsFromDB = seatService.getAllSeatsFromDB();
        model.addAttribute("seatsTotal", seatsFromDB.size());
        model.addAttribute("rows", seatsFromDB.stream().collect(Collectors.groupingBy(item -> item.getSeatRow())));
        return "buyTicket";
    }

    @PostMapping("/buy/{sessionId}")
    public String buyTicket(@PathVariable(name = "sessionId") Integer sessionId,
                            @RequestParam(name = "seatId") Integer[] seatId,
                            RedirectAttributes redirectAttributes) {
        try {
            redirectAttributes.addFlashAttribute("seats", seatService.buySeat(sessionId, seatId));
        } catch (DBexception ex) {
            logger.error("Buy ticket(s) transaction failed", ex);
        }
        return "redirect:/buy/" + sessionId;
    }
}
