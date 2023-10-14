package com.example.prehack.service;

import com.example.prehack.model.Project;
import com.example.prehack.web.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    /**
     * Retrieve a project from the database by its unique ID.
     *
     * @param id The unique identifier of the project.
     * @return The project object associated with the given ID.
     */
    Project getProjectById(Long id);

    /**
     * Retrieve a list of projects associated with a specific user's email.
     *
     * @param userEmail The email address of the user.
     * @return A list of projects associated with the provided user's email.
     */
    List<Project> getAllProjectForUserByEmail(String userEmail);

    /**
     * Create and save a new project with the provided information.
     *
     * @param emailCreator The email address of the user creating the project.
     * @param projectDTO Information about the project to be created.
     * @return The newly created project.
     */
    Project createProject(String emailCreator, ProjectDTO projectDTO);

    /**
     * Delete a project from the database based on its ID.
     *
     * @param projectId The unique identifier of the project to be deleted.
     */
    void deleteProject(Long projectId);

    /**
     * Update the information of an existing project in the database.
     *
     * @param projectId The unique identifier of the project to update.
     * @param projectDTO Information with the updated project data.
     * @return The updated project.
     */
    Project updateProject(Long projectId, ProjectDTO projectDTO);

    /**
     * Set a new user to be associated with a project.
     *
     * @param id The unique identifier of the project.
     * @param userEmail The email address of the user to associate with the project.
     * @return The updated project with the new user association.
     */
    Project setNewUserForProject(Long id, String userEmail);

    /**
     * Retrieve a list of all projects stored in the database.
     *
     * @return A list of all projects available in the database.
     */
    List<Project> getAllProject();

}
