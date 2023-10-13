package com.example.prehack.web.dto;

import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.enumformodel.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {

    private String name;

    private String description;

    private Priority priority;

    private Status status;

    private LocalDate dataStart;

    private LocalDate dataFinish;

    private Long projectId;

}
