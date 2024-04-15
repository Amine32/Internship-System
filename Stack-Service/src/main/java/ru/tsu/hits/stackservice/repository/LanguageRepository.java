package ru.tsu.hits.stackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.stackservice.model.LanguageEntity;

import java.util.List;

public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {
    List<LanguageEntity> findAllByRelatedStacks_Name(String stackName);

    List<LanguageEntity> findAllByRelatedTechnologies_Name(String technologyName);
}

