package ru.tsu.hits.internshipapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.internshipapplication.model.StudentProfile;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentProfile, String> {
    List<StudentProfile> findByApplications_PositionIdIn(List<String> positionIds);
}
