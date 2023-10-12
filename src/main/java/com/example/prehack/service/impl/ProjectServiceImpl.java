package com.example.prehack.service.impl;


import com.example.prehack.exception.ResourceNotFoundException;
import com.example.prehack.mapper.ProjectMapper;
import com.example.prehack.model.Project;
import com.example.prehack.model.User;
import com.example.prehack.repository.ProjectRepository;
import com.example.prehack.repository.UserRepository;
import com.example.prehack.service.ProjectService;
import com.example.prehack.service.UserService;
import com.example.prehack.web.dto.ProjectDTO;
import com.example.prehack.web.dto.UserEmailsForProjectDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final UserRepository userRepository;
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
        log.info("[getAllTaskFromProject] >> userEmail: {}", userEmail);

        User user = userService.getUserByEmail(userEmail);

        List<Project> projects = user.getProjects();

        log.info("[getAllTaskFromProject] << result: {}", projects);

        return projects;
    }

    @Override
    public Project createProject(ProjectDTO projectDTO) {
        log.info("[createProject] >> projectDTO: {}", projectDTO);

        Project project = projectMapper.projectDTOToProject(projectDTO);

        //FIXME Не сохраняются пользователи в связке с проектами
        List<User> users = new ArrayList<>();
        for (UserEmailsForProjectDTO email : projectDTO.getUserEmails()) {
            users.add(userService.getUserByEmail(email.getEmail()));
        }

        project.setUsers(users);
        Project savedProject = projectRepository.save(project);

        log.info("[createProject] << result : {}", savedProject);

        return savedProject;
    }

    @Override
    public void deleteProject(Long projectId) {
        //TODO реализация отсутствует
    }

    @Override
    public Project updateProject(Long projectId, ProjectDTO projectDTO) {
        return null;
    }

    @Override
    public Project setNewUserForProject(Long id, String userEmail) {
        log.info("[setNewUserForProject] >> id: {}, userEmail: {}", id, userEmail);

        Project project = getProjectById(id);

        User user = userService.getUserByEmail(userEmail);

        //FIXME БЕСКОНЕЧНО СОХРАНЕНИЕ ПОЛЬЗОВАТЕЛЕЙ
        /*if (users.isEmpty()){*/
        user.getProjects().add(project);

        User user1 = userRepository.save(user);
        /*}
        else {
            project.getUsers().add(user);
        }*/

        //Project savedProject = projectRepository.save(project);

        log.info("[setNewUserForProject] << result : {}", project);

        return project;
    }

    @Override
    public Project setTaskToProject(Long projectId, Long taskId) {
        //TODO реализация отсутствует
        return null;
    }
}
