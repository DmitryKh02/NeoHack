package com.example.prehack.service;

import com.example.prehack.model.Project;
import com.example.prehack.model.Task;
import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.enumformodel.Status;
import com.example.prehack.web.dto.*;

import java.util.List;

public interface ProjectService {

    Project getProjectById(Long id);

    List<Project> getAllProjectForUserByEmail(String userEmail);

    Project createProject(ProjectDTO projectDTO);

    void deleteProject(Long projectId);

    Project updateProject(Long projectId, ProjectDTO projectDTO);

    Project setNewUserForProject(Long id, String userEmail);

}
