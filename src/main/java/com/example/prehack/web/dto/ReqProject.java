package com.example.prehack.web.dto;

import com.example.prehack.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqProject {

    private Long projectId;

    private String name;

    private String description;

    private String customer;

    private LocalDate dataStart;

    private LocalDate dataFinish;

    private Set<User> users;
}
