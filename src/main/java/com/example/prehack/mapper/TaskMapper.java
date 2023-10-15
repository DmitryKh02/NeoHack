package com.example.prehack.mapper;

import com.example.prehack.model.Project;
import com.example.prehack.model.Task;
import com.example.prehack.web.dto.EditTaskDTO;
import com.example.prehack.web.dto.ReqProject;
import com.example.prehack.web.dto.ReqTask;
import com.example.prehack.web.dto.TaskDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TaskMapper {

    @Mapping(source = "dataStart", target = "dataStart", qualifiedByName = "timestampToLocalDate")
    @Mapping(source = "dataFinish", target = "dataFinish", qualifiedByName = "timestampToLocalDate")
    Task taskDTOToTask(TaskDTO requestDTO);

    @Mapping(source = "dataStart", target = "dataStart", qualifiedByName = "timestampToLocalDate")
    @Mapping(source = "dataFinish", target = "dataFinish", qualifiedByName = "timestampToLocalDate")
    Task editTaskDTOToTask(EditTaskDTO requestDTO);

    @Mapping(source = "dataStart", target = "dataStart", qualifiedByName = "LocalDateToTimestamp")
    @Mapping(source = "dataFinish", target = "dataFinish", qualifiedByName = "LocalDateToTimestamp")
    ReqTask TaskToReqTask(Task requestDTO);

    @Named("LocalDateToTimestamp")
    static LocalDate LocalDateToTimestamp(Timestamp date) {
        return date.toLocalDateTime().toLocalDate();
    }

    @Named("timestampToLocalDate")
    static Timestamp timestampToLocalDate(LocalDate date) {
        return Timestamp.valueOf(date.atTime(LocalTime.now()));
    }
}
