package com.example.prehack.service.impl;

import com.example.prehack.exception.ResourceNotFoundException;
import com.example.prehack.exception.UserAlreadyExistException;
import com.example.prehack.mapper.UserMapper;
import com.example.prehack.model.User;
import com.example.prehack.repository.UserRepository;
import com.example.prehack.service.RoleService;
import com.example.prehack.utils.JwtTokenUtils;
import com.example.prehack.web.dto.RegistrationUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    JwtTokenUtils jwtTokenUtils;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;
    @Mock
    RoleService roleService;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void getUserByEmail_withExistingEmail() {
        String email = random(String.class);
        User user = User.builder().build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));

        User userByEmail = userService.getUserByEmail(email);
        assertEquals(user, userByEmail);
    }

    @Test
    void getUserByEmail_withNotExistingEmail() {
        String email = random(String.class);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByEmail(email));
    }


    @Test
    void getUserByName_withExistingName() {
        String name = random(String.class);
        User user = User.builder().build();

        when(userRepository.findByUserName(name)).thenReturn(Optional.ofNullable(user));

        User userByName = userService.getUserByName(name);
        assertEquals(user, userByName);
    }

    @Test
    void getUserByName_withNotExistingName() {
        String name = random(String.class);

        when(userRepository.findByUserName(name)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.getUserByName(name));
    }

    @Test
    void getUserById_withExistingId() {
        Long id = random(Long.class);
        User user = User.builder().build();

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));

        User userByName = userService.getUserById(id);
        assertEquals(user, userByName);
    }

    @Test
    void getUserById_withNotExistingId() {
        Long id = random(Long.class);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(id));
    }

    @Test
        //TODO не работает
    void createUser_whenUserAlreadyExists() {
        RegistrationUserDTO dto = RegistrationUserDTO.builder()
                .email(random(String.class))
                .build();

        when(userRepository.findByEmail(any(String.class)).isPresent()).thenReturn(true);

        assertThrows(UserAlreadyExistException.class, () -> userService.createUser(dto));
    }

    @Test
    void setUserToSecurityAndCreateToken() {
        //TODO untestable
    }

    @Test
    void fullUpdateUser() {
        //TODO untestable
    }

    @Test
    void updateSomeUserInfo() {
        //TODO untestable
    }

    @Test
    void deleteUser() {
        //TODO untestable
    }

    @Test
    void getAllUser() {
        User user1 = User.builder().build();
        User user2 = User.builder().build();
        List<User> users = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        userService.getAllUser();
    }

    @Test
    void userEmailConfirmation() {
        //TODO method to test is not implemented
    }

    @Test
    void createSesCode() {
        //TODO method to test is not implemented
    }

    @Test
    void loadUserByUsername() {
        //TODO untestable
    }
}