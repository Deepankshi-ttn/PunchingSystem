package com.example.punchingSystem.scheduler;

import com.example.punchingSystem.entity.PunchLog;
import com.example.punchingSystem.entity.WorkScheduleSettings;
import com.example.punchingSystem.repository.PunchLogRepository;
import com.example.punchingSystem.repository.WorkScheduleSettingsRepository;
import com.example.punchingSystem.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.time.Duration;

@Component
public class PunchLogScheduler {

    @Autowired
    private PunchLogRepository punchLogRepository;

    @Autowired
    private WorkScheduleSettingsRepository workScheduleSettingsRepository;

    @Scheduled(cron = "0 0 06 * * ?") // Run every day at 6 AM
    public void identifyAndReportDefaulters() {
////        System.out.println("I RAN");
//        Calendar calendar = Calendar.getInstance();
//
//        calendar.setTime(new Date());
//        calendar.add(Calendar.DATE, -1);
//
//        Date startDate = calendar.getTime();
//
//        calendar.add(Calendar.DATE, 1);
//        Date endDate = calendar.getTime();

        LocalDateTime startOfDay = LocalDateTime.now().minusDays(1).with(LocalTime.MIN);

        LocalDateTime endOfDay = LocalDateTime.now().minusDays(1).with(LocalTime.MAX);

        List<PunchLog> punchLogs = punchLogRepository.findByPunchTimeBetween(startOfDay, endOfDay);

        Map<String, List<PunchLog>> userPunchLogs = punchLogs.stream()
                .collect(Collectors.groupingBy(punchLog -> punchLog.getWorkScheduleSettings().getUserEmail()));

        userPunchLogs.forEach((userEmail, logs) -> {
            logs.sort(Comparator.comparing(PunchLog::getPunchTime));

            LocalDateTime firstPunch = logs.get(0).getPunchTime();
            LocalDateTime lastPunch = logs.get(logs.size() - 1).getPunchTime();

            Duration workDuration = Duration.between(firstPunch, lastPunch);
            long workHours = workDuration.toHours();

            if (workHours < 6) {
                WorkScheduleSettings settings = workScheduleSettingsRepository.findById(userEmail).orElse(null);  //1000 emails
//
                if (settings != null) {
                    Project project = settings.getProject();
                    String managerEmail = project.getReportingManagerEmail();
                    System.out.println("Defaulter: " + userEmail + ", Hours Worked: " + workHours + ", Manager: " + managerEmail);

                    // TODO: logic to email this data to managers
                }
            }
        });

    }

}
