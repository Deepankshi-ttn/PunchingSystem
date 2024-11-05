package com.example.punchingSystem.scheduler;

import com.example.punchingSystem.NotificationSender;
import com.example.punchingSystem.entity.Project;
import com.example.punchingSystem.entity.PunchLog;
import com.example.punchingSystem.entity.WorkScheduleSettings;
import com.example.punchingSystem.repository.PunchLogRepository;
import com.example.punchingSystem.repository.WorkScheduleSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PunchLogScheduler {

    @Autowired
    private PunchLogRepository punchLogRepository;

    @Autowired
    private WorkScheduleSettingsRepository workScheduleSettingsRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private NotificationSender notificationSender;


    @Scheduled(cron = "0 0 06 * * ?") // Run every day at 6 AM
    public void identifyAndReportDefaulters() {
//        System.out.println("I RAN");
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
                WorkScheduleSettings settings = logs.get(0).getWorkScheduleSettings();
                //workScheduleSettingsRepository.findById(userEmail).orElse(null);  //1000 emails
//
                if (settings != null) {
                    Project project = settings.getProject();
                    String managerEmail = project.getReportingManagerEmail();

                    System.out.println("Defaulter: " + userEmail + ", Hours Worked: " + workHours + ", Manager: " + managerEmail);

                    // TODO: logic to email this data to managers
                }
            }
        });
        // Testing SMTP connection
        notificationSender.sendNotification();
        System.out.println("Async email");
    }


}
