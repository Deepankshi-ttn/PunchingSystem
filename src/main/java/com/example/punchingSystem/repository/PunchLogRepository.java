package com.example.punchingSystem.repository;

import com.example.punchingSystem.entity.PunchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Repository
public interface PunchLogRepository extends JpaRepository<PunchLog, Long> {
//        SELECT * FROM punch_logs  where punch_time BETWEEN 'startDte' AND 'endDate';
    List<PunchLog> findByPunchTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
