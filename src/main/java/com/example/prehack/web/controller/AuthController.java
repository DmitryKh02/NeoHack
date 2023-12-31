package com.example.prehack.web.controller;

import com.example.prehack.service.UserService;
import com.example.prehack.web.dto.AuthorizationUserDTO;
import com.example.prehack.web.dto.JwtResponseDTO;
import com.example.prehack.web.dto.RegistrationUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @Operation(summary = "enterForNewUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/authentication")
    public ResponseEntity<JwtResponseDTO> enterForNewUser(@RequestBody AuthorizationUserDTO authorizationUserDTO) {
        log.info("[createToken] >> create token for email: {}", authorizationUserDTO.getEmail());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authorizationUserDTO.getEmail(), authorizationUserDTO.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            log.error(badCredentialsException.getMessage());
            throw badCredentialsException;
        }

        String token = userService.createTokenForUser(authorizationUserDTO.getEmail());

        log.info("[createToken] << result is token: {}", token);
        return ResponseEntity.ok().body(new JwtResponseDTO(token));
    }

    @Operation(summary = "createUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/registration")
    public ResponseEntity<JwtResponseDTO> createUser(@RequestBody RegistrationUserDTO registrationUserDTO) {
        log.info("[createUser] >> create user with name: {}", registrationUserDTO.getUserName());

        String token = userService.createUser(registrationUserDTO);

        log.info("[createUser] << result is token");

        return ResponseEntity.ok().body(new JwtResponseDTO(token));
    }
}
