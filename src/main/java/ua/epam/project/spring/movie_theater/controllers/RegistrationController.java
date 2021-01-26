package ua.epam.project.spring.movie_theater.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.epam.project.spring.movie_theater.dto.UserDTO;
import ua.epam.project.spring.movie_theater.entities.Role;
import ua.epam.project.spring.movie_theater.entities.User;
import ua.epam.project.spring.movie_theater.repositories.UserRepository;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String registerPage(UserDTO userDTO) {
        return "register";
    }

    @PostMapping
    public String addUser(@Valid UserDTO userDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes
                         ) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        User userFromDb = userRepository.findUserByEmail(userDTO.getEmail());
        if (userFromDb != null) {
            redirectAttributes.addAttribute("error", "error.register.exists");
            return "redirect:register";
        }
        User newUser = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12
        String encodedPassword = encoder.encode(userDTO.getPassword());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setRoles(Collections.singleton(Role.USER));
        newUser.setEnabled(true);
        userRepository.save(newUser);
        redirectAttributes.addFlashAttribute("success", "success.register");
        return "redirect:/home";
    }

}
