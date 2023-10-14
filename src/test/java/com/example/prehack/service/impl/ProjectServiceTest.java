package com.example.prehack.service.impl;

import com.example.prehack.mapper.ProjectMapper;
import com.example.prehack.model.Project;
import com.example.prehack.model.User;
import com.example.prehack.repository.ProjectRepository;
import com.example.prehack.service.UserService;
import com.example.prehack.web.dto.ProjectDTO;
import com.example.prehack.web.dto.UserEmailsForProjectDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @InjectMocks
    ProjectServiceImpl projectService;
    @Mock
    ProjectRepository projectRepository;
    @Mock
    UserService userService;
    @Mock
    ProjectMapper projectMapper;

    @Test
    void getProjectById() {
        Long id = random(Long.class);
        Project project1 = Project.builder().build();

        when(projectRepository.findById(id)).thenReturn(Optional.ofNullable(project1));

        projectService.getProjectById(id);
        assertEquals(project1, projectRepository.findById(id).get());
    }

    @Test
    void getAllProjectForUserByEmail() {
        //TODO update
/*        String userEmail = random(String.class);
        Project project1 = Project.builder()
                .name("Name1")
                .build();
        Project project2 = Project.builder()
                .name("Name2")
                .build();
        List<Project> projects = List.of(project1, project2);
        User user = User.builder()
                .projects(List.of(project1, project2))
                .build();

        when(userService.getUserByEmail(userEmail)).thenReturn(user);

        projectService.getAllProjectForUserByEmail(userEmail);
        assertTrue(projects.contains(project1));
        assertTrue(projects.contains(project2));*/
    }

    @Test
    void createProject() {
        //TODO update
/*        UserEmailsForProjectDTO emails = UserEmailsForProjectDTO.builder()
                .email("qwe@rty.ru")
                .build();
        ProjectDTO projectDTO = ProjectDTO.builder()
                .userEmails(Set.of(emails))
                .build();
        Project project = Project.builder().build();
        Project savedProject = project;

        when(projectMapper.projectDTOToProject(projectDTO)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(savedProject);

        projectService.createProject(projectDTO);
        assertEquals(savedProject, project);*/
    }

    @Test
    void deleteProject() {
        //TODO
    }

    @Test
    void updateProject() {
        //TODO
    }

    @Test
    void setNewUserForProject() {
        //TODO
    }
}