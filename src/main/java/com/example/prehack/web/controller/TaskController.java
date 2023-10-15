package com.example.prehack.web.controller;

import com.example.prehack.mapper.TaskMapper;
import com.example.prehack.model.Task;
import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.enumformodel.Status;
import com.example.prehack.service.TaskService;
import com.example.prehack.web.dto.ReqTask;
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
import java.util.LinkedList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    //region Get methods
    @Operation(summary = "get All Task for this User (by email)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/user")
    public ResponseEntity<List<ReqTask>> getAllTaskFromUser(Principal principal) {
        log.info("[getAllTaskFromUser] >> email: {}", principal.getName());

        List<Task> taskList = taskService.getAllTaskForUserByEmail(principal.getName());

        List<ReqTask> reqTasks = new LinkedList<>();

        for (Task task : taskList){
            reqTasks.add(taskMapper.TaskToReqTask(task));
        }

        log.info("[getAllTaskFromUser] << result: {}", taskList);

        return ResponseEntity.ok().body(reqTasks);
    }
    //endregion

    //region Post method
    @Operation(summary = "createTask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping
    public ResponseEntity<ReqTask> createTask(Principal principal,
                                        @Valid @RequestBody TaskDTO taskDTO) {
        log.info("[createTask] >> taskDTO: {}", taskDTO);

        //В principal хранится почта пользователя(данные из JWT) principal.getName();
        Task task = taskService.createTask(taskDTO, principal.getName());

        log.info("[createTask] << result: {}", task);

        return ResponseEntity.ok().body(taskMapper.TaskToReqTask(task));
    }
    //endregion

    //region Patch methods
    @Operation(summary = "updateStatusForTask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<ReqTask> updateStatusForTask(Principal principal,
                                                 @PathVariable(value = "taskId") Long taskId,
                                                 @RequestParam(value = "status") Status status) {
        log.info("[updateStatusForTask] >> taskId: {}, status: {}", taskId, status);

        Task task = taskService.setStatusForTask(principal.getName(), taskId, status);

        log.info("[updateStatusForTask] << result: {}", task);

        return ResponseEntity.ok().body(taskMapper.TaskToReqTask(task));
    }

    @Operation(summary = "updatePriorityForTask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PatchMapping("/{taskId}/priority")
    public ResponseEntity<ReqTask> updatePriorityForTask(@PathVariable(value = "taskId") Long taskId,
                                                   @RequestParam(value = "priority") Priority priority) {
        log.info("[updatePriorityForTask] >> taskId: {}, priority: {}", taskId, priority);

        Task task = taskService.setPriorityForTask(taskId, priority);

        log.info("[updatePriorityForTask] << result: {}", task);

        return ResponseEntity.ok().body(taskMapper.TaskToReqTask(task));
    }
    //endregion

}
