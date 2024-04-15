package ru.tsu.hits.applicationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.applicationservice.model.InterviewEntity;

public interface InterviewRepository extends JpaRepository<InterviewEntity, String> {
}
