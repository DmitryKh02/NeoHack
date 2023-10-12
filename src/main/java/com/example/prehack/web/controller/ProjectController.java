package com.example.prehack.web.controller;

import com.example.prehack.model.Project;
import com.example.prehack.service.ProjectService;
import com.example.prehack.service.TaskService;
import com.example.prehack.service.UserService;
import com.example.prehack.web.dto.ProjectDTO;
import com.example.prehack.web.dto.UserEmailsForProjectDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "NON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        log.info("[createProject] >> projectDTO: {}", projectDTO);

        Project project = projectService.createProject(projectDTO);

        log.info("[createProject] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }

    @Operation(summary = "NON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PatchMapping("/{projectId}/users")
    public ResponseEntity<?> addUserForProject(@PathVariable(value = "projectId") Long projectId,
                                               @Valid @RequestBody UserEmailsForProjectDTO userEmailsForProjectDTO) {
        log.info("[addUserForProject] >> projectId: {} , userEmailsForProjectDTO: {}", projectId, userEmailsForProjectDTO);

        Project project = projectService.setNewUserForProject(projectId, userEmailsForProjectDTO.getEmail());

        log.info("[addUserForProject] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }

    @Operation(summary = "NON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable(value = "projectId") Long projectId) {
        log.info("[deleteProject] >> projectId: {}", projectId);

        projectService.deleteProject(projectId);

        log.info("[getAllCompany] << result: project has been deleted");

        return ResponseEntity.ok().body(true);
    }

    @Operation(summary = "NON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PutMapping("/{projectId}")
    public ResponseEntity<?> changeProjectInformation(@PathVariable(value = "projectId") Long projectId,
                                                      @Valid @RequestBody ProjectDTO projectDTO) {
        log.info("[changeProjectInformation] >> projectId:{}, projectDTO: {}", projectId, projectDTO);

        Project project = projectService.updateProject(projectId, projectDTO);

        log.info("[changeProjectInformation] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }
}
