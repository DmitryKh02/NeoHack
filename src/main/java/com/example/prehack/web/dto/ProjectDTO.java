package com.example.prehack.web.dto;

import com.example.prehack.model.Project;
import com.example.prehack.model.User;
import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.enumformodel.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {

    private String name;

    private String description;

    private String customer;

    private LocalDateTime dataStart;

    private LocalDateTime dataFinish;

    private Set<UserEmailsForProjectDTO> userEmails;
}
