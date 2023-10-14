package com.example.prehack.repository;

import com.example.prehack.model.Project;
import com.example.prehack.model.Task;
import com.example.prehack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "select task0_.*\n from tasks task0_\n where task0_.task_id= :taskId",
            nativeQuery = true)
    Optional<Task> findTaskWithoutProject(@Param("taskId") Long taskId);

    List<Task> findAllByProject(Project project);

    List<Task> findAllByUser(User user);

}
