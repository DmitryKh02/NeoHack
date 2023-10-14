package com.example.prehack.repository;

import com.example.prehack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);

    @Query(value = "select u.* from projects p " +
            "inner join user_project on p.project_id = user_project.project_id " +
            "inner join users u on user_project.user_id = u.user_id " +
            "where p.project_id = :projectId",
            nativeQuery = true)
    List<User> findAllUserInProject(@Param("projectId") Long projectId);

}
