package ru.tsu.hits.internshipapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.internshipapplication.model.InterviewEntity;

public interface InterviewRepository extends JpaRepository<InterviewEntity, String> {
}
