package com.example.punchingSystem.entity;

import com.example.punchingSystem.workshiftEnum.WorkDays;
import com.example.punchingSystem.workshiftEnum.WorkShift;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "work_schedule_settings")
public class WorkScheduleSettings {
    @Id
    @Column(name = "user_email")
    private String userEmail;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_shift")
    private WorkShift workShift;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = WorkDays.class)
    @CollectionTable(name = "work_schedule_days", joinColumns = @JoinColumn(name = "user_email"))
    @Column(name = "office_days")
    private List<WorkDays> officeDays;
}
