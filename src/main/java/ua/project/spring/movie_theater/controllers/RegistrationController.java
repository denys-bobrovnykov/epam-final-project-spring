package ua.project.spring.movie_theater.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.project.spring.movie_theater.dto.UserDTO;
import ua.project.spring.movie_theater.message.MessageFactory;
import ua.project.spring.movie_theater.services.RegistrationService;

import javax.validation.Valid;

/**
 * Registration page controller
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final Logger logger = LogManager.getLogger(RegistrationController.class);

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String registerPage(UserDTO userDTO) {
        return "register";
    }

    @PostMapping
    public String addUser(@Valid UserDTO userDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            logger.warn("Registration validation failed {}", bindingResult);
            return "register";
        }
        try {
            registrationService.saveUser(userDTO);
        } catch (Exception ex) {
            logger.error("Error saving user in DB", ex);
            redirectAttributes.addFlashAttribute("error", MessageFactory.getMessage("error.register.exists"));
            redirectAttributes.addFlashAttribute("emailRedirect", userDTO.getEmail());
            return "redirect:/register";
        }
        redirectAttributes.addFlashAttribute("regSuccess", MessageFactory.getMessage("success.register"));
        return "redirect:/home";
    }

}
