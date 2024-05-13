package ru.tsu.hits.application_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.application_service.model.StudentEntity;

import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<StudentEntity, String> {
    Page<StudentEntity> findByApplications_PositionIdIn(List<String> positionIds, Pageable pageable);

    Page<StudentEntity> findByIdIn(Set<String> studentIds, Pageable pageable);
}
