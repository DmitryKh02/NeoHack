package com.example.prehack.model;

import com.example.prehack.model.enumformodel.Priority;
import com.example.prehack.model.enumformodel.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "data_start")
    private Timestamp dataStart;

    @Column(name = "data_finish")
    private Timestamp dataFinish;

    @ManyToMany(mappedBy="tasks")
    private List<User> users;

    @ManyToOne
    @JoinColumn(name="project_id", nullable=false)
    private Project project;


}
