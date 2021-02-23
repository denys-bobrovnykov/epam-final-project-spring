package ua.project.spring.movie_theater.services;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ua.project.spring.movie_theater.dto.UserDTO;
import ua.project.spring.movie_theater.entities.Role;
import ua.project.spring.movie_theater.entities.User;
import ua.project.spring.movie_theater.repositories.UserRepository;


public class RegistrationServiceTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final RegistrationService registrationService = new RegistrationService(userRepository);

    @Test
    public void saveUserReturnsNewUser() {
        User user = User.userBuilder()
                .email("new@email.com")
                .password("password")
                .enabled(true)
                .role(Role.USER).build();
        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        Assert.assertEquals(user, registrationService.saveUser(userDTO));
    }
    @Test(expected = Exception.class)
    public void saveUserThrowsErrorOnSave() {
        User user = User.userBuilder()
                .email("new@email.com")
                .password("password")
                .enabled(true)
                .role(Role.USER).build();
        Mockito.when(userRepository.save(user)).thenThrow(new Exception("error"));
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        registrationService.saveUser(userDTO);
    }
}