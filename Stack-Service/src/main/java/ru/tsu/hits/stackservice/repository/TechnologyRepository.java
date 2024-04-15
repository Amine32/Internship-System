package ru.tsu.hits.stackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.stackservice.model.TechnologyEntity;

import java.util.List;

public interface TechnologyRepository extends JpaRepository<TechnologyEntity, Long> {
    List<TechnologyEntity> findAllByRelatedLanguages_Name(String languageName);

    List<TechnologyEntity> findAllByRelatedStacks_Name(String stackName);

    List<TechnologyEntity> findAllByRelatedStacks_NameAndRelatedLanguages_Name(String stackName, String languageName);
}
