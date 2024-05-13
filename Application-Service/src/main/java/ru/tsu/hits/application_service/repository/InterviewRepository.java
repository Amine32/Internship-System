package ru.tsu.hits.application_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.application_service.model.InterviewEntity;

public interface InterviewRepository extends JpaRepository<InterviewEntity, String> {
}
