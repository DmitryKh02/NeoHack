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
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "NON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getAllTaskInProject(@PathVariable(value = "projectId") Long projectId) {
        log.info("[getAllTaskInProject] >> projectId: {}",projectId);

        List<Task> taskList = taskService.getAllTaskFromProject(projectId);

        log.info("[getAllTaskInProject] << result: {}", taskList);

        return ResponseEntity.ok().body(taskList);
    }

    @Operation(summary = "NON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/{projectId}")
    public ResponseEntity<?> createTask(@PathVariable(value = "projectId") Long projectId,
                                        @Valid @RequestBody TaskDTO taskDTO) {
        log.info("[createTask] >> projectId: {}, taskDTO: {}",projectId, taskDTO);

        Task task = taskService.createTask(taskDTO, projectId);

        log.info("[createTask] << result: {}", task);

        return ResponseEntity.ok().body(task);
    }

    @Operation(summary = "setUserForTask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PatchMapping("/{taskId}")
    public ResponseEntity<?> setUserForTask(@PathVariable(value = "taskId") Long taskId,
                                            @Valid @RequestBody UserEmailsForProjectDTO userEmailsForProjectDTO) {
        log.info("[changeTaskInformation] >> taskId:{}, userEmailsForProjectDTO: {}", taskId, userEmailsForProjectDTO);

        Task task = taskService.setUserForTask(taskId, userEmailsForProjectDTO.getEmail());

        log.info("[changeTaskInformation] << result: {}", task);

        return ResponseEntity.ok().body(task);
    }
    @Operation(summary = "NON")
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
}
