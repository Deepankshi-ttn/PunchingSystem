package com.example.punchingSystem.service.punchlog;

import org.springframework.web.multipart.MultipartFile;

public interface PunchLogService {
    void processPunchLogFile(MultipartFile file) throws Exception;
}
