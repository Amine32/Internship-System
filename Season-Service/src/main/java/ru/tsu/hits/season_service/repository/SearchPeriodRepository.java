package ru.tsu.hits.season_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.season_service.model.SearchPeriodEntity;

import java.util.Optional;

public interface SearchPeriodRepository extends JpaRepository<SearchPeriodEntity, String> {
    Optional<SearchPeriodEntity> findBySeasonId(String seasonId);
}
