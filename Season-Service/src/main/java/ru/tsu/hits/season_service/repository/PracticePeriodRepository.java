package ru.tsu.hits.season_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.season_service.model.PracticePeriodEntity;

public interface PracticePeriodRepository extends JpaRepository<PracticePeriodEntity, String> {
}
