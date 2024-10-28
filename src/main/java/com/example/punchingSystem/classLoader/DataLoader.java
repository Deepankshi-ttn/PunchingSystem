package com.example.punchingSystem.classLoader;

import com.example.punchingSystem.entity.Project;
import com.example.punchingSystem.entity.WorkScheduleSettings;
import com.example.punchingSystem.repository.ProjectRepository;
import com.example.punchingSystem.repository.WorkScheduleSettingsRepository;
import com.example.punchingSystem.workshiftEnum.WorkShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.example.punchingSystem.workshiftEnum.WorkDays.*;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private WorkScheduleSettingsRepository workScheduleSettingsRepository;

    @Override
    public void run(String... args) throws Exception {

        if (projectRepository.count() == 0) {
            // Populate Project Table
            Project project1 = new Project();
            project1.setName("Project NSC");
            project1.setReportingManagerEmail("manager.nsc@tothenew.com");
            projectRepository.save(project1);

            Project project2 = new Project();
            project2.setName("Project Tataplay");
            project2.setReportingManagerEmail("manager.tataplay@tothenew.com");
            projectRepository.save(project2);
        }
        if (workScheduleSettingsRepository.count() == 0) {
            //Populate WorkScheduleSettings Table
            WorkScheduleSettings workSchedule1 = new WorkScheduleSettings();
            workSchedule1.setUserEmail("deepankshiarora@tothenew.com");
            workSchedule1.setProject(projectRepository.findById(1L).orElse(null));
            workSchedule1.setWorkShift(WorkShift.MORNING_SHIFT);;
            workSchedule1.setOfficeDays(Arrays.asList(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY));
            workScheduleSettingsRepository.save(workSchedule1);

            WorkScheduleSettings workSchedule2 = new WorkScheduleSettings();
            workSchedule2.setUserEmail("deepankshiarora@gmail.com");
            workSchedule2.setProject(projectRepository.findById(2L).orElse(null));
            workSchedule2.setWorkShift(WorkShift.EVENING_SHIFT);
            workSchedule2.setOfficeDays(Arrays.asList(MONDAY, TUESDAY, THURSDAY));
            workScheduleSettingsRepository.save(workSchedule2);

            WorkScheduleSettings workSchedule3 = new WorkScheduleSettings();
            workSchedule3.setUserEmail("deepankshiarora5359@gmail.com");
            workSchedule3.setProject(projectRepository.findById(1L).orElse(null));
            workSchedule3.setWorkShift(WorkShift.NIGHT_SHIFT);;
            workSchedule3.setOfficeDays(Arrays.asList(MONDAY, TUESDAY));
            workScheduleSettingsRepository.save(workSchedule3);

        }

    }
}
