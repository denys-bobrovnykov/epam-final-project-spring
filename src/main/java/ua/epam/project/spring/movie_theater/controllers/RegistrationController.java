package ua.epam.project.spring.movie_theater.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.epam.project.spring.movie_theater.dto.UserDTO;
import ua.epam.project.spring.movie_theater.entities.Role;
import ua.epam.project.spring.movie_theater.entities.User;
import ua.epam.project.spring.movie_theater.exceptions.DBexception;
import ua.epam.project.spring.movie_theater.message.MessageFactory;
import ua.epam.project.spring.movie_theater.repositories.UserRepository;
import ua.epam.project.spring.movie_theater.services.RegistrationService;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

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
            return "register";
        }
        try {
            registrationService.saveUser(userDTO);
        } catch (DBexception ex) {
            redirectAttributes.addFlashAttribute("error", ex);
            return "redirect:/register";
        }
        redirectAttributes.addFlashAttribute("success", MessageFactory.getMessage("success.register"));
        return "redirect:/home";
    }

}
