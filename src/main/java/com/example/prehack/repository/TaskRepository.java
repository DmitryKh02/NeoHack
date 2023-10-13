package com.example.prehack.repository;

import com.example.prehack.model.Project;
import com.example.prehack.model.Task;
import com.example.prehack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByProject(Project project);

    List<Task> findAllByUserContaining(User user);
}
