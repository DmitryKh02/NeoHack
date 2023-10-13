package com.example.prehack.service.impl;

import com.example.prehack.mapper.TaskMapper;
import com.example.prehack.model.Project;
import com.example.prehack.model.Task;
import com.example.prehack.repository.TaskRepository;
import com.example.prehack.service.ProjectService;
import com.example.prehack.service.UserService;
import com.example.prehack.web.dto.TaskDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

        taskService.getTaskById(id);
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
        Long projectId = random(Long.class);
        Task task = Task.builder().build();
        Task savedTask = task;
        Project project = Project.builder().build();
        TaskDTO taskDTO = TaskDTO.builder().build();

        when(taskMapper.taskDTOToTask(taskDTO)).thenReturn(task);
        when(projectService.getProjectById(projectId)).thenReturn(project);
        when(taskRepository.save(savedTask)).thenReturn(savedTask);

        taskService.createTask(taskDTO, projectId);
        assertEquals(savedTask, task);
    }

    // как тестировать этот и следующие методы, если они вызывают getTaskById?

//    @Test
//    void updateTask() {
//        Long taskId = random(Long.class);
//        Task taskForUpdate = Task.builder().build();
//        TaskDTO taskDTO = TaskDTO.builder().build();
//
//        when()
//    }

    @Test
    void setNewPriority() {
    }

    @Test
    void setUserForTask() {
    }

    @Test
    void setNewStatus() {
    }
}