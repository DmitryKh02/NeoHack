package com.example.prehack.service.impl;


import com.example.prehack.exception.ResourceNotFoundException;
import com.example.prehack.exception.UserAlreadyExistException;
import com.example.prehack.mapper.ProjectMapper;
import com.example.prehack.model.Project;
import com.example.prehack.model.User;
import com.example.prehack.repository.ProjectRepository;
import com.example.prehack.service.ProjectService;
import com.example.prehack.service.UserService;
import com.example.prehack.web.dto.ProjectDTO;
import com.example.prehack.web.dto.UserEmailsForProjectDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final UserService userService;

    private final ProjectMapper projectMapper;

    @Override
    public Project getProjectById(Long id) {
        log.info("[getProjectById] >> id: {}", id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Project not found by this id :{} ", id);
                    return new ResourceNotFoundException("Project not found by this id :: " + id);
                });

        log.info("[getProjectById] << result: {}", project);

        return project;
    }

    @Override
    public List<Project> getAllProjectForUserByEmail(String userEmail) {
        log.info("[getAllProjectForUserByEmail] >> userEmail: {}", userEmail);

        User user = userService.getUserByEmail(userEmail);

        List<Project> projects = projectRepository.findAllByUsersContaining(user);

        log.info("[getAllProjectForUserByEmail] << result: {}", projects);

        return projects;
    }

    @Override
    public List<Project> getAllProject() {
        log.info("[getAllTaskFromProject] >> without");

        List<Project> projects = projectRepository.findAll();

        log.info("[getAllTaskFromProject] << result: {}", projects);

        return projects;
    }

    @Override
    @Transactional
    public Project createProject(String emailCreator, ProjectDTO projectDTO) {
        log.info("[createProject] >> projectDTO: {}", projectDTO);

        Project project = projectMapper.projectDTOToProject(projectDTO);

        project = projectRepository.save(project);

        Set<User> users = new HashSet<>();
        users.add(userService.getUserByEmail(emailCreator));

        if (!projectDTO.getUserEmails().isEmpty()) {
            for (UserEmailsForProjectDTO email : projectDTO.getUserEmails()) {
                users.add(userService.getUserByEmail(email.getEmail()));
            }
        }

        project.setUsers(users);

        Project savedProject = projectRepository.save(project);

        log.info("[createProject] << result : {}", savedProject);

        return savedProject;
    }

    @Override
    public void deleteProject(Long projectId) {
        log.info("[deleteProject] >> projectId: {}", projectId);

        projectRepository.delete(getProjectById(projectId));

        log.info("[deleteProject] << result task was deleted");
    }

    @Override
    public Project updateProject(Long projectId, ProjectDTO projectDTO) {
        //TODO Реализовать
        return null;
    }

    @Override
    public Project setNewUserForProject(Long id, String userEmail) throws UserAlreadyExistException {
        log.info("[setNewUserForProject] >> id: {}, userEmail: {}", id, userEmail);

        Project project = getProjectById(id);
        User user = userService.getUserByEmail(userEmail);

        if(project.getUsers().contains(user)){
            throw new UserAlreadyExistException("User with id " + user.getUserId() +" already exists in this project");
        }

        project.getUsers().add(user);

        Project savedProject = projectRepository.save(project);

        log.info("[setNewUserForProject] << result : {}", savedProject);

        return savedProject;
    }


}
