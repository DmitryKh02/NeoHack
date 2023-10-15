package com.example.prehack.web.controller;

import com.example.prehack.mapper.ProjectMapper;
import com.example.prehack.model.Project;
import com.example.prehack.model.Task;
import com.example.prehack.service.ProjectService;
import com.example.prehack.web.dto.ProjectDTO;
import com.example.prehack.web.dto.ReqProject;
import com.example.prehack.web.dto.ReqTask;
import com.example.prehack.web.dto.UserEmailsForProjectDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/secure/projects")
public class ProjectSecurityController {

    private final ProjectService projectService;

    private final ProjectMapper projectMapper;
    //region Get methods
    @Operation(summary = "getAllProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping
    public ResponseEntity<List<ReqProject>> getAllProject() {
        log.info("[getAllProject] >> without");

        List<Project> projects = projectService.getAllProject();
        List<ReqProject> reqProjects = new LinkedList<>();

        for (Project project : projects){
            reqProjects.add(projectMapper.projectDTOToProject(project));
        }

        log.info("[getAllProject] << result: {}", reqProjects);

        return ResponseEntity.ok().body(reqProjects);
    }
    //endregion

    //region Post method
    @Operation(summary = "createProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping
    public ResponseEntity<Project> createProject(Principal principal,
                                           @Valid @RequestBody ProjectDTO projectDTO) {
        log.info("[createProject] >> projectDTO: {}", projectDTO);

        Project project = projectService.createProject(principal.getName(), projectDTO);

        log.info("[createProject] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }
    //endregion

    //region Patch methods
    @Operation(summary = "setUserToProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PatchMapping("/{projectId}/user")
    public ResponseEntity<Project> setUserToProject(@PathVariable(value = "projectId") Long projectId,
                                              @Valid @RequestBody UserEmailsForProjectDTO userEmailsForProjectDTO) {
        log.info("[addUserForProject] >> projectId: {} , userEmailsForProjectDTO: {}", projectId, userEmailsForProjectDTO);

        Project project = projectService.setNewUserForProject(projectId, userEmailsForProjectDTO.getEmail());

        log.info("[addUserForProject] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }
    //endregion

    //region Put methods
    @Operation(summary = "changeProjectInformation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PutMapping("/{projectId}")
    public ResponseEntity<Project> changeProjectInformation(@PathVariable(value = "projectId") Long projectId,
                                                      @Valid @RequestBody ProjectDTO projectDTO) {
        log.info("[changeProjectInformation] >> projectId:{}, projectDTO: {}", projectId, projectDTO);

        Project project = projectService.updateProject(projectId, projectDTO);

        log.info("[changeProjectInformation] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }
    //endregion

    //region Delete methods
    @Operation(summary = "deleteProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Boolean> deleteProject(@PathVariable(value = "projectId") Long projectId) {
        log.info("[deleteProject] >> projectId: {}", projectId);

        projectService.deleteProject(projectId);

        log.info("[getAllCompany] << result: project has been deleted");

        return ResponseEntity.ok().body(true);
    }
    //endregion
}
