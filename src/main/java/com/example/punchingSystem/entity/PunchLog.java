package com.example.punchingSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "punch_logs")
public class PunchLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "user_email", nullable = false)
    private WorkScheduleSettings workScheduleSettings;

    @Column(name = "punch_time")
    private LocalDateTime punchTime;
}
