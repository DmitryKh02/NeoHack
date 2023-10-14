package com.example.prehack.web.controller;

import com.example.prehack.model.Task;
import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.enumformodel.Status;
import com.example.prehack.service.TaskService;
import com.example.prehack.web.dto.TaskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final TaskService taskService;

    @Operation(summary = "createTask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/task")
    public ResponseEntity<?> createTask(Principal principal,
                                        @Valid @RequestBody TaskDTO taskDTO) {
        log.info("[createTask] >> taskDTO: {}", taskDTO);

        //В principal хранится почта пользователя(данные из JWT) principal.getName();
        Task task = taskService.createTask(taskDTO, principal.getName());

        log.info("[createTask] << result: {}", task);

        return ResponseEntity.ok().body(task);
    }

    @Operation(summary = "updateStatusForTask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<?> updateStatusForTask(Principal principal,
                                                 @PathVariable(value = "taskId") Long taskId,
                                                 @RequestParam(value = "status") Status status) {
        log.info("[updateStatusForTask] >> taskId: {}, status: {}", taskId, status);

        Task task = taskService.setStatusForTask(principal.getName(), taskId, status);

        log.info("[updateStatusForTask] << result: {}", task);

        return ResponseEntity.ok().body(task);
    }

    @Operation(summary = "updatePriorityForTask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PatchMapping("/{taskId}/priority")
    public ResponseEntity<?> updatePriorityForTask(@PathVariable(value = "taskId") Long taskId,
                                                   @RequestParam(value = "priority") Priority priority) {
        log.info("[updatePriorityForTask] >> taskId: {}, priority: {}", taskId, priority);

        Task task = taskService.setPriorityForTask(taskId, priority);

        log.info("[updatePriorityForTask] << result: {}", task);

        return ResponseEntity.ok().body(task);
    }

}
