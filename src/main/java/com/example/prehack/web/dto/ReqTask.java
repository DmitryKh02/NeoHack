package com.example.prehack.web.dto;

import com.example.prehack.model.Project;
import com.example.prehack.model.User;
import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.jsonb.StatusHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqTask {

    private Long taskId;

    private String name;

    private String description;

    private String customer;

    private Priority priority;

    private LocalDate dataStart;

    private LocalDate dataFinish;

    private List<StatusHistory> statusHistories;

    private User users;

    private Project project;
}
