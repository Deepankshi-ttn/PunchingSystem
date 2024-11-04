package com.example.punchingSystem.impl.punchlog;

import com.example.punchingSystem.entity.PunchLog;
import com.example.punchingSystem.entity.WorkScheduleSettings;
import com.example.punchingSystem.exception.DataNotFoundException;
import com.example.punchingSystem.exception.InvalidLineFormatException;
import com.example.punchingSystem.exception.PunchLogProcessingException;
import com.example.punchingSystem.repository.PunchLogRepository;
import com.example.punchingSystem.repository.WorkScheduleSettingsRepository;
import com.example.punchingSystem.service.punchlog.PunchLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Service
public class PunchLogServiceImpl implements PunchLogService {

    @Autowired
    private PunchLogRepository punchLogRepository;

    @Autowired
    private WorkScheduleSettingsRepository workScheduleSettingsRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PunchLogServiceImpl.class);

    @Override
    public void processPunchLogFile(MultipartFile file) throws Exception {
        // Define a DateTimeFormatter for the expected format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");// For parsing "28 Oct 2024 09:00:00"

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String userEmail = data[0].trim();
                    String punchTimeStr = data[1].trim();

                    LocalDateTime punchTime = LocalDateTime.parse(punchTimeStr, formatter);

                    WorkScheduleSettings workScheduleSettings = workScheduleSettingsRepository.findById(userEmail)
                            .orElseThrow(() -> new DataNotFoundException("Work schedule not found for user: " + userEmail));

                    // Create and save PunchLog
                    PunchLog punchLog = new PunchLog();
                    punchLog.setWorkScheduleSettings(workScheduleSettings);
                    punchLog.setPunchTime(punchTime);
                    punchLogRepository.save(punchLog);
                } else {
                    throw new InvalidLineFormatException("Invalid line format at: " + line);
                }
            }
        }catch (DateTimeParseException e) {
            throw new InvalidLineFormatException("Error parsing punch time: ");
        }
    }
}
