package com.example.prehack.service.impl;

import com.example.prehack.mapper.TaskMapper;
import com.example.prehack.model.Project;
import com.example.prehack.model.Task;
import com.example.prehack.model.User;
import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.enumformodel.Status;
import com.example.prehack.repository.TaskRepository;
import com.example.prehack.service.ProjectService;
import com.example.prehack.service.UserService;
import com.example.prehack.web.dto.TaskDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    TaskRepository taskRepository;
    @Mock
    ProjectService projectService;
    @Mock
    UserService userService;
    @Mock
    TaskMapper taskMapper;
    @InjectMocks
    TaskServiceImpl taskService;


    @Test
    void getTaskById() {
        Long id = random(Long.class);
        Task task = Task.builder().build();

        when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(task));

        taskService.getTaskByIdWithoutProject(id);
        assertEquals(task, taskRepository.findById(id).get());
    }

    @Test
    void getAllTaskFromProject() {
        Long projectId = random(Long.class);
        Project project = Project.builder().build();
        List<Task> tasksMock = List.of(
                Task.builder().build(),
                Task.builder().build());

        when(projectService.getProjectById(projectId)).thenReturn(project);
        when(taskRepository.findAllByProject(project)).thenReturn(tasksMock);

        List<Task> tasks = taskService.getAllTaskFromProject(projectId);
        assertEquals(tasksMock, tasks);

    }

    @Test
    void createTask() {
        //TODO update
/*        Long projectId = random(Long.class);
        Task task = Task.builder().build();
        Task savedTask = task;
        Project project = Project.builder().build();
        TaskDTO taskDTO = TaskDTO.builder().build();

        when(taskMapper.taskDTOToTask(taskDTO)).thenReturn(task);
        when(projectService.getProjectById(projectId)).thenReturn(project);
        when(taskRepository.save(savedTask)).thenReturn(savedTask);

        taskService.createTask(taskDTO, projectId);
        assertEquals(savedTask, task);*/
    }

    @Test
    void updateTask() {
        //TODO update
/*        Long taskId = random(Long.class);
        Task taskForUpdate = Task.builder().build();
        TaskDTO taskDTO = TaskDTO.builder().build();

        when(taskMapper.taskDTOToTask(taskDTO)).thenReturn(taskForUpdate);
        when(taskRepository.save(any())).thenReturn(taskForUpdate);
        when(taskRepository.findById(any())).thenReturn(Optional.of(taskForUpdate));

        Task updatedTask = taskService(taskId, taskDTO);
        assertEquals(taskForUpdate, updatedTask);*/
    }

    @Test
    void setNewPriority() {
        //TODO update
/*        Long taskId = random(Long.class);
        Priority priority = random(Priority.class);
        Task taskToSetPriority = Task.builder().build();
        Task taskWithPriority = Task.builder()
                .priority(priority)
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.ofNullable(taskToSetPriority));
        when(taskRepository.save(any(Task.class))).thenReturn(taskWithPriority);

        taskService.setNewPriority(taskId, priority);
        assertEquals(priority, taskWithPriority.getPriority());*/

    }

    @Test
    void setUserForTask_whenTaskHasNoUsers() {
        //TODO update
/*        Long taskId = random(Long.class);
        String userEmail = random(String.class);
        Task taskToSetUser = Task.builder()
                .users(new ArrayList<>())
                .build();
        User user = User.builder()
                .userName(userEmail)
                .build();
        Task taskWithUser = Task.builder()
                .users(List.of(user))
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.ofNullable(taskToSetUser));
        when(userService.getUserByEmail(userEmail)).thenReturn(user);
        when(taskRepository.save(any(Task.class))).thenReturn(taskWithUser);

        taskService.setUserForTask(taskId, userEmail);
        assertTrue(taskWithUser.getUsers().contains(user));*/
    }

    @Test
    void setUserForTask_whenTaskHasUsers() {
        //TODO update
/*        User alreadyAddedUser = User.builder()
                .userName(random(String.class))
                .build();
        List<User> users = new ArrayList<>();
        users.add(alreadyAddedUser);
        Long taskId = random(Long.class);
        String userEmail = random(String.class);
        Task taskToSetUser = Task.builder()
                .users(users)
                .build();
        User userToAdd = User.builder()
                .userName(userEmail)
                .build();
        users.add(userToAdd);
        Task taskWithUser = Task.builder()
                .users(users)
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.ofNullable(taskToSetUser));
        when(userService.getUserByEmail(userEmail)).thenReturn(userToAdd);
        when(taskRepository.save(any(Task.class))).thenReturn(taskWithUser);

        taskService.setUserForTask(taskId, userEmail);
        assertTrue(taskWithUser.getUsers().contains(userToAdd));*/
    }

    @Test
    void setNewStatus() {
        //TODO update
/*        Long taskId = random(Long.class);
        Status status = random(Status.class);
        Task updatedTask = Task.builder()
                .status(status)
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.ofNullable(updatedTask));
        when(taskRepository.save(updatedTask)).thenReturn(updatedTask);

        taskService.setNewStatus(taskId, status);
        assertEquals(status, updatedTask.getStatus());*/
    }
}