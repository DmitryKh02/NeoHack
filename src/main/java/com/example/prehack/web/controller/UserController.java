package com.example.prehack.web.controller;

import com.example.prehack.model.User;
import com.example.prehack.service.UserService;
import com.example.prehack.web.dto.UserInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    //region Get methods
    @Operation(summary = "Get User by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long id) {
        log.info("[getUser] >> getAllCompany");

        User user = userService.getUserById(id);

        log.info("[getUser] << result: {}", user);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE).body(user);
    }

    @Operation(summary = "Get info about this user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/user")
    public ResponseEntity<User> getInfoAboutThisUser(Principal principal) {
        log.info("[getInfoAboutThisUser] >> principal: {}", principal.getName());

        User user = userService.getUserByEmail(principal.getName());

        log.info("[getInfoAboutThisUser] << result: {}", user);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE).body(user);
    }

    @Operation(summary = "Get all User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        log.info("[getAllUser] >> getAllCompany");

        List<User> users = userService.getAllUser();

        log.info("[getAllUser] << result: {}", users.size());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE).body(users);
    }
    //endregion

    //region Patch methods

    //endregion

    //region Put methods
    @Operation(summary = "changeAllUserInformation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PutMapping
    public ResponseEntity<User> changeAllUserInformation(Principal principal,
                                                      @Valid @RequestBody UserInfoDTO userInfoDTO) {
        log.info("[changeAllUserInformation] >> userInfoDTO:{}", userInfoDTO);

        User user = userService.fullUpdateUser(principal.getName(), userInfoDTO);

        log.info("[changeAllUserInformation] << result: {}", user);

        return ResponseEntity.ok().body(user);
    }
    //endregion

    //region Delete methods
    @Operation(summary = "delete User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @DeleteMapping
    public ResponseEntity<Boolean> deleteUser(Principal principal) {
        log.info("[deleteUser] >> email: {}", principal.getName());

        userService.deleteUser(principal.getName());

        log.info("[deleteUser] << result: User has been deleted");

        return ResponseEntity.ok().body(true);
    }
    //endregion
}
