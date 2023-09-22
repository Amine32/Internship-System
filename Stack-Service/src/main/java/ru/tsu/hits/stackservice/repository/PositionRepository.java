package ru.tsu.hits.stackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.stackservice.model.PositionEntity;

public interface PositionRepository extends JpaRepository<PositionEntity, String> {
}
