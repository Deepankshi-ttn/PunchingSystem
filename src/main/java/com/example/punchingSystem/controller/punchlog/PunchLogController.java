package com.example.punchingSystem.controller.punchlog;

import com.example.punchingSystem.service.punchlog.PunchLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/punch-logs")
public class PunchLogController {
    @Autowired
    private PunchLogService punchLogService;

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
}
