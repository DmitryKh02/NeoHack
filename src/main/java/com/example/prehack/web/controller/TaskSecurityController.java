package com.example.prehack.web.controller;


import com.example.prehack.model.Task;
import com.example.prehack.service.TaskService;
import com.example.prehack.web.dto.TaskDTO;
import com.example.prehack.web.dto.UserEmailsForProjectDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/secure/tasks")
public class TaskSecurityController {

    private final TaskService taskService;

    //region Get methods
    @Operation(summary = "get Task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable(value = "taskId") Long taskId) {
        log.info("[getTaskById] >> taskId: {}", taskId);

        Task task = taskService.getTaskByIdWithoutProject(taskId);

        log.info("[getTaskById] << result: {}", task);

        return ResponseEntity.ok().body(task);
    }

    @Operation(summary = "getAllTask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTask() {
        log.info("[getAllTask] >> without");

        List<Task> taskList = taskService.getAllTask();

        log.info("[getAllTask] << result: {}", taskList);

        return ResponseEntity.ok().body(taskList);
    }

    @Operation(summary = "getAllTaskInProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<?> getAllTaskInProject(@PathVariable(value = "projectId") Long projectId) {
        log.info("[getAllTaskInProject] >> projectId: {}", projectId);

        List<Task> taskList = taskService.getAllTaskFromProject(projectId);

        log.info("[getAllTaskInProject] << result: {}", taskList);

        return ResponseEntity.ok().body(taskList);
    }

    //endregion

    //region Patch methods
    @Operation(summary = "setTaskToProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PatchMapping("/project/{projectId}/task/{taskId}")
    public ResponseEntity<?> setTaskToProject(@PathVariable(value = "projectId") Long projectId,
                                              @PathVariable(value = "taskId") Long taskId) {
        log.info("[setTaskToProject] >> projectId:{}, taskId:{}", projectId, taskId);

        Task task = taskService.setTaskToProject(projectId, taskId);

        log.info("[setTaskToProject] << result: {}", task);

        return ResponseEntity.ok().body(task);
    }

    @Operation(summary = "changeNameAboutTask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PatchMapping("/task/{taskId}/name")
    public ResponseEntity<?> changeNameAboutTask(@PathVariable(value = "taskId") Long taskId,
                                                 @Valid @RequestBody TaskDTO taskDTO) {
        log.info("[changeNameOfTask] >> taskId:{}, taskDTO: {}", taskId, taskDTO);

        Task task = taskService.setNameForTask(taskId, taskDTO);

        log.info("[changeNameOfTask] << result: {}", task);

        return ResponseEntity.ok().body(task);
    }

    @Operation(summary = "setUserToTask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PatchMapping("/task/{taskId}")
    public ResponseEntity<?> setUserToTask(@PathVariable(value = "taskId") Long taskId,
                                           @Valid @RequestBody UserEmailsForProjectDTO userEmailsForProjectDTO) {
        log.info("[changeTaskInformation] >> taskId:{}, userEmailsForProjectDTO: {}", taskId, userEmailsForProjectDTO);

        Task task = taskService.setUserForTask(taskId, userEmailsForProjectDTO.getEmail());

        log.info("[changeTaskInformation] << result: {}", task);

        return ResponseEntity.ok().body(task);
    }
    //endregion

    //region Put methods
    @Operation(summary = "changeTaskInformation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PutMapping("/task/{taskId}")
    public ResponseEntity<?> changeTaskInformation(@PathVariable(value = "taskId") Long taskId,
                                                   @Valid @RequestBody TaskDTO taskDTO) {
        log.info("[changeTaskInformation] >> taskId:{}, taskDTO: {}", taskId, taskDTO);

        Task task = taskService.setFullInfoForTask(taskId, taskDTO);

        log.info("[changeTaskInformation] << result: {}", task);

        return ResponseEntity.ok().body(task);
    }
    //endregion

    //region Delete methods
    @Operation(summary = "deleteTask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable(value = "taskId") Long taskId) {
        log.info("[deleteTask] >> taskId: {}", taskId);

        taskService.deleteTask(taskId);

        log.info("[deleteTask] << result: task has been deleted");

        return ResponseEntity.ok().body(true);
    }

    //endregion
}
