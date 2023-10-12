package com.example.prehack.repository;

import com.example.prehack.model.Project;
import com.example.prehack.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository  extends JpaRepository<Project, Long> {
}
