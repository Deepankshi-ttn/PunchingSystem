package com.example.punchingSystem.repository;

import com.example.punchingSystem.entity.WorkScheduleSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkScheduleSettingsRepository extends JpaRepository<WorkScheduleSettings, String> {
    // Method to find WorkScheduleSettings by a list of user emails
    List<WorkScheduleSettings> findByUserEmailIn(List<String> userEmails);
}
