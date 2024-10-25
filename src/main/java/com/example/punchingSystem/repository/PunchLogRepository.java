package com.example.punchingSystem.repository;

import com.example.punchingSystem.entity.PunchLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PunchLogRepository extends JpaRepository<PunchLog, Long> {
}
