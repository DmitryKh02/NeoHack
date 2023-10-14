package com.example.prehack.web.controller;

import com.example.prehack.model.Role;
import com.example.prehack.model.User;
import com.example.prehack.repository.RoleRepository;
import com.example.prehack.repository.UserRepository;
import com.example.prehack.service.RoleService;
import com.example.prehack.service.UserService;
import com.example.prehack.web.dto.RegistrationUserDTO;
import com.example.prehack.web.dto.UserInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import liquibase.pro.packaged.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final RoleRepository roleRepository;
    private final RoleService roleService;

    private final UserRepository userRepository;
    private final UserService userService;

    @Operation(summary = "Set start data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/create")
    public ResponseEntity<?> creatAll() {
        log.info("[creatAll] >> create");

        roleRepository.save(Role.builder().name("ROLE_USER").build());
        roleRepository.save(Role.builder().name("ROLE_TESTER").build());
        roleRepository.save(Role.builder().name("ROLE_MANAGER").build());

        userService.createUser(new RegistrationUserDTO("user1","user1@gmail.com","100"));
        userService.createUser(new RegistrationUserDTO("user2","user2@gmail.com","100"));
        userService.createUser(new RegistrationUserDTO("user3","user3@gmail.com","100"));
        userService.createUser(new RegistrationUserDTO("user4","user4@gmail.com","100"));

        userService.createTester(new RegistrationUserDTO("tester1","tester1@gmail.com","100"));
        userService.createTester(new RegistrationUserDTO("tester2","tester2@gmail.com","100"));
        userService.createTester(new RegistrationUserDTO("tester3","tester3@gmail.com","100"));
        userService.createTester(new RegistrationUserDTO("tester4","tester4@gmail.com","100"));

        userService.createManager(new RegistrationUserDTO("manager1","manager1@gmail.com","100"));
        userService.createManager(new RegistrationUserDTO("manager2","manager2@gmail.com","100"));

        log.info("[creatAll] << result");

        return ResponseEntity.ok().body(true);
    }
}
