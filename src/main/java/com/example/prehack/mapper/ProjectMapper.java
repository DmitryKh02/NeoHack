package com.example.prehack.mapper;

import com.example.prehack.model.Project;
import com.example.prehack.web.dto.ProjectDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProjectMapper {

    @Mapping(source = "dataStart", target = "dataStart", qualifiedByName = "timestampToLocalDate")
    @Mapping(source = "dataFinish", target = "dataFinish", qualifiedByName = "timestampToLocalDate")
    Project projectDTOToProject(ProjectDTO requestDTO);

    @Named("timestampToLocalDate")
    static Timestamp timestampToLocalDate(LocalDate date) {
        return Timestamp.valueOf(date.atTime(LocalTime.now()));
    }
}
