package com.example.prehack.service.impl;

import com.example.prehack.exception.ResourceNotFoundException;
import com.example.prehack.mapper.TaskMapper;
import com.example.prehack.model.Role;
import com.example.prehack.model.Task;
import com.example.prehack.model.User;
import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.enumformodel.Status;
import com.example.prehack.model.jsonb.StatusHistory;
import com.example.prehack.repository.TaskRepository;
import com.example.prehack.service.ProjectService;
import com.example.prehack.service.RoleService;
import com.example.prehack.service.TaskService;
import com.example.prehack.service.UserService;
import com.example.prehack.web.dto.EditTaskDTO;
import com.example.prehack.web.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final UserService userService;
    private final RoleService roleService;

    private final TaskMapper taskMapper;

    @Override
    public Task getTaskById(Long id) {
        log.info("[getTaskById] >> id: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Task not found by this id :{} ", id);
                    return new ResourceNotFoundException("Task not found by this id :: " + id);
                });

        log.info("[getTaskById] << result: {}", task);

        return task;
    }

    @Override
    public Task getTaskByIdWithoutProject(Long id) {
        log.info("[getTaskById] >> id: {}", id);

        Task task = taskRepository.findTaskWithoutProject(id)
                .orElseThrow(() -> {
                    log.error("Task not found by this id :{} ", id);
                    return new ResourceNotFoundException("Task not found by this id :: " + id);
                });

        log.info("[getTaskById] << result: {}", task);

        return task;
    }

    @Override
    public List<Task> getAllTask() {
        log.info("[getAllTask] >> without");

        List<Task> tasks = taskRepository.findAll();

        log.info("[getAllTask] << result: {}", tasks);

        return tasks;
    }

    @Override
    public List<Task> getAllTaskForUserByEmail(String email) {
        log.info("[getAllUsersTask] >> email: {}", email);

        List<Task> tasks = taskRepository.findAllByUser(userService.getUserByEmail(email));

        log.info("[getAllUsersTask] << result: {}", tasks);

        return tasks;
    }

    @Override
    public List<Task> getAllTaskFromProject(Long projectId) {
        log.info("[getAllTaskFromProject] >> projectId: {}", projectId);

        List<Task> tasks = taskRepository.findAllByProject(projectService.getProjectById(projectId));

        log.info("[getAllTaskFromProject] << result: {}", tasks);

        return tasks;
    }

    @Override
    public Task setTaskToProject(Long projectId, Long taskId) {
        log.info("[setTaskToProject] >> projectId: {}, taskId: {}", projectId, taskId);

        Task task = getTaskByIdWithoutProject(taskId);

        task.setProject(projectService.getProjectById(projectId));

        task = taskRepository.save(task);

        log.info("[setTaskToProject] << result: {}", task);

        return task;
    }

    @Override
    @Transactional
    public Task createTask(TaskDTO taskDTO, String email) {
        log.info("[createTask] >> taskDTO: {}", taskDTO);
        //Без привязки к проекту и к пользователю
        //Название только если пользователь - менеджер

        User user = userService.getUserByEmail(email);
        Role roleManager = roleService.getRoleByName("ROLE_MANAGER");

        Task task = taskMapper.taskDTOToTask(taskDTO);

        StatusHistory statusHistory = new StatusHistory(user, Status.OPENED, LocalDateTime.now());
        task.setStatusHistories(List.of(statusHistory));

        if (!user.getRoles().contains(roleManager)) {
            task.setName("");
        } else {
            if (taskDTO.getProjectId() != null || !taskDTO.getProjectId().equals(0L)) {
                task.setProject(projectService.getProjectById(taskDTO.getProjectId()));
            }
        }

        Task savedTask = taskRepository.save(task);

        log.info("[createTask] << result : {}", savedTask);

        return savedTask;
    }


    @Override
    public Task setFullInfoForTask(Long taskId, EditTaskDTO taskDTO) {
        log.info("[setFullInfoForTask] >> taskDTO: {}", taskDTO);

        Task taskForUpdate = getTaskByIdWithoutProject(taskId);
        Task newTask = taskMapper.editTaskDTOToTask(taskDTO);

        newTask.setTaskId(taskForUpdate.getTaskId());
        newTask.setStatusHistories(taskForUpdate.getStatusHistories());
        newTask.setProject(taskForUpdate.getProject());

        Task savedTask = taskRepository.save(newTask);

        setUserForTask(savedTask.getTaskId(), taskDTO.getUserEmail());

        log.info("[setFullInfoForTask] << result : {}", savedTask);

        return savedTask;
    }

    @Override
    public Task setPriorityForTask(Long taskId, Priority priority) {
        log.info("[setNewPriority] >> taskId: {}, priority: {}", taskId, priority);
        Task task = getTaskByIdWithoutProject(taskId);

        task.setPriority(priority);

        Task savedTask = taskRepository.save(task);

        log.info("[setNewPriority] << result : {}", savedTask);

        return savedTask;
    }

    @Override
    public Task setUserForTask(Long taskId, String userEmail) {
        log.info("[setUserForTask] >> taskId: {}, userEmail: {}", taskId, userEmail);
        Task task = getTaskByIdWithoutProject(taskId);

        task.setUser(userService.getUserByEmail(userEmail));

        Task savedTask = taskRepository.save(task);

        log.info("[setUserForTask] << result : {}", savedTask);

        return savedTask;
    }

    @Override
    public Task setStatusForTask(String email, Long taskId, Status status) {
        log.info("[setNewStatus] >> taskId: {}, status: {}", taskId, status);

        Task task = getTaskByIdWithoutProject(taskId);
        User user = userService.getUserByEmail(email);

        StatusHistory statusHistory = new StatusHistory(user, status, LocalDateTime.now());

        task.getStatusHistories().add(statusHistory);

        Task savedTask = taskRepository.save(task);

        log.info("[setNewStatus] << result : {}", savedTask);

        return savedTask;
    }

    @Override
    public Task setNameForTask(Long taskId, TaskDTO taskDTO) {
        log.info("[changeNameAboutTask] >> taskId: {}, taskDTO: {}", taskId, taskDTO);
        Task task = getTaskByIdWithoutProject(taskId);

        task.setName(taskDTO.getName());

        Task savedTask = taskRepository.save(task);

        log.info("[changeNameAboutTask] << result : {}", savedTask);

        return savedTask;
    }

    @Override
    public void deleteTask(Long taskId) {
        log.info("[deleteTask] >> taskId: {}", taskId);

        taskRepository.delete(getTaskByIdWithoutProject(taskId));

        log.info("[deleteTask] << result task was deleted");
    }
}
