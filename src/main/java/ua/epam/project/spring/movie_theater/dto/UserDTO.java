package ua.epam.project.spring.movie_theater.dto;

import ua.epam.project.spring.movie_theater.config.Constants;

import javax.validation.constraints.Pattern;

public class UserDTO {
    @Pattern(regexp = Constants.EMAIL_REGEX, message = "error.register.email")
    private String email;
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "error.register.password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                '}';
    }
}
