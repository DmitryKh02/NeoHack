package com.example.prehack.service;

import com.example.prehack.model.Task;
import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.enumformodel.Status;
import com.example.prehack.web.dto.TaskDTO;

import java.util.List;

public interface TaskService {
//TODO документацию написать
    Task getTaskById(Long id);

    List<Task> getAllUsersTask(Long userId);
    List<Task> getAllTaskFromProject(Long projectId);

    Task createTask(TaskDTO taskDTO, String email);
    Task updateTask(Long taskId, TaskDTO taskDTO);
    Task setNewPriority(Long taskId, Priority priority);
    Task setUserForTask(Long taskId, String userEmail);
    Task setNewStatus(Long taskId, Status status);

    Task changeNameAboutTask(Long taskId, TaskDTO taskDTO);
}
