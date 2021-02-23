package ua.project.spring.movie_theater.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Login page controller
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String displayLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser() {
        return "redirect:/home";
    }
}
