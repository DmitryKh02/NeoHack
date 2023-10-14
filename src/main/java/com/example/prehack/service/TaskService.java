package com.example.prehack.service;

import com.example.prehack.model.Task;
import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.enumformodel.Status;
import com.example.prehack.web.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    /**
     * Get a task from the database by its ID.
     *
     * @param id The unique identifier of the task.
     * @return The task object associated with the given ID, or null if not found.
     */
    Task getTaskById(Long id);

    /**
     * Retrieve a list of tasks associated with a specific user's email.
     *
     * @param email The email address of the user.
     * @return A list of tasks associated with the provided user's email.
     */
    List<Task> getAllTaskForUserByEmail(String email);

    /**
     * Retrieve a list of tasks associated with a specific project.
     *
     * @param projectId The unique identifier of the project.
     * @return A list of tasks associated with the provided project.
     */
    List<Task> getAllTaskFromProject(Long projectId);

    /**
     * Get a task from the database by its ID without considering its project association.
     *
     * @param id The unique identifier of the task.
     * @return The task object associated with the given ID, without considering its project.
     */
    Task getTaskByIdWithoutProject(Long id);

    /**
     * Retrieve a list of all tasks in the database.
     *
     * @return A list of all tasks stored in the database.
     */
    List<Task> getAllTask();

    /**
     * Create a new task using the provided TaskDTO and associate it with a user's email.
     *
     * @param taskDTO The data to create the task, including its properties.
     * @param email The email address of the user to associate with the created task.
     * @return The created task object.
     */
    Task createTask(TaskDTO taskDTO, String email);

    /**
     * Set the association of a task with a specific project.
     *
     * @param projectId The unique identifier of the project to associate the task with.
     * @param taskId The unique identifier of the task.
     * @return The updated task object with the new project association.
     */
    Task setTaskToProject(Long projectId, Long taskId);

    /**
     * Set the full information for a task, replacing its current data with the provided TaskDTO.
     *
     * @param taskId The unique identifier of the task to update.
     * @param taskDTO The updated task data to set.
     * @return The updated task object with the new information.
     */
    Task setFullInfoForTask(Long taskId, TaskDTO taskDTO);

    /**
     * Set the priority of a task.
     *
     * @param taskId The unique identifier of the task to update.
     * @param priority The new priority to set for the task.
     * @return The updated task object with the new priority.
     */
    Task setPriorityForTask(Long taskId, Priority priority);

    /**
     * Set the user associated with a task by their email.
     *
     * @param taskId The unique identifier of the task to update.
     * @param userEmail The email address of the user to associate with the task.
     * @return The updated task object with the new user association.
     */
    Task setUserForTask(Long taskId, String userEmail);

    /**
     * Set the status of a task.
     *
     * @param email The email address of the user making the status change.
     * @param taskId The unique identifier of the task to update.
     * @param status The new status to set for the task.
     * @return The updated task object with the new status.
     */
    Task setStatusForTask(String email, Long taskId, Status status);

    /**
     * Set the name of a task using the provided TaskDTO.
     *
     * @param taskId The unique identifier of the task to update.
     * @param taskDTO The updated task data containing the new name.
     * @return The updated task object with the new name.
     */
    Task setNameForTask(Long taskId, TaskDTO taskDTO);

    /**
     * Delete a task from the database based on its ID.
     *
     * @param taskId The unique identifier of the task to be deleted.
     */
    void deleteTask(Long taskId);

}
