package com.example.punchingSystem.controller.punchlog;

import com.example.punchingSystem.impl.punchlog.PunchLogServiceImpl;
import com.example.punchingSystem.scheduler.PunchLogScheduler;
import com.example.punchingSystem.service.punchlog.PunchLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/punch-logs")
public class PunchLogController {
    @Autowired
    private PunchLogService punchLogService;
    @Autowired
    private PunchLogScheduler scheduler;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPunchLogs(@RequestParam("file") MultipartFile file) {
        try {
            punchLogService.processPunchLogFile(file);
            return ResponseEntity.ok("Punch logs uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing the punch logs: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        scheduler.identifyAndReportDefaulters();
        return null;
    }
}
