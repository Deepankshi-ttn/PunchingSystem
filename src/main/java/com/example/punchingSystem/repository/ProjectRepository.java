package com.example.punchingSystem.repository;

import com.example.punchingSystem.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
