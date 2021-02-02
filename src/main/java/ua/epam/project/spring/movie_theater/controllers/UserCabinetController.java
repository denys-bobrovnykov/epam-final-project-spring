package ua.epam.project.spring.movie_theater.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.epam.project.spring.movie_theater.services.TicketService;

@Controller
public class UserCabinetController {
    private final TicketService ticketService;

    public UserCabinetController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/cabinet")
    public String displayCabinet(Model model) {
        model.addAttribute("tickets", ticketService.getTicketsForCurrentUser());
        return "cabinet";
    }
}
