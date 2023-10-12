package com.example.prehack.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
