package ru.tsu.hits.stackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.stackservice.model.StackEntity;

import java.util.List;

public interface StackRepository extends JpaRepository<StackEntity, Long> {
    List<StackEntity> findAllByRelatedLanguages_Name(String languageName);

    List<StackEntity> findAllByRelatedTechnologies_Name(String technologyName);
}

