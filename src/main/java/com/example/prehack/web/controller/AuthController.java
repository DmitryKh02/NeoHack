package com.example.prehack.web.controller;

import com.example.prehack.model.User;
import com.example.prehack.service.UserService;
import com.example.prehack.web.dto.AuthorizationUserDTO;
import com.example.prehack.web.dto.JwtResponseDTO;
import com.example.prehack.web.dto.RegistrationUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    //TODO веб документация
    @PostMapping("/authentication")
    public ResponseEntity<JwtResponseDTO> enterForNewUser(@RequestBody AuthorizationUserDTO authorizationUserDTO) {
        log.info("[createToken] >> create token for email: {}", authorizationUserDTO.getEmail());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authorizationUserDTO.getEmail(), authorizationUserDTO.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            log.error(badCredentialsException.getMessage());
            throw badCredentialsException;
        }

        String token = userService.setUserToSecurityAndCreateToken(authorizationUserDTO.getEmail());

        log.info("[createToken] << result is token");
        return ResponseEntity.ok().body(new JwtResponseDTO(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<JwtResponseDTO> createUser(@RequestBody RegistrationUserDTO registrationUserDTO) {
        log.info("[createUser] >> create user with name: {}", registrationUserDTO.getUserName());

        String token = userService.createUser(registrationUserDTO);

        log.info("[createUser] << result is token");

        return ResponseEntity.ok().body(new JwtResponseDTO(token));
    }
}
