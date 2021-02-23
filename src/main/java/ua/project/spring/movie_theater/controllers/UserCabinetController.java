package ua.project.spring.movie_theater.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.project.spring.movie_theater.services.TicketService;

import static ua.project.spring.movie_theater.utils.Utils.getUserNameFromSecurity;

/**
 * User cabinet page controller
 */
@Controller
public class UserCabinetController {
    private final Logger logger = LogManager.getLogger(UserCabinetController.class);
    private final TicketService ticketService;

    public UserCabinetController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/cabinet")
    public String displayCabinet(Model model) {
        String currentUserEmail = getUserNameFromSecurity();
        try {
            model.addAttribute("tickets", ticketService.getTicketsForCurrentUser(currentUserEmail));
        } catch (Exception ex) {
            logger.error("Error while getting tickets from DB", ex);
        }
        return "cabinet";
    }
}
