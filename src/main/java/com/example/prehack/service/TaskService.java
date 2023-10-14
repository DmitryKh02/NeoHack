package com.example.prehack.service;

import com.example.prehack.model.Task;
import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.enumformodel.Status;
import com.example.prehack.web.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    //TODO документацию написать
    Task getTaskById(Long id);

    List<Task> getAllTaskForUserByEmail(String email);

    List<Task> getAllTaskFromProject(Long projectId);

    Task getTaskByIdWithoutProject(Long id);

    List<Task> getAllTask();

    Task createTask(TaskDTO taskDTO, String email);

    Task setTaskToProject(Long projectId, Long taskId);

    Task setFullInfoForTask(Long taskId, TaskDTO taskDTO);

    Task setPriorityForTask(Long taskId, Priority priority);

    Task setUserForTask(Long taskId, String userEmail);

    Task setStatusForTask(String email, Long taskId, Status status);

    Task setNameForTask(Long taskId, TaskDTO taskDTO);

    void deleteTask(Long taskId);
}
