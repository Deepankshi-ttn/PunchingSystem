package com.example.punchingSystem.classLoader;

import com.example.punchingSystem.entity.Project;
import com.example.punchingSystem.entity.WorkScheduleSettings;
import com.example.punchingSystem.repository.ProjectRepository;
import com.example.punchingSystem.repository.WorkScheduleSettingsRepository;
import com.example.punchingSystem.workshiftEnum.WorkShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

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
            project1.setName("Project Alpha");
            project1.setReportingManagerEmail("manager.alpha@tothenew.com");
            projectRepository.save(project1);

            Project project2 = new Project();
            project2.setName("Project Beta");
            project2.setReportingManagerEmail("manager.beta@tothenew.com");
            projectRepository.save(project2);
        }
        if (workScheduleSettingsRepository.count() == 0) {
            //Populate WorkScheduleSettings Table
            WorkScheduleSettings workSchedule1 = new WorkScheduleSettings();
            workSchedule1.setUserEmail("employee1@tothenew.com");
            workSchedule1.setProject(projectRepository.findById(1L).orElse(null));
            workSchedule1.setWorkShift(WorkShift.MORNING_SHIFT);;
            workSchedule1.setOfficeDays("Monday, Tuesday, Wednesday, Thursday, Friday");
            workScheduleSettingsRepository.save(workSchedule1);

            WorkScheduleSettings workSchedule2 = new WorkScheduleSettings();
            workSchedule2.setUserEmail("employee2@tothenew.com");
            workSchedule2.setProject(projectRepository.findById(2L).orElse(null));
            workSchedule2.setWorkShift(WorkShift.EVENING_SHIFT);
            workSchedule2.setOfficeDays("Monday,Tuesday,Thursday");
            workScheduleSettingsRepository.save(workSchedule2);
        }

    }
}
