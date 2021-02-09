package ua.epam.project.spring.movie_theater.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.epam.project.spring.movie_theater.services.TicketService;

@Controller
public class UserCabinetController {
    private final Logger logger = LogManager.getLogger(UserCabinetController.class);
    private final TicketService ticketService;

    public UserCabinetController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/cabinet")
    public String displayCabinet(Model model) {
        try {
            model.addAttribute("tickets", ticketService.getTicketsForCurrentUser());
        } catch (Exception ex) {
            logger.error("Error while getting tickets from DB", ex);
        }
        return "cabinet";
    }
}
