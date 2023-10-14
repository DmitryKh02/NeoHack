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

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final ProjectService projectService;

    private final TaskService taskService;

    //region Get methods
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

    @Operation(summary = "getAllTaskFromUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<?> getAllTaskFromUser(@PathVariable(value = "userId") Long userId) {
        log.info("[getAllTaskFromUser] >> userId: {}", userId);

        //TODO не только менеджер
        List<Task> taskList = taskService.getAllUsersTask(userId);

        log.info("[getAllTaskFromUser] << result: {}", taskList);

        return ResponseEntity.ok().body(taskList);
    }

    @Operation(summary = "getInfoAboutProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<?> getInfoAboutProject(@PathVariable(value = "projectId") Long projectId) {
        log.info("[getAllTaskInProject] >> projectId: {}", projectId);
        //TODO не только менеджер
        Project project = projectService.getProjectById(projectId);

        log.info("[getAllTaskInProject] << result: {}", project);

        return ResponseEntity.ok().body(project);
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

    @Operation(summary = "getAllProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/projects")
    public ResponseEntity<?> getAllProject() {
        log.info("[getAllProject] >> without");

        List<Project> projects = projectService.getAllProject();

        log.info("[getAllProject] << result: {}", projects);

        return ResponseEntity.ok().body(projects);
    }

    @Operation(summary = "getAllProjectForUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/projects/user")
    public ResponseEntity<?> getAllProjectForUser(Principal principal) {
        log.info("[getAllProject] >> without");

        List<Project> projects = projectService.getAllProjectForUserByEmail(principal.getName());

        log.info("[getAllProject] << result: {}", projects);

        return ResponseEntity.ok().body(projects);
    }
    //endregion

    //region Post methods
    @Operation(summary = "createProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/project")
    public ResponseEntity<?> createProject(Principal principal,
                                           @Valid @RequestBody ProjectDTO projectDTO) {
        log.info("[createProject] >> projectDTO: {}", projectDTO);

        Project project = projectService.createProject(principal.getName(), projectDTO);

        log.info("[createProject] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }

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

    //endregion

    //region Patch methods
    @Operation(summary = "setUserToProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PatchMapping("/project/{projectId}/users")
    public ResponseEntity<?> setUserToProject(@PathVariable(value = "projectId") Long projectId,
                                              @Valid @RequestBody UserEmailsForProjectDTO userEmailsForProjectDTO) {
        log.info("[addUserForProject] >> projectId: {} , userEmailsForProjectDTO: {}", projectId, userEmailsForProjectDTO);

        Project project = projectService.setNewUserForProject(projectId, userEmailsForProjectDTO.getEmail());

        log.info("[addUserForProject] << result: {}", project);

        return ResponseEntity.ok().body(project);
    }

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

    //region Another methods

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
    //endregion

    //TODO ПМ может увиджеть сколько и каких задач сделать пользователь

    //TODO создание задачи без статуса приходит
    //TODO Запрос всех задач
    //TODO редактирование задачи
    //TODO удаление задачи
    //TODO создание проекта без участников
    //TODO Добавление по 1 участнику на проект
    //TODO Создание задачи без участников
    //TODO все проекты
    //TODO все проекты пользователя

    //TODO все задачи пользователя

    //TODO DataTime -> Data


}



