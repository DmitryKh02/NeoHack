package com.example.prehack.repository;

import com.example.prehack.model.Project;
import com.example.prehack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByUsersContaining(User user);
}
