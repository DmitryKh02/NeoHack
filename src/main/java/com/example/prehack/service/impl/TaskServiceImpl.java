package com.example.prehack.service.impl;

import com.example.prehack.exception.ResourceNotFoundException;
import com.example.prehack.mapper.TaskMapper;
import com.example.prehack.model.Project;
import com.example.prehack.model.Task;
import com.example.prehack.model.User;
import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.enumformodel.Status;
import com.example.prehack.repository.TaskRepository;
import com.example.prehack.service.ProjectService;
import com.example.prehack.service.TaskService;
import com.example.prehack.service.UserService;
import com.example.prehack.web.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final ProjectService projectService;

    private final UserService userService;

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
    public List<Task> getAllTaskFromProject(Long projectId) {
        log.info("[getAllTaskFromProject] >> projectId: {}", projectId);

        Project project = projectService.getProjectById(projectId);

        List<Task> tasks = taskRepository.findAllByProject(project);

        log.info("[getAllTaskFromProject] << result: {}", tasks);

        return tasks;
    }

    @Override
    public Task createTask(TaskDTO taskDTO, Long projectId) {
        log.info("[createTask] >> taskDTO: {}", taskDTO);

        Task task = taskMapper.taskDTOToTask(taskDTO);
        task.setProject(projectService.getProjectById(projectId));

        Task savedTask = taskRepository.save(task);

        log.info("[createTask] << result : {}", savedTask);

        return savedTask;
    }

    @Override
    public Task updateTask(Long taskId, TaskDTO taskDTO) {
        log.info("[updateTask] >> taskDTO: {}", taskDTO);

        Task taskForUpdate = getTaskById(taskId);

        Task task = taskMapper.taskDTOToTask(taskDTO);
        task.setTaskId(taskForUpdate.getTaskId());
        task.setProject(taskForUpdate.getProject());

        Task savedTask = taskRepository.save(task);

        log.info("[updateTask] << result is token for user");

        return savedTask;
    }

    @Override
    public Task setNewPriority(Long taskId, Priority priority) {
        log.info("[setNewPriority] >> taskId: {}, priority: {}", taskId, priority);
        Task task = getTaskById(taskId);

        task.setPriority(priority);

        Task savedTask = taskRepository.save(task);

        log.info("[setNewPriority] << result : {}", savedTask);

        return savedTask;
    }

    @Override
    public Task setUserForTask(Long taskId, String userEmail) {
        log.info("[setUserForTask] >> taskId: {}, userEmail: {}", taskId, userEmail);
        Task task = getTaskById(taskId);
        User user = userService.getUserByEmail(userEmail);

        List<User> users = task.getUsers();
        if (users.isEmpty()){
            task.setUsers(List.of(user));
        }
        else {
            task.getUsers().add(user);
        }

        Task savedTask = taskRepository.save(task);

        log.info("[setNewPriority] << result : {}", savedTask);

        return savedTask;
    }

    @Override
    public Task setNewStatus(Long taskId, Status status) {
        log.info("[setNewStatus] >> taskId: {}, status: {}", taskId, status);
        Task task = getTaskById(taskId);

        task.setStatus(status);

        Task savedTask = taskRepository.save(task);

        log.info("[setNewPriority] << result : {}", savedTask);

        return savedTask;
    }

}
