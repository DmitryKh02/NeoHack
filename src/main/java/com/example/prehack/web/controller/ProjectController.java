package com.example.prehack.web.controller;

import com.example.prehack.model.Project;
import com.example.prehack.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    //region Get methods
    @Operation(summary = "getInfoAboutProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getInfoAboutProject(@PathVariable(value = "projectId") Long projectId) {
        log.info("[getAllTaskInProject] >> projectId: {}", projectId);

        Project project = projectService.getProjectById(projectId);

        log.info("[getAllTaskInProject] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }

    @Operation(summary = "getAllProjectForUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/user")
    public ResponseEntity<?> getAllProjectForUser(Principal principal) {
        log.info("[getAllProject] >> without");

        List<Project> projects = projectService.getAllProjectForUserByEmail(principal.getName());

        log.info("[getAllProject] << result: {}", projects);

        return ResponseEntity.ok().body(projects);
    }

    //endregion

    //region Post methods

    //endregion

    //region Patch methods

    //endregion

    //region Put methods

    //endregion

}
