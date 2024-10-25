package com.example.punchingSystem.repository;

import com.example.punchingSystem.entity.WorkScheduleSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkScheduleSettingsRepository extends JpaRepository<WorkScheduleSettings, String> {
}
