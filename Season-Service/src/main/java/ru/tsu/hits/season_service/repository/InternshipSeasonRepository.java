package ru.tsu.hits.season_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.season_service.model.InternshipSeasonEntity;
import ru.tsu.hits.season_service.model.SeasonPhase;

import java.util.List;

public interface InternshipSeasonRepository extends JpaRepository<InternshipSeasonEntity, String> {
    List<InternshipSeasonEntity> findByPhase(SeasonPhase phase);
}
