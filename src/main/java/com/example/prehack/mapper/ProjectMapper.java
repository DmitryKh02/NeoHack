package com.example.prehack.mapper;

import com.example.prehack.model.Project;
import com.example.prehack.web.dto.ProjectDTO;
import com.example.prehack.web.dto.ReqProject;
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

    @Mapping(source = "dataStart", target = "dataStart", qualifiedByName = "LocalDateToTimestamp")
    @Mapping(source = "dataFinish", target = "dataFinish", qualifiedByName = "LocalDateToTimestamp")
    ReqProject projectDTOToProject(Project requestDTO);

    @Named("timestampToLocalDate")
    static Timestamp timestampToLocalDate(LocalDate date) {
        return Timestamp.valueOf(date.atTime(LocalTime.now()));
    }

    @Named("LocalDateToTimestamp")
    static LocalDate LocalDateToTimestamp(Timestamp date) {
        return date.toLocalDateTime().toLocalDate();
    }
}
