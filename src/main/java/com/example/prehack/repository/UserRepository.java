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

    @Query(value = "select u.* from projects_server p " +
            "inner join user_server_project_server on p.project_id = user_server_project_server.project_id " +
            "inner join users_server u on user_server_project_server.user_id = u.user_id " +
            "where p.project_id = :projectId",
            nativeQuery = true)
    List<User> findAllUserInProject(@Param("projectId") Long projectId);

}
