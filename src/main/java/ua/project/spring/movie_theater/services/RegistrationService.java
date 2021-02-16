package ua.project.spring.movie_theater.services;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.spring.movie_theater.dto.UserDTO;
import ua.project.spring.movie_theater.entities.Role;
import ua.project.spring.movie_theater.entities.User;
import ua.project.spring.movie_theater.exceptions.DBexception;
import ua.project.spring.movie_theater.repositories.UserRepository;

import java.util.Collections;

@Service
public class RegistrationService {

    private final UserRepository userRepository;

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User saveUser(UserDTO userDTO) throws DBexception{
//        TODO make optional
//        chekIfUserExists(userDTO);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String encodedPassword = encoder.encode(userDTO.getPassword());
        return userRepository.save(User.userBuilder()
                .email(userDTO.getEmail())
                .password(encodedPassword)
                .enabled(true)
//                .role(Collections.singleton(Role.USER))
                .role(Role.USER)
                .build());
    }

    private User chekIfUserExists(UserDTO userDTO) throws DBexception{
       return userRepository.findUserByEmail(userDTO.getEmail())
               .orElseThrow(() -> new DBexception("error.register.exists"));
    }
}
