package ua.epam.project.spring.movie_theater.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.epam.project.spring.movie_theater.entities.Seat;
import ua.epam.project.spring.movie_theater.entities.MovieSession;
import ua.epam.project.spring.movie_theater.exceptions.DBexception;
import ua.epam.project.spring.movie_theater.message.MessageFactory;
import ua.epam.project.spring.movie_theater.services.SeatService;
import ua.epam.project.spring.movie_theater.services.MovieSessionService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BuyTicketController {
    private final SeatService seatService;
    private final MovieSessionService sessionService;

    public BuyTicketController(SeatService seatService, MovieSessionService sessionService) {
        this.seatService = seatService;
        this.sessionService = sessionService;
    }

    @GetMapping("/buy/{id}")
    public String buyTicketPage(@PathVariable(name = "id") Integer sessionId, Model model) {
        MovieSession sessionFromDb = sessionService.getSessionFromDbById(sessionId);
        if( sessionFromDb == null) {
            model.addAttribute("error", MessageFactory.getMessage("error.session.not.exist"));
            return "sessionDetails";
        }
        List<Seat> seats = seatService.getAllSeatsFromDB();
        model.addAttribute("sessionId", sessionFromDb.getId());
        model.addAttribute("seatsBought", sessionFromDb.getSeats());
        model.addAttribute("rows", seats.stream().collect(Collectors.groupingBy(item -> item.getSeatRow())));
        return "buyTicket";
    }

    @PostMapping("/buy/{sessionId}/{seatId}")
    public String buyTicket(@PathVariable(name = "sessionId") Integer sessionId,
                            @PathVariable(name = "seatId") Integer seatId) {
        try {
            seatService.buySeat(sessionId, seatId);
        } catch (DBexception dBexception) {
            dBexception.printStackTrace();
        }
        return "redirect:/buy/" + sessionId;
    }
}
