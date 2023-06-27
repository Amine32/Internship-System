package ru.tsu.hits.internshipapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.internshipapplication.model.StudentProfile;

public interface StudentRepository extends JpaRepository<StudentProfile, String> {
}
