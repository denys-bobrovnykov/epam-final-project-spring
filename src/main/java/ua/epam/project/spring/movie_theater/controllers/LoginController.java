package ua.epam.project.spring.movie_theater.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.epam.project.spring.movie_theater.dto.UserDTO;

import javax.validation.Valid;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String displayLoginPage(UserDTO userDTO) {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid UserDTO userDTO, BindingResult bindingResult) {
        return "redirect:/home";
    }
}
