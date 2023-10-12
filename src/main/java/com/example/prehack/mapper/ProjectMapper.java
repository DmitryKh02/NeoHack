package com.example.prehack.mapper;

import com.example.prehack.model.Project;
import com.example.prehack.model.Task;
import com.example.prehack.web.dto.ProjectDTO;
import com.example.prehack.web.dto.TaskDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProjectMapper {

    @Mapping(source = "dataStart", target = "dataStart", qualifiedByName = "timestampToLocalDate")
    @Mapping(source = "dataFinish", target = "dataFinish", qualifiedByName = "timestampToLocalDate")
    Project projectDTOToProject(ProjectDTO requestDTO);

    @Named("timestampToLocalDate")
    static Timestamp timestampToLocalDate(LocalDateTime date) {
        return Timestamp.valueOf(date);
    }
}
