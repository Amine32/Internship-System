package ru.tsu.hits.stackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.stackservice.model.TechnologyEntity;

public interface TechnologyRepository extends JpaRepository<TechnologyEntity, Long> {
}
