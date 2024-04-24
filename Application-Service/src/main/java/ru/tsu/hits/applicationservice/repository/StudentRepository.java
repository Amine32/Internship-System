package ru.tsu.hits.applicationservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.applicationservice.model.StudentProfile;

import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<StudentProfile, String> {
    Page<StudentProfile> findByApplications_PositionIdIn(List<String> positionIds, Pageable pageable);

    Page<StudentProfile> findByIdIn(Set<String> studentIds, Pageable pageable);
}
