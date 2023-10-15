package com.example.prehack.web.controller;

import com.example.prehack.model.Role;
import com.example.prehack.repository.RoleRepository;
import com.example.prehack.repository.UserRepository;
import com.example.prehack.service.RoleService;
import com.example.prehack.service.UserService;
import com.example.prehack.web.dto.RegistrationUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        userService.createUser(new RegistrationUserDTO("user1","user1@gmail.com","password"), true);
        userService.createUser(new RegistrationUserDTO("user2","user2@gmail.com","password"), true);
        userService.createUser(new RegistrationUserDTO("user3","user3@gmail.com","password"), true);
        userService.createUser(new RegistrationUserDTO("user4","user4@gmail.com","password"), true);
        userService.createUser(new RegistrationUserDTO("user5","user5@gmail.com","password"), true);
        userService.createUser(new RegistrationUserDTO("user6","user6@gmail.com","password"), true);
        userService.createUser(new RegistrationUserDTO("user7","user7@gmail.com","password"), true);
        userService.createUser(new RegistrationUserDTO("user8","user8@gmail.com","password"), true);
        userService.createUser(new RegistrationUserDTO("user9","user9@gmail.com","password"), true);
        userService.createUser(new RegistrationUserDTO("user10","user10@gmail.com","password"), true);
        userService.createUser(new RegistrationUserDTO("user11","user11@gmail.com","password"), true);
        userService.createUser(new RegistrationUserDTO("user12","user12@gmail.com","password"), true);

        userService.createTester(new RegistrationUserDTO("tester1","tester1@gmail.com","password"));
        userService.createTester(new RegistrationUserDTO("tester2","tester2@gmail.com","password"));
        userService.createTester(new RegistrationUserDTO("tester3","tester3@gmail.com","password"));
        userService.createTester(new RegistrationUserDTO("tester4","tester4@gmail.com","password"));
        userService.createTester(new RegistrationUserDTO("tester5","tester5@gmail.com","password"));
        userService.createTester(new RegistrationUserDTO("tester6","tester6@gmail.com","password"));
        userService.createTester(new RegistrationUserDTO("tester7","tester7@gmail.com","password"));
        userService.createTester(new RegistrationUserDTO("tester8","tester8@gmail.com","password"));
        userService.createTester(new RegistrationUserDTO("tester9","tester9@gmail.com","password"));
        userService.createTester(new RegistrationUserDTO("tester10","tester10@gmail.com","password"));

        userService.createManager(new RegistrationUserDTO("manager1","manager1@gmail.com","password"));
        userService.createManager(new RegistrationUserDTO("manager2","manager2@gmail.com","password"));
        userService.createManager(new RegistrationUserDTO("manager3","manager3@gmail.com","password"));
        userService.createManager(new RegistrationUserDTO("manager4","manager4@gmail.com","password"));

        log.info("[creatAll] << result");

        return ResponseEntity.ok().body(true);
    }
}
