package com.example.prehack.web.controller;

import com.example.prehack.model.Project;
import com.example.prehack.model.Task;
import com.example.prehack.service.ProjectService;
import com.example.prehack.service.TaskService;
import com.example.prehack.web.dto.ProjectDTO;
import com.example.prehack.web.dto.TaskDTO;
import com.example.prehack.web.dto.UserEmailsForProjectDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final ProjectService projectService;

    private final TaskService taskService;

    @Operation(summary = "createProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/project")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        log.info("[createProject] >> projectDTO: {}", projectDTO);

        Project project = projectService.createProject(projectDTO);

        log.info("[createProject] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }

    @Operation(summary = "addUserForProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PatchMapping("/project/{projectId}/users")
    public ResponseEntity<?> addUserForProject(@PathVariable(value = "projectId") Long projectId,
                                               @Valid @RequestBody UserEmailsForProjectDTO userEmailsForProjectDTO) {
        log.info("[addUserForProject] >> projectId: {} , userEmailsForProjectDTO: {}", projectId, userEmailsForProjectDTO);

        Project project = projectService.setNewUserForProject(projectId, userEmailsForProjectDTO.getEmail());

        log.info("[addUserForProject] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }

    @Operation(summary = "deleteProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable(value = "projectId") Long projectId) {
        log.info("[deleteProject] >> projectId: {}", projectId);

        projectService.deleteProject(projectId);

        log.info("[getAllCompany] << result: project has been deleted");

        return ResponseEntity.ok().body(true);
    }

    @Operation(summary = "changeProjectInformation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PutMapping("/project/{projectId}")
    public ResponseEntity<?> changeProjectInformation(@PathVariable(value = "projectId") Long projectId,
                                                      @Valid @RequestBody ProjectDTO projectDTO) {
        log.info("[changeProjectInformation] >> projectId:{}, projectDTO: {}", projectId, projectDTO);

        Project project = projectService.updateProject(projectId, projectDTO);

        log.info("[changeProjectInformation] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }

    @Operation(summary = "setTaskToProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PutMapping("/project/{projectId}/task/{taskId}")
    public ResponseEntity<?> setTaskToProject(@PathVariable(value = "projectId") Long projectId,
                                              @PathVariable(value = "taskId") Long taskId) {
        log.info("[setTaskToProject] >> projectId:{}, taskId:{}", projectId, taskId);

        Project project = projectService.setTaskToProject(projectId, taskId);

        log.info("[setTaskToProject] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }


    @Operation(summary = "getAllTaskInProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/project/{projectId}/tasks")
    public ResponseEntity<?> getAllTaskInProject(@PathVariable(value = "projectId") Long projectId) {
        log.info("[getAllTaskInProject] >> projectId: {}",projectId);

        List<Task> taskList = taskService.getAllTaskFromProject(projectId);

        log.info("[getAllTaskInProject] << result: {}", taskList);

        return ResponseEntity.ok().body(taskList);
    }

    @Operation(summary = "getInfoAboutProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getInfoAboutProject(@PathVariable(value = "projectId") Long projectId) {
        log.info("[getAllTaskInProject] >> projectId: {}",projectId);

        Project project = projectService.getProjectById(projectId);

        log.info("[getAllTaskInProject] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }
    @Operation(summary = "getAllTaskFromUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/user/{userId}/tasks")
    public ResponseEntity<?> getAllTaskFromUser(@PathVariable(value = "userId") Long userId) {
        log.info("[getAllTaskFromUser] >> userId: {}",userId);

        List<Task> taskList = taskService.getAllUsersTask(userId);

        log.info("[getAllTaskFromUser] << result: {}", taskList);

        return ResponseEntity.ok().body(taskList);
    }

    @Operation(summary = "changeNameAboutTask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PutMapping("/task/{taskId}/info")
    public ResponseEntity<?> changeInfoAboutTask(@PathVariable(value = "taskId") Long taskId,
                                                 @Valid @RequestBody TaskDTO taskDTO) {
        log.info("[changeNameOfTask] >> taskId:{}, taskDTO: {}", taskId, taskDTO);

        Task task = taskService.changeNameAboutTask(taskId, taskDTO);

        log.info("[changeNameOfTask] << result: {}", task);

        return ResponseEntity.ok().body(task);
    }

    @Operation(summary = "changeTaskInformation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PutMapping("/{taskId}")
    public ResponseEntity<?> changeTaskInformation(@PathVariable(value = "taskId") Long taskId,
                                                   @Valid @RequestBody TaskDTO taskDTO) {
        log.info("[changeTaskInformation] >> taskId:{}, taskDTO: {}", taskId, taskDTO);

        Task task = taskService.updateTask(taskId ,taskDTO);

        log.info("[changeTaskInformation] << result: {}", task);

        return ResponseEntity.ok().body(task);
    }

    @Operation(summary = "setUserForTask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PatchMapping("/task/{taskId}")
    public ResponseEntity<?> setUserForTask(@PathVariable(value = "taskId") Long taskId,
                                            @Valid @RequestBody UserEmailsForProjectDTO userEmailsForProjectDTO) {
        log.info("[changeTaskInformation] >> taskId:{}, userEmailsForProjectDTO: {}", taskId, userEmailsForProjectDTO);

        Task task = taskService.setUserForTask(taskId, userEmailsForProjectDTO.getEmail());

        log.info("[changeTaskInformation] << result: {}", task);

        return ResponseEntity.ok().body(task);
    }

    //TODO ПМ может увиджеть сколько и каких задач сделать пользователь

}
