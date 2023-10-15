package com.example.prehack.model;

import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.jsonb.StatusHistory;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.mapstruct.Named;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks_server")
@TypeDef(name = "json", typeClass = JsonType.class)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;


    @Column(name = "data_start")
    private Timestamp dataStart;

    @Column(name = "data_finish")
    private Timestamp dataFinish;


    @Type(type = "json")
    @Column(name = "status_history", columnDefinition = "json")
    private List<StatusHistory> statusHistories;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", referencedColumnName = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;



}
